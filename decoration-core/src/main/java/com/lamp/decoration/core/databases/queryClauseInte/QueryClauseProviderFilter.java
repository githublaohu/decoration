/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.decoration.core.databases.queryClauseInte;

import com.lamp.decoration.core.ConstantConfig;
import com.lamp.decoration.core.DecorationContext;
import com.lamp.decoration.core.databases.Querylimit;
import com.lamp.decoration.core.databases.QueryLimitData;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.config.invoker.DelegateProviderMetaDataInvoker;
import org.apache.dubbo.rpc.*;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;

/**
 * @author laohu
 */
@Activate(group = PROVIDER, order = 0)
public class QueryClauseProviderFilter extends ListenableFilter {

    private ConcurrentHashMap<Invoker<?>, QueryLimitData> querylimitMap = new ConcurrentHashMap<>();

    private ConstantConfig constantConfig;

    public QueryClauseProviderFilter() {
        //super.listener = new QueryClauseProviderListener();
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        QueryLimitData querylimitData = querylimitMap.get(invoker);
        if (Objects.isNull(querylimitData)) {
            querylimitData = new QueryLimitData();
            querylimitMap.put(invoker,querylimitData);
            if (invoker instanceof DelegateProviderMetaDataInvoker) {
                DelegateProviderMetaDataInvoker<?> delegateProviderMetaDataInvoker =
                        (DelegateProviderMetaDataInvoker<?>) invoker;
                Object ref = delegateProviderMetaDataInvoker.getMetadata().getRef();
                try {
                    Method method =
                            ref.getClass().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    Querylimit querylimit = method.getAnnotation(com.lamp.decoration.core.databases.Querylimit.class);
                    if(Objects.nonNull(querylimit)) {
                        querylimitData.setQueryLimit(true);
                    }
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (querylimitData.isQueryLimit()) {
            QueryClauseCentre.queryClauseHandler(context.getAttachment(QueryClause.QUERY_CLAUSE_KEY), null);
        }
        Result object = invoker.invoke(invocation);
        QueryClauseCentre.pageHandler(object.getValue());
        DecorationContext.get().close();
        return object;
    }


    class QueryClauseProviderListener implements Listener {

        @Override
        public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {


        }

        @Override
        public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

        }

    }

}