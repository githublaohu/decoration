package cn.lampup.decoration.example.springmvc;


import com.lamp.decoration.core.duplicate.DuplicateSubmission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("duplicateSubmission")
public class DuplicateSubmissionController {


    public void pageAll(){

    }


    @DuplicateSubmission
    public void lock(){

    }
}
