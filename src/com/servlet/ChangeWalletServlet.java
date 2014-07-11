package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

public class ChangeWalletServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = resp.getWriter();
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/xml;charset=UTF-8");
		HttpSession session=req.getSession();
		
		String walletID=req.getParameter("wallet");
		System.out.println("change to "+walletID);
		session.setAttribute("defaultWallet", walletID);
		//resp.sendRedirect("main.jsp");
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<result><msg>ok</name></result>");
	}

}
