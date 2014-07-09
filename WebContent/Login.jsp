<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Log in - ManyMoney Account</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<link rel='stylesheet' href='css/bt.css'>
<link rel="stylesheet" href="css/register.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
</head>
<body>
	<section class="content">
		<div class="form-unit">
			<a class="brand" href="index.jsp">
				<h1>ManyMoney</h1>
			</a>
			<h3>Use your ManyMoney Account to login</h3>

			<form method="post" action="" class="auth-form form-horizontal">

				<div class="form-field">
					<input type="email" placeholder="Email" name="email" value=""
						class="form-control email" autofocus /> <span
						class="icon icon-envelope-bold"></span>
				</div>
				<div class="form-field">
					<input type="password" placeholder="Password" name="password"
						class="form-control password" /> <span class="icon icon-lock"></span>
				</div>
				<div class="form-field" style="height: 35px;display: none;text-align: center" id="messageBar">
					
				</div>
				<button type="submit" id="login" class="btn btn-primary btn-large"
					>Log in</button>
				<div class="action-wrapper">
					<a href="" class="forget">Fogot password?</a> <a href="Register.jsp"
						class="signup pull-right">Signup for free</a>
				</div>

			</form>
		</div>

	</section>

	
	<script type="text/javascript">
	
	$(".form-control").focus(function(){
		$("#messageBar").css("display","none");
	});
	
	$(document).ready(function () {
	    $("#login").click(function () {
	        login();
	        return false;
	    });
	});
	
	  function login() {
		$.ajax({
			type : "post",//请求方式
			url : "LoginServlet",//发送请求地址
			data : {//发送给数据库的数据
				email : $("*[name='email']").val(),
				password : $("*[name='password']").val()
			},
			dataType:"html",
			//请求成功后的回调函数有两个参数
			success : function(data) {
				 var res=$(data).find("msg").text();
				if("ok"==res)
					{
						//alert("登陆成功");
						window.location.href="main.jsp";
					}
				if("notexist"==res)
				{
					$("#messageBar").css("display","block");
					$("#messageBar").html("帐号不存在");
				}
				if("Error"==res)
				{
					$("#messageBar").css("display","block");
					$("#messageBar").html("密码错误");
				}
			}
		});

	};
	</script>
	<script src="https://dn-st.qbox.me/pages/scripts/analytics.js"></script>
</body>
</html>