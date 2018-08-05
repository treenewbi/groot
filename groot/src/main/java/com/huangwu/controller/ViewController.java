package com.huangwu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Package: com.huangwu.controller
 * @Author: huangwu
 * @Date: 2018/8/4 15:05
 * @Description:
 * @LastModify:
 */
@Controller
public class ViewController {

    @GetMapping("/")
    public String upload() {
        return "upload";
    }


}
