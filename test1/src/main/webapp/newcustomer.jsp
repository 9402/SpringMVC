<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'newcustomer.jsp' starting page</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/public.css" rel="stylesheet">
	<link href="css/list.css" rel="stylesheet">
	<link href="css/buttons.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   
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
                    <span class="fui-list-small-thumbnails lefticon"></span>Dashboard<span
                        class="fui-arrow-right righticon"></span>
                </button>
                <button class="btn btn-info leftbutton  dhn-active" style="width: 100%;">
                    <span class="fui-list-small-thumbnails lefticon"></span>应用设置<span
                        class="fui-arrow-right righticon"></span>
                </button>

            </div>

        </div>

        <div class="col-md-10 dhn-content-right">
            <div class="dhn-content-right-content">
                <div class="dhn-table">
                   <div class="dhn-title">
							<h1>
								<strong>创建customer</strong>
							</h1>
					</div>
                    <div class="dhn-form-border">
                        <div class="panel panel-default">
                            <div class="panel-body">
                          		     基本信息
                            </div>
                        </div>
                        <form action="/test1/hand/toadd">
                        <div id="dhn-table" class="container-fluid" style="background-color: white " >
                            <div class="input-group" style="margin-top: 10px">

                                <span class="input-group-addon">first_name</span>
                                <input type="text" name="first_name" class="form-control" placeholder="Twitterhandle" style="width: 200px">
                            </div>
                            <div class="input-group" style="margin-top: 10px">

                                <span class="input-group-addon"> last_name</span>
                                <input type="text" name="last_name" class="form-control" placeholder="Twitterhandle" style="width: 200px">
                            </div>

                            <div class="input-group" style="margin-top: 10px">

                                <span class="input-group-addon" style="width: 90px">email</span>
                                <input type="text" name="email" class="form-control" placeholder="Twitterhandle" style="width: 200px">
                            </div>

                            <div class="input-group" style="margin-top: 10px">
                                <span class="input-group-addon" style="width: 90px">address</span>
                                <select name="address_id.address_id" class="selectpicker show-tick" data-size="5" style="width: 200px">
                                    <c:forEach items="${list}" var="m">
                                    <option  value='${m.address_id}'>${m.address}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <br>
                            <button type="submit" class="btn btn-primary">新建</button>

                        </div>
							</form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
  </body>
</html>
