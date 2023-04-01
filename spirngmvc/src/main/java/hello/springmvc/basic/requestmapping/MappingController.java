package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @RequestMapping(value = {"/hello-basic","/hello-go"})   //여러개의 url을 넣을수도있다.
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }
    @RequestMapping(value = "/mapping-get-v1",method = RequestMethod.GET)   //받는 방식(GET,POST 등등)을 설정해줄수있따
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }
    /**
     * 편리한 축약 애노테이션
     * @GetMapping              -> GET으로만 받을수있어
     * @PostMapping             -> POST로만 받을수있어
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @pathVariable("userID") String userID -> @PathVariable userID
     * /mapping/userA               -->url 자체에 값이 들어있다.
     */
    @GetMapping("/mapping/{userID}")
    public String mappingPath(@PathVariable("userID") String data){    //경로변수
        log.info("mappingPath userID ={}",data);
        return "ok";
    }
    /**
     * PathVariable 다중 사용
     */

    @GetMapping("mapping/users/{userID}/orders/{orderID}")
    public String mappingPath(@PathVariable("userID") String userId, @PathVariable("orderID") Long orderId){
        log.info("mappingPath userId ={}, orderId = {}",userId,orderId);
        return "ok";
    }
    /**
     * 파라미터로 추가 매핑
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug"
     * params={"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-header",headers = "mode=debug") //특정 파라미터 정보가 있을때 동작.
    public String mappingParam(){                                   //params -> 저 value에 적힌 url 에 추가적으로 뭔가 있어야함.
        log.info("mappingParam");                               //headers -> 헤더에 추가적으로 뭔가가 있어ㅑ함
        return "ok";
    }
    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE) //HTTP 요청의 Content-Type헤더를 기반으로 조건에 맞을경우 메서드실행
    public String mappingConsumes() {   //consumes = "application/json"
        log.info("mappingConsumes");
        return "ok";
    }
    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)    //->헤더에 Accept : text/html
    public String mappingProduces() {       //produces = "text/html"
        log.info("mappingProduces");
        return "ok";
    }
}
