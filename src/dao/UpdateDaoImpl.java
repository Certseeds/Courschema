package dao;

import bean.CourseBean;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateDaoImpl implements UpdateDao {
    DBUtil dbutil = new DBUtil();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement statement = null;
    ResultSetMetaData metaData = null;
    @Override
    public List<CourseBean> showList(String course, String department, String year,String plan) throws Exception {
        List<CourseBean> cb = null;
        connection = dbutil.getConnection();
        String sql0="update ********* from Course";
        preparedStatement=connection.prepareStatement(sql0);
        preparedStatement.execute();
        String sql="select ********* from Course";
        preparedStatement=connection.prepareStatement(sql);
        resultSet=preparedStatement.executeQuery();
        cb=new ArrayList<CourseBean>();
        while (resultSet.next()){
            CourseBean C = new CourseBean();
            //attributes
            cb.add(C);
        }
        return cb;
    }

    @Override
    public void Delete(int id, int planId) throws Exception {
        connection = dbutil.getConnection();
        String sql0="select * from plan_course where id_plan = ? and id_course = ?;";
        preparedStatement=connection.prepareStatement(sql0);
        preparedStatement.setInt(1,planId);
        preparedStatement.setInt(2,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            System.out.println("Exists id="+id+",plan="+planId);
        }else{
            System.out.println("does not exist id="+id+",plan="+planId);
        }
        sql0="delete from plan_course where id_plan = ? and id_course = ?;";
        preparedStatement=connection.prepareStatement(sql0);
        preparedStatement.setInt(1,planId);
        preparedStatement.setInt(2,id);
        int x = preparedStatement.executeUpdate();
        System.out.println(x);
    }

    @Override
    public int add(String pre,String courseName, String shortName, String credit, String semester, String major,
            Object year,Object department,Object plan) throws Exception {
        //major = group
        connection = dbutil.getConnection();
        String sql0="select * from course where name_course||'('||abbr_course||')'=?;";
        preparedStatement=connection.prepareStatement(sql0);
        preparedStatement.setString(1,courseName);
        resultSet = preparedStatement.executeQuery();
        int id_course = 0;
        if(!resultSet.next()){
            System.out.println("does not exist");
            return 0;
        }else{
            id_course = resultSet.getInt(1);
        }
        sql0 = "select id_plan from schema_plan sp join plan_course pc on sp.id_auto = pc.id_plan join major m on sp.major_schema = m.id_major\n" +
                "where year = ? and name_major like ? and pc.inter_year = ?";
        preparedStatement=connection.prepareStatement(sql0);
        preparedStatement.setInt(1,Integer.parseInt((String)year));
        preparedStatement.setString(2,"%"+(String)department+"%");
        preparedStatement.setInt(3,Integer.parseInt((String)plan)==13?1:2);
        resultSet = preparedStatement.executeQuery();
        int id_plan = 0;
        if(resultSet.next()){
            id_plan = resultSet.getInt(1);
            System.out.println("plan:"+id_plan);
        }
//        sql0 = "select * from course where name_course = ?";
//        preparedStatement=connection.prepareStatement(sql0);
//        preparedStatement.setString(1,courseName);
//        resultSet = preparedStatement.executeQuery();
//        int id_course = 0;
//        if(resultSet.next()){
//            id_course = resultSet.getInt(1);
//        }
        sql0 = "insert into plan_course values (?,?,?,?,?,?,?,?)";
        String group = "";
        switch (major){
            case "1":
                group = "专业先修";
                break;
            case "2":
                group = "专业选修";
                break;
            case "3":
                group = "专业核心课";
                break;
        }
        int len = 0;
        String[] pres = null;
        if(shortName!=null&&shortName!=""){
            pres = pre.split(",");
            len = pres.length;
            for(int i = 0;i<len;i++){
                pres[i] = pres[i].split("\\(")[0];
            }
            System.out.println(pres);
        }
        System.out.println(id_plan);
        System.out.println(id_course);
        System.out.println(courseName);
        System.out.println(shortName);
        System.out.println(Integer.parseInt(credit));
        System.out.println(4*Integer.parseInt(credit));
        System.out.println(group);
        System.out.println(Integer.parseInt(semester));
        System.out.println(Integer.parseInt((String)plan)==13?1:2);
        int re = 0;
        courseName = courseName.split("\\(")[0];
        System.out.println(courseName);
        if(len==0){
            preparedStatement=connection.prepareStatement(sql0);
            preparedStatement.setInt(1,id_plan);
            preparedStatement.setInt(2,id_course);
            preparedStatement.setString(3,courseName);
            preparedStatement.setInt(4,Integer.parseInt(credit));
            preparedStatement.setInt(5,Integer.parseInt(credit));
            preparedStatement.setString(6,group);
            preparedStatement.setInt(7,Integer.parseInt(semester));
            preparedStatement.setInt(8,Integer.parseInt((String)plan)==13?1:2);
            re = preparedStatement.executeUpdate();
        }else{
            for(int i = 0;i<len;i++){
                preparedStatement=connection.prepareStatement(sql0);
                preparedStatement.setInt(1,id_plan);
                preparedStatement.setInt(2,id_course);
                preparedStatement.setString(3,courseName);
                preparedStatement.setInt(4,Integer.parseInt(credit));
                preparedStatement.setInt(5,Integer.parseInt(credit));
                preparedStatement.setString(6,group);
                preparedStatement.setInt(7,Integer.parseInt(semester));
                preparedStatement.setInt(8,Integer.parseInt((String)plan)==13?1:2);
                re = preparedStatement.executeUpdate();
                System.out.println(re+" 1");
                String sql1 = "insert into pre_course(id_course, pre_course_id, groups) select c1.id_course,c2.id_course,1 from course c1 join course c2\n" +
                        "on c1.name_course = ? and c2.name_course = ?;";
                PreparedStatement ps = connection.prepareStatement(sql1);
                ps.setString(1,courseName);
                ps.setString(2,pres[i]);
                System.out.println(sql1);
                re = ps.executeUpdate();
                System.out.println(re+" 2");
            }
        }
        return re;
    }

}
