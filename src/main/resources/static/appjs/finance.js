
var FINREPORT =function (){
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>Summarised Apportionment  By Class</h1>'
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
		html += '<button type=\"button\" class=\"btn btn-info  mr-10\" onclick=\"finreport.loadStorageReport();\" >Load</button>'
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
		html += '<div class=\"main-box billingTable\" id=\"finance\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'
		html += '<div class="col-12-lg imgclass" style="display:none">'
		html += '<center><img src="image/logo4.png" class="logo " style="width:750px;"><br><br><span class="tax">Finance Records</span><br></center>'
		html += '<hr>'
		html += '</div>'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"finThead\">'
		/*html += '<tr>'
		html += '<th>Charge Item </th>'
		html += '<th style="text-align: right">Revenue</th>'
		html += '</tr>'*/
		html += '</thead>'
		html += '<tbody id=\"finTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="finreport.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="finreport.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="finreport.customEmail(this);">' +
            'Send Email'+
            '<span class="glyphicon glyphicon-send">'+'</span>'+
            '</button>'
		html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
		
          $("#content-wrapper").append(html);	
        
      
        }
	
	// Start Load charges and Storage
	 this.loadStorageReport = function(){
		 
		 var fromdate = $("#fromdate").val();
		 var todate = $("#todate").val();
		 
		 if(fromdate == undefined || fromdate == null || fromdate == ""){
			 bootAlert.show("error", "Please Select From date");
			 return false;
		 }
		 
		 if(todate == undefined || todate == null || todate == ""){
			 bootAlert.show("error", "Please Select to date");
			 return false;
		 }
		 
		 if(Date.parse(todate) < Date.parse(fromdate)){
		        bootAlert.show("error","to Date can't be less than to from date......");
		        return false;
		    }
		 var jsondata = {'fromdate': fromdate,'todate':todate}
		   $.ajax({
		       type: "POST",
		       /*contentType: "application/json",*/
		       url: contextPath+ "/billings/getStorageReport/",
		       data : jsondata,
		       dataType: 'json',
		       cache: false,
		       timeout: 600000,
		       success: function (response) {
		           data = response.data;
		           data1 = response.data1;
		           grandCredit = response.grandCredit;
		           grandCreditAmount = response.grandCreditAmount;
		           grandCreditGst = response.grandCreditGst;
		           console.log(data)
		           console.log(data1)
		           console.log(grandCredit)
		           console.log(grandCreditAmount)
		           console.log(grandCreditGst)
		           //alert("success1");
		           $("#printMePdf").show();
		           $("#printMeXls").show();
		           $("#sendEmail").show();		           
		           $(".imgclass").show();
		           $("#finTbody").empty();
		           var subtotal = 0.00;
		           var totalgst = 0.00;
		           var total = 0.00;
		            var tr="";
		            for(var i=0; i<data.length; i++){
		            	
		                var storage = data[i][0];
		                var netAmount  = data[i][1] ;
		                tr += "<tr>";
		                tr+= "<td>"+storage+"</td> <td style='text-align: right'> "+netAmount.toFixed(2)+" </td>";
		                tr += "</tr>";
		                totalgst += parseFloat(data[i][3]);
		                subtotal += parseFloat(netAmount);
		            }
		            /*for(var i=0; i<data.length; i++){
		                var storage = data[i][0];
		                var handlingCharges  = data[i][2] ;
		                tr += "<tr>";
		                tr+=   "<td>"+storage+"-Charges</td> <td style='text-align: right'> "+handlingCharges.toFixed(2)+" </td>";
		                tr += "</tr>";
		                subtotal += parseFloat(handlingCharges);
		            }*/
		            for(var i=0; i<data1.length; i++){
		                var chargeItem = data1[i][0];
		                var netAmount  = data1[i][1] ;
		                tr += "<tr>";
		                tr+=   "<td>"+chargeItem+"</td> <td style='text-align: right'> "+netAmount.toFixed(2)+" </td>";
		                tr += "</tr>";
		                totalgst += parseFloat(data1[i][3]);
		                subtotal += parseFloat(netAmount);
		                //subtotal += parseFloat(handlingCharges);
		            }
		            $("#finTbody").append(tr);
		            total = subtotal - grandCreditAmount - grandCreditGst;
		            
		            if(parseFloat(total) > 0){
		            $("#finTbody").append('<tr>'+
		            	    '<td class="td"><b>Sub Total</b></td>'+
		            	    '<td class="td" style="text-align: right"><b>'+subtotal.toFixed(2)+'</b></td>'+
		                '</tr>'+
		                //'<tr>'+
	            	    //	'<td class="td"><b>Total Credit Amount</b></td>'+
	            	    //	'<td class="td" style="text-align: right"><b>'+grandCreditAmount.toFixed(2)+'</b></td>'+
	            	    //'</tr>'+
		                '<tr>'+
		            	    '<td class="td"><b>Total Gst</b></td>'+
		            	    '<td class="td" style="text-align: right"><b>'+totalgst.toFixed(2)+'</b></td>'+
	                	'</tr>'+
	                	
	            	    //'<tr>'+
		            	//    '<td class="td"><b>Total Credit Gst</b></td>'+
		            	//    '<td class="td" style="text-align: right"><b>'+grandCreditGst.toFixed(2)+'</b></td>'+
		            	//'</tr>'+
		            	'<tr>'+
		            	    '<td class="td"><b>Grand Total</b></td>'+
		            	    '<td class="td" style="text-align: right"><b>'+total.toFixed(2)+'</b></td>'+
	                	'</tr>');
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
	// End Load charges and Storage
	 
	 this.customPrintPdf = function(thisObj) {
		 var fromdate = $("#fromdate").val();
		 var todate = $("#todate").val();
			/*var invoiceNo = $(thisObj).attr("data");*/
			window.open('download/finance/financeReport.pdf/pdf/'+fromdate+'/'+todate, '_parent');
			// $("#printJS-form").printMe();
			/*$("#printJS-form").printMe(
					{
						"path" : [ "css/compiled/theme_styles.css",
								"css/bootstrap/bootstrap.min.css",
								"css/mystyle2.css", "css/footer.css" ]

					});*/
		}
	 this.customPrintXls = function(thisObj) {
		 var fromdate = $("#fromdate").val();
		 var todate = $("#todate").val();
			/*var invoiceNo = $(thisObj).attr("data");*/
			window.open('download/finance/financeReport.xls/xls/'+fromdate+'/'+todate, '_parent');
			// $("#printJS-form").printMe();
			/*$("#printJS-form").printMe(
					{
						"path" : [ "css/compiled/theme_styles.css",
								"css/bootstrap/bootstrap.min.css",
								"css/mystyle2.css", "css/footer.css" ]

					});*/
		}
	 this.customEmail = function (thisObj){
		 var fromdate = $("#fromdate").val();
		 var todate = $("#todate").val();
		 window.open('email/finance/financeReport.pdf/pdf/'+fromdate+'/'+todate, '_blank');
	 }
}