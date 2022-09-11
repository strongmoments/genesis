
var PAYREPORT =function (){
	var invoicePaymentList = new Array();
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
		html += '<div class=\"row delivery-style datesscroll \">'
		html += '<div class=\"col-lg-12\">'
		html += '<div class=\"row\">'
		html += '<div class=\"col-lg-12\">'
		html += '<h1>Payment Report</h1>'
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
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<div class=\"radio\">'
		html += '<input name=\"optionsRadios\" id=\"optionsRadios1\" type=\"radio\" checked value=\"C\">'
		html += '<label for=\"optionsRadios1\">'
		html += 'Credit Note'
		html += '</label>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		html += '<div class=\"radio\">'
		html += '<input name=\"optionsRadios\" id=\"optionsRadios2\" type=\"radio\"   value=\"P\">'
		html += '<label for=\"optionsRadios2\">'
		html += 'Payment Note'
		html += '</label>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"btn btn-primary  mr-10\" onclick=\"payreport.loadAllPayment();\" >Load Payment</button>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"btn btn-info  mr-10\" onclick=\"payreport.makePaymentPopup();\" data-toggle="modal">Make Payment</button>'
		html += '</div>'
		html += '<div class=\"col-lg-4\">'
		html += '<a  href=\"#\" class=\"btn btn-danger\">Exit</a>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"main-box billingTable\" id=\"payment\">'
		html += '<div class=\"main-box-body clearfix\">'
		html += '<table class=\"table table-hover\">'
		html += '<thead id=\"paymentThead\">'
		/*html += '<tr>'
		html += '<th><div class="checkbox-nice checkbox-inline"><input id="chkHeader" name="chkHeader" type="checkbox" onclick="payreport.checkUncheckAll()"><label for="checkbox-inl-1" >ALL</label></div></th>'
		html += '<th> Client Name</th>'
		html += '<th> Invoice No</th>'
		html += '<th style="text-align:right"> Total Amount</th>'
		html += '<th style="text-align:right"> Balance Amount</th>'
		html += '</tr>'*/
		html += '</thead>'
		html += '<tbody id=\"paymentTbody\">'
		html += '<tr>'
		html += '</tr>'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
		html += '<div class="modal fade" id="otherChargeModel" role="dialog" tabindex="-1">'
		html +='<div class="modal-dialog modal-sm" >'
		html +='<div class="modal-content">'
		html +='<div class="modal-header" style="background: #7FC8BA; color: #fff;">'
		html += '<button type="button" class="close" data-dismiss="modal">&times;</button>'
		html +='<h3 id="dmhId">Payment</h3>'                 
		html +='</div>'
		html +='<div class="modal-body" id="modelBodyId">'
		html +='<div class="form-group">'
		html +='<label>Total</label> <label id="totalAmounts" ></label><br>'
		html +='<label>Pay Amount</label>'
		html +='<input type="text" class="form-control" id="payAmnt">'
		html +='<label id="creditNotlbl">Credit Note</label>'
		html +='<input type="text" class="form-control" id="creditNot" onkeyup=\"payreport.claculatePayment(this);\">'
		html +='</div>'
		html +='<div class="btn btn-success" id="paymentAmount" onclick=\"payreport.makePayment();\">Pay</div>'
		html +='</div>'
		html +='</div>'
		html += '</div>'
		html +='</div>'
		html += '</div>'
		html += '</div>' 
			html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
          $("#content-wrapper").append(html);	
			wso1.loadAllClient();
        }
	
	this.claculatePayment = function (thisObj){
		var currentVal  = $(thisObj).val();
		if(currentVal == undefined || currentVal == ""){
			currentVal= "0";
			$(thisObj).val(currentVal.toFixed(2));
		}
		var payAmnt  = $("#totalAmounts").text();
		currentVal = parseFloat(currentVal);
		payAmnt = parseFloat(payAmnt);
		if(payAmnt<currentVal){
			currentVal = payAmnt;
			$(thisObj).val(currentVal.toFixed(2));
		}
		payAmnt = payAmnt-currentVal;
		$("#payAmnt").val(payAmnt.toFixed(2));
	}
	
	
	this.loadAllPayment = function(){
		var clientId = $("#clientInfo").val();
		if(clientId == undefined || clientId == null || clientId == ""){
			bootAlert.show("error", "Please Select to client");
			 return false;
		}
		clientId = parseInt(clientId);
		$.ajax({
		       type: "POST",
		       /*contentType: "application/json",*/
		       url: contextPath+ "/billings/loadAllPayment/" + clientId,
		       dataType: 'json',
		       cache: false,
		       timeout: 600000,
		       success: function (response) {
		    	   data = response.data;
		           console.log(data);
		           data1 = response.data1;
		           console.log(data1);
		           $("#paymentTbody").empty();
		            var tr="";
		            for(var i=0; i<data.length; i++){
		            	var clientTitle = data[i][0];
		                var invoiceNo  = data[i][1] ;
		                var netAmount = data[i][2];
		                var availableAmount  = data1[i].balanceAmount;
		                if(parseFloat(availableAmount) == 0){
		                	availableAmount = netAmount;
		                }
		                tr += "<tr>";
		                tr+= "<td ><input type='checkbox' class ='makePayment' id="+invoiceNo+" data="+availableAmount+" > </td><td>"+clientTitle+"</td> <td> "+invoiceNo+" </td><td style='text-align:right'>"+netAmount.toFixed(2)+"</td><td style='text-align:right'>"+parseFloat(availableAmount).toFixed(2)+"</td>";
		                tr += "</tr>";
		            }
		            $("#paymentTbody").append(tr);
		       },
		       error: function (e) {
		           var json = "<h4>Ajax Response</h4><pre>"
		               + e.responseText + "</pre>";
		           $('#feedback').html(json);
		           console.log("ERROR : ", e);
		       }
		   });
	}
	
	this.makePaymentPopup = function (){
	 invoicePaymentList = new Array();
	 var paymentType = $("input[name='optionsRadios']:checked").val();
	 	var totalPay = 0;
		
	 	$(".makePayment").each(function() {
			if($(this).is(':checked')){
				totalPay = totalPay+ parseFloat($(this).attr("data"));
				var paymentDetail = {"invoiceNo":$(this).attr("id"),"balance":$(this).attr("data"),"amount":"0"};
				invoicePaymentList.push(paymentDetail);
			}
    	});
		
		if( paymentType == "P" && invoicePaymentList.length>0){
			$('#otherChargeModel').modal('show');
			$("#totalAmounts").text(totalPay.toFixed(2));
			$("#payAmnt").attr("readonly",false);
			$("#creditNot").hide();
			$("#creditNotlbl").hide();
		}else if(paymentType == "C" && invoicePaymentList.length >0){
			if(invoicePaymentList.length  == 1){
				$('#otherChargeModel').modal('show');
				$("#payAmnt").val(totalPay.toFixed(2));
				$("#payAmnt").attr("readonly",true);
				$("#totalAmounts").text(totalPay.toFixed(2));
				$("#creditNot").show();
				$("#creditNotlbl").show();
			}else{
				bootAlert.show("error","Only One Invoice is allowed for credit payment !");
			}
			
		}else{
			
			bootAlert.show("error","Plase select row to make payment !");
		}
		
	}
	
	this.makePayment = function (){
		var   givenAmount = $("#payAmnt").val();
		var creditNote  =  $("#creditNot").val();
		var paymentType = $("input[name='optionsRadios']:checked").val();
		if(creditNote == undefined || creditNote == "" ){
			creditNote  = "0";
		}
		var filnalPayment = new Array();
		for(var i = 0; i <invoicePaymentList.length; i++ ){
			var crntblnc = invoicePaymentList[i].balance;
			givenAmount = parseFloat(givenAmount);
			crntblnc  = parseFloat(crntblnc);
			if(crntblnc <givenAmount){
				filnalPayment.push({"invoiceNo":invoicePaymentList[i].invoiceNo,"balance":invoicePaymentList[i].balance,"amount":invoicePaymentList[i].balance, "cnote":creditNote,"paymentType":"P" });
				givenAmount = givenAmount-crntblnc;
			}else{
				var givenamt = givenAmount;
				filnalPayment.push({"invoiceNo":invoicePaymentList[i].invoiceNo,"balance":invoicePaymentList[i].balance,"amount":givenamt.toString(), "cnote":creditNote,"paymentType":"P" });
				givenAmount = 0;
			}
			
		}
		/*if("C" == paymentType){
			filnalPayment.push({"invoiceNo":invoicePaymentList[0].invoiceNo,"balance":"0","amount":"0", "cnote":creditNote,"paymentType":"C" });
		}*/

		var fpayment  = encodeURIComponent(JSON.stringify(filnalPayment));
        
        var jsondata = {"paymentData":fpayment,"paymentType":paymentType};
    $.ajax({
        type: "POST",
        url: contextPath+"/billings/makePayment",
        data: jsondata,
       // data: '{"dateOfDelivery":"'+dateOfDelivery+'","dlNo":"'+dlNo+'","timeOfIssue":"'+timeOfIssue+'","nameOfReceiver":"'+nameOfReceiver+'","vehicleNo":"'+vehicleNo+'","nricOfReceiver":"'+nricOfReceiver+'","verify":"'+verify+'"}',
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$('#otherChargeModel').modal('hide');
        	bootAlert.show("success","Paid Successfully!")
        	payreport.loadAllPayment();
        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);

        }
    });
	}
	
	 this.checkUncheckAll = function(sender) {
        var chkElements = document.getElementsByName('chkItems');
        for (var i = 0; i < chkElements.length; i++) {
            chkElements[i].checked = sender.checked;
        }
    }
     this.checkUnCheckParent = function() {
        var chkHeader = document.getElementById('chkHeader');
        var chkElements = document.getElementsByName('chkItems');
        var checkedCount = 0;
        for (var i = 0; i < chkElements.length; i++) {
            if (chkElements[i].checked) {
                checkedCount += 1;
            }
        }
        chkHeader.checked = (checkedCount === chkElements.length);
    }
	
}