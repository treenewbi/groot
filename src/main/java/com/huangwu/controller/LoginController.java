package com.huangwu.controller;

import com.huangwu.common.Result;
import com.huangwu.domain.vo.LoginVo;
import com.huangwu.service.IEmailService;
import com.huangwu.service.IUserService;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.domain.vo.LoginVo;
import com.huangwu.service.IEmailService;
import com.huangwu.service.IUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 登录相关API
 *
 * @Package: com.huangwu.controller
 * @Author: huangwu
 * @Date: 2018/5/19 18:59
 * @Description:
 * @LastModify:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private IEmailService mailService;

    @Resource
    private IUserService userService;

    @RequestMapping("/login")
    public Result<GrootUser> login() throws Exception {
        mailService.sendRegisterHtmlEmail("314303732dadada@qq.com");
        return Result.succees(null);
    }

    @RequestMapping("/checkPhone")
    public Result<LoginVo> checkPhone(@RequestBody() @Valid LoginVo loginVo) throws Exception {
        return Result.succees(loginVo);
    }
}
