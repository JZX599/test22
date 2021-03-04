package com.jzx.blog.common.lang;

import lombok.Data;
import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code; // 结果集状态码  200正常 404页面丢失  ...
    private String msg;// 返回消息
    private Object data;// 返回数据
    private Integer currentPage;// 当前页
    private Integer pageSize;// 当前总数据条数
    private Long total;// 总数据条数

    public static Result succ(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result fail(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result success(Object data){
        return succ(200,"操作成功",data);
    }
    public static Result fail(int code,String msg){
        return fail(code, msg);
    }
    public static Result success(int code,String msg){
        return succ(code, msg,null);
    }




    /*返回查询数据分页成功结果集*/

    public static Result succPage(int code,String msg,Object data,Integer currentPage,Integer pageSize,Long total){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setCurrentPage(currentPage);
        result.setPageSize(pageSize);
        result.setTotal(total);
        return result;
    }

    /*返回查询数据分页失败结果集*/

    public static Result failPage(int code,String msg,Integer currentPage,Integer pageSize,Long total){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        result.setCurrentPage(currentPage);
        result.setPageSize(pageSize);
        result.setTotal(total);
        return result;
    }






}
