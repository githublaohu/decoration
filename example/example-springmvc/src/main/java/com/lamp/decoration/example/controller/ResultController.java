package com.lamp.decoration.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("resultController")
@RequestMapping("/result")
//enum color{
//    RED, GREEN, BLUE;
//}
public class ResultController {

    @PostMapping("/resultVoid")
    public void resultVoid(){

    }
    @PostMapping("/resultInteger")
    public Integer resultInteger(@RequestParam("value") Integer integer){
        return integer;
    }

    @PostMapping("/resultString")
    public String resultString(){
        return "resultString";
    }
    @PostMapping("/resultLong")
    public Long resultLong(){
        return 1L;
    }

    @PostMapping("/resultException")
    public Exception resulObject(){
        return new Exception("example exception");
    }



    @PostMapping("/resultEnum")
    public Enum<?> resultEnum(Enum<?> colorEnum){
        return colorEnum;
    }

    @PostMapping("/resultList")
    public List<?> resultList(){
        List<String> list = new ArrayList<>();
        list.add("1");
        return list;
    }


}
