package service;

import bean.CourseBean;
import bean.UserBean;
import dao.UserDao;
import dao.UserDaoImpl;

import java.util.List;

public class UserServiceImpl implements UserService{
    private UserDao userDao = new UserDaoImpl();
    @Override
    public int login(String username, String password) {

        int result = 0;
        try {
            result = userDao.login(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<UserBean> info(String username) {
        List<UserBean> info = null;
        try {
            info = userDao.info(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    @Override
    public List<CourseBean> show(String username) {
        List<CourseBean> result = null;
        try {
            result = userDao.show(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<UserBean> notice(String username) {
        List<UserBean> result = null;
        try {
            result = userDao.notice(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public int getAU(String username) throws Exception {
        return userDao.getAU(username);
    }

    @Override
    public int updatePassword(String emailAdd, String newPassword) throws Exception {
        return userDao.updatePassword(emailAdd, newPassword);
    }
}
