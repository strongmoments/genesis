
var PAYMENTBILLING =function (){
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
        
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>Payment Billing</h1>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<form role=\"form\" >'
		html += '<div class=\"main-box\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>From Date</label>'
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
		html += '<button type=\"button\" class=\"btn btn-primary  mr-10\" onclick=\"paymentbilling.loadPaymentBetweenDates();\" >Load </button>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		/*html += '<a  href=\"#\" class=\"btn btn-danger\">Exit</a>'*/
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"main-box outstandingTable\" id=\"outstanding\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"outstandingThead\">'
		/*html += '<tr>'
		html += '<th style="text-align: right"> Invoice</th>'
		html += '<th> Date</th>'
		html += '<th style="text-align: right"> Invoice Amount</th>'
		html += '<th style="text-align: right"> Balance</th>'
		html += '<th style="text-align: right"> Credit Terms</th>'
		html += '</tr>'*/
		html += '</thead>'
		html += '<tbody id=\"outstandingTbody\">'
		html += '<tr>'
		html += '</tr>'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
		html +='</div>'
		html += '</div>'	
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="paymentbilling.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="paymentbilling.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="paymentbilling.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
			html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
          $("#content-wrapper").append(html);
		/*for (i = new Date().getFullYear(); i > 2016; i--)
		{
		    $('#yearpicker').append($('<option />').val(i).html(i));
		}*/
        }
	
	this.loadPaymentBetweenDates = function(){
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
		
		var jsondata = {'fromdate':fromdate,'todate':todate}
		$.ajax({
	        type: "POST",
	        url: contextPath+ "/clientStorageInfos/paymentBetweenDates",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	$("#outstanding").empty();
	        	$("#printMePdf").show();
		           $("#printMeXls").show();
		           $("#sendEmail").show();
	           var currentDate = new Date();
	           var day = currentDate.getDate();
	           var month = currentDate.getMonth() + 1;
	           var year = currentDate.getFullYear();
        		var html1 = ""
        			html1 += '<div class=\"main-box-body clearfix\">'
        			html1 += '<div style="test-align:right">'+ day + '/' + month + '/' + year + '</div>'	
        			
    				html1 += '<div class="col-12-lg imgclass" style="display:show">'	
					html1 += '<center><img src="image/logo4.png" class="logo " style="width:750px;"> <br><br><span class="tax">Payment Billing Report</span><br></center>'
					html1 += '<hr>'
					html1 += '</div>'
        						
        			//html1 += '<div class="row">'
					html1 += '<div class="table-responsive">'
        			html1 += '<table class=\"table table-hover\">'
        			html1 += '<thead id=\"outstandingThead\">'
        			html1 += '<tr>'
        			html1 += '<th > Date</th>'
        			html1 += '<th > Client Name</th>'
        			html1 += '<th style="text-align: right"> Amount</th>'
        			html1 += '<th> Form No</th>'
        			html1 += '<th> Status</th>'
        			html1 += '<th> Verify</th>'
        			/*html1 += '<th style="text-align: right"> Balance</th>'
        			html1 += '<th style="text-align: right"> Credit Terms</th>'*/
        			html1 += '</tr>'
        			html1 += '</thead>'
        			html1 += '<tbody id=\"outstandingTbody\">'
        			html1 += '<tr>'
        			html1 += '</tr>'
        			html1 += '</tbody>'
        			html1 += '</table>'
        			html1 += '</div>'
        			$("#outstanding").append(html1);
            		$("#outstandingTbody").empty();

            		
	            data = response.data;
	            console.log("hello    hello")
	            console.log(data)
	            var ttlAmt = 0.0;
	            var tr = "";
	            for (var i = 0; i < data.length; i++) {
	            	var clientName = data[i].clientTitle;
					var recivedDate = data[i].receivedDate;
					recivedDate = common.getFormattedDate(new Date(
							recivedDate));
					var amount = data[i].receivedAmt;
					var subTotAmt = data[i].subTotal;
					var prntSubTot = data[i].prntSubTot;
					var invoiceNo = data[i].invoiceNo;
					var status = data[i].status;
					
					tr += "<tr>";
					tr += "<td>" + recivedDate + "</td>";
					tr += "<td>" + clientName + "</td>";
					tr += "<td style='text-align: right'>$" + amount.toFixed(2)  + "</td>";
					tr += "<td>" + invoiceNo + "</td>";
					tr += "<td>" + status + "</td>";
					tr += "<td>" + "Y" + "</td>";
					tr += "</tr>";
					
					if(prntSubTot == "true") {
						tr += "<tr>";
						tr += "<td>" + " " + "</td>";
						tr += "<td>" + " " + "</td>";
						tr += "<td style='text-align: right'>Total: " + "$" + subTotAmt.toFixed(2)  + "</td>";
						tr += "</tr>";
					}
					
					ttlAmt += parseFloat(data[i].receivedAmt);
	            }
	        
	            //$("#outstandingTbody").append(tr);
	            $("#outstandingTbody").append(tr,'<tr>'+
	            	    '<td class="td">Grand Total</td>'+
	            	    '<td></td>'+
	            	    '<td class="td" style="text-align:right">'+ttlAmt.toFixed(2)+'</td>'+
	                '</tr>');
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    }); 
	 }
	
	/*this.customPrintPdf = function (thisObj){
		$("#outstanding").printMe(
		{
			"path" : [ "css/compiled/theme_styles.css",
					"css/bootstrap/bootstrap.min.css",
					"css/mystyle2.css", "css/footer.css" ]

		});
	 }*/
	this.customPrintPdf = function (thisObj){
		$("#outstanding").printMe(
		{
			"path" : [ "css/compiled/theme_styles.css",
					"css/bootstrap/bootstrap.min2.css",
					"css/mystyle2.css", "css/footer.css" ]

		});
	 }
	/*this.customPrintPdf = function (thisObj){
		var fromdate = $('#fromdate').val();
		var todate = $('#todate').val();
		alert("Print Button Clicked");
		 window.open('download/paymentBetweenDates/monthpaymentReport.pdf/pdf/'+fromdate+'/'+todate, '_blank');
	 }*/
	 this.customPrintXls = function (thisObj){
		 var fromdate = $('#fromdate').val();
		 var todate = $('#todate').val();
		 window.open('download/pymtbilling/monthpaymentReport.xls/xls/'+fromdate+'/'+todate, '_blank');
	 }
	 /*
	 this.customPrintXls = function (thisObj){
		 var fromdate = $('#fromdate').val();
		 var todate = $('#todate').val();
		 window.open('download/paymentBetweenDates/monthpaymentReport.xls/xls/'+fromdate+'/'+todate, '_blank');
	 }
	  */
	 this.customEmail = function (thisObj){
		 var fromdate = $('#fromdate').val();
		 var todate = $('#todate').val();
		 window.open('email/pymtBillingReport/monthpaymentReport.pdf/'+fromdate+'/'+todate, '_blank');
	 }
}