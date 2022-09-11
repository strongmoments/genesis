<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>GENESIS</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="css/libs/font-awesome.css"/>
<link rel="stylesheet" type="text/css" href="css/libs/nanoscroller.css"/>
<link rel="stylesheet" type="text/css" href="css/compiled/theme_styles.css"/>

</head>


<body id="login-page-full" class="theme-blue" >
<div id="login-full-wrapper" style="background-image: url('img/samples/genesis.jpg') ;">
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<div id="login-box">
					<div id="login-box-holder">
						<div class="row">
							<div class="col-xs-12">
								<header id="login-header">
									<div id="login-logo">
									<img src="img/samples/logo7.png" alt="" class="normal-logo logo-white" />
									</div>
								</header>
								<div id="login-box-inner">



				 
<p style="color:green">${successMessage}</p>			 
<p style="color:red">${errorMessage}</p>	
<div class="row">
<div class="col-xs-12">
<a href="/genesis/login" id="login-link" class="col-xs-6" style="float:right">
Go to Login Page
</a>
</div>
</div>


	

	
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				</div>
			</div>
			<script  src="<c:url value="js/demo-skin-changer.js" />"></script>  
			<script  src="<c:url value="js/jquery.js" />"></script>
			<script  src="<c:url value="js/bootstrap.js" />" ></script>
			<script  src="<c:url value="js/jquery.nanoscroller.min.js" />" ></script>
			<script  src="<c:url value="js/demo.js" />" ></script>  
 			<script  src="<c:url value="js/scripts.js" />" ></script>
			<script src="<c:url value="appjs/login.js" />"></script>
			
			<!-- note all js should add before main js -->
			<script src="<c:url value="appjs/main.js" />"></script>
			
		
		</body>
</html>
