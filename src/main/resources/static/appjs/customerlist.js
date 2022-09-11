var CUSTOMERLIST=function (){
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>Customer List</h1>'
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
		html += '<label>From DATE</label>'
		html += '<input class=\"form-control\" id=\"fromdate\" type="date">'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>To DATE</label>'
		html += '<input class=\"form-control\" id=\"todate\" type="date">'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"form-control btn btn-info  mr-10\" onclick=\"customerlist.loadCustomerlistByDate();\" >Load</button>'
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
		html += '<thead id=\"cusThead\">'
		html += '<tr>'
		html += '<th>Client Name</th>'
		html += '<th>Storage</th>'
		html += '<th style="text-align: right;">Other</th>'
		html += '<th style="text-align: right;">FBA</th>'
		html += '<th style="text-align: right;">RT</th>'
		html += '<th style="text-align: right;">TOTAL GST</th>'
		html += '<th style="text-align: right;">Total Revenue</th>'
		html += '</tr>'
		html += '</thead>'
		html += '<tbody id=\"customerTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'	
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="customerlist.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="customerlist.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="customerlist.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
		html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
          $("#content-wrapper").append(html);	
			
		tally.loadAllclient();
        }
	
	///Start loadCustomerlistByDate
	this.loadCustomerlistByDate = function(){
		var fromdate = $("#fromdate").val();
		var todate = $("#todate").val();
		
		if(fromdate == "" || fromdate == undefined || fromdate == null){
			 bootAlert.show("error","Please select from date");
	         return false;
		}
		
		if(todate == "" || todate == undefined || todate == null){
			 bootAlert.show("error","Please select to date");
	         return false;
		}
		var jsondata = {
				'fromdate': fromdate,
				'todate': todate};
		 
	    $.ajax({
	        type: "POST",
	        /*contentType: "application/json",*/
	        url: contextPath+ "/clientStorageInfos/customerlist",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	data = response.data;
	        	console.log(data);
	            $("#customerTbody").empty();
	            var tr="";
	            var totalStorage = 0.0;
	            var totalOthersChrg = 0.00;
	        	var totalTotalBilableAmtFBA = 0.00;
	        	var totalTotalBilableAmtRT = 0.00;
	        	var totalTotalGst = 0.00;
	        	var totalTotalRevenue = 0.00;
	             if(data.length <= 0){
	            	 alert("No data available")
	             }else{
	            	 for(var i=0; i<data.length; i++){
	 	            	$("#printMePdf").show();
	 	            	$("#printMeXls").show();
	 	            	//$("#sendEmail").show();
	 	                var clientTitle = data[i].clientTitle;
	 	                var storage   = data[i].storage.toFixed(2) ;
	 	                var othersChrg   = data[i].othersChrg.toFixed(2) ;
	 	                var totalBilableAmtFBA   = data[i].totalBilableAmtFBA.toFixed(2) ;
	 	                var totalBilableAmtRT   = data[i].totalBilableAmtRT.toFixed(2) ;
	 	                var gst = data[i].gst.toFixed(2) ;
	 	                var totalRevenue   = data[i].totalRevenue.toFixed(2);
	 	                
	 	                tr += "<tr>";
	 	                tr += "<td style='width: 300px;'>"+clientTitle+"</td> <td style='width: 100px; text-align: left;'> "+storage+" </td> <td style='width: 100px;text-align: right;'> "+othersChrg+"</td> <td style='width: 100px;text-align: right'> "+totalBilableAmtFBA+" </td> <td style='width: 100px;text-align: right'> "+totalBilableAmtRT+" </td> <td style='width: 100px;text-align: right'> "+gst+" </td> <td style='width: 100px;text-align: right'> "+totalRevenue+" </td> " ;
	 	                tr += "</tr>";
	 	                totalStorage += parseFloat(storage);
	 	                totalOthersChrg += parseFloat(othersChrg);
	 	                totalTotalBilableAmtFBA += parseFloat(totalBilableAmtFBA);
	 	                totalTotalBilableAmtRT += parseFloat(totalBilableAmtRT);
	 	                totalTotalGst += parseFloat(gst);
	 	                totalTotalRevenue += parseFloat(totalRevenue);
	 	               
	 	            }
	 	            $("#customerTbody").append(tr);
	 	            $("#customerTbody").append('<tr>'+
	 	            	    '<td  style="text-align: left"><b>Total</b></td><td><b>'+totalStorage.toFixed(2)+'</b></td><td class="td" style="text-align: right"><b>'+totalOthersChrg.toFixed(2)+'</b></td><td class="td" style="text-align: right"><b>'+totalTotalBilableAmtFBA.toFixed(2)+'</b></td><td class="td" style="text-align: right"><b>'+totalTotalBilableAmtRT.toFixed(2)+'</b></td></td><td class="td" style="text-align: right"><b>'+totalTotalGst.toFixed(2)+'</b></td><td class="td" style="text-align: right"><b>'+totalTotalRevenue.toFixed(2)+'</b></td></tr>'  
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

	// End loadCustomerlistByDate
	
	
	this.customPrintPdf = function (thisObj){
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
//		alert("Print Button Clicked");
		window.open('download/getCustomerlist/customerlist.pdf/pdf/'+fromdate+'/'+todate, '_blank');
	 }
	 this.customPrintXls = function (thisObj){
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
		window.open('download/getCustomerlist/customerlist.xls/xls/'+fromdate+'/'+todate, '_blank');
	 }
	 this.customEmail = function (thisObj){
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
		window.open('email/getCustomerlist/customerlist.pdf/'+fromdate+'/'+todate, '_blank');
	 }
	}