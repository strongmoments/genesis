var LOTREPORT =function (){
	this.show = function() {
        $("#content-wrapper").empty();
        var html = "";
        
        var currentDate = new Date()
        var day = currentDate.getDate()
        var month = currentDate.getMonth() + 1
        var year = currentDate.getFullYear()
        var clientName;
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
		html += '<input class=\"form-control\" id=\"date1\" type="date">'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"form-control btn btn-info  mr-10\" onclick=\"lotreport.getLotDetailByClientId();\" >Load</button>'
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
		html += '<div style="test-align:right">'+ day + '/' + month + '/' + year + '</div>'
		html += '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'
		html += '<div class="col-12-lg imgclass" style="display:none">'	
		html += '<center><img src="image/logo4.png" class="logo " style="width:750px;"> <br><br><span class="tax">Stock Balance Records</span><br></center>'
		html += '<hr>'
		html += '</div>'
		
		html += '<div class="row" id="showClient"><div class="col-6-lg"></div>'
		html += '<div class="col-6-lg"></div></div>'	
		html += '<div class="table-responsive">'	
		html += '<table class=\"table table-hover\" style="width:100%;">'
		html += '<thead id=\"lotThead\">'		
		html += '<tr>'
		html += '<th>Lot No</th>'
		html += '<th>Lot Description</th>'
		html += '<th style="text-align: right">WSO No</th>'
		html += '<th style="text-align: right">Storage Class</th>'
		html += '<th style="text-align: right">Date</th>'
		html += '<th style="text-align: right">INIT Qty</th>'
		html += '<th style="text-align: right">CURR  Qty</th>'
		html += '<th>Prod Date</th>'
		html += '<th>Exp Date</th>'
		html += '<th>UNIT (N)(KG)</th>'
		//html += '<th>UnitGross Wt</th>'
		html += '<th>UNIT QTY/LOT</th>'
		html += '</tr>'	
		html += '</thead>'
		html += '<tbody id=\"lotTbody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'	
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="lotreport.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="lotreport.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="lotreport.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
		html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
          $("#content-wrapper").append(html);	
			
		tally.loadAllclient();
        }
	
	//Start getLotDetailByClientId
	this.getLotDetailByClientId =function(){
		var html2 = "";
		var clientInfo = $("#clientInfo").val();
		var date1 = $("#date1").val();
		var date2 = new Date();
		date2 = common.getFormattedDate(new Date(date1));
		clientName =$("#clientInfo option:selected").text();
		$("#showClient").empty();
		html2 += '<div class="row" id="showClient"><div class="col-6-lg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>Client Name</label>&nbsp;:-&nbsp;'+clientName+'</div>'
		html2 += '<div class="col-6-lg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>Date</label>&nbsp;:-&nbsp;'+date2+'</div></div>'
		$("#showClient").append(html2);
		//alert(clientName,date1);
		
		if(clientInfo == "" || clientInfo == undefined || clientInfo == null){
			 bootAlert.show("error","Please select Client");
	         return false;
		}
		
		var jsondata = {
				'clientId': clientInfo,
				'date1': date1
		};
		$.ajax({
	        type: "POST",
	        //contentType: "application/json",
	        url: contextPath+ "/lotInfos/getLotDetailByClientId",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	data = response.data
	        	console.log(data);
	        	//alert("success");
	        	
	        	$("#lotTbody").empty();
	        	var tr="";
	        	var GtotalInitialQty = 0.00;
	            var GtotalCurrentQty= 0.00;
	        	for(var i=0; i<data.length; i++){
	        		if(data[i].length > 0){
	        			
	        			$(".imgclass").show();
	        			$("#printMePdf").show();
	        			$("#printMeXls").show();
	        			/*$("#sendEmail").show();*/
	        			var html1 = ""
			    	    /*html1 += '<div class=\"main-box-body clearfix\">'
	        			
		        		html1 += '<table class=\"table table-hover\">'
		        		html1 += '<thead id=\"outgoingThead\">'
		        			if(i==0){
		        			html1 += '<tr>'
		        				html1 += '<th>Lot No</th>'
		        				html1 += '<th>Lot Description</th>'
		        				html1 += '<th style="text-align: right">WSO No</th>'
		        				html1 += '<th>Date</th>'
		        				html1 += '<th style="text-align: right">INIT Qty</th>'
		        				html1 += '<th style="text-align: right">CURR  Qty</th>'
		        				html1 += '<th>Prod Date</th>'
		        				html1 += '<th>Exp Date</th>'
		        				html1 += '<th>UNIT (N)(KG)</th>'
		        				html1 += '<th>UnitGross Wt</th>'
		        				html1 += '</tr>'
		        			}
		        		html1 += '</thead>'
		        		html1 += '<tbody id="outgoingTbody'+i+'">'
		        		html1 += '</tbody>'
		        		html1 += '</table>'
		        	
		        		html1 += '</div>'
		    	        $("#lotTbody").append(html1);*/
	        		}
	        		
	        		var totalInitialQty = 0.00;
		            var totalCurrentQty= 0.00;
		            
	        		//$("#outgoingTbody"+i).empty();	
	        		//var tr="";
	        		
	        		for(j=0; j<data[i].length;j++){
//	        			var totalInitialQty = 0.00;
//			            var totalCurrentQty= 0.00;	      
	        			var lotNo   = data[i][j].lotNo ;
	 	                var lotDesc = data[i][j].lotDesc;
	 	                var wsoNo   = data[i][j].wsoNo ;
	 	                var date   = data[i][j].date ;
	 	                date= common.getFormattedDate(new Date(date));
	 	                var initialQty   = data[i][j].initialQty ;
	 	                var currentQty   = data[i][j].currentQty ;
	 	                var prodDate   = data[i][j].prodDate ;
	 	                prodDate= common.getFormattedDate(new Date(prodDate));
	 	                var expDate   = data[i][j].expDate ;
	 	                expDate= common.getFormattedDate(new Date(expDate));
	 	                var unitKg   = data[i][j].unitKg;
	 	                var unitQtyLot   = data[i][j].unitQtyLot;
	 	                var storageClass = data[i][j].storageClass;
	 	                tr += "<tr>";
	 	                tr += "<td>"+lotNo+"</td> <td style='width: 100px; text-align: left;'> "+lotDesc+" </td> <td style='width: 100px;text-align:right'> "+wsoNo+"</td> <td style='width: 100px;text-align: right'> "+storageClass+" </td> <td style='width: 100px;text-align: right'> "+date+" </td> <td style='text-align: right'> "+initialQty+" </td> <td style='width: 100px;text-align: right'> "+currentQty+" </td> " ;
	 	                tr += "<td style='width: 100px;'>"+prodDate+"</td> <td style='width: 100px; text-align: right;'> "+expDate+" </td> <td style='width: 100px;text-align:right;'> "+unitKg+"</td> <td style='width: 100px;text-align: right'> "+unitQtyLot+" </td>";
	 	                tr += "</tr>";
	 	                totalInitialQty += parseFloat(initialQty);
	 	                totalCurrentQty += parseFloat(currentQty);
		  	          	
	 	                
		  	          if(j == data[i].length-1){
		  	        	  	tr += "<tr><td></td><td></td><td></td><td></td><td></td>";
		  	        	    tr += "<td style='text-align: right;'><b>"+totalInitialQty+"</b></td><td style='text-align: right;'><b>"+totalCurrentQty+"</b></td><td></td><td></td><td></td><td></td><td></td></tr> ";	
		  	        	  
		  	        	    GtotalInitialQty += parseFloat(totalInitialQty);
			  	          	GtotalCurrentQty += parseFloat(totalCurrentQty);
		  	        	    
		  	        	    totalInitialQty = 0.00;
			  	          	totalCurrentQty = 0.00;
			  	       }	
			  	        
	        		}
	        		
	        		if(i == data.length-1){
		  	          	
		  	          	tr += "<tr><td></td><td></td><td></td><td></td><td><b>Grand Total</b></td><td style='text-align: right'>"+GtotalInitialQty+"</td><td style='text-align: right'>"+GtotalCurrentQty+"</td><td></td><td></td><td></td><td></td></tr>"
	                	
		  	          	}
	        		//$("#outgoingTbody"+i).append(tr);
	        	}
	        	$("#lotTbody").append(tr);
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	            $("#printMePdf").hide();
    			$("#printMeXls").hide();
	            $("#lotTbody").empty();
	            
	        }
	    });

	}
	this.customPrintPdf = function (thisObj){
		$("#printJS-form").printMe(
		{
			"path" : [ "css/compiled/theme_styles.css",
					"css/bootstrap/bootstrap.min.css",
					"css/mystyle2.css", "css/footer.css" ]

		});
		/*var clientId = $('#clientInfo').val();
//		alert("Print Button Clicked");
		window.open('download/getLotDetailByClientId/stockBalanceReport.pdf/pdf/'+clientId, '_blank');*/
	 }
	 this.customPrintXls = function (thisObj){
		var clientId = $('#clientInfo').val();
		var date1 = $("#date1").val();
		window.open('download/getLotDetailByClientIdPOI/stockBalanceReport.xls/xls/'+clientId+'/'+date1, '_blank');
	 }
	 this.customEmail = function (thisObj){
		var clientId = $('#clientInfo').val();
		window.open('email/getLotDetailByClientId/stockBalanceReport.pdf/'+clientId, '_blank');
	 }
	}