package cn.lampup.decoration.example.springmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
//@EnableSwagger2
public class DecorationApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(DecorationApplication.class, args);
            log.info("{} 启动成功", DecorationApplication.class.getSimpleName());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
