var REPORT_TALLYSHEET = function() {

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
        	  html += '<div class=\"col-lg-12 full-height\" id=\"tallysheetReportTable\">'
        	  html += '<div class=\"main-box clearfix full-height\">'
        	  html += '<header class=\"main-box-header clearfix\">'
        	  html += '<h2 class=\"pull-left\">Tallysheet Report</h2>'
        	  html += '<div class=\"filter-block pull-right\">'
        	  html += '<div class=\"form-group pull-left\">'
        	  html += '<input type=\"text\" class=\"form-control\" placeholder=\"Search Using Tallysheet No...\" style=\" margin-right:60px;float: right;\" id="searchTallysheetNo" onkeyup="treport.tallySearch();" margin-right:60px;float: right;\">'
        	  html += '<i class=\"fa fa-search search-icon\" style=\" margin-right:60px;float: right;\"></i>'
        	  html += '<a  href=\"index.html\" class=\"btn btn-danger\" style=\" margin-top: -35px;float: right;\">Exit</a>'
        	  html += '</div>'
        	  html += '</div>'
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
        	  html += '<span>TALLYSHEET NO</span>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<span>DATE OF STORAGE</span>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<span>EX VESSEL</span>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<span>CONTAINER NO</span>'
        	  html += '</th>'
        	  html += '<th>'
        	  html += '<span>Report View</span>'
        	  html += '</th>'
        	  html += '</tr>'
        	  html += '</thead>'
        	  html += '<tbody id=\"tallysheetReport\">'
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
        	  html += '<th>'
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
          treport.loadAllTallysheet();
    }
    
    //Start 
     this.loadAllTallysheet = function() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: contextPath+ "/tallysheets/getAllTallysheetreports",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (response) {
            data = response.data;
            console.log(data)
            $("#tallysheetReport").empty();
            var tr= "";
            for(var i =0; i<data.length; i++){
                var clientTitle  = data[i].clientTitle;
                var tallysheetNumber  = data[i].tallysheetNumber;
                var storageDate  = data[i].storageDate;
                storageDate = common.getFormattedDate(new Date(storageDate));
                var exVessel  = data[i].exVessel;
                var containerNo  = data[i].containerNo;
                //var invoicerate = data[i].invoicerate;

                tr += "<tr>";
                tr += "<td>"+clientTitle+"</td>";
                tr += "<td>"+tallysheetNumber+"</td>";
                tr += "<td>"+storageDate+"</td>";
                tr += "<td>"+exVessel+"</td>";
                tr += "<td>"+containerNo+"</td>";
                tr += "<td><button class='btb btn-info' data-toggle='modal' data-target='#dynamicModel2' onclick='treport.showTallysheetReport(" + data[i].tallysheetId + ")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
                // tr += "<td>"+invoicerate+"</td>";
                tr += "</tr>";
            }

            $("#tallysheetReport").append(tr);

        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
        }
    });
}
     // End Show tallysheet report

     //Start Show tallysheet report by id
 this.showTallysheetReport = function(tallysheetId) {
    tallysheet = parseInt(tallysheetId);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: contextPath+ "/tallysheets/getAllTallysheeetreports/" + tallysheet,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (response) {
            data = response.data;
            wsoDetail = response.wsoDetail;
            console.log(data)
            treport.loadtallyheeyReportspage(tallysheet,data,wsoDetail);

        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
        }
    });

}
 
 //End Show tallysheet report by id

