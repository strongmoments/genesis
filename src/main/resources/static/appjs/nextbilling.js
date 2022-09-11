var NEXTBILLING = function() {
	
	this.show = function() {
  	  $("#content-wrapper").empty();
        var html = '<body onload=\"this.nextBilling()\">';
        html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-12\">'
      	  html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-12\">'
      	  html += '<div id=\"content-header\" class=\"clearfix\">'
      	  html += '<div class=\"pull-left\">'
      	  html += '<h1>Projected Billing</h1>'
      	  html += '</div>'
      	  html += ''
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += ''
      	  html += '<div class=\"row table-style\">'
      	  html += '<div class=\"col-lg-12 full-height\" id=\"nextBillingReportTable\">'
      	  html += '<div class=\"main-box clearfix full-height\">'
      	  html += '<header class=\"main-box-header clearfix\">'
      	  html += '<h2 class=\"pull-left\">Projected Billing Report</h2>'
      	  html += '</header>'      			
      	  html += '<div class=\"main-box-body clearfix datesscroll report\" style=\"height: 900px; overflow-y: scroll;\">'
      	  html += '<div class=\"table-responsive clearfix\">'
      	  html += '<table class=\"table table-hover\">'
      	  html += '<thead>'
      	  html += '<tr>'
      	  html += '<th>'
      	  html += '<span>CLIENT NAME</span>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<span>FROM DATE</span>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<span>TO DATE</span>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<span>TOTAL WSO</span>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<span>TOTAL LOT</span>'
      	  html += '</th>'
      	  html += '<th>'
    	  html += '<span>AMOUNT</span>'
    	  html += '</th>'
      	  html += '<th>'
      	  html += '<span>View</span>'
      	  html += '</th>'
      	  html += '</tr>'
      	  html += '</thead>'
      	  html += '<tbody id=\"nextBillingReport\">'
      	  html += ''
      	  html += '</tbody>'
      	  html += '</table>'
      	  html += '</div>'
      	  html += '<div class=\"modal fade\" id=\"dynamicModel1\" role=\"dialog\" tabindex=\"-1\">'
      	  html += '<div class=\"modal-dialog\">'
      	  html += '<div class=\"modal-content\">'
      	  html += '<div class=\"modal-header\" style=\"background: #7FC8BA; color: #fff;\">'
      	  html += '<h3 id=\"dmhId\">nextBilling Report</h3>'
      	  html += '</div>'
      	  html += '<div class=\"modal-body\" id=\"modelBodyId\">'
      	  html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-5\">'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Customer No</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"clientNo\">'
      	  html += '</div>'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Client Title</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"clientTitle\">'
      	  html += '</div>'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Reefer/Dry</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"reeferDry\">'
      	  html += '</div>'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Temperature</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"temp\">'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '<div class=\"col-lg-2\"></div>'
      	  html += '<div class=\"col-lg-5\">'
      	  html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-12\">'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Tally Sheet No</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"talySheetNo\">'
      	  html += '</div>'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Date Of Storage</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"dateOfStorage\">'
      	  html += '</div>'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Warehouse ID</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"warehouseId\">'
      	  html += '</div>'
      	  html += '<div class=\"form-group\">'
      	  html += '<label>Page</label>'
      	  html += '<input type=\"text\" class=\"form-control\" id=\"page\">'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-12\">'
      	  html += '<div class=\"table-responsive\">'
      	  html += '<table class=\"table\">'
      	  html += '<thead>'
      	  html += '<tr>'
      	  html += '<th>'
      	  html += '<samp>CLIENT NAME</samp>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<samp>nextBilling NO</samp>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<samp>DATE OF STORAGE</samp>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<samp>EX VESSEL</samp>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<samp>WEIGHT(KG)</samp>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<samp>MEASUREMENT</samp>'
      	  html += '</th>'
      	  html += '</tr>'
      	  html += '</thead>'
      	  html += '<tbody id="nextBillingReport">'
      	  html += '<tr>'
      	  html += '<td>G0001</td>'
      	  html += '<td>Pallets scb Unsalted camplna-lc BB0p38f=27X36C'
      	  html += 'Text1Text2'
      	  html += '</td>'
      	  html += '<td>STO-CC</td>'
      	  html += '<td>28</td>'
      	  html += '<td>28000</td>'
      	  html += '<td>28</td>'
      	  html += '</tr>'
      	  html += '<tr>'
      	  html += '<td>G0001</td>'
      	  html += '<td>Pallets scb Unsalted camplna-lc BB0p38f=27X36C'
      	  html += 'Text1Text2'
      	  html += '</td>'
      	  html += '<td>STO-CC</td>'
      	  html += '<td>28</td>'
      	  html += '<td>28000</td>'
      	  html += '<td>28</td>'
      	  html += '</tr>'
      	  html += '</tbody>'
      	  html += '<tfoot>'
      	  html += '<tr>'
      	  html += '<td></td>'
      	  html += '<td></td>'
      	  html += '<td>'
      	  html += '<samp>TOTAL</samp>'
      	  html += '</td>'
      	  html += '<td>'
      	  html += '<samp>28</samp>'
      	  html += '</td>'
      	  html += '<td>'
      	  html += '<samp>28000</samp>'
      	  html += '</td>'
      	  html += '<td></td>'
      	  html += '</tr>'
      	  html += '</tfoot>'
      	  html += '</table>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '<div class=\"row\">'
      	  html += '<button type=\"button\" class=\"btn btn-success btn-lg btn-block\">'
      	  html += 'Download PDF'
      	  html += '<span class=\"glyphicon glyphicon-chevron-right\"></span>'
      	  html += '</button>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '<!-- End Tally Sheet Report-->'
      	  html += ''
      	  html += '</div>'
      	  html += '</div>'
      	  html += '<footer id=\"footer-bar\" class=\"row\">'
      	  html += '<p id=\"footer-copyright\" class=\"col-xs-12\">'
      	  html += 'Powered by Sandrokottos From StrongMoments Pvt Ltd'
      	  html += '</p>'
      	  html += '</footer></body>'
           $("#content-wrapper").append(html);	
        nextbilling.nextBilling();
        //treport.loadAllnextBilling();
  }
	
	this.nextBilling = function(){
		//alert("Next Billing function...");
		var jsondata = {'nothing':"",'ac':""}
	    $.ajax({
	        type: "POST",
	        url: contextPath+ "/clientStorageInfos/nextMonthBilling",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	            data = response.data;
	        	console.log(data)
	        	$("#nextBillingReport").empty();
	        
	        	for(var i=0; i<data.length; i++){
	        		if(data.length > 0){
	        			
	        			$(".imgclass").show();
	        			$("#printMePdf").show();
	        			$("#printMeXls").show();
	        			$("#sendEmail").show();
	        			var html = ""
			    	    //html += '<div class=\"main-box-body clearfix\">'
	        			var clientName = data[i].clientName;
	        			var clientId = data[i].clientId;
	        			var fromDate = data[i].fromDate;
	        			var toDate = data[i].toDate;
	        			//storageDate= common.getFormattedDate(new Date(storageDate));
	        			var totalWSO = data[i].totalWSO;
	        			var totalLot = data[i].totalLot;
	        			var amount = data[i].amount;
	        			
	        			html += "<tr>";
	        			html +="<td >"+clientName+"</td> <td > "+fromDate+" <td > "+toDate+" </td> <td> "+totalWSO+" </td><td>"+totalLot+" </td><td>$"+amount.toFixed(2)+" </td>";
	        			html += "<td><button class='btb btn-info'  onclick='nextbilling.nextBillReport("+clientId+")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
	        			html += "</tr>";  
		        		
		    	        $("#nextBillingReport").append(html);}	
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
	this.nextBillReport = function(clientId){
		
		var jsondata = {'clientId':clientId}
		$.ajax({
	        type: "POST",
	        url: contextPath+ "/clientStorageInfos/nextBilling",
	        data : jsondata,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	            data1 = response.data;
	            wsoDetail = response.wsoDetail;
	        	console.log(wsoDetail)
	        	$("#nextBillingReport").empty();
	        
	        	loadnextBillingReportspage(data1, wsoDetail);
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });

	}
	
	function loadnextBillingReportspage(data1, wsoDetail){
	    console.log(wsoDetail)
	    /*var storageDate  = data1.storageDate;
	    storageDate = getFormattedDate(new Date(storageDate));
	   */ $("#nextBillingReportTable").empty().append('<div class="main-box clearfix"><button type="button" class="btn btn-info  mr-10" onclick="nextbilling.show();" style="float:right">Back</button>'+
	        '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'+
	        '<h2 class="pull-left">NextBilling Report</h2>'+
	        '</header>'+
	        '<div class="main-box-body clearfix">'+
	        '<div class="row" style="text-align:center;">'+
	        '<img src="image/logo3.png" style="width:450px; height:100px;">'+
	        '<hr>'+
	        '</div>'+
	        '<div class="row">'+
	        '</div>'+
	        /*'<div class="row">'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>nextBilling No : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.nextBillingNumber+' </label>'+
	        '</div>'+
	        '</div>'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Date Of Storage : </label>&nbsp;&nbsp;'+
	        '<label>'+storageDate+'</label>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+*/
	        '<div class="row">'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Client Name : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.clientTitle+'</label>'+
	        '</div>'+
	        '</div>'+
	       /* '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Ex-Vessel : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.exVessel+'</label>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+
	        '<div class="row">'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Unstuff Teml : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.tempUnstuddUnload+'</label>'+
	        '</div>'+
	        '</div>'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Temp Recd : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.tempRecorded+'</label>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+
	        */'<div class="row">'+
	        '<div class="col-lg-12">'+
	        '<div class="table-responsive">'+
	        '<table class="table">'+
	        '<thead>'+
	        '<tr>'+
	        '<th>'+
	        '<samp>WSO NO</samp>'+
	        '</th>'+
	        '<th>'+
	        '<samp>STORAGE TYPE</samp>'+
	        '</th>'+
	        '<th>'+
	        '<samp>TOTAL WSO WEIGHT(KG)</samp>'+
	        '</th>'+
	        '<th>'+
	        '<samp>NO OF PALLETS</samp>'+
	        '</th>'+
	        '<th>'+
	        '<samp>WSO Lot QTY</samp>'+
	        '</th>'+
	        '<th>'+
	        '<samp>AMOUNT</samp>'+
	        '</th>'+
	        '<th>'+
	        '<samp>GST</samp>'+
	        '</th>'+
	        '</tr>'+
	        '</thead>'+
	        '<tbody id="nextBillingWsoBody">'+
	        '</tbody>'+
	        '<tfoot id="nextBillingFooter">'+
	        
	        '</tfoot>'+
	        '</table>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+
	        /*'<div class="row">'+
	        '<button type="button" class="btn btn-success btn-lg btn-block">'+
	        'Print Now'+
	        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
	        '</button>'+
	        '</div>'+*/
	        '</div>'+
	        '</div>')
	        /*'<button type="button" onclick="printJS(\'printJS-form\', \'html\')">' +
	        'Print Now'+
	        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
	        '</button>'*/

	    var dnTr ="";
	    var ttlWsoWgt = 0;
	    var ttlPallets = 0;
	    var wsottlQty = 0;
	    for(var i =0; i<wsoDetail.length ; i++){
	        dnTr  += "<tr><td>"+wsoDetail[i].wsoNo+"</td>   <td>"+wsoDetail[i].storageTypeName+"</td>   <td>"+wsoDetail[i].totalWsoWeight+"</td>  <td>"+wsoDetail[i].totalNoOfPallets+"</td> <td>"+wsoDetail[i].ttlWsoQuantity+"</td>  <td>$"+wsoDetail[i].amount.toFixed(2)+"</td>  <td>$"+wsoDetail[i].gst.toFixed(2)+"</td></tr>";
	        /*ttlWsoWgt += parseFloat(wsoDetail[i].totalWsoWeight);
	        ttlPallets += parseFloat(wsoDetail[i].totalNoOfPallets);
	        wsottlQty += parseFloat(wsoDetail[i].ttlWsoQuantity);*/
	    }
	    $("#nextBillingWsoBody").append(dnTr);
	    /*$("#nextBillingFooter").append(
	    '<tr>'+
		    '<td>Total</td>'+
		    '<td></td>'+
		    '<td>'+ttlWsoWgt+'</td>'+
		    '<td>'+ttlPallets+'</td>'+
		    '<td>'+wsottlQty+'</td>'+
	    '</tr>'
	    );*/
	}

	
}