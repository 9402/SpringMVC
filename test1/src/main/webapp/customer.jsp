<%@ page language="java" import="java.util.*,com.hand.bean.*"
	pageEncoding="utf-8"%>
<%-- <%@taglib uri="/struts-tags" prefix="s"%> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Mypage</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/public.css" rel="stylesheet">
<link href="css/list.css" rel="stylesheet">
<link href="css/buttons.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
.pagination a {
	color: #333;
	text-decoration: none;
}

.pagination {
	margin-top: 10%;
	line-height: 30px;
	text-align: center;
	font-weight: bold;
}

.pagination a {
	margin: 0 3px;
	display: inline-block;
	padding: 0 10px;
	border: 1px solid #CCC;
}

.pagination a:hover {
	background: #F5F5F5;
}

.pagination a.on {
	background: #3BA2E3;
	border-color: #3BA2E3;
	color: #FFF;
}

.pagination a.page_turn:hover {
	background: #3BA2E3;
	border-color: #3BA2E3;
	color: #FFF;
	box-shadow: 1px 1px 3px #CCC;
}

.pagination input {
	width: 30px;
	height: 28px;
	line-height: 28px;
	margin-right: 8px;
	padding-left: 5px;
	border: 1px solid #CCC;
	vertical-align: top;
}

.pagination input:hover {
	border-color: #3BA2E3;
}
</style>
</head>

<script type="text/javascript">
	$(function() {
		$.ajax({
					type : "post",
					url : "/test1/hand/toselectAllAddressAjax",
					dataType : "json",
					success : function(addressList) {
						//alert(addressList);
						for ( var indexs in addressList) {
							var src = "<option value='"+addressList[indexs].address_id+"''>"
									+ addressList[indexs].address + "</option>"
							//对地址的下拉框进行对应的追加操作
							$("#select_id").append(src);
						}
					},
					error : function() {
						alert("ajax++出错啦！！ ");
					}
				});
	});
</script>


