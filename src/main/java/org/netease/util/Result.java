package org.netease.util;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 自定义返回值类型
 *
 * @author : AnWen
 * @version :1.0
 * @date : 2019/11/28 11:09
 * @email : anwen375@qq.com
 */
public class Result<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    /**成功*/
    public static final int SUCCESS = 1;
    public static final String SUCCESS_MESSAGE = "操作成功";
    /**错误*/
    public static final int ERROR = 0;
    private static final String ERROR_MESSAGE = "操作失败";


    /**状态码*/
    private int code;

    /**返回内容*/
    private String message;

    /** 数据对象*/
    private T data;

    private Result() {
    }


    public static boolean isSuccess(Result<?> result) {
        return result.getCode () == SUCCESS;
    }


    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success (SUCCESS, SUCCESS_MESSAGE, (T) null);
    }

    public static <T> Result<T> success(String message) {
        return success (SUCCESS, message, (T) null);
    }

    public static <T> Result<T> success(T data) {
        return success (SUCCESS, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return success (SUCCESS, message, data);
    }

    public static <T> Result<T> success(int code, String message, T data) {
        return new Result<> (code, message, data);
    }


    public static <T> Result<T> error() {
        return error (ERROR, ERROR_MESSAGE, (T) null);
    }

    public static <T> Result<T> error(int code) {
        return error (code, ERROR_MESSAGE, (T) null);
    }

    public static <T> Result<T> error(String message) {
        return error (ERROR, message, (T) null);
    }

    public static <T> Result<T> error(String message, T data){
        return error(ERROR, message, data);
    }

    public static <T> Result<T> error(T data) {
        return error (ERROR, ERROR_MESSAGE, data);
    }

    public static <T> Result<T> error(int code, String message) {
        return error (code, message, (T) null);
    }

    public static <T> Result<T> error(int code, T data) {
        return error (code, ERROR_MESSAGE, data);
    }

    public static <T> Result<T> error(int code, String message, T data) {
        return new Result<> (code, message, data);
    }

    public static <T> Result<T> response(boolean result) {
        return result ? success () : error ();
    }

    public static <T> Result<T> response(int rows) {
        return rows > 0 ? success () : error ();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return JSON.toJSONString (this);
    }
}