//Start view tallysheet report by id
 this.loadtallyheeyReportspage = function(tallysheet,data1,wsoDetail){
    console.log(data1)
    var storageDate  = data1.storageDate;
    storageDate = common.getFormattedDate(new Date(storageDate));
    $("#tallysheetReportTable").empty().append('<div class="main-box clearfix"><button type="button" class="btn btn-info  mr-10" onclick="treport.show();" style="float:right">Back</button>'+
        '<form method="post" action="#" id="printJS-form"><header class="main-box-header clearfix">'+
        '</header>'+
        '<div class="main-box-body clearfix">'+
        '<div class="row imgclass" >'+
        '<img src="image/logo4.png"class="logo" style="width: 750px"><br><span class="tax">Tally-Sheet Report</span><br>'+
        '<hr>'+
        '</div>'+
        '<div class="row">'+
        '</div>'+
        '<div class="row">'+
        '<div class="col-lg-6 dldiv2">'+
        '<div class="form-group">'+
        '<label>TallySheet No : </label>&nbsp;&nbsp;'+
        '<label>'+data1.tallysheetNumber+' </label>'+
        '</div>'+
        '</div>'+
        '<div class="col-lg-6 dldiv2">'+
        '<div class="form-group">'+
        '<label>Date Of Storage : </label>&nbsp;&nbsp;'+
        '<label>'+storageDate+'</label>'+
        '</div>'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="col-lg-6">'+
        '<div class="form-group">'+
        '<label>Client Name : </label>&nbsp;&nbsp;'+
        '<label>'+data1.clientTitle+'</label>'+
        '</div>'+
        '</div>'+
        '<div class="col-lg-6 ">'+
        '<div class="form-group">'+
        '<label>Ex-Vessel : </label>&nbsp;&nbsp;'+
        '<label>'+data1.exVessel+'</label>'+
        '</div>'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="col-lg-6 dldiv2">'+
        '<div class="form-group">'+
        '<label>Unstuff Temp : </label>&nbsp;&nbsp;'+
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
        '<div class="row">'+
        '<div class="col-lg-6 dldiv2">'+
        '<div class="form-group">'+
        '<label>Ref/Dry : </label>&nbsp;&nbsp;'+
        '<label>'+data1.refDry+'</label>'+
        '</div>'+
        '</div>'+
        '<div class="col-lg-6 dldiv2">'+
        '<div class="form-group">'+
        '<label>Lorry/Container : </label>&nbsp;&nbsp;'+
        '<label>'+data1.lorryContainer+'</label>'+
        '</div>'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="col-lg-6 dldiv2">'+
        '<div class="form-group">'+
        '<label>Container No : </label>&nbsp;&nbsp;'+
        '<label>'+data1.containerNo+'</label>'+
        '</div>'+
        '</div>'+
        '</div>'+
        '<div class="row">'+
        '<div class="col-lg-12">'+
        '<div class="table-responsive ">'+
        '<table class="table tstable">'+
        '<thead>'+
        '<tr>'+
        '<th  class="td1" style="text-align:right">'+
        '<samp>WSO NO</samp>'+
        '</th>'+
        '<th class="td2">'+
        '<samp>DESCRIPTION/Remarks</samp>'+
        '</th>'+
        '<th class="td1">'+
        '<samp>Storage Type</samp>'+
        '</th>'+
        '<th class="td" style="text-align:right">'+
        '<samp>TOTAL WSO WEIGHT(KG)</samp>'+
        '</th>'+
        '<th class="td" style="text-align:right">'+
        '<samp>NO OF PALLETS</samp>'+
        '</th>'+
        '<th class="td" style="text-align:right">'+
        '<samp>WSO Lot QTY</samp>'+
        '</th>'+
        '</tr>'+
        '</thead>'+
        '<tbody id="tallysheetWsoBody">'+
        '</tbody>'+
        '<tfoot id="tallysheetFooter">'+
        
        '</tfoot>'+
        '</table>'+
        '</div>'+
        '</div>'+
        '</div>'+
        /*'<div class="row">'+
        '<button type="button" class="btn btn-success btn-lg btn-block">'+
        'Download PDF'+
        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
        '</button>'+
        '</div>'+*/
        '<p class="my-footer">1. Condition of Cargo:CONTENT UNCHECKED/CONDITION UNKNOWN.<br> 2. If there are any discrepancies,kindly inform us within 2 working days via E-mail. <br> 3. This is an electronically generated document, Signature is required.</p>'+
        '</div>'+
        '</div>'+
        '<button type="button" class="btn btn-info" data='+tallysheet+' onclick="treport.customPrintPdf(this);">' +
        'Download PDF'+
        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
        '</button>'+
        '<button type="button" class="btn btn-info" data='+tallysheet+' style="margin-left:20px"; onclick="treport.customPrintXls(this);">' +
        'Download Excel'+
        '<span class="glyphicon glyphicon-chevron-right">'+'</span>'+
        '</button>'+
        '<button type="button" class="btn btn-primary" style="margin-left:20px"; data='+tallysheet+' onclick="treport.customEmail(this);">' +
        'Send Email'+
        '<span class="glyphicon glyphicon-send">'+'</span>'+
        '</button>')

    var dnTr ="";
    var ttlWsoWgt = 0;
    var ttlPallets = 0;
    var wsottlQty = 0;
    for(var i =0; i<wsoDetail.length ; i++){
        dnTr  += "<tr><td class='td1' style='text-align:right' >"+wsoDetail[i].wsoNo+"</td>  <td class='td2'>"+wsoDetail[i].description+" / "+wsoDetail[i].remarks+"</td> <td class='td1'>"+wsoDetail[i].storageTypeName+"</td>  <td class='td' style='text-align:right'>"+wsoDetail[i].totalWsoWeight+"</td>  <td class='td' style='text-align:right'>"+wsoDetail[i].totalNoOfPallets+"</td> <td class='td' style='text-align:right'>"+wsoDetail[i].ttlWsoQuantity+"</td</tr>";
        ttlWsoWgt += parseFloat(wsoDetail[i].totalWsoWeight);
        ttlPallets += parseFloat(wsoDetail[i].totalNoOfPallets);
        wsottlQty += parseFloat(wsoDetail[i].ttlWsoQuantity);
    }
    $("#tallysheetWsoBody").append(dnTr,'<tr>'+
    	    '<td class="td">Total</td>'+
    	    '<td></td>'+
    	    '<td></td>'+
    	    '<td class="td" style="text-align:right">'+ttlWsoWgt+'</td>'+
    	    '<td class="td" style="text-align:right">'+ttlPallets+'</td>'+
    	    '<td class="td" style="text-align:right">'+wsottlQty+'</td>'+
        '</tr>');
    /*$("#tallysheetFooter").append(
    '<tr>'+
	    '<td>Total</td>'+
	    '<td></td>'+
	    '<td></td>'+
	    '<td>'+ttlWsoWgt+'</td>'+
	    '<td>'+ttlPallets+'</td>'+
	    '<td>'+wsottlQty+'</td>'+
    '</tr>'
    );*/
}

