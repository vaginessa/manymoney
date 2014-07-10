package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.MD5;
import com.service.UserService;

public class EmailCheckServlet extends HttpServlet {

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
		System.out.println("AJAX checkEmail invoked!");
		String username = req.getParameter("email");
		UserService us = new UserService();
		Boolean res=us.CheckEmail(username);

		if (res) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>exist</name></result>");
		}
		if (!res) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>null</name></result>");
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
