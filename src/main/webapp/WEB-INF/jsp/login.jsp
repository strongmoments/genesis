<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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


<form role="form" id="loginForm" action="/genesis/j_spring_security_check" method="POST" >
				 
<div style="margin-top: -20px;">
<c:if test="${param.error ne null}">
				<div class="alert-danger" style="margin-bottom: 23px;" >Invalid username or password.</div>
			</c:if>
			<c:if test="${param.logout ne null}">
				<div class="alert-normal" style="margin-bottom: 23px;" >You have been logged out.</div>
</c:if>
</div>				 
<div class="input-group">
<span class="input-group-addon"><i class="fa fa-user"></i></span>
<input class="form-control" type="text" placeholder="Email address" name="j_username" >
</div>
<div class="input-group">
<span class="input-group-addon"><i class="fa fa-key"></i></span>
<input type="password" class="form-control" placeholder="Password" name="j_password">
</div>
<div id="remember-me-wrapper">
<div class="row">
<div class="col-xs-6">
<div class="checkbox-nice">
<input type="checkbox" id="remember-me" checked="checked"/>
<label for="remember-me">
Remember me
</label>
</div>
</div>
<a href="/genesis/forgot-password" id="login-forget-link" class="col-xs-6">
Forgot password?
</a>
</div>
</div>
<div class="row">
<div class="col-xs-12">
<button type="submit"  class="btn btn-success col-xs-12" onclick="fubmitLogin()">Login</button>
</div>
</div>

<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
</form>
	<%-- <form action="/genesis/j_spring_security_check" method="POST">

		<div class="lc-block">
			<div>
			<input type="text" class="form-control" placeholder="UserId" name="j_username" autofocus>
				
			</div>
			<div>
			<input type="password" class="form-control" placeholder="Password" name="j_password" >
                <input type="hidden" class="form-control"  value="true" name="fromLogin" >
			</div>
			<div>
				<input type="submit" value="Sign In" class="button red small" />
				<input type="button" value="Check for js" class="button red small" onclick="login.show();" />
			</div>
			<c:if test="${param.error ne null}">
				<div class="alert-danger">Invalid username and password.</div>
			</c:if>
			<c:if test="${param.logout ne null}">
				<div class="alert-normal">You have been logged out.</div>
			</c:if>
		</div>
		
	</form> --%>
	
	
	

	
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
