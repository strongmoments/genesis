var BILLING = function() {
	var invoiceNo, fromDate, toDate, billingQuantity, billingRate, billingWeight, gst, invoiceDate, totalAmount, wsoInfo;
	var id = 1;
	this.show = function() {
		$("#content-wrapper").empty();
		var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>Billing</h1>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<form role=\"form\">'
		html += '<div class=\"main-box\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-4\">'
		html += '<div class=\"form-group\">'
		html += '<label>From Client ID</label>'
		html += '<select class=\"form-control\" id=\"fromClientInfo\">'
		html += '<option>All Client</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-4\"></div>'
		html += '<div class=\"col-lg-4\">'
		html += '<div class=\"form-group\">'
		html += '<label>To Client ID</label>'
		html += '<select class=\"form-control\" id=\"toClientInfo\">'
		html += '<option>All Client</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<label>Billing Type</label>'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<div class=\"radio\">'
		html += '<input name=\"optionsRadios\" id=\"optionsRadios1\" type=\"radio\" checked value=\"common\">'
		html += '<label for=\"optionsRadios1\">'
		html += 'Common Billing'
		html += '</label>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		html += '<div class=\"radio\">'
		html += '<input name=\"optionsRadios\" id=\"optionsRadios2\" type=\"radio\"   value=\"lease\">'
		html += '<label for=\"optionsRadios2\">'
		html += 'Lease Billing'
		html += '</label>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		html += '<div class=\"radio\">'
		html += '<input name=\"optionsRadios\" id=\"optionsRadios3\" type=\"radio\" value=\"other\">'
		html += '<label for=\"optionsRadios3\">'
		html += 'Other Billing'
		html += '</label>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"btn btn-primary  mr-10\" id=\"saveBilling\" onclick=\"billing.generateBill();\">Bill-Generate</button>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"btn btn-info  mr-10\" onclick=\"billing.loadAllBill();\" id=\"cancelBilling\">Load All Bill</button>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		html += '<a  href=\"index.html\" class=\"btn btn-danger\">Exit</a>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"main-box billingTable\" id=\"commonBilling\" style=\"display: none\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"commonThead\">'
		html += '<tr>'
		html += '<th> Client Name</th>'
		html += '<th> From Date</th>'
		html += '<th> To Date</th>'
		html += '<th> Invoice No</th>'
		html += '<th> Total Amount</th>'
		html += '<th> Print Bill</th>'
		html += '</tr>'
		html += '</thead>'
		html += '<tbody id=\"commonTbody\">'
		html += ''
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"main-box billingTable\" id=\"leaseBilling\" style=\"display: none\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"leaseThead\">'
		html += '<tr>'
		html += '<th> Client Name</th>'
		html += '<th> From Date</th>'
		html += '<th> To Date</th>'
		html += '<th> Invoice No</th>'
		html += '<th> Invoice Date</th>'
		html += '<th> Gst</th>'
		html += '<th> Total Amount</th>'
		html += '<th> Print Bill</th>'
		html += '</tr>'
		html += '</thead>'
		html += '<tbody id=\"leaseTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"main-box billingTable\" id=\"otherBilling\" style=\"display: none\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"otherThead\">'
		html += '<tr>'
		html += '<th> Client Name</th>'
		html += '<!-- <th> Form No</th> --> '
		html += '<th> Invoice Date </th>'
		html += '<th> Invoice No</th>'
		html += '<th> Gst</th>'
		html += '<th> Total Amount</th>'
		html += '<th> Print Bill</th>'
		html += '</tr>'
		html += '</thead>'
		html += '<tbody id=\"otherTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'

		$("#content-wrapper").html(html);
		var billType = $("input[name='optionsRadios']:checked").val();
		billing.getClientName();
		billing.getAllBills(billType);
	}

	// Start Get Client
	this.getClientName = function() {
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : contextPath + "/clientInfos/allClientTitle/",
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(data) {
				console.log("success.....", data);
				// bootbox.alert(JSON.stringify(data))

				for (var i = 0; i < data.length; i++) {
					$('#fromClientInfo').append(
							"<option value=" + data[i].clientInfoId + ">"
									+ data[i].clientInfoId + "</option>");
					$('#toClientInfo').append(
							"<option value=" + data[i].clientInfoId + ">"
									+ data[i].clientInfoId + "</option>");
				}
				console.log("SUCCESS : ", data);
			},
			error : function(e) {
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText
						+ "</pre>";
				$('#feedback').html(json);
				console.log("ERROR : ", e);
			}
		});
	}
	// End Get Client

	// Start All bill
	this.getAllBills = function(billingTtype) {
		var fromClientInfo = $("#fromClientInfo option:selected").val();
		var toClientInfo = $("#toClientInfo option:selected").val();
		if (fromClientInfo == "All Client" && toClientInfo == "All Client") {
			fromClientInfo == "0";
			toClientInfo == "0";
		} else if (fromClientInfo == "All Client"
				&& toClientInfo != "All Client") {
			bootAlert.show("error", "Please Select From Client");
			return false;
		} else if (fromClientInfo != "All Client"
				&& toClientInfo == "All Client") {
			bootAlert.show("error", "Please Select To Client");
			return false;
		}

		var jsondata = {
			"billType" : billingTtype,
			"fromClient" : fromClientInfo,
			"toClientInfo" : toClientInfo
		};
		$
				.ajax({
					type : "POST",
					url : contextPath + "/billings/loadAllBill",
					data : jsondata,
					dataType : 'json',
					cache : false,
					timeout : 600000,
					success : function(response) {
						var data = response.data;
						console.log(data);
						$(".billingTable").hide();
						$("#" + billingTtype + "Billing").show();
						$("#" + billingTtype + "Thead").show();
						if (billingTtype == "common") {

							$("#" + billingTtype + "Tbody").empty();
							var tr = "";
							for (var i = 0; i < data.length; i++) {
								var clientName = data[i][0];
								var fromDate = data[i][1];
								fromDate = common.getFormattedDate(new Date(
										fromDate));
								var toDate = data[i][2];
								toDate = common.getFormattedDate(new Date(
										toDate));
								var invoiceNo = data[i][3];
								var totalAmount = data[i][4].toFixed(2);
								tr += "<tr>";
								tr += "<td>" + clientName + "</td>";
								tr += "<td>" + fromDate + "</td>";
								tr += "<td>" + toDate + "</td>";
								tr += "<td>" + invoiceNo + "</td>";
								tr += "<td>$ " + totalAmount + "</td>";
								tr += "<td><button class='btn btn-info' onclick='billing.commonBillReport(\""
										+ invoiceNo
										+ "\",\""
										+ billingTtype
										+ "\")'><i class='glyphicon glyphicon-print'></i></button></td>";
								tr += "</tr>";
							}
							$("#" + billingTtype + "Tbody").append(tr);
						}

						if (billingTtype == "lease") {
							$("#" + billingTtype + "Tbody").empty();
							var tr = "";
							for (var i = 0; i < data.length; i++) {
								var clientName = data[i][0];
								var fromDate = data[i][1];
								fromDate = common.getFormattedDate(new Date(
										fromDate));
								var toDate = data[i][2];
								toDate = common.getFormattedDate(new Date(
										toDate));
								var invoiceNo = data[i][3];
								var totalAmount = data[i][4].toFixed(2);
								var GST = data[i][5].toFixed(2);
								var invoiceDate = data[i][6];
								invoiceDate = common.getFormattedDate(new Date(invoiceDate));

								tr += "<tr>";
								tr += "<td>" + clientName + "</td>";
								tr += "<td>" + fromDate + "</td>";
								tr += "<td>" + toDate + "</td>";
								tr += "<td>" + invoiceNo + "</td>";
								tr += "<td>" + invoiceDate + "</td>";
								tr += "<td>$ " + GST + "</td>";
								tr += "<td>$ " + totalAmount + "</td>";
								tr += "<td><button class='btn btn-info' onclick='billing.commonBillReport(\""
										+ invoiceNo
										+ "\",\""
										+ billingTtype
										+ "\")'><i class='glyphicon glyphicon-print'></i></button></td>";
								tr += "</tr>";
							}
							$("#" + billingTtype + "Tbody").append(tr);
						}

						if (billingTtype == "other") {
							$("#" + billingTtype + "Tbody").empty();
							var tr = "";
							console.log(data)
							for (var i = 0; i < data.length; i++) {
								var clientName = data[i][0];
								var invoiceDate = data[i][1];
								invoiceDate = common.getFormattedDate(new Date(
										invoiceDate));
								var invoiceNo = data[i][2];
								var GST = data[i][3].toFixed(2);
								var totalAmount = data[i][4].toFixed(2);
								var formNumber = data[i][5];

								tr += "<tr>";
								tr += "<td>" + clientName + "</td>";
								/* tr += "<td>"+formNumber+"</td>"; */
								tr += "<td>" + invoiceDate + "</td>";
								tr += "<td>" + invoiceNo + "</td>";
								tr += "<td> $" + GST + "</td>";
								tr += "<td> $" + totalAmount + "</td>";
								tr += "<td><button class='btn btn-info' onclick='billing.commonBillReport(\""
										+ invoiceNo
										+ "\",\""
										+ billingTtype
										+ "\")'><i class='glyphicon glyphicon-print'></i></button></td>";
								tr += "</tr>";
							}
							$("#" + billingTtype + "Tbody").append(tr);
						}

					},
					error : function(e) {

						var json = "<h4>Ajax Response</h4><pre>"
								+ e.responseText + "</pre>";
						$('#feedback').html(json);

						console.log("ERROR : ", e);

					}
				});
	}

	// End All bill

	// Start load All bill
	this.loadAllBill = function() {
		var billType = $("input[name='optionsRadios']:checked").val();
		billing.getAllBills(billType);
	}
	// End load All Bill

	// Start generate Bill
	this.generateBill = function() {
		event.preventDefault();
		var fromClientInfo = parseInt($("#fromClientInfo").val());
		var toClientInfo = parseInt($("#toClientInfo").val());
		var billType = $("input[name='optionsRadios']:checked").val();

		var jsondata = {
			"fromClientInfo" : fromClientInfo,
			"toClientInfo" : toClientInfo,
			"billType" : billType
		};
		$.ajax({
			type : "POST",
			url : contextPath + "/billings/generateBill",
			data : jsondata,
			// data:
			// '{"dateOfDelivery":"'+dateOfDelivery+'","dlNo":"'+dlNo+'","timeOfIssue":"'+timeOfIssue+'","nameOfReceiver":"'+nameOfReceiver+'","vehicleNo":"'+vehicleNo+'","nricOfReceiver":"'+nricOfReceiver+'","verify":"'+verify+'"}',
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(data) {
				billing.getAllBills(billType);
				// bootbox.alert("bill generated successfully !"
				// +JSON.stringify(data));

			},
			error : function(e) {

				var json = "<h4>Ajax Response</h4><pre>" + e.responseText
						+ "</pre>";
				$('#feedback').html(json);

				console.log("ERROR : ", e);

			}
		});

	}
	// End Generate Bil

	this.commonBillReport = function(invoiceNumber, billingTtype) {
		$(".billingTable").hide();
		$("#" + billingTtype + "Billing").show();
		if (billingTtype == "common") {
			var jsondata = {
				"invoiceNumber" : invoiceNumber
			};
			$
					.ajax({
						type : "POST",
						url : contextPath + "/billings/getBillingByInvoice",
						data : jsondata,
						dataType : 'json',
						cache : false,
						timeout : 600000,
						async : false,
						success : function(response) {
							var data = response.data;
							console.log('getBillingByInvoice :'+data);
							$("#" + billingTtype + "Thead").hide();
							$("#" + billingTtype + "Tbody").empty();
							$("#" + billingTtype + "Tbody")
									.append(
											'<tr><td colspan="11"><div class="main-box"><button type="button" class="btn btn-info  mr-10" onclick="billing.loadAllBill();" style="float:right">Back</button>'
													+ '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'
													+ '<div style="float:left;" ></div><div style="float:right;"> </div>'
													+ '</header>'
													+ '<div class="main-box-body clearfix">'
													+ '<div class="row imgclass" >'
													+ '<img src="image/logo4.png" class="logo" style="width:750px"><br><br><span class="tax">Tax Invoice</span><br>'
													+ '<hr>'
													+ '</div>'
													+

													'<div class="row">'
													+ '<div class="col-xs-6 col-sm-6 col-md-6">'
													+ '<p id="commonAddress" class="clientAddress" style="width:300px;height:200px;">'
													+ '</p>'
													+ '</div>'
													+ '<div class="col-xs-6 col-sm-6 col-md-6 text-right">'
													+ '<table class="table">'
													+ '<tbody>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Invoice No</strong>'
													+ '</td>'
													+ '<td class="col-md-3">'
													+ '<label id="cinvoiceNo"></label>'
													+ '</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Date</strong>'
													+ '</td>'
													+ '<td class="col-md-3" id="cInvoiceDate"></td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Credit Terms</strong>'
													+ '</td>'
													+ '<td class="col-md-3">30 Days</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Page</strong>'
													+ '</td>'
													+ '<td class="col-md-9">1</td>'
													+ '</tr>'
													+ '</tbody>'
													+ '</table>'
													+ '</div>'
													+ '</div>'
													+ '<div class="row">'
													+ '<table class="table table-hover">'
													+ '<thead>'
													+ '<tr>'
													+ '<th><label style="margin-left: 11px;">Product</label></th>'
													+ '<th>'
													+ '</th>'
													+ '<th>'
													+ '</th>'
													+ '<th class="text-center">Weight</th>'
													+ '<th class="text-center">Rate</th>'
													+ '<th class="text-center">Sub Total</th>'
													+ '<th class="text-center">GST</th>'
													+ '<th class="text-center">Amount</th>'
													+ '</tr>'
													+ '</thead>'
													+ '<tbody id="cPoductDetail">'
													+ '<tr>'
													+ '</tr>'
													+ '</tbody>'
													+ '</table>'
													+ '</div>'
													+ '<p class="my-footer">CHEQUES SHOULD BE MADE CROSSED TO THE ORDER OF GENESIS COLD STORAGE (S) PTE LTD. <br> THIS IS A COMPUTER GENERATED INVOICE. NO SIGNATURE IS REQUIRED. <br> NOTE: INTEREST RATE @ 1% PER MONTH WILL BE IMPOSED ON ALL LATE PAYMENT.</p>'
													+ '</div>'
													+ '</div><button type="button" class="btn btn-info"  data='+invoiceNumber+'  onclick="billing.customPrint(this);">'
													+ 'Download'
													+ '<span class="glyphicon glyphicon-chevron-right">'
													+ '</span>'
													+ '<button type="button" class="btn btn-info" style="margin-left:20px;" data='+invoiceNumber+' id="printMeXls" onclick="billing.customPrintXls(this);">Download Excel'
													+ '<span class="glyphicon glyphicon-chevron-right">'
													+ '</span></button>'
													+ '</button><button type="button" class="btn btn-primary" data='+invoiceNumber+' style="margin-left:20px"; onclick="billing.customEmail(this);">'  
										            +'Send Email'
										            +'<span class="glyphicon glyphicon-send"></span>'
										            +'</button></td></tr>');
							var invoiceNo = data[0].invoiceNo;
							var invoiceDate = data[0].invoiceDate;
							var clientInfo = data[0].clientInfo;
							invoiceDate = common.getFormattedDate(new Date(
									invoiceDate));
							var netAmount = data[0].netAmount.toFixed(2);
							$("#cinvoiceNo").text(invoiceNo);
							$("#cInvoiceDate").text(invoiceDate);
							$("#cPoductDetail").empty();
							var totalamount = 0;
							var dTry = "";
							for (var i = 0; i < data.length; i++) {
								var formDate = data[i].fromDate;
								formDate = common.getFormattedDate(new Date(
										formDate));
								var toDate = data[i].toDate;
								toDate = common.getFormattedDate(new Date(
										toDate));
								var wsoNO = data[i].wsoInfo.wsoNo;
								var pallets = data[i].wsoInfo.totalNoOfPallets;
								var weight = data[i].billingWeight;
								var rate = data[i].billingRate;
								var description = data[i].wsoInfo.description;
								var gst = data[i].gst.toFixed(2);
								var netAmount = data[i].netAmount.toFixed(2);
								var billingWeight = data[i].billingWeight;
								var billingRate = data[i].billingRate.toFixed(2);
								var handlingCharge = data[i].handlingCharge;
								var subTotal = netAmount - gst;
								console.log('handlingCharge is '+handlingCharge)
								totalamount += parseFloat(netAmount);
								if (handlingCharge == 0
										|| handlingCharge == "0") {
									dTry += "<tr> <td class='col-md-9' ><em style='margin-left: 11px;'>"
											+ wsoNO
											/*+ "/"*/
											/*+ description*/
											+ " Billing Period from  "
											+ formDate
											+ " to  "
											+ toDate
											+ "</em></td> <td></td> <td></td>  <td class='col-md-1 text-center'>"
											+ billingWeight
											+ "</td> <td class='col-md-1 text-center'>$"
											+ billingRate
											+ "</td> <td class='col-md-1 text-center'>$"
											+ subTotal
											+ "</td> <td class='col-md-1 text-center'>$"
											+ gst
											+ "</td> <td class='col-md-1 text-center'>$"
											+ netAmount + "</td></tr>"
								} else {
									dTry += "<tr> <td class='col-md-9' ><em style='margin-left: 11px;'>Handling Charge</em></td> <td></td> <td class='col-md-1 text-center'></td>  <td class='col-md-4 text-center'>Pallets: "
											+ pallets
											+ "</td> <td class='col-md-1 text-center'>$"
											+ billingRate
											+ "</td> <td class='col-md-1 text-center'>$"
											+ subTotal
											+ "</td> <td class='col-md-1 text-center'>$"
											+ gst
											+ "</td> <td class='col-md-1 text-center'>$"
											+ netAmount + "</td></tr>"
								}

							}

							var clientAddress = clientInfo.clientAddress1;
							var clientTitle = clientInfo.clientTitle
							clientAddress = unescape(clientAddress);
							$("#commonAddress").html(
									"<b>" + clientTitle + "</b><br>"
											+ clientAddress);
							$("#cPoductDetail").append(dTry);
							$("#cPoductDetail")
									.append(
											'<tr>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td class="text-right">'
													+ '<h4>'
													+ '<b style="color:red;">Total:</b>'
													+ '</h4>'
													+ '</td>'
													+ '<td class="text-center text-danger">'
													+ '<h4>' + '<strong>$'
													+ totalamount.toFixed(2)
													+ '</strong>' + '</h4>'
													+ '</td>' + '</tr>');
						},
						error : function(e) {
						}
					});

		}

		if (billingTtype == "lease") {
			var jsondata = {
				"invoiceNumber" : invoiceNumber
			};
			$
					.ajax({
						type : "POST",
						url : contextPath + "/billings/getBillingByInvoice",
						data : jsondata,
						dataType : 'json',
						cache : false,
						timeout : 600000,
						async : false,
						success : function(response) {
							var data = response.data;
							$("#" + billingTtype + "Thead").hide();
							$("#" + billingTtype + "Tbody").empty();
							$("#" + billingTtype + "Tbody")
									.append(
											'<tr><td colspan="11"><div class="main-box"><button type="button" class="btn btn-info  mr-10" onclick="billing.loadAllBill();" style="float:right">Back</button>'
													+ '<form method="post" action="#" id="printJS-form2"><header class="main-box-header clearfix">'
													+ '<div style="float:left;" ></div><div style="float:right;"> </div>'
													+ '</header>'
													+ '<div class="main-box-body clearfix">'
													+ '<div class="row imgclass" >'
													+ '<img src="image/logo4.png"class="logo" style="width:750px"><br><br><span class="tax">Tax Invoice</span><br>'
													+ '<hr>'
													+ '</div>'
													+

													'<div class="row">'
													+ '<div class="col-xs-6 col-sm-6 col-md-6">'
													+ '<p id="leaseAddress" class="clientAddress" style="width:300px;height:200px;">'
													+ '</p>'
													+ '</div>'
													+ '<div class="col-xs-6 col-sm-6 col-md-6 text-right">'
													+ '<table class="table">'
													+ '<tbody>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Invoice No</strong>'
													+ '</td>'
													+ '<td class="col-md-3">'
													+ '<label id="linvoiceNo">'
													+ '</label>'
													+ '</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Date</strong>'
													+ '</td>'
													+ '<td class="col-md-3" id="lbInvoiceDate">'
													+ '</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Credit Terms</strong>'
													+ '</td>'
													+ '<td class="col-md-3">30 Days</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Page</strong>'
													+ '</td>'
													+ '<td class="col-md-9">1</td>'
													+ '</tr>'
													+ '</tbody>'
													+ '</table>'
													+ '</div>'
													+ '</div>'
													+ '<div class="row">'
													+ '<table class="table table-hover">'
													+ '<thead>'
													+ '<tr>'
													+ '<th><label style="margin-left: 11px;">Description</label></th>'
													+ '<th>'
													+ '</th>'
													+ '<th>'
													+ '</th>'
													+ '<th>'
													+ '</th>'
													+ '<th class="text-center"></th>'
													+ '<th class="text-center">Rate</th>'
													+ '<th class="text-center">GST</th>'
													+ '<th class="text-center">Amount</th>'
													+ '</tr>'
													+ '</thead>'
													+ '<tbody id="lbillPoductDetail">'
													+ '<tr>'
													+ '</tr>'
													+ '</tbody>'
													+ '</table>'
													+ '</div>'
													+ '<p class="my-footer">CHEQUES SHOULD BE MADE CROSSED TO THE ORDER OF GENESIS COLD STORAGE (S) PTE LTD .<br> THIS IS A COMPUTER GENERATED INVOICE. NO SIGNATURE IS REQUIRED. <br> NOTE: INTEREST RATE @ 1% PER MONTH WILL BE IMPOSED ON ALL LATE PAYMENT.</p>'
													+ '</div>'
													
													+ '</div><button type="button" class="btn btn-info" data='+invoiceNumber+' onclick="billing.customPrint1(this)">'
													+ 'Download'
													+ '<span class="glyphicon glyphicon-chevron-right">'
													+ '</span>'
													+ '<button type="button" class="btn btn-info" style="margin-left:20px;" data='+invoiceNumber+' id="printMeXls" onclick="billing.customPrintXls1(this);">Download Excel'
													+ '<span class="glyphicon glyphicon-chevron-right">'
													+ '</span></button>'
													+ '</button><button type="button" class="btn btn-primary" data='+invoiceNumber+' style="margin-left:20px"; onclick="billing.customEmail1(this);">'  
										            +'Send Email'
										            +'<span class="glyphicon glyphicon-send"></span>'
										            +'</button></td></tr>');

							var invoiceNo = data[0].invoiceNo;
							var invoiceDate = data[0].invoiceDate;
							invoiceDate = common.getFormattedDate(new Date(
									invoiceDate));
							var netAmount = data[0].netAmount;
							$("#linvoiceNo").text(invoiceNo);
							$("#lbInvoiceDate").text(invoiceDate);
							$("#lbillPoductDetail").empty()
							var totalamount = 0;
							var dTry = "";
							for (var i = 0; i < data.length; i++) {
								var clientInfo = data[i].clientInfo.clientTitle;
								var storageClass = data[i].storageClass.storageTypeName;
								var rate = data[i].billingRate;
								var gst = data[i].gst;
								var netAmount = data[i].netAmount;
								var formDate = data[i].fromDate;
								formDate = common.getFormattedDate(new Date(
										formDate));
								var toDate = data[i].toDate;
								toDate = common.getFormattedDate(new Date(
										toDate));
								var handlingCharge = data[i].handlingCharge;
								totalamount += parseFloat(netAmount);

								if (handlingCharge == 0
										|| handlingCharge == "0") {
									dTry += "<tr> <td class='col-md-9' ><em style='margin-left: 11px;'> Billing Period for the "
											+ storageClass
											+ " is from  "
											+ formDate
											+ " to  "
											+ toDate
											+ "</em></td> <td></td> <td></td>  <td class='col-md-1 text-center'></td><td class='col-md-1 text-center'></td> <td class='col-md-1 text-center'>$"
											+ rate
											+ "</td> <td class='col-md-1 text-center'>$"
											+ gst
											+ "</td> <td class='col-md-1 text-center'>$"
											+ netAmount + "</td></tr>"
								}

							}
							var clientInfo = data[0].clientInfo;
							var clientAddress = clientInfo.clientAddress1;
							var clientTitle = clientInfo.clientTitle
							clientAddress = unescape(clientAddress);
							$("#leaseAddress").html(
									"<b>" + clientTitle + "</b><br>"
											+ clientAddress);

							$("#lbillPoductDetail").append(dTry);
							$("#lbillPoductDetail")
									.append(
											'<tr>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td class="text-right">'
													+ '<h4>'
													+ '<b style="color:red;">Total:</b>'
													+ '</h4>'
													+ '</td>'
													+ '<td class="text-center text-danger">'
													+ '<h4>' + '<strong>$'
													+ totalamount.toFixed(2)
													+ '</strong>' + '</h4>'
													+ '</td>' + '</tr>');
						},
						error : function(e) {
						}
					});
		}

		if (billingTtype == "other") {
			var jsondata = {
				"invoiceNumber" : invoiceNumber
			};
			$
					.ajax({
						type : "POST",
						url : contextPath + "/billings/getBillingByInvoice",
						data : jsondata,
						dataType : 'json',
						cache : false,
						timeout : 600000,
						async : false,
						success : function(response) {

							var data = response.data;
							$("#" + billingTtype + "Thead").hide();
							$("#" + billingTtype + "Tbody").empty();
							$("#" + billingTtype + "Tbody")
									.append(
											'<tr><td colspan="11"><div class="main-box"> <button type="button" class="btn btn-info  mr-10" onclick="billing.loadAllBill();" style="float:right">Back</button>'
													+ '<form method="post" action="#" id="printJS-form1"><header class="main-box-header clearfix">'
													+ '<div style="float:left;" ></div><div style="float:right;"></div>'
													+ '</header>'
													+ '<div class="main-box-body clearfix">'
													+ '<div class="row imgclass" >'
													+ '<img src="image/logo4.png"class="logo" style="width:750px"><br><br><span class="tax">Tax Invoice</span><br>'
													+ '<hr>'
													+ '</div>'
													+ '<div class="row">'
													+ '<div class="col-xs-6 col-sm-6 col-md-6">'
													+ '<p id="otherAddress" class="clientAddress" style="width:300px;height:200px;">'
													+ '</p>'
													+ '</div>'
													+ '<div class="col-xs-6 col-sm-6 col-md-6 text-right">'
													+ '<table class="table">'
													+ '<tbody>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Invoice No</strong>'
													+ '</td>'
													+ '<td class="col-md-3">'
													+ '<label id="oinvoiceNo">'
													+ '</label>'
													+ '</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Date</strong>'
													+ '</td>'
													+ '<td class="col-md-3">'
													+ '<label id="obInvoiceDate">'
													+ '</label>'
													+ '</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Credit Terms</strong>'
													+ '</td>'
													+ '<td class="col-md-3">30 Days</td>'
													+ '</tr>'
													+ '<tr>'
													+ '<td class="col-md-9">'
													+ '<strong>Page</strong>'
													+ '</td>'
													+ '<td class="col-md-9">1</td>'
													+ '</tr>'
													+ '</tbody>'
													+ '</table>'
													+ '</div>'
													+ '</div>'
													+ '<div class="row">'
													+ '<table class="table table-hover">'
													+ '<thead>'
													+ '<tr>'
													+ '<th><label style="margin-left: 11px;">Charge Items</label></th>'
													+ '<th class="text-center" ></th>'
													+ '<th>'
													+ '</th>'
													+ '<th class="text-center"></th>'
													+ '<th class="text-center">Billable Unit</th>'
													+ '<th class="text-center">Rate</th>'
													+ '<th class="text-center">Sub Total</th>'
													+ '<th class="text-center" >GST</th>'
													+ '<th class="text-center">Amount</th>'
													+ '</tr>'
													+ '</thead>'
													+ '<tbody id="obillPoductDetail">'
													+ '<tr>'
													+ '</tr>'
													+

													'</tbody>'
													+ '</table>'
													+ '</div>'
													+ '<p class="my-footer">CHEQUES SHOULD BE MADE CROSSED TO THE ORDER OF GENESIS COLD STORAGE (S) PTE LTD. <br> THIS IS A COMPUTER GENERATED INVOICE. NO SIGNATURE IS REQUIRED. <br> NOTE: INTEREST RATE @ 1% PER MONTH WILL BE IMPOSED ON ALL LATE PAYMENT.</p>'
													+ '</div>'
													
													+ '</div><button type="button" class="btn btn-info"  data='+invoiceNumber+'  onclick="billing.customPrint2(this)">'
													+ 'Download'
													+ '<span class="glyphicon glyphicon-chevron-right">'
													+ '</span>'
													+ '<button type="button" class="btn btn-info" style="margin-left:20px;" data='+invoiceNumber+' id="printMeXls" onclick="billing.customPrintXls2(this);">Download Excel'
													+ '<span class="glyphicon glyphicon-chevron-right">'
													+ '</span></button>'
													+ '</button><button type="button" class="btn btn-primary" data='+invoiceNumber+' style="margin-left:20px"; onclick="billing.customEmail2(this);">'  
										            +'Send Email'
										            +'<span class="glyphicon glyphicon-send"></span>'
										            +'</button></td></tr>');
							var invoiceNo = data[0].invoiceNo;
							var invoiceDate = data[0].invoiceDate;
							invoiceDate = common.getFormattedDate(new Date(
									invoiceDate));
							// var netAmount=data[0].netAmount;
							$("#oinvoiceNo").text(invoiceNo);
							$("#obInvoiceDate").text(invoiceDate);
							$("#obillPoductDetail").empty();
							var totalamount = 0;
							var dTry = "";
							for (var i = 0; i < data.length; i++) {
								var chargeItem = data[i].chargeItems.chargeItem;
								var rate = data[i].billingRate.toFixed(2);
								var gst = data[i].gst.toFixed(2);
								var netAmount = data[i].netAmount.toFixed(2);
								var formNumber = data[i].formNo;
								var naration = data[i].naration;
								var billableUnit = data[i].billingQuantity
										.toFixed(2);
								totalamount += parseFloat(netAmount)
								var subTotal = billableUnit * parseFloat(rate);
								dTry += "<tr> <td class='col-md-8' ><em style='margin-left: 11px;' ><br>"
										
										+ "<pre>"+naration+"</pre>"
										+ "</em></td> <td></td> <td></td><td></td> <td class='col-md-1 text-center'>"
										
										+ billableUnit
										+ "</td> <td class='col-md-2 text-center'>$"
										+rate
										+ "</td> <td class='col-md-2 text-center'>$"
										+subTotal
										+ "</td> <td class='col-md-2 text-center'>$ "
										+ gst
										+ "</td> <td class='col-md-2 text-center'>$ "
										+ netAmount + "</td></tr>"
							}
							
							var clientInfo = data[0].clientInfo;
							var clientAddress = clientInfo.clientAddress1;
							var clientTitle = clientInfo.clientTitle
							clientAddress = unescape(clientAddress);
							$("#otherAddress").html(
									"<b>" + clientTitle + "</b><br>"
											+ clientAddress);
							$("#obillPoductDetail").append(dTry);
							$("#obillPoductDetail")
									.append(
											'<tr>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td>'
													+ '</td>'
													+ '<td class="text-right">'
													+ '<h4>'
													+ '<b style="color:red;">Total:</b>'
													+ '</h4>'
													+ '</td>'
													+ '<td class="text-center text-danger">'
													+ '<h4>' + '<strong>$'
													+ totalamount.toFixed(2)
													+ '</strong>' + '</h4>'
													+ '</td>' + '</tr>');
						},
						error : function(e) {
						}
					});

		}
	}

	this.customPrint = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		window.open('download/common/commonbilling.pdf/pdf/'+invoiceNo,"_parent");
		// $("#printJS-form").printMe();
		/*$("#printJS-form").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});*/
	}

	this.customPrint1 = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		
		window.open('download/lease/leasebilling.pdf/pdf/'+invoiceNo, '_parent');
		
		// $("#printJS-form").printMe();
		/*$("#printJS-form2").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});*/
	}

	this.customPrint2 = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		
		window.open('download/other/otherbilling.pdf/pdf/'+invoiceNo, '_parent');
		
		// $("#printJS-form").printMe();
		/*$("#printJS-form1").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});*/
	}
	
	this.customPrintXls = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		window.open('download/common/commonbilling.xls/xls/'+invoiceNo,"_parent");
		// $("#printJS-form").printMe();
		/*$("#printJS-form").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});*/
	}
	this.customPrintXls1 = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		
		window.open('download/lease/leasebilling.xls/xls/'+invoiceNo, '_parent');
		
		// $("#printJS-form").printMe();
		/*$("#printJS-form2").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});*/
	}
	this.customPrintXls2 = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		
		window.open('download/other/otherbilling.xls/xls/'+invoiceNo, '_parent');
		
		// $("#printJS-form").printMe();
		/*$("#printJS-form1").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});*/
	}
	this.customEmail = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		//alert("Invoice Number:"+invoiceNo);
		window.open('email/common/commonbilling.pdf/pdf/'+invoiceNo, '_blank');
	}
	this.customEmail1 = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		//alert("Invoice Number:"+invoiceNo);
		window.open('email/lease/leasebilling.pdf/pdf/'+invoiceNo, '_blank');
	}
	this.customEmail2 = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		//alert("Invoice Number:"+invoiceNo);
		window.open('email/other/otherbilling.pdf/pdf/'+invoiceNo, '_blank');
	}

}
