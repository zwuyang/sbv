package com.zlzy.sbv.common;

import java.util.HashMap;
import java.util.Objects;

public class CommonResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    //状态码
    private static final String CODE_TAG = "code";

    //消息
    private static final String MSG_TAG = "msg";

    //数据对象
    private static final String DATA_TAG = "data";

    public CommonResult(){}

    public CommonResult(int code, String msg){
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public CommonResult(int code, String msg, Object data){
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (Objects.nonNull(data)){
            super.put(DATA_TAG, data);
        }
    }

    public static CommonResult success(){
        return CommonResult.success("操作成功.");
    }

    public static CommonResult success(String msg){
        return CommonResult.success(msg, null);
    }

    public static CommonResult success(String msg, Object data){
        return new CommonResult(HttpStatus.SUCCESS , msg, data);
    }

    public static CommonResult error(){
        return CommonResult.error("操作失败.");
    }

    public static CommonResult error(String msg){
        return CommonResult.error(msg, null);
    }

    public static CommonResult error(String msg, Object data){
        return new CommonResult(HttpStatus.FAIL , msg, data);
    }

    @Override
    public CommonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
