<%@page import="java.util.ArrayList"%>
<%@page import="com.entity.DetailEntity"%>
<%@page import="com.entity.WalletEntity"%>
<%@page import="java.util.List"%>
<%@page import="com.entity.AccountEntity"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.service.*"%>
<%@ page import="com.dao.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="css/setting.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>ManyMoney</title>
</head>
<body>
	<%
		AccountEntity acc = new AccountEntity();
			if (session.getAttribute("user") == null) {
		response.sendRedirect("Login.jsp");
			} else {
		acc = (AccountEntity) session.getAttribute("user");
		int ID = acc.getID();
			}
			
			PrintToolService pts=new PrintToolService();
	%>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<!-- We use the fluid option here to avoid overriding the fixed width of a normal container within the narrow content columns. -->
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-6">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">ManyMoney</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-6">
			<ul class="nav navbar-nav">
				<li style="padding-left: 70px;"><a href="main.jsp">记一笔</a></li>
				<li style="padding-left: 70px;"><a href="report.jsp">报表</a></li>
				<li class="active" style="padding-left: 70px;"><a
					href="setting.jsp">设置</a></li>
			</ul>
			<input type="text" id="userID" style="display: none"
				value="<%=acc.getID()%>">
			<div id="header-bar">
				<img class="header-img" src="images/sm1.jpg"/>
				<a href="setting.jsp" id="header-name">
					<%=acc.getNickName()%>
				</a>
				<!-- Split button -->
				<select id="walletID">
					<%
						List<WalletEntity> wel=new WalletService().GetWalletByid(acc.getID());
					%>
					<%
						for(int i=0;i<wel.size();i++)
																											{
																												out.print("<option value="+wel.get(i).getWalletID()+"><a href='changeWalletServlet'>"+wel.get(i).getWalletName()+"</a></option>");
																											}
					%>
					<option>
						<新建钱包>
					</option>
				</select>

				<div id="logout">
					<a href="LogoutServlet">注销</a>
				</div>
			</div>

		</div>
		<!-- /.navbar-collapse -->
	</div>
	</nav>
	<div id="main" style="height: 480px; width: 1000px; margin-top: 150px;">
		<div id="bar-left">
			<ul class="nav nav-pills nav-stacked" role="tablist"
				style="max-width: 300px;">
				<li class="active" id="person-li"><a href="#">个人设置</a></li>
				<li id="password-li"><a href="#">修改密码</a></li>
			</ul>
		</div>
		<div id="setting-right">
			<div id="personal_setting">
				
				<table>
					<tr>
						<td class="first">注册邮箱</td>
						<td class="second"><label id="email"><%=acc.getEmail()%></label></td>
						<td class="third">注册邮箱不可更改</td>
					</tr>
					<tr>
						<td>昵称</td>
						<td><input id="nickname" type="text"
							value="<%=acc.getNickName()%>" /></td>
						<td></td>
					</tr>
					<tr>
						<td>性别</td>
						<td><select id="sex-select">
								<option value="男">男</option>
								<option value="女">女</option>
								<option value="不详">不详</option>
						</select></td>
						<td></td>
					</tr>

					<tr>
						<td>生日</td>
						<td><input id="birthday" type="text"
							value="<%=acc.getBirthday()%>" /></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="3">
							<button type="button" class="btn btn-success"
								style="margin-left: 150px;" id="save-button">保存</button>
						</td>
					</tr>
				</table>
				<div id="info"></div>
				
			</div>

			<div id="change_password" style="display: none">
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">原密码</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="oldpassword"
								placeholder="Password">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">新密码</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="inputPassword1"
								placeholder="Password">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">新密码</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="inputPassword2"
								placeholder="Password">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-default">修改</button>
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>

</body>
<script type="text/javascript">
	$("#person-li").click(function() {
		$("#person-li").addClass("active");
		$("#password-li").removeClass("active");
		$("#personal_setting").css("display", "block");
		$("#change_password").css("display", "none");
	});

	$("#password-li").click(function() {
		$("#password-li").addClass("active");
		$("#person-li").removeClass("active");
		$("#personal_setting").css("display", "none");
		$("#change_password").css("display", "block");
	});

	$("#save-button").click(function() {
		var nickname = $("#nickname").val();
		var sex = $("#sex-select").val();
		var birthday = $("#birthday").val();
		//alert($("#email").html());
		$.ajax({
			type : "post",//请求方式
			url : "ChangeInfoServlet",//发送请求地址
			data : {//发送给数据库的数据
				email : $("#email").html(),
				nickname : nickname,
				sex : sex,
				birthday : birthday,
			},
			dataType : "html",
			//请求成功后的回调函数有两个参数
			success : function(data) {
				var res = $(data).find("msg").text();
				if ("ok" == res) {
					
					$("#info").html("<div class='alert alert-success alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button><strong>成功!</strong> 修改成功！</div>");
				}
				else
					{
					$("#info").html("<div class='alert alert-danger alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button><strong>失败!</strong> 修改失败！</div>");	
					}

			}
		});

	});
</script>
</html>