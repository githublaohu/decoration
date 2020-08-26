package com.lamp.decoration.core.databases.pagehelper;

import java.util.Objects;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import com.github.pagehelper.PageHelper;

public class PagehelperFilter implements Filter {

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String pageSize = context.getAttachment("pageSize");
        String limitCoordinate = context.getAttachment("limitCoordinate");
        if(Objects.nonNull(pageSize) && Objects.nonNull(limitCoordinate)) {
            PageHelper.offsetPage(Integer.valueOf(limitCoordinate), Integer.valueOf(pageSize));
            context.setAttachment( "pageSize" , pageSize);
            context.setAttachment( "limitCoordinate" , limitCoordinate);
        }
        String orderBy = context.getAttachment("orderBy");
        if(Objects.nonNull(orderBy)) {
            PageHelper.orderBy(orderBy);
            context.setAttachment( "orderBy" , orderBy);
        }
        
        return invoker.invoke(invocation);
    }

}