//End view tallysheet report by id
 
 
 this.customPrintPdf = function (thisObj){
	 var tallysheet = $(thisObj).attr("data");
	 tallysheet = parseInt(tallysheet);
	 window.open('download/tallysheet/tallysheetReport.pdf/pdf/'+tallysheet, '_parent');
	 //$("#printJS-form").printMe();
	 /*$("#printJS-form").printMe({
			"path" : ["css/compiled/theme_styles.css","css/bootstrap/bootstrap.min.css","css/mystyle2.css"]
		 "path" : ["css/compiled/theme_styles.css","css/footer.css","css/mystyle2.css"]
		});*/
 }
 this.customPrintXls = function (thisObj){
	 var tallysheet = $(thisObj).attr("data");
	 tallysheet = parseInt(tallysheet);
	 window.open('download/tallysheet/tallysheetReport.xls/xls/'+tallysheet, '_parent');
	 //$("#printJS-form").printMe();
	 /*$("#printJS-form").printMe({
			"path" : ["css/compiled/theme_styles.css","css/bootstrap/bootstrap.min.css","css/mystyle2.css"]
		 "path" : ["css/compiled/theme_styles.css","css/footer.css","css/mystyle2.css"]
		});*/
 }
 this.customEmail = function (thisObj){
	 var tallysheet = $(thisObj).attr("data");
	 tallysheet = parseInt(tallysheet);
	 window.open('email/tallysheet/tallysheetReport.pdf/'+tallysheet, '_blank');
 }
 
 
 this.tallySearch = function(){
	 var searchTallysheetNo = $("#searchTallysheetNo").val();
	 //	alert(searchTallysheetNo)
	 if(searchTallysheetNo == ''){
		 treport.loadAllTallysheet();
		 return false;
	 }
	 
	 $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/tallysheets/getAllTallysheetreportsByTallyNo/" + searchTallysheetNo,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	            data = response.data;
	            console.log(data)
	            $("#tallysheetReport").empty();
	            var tr= "";
	            for(var i =0; i<data.length; i++){
	                var clientTitle  = data[i].clientTitle;
	                var tallysheetNumber  = data[i].tallysheetNumber;
	                var storageDate  = data[i].storageDate;
	                storageDate = common.getFormattedDate(new Date(storageDate));
	                var exVessel  = data[i].exVessel;
	                var measurement  = data[i].measurement;

	                tr += "<tr>";
	                tr += "<td>"+clientTitle+"</td>";
	                tr += "<td>"+tallysheetNumber+"</td>";
	                tr += "<td>"+storageDate+"</td>";
	                tr += "<td>"+exVessel+"</td>";
	                tr += "<td>"+measurement+"</td>";
	                tr += "<td><button class='btb btn-info' data-toggle='modal' data-target='#dynamicModel2' onclick='treport.showTallysheetReport(" + data[i].tallysheetId + ")'><i class='glyphicon glyphicon-eye-open'></i></button></td>";
	              
	                tr += "</tr>";
	            }

	            $("#tallysheetReport").append(tr);

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
