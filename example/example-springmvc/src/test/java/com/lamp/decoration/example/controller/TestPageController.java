package com.lamp.decoration.example.controller;

import com.alibaba.fastjson.JSON;
import com.lamp.decoration.core.databases.queryClauseInte.QueryClause;
import com.lamp.decoration.core.result.ResultObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles
public class TestPageController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    PageController bean;


    @Autowired
    DataSource dataSource;

    @Before
    public void befead() throws SQLException {
        bean = webApplicationContext.getBean(PageController.class);
        Connection connection = dataSource.getConnection();
    }

    @Test
    public void testPage() throws Exception {
        QueryClause queryClause = new QueryClause();
        queryClause.setLimitPageNum(0);
        queryClause.setLimitSize(20);
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/page/paging").header(QueryClause.QUERY_CLAUSE_KEY, JSON.toJSONString(queryClause))).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

        ResultObject<Object> resultObject =  JSON.parseObject(content, ResultObject.class);

        Assert.assertEquals(resultObject.getCode(),Integer.valueOf(400));
        Assert.assertEquals(resultObject.getMessage(),"系统异常");
        Assert.assertEquals(resultObject.getData(),"throw runtimne exception ");

    }

}
