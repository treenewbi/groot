package com.huangwu.controller;

import com.huangwu.common.Result;
import com.huangwu.domain.vo.SeckillUserVo;
import com.huangwu.common.Result;
import com.huangwu.domain.vo.SeckillUserVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 提供一个完整的秒杀功能实现
 *
 * @Package: com.huangwu.controller
 * @Author: huangwu
 * @Date: 2018/6/2 21:05
 * @Description:
 * @LastModify:
 */
@RequestMapping("/seckill")
@RestController
public class SeckillController {


    @PostMapping("/path")
    public Result<String> getSeckillPath(HttpServletRequest request,
                                         @RequestBody Map<String, Object> params) throws Exception {
        return Result.succees(null);
    }

    @PostMapping("/verifyCode")
    public Result<String> getSeckillVerifyCode(HttpServletResponse response, SeckillUserVo userVo,
                                               @RequestParam("goodsId") long goodsId) throws Exception {
        return Result.succees(null);
    }

    @PostMapping("/{path}/doSeckill")
    public Result<Integer> doSeckill(SeckillUserVo userVo, @RequestParam("goodsId") long goodsId, @PathVariable("path") String path) throws Exception {
        return Result.succees(null);
    }

    @PostMapping("/result")
    public Result<Long> seckillResult(SeckillUserVo userVo, @RequestParam("goodsId") long goodsId) throws Exception {
        return Result.succees(null);
    }
}
