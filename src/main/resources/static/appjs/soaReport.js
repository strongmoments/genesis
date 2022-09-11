
var SOAREPORT =function (){

	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>STATEMENT OF ACCOUNT</h1>'
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
		html += '<option>Select Client</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>To Client ID</label>'
		html += '<select class=\"form-control\" id=\"toClientInfo\">'
		html += '<option>Select Client</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
					
		
			
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"btn btn-primary  mr-10\" onclick=\"soareport.loadPaymentByClient();\" >Load Payment</button>'
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
		html += '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'
		html += '<div class="col-12-lg imgclass" style="display:none">'
		html += '<center><img src="image/logo4.png" class="logo " style="width:750px;"><br><br><span class="tax">OutGoing Records</span><br></center>'
		html += '<hr>'
		html += '</div>'
		html += '<div class=\"main-box outstandingTable\" id=\"outstanding\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"outstandingThead\">'
		/*html += '<tr>'
		html += '<th> INVOICE DATE</th>'
		html += '<th> INVOICE NUMBER</th>'
		html += '<th> CREDIT TERMS</th>'
		html += '<th> DOCUMENT DATE</th>'
		html += '<th> PAYMENT/CREDIT NOTE</th>'
		html += '<th> DAYS OVER DUE</th>'
		html += '<th> DEBIT (S)</th>'
		html += '<th> CREDIT (S)</th>'
		html += '<th> BALANCE</th>'
		html += '</tr>'*/
		html += '</thead>'
		html += '<tbody id=\"outstandingTbody\">'
		html += '<tr>'
		html += '</tr>'
		html += '</tbody>'
		html += '</table>'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"outstandingThead\">'
		/*html += '<tr>'
		html += '<th> CURRENT</th>'
		html += '<th> OVERDUE 1 - 30 DAYS</th>'
		html += '<th> OVERDUE 31 - 60 DAYS</th>'
		html += '<th> OVERDUE 61 - 90 DAYS</th>'
		html += '<th> OVERDUE 91 - 120 DAYS</th>'
		html += '<th> OVERDUE > 120 DAYS</th>'
		html += '<th> TOTAL</th>'
		html += '<th> CREDIT (S)</th>'
		html += '<th> BALANCE)</th>'
		html += '</tr>'*/
		html += '</thead>'
		html += '<tbody id=\"outstandingTbody\">'
		html += '<tr>'
		html += '</tr>'
		html += '</tbody>'
		html += '</table>'
		html += '<div class="row">'
        /*html += '<div class="col-lg-3" style="font-size:16px;"><b>CREDIT BALANCE :</b></div></div>'*/	
		html += '</div>'
		
		html +='</div>'
			
		html += '</div>'
			html += '</form>'
		html += '</div>' 
		
		
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="soareport.customPrint1();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none;margin-left:20px;" id="printMeXls" onclick="soareport.customPrint2();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="soareport.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
          $("#content-wrapper").append(html);	
		billing.getClientName();
        }
	
	this.loadPaymentByClient = function(){
		var fromClient = $("#fromClientInfo").val();
		var toClient = $("#toClientInfo").val();
		if(fromClient == "" || fromClient == undefined || fromClient == "Select Client" || toClient == "" || toClient == undefined || toClient == "Select Client"){
			bootAlert.show("error", "Please Select From Client to To Client");
			return false;
		}
		var jsondata = {'fromClient':fromClient,"toClient":toClient}
        $.ajax({
            type: "GET",
            //contentType: "application/json",
            url: contextPath+ "/soa/soaEnquiry",
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
            	console.log("Value of data2:"+data2)
            	$("#outstanding").empty();
            	for(var j=0; j<data.length; j++){
            		//var tempArray = new Array();
            		if(data[j].length > 0){
            			$(".imgclass").show();
	        			$("#printMePdf").show();
	        			$("#printMeXls").show();
	        			$("#sendEmail").show();
                		var html1 = ""
                		html1 += '<div class=\"main-box-body clearfix\">'
                			html1 += '<div class="row">'
                			html1 += '<div class="col-lg-6"><b>Client Name : '+data1[j].clientId+'</b></div>'
                    		html1 += '<div class="row">'
                        	html1 += '<div class="col-lg-3"><b>Contact No : '+data1[j].contactMobile+'</b></div></div>'
                			html1 += '<table class=\"table table-hover\">'
                			html1 += '<thead id=\"outgoingThead\">'
                			html1 += '<tr>'
                        	html1 += '<th> INVOICE DATE</th>'
                        	html1 += '<th> INVOICE NUMBER</th>'
                        	html1 += '<th> CREDIT TERMS</th>'
                        	html1 += '<th> DOCUMENT DATE</th>'
                        	html1 += '<th> PAYMENT/CREDIT NOTE</th>'		
                        	html1 += '<th> DAYS OVER DUE</th>'
                            html1 += '<th> DEBIT (S)</th>'
                            html1 += '<th> CREDIT (S)</th>'
                            html1 += '<th> BALANCE</th>'
                            html1 += '</tr>'
                			html1 += '</thead>'
                			html1 += '<tbody id=\"outstandingTbody'+j+'\">'
                			//html1 += '<tbody id="outgoingTbody'+i+'">'
                			html1 += '<tr>'
                			html1 += '</tr>'
                			html1 += '</tbody>'
                			html1 += '</table>'
                			html1 += '</div>'
                			$("#outstanding").append(html1);
                		
                		//$("#outgoingTbody"+i).empty();
                		$("#outstandingTbody"+j).empty();
    		            var tr="";
    		            for(var i=0,k=j; i<data[k].length; i++){
    		            	var invoiceDate = data[k][i].invoiceDate;
    		            	invoiceDate= common.getFormattedDate(new Date(invoiceDate));
    		                var invoiceNo  = data[k][i].invoiceNo ;
    		                var creditTerms = data[k][i].creditTerms;
    		                var documentDate  = data[k][i].documentDate;
    		                var paymentCreditNote  = data[k][i].paymentCreditNote;
    		                var daysOverDue  = data[k][i].daysOverDue;
    		                var debit  = data[k][i].debit;
    		                var credit  = data[k][i].credit;
    		                var balanceAmount  = data[k][i].balanceAmount;
    		                tr += "<tr>";
    		                tr+= "<td>"+invoiceDate+"</td> <td> "+invoiceNo+" </td><td>"+creditTerms+"</td><td>"+documentDate+"</td><td>"+paymentCreditNote+"</td><td>"+daysOverDue+"</td><td>$"+debit.toFixed(2)+"</td><td>$"+credit.toFixed(2)+"</td><td>$"+balanceAmount.toFixed(2)+"</td>";
    		                tr += "</tr>";
    		            }
    		            var current = data2[j].current;
    		            var overdue1 = data2[j].overdue30;
    		            var overdue2 = data2[j].overdue60;
    		            var overdue3 = data2[j].overdue90;
    		            var overdue4 = data2[j].overdue120;
    		            var overdue5 = data2[j].overdueMore120;
    		            var total = overdue1+overdue2+overdue3+overdue4+overdue5;
    		            var creditBalance = total + current;
    		            tr += '<tr>'
		                tr += '<th> CURRENT</th>'
		                tr += '<th> OVERDUE 1 - 30 DAYS</th>'
		                tr += '<th> OVERDUE 31 - 60 DAYS</th>'
		                tr += '<th> OVERDUE 61 - 90 DAYS</th>'
            			tr += '<th> OVERDUE 91 - 120 DAYS</th>'
            			tr += '<th> OVERDUE > 120 DAYS</th>'
            			tr += '<th> TOTAL</th>'
    					tr += '</tr>'
    					tr += "<tr>";
		                tr += "<td>$"+current.toFixed(2)+"</td> <td> $"+overdue1.toFixed(2)+" </td><td>$"+overdue2.toFixed(2)+"</td><td>$"+overdue3.toFixed(2)+"</td><td>$"+overdue4.toFixed(2)+"</td><td>$"+overdue5.toFixed(2)+"</td><td>$"+total.toFixed(2)+"</td>";
		                tr += "</tr>";	
		                tr += '<tr><td colspan="2"><b>CREDIT BALANCE: </b>$'+creditBalance.toFixed(2)+'</td></tr>'
    		            $("#outstandingTbody"+j).append(tr);
    		            //$("#outstandingT body").append(tr);
                	}
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
	this.customPrint1 = function() {
		/*alert("hello");*/
		fromClientInfo = $("#fromClientInfo").val();
		fromClientInfo = parseInt(fromClientInfo);
		toClientInfo = $("#toClientInfo").val();
		toClientInfo = parseInt(toClientInfo);
		window.open('download/soa/soaReport.pdf/pdf/'+fromClientInfo+'/'+toClientInfo, '_parent');
		
		}
	this.customPrint2 = function() {
		/*alert("hello");*/
		fromClientInfo = $("#fromClientInfo").val();
		fromClientInfo = parseInt(fromClientInfo);
		toClientInfo = $("#toClientInfo").val();
		toClientInfo = parseInt(toClientInfo);
		window.open('download/soa/soaReport.xls/xls/'+fromClientInfo+'/'+toClientInfo, '_parent');
		
		}
	this.customEmail = function (thisObj){
		fromClientInfo = $("#fromClientInfo").val();
		fromClientInfo = parseInt(fromClientInfo);
		toClientInfo = $("#toClientInfo").val();
		toClientInfo = parseInt(toClientInfo);
		window.open('email/soa/soaReport.pdf/pdf/'+fromClientInfo+'/'+toClientInfo, '_parent');
	 }
	/*this.customPrint = function(thisObj) {
		var invoiceNo = $(thisObj).attr("data");
		window.open('download/common/commonbilling.pdf/'+invoiceNo);
		// $("#printJS-form").printMe();
		$("#printMe").hide();
		$("#printJS-form").printMe(
				{
					"path" : [ "css/compiled/theme_styles.css",
							"css/bootstrap/bootstrap.min.css",
							"css/mystyle2.css", "css/footer.css" ]

				});
		$("#printMe").show();
	}*/

}