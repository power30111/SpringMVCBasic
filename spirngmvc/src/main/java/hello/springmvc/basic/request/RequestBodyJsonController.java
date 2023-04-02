package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    /**
     *입력
     * {"username":"hello","age":20}
     * content-type = application/json
     */
    private ObjectMapper objectMapper = new ObjectMapper();
    //Json 이니까 ObjectMapper 필요

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        //objectMapper를 통해(string형태로 적혀있는 messageBody룰 HelloData.class에 맞게 변환하여 넣어줄수있다.
        log.info("username = {}, age = {}",helloData.getUsername(),helloData.getAge());

        response.getWriter().write("ok");
    }
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        //@RequestBody와 ResponseBody 활용하기
        log.info("messageBody = {}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        //@RequestBody에 객체를 지정하여 넣기.
        log.info("username = {}, age = {}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity){
        HelloData data = httpEntity.getBody();
        log.info("username = {}, age = {}",data.getUsername(),data.getAge());

        return "ok";
    }
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data){
        log.info("username = {}, age = {}",data.getUsername(),data.getAge());

        return data;
        //json 으로 받은뒤 HelloData에 맞게 변환하여 사용후 다시 반환도 HelloData(json)으로 할수있다.
        //객체가 반환될떄 (return) 다시 json으로 변환된다.
    }
}
