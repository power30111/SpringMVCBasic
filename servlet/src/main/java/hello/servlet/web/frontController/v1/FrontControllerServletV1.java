package hello.servlet.web.frontController.v1;

import hello.servlet.web.frontController.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontController.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontController.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1",urlPatterns = "/front-controller/v1/*")   //v1 하위에 있는애가 호ㅜ출되면 일단 얘가 호출됨
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String, ControllerV1> controllerV1Map = new HashMap<>();

    public FrontControllerServletV1() {
        controllerV1Map.put("/front-controller/v1/members/new-form",new MemberFormControllerV1());  //각각의 패턴에 맞는 컨트롤러가 호출되게끔 함.
        controllerV1Map.put("/front-controller/v1/members/save",new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members",new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();    //Request로 온 URI를 String형식으로 받음.

        ControllerV1 controllerV1 = controllerV1Map.get(requestURI);        //Map에 저장되어있는 URI형식에 대한 value값을 받음.각 개체에 맞는 인스턴스 반환
        if(controllerV1 == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);       //만일 없을경우 SC_NOT_FOUND(404?) 반환
            return;
        }
        controllerV1.process(request,response);
    }
}
