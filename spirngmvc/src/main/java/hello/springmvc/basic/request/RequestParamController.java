package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username ={}, age ={}",username,age);

        response.getWriter().write("ok");
    }
    @ResponseBody               //RestController같이 HttpBody에 return값 넣어줌
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memeberName,   //쿼리파라미터를 통해 들어온 값들 저장
            @RequestParam("age") int memberAge){
        log.info("username ={}, age ={}",memeberName,memberAge);
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,  //요런식으로 간단하게 할수있으나
            @RequestParam int age){         //단 parameter로 오는 변수명과 동일해야만 한다.
        log.info("username ={}, age ={}",username,age);
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username,int age){
        //단순타입(Int,String,Integer등등 객체아닌거는 더 줄일수있다.
        // 다만 여기서도 parameter와 변수명이 동일해야한다.
        log.info("username ={}, age ={}",username,age);
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age){
            //required -> 해당 파라미터의 필수여부 판별(단 ""값이 통과될수있으므로 주의)
            //int 에는 null이 들어갈수없기떄문에 int age값을 파라미터에 안넣어줄경우 500
            //그래서 Integer로 변경하여 null값이  들어가도 문제없게끔 하거나 defalut값 설정해야함
        log.info("username ={}, age ={}",username,age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap){
        //Parameter 를 Map으로도 받을수있고, MultiValue로도 받을수있다,
        // ex) userId=id1&userId=id2 -> key는 같은데 value값이 다르다..
        //이럼 MultiValueMap 을 사용하면 key하나에 여러value가 나오게할수있다.
        log.info("username ={}, age = {}",paramMap.get("username"),paramMap.get("age"));
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modeAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username ={}, age = {}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modeAttributeV2(HelloData helloData){//ModelAttibute 생략가능..
        log.info("username ={}, age = {}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }
}
