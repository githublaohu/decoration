package cn.lampup.decoration.example.springmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
//@EnableSwagger2
public class DecorationApp3lication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(DecorationApp3lication.class, args);
            log.info("{} 启动成功", DecorationApp3lication.class.getSimpleName());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
