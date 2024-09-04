package cn.lampup.decoration.example.springmvc;


//import com.lamp.decoration.core.DecorationContext;
//import com.lamp.decoration.core.result.DecorationResultException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamp.decoration.core.result.ResultObject;
import com.lamp.decoration.core.result.third.WangeditorResultObject;
import com.lamp.decoration.core.result.third.WangeditorResultObject.WangeditorResultData;

@RestController
@RequestMapping("result")
public class ResultController {


    @PostConstruct
    public void init(){
        this.getClass();
    }

    @GetMapping("exceptionResult")
    public void exceptionResult(){
        //DecorationResultException.throwDecorationResultException(ResultObject.DEFAULT_DUPLICATE_FAIL);
    }

    @GetMapping("decorationContext")
    public void decorationContext(){
        //DecorationContext.get().setResultObject(ResultObject.DEFAULT_DUPLICATE_FAIL);
        return;
    }


    @PostMapping("validResult")
    public void validResult(@Valid @RequestBody  Data data){

    }

    @PostMapping("resultList")
    public List<String> resultList(){
        return Collections.emptyList();
    }

    @PostMapping("resultArrayList")
    public ArrayList<String> resultArrayList(){
        return new ArrayList<>();
    }

    @PostMapping("resultResultObjectList")
    public ResultObject<List<String>> resultResultObjectList(){
        return ResultObject.success(Collections.emptyList());
    }

    @PostMapping("resultExclude")
    public WangeditorResultObject  resultExclude(){
        WangeditorResultObject welcomeitorResultObject = new WangeditorResultObject();
        welcomeitorResultObject.setErrno(0);
        welcomeitorResultObject.setData(new WangeditorResultData());
        return welcomeitorResultObject;
    }

    static class Data{
        @NotNull
        private String id;

        @Length(min = 5 , max = 31)
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
