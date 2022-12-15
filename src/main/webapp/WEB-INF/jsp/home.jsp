<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ page session="false" %>


<%
HttpSession sessionsa = request.getSession(false);
String LoggedUserId = (String) sessionsa.getAttribute("userId");
long LoggedSno = (long) sessionsa.getAttribute("sno");

%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>GENESIS</title>
	<script type="text/javascript">
	var LoggedUserIdVar = "<%out.print(LoggedUserId);%>";
	var LoggedSnoVar = "<%out.print(LoggedSno);%>";
	</script>
	
	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css" />

	<script src="js/demo-rtl.js"></script>


	<link rel="stylesheet" type="text/css" href="css/libs/font-awesome.css" />
	<link rel="stylesheet" type="text/css" href="css/libs/nanoscroller.css" />
	
	<link rel="stylesheet" type="text/css" href="css/mystyle2.css" />

	<link rel="stylesheet" type="text/css" href="css/compiled/theme_styles.css" />
	<link rel="stylesheet" type="text/css" href="css/print.min.css" />

	<link rel="stylesheet" href="css/libs/daterangepicker.css" type="text/css" />
	<link rel="stylesheet" href="css/libs/jquery-jvectormap-1.2.2.css" type="text/css" />
	<link rel="stylesheet" href="css/libs/weather-icons.css" type="text/css" />
	 <link rel="stylesheet" href="css/libs/bootstrap-timepicker.css" type="text/css" />
	 <link rel="stylesheet" type="text/css" href="css/timePicker.css">
	<link rel="stylesheet" href="css/libs/morris.css" type="text/css" />
	<link type="image/x-icon" href="favicon.png" rel="shortcut icon" />
	
	
	<script src="js/print.min.js"></script>


	<link href='//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300' rel='stylesheet' type='text/css'>
	<!--[if lt IE 9]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.min.js"></script>
	<![endif]-->


</head>

