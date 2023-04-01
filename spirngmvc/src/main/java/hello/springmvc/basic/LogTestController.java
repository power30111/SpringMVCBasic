package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@Slf4j
@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());  //@Slf4j (lombok에서 지원)을 사용하면 log클래스 지정안해줘도 알잘딱해줌
    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("name = " + name);

        log.trace("trace log={} ", name);
        log.debug("debug log={} ", name);
        log.info("info log = {} ", name);       //log.info("info log = "+name);으로도 가능한데 사용하면 안된다. 왜일까?
        log.warn("warn log = {} ", name);       //-> String가지고 +연산을 하면 비용이 발생하기 때문에 사용 x
        log.error("error log ={} ",name);

        return "ok";                //RestController에 의하여 HTTP Body에 return값이 담겨서 request.
    }
}
