var DATEINVENTORY =function (){
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>Stock Balance Report(by Lot Description)</h1>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<form role=\"form\">'
		html += '<div class=\"main-box\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>Client Name</label>'
		html += '<select class=\"form-control\" id=\"clientInfo\">'
		html += '<option>Select Client Name</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>As On DATE</label>'
		html += '<input class=\"form-control\" id=\"fromdate\" type="date">'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"form-control btn btn-info  mr-10\" onclick=\"dateInventory.loadInventoryByClient();\" >Load</button>'
		html += '</div>'
		/*html += '<div class=\"col-lg-4\">'
		html += '<a  href=\"#\" class=\"btn btn-danger\">Exit</a>'
		html += '</div>'*/
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"main-box billingTable\" id=\"finance\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'
		html += '<div class="col-12-lg imgclass" style="display:none">'
		html += '<center><img src="image/logo4.png" class="logo " style="width:750px;"><br><br><span class="tax">OutGoing Records</span><br></center>'
		html += '<hr>'
		html += '</div>'
		html += '<div class="table-responsive">'
		html += '<table class=\"table table-hover\" style="width:100%;">'
		html += '<thead id=\"finThead\">'
		html += '<tr>'
		html += '<th>Product Description</th>'
		html += '<th style="text-align: right">Product Code</th>'
		html += '<th style="text-align: right">WSO No</th>'
		html += '<th>CLASS</th>'
		html += '<th>Date</th>'
		html += '<th style="text-align: right">INIT Qty</th>'
		html += '<th style="text-align: right">CURR  Qty</th>'
		html += '<th>Prod Date</th>'
		html += '<th>Exp Date</th>'
		/*html += '<th>Net Wt</th>'
		html += '<th>Gross Wt</th>'*/
		html += '</tr>'
		html += '</thead>'
		html += '<tbody id=\"inventoryTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'	
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="dateInventory.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="dateInventory.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="dateInventory.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
		html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
          $("#content-wrapper").append(html);	
			
		tally.loadAllclient();
        }
	
	//Start loadInventoryByClient
	this.loadInventoryByClient = function(){
		var clientInfo = $("#clientInfo").val();
		var fromdate = $("#fromdate").val();
		
		if(clientInfo == "" || clientInfo == undefined || clientInfo == null){
			 bootAlert.show("error","Please select Client");
	         return false;
		}
		
		if(fromdate == "" || fromdate == undefined || fromdate == null){
			 bootAlert.show("error","Please select date");
	         return false;
		}
		
		var jsondata = {'fromdate': fromdate};
		 
	    $.ajax({
	        type: "POST",
	        /*contentType: "application/json",*/
	        url: contextPath+ "/tallysheets/loadInventoryByClient/"+clientInfo,
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	data = response.data;
	        	console.log(data);
	            $("#inventoryTbody").empty();
	            var tr="";
	            var totalInitialQuantity = 0.00;
	            var totalAvailableLot = 0.00;
	             if(data.length <= 0){
	            	 alert("No data available")
	             }else{
	            	 for(var i=0; i<data.length; i++){
	 	            	$("#printMePdf").show();
	 	            	$("#printMeXls").show();
	 	            	$("#sendEmail").show();
	 	                var productDescription = data[i].productDescription;
	 	                var wsoNo   = data[i].wsoNo ;
	 	                //clientAddress1  = escape(clientAddress1);
	 	                //clientAddress1 = unescape(clientAddress1);
	 	                var lotNo   = data[i].lotNo ;
	 	                var storageClasss   = data[i].storageClasss ;
	 	                var storageDate   = data[i].storageDate ;
	 	                storageDate= common.getFormattedDate(new Date(storageDate));
	 	                var initialQuantity   = data[i].initialQuantity ;
	 	                var availableLot   = data[i].availableLot ;
	 	                var expiryDate   = data[i].expiryDate ;
	 	                expiryDate= common.getFormattedDate(new Date(expiryDate));
	 	                var productionDate   = data[i].productionDate ;
	 	                productionDate= common.getFormattedDate(new Date(productionDate));
	 	                var netWeight   = data[i].netWeight.toFixed(2) ;
	 	                var grossWeight   = data[i].grossWeight.toFixed(2) ;
	 	                tr += "<tr>";
	 	                tr += "<td style='width: 500px;'>"+productDescription+"</td> <td style='width: 100px; text-align: right;'> "+lotNo+" </td> <td style='width: 100px;text-align: right;'> "+wsoNo+"</td> <td style='width: 100px;'> "+storageClasss+" </td> <td style='width: 100px;'> "+storageDate+" </td> <td style='width: 100px;text-align: right'> "+initialQuantity+" </td> " ;
	 	                tr += "<td style='width: 100px;text-align: right'>"+availableLot+"</td><td style='width: 200px;'> "+productionDate+"</td> <td style='width: 300px;'> "+expiryDate+" </td> ";/* <td style='width: 200px;text-align: right'> "+netWeight+" </td> <td style='width: 200px;text-align: right'> "+grossWeight+" </td>*/
	 	                tr += "</tr>";
	 	                totalInitialQuantity += parseFloat(initialQuantity);
	 	                totalAvailableLot += parseFloat(availableLot);
	 	               
	 	            }
	 	            $("#inventoryTbody").append(tr);
	 	            $("#inventoryTbody").append('<tr>'+
	 	            	    '<td></td><td></td><td class="td"><b>Total</b></td><td></td><td></td>'+
	 	            	    '<td class="td" style="text-align: right"><b>'+totalInitialQuantity+'</b></td><td class="td" style="text-align: right"><b>'+totalAvailableLot+'</b></td></tr>' 
	 	                /*'<td></td><td></td><td></td><td></td>'*/
	 	            	);

	             }
	            	
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });

	}

	// End loadInventoryByClient
	
	
	this.customPrintPdf = function(thisObj) {
	
		var clientInfo = $("#clientInfo").val();
		clientInfo = parseInt(clientInfo);
		var fromdate = $("#fromdate").val();
			/*var invoiceNo = $(thisObj).attr("data");*/
			window.open('download/dateInventory/dateInventoryReport.pdf/pdf/'+clientInfo+'/'+fromdate,  '_parent');
			// $("#printJS-form").printMe();
			/*$("#printJS-form").printMe(
					{
						"path" : [ "css/compiled/theme_styles.css",
								"css/bootstrap/bootstrap.min.css",
								"css/mystyle2.css", "css/footer.css" ]

					});*/
		}

	this.customPrintXls = function(thisObj) {
	
		var clientInfo = $("#clientInfo").val();
		clientInfo = parseInt(clientInfo);
		var fromdate = $("#fromdate").val();
			/*var invoiceNo = $(thisObj).attr("data");*/
			window.open('download/dateInventory/dateInventoryReport.xls/xls/'+clientInfo+'/'+fromdate,  '_parent');
			// $("#printJS-form").printMe();
			/*$("#printJS-form").printMe(
					{
						"path" : [ "css/compiled/theme_styles.css",
								"css/bootstrap/bootstrap.min.css",
								"css/mystyle2.css", "css/footer.css" ]

					});*/
		}
	this.customEmail = function (thisObj){
		var clientInfo = $("#clientInfo").val();
		clientInfo = parseInt(clientInfo);
		var fromdate = $("#fromdate").val();
		window.open('email/dateInventory/dateInventoryReport.pdf/pdf/'+clientInfo+'/'+fromdate,  '_parent');
	 }
	}