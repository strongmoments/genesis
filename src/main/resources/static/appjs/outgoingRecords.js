var OUTRECREPORT =function (){
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>OUTGOING RECORDS</h1>'
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
		html += '<label>From Client ID</label>'
		html += '<select class=\"form-control\" id=\"fromClientInfo\">'
		html += '<option>All Client</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>To Client ID</label>'
		html += '<select class=\"form-control\" id=\"toClientInfo\">'
		html += '<option>All Client</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
			
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>From Date</label>'
		html += '<input type="date" class=\"form-control\" id=\"fromDate\">'
	
		html += '</input>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>To Date</label>'
		html += '<input type="date" class=\"form-control\" id=\"toDate\">'
		
		html += '</input>'
		html += '</div>'
		html += '</div>'
		
			
		html += '<div class=\"col-lg-3\">'
		html += '<div class=\"form-group\">'
		html += '<label></label>'
		html += '<button type=\"button\" class=\"btn btn-info  mr-10 form-control" onclick="outrecreport.loadAllOutGoingReport();" style="margin-top:25px;" >Load</button>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'
		html += '<div class="col-12-lg imgclass" style="display:none">'
		html += '<center><img src="image/logo4.png" class="logo " style="width:750px;"><br><br><span class="tax">OutGoing Records</span><br></center>'
		html += '<hr>'
		html += '</div>'
		html += '<div class=\"main-box billingTable\" id=\"outstanding\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"finThead\">'
		/*html += '<tr>'
		html += '<th>Invoice No</th>'
		html += '<th>Date</th>'
		html += '<th>Invoice Amount</th>'
		html += '<th>Balance</th>'
		html += '<th>Credit</th>'
		html += '</tr>'*/
		html += '</thead>'
		html += '<tbody id=\"finTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
		html += '</div>'
		html += '</form>'
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="outrecreport.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="outrecreport.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="outrecreport.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
          $("#content-wrapper").append(html);	
		
		billing.getClientName();
        }
	
	//Start load dl by client id and date
	this.loadAllOutGoingReport =function(){
		
		var fromClient = $("#fromClientInfo").val();
		var toClient = $("#toClientInfo").val();
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		
		if(fromClient == "" || fromClient == undefined || fromClient == "Select Client" || toClient == "" || toClient == undefined || toClient == "Select Client"){
			bootAlert.show("error", "Please Select From Client to To Client");
			return false;
		}
		
		if(fromDate == "" || fromDate == undefined || toDate == "" || toDate == undefined ){
			bootAlert.show("error", "Please Select From Date to To Date");
			return false;
		}
		
		var jsondata = {'fromClient':fromClient,"toClient":toClient,"fromDate":fromDate,"toDate":toDate}
		$.ajax({
	        type: "GET",
	        //contentType: "application/json",
	        url: contextPath+ "/deliveryLists/loadDeliveryBetweenDatesReport",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	data = response.data
	        	console.log(data);
	        	//alert("success");
	        	
	        	$("#outstanding").empty();
	        
	        	
	        	for(var i=0; i<data.length; i++){
	        		if(data[i].length > 0){
	        			
	        			$(".imgclass").show();
	        			$("#printMePdf").show();
	        			$("#printMeXls").show();
	        			$("#sendEmail").show();
	        			var html1 = ""
			    	    html1 += '<div class=\"main-box-body clearfix\">'
	        			var clientTitle = data[i][0].clientTitle;
	        			
		        		html1 += '<p style="font-size:16px;"><b> ClientName : '+clientTitle+'</b></p>'
		        		html1 += '<table class=\"table table-hover\">'
		        		html1 += '<thead id=\"outgoingThead\">'
		        		html1 += '<tr>'
		        		html1 += '<th>DL NO</th>'
		        		html1 += '<th>Date</th>'
		        		html1 += '<th></th>'
		        		html1 += '<th>WSO NO</th>'
		        		html1 += '<th>LOT NO</th>'
		        		html1 += '<th style="text-align: right">Qty Out</th>'
		        		html1 += '<th style="text-align: right">WT</th>'
		        		html1 += '</tr>'
		        		html1 += '</thead>'
		        		html1 += '<tbody id="outgoingTbody'+i+'">'
		        		html1 += '</tbody>'
		        		html1 += '</table>'
		        	
		        		html1 += '</div>'
		    	        $("#outstanding").append(html1);
		        		//var clientTitle = data[i][0].clientTitle;
		        		//$("#clientName"+i).val(clientTitle);
		        		//console.log($("#clientName"+i).val(clientTitle));
	        		}
	        		
	        		var TttlQty = 0;
	        	    var TttlWt = 0;
	        	    var GTttlQty = 0;
	        	    var GTttlWt = 0;
	        		$("#outgoingTbody"+i).empty();	
	        		var tr="";
	        		var storeDl = "";
	        		for(j=0; j<data[i].length;j++){
	        			//$('#clientName').val(data[i][j].clientTitle);
	      
	        			var dl = data[i][j].dlNo;
	        			var dateOfDelivery = data[i][j].dateOfDelivery;
	        			dateOfDelivery= common.getFormattedDate(new Date(dateOfDelivery));
	        			var wsoNo = data[i][j].wsoNo;
	        			var lotNo = data[i][j].lotNo;
	        			var ttlQty = data[i][j].ttlQty;
	        			var ttlWt = data[i][j].ttlWt;
	        			
	        			
		                if(storeDl == ""){
		                	storeDl = dl;
		                }
		                if(dl != storeDl){
		                	storeDl = dl;
		                	if(TttlQty == 0|| TttlWt == 0){
		               
		                	}else{
		                	tr += "<tr><td></td><td></td><td><td></td></td><td><b>Total</td><td style='text-align: right'>"+TttlQty+"</td><td style='text-align: right'>"+TttlWt+"</td></b></tr>"
		                	tr += "<tr></tr>";
		                	
		                	TttlQty = 0;
			        	    TttlWt = 0;
		                	}
		                }
		                
	        			tr += "<tr>";
		                tr +="<td >"+dl+"</td> <td style='width: 300px;'> "+dateOfDelivery+" </td><td></td> <td style='width: 200px;'> "+wsoNo+"</td> <td style='width: 200px;'> "+lotNo+" </td> <td style='width: 200px;text-align: right'> "+ttlQty+" </td> <td style='width: 200px;text-align: right'> "+ttlWt+" </td> ";
		                tr += "</tr>";
		                TttlQty += parseFloat(ttlQty);
		  	          	TttlWt += parseFloat(ttlWt);
		  	          	
		  	          	GTttlQty += parseFloat(ttlQty);
		  	          	GTttlWt += parseFloat(ttlWt);
		  	          	
		  	          	if(j == data[i].length-1){
		  	          	tr += "<tr><td></td><td></td><td><td></td></td><td><b>Total</b></td><td style='text-align: right'>"+TttlQty+"</td><td style='text-align: right'>"+TttlWt+"</td></tr>"
		  	          	tr += "<tr><td></td><td></td><td><td></td></td><td><b>Grand Total</b></td><td style='text-align: right'>"+GTttlQty+"</td><td style='text-align: right'>"+GTttlWt+"</td></tr>"
	                	tr += "<tr></tr>";
	                	
	                	TttlQty = 0;
		        	    TttlWt = 0;
		        	    GTttlQty = 0;
		        	    GTttlWt = 0;
		  	          	}
	        		}
	        		$("#outgoingTbody"+i).append(tr);
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
	
	//End load dl by client id and date
	this.customPrintPdf = function() {
		$("#printJS-form").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});
		/*alert("hello");*/
		/*var fromClient = $("#fromClientInfo").val();
		fromClient = parseInt(fromClient);
		var toClient = $("#toClientInfo").val();
		toClient = parseInt(toClient);
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		
		window.open('download/outgoing/outgoingReport.pdf/pdf/'+fromClient+'/'+toClient+'/'+fromDate+'/'+toDate, '_parent');*/
		
		}
	this.customPrintXls = function() {
		/*alert("hello");*/
		var fromClient = $("#fromClientInfo").val();
		fromClient = parseInt(fromClient);
		var toClient = $("#toClientInfo").val();
		toClient = parseInt(toClient);
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		
		window.open('download/outgoing/outgoingReport.xls/xls/'+fromClient+'/'+toClient+'/'+fromDate+'/'+toDate, '_parent');
		
		}
	this.customEmail = function (thisObj){
		var fromClient = $("#fromClientInfo").val();
		fromClient = parseInt(fromClient);
		var toClient = $("#toClientInfo").val();
		toClient = parseInt(toClient);
		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		window.open('email/outgoing/outgoingReport.pdf/pdf/'+fromClient+'/'+toClient+'/'+fromDate+'/'+toDate, '_parent');
	 }
	/*this.customPrint = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		window.open('download/common/commonbilling.pdf/'+invoiceNo);
		// $("#printJS-form").printMe();
		$("#printJS-form").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});
	}*/
}