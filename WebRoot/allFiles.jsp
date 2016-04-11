<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ page import="java.lang.String"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.yisinian.mdfs.orm.Mdfsfile"%>
<%@page import="net.sf.json.JSON"%>


<!-- 获取系统的数据 -->
<%
	JSONArray allFiles= JSONArray.fromObject(request.getAttribute("fileAndBlocksMsg"));
 %>




<!DOCTYPE html>
<html>
<head>
    <title>Detail Admin - Tables showcase</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" >
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/icons.css" />

    <!-- libraries -->
    <link href="css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/tables.css" type="text/css" media="screen" />

    <!-- open sans font -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css' />

    <!--引入jQuery-->
    <script src="http://libs.useso.com/js/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <!--表单击展开js.css文件库 -->
    <link rel="stylesheet" href="css/bootstrap-table-expandable.css">
    <script src="js/bootstrap-table-expandable.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>

<!-- navbar -->
<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <button type="button" class="btn btn-navbar visible-phone" id="menu-toggler">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

        <a class="brand" href="index.html"><img src="img/logo.png" /></a>

        <ul class="nav pull-right">
            <li class="hidden-phone">
                <input class="search" type="text" />
            </li>
            <li class="notification-dropdown hidden-phone">
                <a href="#" class="trigger">
                    <i class="icon-warning-sign"></i>
                    <span class="count">8</span>
                </a>
                <div class="pop-dialog">
                    <div class="pointer right">
                        <div class="arrow"></div>
                        <div class="arrow_border"></div>
                    </div>
                    <div class="body">
                        <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                        <div class="notifications">
                            <h3>You have 6 new notifications</h3>
                            <a href="#" class="item">
                                <i class="icon-signin"></i> New user registration
                                <span class="time"><i class="icon-time"></i> 13 min.</span>
                            </a>
                            <a href="#" class="item">
                                <i class="icon-signin"></i> New user registration
                                <span class="time"><i class="icon-time"></i> 18 min.</span>
                            </a>
                            <a href="#" class="item">
                                <i class="icon-envelope-alt"></i> New message from Alejandra
                                <span class="time"><i class="icon-time"></i> 28 min.</span>
                            </a>
                            <a href="#" class="item">
                                <i class="icon-signin"></i> New user registration
                                <span class="time"><i class="icon-time"></i> 49 min.</span>
                            </a>
                            <a href="#" class="item">
                                <i class="icon-download-alt"></i> New order placed
                                <span class="time"><i class="icon-time"></i> 1 day.</span>
                            </a>
                            <div class="footer">
                                <a href="#" class="logout">View all notifications</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="notification-dropdown hidden-phone">
                <a href="#" class="trigger">
                    <i class="icon-envelope-alt"></i>
                </a>
                <div class="pop-dialog">
                    <div class="pointer right">
                        <div class="arrow"></div>
                        <div class="arrow_border"></div>
                    </div>
                    <div class="body">
                        <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                        <div class="messages">
                            <a href="#" class="item">
                                <img src="img/contact-img.png" class="display" />
                                <div class="name">Alejandra Galván</div>
                                <div class="msg">
                                    There are many variations of available, but the majority have suffered alterations.
                                </div>
                                <span class="time"><i class="icon-time"></i> 13 min.</span>
                            </a>
                            <a href="#" class="item">
                                <img src="img/contact-img2.png" class="display" />
                                <div class="name">Alejandra Galván</div>
                                <div class="msg">
                                    There are many variations of available, have suffered alterations.
                                </div>
                                <span class="time"><i class="icon-time"></i> 26 min.</span>
                            </a>
                            <a href="#" class="item last">
                                <img src="img/contact-img.png" class="display" />
                                <div class="name">Alejandra Galván</div>
                                <div class="msg">
                                    There are many variations of available, but the majority have suffered alterations.
                                </div>
                                <span class="time"><i class="icon-time"></i> 48 min.</span>
                            </a>
                            <div class="footer">
                                <a href="#" class="logout">View all messages</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle hidden-phone" data-toggle="dropdown">
                    Your account
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="personal-info.html">Personal info</a></li>
                    <li><a href="#">Account settings</a></li>
                    <li><a href="#">Billing</a></li>
                    <li><a href="#">Export your data</a></li>
                    <li><a href="#">Send feedback</a></li>
                </ul>
            </li>
            <li class="settings hidden-phone">
                <a href="personal-info.html" role="button">
                    <i class="icon-cog"></i>
                </a>
            </li>
            <li class="settings hidden-phone">
                <a href="signin.html" role="button">
                    <i class="icon-share-alt"></i>
                </a>
            </li>
        </ul>
    </div>
</div>
<!-- end navbar -->


<!-- sidebar -->
    <div id="sidebar-nav">
        <ul id="dashboard-menu">
            <li>                
                <a href="loginSystem.action">
                    <i class="icon-home"></i>
                    <span>Home</span>
                </a>
            </li>            
            <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-group"></i>
                    <span>Nodes</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="allNodes.action" class="active">Node list</a></li>
                    <li><a href="new-user.html">New user form</a></li>
                    <li><a href="user-profile.html">User profile</a></li>
                </ul>
            </li>
            <li>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-edit"></i>
                    <span>Task</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="submenu">
                    <li><a href="allTasks.action">Task Schedule</a></li>
                    <li><a href="newTask.action">New Task</a></li>
                </ul>
            </li>
            <li>
                <a href="calendar.html">
                    <i class="icon-calendar-empty"></i>
                    <span>Calendar</span>
                </a>
            </li>
            <li class="active">
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <a class="dropdown-toggle" href="#">
                    <i class="icon-th-large"></i>
                    <span>File</span>
                    <i class="icon-chevron-down"></i>
                </a>
                <ul class="active submenu">
                    <li><a href="allFilesAndBlocks.action">File List</a></li>
                    <li><a href="allFilesAndBlocks.action">Upload File</a></li>
                </ul>
            </li>
            <li>
                <a href="systemInfo.action">
                    <i class="icon-cog"></i>
                    <span>System Info</span>
                </a>
            </li>
        </ul>
    </div>
