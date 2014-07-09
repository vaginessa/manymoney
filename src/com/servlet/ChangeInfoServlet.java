package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.AccountEntity;
import com.service.MD5;
import com.service.UserService;

public class ChangeInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/xml;charset=UTF-8");

		PrintWriter out = resp.getWriter();
		System.out.println("AJAX updateInfo invoked!");
		String email = req.getParameter("email");
		String nickname = req.getParameter("nickname");
		String sex = req.getParameter("sex");
		String birthday = req.getParameter("birthday");
		
		AccountEntity acc=new AccountEntity();
		acc.setEmail(email);
		acc.setNickName(nickname);
		acc.setSex(sex);
		acc.setBirthday(birthday);
		UserService us = new UserService();
		Boolean res=us.changeInfoByEmail(acc);

		if (res) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>ok</name></result>");
			System.out.println("update success");
			HttpSession session=req.getSession();
			AccountEntity old=(AccountEntity)session.getAttribute("user");
			acc.setID(old.getID());
			session.setAttribute("user", acc);
		}
		if (!res) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>error</name></result>");
		}
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);

	}

}
