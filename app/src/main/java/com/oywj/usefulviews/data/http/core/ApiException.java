package com.oywj.usefulviews.data.http.core;

/**
 * Created by Android on 2017/7/11.
 * ApiException: 处理服务器主动返回异常。
 */
public class ApiException extends RuntimeException {
    private int code;

    public int getCode() {
        return code;
    }

    public ApiException(int code) {
        super(handleCode(code));
        this.code = code;
    }

    private static String handleCode(int code) {
        return "ApiException : statusCode == " + code;
    }

    public ApiException(String msg) {
        super(msg);
    }


}