<body>
	<%-- <span onload="main.sessionData("<%LoggedUserId %>");"></span> --%>
	<div id="theme-wrapper">
		<header class="navbar" id="header-navbar">
			<div class="container">
				<a href="#" id="logo" class="navbar-brand">
					<img src="img/samples/logo7.png" alt="" class="normal-logo logo-white" />
				</a>
				<div class="clearfix">
					<button class="navbar-toggle" data-target=".navbar-ex1-collapse" data-toggle="collapse" type="button">
						<span class="sr-only">Toggle navigation</span>
						<span class="fa fa-bars"></span>
					</button>
					<div class="nav-no-collapse navbar-left pull-left hidden-sm hidden-xs">
						<ul class="nav navbar-nav pull-left">
							<li>
								<a class="btn" id="make-small-nav">
									<i class="fa fa-bars"></i>
								</a>
							</li>
						</ul>
					</div>
					<div class="nav-no-collapse pull-right" id="header-nav">
						<ul class="nav navbar-nav pull-right">
							<li class="mobile-search">
								<a class="btn">
									<i class="fa fa-search"></i>
								</a>
								<div class="drowdown-search">
									<form role="search">
										<div class="form-group">
											<input type="text" class="form-control" placeholder="Search..." >
											<i class="fa fa-search nav-search-icon"></i>
										</div>
									</form>
								</div>
							</li>

							<li class="dropdown profile-dropdown">
								<a href=""  class="dropdown-toggle" data-toggle="dropdown">
									<img src="/genesis/users/displayImage" alt="" />
									<span class="hidden-xs" id="user"><%= LoggedUserId %></span>
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li>
										<a href="#" onclick="setting.show();" data-toggle="modal" data-target="#dynamicModel1" >
											<i class="fa fa-user"></i>Profile</a>
									</li>
									<!-- <li>
										<a href="#" data-toggle="modal" data-target="#dynamicModel1" >
											<i class="fa fa-cog"></i>Settings</a>
									</li> -->
									<li>
										<a href="/genesis/logout" >
											<i class="fa fa-power-off"></i>Logout</a>
									</li>
								</ul>
							</li> 

						</ul>
					</div>
				</div>
			</div>
			<div class="container" style="background: #2ecc71;height: 5px;"></div>
		</header>
		<div id="page-wrapper" class="container">
			<div class="row">
				<div id="nav-col">
					<section id="col-left" class="col-left-nano">
						<div id="col-left-inner" class="col-left-nano-content">
							<div id="user-left-box" class="clearfix hidden-sm hidden-xs dropdown profile2-dropdown">
								<a href="" onclick="setting.getProfilePic();" class="dropdown-toggle" data-toggle="dropdown">
									<img src="/genesis/users/displayImage" alt="image" />
									<b class="caret"></b>
								</a>
								<div class="user-box">
									<span class="name">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" id="user1">
											<%= LoggedUserId %>
										</a>

									</span>
									<span class="status">
										<i class="fa fa-circle"></i> Online
									</span>
								</div>
							</div>
							<div class="collapse navbar-collapse navbar-ex1-collapse" id="sidebar-nav">
								<ul class="nav nav-pills nav-stacked">
									<li class="nav-header nav-header-first hidden-sm hidden-xs">
										Navigation
									</li>
									<li class="active">
										<a href="">
											<i class="fa fa-dashboard"></i>
											<span>Dashboard</span>
											<span class="label label-primary label-circle pull-right"></span>
										</a>
									</li>
									<li onclick="client.show();" style="cursor:pointer">
										<a id="client">
											<i class="fa fa-group"></i>
											<span>Client</span>
											<span class="label label-primary label-circle pull-right"></span>
										</a>
									</li>
									<li onclick="tally.show();" style="cursor:pointer">
										<a>
											<i class="fa fa-table"></i>
											<span>Tally Sheet</span>
										</a>
									</li>
									<li onclick="wso1.show();" style="cursor:pointer">
										<a>
											<i class="fa fa-bar-chart-o"></i>
											<span>WSO Entry</span>
										</a>
									</li>
									<li onclick="delivery.show();" style="cursor:pointer">
										<a >
											<i class="fa fa-th-large"></i>
											<span>Delivery List</span>

										</a>
									</li>
									<li onclick="othercharge.show();" style="cursor:pointer">
										<a>
											<i class="fa fa-copy"></i>
											<span>Other Charges</span>
										</a>
									</li>
									<li onclick="billing.show();" style="cursor:pointer">
										<a>
											<i class="fa fa-file-text-o"></i>
											<span>Billing</span>
										</a>
									</li>
									<li>
										<a href="#" class="dropdown-toggle">
											<i class="fa fa-area-chart"></i>
											<span>Reports</span>
											<i class="fa fa-angle-right drop-icon"></i>
										</a>
										<ul class="submenu" style="display: none;">
												<li onclick="wlreport.show();" style="cursor:pointer" >
													<a>Wsolot Report</a>
												</li>
												<li onclick="dlreport.show();" style="cursor:pointer" >
													<a>DeliveryList Report</a>
												</li>
												<li onclick="treport.show();" style="cursor:pointer" >
													<a>TallySheet Report</a>
												</li>
											</ul>
									</li>
									<li onclick="user.show();" style="cursor:pointer">
										<a>
											<i class="fa fa-user"></i>
											<span>Admin</span>
										</a>
									</li>
									   <li>
										<a href="#footer-bar" class="dropdown-toggle">
											<i class="fa fa-desktop"></i>
											<span>Other Reports</span>
											<i class="fa fa-angle-right drop-icon"></i>
										</a>
										<ul class="submenu" style="display: none;">
												<li onclick="finreport.show();" style="cursor:pointer" >
													<a href="#header-navbar">Finance Report</a>
												</li>
												<li onclick="dateInventory.show();" style="cursor:pointer" >
													<a href="#header-navbar">Date Inventory</a>
												</li>
												<li onclick="outbalreport.show();" style="cursor:pointer" >
													<a href="#header-navbar">Outstanding Balance</a>
												</li>
												<li onclick="soareport.show();" style="cursor:pointer" >
													<a href="#header-navbar">Statement Of Account</a>
												</li>
												 <li onclick="wsoenreport.show();" style="cursor:pointer" >
													<a href="#header-navbar">WSO Enquiry</a>
												</li>
												 <li onclick="outrecreport.show();" style="cursor:pointer" >
													<a href="#header-navbar">Outgoing Records</a>
												</li>
												<li onclick="payreport.show();" style="cursor:pointer" >
													<a href="#header-navbar">Payment Records</a>
												</li>
												<li onclick="nextbilling.show();" style="cursor:pointer" >
													<a href="#header-navbar">Projected Billing</a>
												</li>
												<li onclick="paymentbilling.show();" style="cursor:pointer" >
													<a href="#header-navbar">Monthly Payment</a>
												</li>
											</ul>
									</li>
									<li>
										<a href="#footer-bar" class="dropdown-toggle">
											<i class="fa fa-desktop"></i>
											<span>New Reports</span>
											<i class="fa fa-angle-right drop-icon"></i>
										</a>
										<ul class="submenu" style="display: none;">
												<li onclick="invoicelist.show();" style="cursor:pointer" >
													<a href="#header-navbar">Invoice List</a>
												</li>
												<li onclick="lotreport.show();" style="cursor:pointer" >
													<a href="#header-navbar">Stock Balance</a>
												</li>
												<li onclick="customerlist.show();" style="cursor:pointer" >
													<a href="#header-navbar">Customer List</a>
												</li>
												<li onclick="wsobilling.show();" style="cursor:pointer" >
													<a href="#header-navbar">WSO Billing Report</a>
												</li>
											</ul>
									</li>

								</ul>
							</div>
						</div>
					</section>
					<div id="nav-col-submenu"></div>
				</div>
				<div id="content-wrapper">
					<div class="row">
						<div class="col-lg-12">
							<div class="row">
								<div class="col-lg-12">
									<div id="content-header" class="clearfix">
										<div class="pull-left">
											<h1>Dashboard</h1>
										</div>

									</div>
								</div>
							</div>
							<div class="row">
							<div class="col-md-3 col-sm-6 col-xs-12">
									<div class="main-box small-graph-box purple-bg">
										<div class="box-button">
											<a href="#" class="box-close tooltips" data-toggle="tooltip" title="Close Panel">
												<i class="fa fa-times"></i>
											</a>
										</div>
										<span class="value" id="totalClient"></span>
										<span class="headline">Clients</span>
										<div class="progress">
											<div style="width: 77%;" aria-valuemax="100" aria-valuemin="0" aria-valuenow="77" role="progressbar" class="progress-bar">
												<span class="sr-only">77% Complete</span>
											</div>
										</div>
										<span class="subinfo">
											<i class="fa fa-caret-down"></i> 22% More than last Month
										</span>
									</div>
								</div>
								<div class="col-md-3 col-sm-6 col-xs-12">
									<div class="main-box small-graph-box emerald-bg">
										<div class="box-button">
											<a href="#" class="box-close tooltips" data-toggle="tooltip" title="Close Panel">
												<i class="fa fa-times"></i>
											</a>
										</div>
										<span class="value" id="totalTally"></span>
										<span class="headline">TallySheets</span>
										<div class="progress">
											<div style="width: 84%;" aria-valuemax="100" aria-valuemin="0" aria-valuenow="84" role="progressbar" class="progress-bar">
												<span class="sr-only">84% Complete</span>
											</div>
										</div>
										<span class="subinfo">
											<i class="fa fa-caret-down"></i> 22% less than last Month
										</span>
									</div>
								</div>
								<div class="col-md-3 col-sm-6 col-xs-12">
									<div class="main-box small-graph-box blue-bg">
										<div class="box-button">
											<a href="#" class="box-close tooltips" data-toggle="tooltip" title="Close Panel">
												<i class="fa fa-times"></i>
											</a>
										</div>
										<span class="value" id="totalWso"></span>
										<span class="headline">WSO</span>
										<div class="progress">
											<div style="width: 42%;" aria-valuemax="100" aria-valuemin="0" aria-valuenow="42" role="progressbar" class="progress-bar">
												<span class="sr-only">42% Complete</span>
											</div>
										</div>
										<span class="subinfo">
											<i class="fa fa-caret-up"></i> 15% higher than last Month
										</span>
									</div>
								</div>
								<div class="col-md-3 col-sm-6 col-xs-12">
									<div class="main-box small-graph-box red-bg">
										<div class="box-button">
											<a href="#" class="box-close tooltips" data-toggle="tooltip" title="Close Panel">
												<i class="fa fa-times"></i>
											</a>
										</div>
										<span class="value" id="totalLot"></span>
										<span class="headline">Lots</span>
										<div class="progress">
											<div style="width: 60%;" aria-valuemax="100" aria-valuemin="0" aria-valuenow="60" role="progressbar" class="progress-bar">
												<span class="sr-only">60% Complete</span>
											</div>
										</div>
										<span class="subinfo">
											<i class="fa fa-caret-up"></i> 10% higher than last Month
										</span>
									</div>
								</div>
								
								
							</div>
							<div class="row">
								<div class="col-lg-12">
									<div class="main-box clearfix">
										<header class="main-box-header clearfix">
											<h2 class="pull-left">Client Details</h2>
											<div class="filter-block pull-right">
												<div class="form-group pull-left">
													<input type="text" class="form-control" placeholder="Search Using clientTitle..." id="searchClientTitle" onkeyup="home.clientSearch();">
													<i class="fa fa-search search-icon"></i>
												</div>
											</div>
										</header>
										<div class="main-box-body clearfix">
											<div class="table-responsive clearfix">
												<table class="table table-hover">
													<thead>
														<tr>
															<th>
																<span>Action</span>
															</th>
															<th style="width: 200px;">
																<span>Client Title</span>
															</th>
															<th style="width: 200px;">
																<span>Client Address</span>
															</th>
															<th style="width: 200px;">
																<span>Client City</span>
															</th>
															<th style="width: 200px;">
																<span>Client State</span>
															</th>
															<th style="width: 200px;">
																<span>Country</span>
															</th>
															<th style="width: 200px;">
																<span>Mobile Number</span>
															</th>
														</tr>
													</thead>
													<tbody id="clientDetails" >
														
													</tbody>
												</table>
											</div>
										</div>
										<div class="modal fade" id="dynamicModel" role="dialog" tabindex="-1">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header" style="background: #7FC8BA; color: #fff;">
														<h3 id="dmhId">ADD NEW CUSTOMER</h3>
													</div>
													<div class="modal-body" id="modelBodyId">
														<div class="row">
															<div class="col-lg-6">
																<div class="form-group">
																	<label>First Name</label>
																	<input type="text" class="form-control" placeholder="First Name" id="firstName">
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Middle Name</label>
																	<input type="text" class="form-control" placeholder="Middle Name" id="middleName">
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Last Name</label>
																	<input type="text" class="form-control" placeholder="Last Name" id="lastName">
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Client Title</label>
																	<input type="text" class="form-control" placeholder="Client Title" id="clientTitle">
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Address 1</label>
																	<textarea rows="2" cols="50" class="form-control" placeholder="Address 1" id="Address1"></textarea>
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Address 2</label>
																	<textarea class="form-control" rows="2" cols="50" placeholder="Address 2" id="Address2"></textarea>
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-6">
																<div class="form-group">
																	<label>City</label>
																	<input type="text" class="form-control" placeholder="City" id="city">
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>State</label>
																	<input type="text" class="form-control" id="state" placeholder="State">
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Country</label>
																	<input type="text" class="form-control" id="country" placeholder="Country">
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>zip Code</label>
																	<input type="text" class="form-control" id="zipCode" placeholder="Zip Code">
																</div>
															</div>
														</div>

														<div class="row">
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Phone No</label>
																	<input type="text" class="form-control" id="phoneNo" placeholder="Phone No">
																</div>
															</div>
															<div class="col-lg-6">
																<div class="form-group">
																	<label>Mobile No</label>
																	<input type="text" class="form-control" id="mobileNo" placeholder="Mobile Mo">
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-5">
																<div class="form-group">
																	<label>Start Date</label>
																	<input type="date" class="form-control" id="startDate">
																</div>
															</div>
															<div class="col-lg-2"></div>
															<div class="col-lg-5">
																<div class="form-group">
																	<label>End Date</label>
																	<input type="date" class="form-control" id="endDate">
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-5">
																<div class="form-group">
																	<label>Monthly Rate</label>
																	<input type="number" class="form-control" id="monthlyRate">
																</div>
															</div>
														</div>
														<div class="row">
															<div class="col-lg-2">
																<button class="btn btn-success" id="saveClientInfo">Save</button>
															</div>
															<div class="col-lg-2">
																<button class="btn btn-warning" id="clearClientInfoField">Clear</button>
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
					<footer id="footer-bar" class="row">
						<p id="footer-copyright" class="col-xs-12">
							Powered by Sandrokottos From StrongMoments Pvt Ltd
						</p>
					</footer>
				</div>
			</div>
		</div>
	</div>