<!-- end sidebar -->
    



<!-- main container -->
<div class="content">

    <!-- settings changer -->
    <div class="skins-nav">
        <a href="#" class="skin first_nav selected">
            <span class="icon"></span><span class="text">Default</span>
        </a>
        <a href="#" class="skin second_nav" data-file="css/skins/dark.css">
            <span class="icon"></span><span class="text">Dark skin</span>
        </a>
    </div>

    <div class="container-fluid">
        <div id="pad-wrapper">

            <!-- products table-->
            <!-- the script for the toggle all checkboxes from header is located in js/theme.js -->
            <div class="table-wrapper products-table section">
                <div class="row-fluid head">
                    <div class="span12">
                        <h4>MDFS Files</h4>
                    </div>
                </div>

                <div class="row-fluid filter-block">
                    <div class="pull-right">
                        <div class="ui-select">
                            <select>
                                <option />Filter users
                                <option />Signed last 30 days
                                <option />Active users
                            </select>
                        </div>
                        <input type="text" class="search" />
                        <a class="btn-flat success new-product">+ Add product</a>
                    </div>
                </div>
                <div id="uploader" class="wu-example">
    			<!--用来存放文件信息-->
    				<div id="thelist" class="uploader-list"></div>
    				<div class="btns">
        				<h4>Upload Files to MDFS</h4>
                        <div class="btns">
        				    <form action="uploadFileToMDFS" method="post" enctype="multipart/form-data">
							<input type="file" name="upfile"/>
        					<input class="btn btn-default" type="submit" value="submit"/>
    					    </form>
                        </div>
    				</div>
				</div>
            </div>
            <!-- end products table -->
            <div class="table-wrapper table-wrapper products-table section">
                <table class="table table-hover table-expandable ">
                    <thead>
                    <tr>
                        <th>File Name</th>
                        <th>Size</th>
                        <th>Uploadtime</th>
                        <th>Blocks</th>
                        <th>File Download Time</th>
                        <th>File Process Time</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    <%
                    for(int i=0;i<allFiles.size();i++){
                    JSONObject aFile = JSONObject.fromObject(allFiles.get(i));
                     %>
                    
					<tr>
                         <td>
                            <div class="img">
                                 <img src="img/table-img.png" />
                            </div>
                                 <%=aFile.get("fileName") %>
                            </td>
                            <td class="description">
                             <%=aFile.get("fileSize") %> Byte
                            </td>
                            </td>
                            <td class="description">
                            <%=aFile.get("fileUploadTime") %>
                            </td>
                            <td class="description">
                             <%=aFile.get("fileBlockNumber") %>
                            </td>
                           	</td>
                            <td class="description">
                             <%=aFile.get("fileDownloadTime") %> ms
                            </td>
                            <td class="description">
                             <%=aFile.get("processTime") %> ms
                            </td>
                            <td class="description">
                            <%=aFile.get("status") %>
                            </td> 
                            <td class="description">
                             	<a href="pushFileToNode.action?fileId=<%=aFile.get("fileId") %>">Push</a>
                             	<a href="deleteFileInNode.action?fileId=<%=aFile.get("fileId") %>">Delete</a>
                             	<a href="getFileFromNode.action?fileId=<%=aFile.get("fileId") %>">Get</a>
                            </td>   
                    </tr>
                    
                    
                    <tr>
                        <td colspan="5">
							<div class="row-fluid">
								<table class="table table-hover table-bordered ">
									<thead>
										<tr>
											<th class="span3">
												Block Name
											</th>
											<th class="span3">
												<span class="line"></span>Size
											</th>
											<th class="span3">
												<span class="line"></span>Serial Number
											</th>
											<th class="span3">
												<span class="line"></span>Set time
											</th>
											<th class="span3">
												<span class="line"></span>Status
											</th>
											<th class="span3">
												<span class="line"></span>Location   NodeId
											</th>		
											<th class="span3">
												<span class="line"></span>Block Download Time
											</th>										
										</tr>
									</thead>
									<tbody>
										<!-- row -->
										
										<%
										JSONArray allBlocks = JSONArray.fromObject(aFile.get("allBlocks"));
										for(int j=0;j<allBlocks.size();j++){
										JSONObject aBlock = allBlocks.getJSONObject(j);
										 %>
										<tr>
											<td>
												<div class="img">
													<img src="img/table-img.png" />
												</div>
												<%=aBlock.get("blockName") %>
												</a>
											</td>
											<td class="description">
												<%=aBlock.get("blockSize") %> Byte
											</td>
											</td>
											<td class="description">
												<%=aBlock.get("fileSerialNumber") %>
											</td>
											<td class="description">
												<%=aBlock.get("blockSetTime") %>
											</td>
											<td class="description">
												<%=aBlock.get("state") %>
											</td>    
											<td class="description">
												<%=aBlock.get("location") %>
											</td> 
											<td class="description">
												<%=aBlock.get("blockDownloadTime") %> ms
											</td> 
										</tr>
										<%
										}
										 %>  
									</tbody>
								</table>
							</div>
                        </td>
                    </tr>
                    
                    <%
                    } 
                    %>
                    
                    </tbody>
                </table>
            </div>

        </div>



    </div>
</div>
<!-- scripts -->

</body>
</html>