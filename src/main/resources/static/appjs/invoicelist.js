var INVOICELIST =function (){
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>INVOICE-LIST</h1>'
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
		html += '<select class=\"form-control\" id=\"clientInfo\">'
		html += '<option>Select Client Name</option>'
		html += '</select>'
		html += '</div>'
		html +=	'<div class=\"col-lg-4\"></div>'	
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"btn btn-info  mr-10\" onclick=\"invoicelist.loadBillingByClientAndDate();\" >Load</button>'
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
		html += '<center><img src="image/logo4.png" class="logo " style="width:750px;"><br><br><span class="tax">Finance Records</span><br></center>'
		html += '<hr>'
		html += '</div>'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"invThead\">'
		html += '<th>Client Name</th>'
		html += '<th style="text-align: right;">Invoice No</th>'
		html += '<th style="text-align: center;">Date</th>'
		html += '<th style="text-align: right;">Sub Total</th>'
		html += '<th style="text-align: right;">Gst</th>'
		html += '<th style="text-align: right;">Total</th>'
		html += '</tr>'
		html += '</thead>'
		html += '<tbody id=\"invTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="invoicelist.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="invoicelist.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="invoicelist.customEmail(this);">' +
            'Send Email'+
            '<span class="glyphicon glyphicon-send">'+'</span>'+
            '</button>'
		html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
		
          $("#content-wrapper").append(html);	
        
		tally.loadAllclient();
        }
	//Start loadBillingByClientAndDate
	this.loadBillingByClientAndDate = function(){
		var clientInfo = $("#clientInfo").val();
		var fromdate = $("#fromdate").val();
		var todate = $("#todate").val();
		if(clientInfo == "" || clientInfo == undefined || clientInfo == null){
			 bootAlert.show("error","Please select Client");
	         return false;
		}
		
		if(fromdate == "" || fromdate == undefined || fromdate == null){
			 bootAlert.show("error","Please select from date");
	         return false;
		}
		
		if(todate == "" || todate == undefined || todate == null){
			 bootAlert.show("error","Please select to date");
	         return false;
		}
		var jsondata = {
				'clientId': clientInfo,
				'fromdate': fromdate,
				'todate': todate};
		 
	    $.ajax({
	        type: "POST",
	        /*contentType: "application/json",*/
	        url: contextPath+ "/billings/getBillingByClientIdAndDates",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	data = response.data;
	        	console.log(data);
	            $("#invTbody").empty();
	            var tr="";
	            var totalSubTotal = 0.00;
	            var totalGst= 0.00;
	            var totalTotal= 0.00;
	             if(data.length <= 0){
	            	 alert("No data available")
	             }else{
	            	 for(var i=0; i<data.length; i++){
	 	            	$("#printMePdf").show();
	 	            	$("#printMeXls").show();
	 	            	//$("#sendEmail").show();
	 	                var invNo = data[i].invNo;
	 	                var invDate   = data[i].invDate ;
	 	                invDate= common.getFormattedDate(new Date(invDate));
	 	                var client   = data[i].client ;
	 	                var subTotal   = data[i].subTotal.toFixed(2) ;
	 	                var gst   = data[i].gst.toFixed(2) ;
	 	                var total   = data[i].total.toFixed(2) ;
	 	              
	 	                tr += "<tr>";
	 	                tr += "<td style='width: 300px;'>"+client+"</td> <td style='width: 100px; text-align: right;'> "+invNo+" </td> <td style='width: 100px;text-align: center;'> "+invDate+"</td> <td style='width: 100px;text-align: right;'> "+subTotal+" </td> <td style='width: 100px;text-align: right'> "+gst+" </td> <td style='width: 100px;text-align: right'> "+total+" </td> " ;
	 	                tr += "</tr>";
	 	                totalSubTotal += parseFloat(subTotal);
	 	                totalGst += parseFloat(gst);
	 	                totalTotal += parseFloat(total);
	 	               
	 	            }
	 	            $("#invTbody").append(tr);
	 	            $("#invTbody").append('<tr>'+
	 	            	    '<td></td><td></td><td class="td"><b>Total</b></td>'+
	 	            	    '<td class="td" style="text-align: right"><b>'+totalSubTotal.toFixed(2)+'</b></td><td class="td" style="text-align: right"><b>'+totalGst.toFixed(2)+'</b></td></td><td class="td" style="text-align: right"><b>'+totalTotal.toFixed(2)+'</b></td></tr>'  
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
	this.customPrintPdf = function (thisObj){
		var clientId = $('#clientInfo').val();
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
//		alert("Print Button Clicked");
		window.open('download/getBillingByClientIdAndDates/invoicelistReport.pdf/pdf/'+clientId+'/'+fromdate+'/'+todate, '_blank');
	 }
	 this.customPrintXls = function (thisObj){
		var clientId = $('#clientInfo').val();
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
		window.open('download/getBillingByClientIdAndDates/invoicelistReport.xls/xls/'+clientId+'/'+fromdate+'/'+todate, '_blank');
	 }
	 this.customEmail = function (thisObj){
		var clientId = $('#clientInfo').val();
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
		window.open('email/getBillingByClientIdAndDates/invoicelistReport.pdf/'+clientId+'/'+fromdate+'/'+todate, '_blank');
	 }
}