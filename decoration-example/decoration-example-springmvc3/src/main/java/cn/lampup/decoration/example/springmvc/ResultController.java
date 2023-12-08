package cn.lampup.decoration.example.springmvc;


import com.lamp.decoration.core.DecorationContext;
import com.lamp.decoration.core.result.DecorationResultException;
import com.lamp.decoration.core.result.ResultObject;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("result")
public class ResultController {

    @GetMapping("exceptionResult")
    public void exceptionResult(){
        DecorationResultException.throwDecorationResultException(ResultObject.DEFAULT_DUPLICATE_FAIL);
    }

    @GetMapping("decorationContext")
    public void decorationContext(){
        DecorationContext.get().setResultObject(ResultObject.DEFAULT_DUPLICATE_FAIL);
        return;
    }


    @PostMapping("validResult")
    public void validResult(@Valid @RequestBody  Data data){

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
