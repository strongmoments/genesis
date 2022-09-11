
var REPORT_DELIVERYLIST = function() {

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
        	  html += '<!-- WSO Report -->'
        	  html += '<div class=\"row table-style\">'
        	  html += '<div class=\"col-lg-12 full-height\" id=\"deliveryListReportTable\">'
        	  html += '<div class=\"main-box full-height clearfix\">'
        	  html += '<header class=\"main-box-header clearfix\">'
        	  html += '<h2 class=\"pull-left\">DeliveryList Report</h2>'
        	  html += '<div class=\"filter-block pull-right\">'
        	  html += '<div class=\"form-group pull-left\">'
        	  html += '<input type=\"text\" class=\"form-control\" placeholder=\"Search Using DlNo...\" id="searchDlNo" onkeyup="dlreport.DlSearch();" style=\" margin-right:60px;float: right;\">'
        	  html += '<i class=\"fa fa-search search-icon\" style=\" margin-right:60px;float: right;\"></i>'
        	  html += '<a  href=\"index.html\" class=\"btn btn-danger\" style=\" margin-top: -35px;float: right;\">Exit</a>'
        	  html += '</div>'
        	  html += '</div>'
        	  html += '</header>'
        	  html += '<div class=\"main-box-body clearfix datesscroll report\">'
        	  html += '<div class=\"table-responsive clearfix\">'
        	  html += '<table class=\"table table-hover\">'
        	  html += '<thead>'
        	  html += '<tr>'
        	  html += '<th>'
        	  html += '<span>CLIENT NAME</span>'
        	  html += '</th>'
        	  html += '<th style="text-align:right">'
        	  html += '<span>WSO NO</span>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<span>DATE OF DELIVRY</span>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<span>TIME OF ISSUE</span>'
        	  html += '</th>'
        	  html += '<th style="text-align:right">'
        	  html += '<span>DL NO</span>'
        	  html += '</th>'
        	html += '<th>'
              html += '<span>VEHICLE NO</span>'
              html += '</th>'
              html += '<th>'
              html += '<span>NAME OF RECEIVER</span>'
              html += '</th>'
        	  html += '<th>'
        	  html += '<span>Report View</span>'
        	  html += '</th>'
        	  html += '</tr>'
        	  html += '</thead>'
        	  html += '<tbody id=\"deliveryListReportBody\">'
        	  html += ''
        	  html += '</tbody>'
        	  html += '</table>'
        	  html += '</div>'
        	  html += '<div class=\"modal fade\" id=\"dynamicModel1\" role=\"dialog\" tabindex=\"-1\">'
        	  html += '<div class=\"modal-dialog\">'
        	  html += '<div class=\"modal-content\">'
        	  html += '<div class=\"modal-header\" style=\"background: #7FC8BA; color: #fff;\">'
        	  html += '<h3 id=\"dmhId\">TALLYSHEET Report</h3>'
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
        	  html += '<th style="text-align:right">'
        	  html += '<samp>TALLYSHEET NO</samp>'
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
        	  html += '<tbody id="tallysheetReport">'
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
        	  html += '</footer>'
             $("#content-wrapper").append(html);		
          dlreport.loadAllDeliveryList();
    }
    
    // Start Show DL 
     this.loadAllDeliveryList = function() {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+"/deliveryLists/getAllDeliveryListreports",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (response) {
                data = response.data;
                console.log(data)
                $("#deliveryListReportBody").empty();
                var tr= "";
                for(var i =0; i<data.length; i++){
                    var clientTitle  = data[i].clientTitle;
                    var wsoNo  = data[i].wsoNo;
                    var dateOfDelivery  = data[i].dateOfDelivery;
                    dateOfDelivery = common.getFormattedDate(new Date(dateOfDelivery));
                    var dlNo  = data[i].dlNo;
                    var timeOfIssue  = data[i].timeOfIssue;
                    var vehicleNo  = data[i].vehicleNo;
                    //var totalQty  = data[i].totalQty;
                    var nameOfReceiver = data[i].nameOfReceiver;

                    tr += "<tr>";
                    tr += "<td>"+clientTitle+"</td>";
                    tr += "<td style='text-align:right'>"+wsoNo+"</td>";
                    tr += "<td>"+dateOfDelivery+"</td>";
                    tr += "<td>"+timeOfIssue+"</td>";
                    tr += "<td style='text-align:right'>"+dlNo+"</td>";
                    tr += "<td>"+vehicleNo+"</td>";
                    //tr += "<td>"+totalQty+"</td>";
                    tr += "<td>"+nameOfReceiver+"</td>";
                    tr += "<td><button class='btb btn-info' data-toggle='modal' data-target='#dynamicModel2' onclick='dlreport.showDeliveryListReport(" + data[i].outgoingInventoryId + ")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
                    // tr += "<td>"+invoicerate+"</td>";
                    tr += "</tr>";
                }

                $("#deliveryListReportBody").append(tr);

            },
            error: function (e) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);
                console.log("ERROR : ", e);
            }
        });
    }
    //End Dl list

    // Start show dl By ID
     this.showDeliveryListReport = function(outgoingInventoryId) {
        outgoingInventory = parseInt(outgoingInventoryId)
        //openClientPopu(tallysheetId);
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+"/deliveryLists/getDeliveryListreports/" + outgoingInventory,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (response) {
                data = response.data
                deliveryLotDetail = response.deliveryLotDetail
                console.log(data)
                console.log(deliveryLotDetail)
                dlreport.loaddeliveryListReportPage(outgoingInventory,data,deliveryLotDetail);
            },
            error: function (e) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);
                console.log("ERROR : ", e);
            }
        });

    }
    
    //End DL by ID

    // Start Show Dl by Id
     this.loaddeliveryListReportPage = function(outgoingInventory,data,deliveryLotDetail){
    	 dateOfDelivery = common.getFormattedDate(new Date(data.dateOfDelivery));
    	 var date = new Date();
         var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
         var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
         var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
         time = hours + ":" + minutes + ":" + seconds;
        $("#deliveryListReportTable").empty().append('<div class="main-box clearfix"><button type="button" class="btn btn-info  mr-10" onclick="dlreport.show();" style="float:right">Back</button>'+
            '<header class="main-box-header clearfix">'+
            /*'<button class="btn btn-info" onclick="loadAllList()" style="float:right;">Back</button>'+*/
            '</header>'+
            '<div class="main-box-body clearfix">'+
            '<form method="post" action="#" id="printJS-form"><div class="row" style="text-align:center;">'+
            '<img src="image/logo4.png" style="width:750px; height:100px;"><br><span class="tax">Delivery-List Report</span><br'+
            '<hr>'+
            '</div>'+  
            '<div class="row">'+
            '<div class="col-lg-4 dldiv1">'+
            '<div class="form-group">'+
            '<label>Date Of Delivery : </label>&nbsp;&nbsp;<label>'+dateOfDelivery+'</label>'+

            '</div>'+
            '</div>'+
            '<div class="col-lg-4 dldiv1">'+
            '<div class="form-group">'+
            '<label>Printed Time</label>&nbsp;&nbsp;<label>'+time+'</label>'+

            '</div>'+
            '</div>'+
            '<div class="col-lg-4 dldiv1">'+
            '<div class="form-group">'+
            '<label>Customer : </label>&nbsp;&nbsp;<label>'+data.clientTitle+'</label>'+

            '</div>'+
            '</div>'+
            '</div>'+
            '<div class="row">'+
            '<div class="col-lg-12">'+
            '<div class="col-lg-8"></div>'+
            '<div class="col-lg-4">'+
            '<div class="form-group">'+
            '<label>Page No 1</label> &nbsp;&nbsp;<label></label>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '<div class="row">'+
            '<div class="col-lg-12">'+
            '<div class="col-lg-8"></div>'+
            '<div class="col-lg-4">'+
            '<div class="form-group">'+
            '<label>D/L No : </label>&nbsp;&nbsp;<label>'+data.dlNo+'</label>'+

            '</div>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '<div class="row">'+
            '<div class="col-lg-8 dltable">'+
            '<div class="table-responsive">'+
            '<table class="table">'+
            '<thead>'+
            '<tr>'+
            '<th style="width: 20%; text-align:right">'+
            '<samp>WSO NO</samp>'+
            '</th>'+
            '<th style="width: 20%; text-align:right">'+
            '<samp>LOT NO</samp>'+
            '</th>'+
            '<th style="width: 20%; text-align:right">'+
            '<samp>QUANTITY</samp>'+
            '</th>'+
            '<th style="width: 60%;">'+
            '<samp>DESCRIPTION</samp>'+
            '</th>'+

            '</tr>'+
            '</thead>'+
            '<tbody id="wsoDeliveryBody">'+


            '</tbody>'+
            '<tfoot id="wsoDeliveryFooter">'+

            '</tfoot>'+
            '</table>'+
            '</div>'+
            '</div>'+
            '<div class="col-lg-4 dldiv">'+
            '<h4>OPERATIONS CHECKERS ONE</h4>'+
            '<div class="form-group">'+
            '<label>Name : </label>&nbsp;&nbsp;<label></label>'+
            '</div>'+
            '<div class="form-group">'+
            '<label>ID No : </label>&nbsp;&nbsp;<label></label>'+
            '</div>'+
            '<div class="form-group">'+
            '<label>Time Issue : </label> &nbsp;&nbsp;<label>'+data.timeOfIssue+'</label>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '<div class="row">'+
            '<div class="col-lg-4 dldiv1">'+
            '<div class="form-group">'+
            '<label>Name Of Receiver : </label> &nbsp;&nbsp;<label>'+data.nameOfReceiver+'</label>'+
            '</div>'+
            '</div>'+
            '<div class="col-lg-4 dldiv1">'+
            '<div class="form-group">'+
            '<label>NRIC No : </label> &nbsp;&nbsp;<label>'+data.nricOfReceiver+'</label>'+

            '</div>'+
            '</div>'+
            '<div class="col-lg-4 dldiv1">'+
            '<div class="form-group">'+
            '<label>Vehicle No : </label> &nbsp;&nbsp;<label>'+data.vehicleNo+'</label>'+

            '</div>'+
            '</div>'+
            '</div>'+
            '<footer class="my-footer"> Please retain this Delivery List for verification of charge. No other supporting document will be given.</footer>'+
            /*'<div class="row">'+
            '<button type="button" class="btn btn-success btn-lg btn-block" onclick="printJS(\'/printjs.pdf\')">'+
            'Download PDF'+
            '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
            '</button>'+
            '</div>'+*/
            '</div>'+

            '</div></form>'+
            '<button type="button" class="btn btn-info" data='+outgoingInventory+' onclick="dlreport.customPrintPdf(this);">' +
            'Download PDF'+
            '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
            '</button>'+
            '<button type="button" class="btn btn-info" data='+outgoingInventory+' style="margin-left:20px"; onclick="dlreport.customPrintXls(this);">' +
            'Download Excel'+
            '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
            '</button>'+
            '<button type="button" class="btn btn-primary" data='+outgoingInventory+' style="margin-left:20px"; onclick="dlreport.customEmail(this);">' +
            'Send Email'+
            '<span class="glyphicon glyphicon-send">'+'</span>'+
            '</button>');

        var dnTr ="";
        var ttlLotQuantity = 0;
        for(var i =0; i<deliveryLotDetail.length ; i++){
            dnTr  += "<tr><td style='text-align:right'>"+deliveryLotDetail[i].wsoNo+"</td>  <td style='text-align:right'>"+deliveryLotDetail[i].lotNo+"</td>  <td style='text-align:right'>"+deliveryLotDetail[i].lotQuantity+"</td> <td>"+deliveryLotDetail[i].description+"</td>   </tr>";
            ttlLotQuantity += parseFloat(deliveryLotDetail[i].lotQuantity);
        }
        $("#wsoDeliveryBody").append(dnTr,'<tr>'+
                '<td>'+
                '<samp>TOTAL</samp>'+
                '</td>' +
                '<td></td>'+
                
                '<td style="text-align:right">'+ttlLotQuantity+'</td>'+
                '<td></td>'+
                '</tr>');
        /*$("#wsoDeliveryFooter").append(
            '<tr>'+
            '<td>'+
            '<samp>TOTAL</samp>'+
            '</td>' +
            '<td></td>'+
            
            '<td>'+ttlLotQuantity+'</td>'+
            '<td></td>'+
            '</tr>'
        );*/
    }
    
    //End Show Dl No by Id
     
     this.customPrintPdf = function (thisObj){
    	 var dlId = $(thisObj).attr("data");
    	 dlId = parseInt(dlId);
		 window.open('download/deliverylist/deliverylistReport.pdf/pdf/'+dlId, '_parent');
		 //$("#printJS-form").printMe();
		/* $("#printJS-form").printMe({
				"path" : ["css/compiled/theme_styles.css","css/bootstrap/bootstrap.min.css",]
			 "path" : ["css/footer.css","css/bootstrap/bootstrap.min.css",]
			});*/
	 }
     this.customPrintXls = function (thisObj){
    	 var dlId = $(thisObj).attr("data");
    	 dlId = parseInt(dlId);
		 window.open('download/deliverylist/deliverylistReport.xls/xls/'+dlId, '_parent');
		 //$("#printJS-form").printMe();
		/* $("#printJS-form").printMe({
				"path" : ["css/compiled/theme_styles.css","css/bootstrap/bootstrap.min.css",]
			 "path" : ["css/footer.css","css/bootstrap/bootstrap.min.css",]
			});*/
	 }
     
     this.customEmail = function (thisObj){
    	 var dlId = $(thisObj).attr("data");
    	 dlId = parseInt(dlId);
		 window.open('email/deliverylist/deliverylistReport.pdf/'+dlId, '_blank');
	 }
     
     this.DlSearch = function(){
    	 var searchDlNo = $("#searchDlNo").val();
    	 if(searchDlNo == ''){
    		 dlreport.loadAllDeliveryList();
    		 return false;
    	 }
    	 
    	 $.ajax({
             type: "GET",
             contentType: "application/json",
             url: contextPath+"/deliveryLists/getAllDeliveryListreportsByDlNo/" + searchDlNo,
             dataType: 'json',
             cache: false,
             timeout: 600000,
             success: function (response) {
                 data = response.data;
                 console.log(data)
                 $("#deliveryListReportBody").empty();
                 var tr= "";
                 for(var i =0; i<data.length; i++){
                     var clientTitle  = data[i].clientTitle;
                     var wsoNo  = data[i].wsoNo;
                     var dateOfDelivery  = data[i].dateOfDelivery;
                     dateOfDelivery = common.getFormattedDate(new Date(dateOfDelivery));
                     var dlNo  = data[i].dlNo;
                     var timeOfIssue  = data[i].timeOfIssue;
                     var vehicleNo  = data[i].vehicleNo;
                     //var totalQty  = data[i].totalQty;
                     var nameOfReceiver = data[i].nameOfReceiver;

                     tr += "<tr>";
                     tr += "<td>"+clientTitle+"</td>";
                     tr += "<td>"+wsoNo+"</td>";
                     tr += "<td>"+dateOfDelivery+"</td>";
                     tr += "<td>"+timeOfIssue+"</td>";
                     tr += "<td>"+dlNo+"</td>";
                     tr += "<td>"+vehicleNo+"</td>";
                     //tr += "<td>"+totalQty+"</td>";
                     tr += "<td>"+nameOfReceiver+"</td>";
                     tr += "<td><button class='btb btn-info' data-toggle='modal' data-target='#dynamicModel2' onclick='dlreport.showDeliveryListReport(" + data[i].outgoingInventoryId + ")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
                     // tr += "<td>"+invoicerate+"</td>";
                     tr += "</tr>";
                 }

                 $("#deliveryListReportBody").append(tr);

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