<div id="alertPoput">

</div>

	<script src="js/demo-skin-changer.js"></script>
	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/jquery.nanoscroller.min.js"></script>
	<script src="js/demo.js"></script>
	<script src="js/bootbox.min.js"></script>
	<script src="js/jquery.scrollTo.min.js"></script>
	<script src="js/jquery.slimscroll.min.js"></script>
	<script src="js/moment.min.js"></script>
	<script src="js/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="js/jquery-jvectormap-world-merc-en.js"></script>
	<script src="js/gdp-data.js"></script>
	<script src="js/flot/jquery.flot.min.js"></script>
	<script src="js/flot/jquery.flot.resize.min.js"></script>
	<script src="js/flot/jquery.flot.time.min.js"></script>
	<script src="js/flot/jquery.flot.threshold.js"></script>
	<script src="js/flot/jquery.flot.axislabels.js"></script>
	<script src="js/jquery.sparkline.min.js"></script>
	<script src="js/skycons.js"></script>
	<script src="js/bootstrap-timepicker.min.js"></script>
	 <script type="text/javascript" src="js/jquery-timepicker.js"></script>
	 
	

	<script src="js/raphael-min.js"></script>
	<script src="js/morris.js"></script>
	<script src="js/scripts.js"></script>
	<script src="js/pace.min.js"></script>
	
	<script src="js/print.min.js"></script>

	<script src="appjs/popupAlert.js"></script>
	<script src="appjs/login.js"></script>
	<script src="appjs/client.js"></script>
	<script src="appjs/common.js"></script>
	<script src="appjs/tallysheet.js"></script>
	<script src="appjs/wso.js"></script>
	<script src="appjs/deliverylist.js"></script>
	<script src="appjs/othercharge.js"></script>
	<script src="appjs/billing.js"></script>
	<script src="appjs/wsoreport.js"></script>
	<script src="appjs/deliverylistreport.js"></script>
	<script src="appjs/tallysheetreport.js"></script>
	<script src="appjs/wsolotreport.js"></script>
	<script src="appjs/home.js"></script>
	<script src="appjs/user.js"></script>
	<script src="appjs/setting.js"></script>
	<script src="appjs/payment.js"></script>
	<script src="appjs/finance.js"></script>
	<script src="appjs/outgoingRecords.js"></script>
	<script src="appjs/outstandingbill.js"></script>
	<script src="appjs/dateInventory.js"></script>
	<script src="appjs/wsoenquiry.js"></script>
	<script src="appjs/soaReport.js"></script>
	<script src="appjs/nextbilling.js"></script>
	<script src="appjs/paymentbilling.js"></script>
	<script src="appjs/main.js"></script>
	<script src="appjs/invoicelist.js"></script>
	<script src="appjs/lotreport.js"></script>
	<script src="appjs/customerlist.js"></script>
	<script src="appjs/wsobilling.js"></script>
	<script src="js/jquery-printme.js"></script>
	<script src="js/table-pagination.js"></script>

	
	
	<script>
		//Slide code start
		$(document).ready(function(){
  // Add smooth scrolling to all links
  $("a").on('click', function(event) {

    // Make sure this.hash has a value before overriding default behavior
    if (this.hash !== "") {
      // Prevent default anchor click behavior
      event.preventDefault();

      // Store hash
      var hash = this.hash;

      // Using jQuery's animate() method to add smooth page scroll
      // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
      $('html, body').animate({
        scrollTop: $(hash).offset().top
      }, 800, function(){
   
        // Add hash (#) to URL when done scrolling (default click behavior)
        window.location.hash = hash;
      });
    } // End if
  });
});
// slide code end	
	

	</script>
</body>

</html>