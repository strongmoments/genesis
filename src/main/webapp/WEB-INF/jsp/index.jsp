<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<title>GENESIS</title>

	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css" />

	<script src="js/demo-rtl.js"></script>


	<link rel="stylesheet" type="text/css" href="css/libs/font-awesome.css" />
	<link rel="stylesheet" type="text/css" href="css/libs/nanoscroller.css" />

	<link rel="stylesheet" type="text/css" href="css/compiled/theme_styles.css" />

	<link rel="stylesheet" href="css/libs/daterangepicker.css" type="text/css" />
	<link rel="stylesheet" href="css/libs/jquery-jvectormap-1.2.2.css" type="text/css" />
	<link rel="stylesheet" href="css/libs/weather-icons.css" type="text/css" />
	<link rel="stylesheet" href="css/libs/morris.css" type="text/css" />
	<link type="image/x-icon" href="favicon.png" rel="shortcut icon" />

	<link href='//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300' rel='stylesheet' type='text/css'>
	<!--[if lt IE 9]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.min.js"></script>
	<![endif]-->


</head>

<body>
	<div id="theme-wrapper">
		<header class="navbar" id="header-navbar">
			<div class="container">
				<a href="index.html" id="logo" class="navbar-brand">
					<img src="img/samples/logo.png" alt="" class="normal-logo logo-white" />
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
											<input type="text" class="form-control" placeholder="Search...">
											<i class="fa fa-search nav-search-icon"></i>
										</div>
									</form>
								</div>
							</li>

							<li class="dropdown profile-dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">
									<img src="img/samples/admin.jpg" alt="" />
									<span class="hidden-xs" id="user">Aqib khan</span>
									<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li>
										<a href="user-profile.html">
											<i class="fa fa-user"></i>Profile</a>
									</li>
									<li>
										<a href="#">
											<i class="fa fa-cog"></i>Settings</a>
									</li>
									<li>
										<a href="logout">
											<i class="fa fa-power-off"></i>Logout</a>
									</li>
								</ul>
							</li>

						</ul>
					</div>
				</div>
			</div>
		</header>
		<div id="page-wrapper" class="container">
			<div class="row">
				<div id="nav-col">
					<section id="col-left" class="col-left-nano">
						<div id="col-left-inner" class="col-left-nano-content">
							<div id="user-left-box" class="clearfix hidden-sm hidden-xs dropdown profile2-dropdown">
								<img alt="" src="img/samples/admin.jpg" />
								<div class="user-box">
									<span class="name">
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" id="user1">
											Aqib Khan
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
									<li>
										<a href="index.html">
											<i class="fa fa-dashboard"></i>
											<span>Dashboard</span>
											<span class="label label-primary label-circle pull-right"></span>
										</a>
									</li>
									<li>
										<a href="#" id="client">
											<i class="fa fa-file-text-o"></i>
											<span>Client</span>
											<span class="label label-primary label-circle pull-right"></span>
										</a>
									</li>
									<li>
										<a href="tallySheet.html">
											<i class="fa fa-table"></i>
											<span>Tally Sheet</span>
										</a>
									</li>
									<li>
										<a href="wsoEntry.html">
											<i class="fa fa-bar-chart-o"></i>
											<span>WSO Entry</span>
										</a>
									</li>
									<li>
										<a href="deliveryList.html">
											<i class="fa fa-th-large"></i>
											<span>Delivery List</span>

										</a>
									</li>
									<li>
										<a href="otherCharges.html">
											<i class="fa fa-copy"></i>
											<span>Other Charges</span>
										</a>
									</li>
									<li>
										<a href="billing.html">
											<i class="fa fa-file-text-o"></i>
											<span>Billing</span>
										</a>
									</li>
									<li>
										<a href="#" class="dropdown-toggle">
											<i class="fa fa-desktop"></i>
											<span>Reports</span>
											<i class="fa fa-angle-right drop-icon"></i>
										</a>
										<ul class="submenu" style="display: none;">
												<li>
													<a href="wsoreport.html">Wso Report</a>
												</li>
												<li>
													<a href="billreport.html">Bill Report</a>
												</li>
												<li>
													<a href="deliveryListReport.html">DeliveryList Report</a>
												</li>
												<li>
													<a href="tallySheetreport.html">TallySheet Report</a>
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
									<div class="main-box small-graph-box emerald-bg">
										<div class="box-button">
											<a href="#" class="box-close tooltips" data-toggle="tooltip" title="Close Panel">
												<i class="fa fa-times"></i>
											</a>
										</div>
										<span class="value">923</span>
										<span class="headline">Tally Sheets</span>
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
										<span class="value">2,562</span>
										<span class="headline">Total WSO</span>
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
										<span class="value">69,600</span>
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
								<div class="col-md-3 col-sm-6 col-xs-12">
									<div class="main-box small-graph-box purple-bg">
										<div class="box-button">
											<a href="#" class="box-close tooltips" data-toggle="tooltip" title="Close Panel">
												<i class="fa fa-times"></i>
											</a>
										</div>
										<span class="value">600</span>
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
							</div>
							<div class="row">
								<div class="col-lg-8">
									<div class="main-box">
										<header class="main-box-header clearfix">
											<h2>Server Statistics</h2>
											<div class="toolbar">
												<div class="pull-left">
													<div class="btn-group">
														<a href="#" class="btn btn-default btn-xs">Daily</a>
														<a href="#" class="btn btn-default btn-xs active">Monthly</a>
														<a href="#" class="btn btn-default btn-xs">Yearly</a>
													</div>
												</div>
												<div class="pull-right">
													<div class="btn-group">
														<a aria-expanded="false" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
															Export
															<i class="fa fa-angle-down"></i>
														</a>
														<ul class="dropdown-menu pull-right" role="menu">
															<li>
																<a href="#">Export as PDF</a>
															</li>
															<li>
																<a href="#">Export as CSV</a>
															</li>
															<li>
																<a href="#">Export as PNG</a>
															</li>
															<li class="divider"></li>
															<li>
																<a href="#">Separated link</a>
															</li>
														</ul>
													</div>
													<a href="#" class="btn btn-primary btn-xs">
														<i class="fa fa-cog"></i>
													</a>
												</div>
											</div>
										</header>
										<div class="main-box-body clearfix">
											<div id="hero-area"></div>
										</div>
									</div>
								</div>

								<div class="col-lg-4">
									<div class="main-box feed">
										<header class="main-box-header clearfix">
											<h2 class="pull-left">Server status</h2>
										</header>
										<div class="main-box-body clearfix">
											<div id="graph-flot-realtime" style="height: 400px; padding: 0px; position: relative;"></div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12">
									<div class="main-box clearfix">
										<header class="main-box-header clearfix">
											<h2 class="pull-left">Last orders</h2>
											<div class="filter-block pull-right">
												<div class="form-group pull-left">
													<input type="text" class="form-control" placeholder="Search...">
													<i class="fa fa-search search-icon"></i>
												</div>
												<a href="#" class="btn btn-primary pull-right" data-toggle="modal" data-target="#dynamicModel">
													<i class="fa fa-eye fa-lg"></i> ADD CUSTOMER
												</a>
											</div>
										</header>
										<div class="main-box-body clearfix">
											<div class="table-responsive clearfix">
												<table class="table table-hover">
													<thead>
														<tr>
															<th>
																<span>Client Name</span>
															</th>
															<th>
																<span>Client Title</span>
															</th>
															<th>
																<span>Client Address</span>
															</th>
															<th>
																<span>Client City</span>
															</th>
															<th>
																<span>Country</span>
															</th>
															<th>
																<span>Mobile Number</span>
															</th>
															<th>
																<span>Edit</span>
															</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>Anurag</td>
															<td>Anu@123</td>
															<td>H.NO:23,sec-15</td>
															<td>Faridabad</td>
															<td>India</td>
															<td>9910304052</td>
															<td>
																<a href="#">
																	<i class="glyphicon glyphicon-pencil"></i>
																</a>
															</td>
														</tr>
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

	<script src="js/raphael-min.js"></script>
	<script src="js/morris.js"></script>
	<script src="js/scripts.js"></script>
	<script src="js/pace.min.js"></script>
	<script src="js/main/index.js"></script>
	<script src="js/main/newClient.js"></script>

	<script>
		$(document).ready(function () {
			//BOX BUTTON SHOW AND CLOSE
			jQuery('.small-graph-box').hover(function () {
				jQuery(this).find('.box-button').fadeIn('fast');
			}, function () {
				jQuery(this).find('.box-button').fadeOut('fast');
			});
			jQuery('.small-graph-box .box-close').click(function () {
				jQuery(this).closest('.small-graph-box').fadeOut(200);
				return false;
			});

			//CHARTS
			function gd(year, day, month) {
				return new Date(year, month - 1, day).getTime();
			}

			graphArea2 = Morris.Area({
				element: 'hero-area',
				padding: 10,
				behaveLikeLine: true,
				gridEnabled: false,
				gridLineColor: '#dddddd',
				axes: true,
				resize: true,
				smooth: true,
				pointSize: 0,
				lineWidth: 0,
				fillOpacity: 0.85,
				data: [
					{ period: '2010 Q1', iphone: 2666, ipad: null, itouch: 2647 },
					{ period: '2010 Q2', iphone: 15778, ipad: 13794, itouch: 12041 },
					{ period: '2010 Q3', iphone: 12912, ipad: 10969, itouch: 9901 },
					{ period: '2010 Q4', iphone: 8767, ipad: 6597, itouch: 6689 },
					{ period: '2011 Q1', iphone: 10810, ipad: 10914, itouch: 12293 },
					{ period: '2011 Q2', iphone: 9670, ipad: 9000, itouch: 7881 },
					{ period: '2011 Q3', iphone: 4820, ipad: 3795, itouch: 1588 },
					{ period: '2011 Q4', iphone: 15073, ipad: 8967, itouch: 5175 },
					{ period: '2012 Q1', iphone: 10687, ipad: 4460, itouch: 2028 },
					{ period: '2012 Q2', iphone: 8432, ipad: 5713, itouch: 1791 }
				],
				lineColors: ['#869d9d', '#EFC94C', '#45B29D'],
				xkey: 'period',
				redraw: true,
				ykeys: ['iphone', 'ipad', 'itouch'],
				labels: ['All Visitors', 'Returning Visitors', 'Unique Visitors'],
				pointSize: 2,
				hideHover: 'auto',
				resize: true
			});

			// graph real time
			if ($('#graph-flot-realtime').length) {

				var data = [],
					totalPoints = 300;

				function getRandomData() {

					if (data.length > 0)
						data = data.slice(1);

					// Do a random walk

					while (data.length < totalPoints) {

						var prev = data.length > 0 ? data[data.length - 1] : 50,
							y = prev + Math.random() * 10 - 5;

						if (y < 0) {
							y = 0;
						} else if (y > 100) {
							y = 100;
						}

						data.push(y);
					}

					// Zip the generated y values with the x values

					var res = [];
					for (var i = 0; i < data.length; ++i) {
						res.push([i, data[i]])
					}

					return res;
				}

				// Set up the control widget

				var updateInterval = 30;
				$().val(updateInterval).change(function () {
					var v = $(this).val();
					if (v && !isNaN(+v)) {
						updateInterval = +v;
						if (updateInterval < 1) {
							updateInterval = 1;
						} else if (updateInterval > 2000) {
							updateInterval = 2000;
						}
						$(this).val("" + updateInterval);
					}
				});

				var plot = $.plot("#graph-flot-realtime", [getRandomData()], {
					series: {
						lines: {
							show: true,
							lineWidth: 1,
							fill: true,
							fillColor: { colors: [{ opacity: 0.5 }, { opacity: 0.5 }] }
						},
						shadowSize: 0	// Drawing is faster without shadows
					},
					colors: ["#1ABC9C"],

					yaxis: {
						min: 0,
						max: 110
					},
					xaxis: {
						show: false
					},
					grid: { borderWidth: 1, backgroundColor: "#FFF" }
				});

				function update() {

					plot.setData([getRandomData()]);

					// Since the axes don't change, we don't need to call plot.setupGrid()

					plot.draw();
					setTimeout(update, updateInterval);
				}

				update();
			}

			//WORLD MAP
			$('#world-map').vectorMap({
				map: 'world_merc_en',
				backgroundColor: '#ffffff',
				zoomOnScroll: false,
				regionStyle: {
					initial: {
						fill: '#e1e1e1',
						stroke: 'none',
						"stroke-width": 0,
						"stroke-opacity": 1
					},
					hover: {
						"fill-opacity": 0.8
					},
					selected: {
						fill: '#8dc859'
					},
					selectedHover: {
					}
				},
				markerStyle: {
					initial: {
						fill: '#FF6C60',
						stroke: '#FF6C60'
					}
				},
				markers: [
					{ latLng: [38.35, -121.92], name: 'Los Angeles - 23' },
					{ latLng: [39.36, -73.12], name: 'New York - 84' },
					{ latLng: [50.49, -0.23], name: 'London - 43' },
					{ latLng: [36.29, 138.54], name: 'Tokyo - 33' },
					{ latLng: [37.02, 114.13], name: 'Beijing - 91' },
					{ latLng: [-32.59, 150.21], name: 'Sydney - 22' },
				],
				series: {
					regions: [{
						values: gdpData,
						scale: ['#6fc4fe', '#58DDD0'],
						normalizeFunction: 'polynomial'
					}]
				},
				onRegionLabelShow: function (e, el, code) {
					el.html(el.html() + ' (' + gdpData[code] + ')');
				}
			});

			/* SPARKLINE - graph in header */
			var orderValues = [10, 8, 5, 7, 4, 4, 3, 8, 0, 7, 10, 6, 5, 4, 3, 6, 8, 9];

			$('.spark-orders').sparkline(orderValues, {
				type: 'bar',
				barColor: '#7FC8BA',
				height: 25,
				barWidth: 6
			});

			var revenuesValues = [8, 3, 2, 6, 4, 9, 1, 10, 8, 2, 5, 8, 6, 9, 3, 4, 2, 3, 7];

			$('.spark-revenues').sparkline(revenuesValues, {
				type: 'bar',
				barColor: '#7FC8BA',
				height: 25,
				barWidth: 6
			});

			/* ANIMATED WEATHER */
			var skycons = new Skycons({ "color": "#58DDD0" });
			// on Android, a nasty hack is needed: {"resizeClear": true}

			// you can add a canvas by it's ID...
			skycons.add("current-weather", Skycons.SNOW);

			// start animation!
			skycons.play();

			$('.conversation-inner').slimScroll({
				height: '405px',
				wheelStep: 35,
			});

			$('.chat-input').keypress(function (ev) {
				var p = ev.which;
				var chatTime = moment().format("MMMM Do YYYY, h:mm a");
				var chatText = $('.chat-input').val();
				if (p == 13) {
					if (chatText == "") {
						alert('Empty Field');
					} else {
						$('<div class="conversation-item item-left clearfix"><div class="conversation-user"><img src="img/samples/ryan.png" alt="male"/></div><div class="conversation-body"><div class="name">Ryan Gossling</div><div class="time hidden-xs">' + chatTime + '</div><div class="text">' + chatText + '</div></div></div>').appendTo('.conversation-inner');
						$(this).val('');
						$('.conversation-inner').scrollTo('100%', '100%', {
							easing: 'swing'
						});
					}
					return false;
					ev.epreventDefault();
					ev.stopPropagation();
				}
			});
			$('.chat-send .btn').click(function () {
				var chatTime = moment().format("MMMM Do YYYY, h:mm a");
				var chatText = $('.chat-input').val();
				if (chatText == "") {
					alert('Empty Field');
					$(".chat-input").focus();
				} else {
					$('<div class="conversation-item item-left clearfix"><div class="conversation-user"><img src="img/samples/ryan.png" alt="male"/></div><div class="conversation-body"><div class="name">Ryan Gossling</div><div class="time hidden-xs">' + chatTime + '</div><div class="text">' + chatText + '</div></div></div>').appendTo('.conversation-inner');
					$('.chat-input').val('');
					$(".chat-input").focus();
					$('.conversation-inner').scrollTo('100%', '100%', {
						easing: 'swing'
					});
				}
				return false;
			});
		});
	</script>
</body>

</html>