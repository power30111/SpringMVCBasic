package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController

public class RequestHeaderController {
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,                //HTTP method(GET POST 등)
                          Locale locale,                        //위치정보
                          @RequestHeader MultiValueMap<String, String> headerMap,       //Header정보를 Map에 담아서 반환
                          @RequestHeader("host") String host,                           //특정 Header 를 지정하여 반환
                          @CookieValue(value = "myCookie", required = false)String Cookie)  //Cookie 반환(required false ? ->필수아님)
                    {
                        log.info("request={}", request);
                        log.info("response={}", response);
                        log.info("httpMethod={}", httpMethod);
                        log.info("locale={}", locale);
                        log.info("headerMap={}", headerMap);
                        log.info("host={}", host);
                        log.info("Cookie={}", Cookie);

                        return "ok";
    }
}
