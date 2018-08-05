package com.huangwu.controller;

import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.domain.vo.EmailVo;
import com.huangwu.domain.vo.UserVo;
import com.huangwu.parse.ExcelResolver;
import com.huangwu.parse.Resolver;
import com.huangwu.redis.EmailKey;
import com.huangwu.service.IEmailService;
import com.huangwu.service.IUserService;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.domain.vo.EmailVo;
import com.huangwu.domain.vo.UserVo;
import com.huangwu.exception.GlobalException;
import com.huangwu.redis.EmailKey;
import com.huangwu.service.IEmailService;
import com.huangwu.service.IUserService;
import com.huangwu.util.CollectionHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 用户操作相关API
 *
 * @Package: com.huangwu.controller
 * @Author: huangwu
 * @Date: 2018/5/27 11:18
 * @Description:
 * @LastModify:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUFFIX_2013 = ".xls";
    private static final String SUFFIX_2017 = ".xlsx";
    private static final int FILE_MIN_SIZE = 2;
    private static final int FILE_MAX_SIZE = 10000;
    private static final String[] EXCEL_TEMPLATE_CELL_NAME_ARRAY = {"用户名", "手机号", "用户密码", "盐", "角色id", "是否删除"};
    private static final int EXCEL_TEMPLATE_CELL_NUM = EXCEL_TEMPLATE_CELL_NAME_ARRAY.length;

    @Resource
    private IUserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IEmailService emailService;

    @Resource
    private ExcelResolver excelResolver;

    /**
     * @api POST /user/register 注册用户
     * @apiGroup user
     * @apiRequest Json
     * @apiParam userName String 用户名
     * @apiParam password String 用户密码
     * @apiParam verifyCode String 邮件注册码
     * @apiParam email String 邮箱
     * @apiExample Json
     * {userName:"zhangsan",password:"dsadsadsa",verifyCode:"4589",email:"123456@qq.com"}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam true Boolean 是否注册成功
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {true}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<Boolean> register(@Valid @RequestBody UserVo userVo) throws Exception {
        String verifyCode = redisTemplate.opsForValue().get(EmailKey.emailVerifyCodeKey.realKey(userVo.getEmail())).toString();
        if (StringUtils.isBlank(verifyCode) || !verifyCode.equals(userVo.getVerifyCode())) {
            throw new GlobalException(ErrorCode.EMAIL_VERIFY_CODE_ERROR);
        }
        GrootUser user = new GrootUser();
        user.setUserName(userVo.getUserName());
        user.setPassword(userVo.getPassword());
        user.setEmail(userVo.getEmail());
        user.setIsDeleted(0);
        boolean sussees = userService.register(user);
        return Result.succees(sussees);
    }

    /**
     * @api POST /user/registerEmail 注册邮箱并发送邮件注册码
     * @apiGroup user
     * @apiRequest Json
     * @apiParam email String 邮箱
     * @apiExample Json
     * {email:"123456@qq.com"}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam true Boolean 是否注册成功
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {true}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/registerEmail", method = RequestMethod.POST)
    public Result<String> registerEmail(@Valid @RequestBody EmailVo emailVo) throws Exception {
        emailService.sendRegisterHtmlEmail(emailVo.getEmail());
        return Result.succees(null);
    }

    public void talk() {
        System.out.println("Im userController......");
    }

    @PostMapping(value = "queryUser")
    public Result<GrootUser> queryUserByName(String username) throws Exception {
        GrootUser user = userService.queryUser(username);
        return Result.succees(user);
    }

    @PostMapping(value = "/upload")
    public Result<Integer> uploadConfig(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new GlobalException(ErrorCode.ETCD_CONFIG_FILE_EMPTY);
        }
        //获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffixName = originalFilename == null ? "" : originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        Workbook workbook;
        try {
            if (SUFFIX_2013.equals(suffixName)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (SUFFIX_2017.equals(suffixName)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                throw new GlobalException(ErrorCode.FILE_TYPE_ERROR);
            }
        } catch (IOException e) {
            logger.error("{}文件解析异常", originalFilename);
            throw new GlobalException(ErrorCode.EXCEL_RESOLVE_ERROR);
        }
        Sheet sheet = workbook.getSheetAt(0);
        if (!checkSheetTitle(sheet)) {
            throw new GlobalException(ErrorCode.EXCEL_TEMPLATE_ERROR);
        }
        int totalNum = sheet.getLastRowNum();
        if (totalNum < FILE_MIN_SIZE || totalNum > FILE_MAX_SIZE) {
            throw new GlobalException(ErrorCode.EXCEL_SIZE_LIMIT_ERROR);
        }
        List<GrootUser> users = excelResolver.resolveExcel(sheet, totalNum);
        if (CollectionHelper.isEmpty(users)) {
            throw new GlobalException(ErrorCode.EXCEL_RESOLVE_ERROR);
        }
        int result = userService.batchAddUser(users);
        return Result.succees(result);
    }

    /**
     * 校验上传的文件是不是给定的模板文件，只检验模板头字段
     *
     * @param sheet
     * @return
     */
    private boolean checkSheetTitle(Sheet sheet) {
        // 校验第一行字段是否是给定的模板文件
        Row row = sheet.getRow(0);
        int cellNum = row.getPhysicalNumberOfCells();
        if (cellNum == EXCEL_TEMPLATE_CELL_NUM) {
            for (int i = 0; i < cellNum; i++) {
                if (!EXCEL_TEMPLATE_CELL_NAME_ARRAY[i].equals(row.getCell(i).getStringCellValue())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
