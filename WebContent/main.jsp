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

<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>ManyMoney</title>
</head>
<body onLoad="document.getElementById('price').focus();">
	<%
		int wid=0;
		AccountEntity acc = new AccountEntity();
		if (session.getAttribute("user") == null) {
			response.sendRedirect("Login.jsp");
		} else {
			acc = (AccountEntity) session.getAttribute("user");
			int ID = acc.getID();
		}
		
		if(session.getAttribute("defaultWallet")!=null)
		{
			wid=Integer.parseInt(session.getAttribute("defaultWallet").toString());
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
			<a class="navbar-brand" href="index.jsp">ManyMoney</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-6">
			<ul class="nav navbar-nav">
				<li class="active" style="padding-left: 70px;"><a href="#">记一笔</a></li>
				<li style="padding-left: 70px;"><a href="report.jsp">报表</a></li>
				<li style="padding-left: 70px;"><a href="setting.jsp">设置</a></li>
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
							
																			out.print("<option value='"+wel.get(i).getWalletID()+"'"); 
																			if(wel.get(i).getWalletID()==wid)
																			{
																				out.print("selected='select'");
																			}
																			out.print("><a href='changeWalletServlet'>"+wel.get(i).getWalletName()+"</a></option>");
																		}
					%>
					
				</select>

				<div id="logout">
					<a href="LogoutServlet">注销</a>
				</div>
			</div>

		</div>
		<!-- /.navbar-collapse -->
	</div>
	</nav>
	<div id="main" style="height: auto; width: 1000px; margin-top: 50px;">
		<div id="main-left">
			<div id="money-input">
				<div class="input-group">
					<span class="input-group-addon">￥</span> <input type="text"
						class="form-control" id="price" style="text-align: right" onkeyup="clearNoNum(this)">
				</div>
			</div>

			<div id="type-list">
				<ul id="selectList">
					<%
						QuickTypeService qts = new QuickTypeService();
																								out.print(qts.getUserTypeHtml(acc.getID()));
					%>
					<li><a id="add_button">添加</a></li>

				</ul>
			</div>
			<div id="add">
				<div id="simple_text">
					<textarea name="title" id="tally_title"
						class="s_text" style="background: transparent;" onclick="checkCommont()">备注，限20个汉字以内</textarea>
				</div>
				<div id="simple_btn">
					<input name="tally_submit" type="button" class="btn btn-primary btn-lg" value="记一笔"
						id="ok_button" onclick="add_one()">
				</div>
			</div>
			<div>
				<%
					DetailService ds=new DetailService();
									List<DetailEntity> lde=null;
									if(session.getAttribute("defaultWallet")!=null){
										 lde= ds.getListbyWalletID(Integer.parseInt(session.getAttribute("defaultWallet").toString()));
									}else
										{
										lde=new ArrayList();
										}
				%>

				<table class="table table-hover" style="color: black">
					<thead>
						<tr>
							<th>类型</th>
							<th>类别</th>
							<th>金额</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<%
							float sum=0;
							for(int i=0;i<lde.size();i++){
								sum+=lde.get(i).getDetailDir()*lde.get(i).getDetailPrice();
						%>
						<tr>
							<td><%=pts.PrintDir(lde.get(i).getDetailDir())%></td>
							<td><%=pts.printType(lde.get(i).getDetailType())%></td>
							<td><%=String.valueOf(lde.get(i).getDetailPrice())%></td>
							<td><%=lde.get(i).getDetailCommont()%></td>
						</tr>
						<%
							}
						%>
					
					<tfoot>
						<tr>
							<td>总计</td>
							<td></td>
							<td><%=sum %></td>
							<td></td>
						</tr>
					</tfoot>
					</tbody>
				</table>

			</div>

		</div>

	</div>
	<script type="text/javascript">
		$("#add_button")
				.click(
						function() {
							var html = $("#add_button").html();
							if (html == "添加") {
								$("#add_button")
										.html(
												"<input type='text' id='add_text' /><button id='add-zhi' onclick='addtype()'>添加支出</button><button id='add-shou' onclick='addshoutype()'>添加收入</button>");
							}
						});
	</script>
	<script>
	
		function checkCommont()
		{
			  var textObj = document.getElementById("tally_title");
		      if(textObj.value == "备注，限20个汉字以内"){
		           textObj.value = "";
		      }
		}
		function addtype() {
			var Type = $("#add_text").val();
			var UserID = $("#userID").val();
			if (Type == "") {
				alert("类别不能为空");
				return false;
			}

			$.ajax({
				type : "post",
				url : "AddTypeServlet",
				data : {
					type : $("#add_text").val(),
					userID : $("#userID").val(),
					dir:-1
				},
				dataType : "html",
				success : function(data) {
					var res = $(data).find("msg").text();
					if ("ok" == res) {
						window.location.reload();
					}
				}
			});
		}
		
		function addshoutype() {
			var Type = $("#add_text").val();
			var UserID = $("#userID").val();
			if (Type == "") {
				alert("类别不能为空");
				return false;
			}

			$.ajax({
				type : "post",
				url : "AddTypeServlet",
				data : {
					type : $("#add_text").val(),
					userID : $("#userID").val(),
					dir:1
				},
				dataType : "html",
				success : function(data) {
					var res = $(data).find("msg").text();
					if ("ok" == res) {
						window.location.reload();
					}
				}
			});
		}
		
		

		function add_one() {
			var walletID = $("#walletID").val();
			var TypeID = $(".cur .tt").attr("cid");
			var dir=$(".cur .tt").attr("dir");
			var price = $("#price").val();
			if (price == "") {
				$("#price").trigger("focus");
				return false;
			}
			if (typeof TypeID === "undefined") {
				alert("请选择类别");
				return false;
			}
			
			if (typeof dir === "undefined") {
				alert("错误");
				return false;
			}
			
			 var commont = document.getElementById("tally_title");
			 
		      if(commont.value == "备注，限20个汉字以内"){
		    	  commont.value = "";
		      }
			$.ajax({
				type : "post",//请求方式
				url : "AddDetailServlet",//发送请求地址
				data : {//发送给数据库的数据
					DetailType : TypeID,
					WalletID : walletID,
					DetailDir : dir,
					DetailPrice : price,
					comment : commont.value
				},
				dataType : "html",
				//请求成功后的回调函数有两个参数
				success : function(data) {
					var res = $(data).find("msg").text();
					if ("ok" == res) {
						window.location.reload();
					}

				}
			});

		}
		$("#selectList li").click(function() {
			$("#selectList li").removeClass("cur");
			$(this).addClass("cur");
		});

		$("#walletID").change(function() {
			//alert($("#walletID option:selected").val());
			$.ajax({
				type : "post",//请求方式
				url : "ChangeWalletServlet",//发送请求地址
				data : {//发送给数据库的数据
					wallet : id=$("#walletID option:selected").val()
				},
				dataType : "html",
				//请求成功后的回调函数有两个参数
				success : function(data) {
					var res = $(data).find("msg").text();
					if ("ok" == res) {
						window.location.reload();
					}

				}
			});
			
		});
		
		function clearNoNum(obj)
		{
		   obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
		   obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
		   obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
		   obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		}
		
		$(".xx").click(function(){
			$.ajax({
				type : "post",//请求方式
				url : "DeleteDetailServlet",//发送请求地址
				data : {//发送给数据库的数据
					id:$(this).attr('uid')
				},
				dataType : "html",
				//请求成功后的回调函数有两个参数
				success : function(data) {
					var res = $(data).find("msg").text();
					if ("ok" == res) {
						window.location.reload();
					}

				}
			});
		});
	</script>


</body>
</html>