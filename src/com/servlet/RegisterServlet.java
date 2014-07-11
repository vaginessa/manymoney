package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.font.EAttribute;

import com.entity.AccountEntity;
import com.service.MD5;
import com.service.UserService;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = req.getParameter("email");//获得注册时的邮箱
		String name=req.getParameter("name");//获得注册时的用户名
		String password = req.getParameter("password");//获得注册时的密码
		AccountEntity account=new AccountEntity(); //创建一个用户类
		account.setEmail(email);
		account.setNickName(name);
		account.setPassword(MD5.Md5(password));//密码MD5加密
		account.setSex("不详");
		UserService us=new UserService();
		if(us.Register(account)){//调用注册方法
			resp.getWriter().print("ok");
			resp.sendRedirect("main.jsp");
		}
		
	}
	
}