<script type="text/javascript">
	var pages = 1;
	var totalPage = 1;
	//page为当前页
	function ajaxpage(page) {
		//当使用此部分进行获取数据，此时需要在对象对应的映射文件中关闭延迟加载
		pages = page;
		$.ajax({
					type : "post",
					url : "/test1/hand/toajaxPaget",
					dataType : "json",
					data : 'page=' + page,
					success : function(data) {
						totalPage = data.totalPage;
						//dhn-pagebuttons：分页的id
						document.getElementById("dhn-pagebuttons").innerHTML = Pagehtml();
						//当页面的表格中除了第一行，其余全部移除,其中tr代表行数
						$('#tableCustomer tr:not(:first)').remove();
						//进行循环，indexs是下标
						for ( var indexs in data.customerList) {
							var first_name = data.customerList[indexs].first_name;
							var last_name = data.customerList[indexs].last_name;
							var address = data.customerList[indexs].address_id.address;
							var email = data.customerList[indexs].email;
							var customer_id = data.customerList[indexs].customer_id;
							var last_update_int = data.customerList[indexs].last_update;
							//转换成时间格式
							//alert(new Date(parseInt(last_update_int)));
							var last_update = new Date(
									parseInt(last_update_int)).toLocaleString()
									.replace(/:\d{1,2}$/, ' ');
							var row = $("<tr><td><a  href=\"javascript:void(0);\" onclick=\"deletec("
									+ customer_id
									+ ")\">删除</a>|<a data-toggle=\"modal\" data-target=\"#myModal\" href=\"\"onclick=\"customerid("
									+ customer_id
									+ ",'"
									+ first_name
									+ "','"
									+ last_name
									+ "','"
									+ email
									+ "'"
									+ ")\">编辑</a></td>"
									+ "<td>"
									+ first_name
									+ "</td><td>"
									+ last_name
									+ "</td><td>"
									+ address
									+ "</td><td>"
									+ email
									+ "</td><td>"
									+ customer_id
									+ "</td><td>"
									+ last_update + "</td></tr>");
							//每行所有数据加载完成后，在当前行后追加这一行
							$("#tableCustomer").append(row)
						}
					},
					//用于测试获取数据是否成功
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(XMLHttpRequest.responseTex); //////////////////看输出什么内容，内容就是错误
						alert(XMLHttpRequest.status); //弹出406
						alert(XMLHttpRequest.readyState); //弹出4
						alert(textStatus); //弹出 error
					}

				});

	}
	//分页中指定跳转页数的函数,进行数据校验
	function getInput() {
		var _page = document.getElementById("page_text").value
		var r = /^[0-9]*[1-9][0-9]*$/; //正整数 
		if (_page != "" && r.test(_page)) {
			if (totalPage < _page) {
				_page = totalPage
			}
			ajaxpage(_page);
		} else {
			alert("input false");
		}
	}
	//分页代码
	function Pagehtml() {
		var PageStr = "";
		var Page = parseInt(pages);
		var MaxPage = parseInt(totalPage);
		var xPage = Page - 2, dPage = Page + 2;
		if (xPage < 1) {
			xPage = 1
			dPage = 5
		}
		if (dPage > MaxPage) {
			dPage = MaxPage;
			xPage = (dPage - 4);
		}

		if (xPage < 1) {
			xPage = 1
		}
		if (Page == 1) {
			PageStr += "<a class='page_turn'>上一页</a>"
		} else {
			PageStr += "<a class='page_turn' style='cursor:pointer' onclick='ajaxpage("
					+ (Page - 1) + ")'>上一页</a>";
		}
		if (xPage > 1) {
			PageStr += "<a style='cursor:pointer' onclick='ajaxpage(1)'>1</a>"
		}
		if (xPage > 2) {
			PageStr += "<span>...</span>"
		}
		for (var j = xPage; j <= dPage; j++) {
			PageStr += (Page == j) ? " <a class=\"on\">" + j + "</a>"
					: " <a  style='cursor:pointer' onclick='ajaxpage(" + j
							+ ")'>" + j + "</a>";
		}
		if (dPage < MaxPage - 1) {
			PageStr += " <span>...</span>"
		}
		if (dPage < MaxPage) {
			PageStr += " <a  style='cursor:pointer' onclick='ajaxpage("
					+ MaxPage + ")'>" + MaxPage + "</a>"
		}
		if (Page == MaxPage) {
			PageStr += "<a class='page_turn'>下一页</a>"
		} else {
			PageStr += "<a style='cursor:pointer' class='page_turn' onclick='ajaxpage("
					+ (Page + 1) + ")'>下一页</a>"
		}
		PageStr += ' 跳转到：<input id="page_text" type="text" name="page_text"><a id="page_btn"  class="page_turn" style="cursor:pointer" onclick="getInput()">GO</a>';
		return PageStr
	}

	//编辑中的四个参数对应上边编辑链接中的四个数据，顺序要保持一致
	function customerid(page1, page2, page3, page4) {
		//按照编辑页面(模态框的不同属性的id)中的顺序来进行获取修改前的原数据
		//在原有的数据上进行数据修改
		$("#customer_id").val(page1);
		$("#customer_first_name1").val(page2);
		$("#customer_last_name1").val(page3);
		$("#customer_email1").val(page4);
	}
	function deletec(id) {
		if (confirm("确定要删除吗?")) {
			$.ajax({
				type : "post",
				url : "/test1/hand/todel",
				dataType : "text",
				data : 'customer_id=' + id,
				success : function(data) {
					//如果data为success时，此时删除当前页所有的数据
					ajaxpage(pages);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.responseTex); //////////////////看输出什么内容，内容就是错误
					alert(XMLHttpRequest.status); //弹出406
					alert(XMLHttpRequest.readyState); //弹出4
					alert(textStatus); //弹出 error
				}
			});
		}
	}
