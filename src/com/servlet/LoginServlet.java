package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import com.entity.AccountEntity;
import com.entity.WalletEntity;
import com.service.MD5;
import com.service.UserService;
import com.service.WalletService;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=req.getSession();  
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/xml;charset=UTF-8");
		// resp.setHeader("Cache-Control", "no-cache");

		PrintWriter out = resp.getWriter();
		System.out.println("AJAX invoked!");
		String username = req.getParameter("email");
		String password = req.getParameter("password");
		password = MD5.Md5(password);
		UserService us = new UserService();
		List<Object> result = us.Login(username, password);
		System.out.println(result.get(0).toString());

		if (result.get(0).toString().equals("ok")) {
			AccountEntity acc=(AccountEntity)result.get(1);
			session.setAttribute("user", acc);
			
			List<WalletEntity> wel=new WalletService().GetWalletByid(acc.getID());
			session.setAttribute("defaultWallet", wel.get(0).getWalletID());
			
			System.out.println("login ok");
			System.out.println("session "+session.getAttribute("username"));
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>ok</name></result>");
		}
		if (result.get(0).toString().equals("notexist")) {
			System.out.println("notexist");
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>notexist</name></result>");
		}
		if (result.get(0).toString().equals("Error")) {
			System.out.println("Error");
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>Error</name></result>");
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
