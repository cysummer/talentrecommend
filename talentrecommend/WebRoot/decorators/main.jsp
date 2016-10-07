<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
   <title><decorator:title /></title>
   <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet">
   <link href="<%=path %>/css/datepicker3.css" rel="stylesheet">
   <link href="<%=path %>/css/styles.css" rel="stylesheet">
   <decorator:head />
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	    <div class="container-fluid">
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" ><span>管理员</span></a>
	            <ul class="user-menu">
	                <li class="dropdown pull-right">
	                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> User <span class="caret"></span></a>
	                    <ul class="dropdown-menu" role="menu">
	                        <li><a href="<%=path %>/admin/adminInfo.jsp"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
	                        <li><a ><span class="glyphicon glyphicon-cog"></span> Settings</a></li>
	                        <li><a ><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
	                    </ul>
	                </li>
	            </ul>
	        </div>
	    </div><!-- /.container-fluid -->
	</nav>


	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	    <form role="search">
	        <div class="form-group">
	            <input type="text" class="form-control" placeholder="导航栏" disabled>
	        </div>
	    </form>
	    <ul class="nav menu">
	        <li><a href="<%=path %>/admin/userChecked.jsp"><span class="glyphicon glyphicon-dashboard"></span> 用户审核列表</a></li>
	        <li><a href="<%=path %>/admin/userList.jsp"><span class="glyphicon glyphicon-th"></span> 普通用户列表</a></li>
	        <li><a href="<%=path %>/admin/demChecked.jsp"><span class="glyphicon glyphicon-stats"></span> 需求审核列表</a></li>
	        <li><a href="<%=path %>/admin/techChecked.jsp"><span class="glyphicon glyphicon-list-alt"></span> 技术审核列表</a></li>
	        <li><a href="<%=path %>/admin/postList.jsp"><span class="glyphicon glyphicon-pencil"></span> 帖子管理</a></li>
	        <li><a href="<%=path %>/admin/newsList.jsp"><span class="glyphicon glyphicon-book"></span> 新闻资讯管理</a></li>
	       	<li><a href="<%=path %>/admin/addNews.jsp"><span class="glyphicon glyphicon-info-sign"></span> 添加新闻资讯</a></li>
	        <li class="parent ">
	            <a href="#">
	                <span class="glyphicon glyphicon-list"></span> Dropdown <span data-toggle="collapse" href="#sub-item-1" class="icon pull-right"><em class="glyphicon glyphicon-s glyphicon-plus"></em></span>
	            </a>
	            <ul class="children collapse" id="sub-item-1">
	                <li>
	                    <a class="" href="#">
	                        <span class="glyphicon glyphicon-share-alt"></span> Sub Item 1
	                    </a>
	                </li>
	                <li>
	                    <a class="" href="#">
	                        <span class="glyphicon glyphicon-share-alt"></span> Sub Item 2
	                    </a>
	                </li>
	                <li>
	                    <a class="" href="#">
	                        <span class="glyphicon glyphicon-share-alt"></span> Sub Item 3
	                    </a>
	                </li>
	            </ul>
	        </li>
	        <li role="presentation" class="divider"></li>
	        <li><a href="login.html"><span class="glyphicon glyphicon-user"></span> Login Page</a></li>
	    </ul>
	    <div class="attribution">More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></div>
	</div><!--/.sidebar-->
	
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
	    <div class="row">
	        <ol class="breadcrumb">
	            <li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
	        </ol>
	    </div><!--/.row-->
	    
	    <decorator:body/>
	    
	    
	</div>	<!--/.main-->

<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/chart.min.js"></script>
<script src="js/chart-data.js"></script>
<script src="js/easypiechart.js"></script>
<script src="js/easypiechart-data.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
</body>
</html>
