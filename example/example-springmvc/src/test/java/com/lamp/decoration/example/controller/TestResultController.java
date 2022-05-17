package com.lamp.decoration.example.controller;

import com.alibaba.fastjson.JSON;
import com.lamp.decoration.core.result.ResultObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
//@WebMvcTest
public class TestResultController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    ResultController bean;

    public void befead(){
         bean = webApplicationContext.getBean(ResultController.class);

    }


    @Test
    public void testResultVoid() throws Exception {
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultVoid")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

    }

    @Test
    public void testResultInteger() throws Exception {

        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultInteger").param("value","1")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();


         result=mvc.perform(MockMvcRequestBuilders.post("/result/resultInteger").param("value","-1")).andReturn();
         response = result.getResponse();
         content = response.getContentAsString();

    }


    @Test
    public void testResultString() throws Exception {
       MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultString")).andReturn();
       MockHttpServletResponse response = result.getResponse();
       String content = response.getContentAsString();

    }
    @Test
    public void testResultLong() throws Exception {
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultLong")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

    }
    @Test
    public void testResultObject() throws Exception {
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultObject")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

    }
    @Test
    public void testResultException() throws Exception {
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultException")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

    }
    @Test
    public void testResultEnum() throws Exception {
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultEnum")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();

    }
    @Test
    public void testResultList() throws Exception {
        MvcResult result=mvc.perform(MockMvcRequestBuilders.post("/result/resultList")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        ResultObject<Object> resultObject =  JSON.parseObject(content, ResultObject.class);

        Assert.assertEquals(resultObject.getCode(),Integer.valueOf(200));
        Assert.assertEquals(resultObject.getMessage(),"执行成功");
        List<String> list = new ArrayList<>();
        list.add("1");
        Assert.assertEquals(resultObject.getData().toString(),list.toString());
    }


}
