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
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
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
				<li class="active" style="padding-left: 70px;"><a href="#">记一笔</a></li>
				<li style="padding-left: 70px;"><a href="#">报表</a></li>
				<li style="padding-left: 70px;"><a href="setting.jsp">设置</a></li>
			</ul>
			<input type="text" id="userID" style="display: none"
				value="<%=acc.getID()%>">
			<div id="header-bar">
				<div style="float: left">
					<%=acc.getNickName()%>
				</div>
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
					<option value="new">
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
	<div id="main" style="height: 1000px; width: 1000px; margin-top: 50px;">
		<div id="main-left"">
			<div id="date">
				<div class="btn-toolbar" role="toolbar">
					<div class="btn-group btn-group-lg">
						<button type="button" class="btn btn-default"><</button>
						<button type="button" class="btn btn-default">2</button>
						<button type="button" class="btn btn-default">3</button>
						<button type="button" class="btn btn-default">4</button>
						<button type="button" class="btn btn-default">5</button>
						<button type="button" class="btn btn-default">6</button>
						<button type="button" class="btn btn-default">7</button>
						<button type="button" class="btn btn-default">8</button>
						<button type="button" class="btn btn-default">9</button>
						<button type="button" class="btn btn-default">10</button>
						<button type="button" class="btn btn-default">></button>
					</div>
				</div>

			</div>

			<div id="money-input">
				<div class="input-group">
					<span class="input-group-addon">$</span> <input type="text"
						class="form-control" id="price" style="text-align: right">
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
					<textarea name="title" id="tally_title" init-tip="" cols="" rows=""
						class="s_text">备注，限20个汉字以内（可不填）</textarea>
				</div>
				<div id="simple_btn">
					<input name="tally_submit" type="button" class="s_btn" value="记一笔"
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

				<table class="table table-hover">
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
												"<input type='text' id='add_text' /><button onclick='addtype()'>确认添加</button>");
							}
						});
	</script>
	<script>
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
					userID : $("#userID").val()
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
			var TypeID = $(".cur a").attr("cid");
			var price = $("#price").val();
			if (price == "") {
				$("#price").trigger("focus");
				return false;
			}
			if (typeof TypeID === "undefined") {
				alert("请选择类别");
				return false;
			}
			$.ajax({
				type : "post",//请求方式
				url : "AddDetailServlet",//发送请求地址
				data : {//发送给数据库的数据
					DetailType : TypeID,
					WalletID : walletID,
					DetailDir : -1,
					DetailPrice : price,
					comment : $("#tally_title").val()
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
			alert("")
		});
	</script>


</body>
</html>