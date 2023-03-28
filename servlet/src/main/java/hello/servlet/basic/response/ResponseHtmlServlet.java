package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="responseHtmlServlet",urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type : text/HTML;charset=utf-8  설정해줘야하니까 아래 두줄
        response.setContentType("text/html");   //HTTP 응답으로 HTML을 반환할때에는 content-type 을 text/html로 해줘야한다.
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();      //HTML BODY에 넣어줄 내용
        writer.write("<html>");
        writer.write("<body>");
        writer.write("<div>안녕하세요</div>");
        writer.write("</body>");
        writer.write("</html>");


    }
}
