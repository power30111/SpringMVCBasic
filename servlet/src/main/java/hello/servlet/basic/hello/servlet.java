package hello.servlet.basic.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="helloServlet",urlPatterns = "/hello")
public class servlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("servlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");              //Context type에 들어간다
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello "+username);   //response의 getWriter 메서드 = > HTTP body 에 메세지가 들어간다.

    }
}
