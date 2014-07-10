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
		
		String email = req.getParameter("email");
		String name=req.getParameter("name");
		String password = req.getParameter("password");
		
		AccountEntity account=new AccountEntity();
		account.setEmail(email);
		account.setNickName(name);
		account.setPassword(MD5.Md5(password));
		UserService us=new UserService();
		if(us.Register(account)){
			resp.getWriter().print("ok");
			resp.sendRedirect("main.jsp");
		}
		
	}
	
}
