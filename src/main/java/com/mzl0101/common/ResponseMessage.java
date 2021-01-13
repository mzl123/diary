package com.mzl0101.common;

/**
 * 统一返回类
 * @param <T>
 */

public class ResponseMessage<T> {

    private String code;

    private String msg;
    private T entity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public ResponseMessage(){ }

    public ResponseMessage(String code){
        this.code = code;
    }
    public ResponseMessage(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public ResponseMessage(String code,T entity){
        this.code = code;
        this.entity = entity;
    }
    public ResponseMessage(String code,String msg, T entity){
        this.code = code;
        this.msg = msg;
        this.entity = entity;
    }

}
