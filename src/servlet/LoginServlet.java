package servlet;


import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService userservice = new UserServiceImpl();
        int result = 0;
        result = userservice.login(username, password);
        if (result == 1) {
            try {
                request.getSession().setAttribute("authority",userservice.getAU(username));
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute("username",username);
            request.getRequestDispatcher("SelfInfoServlet").forward(request, response);
        } else {
            response.getWriter().println("<script>alert('用户名或密码错误。'); window.location='login.jsp' </script>");
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}