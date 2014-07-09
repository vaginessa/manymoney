<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
  <head>
    <title>Sign up - ManyMoney Account</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <link rel='stylesheet' href='css/bt.css'>
    <link rel="stylesheet" href="css/register.css">
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
  </head>
  <body>
    <section class="content">
      <div class="form-unit">
        <a href="index.jsp" class="brand">
          <h1>ManyMoney</h1>
        </a>
        <h3>Create ManyMoney Account</h3>
        <form action="RegisterServlet" method="post" class="auth-form form-horizontal" onsubmit="return check()">
          <div class="face"></div>
          
          <input type="hidden" name="source" value="">
          <div class="form-field">
            <input type="email" name="email" placeholder="Email" autocomplete="off" value="" required class="form-control email" autofocus />
            <span class="icon icon-envelope-bold"></span>
          </div>
          <div class="form-field">
            <input type="text" name="name" placeholder='Name' autocomplete="off" value="" required class="form-control name" />
            <span class="icon icon-user-bold"></span>
          </div>
          <div class="form-field">
            <input type="password" placeholder="Password" autocomplete="off" name="password" required class="form-control password" />
            <span class="icon icon-lock"></span>
          </div>
           <div class="form-field" id="messageBar" style="height: 30px;color: red;display: none">
          </div>
          <button type="submit"  class="btn btn-primary btn-large">
            Sign up
          </button>
          <div class="action-wrapper">
            <a href="Login.jsp" class="signup pull-right">Already have an account?</a>
          </div>
        </form>
      </div>
    </section>
    <script src="js/analytics.js"></script>
    <script type="text/javascript">
    var isemail=false;
    	function check()
    	{
    		if(window.isemail==false)
    			return false;
    		else
    			{
    			return true;
    			}
    	}
    	$("*[name='email']").blur(function(){
    		$.ajax({
    			type : "post",//请求方式
    			url : "EmailCheckServlet",//发送请求地址
    			data : {//发送给数据库的数据
    				email : $("*[name='email']").val(),
    			},
    			dataType:"html",
    			//请求成功后的回调函数有两个参数
    			success : function(data) {
    				var res=$(data).find("msg").text();
    				if("exist"==res)
    				{
    					$("#messageBar").css("display","block");
    					$("#messageBar").html("帐号存在");
    					window.isemail=false;
    				}
    				if("null"==res)
    				{
    					$("#messageBar").css("display","none");
    					window.isemail=true;
    				}
    			
    			}
    		});

    	});
    </script>
  </body>
</html>