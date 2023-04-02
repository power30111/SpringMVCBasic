package hello.springmvc.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();  //일단 Request로 온 Stream을 Byte형태의 스트림으로 받고,
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//그걸 UTF-8형식으로 String으로 변환

        log.info(messageBody);

        response.getWriter().write("ok");
    }
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        //아예 inputStream 형태로 받아버릴수있다. response 또한 Writer 객체로 받아버리자.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//그걸 UTF-8형식으로 String으로 변환

        log.info("messageBody = {}",messageBody);

        responseWriter.write("ok");
    }
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        //HttpEntity 를 사용하면  request,response를 각각 따로 받아줄필요없이, HttpBody에 있는 내용을 문자로 바꿔서
        //httpEntity에 넣어준다.(HTTP Converter) 또한  반환또한 HttpEntity로 해줄수있다.

        String body = httpEntity.getBody(); //http 메세지에 있는 body내용을 꺼내기.
        log.info("messageBody = {}",body);

        return new HttpEntity<>("ok");
    }
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        //HTTP body 읽어서 String형태로 messageBody에 넣어준다..
        log.info("messageBody = {}",messageBody);
        //요청 오는건 @RequestBody, 응답하는건 @ResponseBody
        return "ok";
    }
}
