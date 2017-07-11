package com.oywj.usefulviews.data.http.core;

/**
 * Created by Android on 2017/7/11.
 * HttpResult : 封装的是服务器返回的结果的状态信息。
 */
public class HttpResult<T> {
    private int code;
    private String msg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
