var WSOENREPORT = function() { 
	this.show = function() {
        $("#content-wrapper").empty();
		var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>WSO ENQUIRY</h1>'
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
		html += '<label>From WSO</label>'
		html += '<select class=\"form-control\" id=\"fromWsoNo\">'
		html += '<option>Select Wso</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>To Wso</label>'
		html += '<select class=\"form-control\" id=\"toWsoNo\">'
		html += '<option>Select Wso</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
			
		html += '<div class=\"row\">'		
		html += '<div class=\"col-lg-3\">'
		html += '<div class=\"form-group\">'
		html += '<label></label>'
		html += '<button type=\"button\" class=\"btn btn-info  mr-10 form-control" onclick="wsoenreport.wsoInquiryByWso();" style="margin-top:25px;" >Load</button>'
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
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="wsoenreport.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		/*html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="wsoenreport.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="wsoenreport.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'*/
          $("#content-wrapper").append(html);	
		wsoenreport.loadAllWsoNo();
		//billing.getClientName();
    
	}
	
	
	this.wsoInquiryByWso = function(){
		var fromWsoId = $("#fromWsoNo").val();
		var toWsoId = $("#toWsoNo").val();
		
		var jsondata = {'fromWsoId':fromWsoId,"toWsoId":toWsoId}
	    $.ajax({
	        type: "GET",
	        //contentType: "application/json",
	        url: contextPath+ "/wsoInfos/wsoInquiryByWso",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	            data = response.data;
	        	console.log(data)
	        	$("#outstanding").empty();
	        	data1 = response.data1;
	        	var currentWsoQty = 0.0;
	        	console.log(data1);
	        	//console.log("Length of data1 is: "+data1.length);
	        	currentWsoQty = data1;
	        	
	        	for(var i=0; i<data.length; i++){
	        		if(data[i].length > 0){
	        			
	        			$(".imgclass").show();
	        			$("#printMePdf").show();
	        			$("#printMeXls").show();
	        			$("#sendEmail").show();
	        			var html1 = ""
			    	    html1 += '<div class=\"main-box-body clearfix\">'
	        			var wsoNo = data[i][0].wsoNo;
	        			var totalWsoWeight = data[i][0].totalWsoWeight.toFixed(0);
	        			var storageDate = data[i][0].storageDate;
	        			storageDate= common.getFormattedDate(new Date(storageDate));
	        			var currentWsoQty1 = data[i][0].currentWsoQty;
	        			var currentWsoWt = data[i][0].currentWsoWt.toFixed(0);
	        			var ttlWsoQty = data[i][0].ttlWsoQty;
	        			var clientName = data[i][0].clientName;
	        			
	        			html1 += '<div class="row">'
		        		html1 += '<div class="col-lg-3 werdiv"><b>WSO No : '+wsoNo+'</b></div>'
		        		html1 += '<div class="col-lg-3"><b>Date Stored : '+storageDate+'</b></div><div>'
		        		html1 += '<div class="row">'
		        		html1 += '<div class="col-lg-3 werdiv"><b>Total Wso Weight : '+totalWsoWeight+'</b></div>'
		        		html1 += '<div class="col-lg-3" style="font-size:16px;"><b>Total Wso Quantity : '+ttlWsoQty+'</b></div></div>'
		        		html1 += '<div class="row">'
		        		html1 += '<div class="col-lg-3 werdiv"><b>Current Weight : '+currentWsoWt+'</b></div>'
		        		html1 += '<div class="col-lg-3"><b>Current Quantity : '+currentWsoQty+'</b></div><div class="col-lg-6"><b>ClientName :'+clientName+'</b></div><div></div>'
		        		html1 += '<div class="row">'
		        		html1 += '<table class=\"table table-hover\ dltable">'
		        		html1 += '<thead id=\"outgoingThead\">'
		        		html1 += '<tr>'
		        		html1 += '<th>DL NO</th>'
		        		html1 += '<th>Date</th>'
		        		html1 += '<th>LOT NO</th>'
		        		html1 += '<th style="text-align: right">No Of Packages Out</th>'
		        		html1 += '</tr>'
		        		html1 += '</thead>'
		        		html1 += '<tbody id="outgoingTbody'+i+'">'
		        		html1 += '</tbody>'
		        		html1 += '</table>'
		        		html1 += '<hr style="border: 0;border-top: 1px solid #8c8c8c;border-bottom: 1px solid #fff;">'
		        		html1 += '</div>'
		    	        $("#outstanding").append(html1);
		        		
	        		}
	        		
	        		$("#outgoingTbody"+i).empty();	
	        		var tr="";
	        		var ttlpackagesOut = 0;
	        		for(j=0; j<data[i].length;j++){
	        			var dl = data[i][j].dlNo;
	        			var dateOfDelivery = data[i][j].dateOfDelivery;
	        			dateOfDelivery= common.getFormattedDate(new Date(dateOfDelivery));
	        			var lotNo = data[i][j].lotNo;
	        			var packagesOut = data[i][j].packagesOut;
		                tr += "<tr>";
		                tr +="<td >"+dl+"</td> <td > "+dateOfDelivery+" <td > "+lotNo+" </td> <td style='text-align: right'> "+packagesOut+" </td>";
		                tr += "</tr>";    
		                ttlpackagesOut += packagesOut;
	        		}
	        	
	        		//$("#outgoingTbody"+i).append(tr);
	        		$("#outgoingTbody"+i).append(tr,'<tr>' +
	    	    		    '<td>Total :</td>' +
	    	    		    '<td class="td"></td>' +
	    	    		    '<td class="td"></td>' +
	    	    		    '<td class="td" style="text-align: right">'+ttlpackagesOut+'</td>' +
	    	    		    '</tr>');
	        		ttlpackagesOut = 0;
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
	
	this.loadAllWsoNo = function(){
		$.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/wsoInfos/",
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	console.log(data)
	        	for (var i = 0; i < data.length; i++) {
					$('#fromWsoNo').append(
							"<option value=" + data[i].wsoId + ">"
									+ data[i].wsoNo + "</option>");
					$('#toWsoNo').append(
							"<option value=" + data[i].wsoId + ">"
									+ data[i].wsoNo + "</option>");
				}
	        },error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
	}
	
	this.customPrintPdf = function() {
		/*alert("hello");*/
		fromWsoNo = $("#fromWsoNo").val();
		fromWsoNo = parseInt(fromWsoNo);
		toWsoNo = $("#toWsoNo").val();
		toWsoNo = parseInt(toWsoNo);
		window.open('download/wsoenq/wsoEnquiryReport.pdf/pdf/'+fromWsoNo+'/'+toWsoNo, '_parent');
		
		}
	this.customPrintXls = function() {
		/*alert("hello");*/
		fromWsoNo = $("#fromWsoNo").val();
		fromWsoNo = parseInt(fromWsoNo);
		toWsoNo = $("#toWsoNo").val();
		toWsoNo = parseInt(toWsoNo);
		window.open('download/wsoenq/wsoEnquiryReport.xls/xls/'+fromWsoNo+'/'+toWsoNo, '_parent');
		
		}
	this.customEmail = function (thisObj){
		fromWsoNo = $("#fromWsoNo").val();
		fromWsoNo = parseInt(fromWsoNo);
		toWsoNo = $("#toWsoNo").val();
		toWsoNo = parseInt(toWsoNo);
		window.open('email/wsoenq/wsoEnquiryReport.pdf/pdf/'+fromWsoNo+'/'+toWsoNo, '_parent');
	 }
	/*this.customPrint = function(thisObj) {
		var fromWsoId = $("#fromWsoNo").val();
		var toWsoId = $("#toWsoNo").val();
		 
		fromWsoId = parseInt(fromWsoId);
		toWsoId = parseInt(toWsoId);
			
			window.open('download/wsoEnquiry/wsoEnquiryReport.pdf/'+fromWsoId+'/'+toWsoId, '_parent');
			// $("#printJS-form").printMe();
			$("#printJS-form").printMe(
					{
						"path" : [ "css/compiled/theme_styles.css","css/bootstrap/bootstrap.min.css","css/mystyle2.css", "css/footer.css" ]

					});
		}*/
	
}