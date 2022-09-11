var REPORT_WSOLOT = function() {

	this.show = function() {
  	  $("#content-wrapper").empty();
        var html = "";
        html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-12\">'
      	  html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-12\">'
      	  html += '<div id=\"content-header\" class=\"clearfix\">'
      	  html += '<div class=\"pull-left\">'
      	  html += '<h1>Reports</h1>'
      	  html += '</div>'
      	  html += ''
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += ''
      	  html += '<!-- WSO Lot Report -->'
      	  html += '<div class=\"row\">'
      	  html += '<div class=\"col-lg-12\" id=\"wsolotReportBody\">'
      	  html += '<div class=\"main-box clearfix\">'
      	  html += '<header class=\"main-box-header clearfix\">'
      	  html += '<h2 class=\"pull-left\">WSO Lot Report</h2>'
      	  html += '<div class=\"filter-block pull-right\">'
      	  html += '<div class=\"form-group pull-left\">'
      	  html += '<input type=\"text\" class=\"form-control\" placeholder=\"Search Using WsoNo...\" id="searchWsoLotNo" onkeyup="wlreport.wsolotSearch();" style=\" margin-right:60px;float: right;\">'
      	  html += '<i class=\"fa fa-search search-icon\" style=\" margin-right:60px;float: right;\"></i>'
      	  html += '<a  href=\"index.html\" class=\"btn btn-danger\" style=\" margin-top: -35px;float: right;\">Exit</a>'
      	  html += '</div>'
      	  html += '</div>'
      	  html += '</header>'
      	  html += '<div class=\"main-box-body clearfix\" style=\"height: 900px; overflow-y: scroll;\">'
      	  html += '<div class=\"table-responsive clearfix\">'
      	  html += '<table class=\"table table-hover\">'
      	  html += '<thead>'
      	  html += '<tr>'
      	  html += '<th style="text-align:right">'
      	  html += '<span>Wso Number</span>'
      	  html += '</th>'
      	  html += '<th style="text-align:right">'
      	  html += '<span>Total Wso Weight</span>'
      	  html += '</th>'
      	  html += '<!--<th>'
      	  html += '<span>Total Wso Quantity</span>'
      	  html += '</th>-->'
      	  html += '<th style="text-align:right">'
      	  html += '<span>Total Number Of Pallets</span>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<span>Cargo</span>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<span>Report View</span>'
      	  html += '</th>'
      	  html += '</tr>'
      	  html += '</thead>'
      	  html += '<tbody id=\"twsolotReportBody\">'
      	  html += ''
      	  html += '</tbody>'
      	  html += '</table>'
      	  html += '</div>'
      	  html += '<div class=\"modal fade\" id=\"dynamicModel1\" role=\"dialog\" tabindex=\"-1\">'
      	  html += '<div class=\"modal-dialog\">'
      	  html += '<div class=\"modal-content\">'
      	  html += '<div class=\"modal-header\" style=\"background: #7FC8BA; color: #fff;\">'
      	  html += '<h3 id=\"dmhId\">WSO Report</h3>'
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
      	  html += '<th style="text-align:right">'
      	  html += '<samp>WSO NO</samp>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<samp>DESCRIPTION</samp>'
      	  html += '</th>'
      	  html += '<th>'
      	  html += '<samp>TYPE</samp>'
      	  html += '</th>'
      	  html += '<th style="text-align:right">'
      	  html += '<samp>QTY</samp>'
      	  html += '</th>'
      	  html += '<th style="text-align:right">'
      	  html += '<samp>WEIGHT(KG)</samp>'
      	  html += '</th>'
      	  html += '<th style="text-align:right">'
      	  html += '<samp>PALLETS</samp>'
      	  html += '</th>'
      	  html += '</tr>'
      	  html += '</thead>'
      	  html += '<tbody>'
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
      	  html += '</footer>'
           $("#content-wrapper").append(html);		
        	wlreport.loadAllWsoLotReport();
  }
  
	  //Start Load Wso 
	   this.loadAllWsoLotReport = function() {
	      $.ajax({
	          type: "GET",
	          contentType: "application/json",
	          url: contextPath+"/wsoInfos/getAllWsoreports",
	          dataType: 'json',
	          cache: false,
	          timeout: 600000,
	          success: function (response) {
	              var data = response.data;
	              console.log(data)
	              $("#twsolotReportBody").empty();
	              var tr= "";
	              for(var i =0; i<data.length; i++){
	                  var wsoNo  = data[i].wsoNo;
	                  var totalWsoWeight  = data[i].totalWsoWeight;
	                  //var totalWsoWeight  = data[i].totalWsoWeight;
	                  var cargo  = data[i].cargo;
	                  var totalNoOfPallets  = data[i].totalNoOfPallets;
	                  //var invoiceRate  = data[i].invoiceRate;
	                  //var invoicerate = data[i].invoicerate;

	                  tr += "<tr>";
	                  tr += "<td style='text-align:right'>"+wsoNo+"</td>";
	                  tr += "<td style='text-align:right'>"+totalWsoWeight+"</td>";
	                  tr += "<td style='text-align:right'>"+totalNoOfPallets+"</td>";
	                  tr += "<td>"+cargo+"</td>";
	                  //tr += "<td>"+invoiceRate+"</td>";
	                  tr += "<td><button class='btb btn-info' data-toggle='modal' data-target='#dynamicModel2' onclick='wlreport.showWsoLotReport(" + data[i].wsoId + ")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
	                  // tr += "<td>"+invoicerate+"</td>";
	                  tr += "</tr>";
	              }

	              $("#twsolotReportBody").append(tr);

	          },
	          error: function (e) {
	              var json = "<h4>Ajax Response</h4><pre>"
	                  + e.responseText + "</pre>";
	              $('#feedback').html(json);
	              console.log("ERROR : ", e);
	          }
	      });
	  }
	  //End Load Wso
   
   this.showWsoLotReport  =function(wsoId){
	    wso = parseInt(wsoId);
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+"/wsoInfos/getAllWsoreports/" + wso,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	            var data = response.data;
	            var lotDetail = response.lotDetail;
	            console.log(data)
	            console.log(lotDetail)
	            wlreport.loadWsoLotReportspage(wso,data,lotDetail);

	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
	}

	 this.loadWsoLotReportspage = function(wso,data1,lotDetail){
	    console.log(data1)
	    var storageDate  = data1.storageDate;
	    storageDate = common.getFormattedDate(new Date(storageDate));
	    $("#wsolotReportBody").empty().append('<div class="main-box clearfix"><button type="button" class="btn btn-info  mr-10" onclick="wlreport.show();" style="float:right">Back</button>'+
	        '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'+
	        
	        '</header>'+
	        '<div class="main-box-body clearfix">'+
	        '<div class="row imgclass" >'+
	        '<img src="image/logo4.png"class="logo" style="width: 750px"><br><span class="tax">Wso-Lot Report</span><br>'+
	        '<hr>'+
	        '</div>'+
	        '<div class="row">'+
	        '<div class="col-lg-6 dldiv2">'+
	        '<div class="form-group">'+
	        '<label>TallySheet No : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.tallysheetNumber+'</label>'+
	        '</div>'+
	        '</div>'+
	        '<div class="col-lg-6 dldiv2">'+
	        '<div class="form-group">'+
	        '<label>Client Name : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.clientTitle+'</label>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+
	        '<div class="row">'+
	        '<div class="col-lg-6 dldiv2">'+
	        '<div class="form-group">'+
	        '<label>RefDry : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.refDry+'</label>'+
	        '</div>'+
	        '</div>'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Ex-Vessel : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.exVessel+'</label>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+
	        '<div class="row">'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Temp Recd : </label>&nbsp;&nbsp;'+
	        '<label>'+data1.tempRecorded+'</label>'+
	        '</div>'+
	        '</div>'+
	        '<div class="col-lg-6">'+
	        '<div class="form-group">'+
	        '<label>Storage Date : </label>&nbsp;&nbsp;'+
	        '<label>'+storageDate+'</label>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+
	        '<div class="row">'+
	        '<div class="col-lg-12">'+
	        '<div class="table-responsive">'+
	        '<table class="table tstable">'+
	        '<thead>'+
	        '<tr>'+
	        '<th style="text-align:right">'+
	        '<samp>WSO NO</samp>'+
	        '</th>'+
	        '<th style="text-align:right">'+
	        '<samp>LOT NO</samp>'+
	        '</th>'+
	        '<th>'+
	        '<samp>DESCRIPTION</samp>'+
	        '</th>'+
	        '<th class="td" style="text-align:right">'+
	        '<samp>LOT WEIGHT</samp>'+
	        '</th>'+
	        '<th class="td" style="text-align:right">'+
	        '<samp>LOT QUANTITY</samp>'+
	        '</th>'+
	        '<th class="td" style="text-align:right">'+
	        '<samp>PALLETS QUANTITY</samp>'+
	        '</th>'+
	        '</tr>'+
	        '</thead>'+
	        '<tbody id="wsoLotBody">'+


	        '</tbody>'+
	        ' </form><tfoot id="wsoFooter">'+

	        '</tfoot>'+
	        '</table>'+
	        '</div>'+
	        '</div>'+
	        '</div>'+
	        
	        '</div>'+
	        '</div>'+
	        '<button type="button" data='+wso+' class="btn btn-info" onclick="wlreport.customPrintPdf(this);">' +
	        'Download PDF'+
	        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
	        '</button>'+
	        '<button type="button" data='+wso+' class="btn btn-info" style="margin-left:20px"; onclick="wlreport.customPrintXls(this);">' +
	        'Download Excel'+
	        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
	        '</button>'+
	        '<button type="button" class="btn btn-primary" style="margin-left:20px"; data='+wso+' onclick="wlreport.customEmail(this);">' +
            'Send Email'+
            '<span class="glyphicon glyphicon-send">'+'</span>'+
            '</button>')

	    var dnTr ="";
	    var ttlPallets = 0;
	    var ttlLotQuantity = 0;
	    var ttlLotWeight = 0;
	      for(var i =0; i<lotDetail.length ; i++){
	          dnTr  += "<tr><td class='td1' style='text-align:right'>"+lotDetail[i].wsoNo+"</td> <td class='td1' style='text-align:right'>"+lotDetail[i].lotNo+"</td>  <td class='td1'>"+lotDetail[i].description+"</td>   <td class='td' style='text-align:right'>"+lotDetail[i].unitGrossWeightInKg.toFixed(0)+"</td>  <td class='td' style='text-align:right'>"+lotDetail[i].lotQuantity+"</td>  <td class='td' style='text-align:right'>"+lotDetail[i].totalPallets+"</td> </tr>";
	          ttlLotWeight += parseFloat(lotDetail[i].unitGrossWeightInKg);
	          ttlLotQuantity += parseFloat(lotDetail[i].lotQuantity);
	          ttlPallets += parseFloat(lotDetail[i].totalPallets);
	      }
	      $("#wsoLotBody").append(dnTr,'<tr>' +
	    		    '<td>Total : </td><td></td><td></td>' +
	    		    '<td class="td" style="text-align:right">'+ttlLotWeight.toFixed(0)+'</td>' +
	    		    '<td class="td" style="text-align:right">'+ttlLotQuantity+'</td>' +
	    		    '<td class="td" style="text-align:right">'+ttlPallets+'</td>' +
	    		    '</tr>');
	    /*$("#wsoFooter").append(
	    '<tr>' +
	    '<td>Total : </td><td></td><td></td>' +
	    '<td>'+ttlLotWeight+'</td>' +
	    '<td>'+ttlLotQuantity+'</td>' +
	    '<td>'+ttlPallets+'</td>' +
	    '</tr>'
	    );*/



	}
	 
	 this.customPrintPdf = function (thisObj){
		 var wso = $(thisObj).attr("data");
		 wso = parseInt(wso);
		 window.open('download/wsolot/wsolotReport.pdf/pdf/'+wso, '_blank');
		 //$("#printJS-form").printMe();
		 /*$("#printJS-form").printMe({
				"path" : ["css/compiled/theme_styles.css","css/footer.css","css/mystyle2.css"]
			});*/
	 }
	 this.customPrintXls = function (thisObj){
		 var wso = $(thisObj).attr("data");
		 wso = parseInt(wso);
		 window.open('download/wsolot/wsolotReport.xls/xls/'+wso, '_blank');
		 //$("#printJS-form").printMe();
		 /*$("#printJS-form").printMe({
				"path" : ["css/compiled/theme_styles.css","css/footer.css","css/mystyle2.css"]
			});*/
	 }
	 this.customEmail = function (thisObj){
		 var wso = $(thisObj).attr("data");
		 wso = parseInt(wso);
		 window.open('email/wsolot/wsolotReport.pdf/'+wso, '_blank');
	 }
	 //Start wso search
	  this.wsolotSearch = function(){
		  var searchWsoLotNo = $("#searchWsoLotNo").val();
		  if(searchWsoLotNo == ''){
			  wlreport.loadAllWsoLotReport();
			  return false;
		  }
		  
		  $.ajax({
	          type: "GET",
	          contentType: "application/json",
	          url: contextPath+"/wsoInfos/getAllWsoreportsByWsoNo/" + searchWsoLotNo,
	          dataType: 'json',
	          cache: false,
	          timeout: 600000,
	          success: function (response) {
	              var data = response.data;
	              console.log(data)
	              $("#twsolotReportBody").empty();
	              var tr= "";
	              for(var i =0; i<data.length; i++){
	                  var wsoNo  = data[i].wsoNo;
	                  var totalWsoWeight  = data[i].totalWsoWeight;
	                  var cargo  = data[i].cargo;
	                  var totalNoOfPallets  = data[i].totalNoOfPallets;

	                  tr += "<tr>";
	                  tr += "<td style='text-align:right'>"+wsoNo+"</td>";
	                  tr += "<td style='text-align:right'>"+totalWsoWeight+"</td>";
	                  tr += "<td style='text-align:right'>"+totalNoOfPallets+"</td>";
	                  tr += "<td>"+cargo+"</td>";
	                  //tr += "<td>"+invoiceRate+"</td>";
	                  tr += "<td><button class='btb btn-info' data-toggle='modal' data-target='#dynamicModel2' onclick='wlreport.showWsoLotReport(" + data[i].wsoId + ")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
	                  tr += "</tr>";
	              }

	              $("#twsolotReportBody").append(tr);

	          },
	          error: function (e) {
	              var json = "<h4>Ajax Response</h4><pre>"
	                  + e.responseText + "</pre>";
	              $('#feedback').html(json);
	              console.log("ERROR : ", e);
	          }
	      });

	  }
		  
    
 }    