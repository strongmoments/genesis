
var OUTBALREPORT =function (){
	var invoicePaymentList = new Array();
	
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>Outstanding Bill Enquiry</h1>'
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
		html += '<label>Client ID</label>'
		html += '<select class=\"form-control\" id=\"clientInfo\">'
		html += '<option>All Client</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"btn btn-primary  mr-10\" onclick=\"outbalreport.loadPaymentByClient();\" >Load </button>'
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
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="outbalreport.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="outbalreport.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="outbalreport.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
			html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
          $("#content-wrapper").append(html);	
      wso1.loadAllClient();
        }
	
	this.loadPaymentByClient = function(){
		var clientInfo = $("#clientInfo").val();
		if(clientInfo == "" || clientInfo == undefined || clientInfo == null){
			bootAlert.show("error", "Please Select client");
			 return false;
		}
		var jsondata = {"clientId":clientInfo}
        $.ajax({
            type: "GET",
            //contentType: "application/json",
            url: contextPath+ "/payments/outstandingBillEnquiry",
            data: jsondata,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (response) {
            	data = response.data;
            	data1 = response.data1;
            	data2 = response.data2;
            	console.log(data)
            	console.log(data1)
            	console.log(data2)
            	if(data.length != 0){
            		$("#outstanding").empty();
            		var html1 = ""
            		html1 += '<div class=\"main-box-body clearfix\">'
            			html1 += '<div class="row">'
            			html1 += '<div class="col-lg-6"><b>Client Name : '+data2.clientId+'</b></div>'
            			html1 += '<div class="col-lg-3"><b>Credit Balance : $'+data1.ttlBalanceAmount.toFixed(2)+'</b></div></div>'
            			html1 += '<div class="row">'
            			html1 += '<div class="col-lg-6"><b>O/s investment : $'+data1.ttlInvoiceAmount.toFixed(2)+'</b></div>'
            			if(data1.lastBillDate==null){
            				html1 += '<div class="col-lg-6"><b>Last Payment : $'+data1.lastBillPay.toFixed(2)+'</b></div></div>'
            			}
            			else
            			{
            				html1 += '<div class="col-lg-6"><b>Last Payment : $'+data1.lastBillPay.toFixed(2)+' on '+data1.lastBillDate+'</b></div></div>'
            			}
            			
            			/*html1 += '<p>Contact Phone No.: '+data2.contactPhone+'</p>'*/
            			html1 += '<div class="row">'
            			html1 += '<div class="col-lg-3"><b>Contact No : '+data2.contactMobile+'</b></div></div>'
            			html1 += '<table class=\"table table-hover\">'
            			html1 += '<thead id=\"outstandingThead\">'
            			html1 += '<tr>'
            			html1 += '<th style="text-align: right;width:50px;"> Invoice</th>'
            			html1 += '<th style="text-align: center;"> Date</th>'
            			html1 += '<th style="text-align: right;"> Invoice Amount</th>'
            			html1 += '<th style="text-align: right"> Balance</th>'
            			html1 += '<th style="text-align: right"> Credit Terms</th>'
            			html1 += '</tr>'
            			html1 += '</thead>'
            			html1 += '<tbody id=\"outstandingTbody\">'
            			html1 += '<tr>'
            			html1 += '</tr>'
            			html1 += '</tbody>'
            			html1 += '</table>'
            			html1 += '</div>'
        				
            			$("#outstanding").append(html1);
            		$("#printMePdf").show();
            		$("#printMeXls").show();
            		$("#sendEmail").show();
	            	$(".imgclass").show();
            		$("#outstandingTbody").empty();
		            var tr="";
		            for(var i=0; i<data.length; i++){
		            	var balanceAmount = data[i].balanceAmount;
		                var creditTerms  = 30 ;
		                var invoiceAmount = data[i].invoiceAmount;
		                var invoiceNo  = data[i].invoiceNo;
		                var invoiceDate  = data[i].invoiceDate;
		                if(!invoiceDate==''){
		                	invoiceDate= common.getFormattedDate(new Date(invoiceDate));
		                }
		                
		                tr += "<tr>";
		                tr+= "<td style='text-align: right'>"+invoiceNo+"</td> <td style='text-align: center;'> "+invoiceDate+" </td><td style='text-align: right'>$"+invoiceAmount.toFixed(2)+"</td><td style='text-align: right'>$"+balanceAmount.toFixed(2)+"</td></td><td style='text-align: right'>"+creditTerms+"</td>";
		                tr += "</tr>";
		            }
		            $("#outstandingTbody").append(tr);
            	}else{
            		$("#outstanding").empty();
            		var html1 = ""
            		html1 += '<div class=\"main-box-body clearfix\">'
            			html1 += '<table class=\"table table-hover\">'
            			html1 += '<thead id=\"outstandingThead\">'
            			html1 += '<tr>'
            			html1 += '<th> Invoice</th>'
            			html1 += '<th> Date</th>'
            			html1 += '<th> Invoice Amount</th>'
            			html1 += '<th> Balance</th>'
            			html1 += '<th> Credit</th>'
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
		            var tr="";
		                tr += "<tr>";
		                tr+= "<td>No Data Available</td> <td></td><td></td><td></td></td><td></td>";
		                tr += "</tr>";
		            $("#outstandingTbody").append(tr);
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
	this.customPrintPdf = function(thisObj) {
		/*alert("hello");*/
		clientInfo = $("#clientInfo").val();
		clientInfo = parseInt(clientInfo);
		window.open('download/outstandingBill/outStandingReport.pdf/pdf/'+clientInfo, '_parent');
		
		}
	this.customPrintXls = function(thisObj) {
		/*alert("hello");*/
		clientInfo = $("#clientInfo").val();
		clientInfo = parseInt(clientInfo);
		window.open('download/outstandingBill/outStandingReport.xls/xls/'+clientInfo, '_parent');
		
		}
	this.customEmail = function (thisObj){
		clientInfo = $("#clientInfo").val();
		clientInfo = parseInt(clientInfo);
		window.open('email/outstandingBill/outStandingReport.pdf/pdf/'+clientInfo, '_parent');
	 }
}