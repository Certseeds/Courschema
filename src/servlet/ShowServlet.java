package servlet;



import bean.CourseBean;
import service.ShowService;
import service.ShowServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ShowServlet")
public class ShowServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String year = "";
        String department = "";
        String plan = "";
        int cntForPara = 0;
        year = request.getParameter("year");
        department = request.getParameter("department");
        plan = request.getParameter("plan");
        if(request.getParameter("year")==null){
            cntForPara ++;
        }
        if(request.getParameter("department")==null){
            cntForPara ++;
        }
        if(request.getParameter("plan")==null){
            cntForPara ++;
        }
        System.out.println(cntForPara);
//        if(cntForPara>0&&cntForPara<3){
//            PrintWriter out = response.getWriter();
//            out.println("<script>alert('请输入三项内容。'); window.location='schema.jsp' </script>");
//            System.out.println("there");
//        }
        ShowService ss = new ShowServiceImpl();
        List<CourseBean> showCourse = null;
        if(year!=null&&department!=null&&plan!=null){
            showCourse = ss.courseList(year,department,plan);
        }else{
           showCourse = ss.courseList(null,null,null);
        }
        request.getSession().setAttribute("List",showCourse);
        request.getRequestDispatcher("schema.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

}