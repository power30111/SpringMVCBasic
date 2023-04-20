package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        //HTTP 상태코드 404, 500 일때 에러페이지 정의
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class,"/error-page/500");
        //Runtime Exception 일때 에러페이지( Runtime Exception의 자식들 또한 포함)
        factory.addErrorPages(errorPage404,errorPage500,errorPageEx);

    }
}
