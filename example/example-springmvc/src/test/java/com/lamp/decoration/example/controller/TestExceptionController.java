package com.lamp.decoration.example.controller;

import com.alibaba.fastjson.JSON;
import com.lamp.decoration.core.result.ResultObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestExceptionController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    ResultController bean;

    public void befead(){
        bean = webApplicationContext.getBean(ResultController.class);

    }


    @Test
    public void testException() throws Exception {
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/exception/exception")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

        ResultObject<Object> resultObject =  JSON.parseObject(content, ResultObject.class);

        Assert.assertEquals(resultObject.getCode(),Integer.valueOf(400));
        Assert.assertEquals(resultObject.getMessage(),"系统异常");
        Assert.assertEquals(resultObject.getData(),"throw runtime exception ");


    }
}
