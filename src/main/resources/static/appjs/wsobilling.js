var WSOBILLING =function (){
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
		html += '<h1>WSO billing report</h1>'
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
		html += '<select class=\"form-control\" id=\"clientInfo\" onchange=\"wsobilling.loadAllWsoNoByClientId();\">'
		html += '<option>Select Client Name</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"form-group\">'
		html += '<label>Wso No</label>'
		html += '<select class=\"form-control\" id=\"WsoNo\">'
		html += '<option>Select Wso</option>'
		html += '</select>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<div class=\"col-lg-6\">'
		html += '<div class=\"col-lg-4\">'
		html += '<button type=\"button\" class=\"form-control btn btn-info  mr-10\" onclick=\"wsobilling.loadAllWsoBill();\" >Load</button>'
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
		html += '<center><img src="image/logo4.png" class="logo " style="width:750px;"> <br><br><span class="tax">WSO Billing Records</span><br></center>'
		html += '<hr>'
		html += '</div>'
		
		html += '<div class="row" id="wsoHeader"><div class="col-6-lg"></div>'
		html += '<div class="col-6-lg"></div></div>'	
		html += '<div class="table-responsive">'	
		html += '<table class=\"table table-hover\" style="width:100%;">'
		html += '<thead id=\"lotThead\">'		
		html += '<tr>'
		html += '<th style="text-align: left">Billing Period</th>'
		html += '<th style="text-align: left">Pkg Bal</th>'
		html += '<th style="text-align: left">Billed Wt.</th>'
		html += '<th style="text-align: left">Amount Billed($$)</th>'
		html += '<th style="text-align: left">Invoice No.</th>'
		html += '<th style="text-align: left">Invoice Date</th>'
		html += '<th>GST Amount($$)</th>'
		html += '<th>Rates($$)</th>'
		html += '</tr>'	
		html += '</thead>'
		html += '<tbody id=\"wsobody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
		
		html += '<hr><br>'
		//html += '<div class="row" id="dlHeader"><div class="col-6-lg"></div>'
		//html += '<div class="col-6-lg"></div></div>'
			
		html += '<div class="table-responsive">'	
		html += '<table class=\"table table-hover\" style="width:100%;">'
		html += '<thead id=\"wsoThead\">'		
		html += '<tr>'
		html += '<th style="text-align: left">Dl No</th>'
		html += '<th style="text-align: left">Del Dt.</th>'
		html += '<th style="text-align: left">Pkgs Out</th>'
		html += '<th style="text-align: left">Wt. Del</th>'
		html += '<th style="text-align: left">Bal Packs</th>'
		html += '<th style="text-align: left">Bal Wt.</th>'
		html += '</tr>'	
		html += '</thead>'
		html += '<tbody id=\"dlBody\">'
		html += '</tbody>'
		html += '</table>'
		html += '</div>'
			
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		html += '<button type="button" class="btn btn-info" style="display:none"  id="printMePdf" onclick="wsobilling.customPrintPdf();">Print Now'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html += '<button type="button" class="btn btn-info" style="display:none; margin-left:20px;"  id="printMeXls" onclick="wsobilling.customPrintXls();">Print Excel'
		html += '<span class="glyphicon glyphicon-chevron-right">'
		html += '</span></button>'
		html +=	'<button type="button" class="btn btn-primary" style="display:none; margin-left:20px;" id="sendEmail" onclick="wsobilling.customEmail(this);">' +
		        'Send Email'+
		        '<span class="glyphicon glyphicon-send">'+'</span>'+
		        '</button>'
		html +=	'<footer id="footer-bar" class="row"><p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd</p></footer>'
          $("#content-wrapper").append(html);	
			
		tally.loadAllclient();
        }
	
	this.loadAllWsoNoByClientId = function(){
		var html2 = "";
		var clientInfo = $("#clientInfo").val();
		clientName =$("#clientInfo option:selected").text();
		if(clientInfo == "" || clientInfo == undefined || clientInfo == null){
			 bootAlert.show("error","Please select Client");
	         return false;
		}
		var clientId = $( "#clientInfo" ).val();
		
		//$("#showClient").empty();
		$.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/wsoBill/getAllWsoByClientId/"+clientId,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	console.log(data)
	        	$("#WsoNo").empty();
	        	for (var i = 0; i < data.length; i++) {
					$('#WsoNo').append(
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
	
	this.loadAllWsoBill = function(){
		var html2 = "";
		var clientInfo = $("#clientInfo").val();
		var wsoNo = $("#WsoNo").val();
		clientName =$("#clientInfo option:selected").text();
		wsoNumber =$("#WsoNo option:selected").text();
		if(clientInfo == "" || clientInfo == undefined || clientInfo == null){
			 bootAlert.show("error","Please select Client");
	         return false;
		}
		if(wsoNo == "" || wsoNo == undefined || wsoNo == null){
			 bootAlert.show("error","Please select Wso No.");
	         return false;
		}
		var clientId = $( "#clientInfo" ).val();
		
		
		//$("#WsoNo").empty();
		
		var jsondata = {
				'clientId': clientInfo,
				'wsoNo': wsoNumber
		};
		$.ajax({
	        type: "POST",
	        //contentType: "application/json",
	        url: contextPath+ "/wsoBill/loadAllWsoBill",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	wsobill = response.data
	        	console.log(wsobill);
	        	$(".imgclass").show();
    			$("#printMePdf").show();
    			$("#printMeXls").show();
	        	$("#wsoHeader").empty();
	        	
	        	$("#wsobody").empty();
	        	
	        	var tr="";
	        	var storageDate = wsobill[0].storageDate;
	        	storageDate = common.getFormattedDate(new Date(storageDate));
	        	var storageClass = wsobill[0].storageClass;
	        	var wsoWt = wsobill[0].wsoWt.toFixed(0);
	        	var totPallets = wsobill[0].totPallets;
	        	var wsoTotQty = wsobill[0].wsoTotQty;
	        	var currQty = wsobill[0].currQty;
	        	var currWt = wsobill[0].currWt.toFixed(0);
	        	
	        	var currentDate = new Date()
	            var day = currentDate.getDate()
	            var month = currentDate.getMonth() + 1
	            var year = currentDate.getFullYear();
	        	
	        	//html2 += '<div class="row">'
	        	html2 += '<div class="table-responsive">'
	        	html2 += '<div class="col-lg-6"><b>Date : '+ day + '-' + month + '-' + year +'</b></div></div>'
        		html2 += '<div class="table-responsive"><div class="col-lg-6"><b>ClientName : '+clientName+'</b></div></div>'
        		html2 += '<div class="table-responsive"><div class="col-lg-3"><b>WSO No: '+wsoNumber+'</b></div></div><br>'
        		
        		html2 += '<div class="table-responsive" style = "display: inline">'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>Storage Date : '+storageDate+'</b></div>'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>Storage Class : '+storageClass+'</b></div>'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>WSO Weight : '+wsoWt+'</b></div></div><br>'
        		
        		//html2 += '<div class="table-responsive">'
        		
        		html2 += '<div class="table-responsive" style = "display: inline">'
        		//html2 += '<div class="col-lg-3" style = "display: inline"><b>             '+"            "+'</b></div>'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>WSO No: '+wsoNumber+'</b></div>'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>WSO Quantity : '+wsoTotQty+'</b></div>'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>Pallets :'+totPallets+'</b></div></div><br>'
        		
        		html2 += '<div class="table-responsive" style = "display: inline">'
				//html2 += '<div class="col-lg-3" style = "display: inline"><b>             '+"            "+'</b></div>'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>Curr Qty : '+currQty+'</b></div>'
        		html2 += '<div class="col-lg-3" style = "display: inline"><b>Curr Wt : '+currWt+'</b></div></div>'
        		
        		//html2 += '</div>'	
        		html2 += '<hr>'	
	    		$("#wsoHeader").append(html2);
	        	
	        	//Second Part of the Report
	        	
	        	for(j=0; j<wsobill[1].length;j++){
        			var fromDate   = wsobill[1][j].fromDate;
        			fromDate= common.getFormattedDate(new Date(fromDate));
        			var toDate     = wsobill[1][j].toDate;
        			toDate = common.getFormattedDate(new Date(toDate));
        			var billPeriod  = fromDate + "  -  " + toDate; 
 	                var pkgBal      = wsobill[1][j].pkgBal;
 	                var balWeight   = wsobill[1][j].balWeight.toFixed(0);
 	               	var billedWt   = wsobill[1][j].billWt.toFixed(0);
 	                var amtBilled    = wsobill[1][j].amtBilled.toFixed(2);
 	                var invNo      = wsobill[1][j].invNo;
 	                var invDate   = wsobill[1][j].invDate;
 	                invDate = common.getFormattedDate(new Date(invDate));
 	                var gst      = wsobill[1][j].gst.toFixed(2);
 	                var rate      = wsobill[1][j].rate.toFixed(2);
 	                tr += "<tr>";
 	                tr += "<td>"+billPeriod+"</td> <td style='width: 100px; text-align: left;'> "+pkgBal+" </td> <td style='width: 100px;text-align:right'> "+billedWt+"</td> <td style='width: 100px;text-align: right'> "+amtBilled+" </td> <td style='width: 100px;text-align: left'> "+invNo+" </td> <td style='text-align: left'> "+invDate+" </td> <td style='width: 100px;text-align: right'> "+gst+" </td> " ;
 	                tr += "<td style='width: 100px;'>"+rate+"</td>";
 	                tr += "</tr>";
	        	}   
        	
        	$("#wsobody").append(tr);
        	
        	/*$("#dlHeader").empty();
        	html2 = "";
        	html2 += '<div class="row">'
    		html2 += '<div class="col-lg-6"><b>ClientName : '+clientName+'</b></div>'
    		html2 += '<div class="col-lg-3"><b>WSO No: '+wsoNumber+'</b></div>'
    		html2 += '<div class="col-lg-3"><b>Storage Date : '+storageDate+'</b></div></div>'
    		html2 += '<div class="row">'
    		html2 += '<div class="col-lg-3"><b>Storage Class : '+storageClass+'</b></div>'
    		html2 += '<div class="col-lg-3"><b>Curr Qty : '+currQty+'</b></div>'
    		html2 += '<div class="col-lg-3"><b>Curr Wt : '+currWt+'</b></div></div>'
    		
    		$("#dlHeader").append(html2);*/
        	
        	// Fourth part of the Report
        	$("#dlBody").empty();	
        	tr = "";
        	for(j=0; j<wsobill[2].length;j++){
    			var dlNo       = wsobill[2][j].dlNo;
    			var dlDate     = wsobill[2][j].dlDate;
    			dlDate = common.getFormattedDate(new Date(dlDate));
                var pkgOut      = wsobill[2][j].pkgOut;
                var wtDelivered = wsobill[2][j].wtDelivered.toFixed(0);
                var balPkgs     = wsobill[2][j].balPkgs;
                var balWt       = wsobill[2][j].balWt.toFixed(0);
                tr += "<tr>";
                tr += "<td>"+dlNo+"</td> <td style=' text-align: left;'> "+dlDate+" </td> <td style='text-align:left;'> "+pkgOut+"</td> <td style='text-align: left;'> "+wtDelivered+" </td> <td style='text-align: left';> "+balPkgs+" </td> <td style='text-align: left;'> "+balWt+" </td> " ;
                tr += "</tr>";
        	}   
        	$("#dlBody").append(tr);
    	
	        },error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
	}
	
	this.customPrintPdf = function (thisObj){
		$("#printJS-form").printMe(
		{
			"path" : [ "css/compiled/theme_styles.css",
					"css/bootstrap/bootstrap.min2.css",
					"css/mystyle2.css", "css/footer.css" ]

		});
		/*var clientId = $('#clientInfo').val();
//		alert("Print Button Clicked");
		window.open('download/getLotDetailByClientId/stockBalanceReport.pdf/pdf/'+clientId, '_blank');*/
	 }
	 this.customPrintXls = function (thisObj){
		var clientId = $('#clientInfo').val();
		var wsoNumber =$("#WsoNo option:selected").text();
		//var date1 = $("#date1").val();
		//window.open('download/loadAllWsoBill/wsoBilling.xls/xls/'+clientId+'/'+date1, '_blank');
		window.open('download/loadAllWsoBill/wsoBilling.xls/xls/'+clientId+'/'+wsoNumber, '_blank');
	 }
	 this.customEmail = function (thisObj){
		var clientId = $('#clientInfo').val();
		window.open('email/loadAllWsoBill/wsoBilling.pdf/'+clientId, '_blank');
	 }
	}