</script>
<!-- onload：加载页面中表中的数据 -->
<body onload="ajaxpage(pages)">
	<div id="test"></div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 dhn-top">
				<a class="dhn-top-left" href="#"><span class="fui-list">${first_name}
				</span></a> <a class="dhn-top-right" href="/test1/hand/tologin"> <span
					class="fui-user"></span> <strong>Exit</strong>
				</a>
			</div>
		</div>

		<div class="row dhn-content">
			<div class="col-md-2 dhn-content-left ">
				<div class="dhn-left-inner">
					<button class=" btn btn-info leftbutton  dhn-active">
						<span class="fui-list-small-thumbnails lefticon"></span>customer管理<span
							class="fui-arrow-right righticon"></span>
					</button>
					<form action="/test1/hand/toAllAddress">
						<button class="btn btn-info leftbutton  dhn-active"
							style="width: 100%;">
							<span class="fui-list-small-thumbnails lefticon"></span>film设置<span
								class="fui-arrow-right righticon"></span>
						</button>
					</form>
				</div>

			</div>

			<div class="col-md-10 dhn-content-right">
				<div class="dhn-content-right-content">
					<div class="dhn-table">
						<div class="dhn-title">
							<h1>
								<strong>客户管理</strong>
							</h1>
						</div>
						<div class="dhn-form-border">
							<div class="panel panel-default">
								<div class="panel-body">
									客户列表 <a class="btn btn-info btn-default"
										href="/test1/hand/toAllAddress">新增</a>

								</div>
							</div>
							<div id="dhn-table" class="container-fluid"
								style="background-color: white">
								<table id="tableCustomer"
									class="table table-bordered dhn-mytable">
									<tr>
										<td>操作</td>
										<td>First Name</td>
										<td>Last Name</td>
										<td>Address</td>
										<td>Email</td>
										<td>CustomerID</td>
										<td>LastUpdate</td>
									</tr>
									<%-- <c:forEach items="${list}" var="m">
										<tr>
											<td><a href="javascript:deletec(${m.customer_id})"
												>删除</a>|<a
												data-toggle="modal" data-target="#myModal"
												href="javascript:void(0);" id="${m.customer_id}"
												onclick="customerid(${m.customer_id})">编辑</a></td>
											<td>${m.first_name}</td>
											<td>${m.last_name}</td>
											<td>${m.address_id.address}</td>
											<td>${m.email}</td>
											<td>${m.customer_id}</td>
											<td>${m.last_update}</td>
										</tr>
									</c:forEach> --%>
								</table>
								<!--margin-left:左外边距  -->
								<div id="dhn-pagebuttons" class="pagination "
									style="margin-left: 20%"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>


	<!-- 模态框（Modal）Edit -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<form action="/test1/hand/toupdate">
			<input type="hidden" name="customer_id" id="customer_id" value="" />
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
					</div>
					<div class="modal-body">

						<div id="dhn-table" class="container-fluid"
							　 style="background-color: white">
							<div class="input-group" style="margin-top: 10px">

								<span class="input-group-addon">first_name</span> <input
									type="text" name="first_name" class="form-control"
									id="customer_first_name1" placeholder="Twitterhandle"
									style="width: 200px">
							</div>
							<div class="input-group" style="margin-top: 10px">

								<span class="input-group-addon">last_name</span> <input
									type="text" name="last_name" class="form-control"
									id="customer_last_name1" placeholder="Twitterhandle"
									style="width: 200px">
							</div>

							<div class="input-group" style="margin-top: 10px">

								<span class="input-group-addon" style="width: 90px">email</span>
								<input type="text" name="email" class="form-control"
									id="customer_email1" placeholder="Twitterhandle"
									style="width: 200px">
							</div>

							<div class="input-group" style="margin-top: 10px">
								<span class="input-group-addon" style="width: 90px">address</span>
								<select name="address_id.address_id" id="select_id"
									class="selectpicker show-tick" data-size="5"
									style="width: 200px">

								</select>

							</div>
							<br>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="submit" class="btn btn-primary">提交更改</button>
					</div>
		</form>
	</div>
	</div>
	</div>
</body>
</html>
