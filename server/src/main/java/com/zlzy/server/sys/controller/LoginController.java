package com.zlzy.server.sys.controller;

import com.zlzy.sbv.common.model.LoginBody;
import com.zlzy.sbv.common.CommonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginBody loginBody){
        CommonResult result = CommonResult.success();
        String userName = loginBody.getUsername();
        String passWord = loginBody.getPassword();

        return result;
    }
}
