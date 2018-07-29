package com.huangwu.controller;

import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.domain.vo.EmailVo;
import com.huangwu.domain.vo.UserVo;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

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

    @Resource
    private IUserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private IEmailService emailService;

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
//        String verifyCode = redisService.get(EmailKey.getEmailVerifyCode, userVo.getEmail(), String.class);
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
}
