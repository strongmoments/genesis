var REPORT_WSO = function() {

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
        	  html += '<div class=\"row\">'
        	  html += '<div class=\"col-lg-12\" id=\"wsoTable\">'
        	  html += '<div class=\"main-box clearfix\">'
        	  html += '<header class=\"main-box-header clearfix\">'
        	  html += '<h2 class=\"pull-left\">WSO Report</h2>'
        	  html += '<div class=\"filter-block pull-right\">'
        	  html += '<div class=\"form-group pull-left\">'
        	  html += '<input type=\"text\" class=\"form-control\" placeholder=\"Search...\" style=\" margin-right:60px;float: right;\">'
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
        	  html += '<th>'
        	  html += '<span>Wso Number</span>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<span>Total Wso Weight</span>'
        	  html += '</th>'
        	  html += '<!--<th>'
        	  html += '<span>Total Wso Quantity</span>'
        	  html += '</th>-->'
        	  html += '<th>'
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
        	  html += '<tbody id=\"wsoReportBody\">'
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
        	  html += '<th>'
        	  html += '<samp>WSO NO</samp>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<samp>DESCRIPTION</samp>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<samp>TYPE</samp>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<samp>QTY</samp>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<samp>WEIGHT(KG)</samp>'
        	  html += '</th>'
        	  html += '<th>'
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
          	wreport.loadAllWsoReport();
    }
    
    //Start Load Wso 
     this.loadAllWsoReport = function() {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/wsoInfos/getAllWsoreports",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (response) {
                var data = response.data;
                console.log(data)
                $("#wsoReportBody").empty();
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
                    tr += "<td>"+wsoNo+"</td>";
                    tr += "<td>"+totalWsoWeight+"</td>";
                    tr += "<td>"+totalNoOfPallets+"</td>";
                    tr += "<td>"+cargo+"</td>";
                    //tr += "<td>"+invoiceRate+"</td>";
                    tr += "<td><button class='btb btn-info' data-toggle='modal' data-target='#dynamicModel2' onclick='wreport.showWsoReport(" + data[i].wsoId + ")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
                    // tr += "<td>"+invoicerate+"</td>";
                    tr += "</tr>";
                }

                $("#wsoReportBody").append(tr);

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
     
     //Start Load Wso By Id
     this.showWsoReport = function(wsoId){
    	    wso = parseInt(wsoId);
    	    $.ajax({
    	        type: "GET",
    	        contentType: "application/json",
    	        url: contextPath+ "/wsoInfos/getOnlyWsoreports/" + wso,
    	        dataType: 'json',
    	        cache: false,
    	        timeout: 600000,
    	        success: function (response) {
    	            var data = response.data;
    	            //var lotDetail = response.lotDetail;
    	            console.log(data)
    	            //console.log(lotDetail)
    	            wreport.loadWsoReportspage(data);

    	        },
    	        error: function (e) {
    	            var json = "<h4>Ajax Response</h4><pre>"
    	                + e.responseText + "</pre>";
    	            $('#feedback').html(json);
    	            console.log("ERROR : ", e);
    	        }
    	    });
    	}

     //End Load Wso By Id
     
     //Start View Wso information
     this.loadWsoReportspage = function(data1){
    	    console.log(data1)
    	    $("#wsoTable").empty().append('<div class="main-box clearfix">'+
    	        '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'+
    	        '<h2 class="pull-left">WSO Report</h2>'+
    	        '</header>'+
    	        '<div class="main-box-body clearfix">'+
    	        '<div class="row" style="text-align:center;">'+
    	        '<img src="image/logo3.png" style="width:450px; height:100px;">'+
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
    	        '<div class="col-lg-6">'+
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
    	        '<label>'+data1.storageDate+'</label>'+
    	        '</div>'+
    	        '</div>'+
    	        '</div>'+
    	        '<div class="row">'+
    	        '<div class="col-lg-12">'+
    	        '<div class="table-responsive">'+
    	        '<table class="table">'+
    	        '<thead>'+
    	        '<tr>'+
    	        '<th>'+
    	        '<samp>WSO NO</samp>'+
    	        '</th>'+
    	        '<th>'+
    	        '<samp>DESCRIPTION</samp>'+
    	        '</th>'+
    	        '<th>'+
    	        '<samp>WEIGHT</samp>'+
    	        '</th>'+
    	        '<th>'+
    	        '<samp>QUANTITY</samp>'+
    	        '</th>'+
    	        '<th>'+
    	        '<samp>PALLETS QUANTITY</samp>'+
    	        '</th>'+
    	        '<th>'+
    	        '<samp>REMARKS</samp>'+
    	        '</th>'+
    	        '</tr>'+
    	        '</thead>'+
    	        '<tbody id="newWsoBody">'+
    	        '</tbody>'+
    	        '<tfoot id="newWsoFooter">'+
    	        '</tfoot>'+
    	        '</table>'+
    	        '</div>'+
    	        '</div>'+
    	        '</div>'+
    	        /*'<div class="row">'+
    	        '<button type="button" class="btn btn-success btn-lg btn-block" id="wsoPrint">'+
    	        'Download PDF'+
    	        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
    	        '</button>'+
    	        '</div>'+*/
    	        '</div>'+
    	        '</div>'+
    	        '<button type="button" onclick="printJS(\'printJS-form\', \'html\')">' +
    	        'Download PDF'+
    	        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
    	        '</button>')

    	    var dnTr ="";
    	    var ttlPallets1 = 0;
    	    var ttlLotQuantity1 = 0;
    	    var ttlLotWeight1 = 0;
    	      //for(var i =0; i<lotDetail.length ; i++){
    	          dnTr  += "<tr><td>"+data1.wsoNo+"</td> <td>"+data1.description+"</td>   <td>"+data1.totalWsoWeight+"</td>  <td>"+data1.lotQuantity+"</td>  <td>"+data1.totalPallets+"</td>  <td>"+data1.remarks+"</td> </tr>";
    	          ttlLotWeight1 += parseFloat(data1.totalWsoWeight);
    	          ttlLotQuantity1 += parseFloat(data1.lotQuantity);
    	          ttlPallets1 += parseFloat(data1.totalPallets);
    	      //}
    	      $("#newWsoBody").append(dnTr);
    	    $("#newWsoFooter").append(
    	    '<tr>' +
    	    '<td>Total : </td><td></td>' +
    	    '<td>'+ttlLotWeight1+'</td>' +
    	    '<td>'+ttlLotQuantity1+'</td>' +
    	    '<td>'+ttlPallets1+'</td>' +
    	    '<td></td>'+
    	    '</tr>'
    	    );
    	}
     //End View Wso Information
}    