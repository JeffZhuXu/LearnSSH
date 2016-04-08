<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ page import="java.lang.String"%>
    <%@ page import="net.sf.json.JSONObject"%>


<!-- 获取系统的数据 -->
<%
	JSONObject system= JSONObject.fromObject(request.getAttribute("systemMsg"));
 %>

<!DOCTYPE html>
<html>
<head>
	<title>System Info</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet" />
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/elements.css" />
    <link rel="stylesheet" type="text/css" href="css/icons.css" />

    <!-- libraries -->
    <link rel="stylesheet" type="text/css" href="css/lib/font-awesome.css" />
    
    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/personal-info.css" type="text/css" media="screen" />

    <!-- open sans font -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css' />

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
<body>

    <!-- navbar -->
    <div class="navbar navbar-inverse">
        <div class="navbar-inner">
            <a class="brand" href="index.html"><img src="img/logo.png" /></a>
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="loginSystem.action">Home</a></li>
                    <li><a href="chart-showcase.html">Charts</a></li>
                    <li><a href="user-list.html">Users</a></li>
                    <li><a href="form-showcase.html">Task</a></li>
                    <li><a href="gallery.html">Gallery</a></li>
                    <li><a href="icons.html">Icons</a></li>
                    <li><a href="calendar.html">Calendar</a></li>
                    <li><a href="tables.html">Tables</a></li>
                    <li><a href="ui-elements.html">UI Elements</a></li>
                </ul>
            </div>
            <ul class="nav pull-right">
                <li class="hidden-phone">
                    <input class="search" type="text" />
                </li>
                <li class="settings">
                    <a href="personal-info.html" role="button">
                        <span class="navbar_icon"></span>
                    </a>
                </li>
                <li id="fat-menu" class="dropdown">
                    <a href="signin.html" role="button" class="logout">
                        <span class="navbar_icon"></span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <!-- end navbar -->

	<!-- main container .wide-content is used for this layout without sidebar :)  -->
    <div class="content wide-content">
        <div class="container-fluid">
            <div class="settings-wrapper" id="pad-wrapper">
                <!-- avatar column -->
                <div class="span3 avatar-box">
                    <div class="personal-image">
                        <img src="img/personal-info.png" class="avatar img-circle" />
                        <br>
                        <br>
                        <br>
                        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Upload file to MDFS</p>
                        <br>
                        <br>
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="file" />
                    </div>
                </div>

                <!-- edit form column -->
                <div class="span7 personal-info">
                    <div class="alert alert-info">
                        <i class="icon-lightbulb"></i>
                        This page shows the basic information about the MDFS system.
                        <br/> Including:<strong>System name,Introduction,Node number,Storage and so on</strong>
                    </div>
                    <h5 class="personal-title">System info</h5>
                    <form action="changeSystemInfo.action" method="post">
                        <div class="field-box">
                            <label>System name:</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("name") %>" />
                        </div>
                        <div class="field-box">
                            <label>Build time:</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("establishTimeString") %>" />
                        </div>
                        <div class="field-box">
                            <label>Introduction:</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("introductionString") %>" />
                        </div>
                        <div class="field-box">
                            <label>Total node number:</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("totalNodeNum") %>" />
                        </div>

                        <div class="field-box">
                            <label>Active nodes number:</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("liveNodeNum") %>" />
                        </div>
                        <div class="field-box">
                            <label>Positive nodes number:</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("sleepNodeNum") %>" />
                        </div>
                        <div class="field-box">
                            <label>Dead node number:</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("deadNodeNum") %>" />
                        </div>
                        <div class="field-box">
                            <label>Total storage(Bytes):</label>
                             <input class="span5 inline-input" type="text" value="<%=system.get("totalStorage") %>" />
                        </div>
                        <div class="field-box">
                             <label>Rest storage(Bytes):</label>
                            <input class="span5 inline-input" type="text" value="<%=system.get("restStorafe") %>" />
                        </div>
                        <div class="field-box">
                        <!--文件分块大小-->
                            <label>Block size(Chars)~:</label>
                            <input name="blockSize" class="span5 inline-input" type="text" value="<%=system.get("blockSize") %>" />
                         </div>
                        <div class="field-box">
                        <!--文件备份备份次数-->
                            <label>Backup times~:</label>
                            <input name="backupTimes" class="span5 inline-input" type="text" value="<%=system.get("backupTimes") %>" />
                        </div>
                        <div class="field-box">
                            <label>Net speed:</label>
                            <input class="span5 inline-input" type="text"  value="<%=system.get("netSpeed") %>" />
                        </div>
                        <div class="field-box">
                            <label>compress~:</label>
                            <input name="compress" class="span5 inline-input" type="text"  value="<%=system.get("compress") %>" />
                        </div>
                        <div class="field-box">
                            <label>adapt~:</label>
                            <input name="adapt" class="span5 inline-input" type="text"  value="<%=system.get("adapt") %>" />
                        </div>                        
                        <div class="field-box">
                            <label>                                        </label>
                            <button type="submit" class="btn-glow primary login"  href="systemInfo.action">Commit Update</button>
                            </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- end main container -->


	<!-- scripts -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/theme.js"></script>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>