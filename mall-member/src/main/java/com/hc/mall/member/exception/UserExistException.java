package com.hc.mall.member.exception;

public class UserExistException  extends RuntimeException{
    public  UserExistException(){
        super("该用户名已存在");
    }
}
