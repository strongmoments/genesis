var login;
var client;
var bootAlert;
var common;
var tally;
var wso1;
var delivery;
var othercharge;
var billing;
var warehouseName;
var wreport;
var breport;
var home;
var user;
var payreport;
var finreport;
var setting;
var outbalreport;
var outrecreport;
var dateInventory;
var wsoenreport;
var soareport;
var nextbilling;
var paymentbilling;
var invoicelist;
var lotreport;
var wsobilling;
var customerlist

var contextPath = "/genesis";

$().ready(function(){
	initiate();
	loadDefaultInfo();
	countDetails();
	loadAllClient();
	//loadInventoryByClient();
	$(".time-picker").hunterTimePicker();
	addActiveClass();
	
});

function addActiveClass(){
	$( '#sidebar-nav .nav-stacked a' ).on( 'click', function () {
		$( '#sidebar-nav .nav-stacked' ).find( 'li.active' ).removeClass( 'active' );
		$( this ).parent( 'li' ).addClass( 'active' );
	});
}

function initiate(){
	login = new LOGIN();
	client = new CLIENT();
	bootAlert = new ALERT();
	tally = new TALLYSHEET();
	wso1 = new WSO();
	delivery = new DELIVERY_LIST();
	othercharge = new OTHER_CHARGE(); 
	billing = new BILLING();
	common = new COMMON();
	wreport = new REPORT_WSO();
	treport = new REPORT_TALLYSHEET();
	wlreport = new REPORT_WSOLOT();
	dlreport = new REPORT_DELIVERYLIST();
	home = new HOME();
	user = new USER();
	setting = new SETTING();
	payreport = new PAYREPORT();
	finreport = new FINREPORT();
	dateInventory = new DATEINVENTORY();
	outrecreport = new OUTRECREPORT();
	wsoenreport = new WSOENREPORT();
	outbalreport = new OUTBALREPORT();
	soareport = new SOAREPORT();
	nextbilling = new NEXTBILLING();
	paymentbilling = new PAYMENTBILLING();
	invoicelist = new INVOICELIST();
	lotreport = new LOTREPORT();
	wsobilling = new WSOBILLING();
	customerlist = new CUSTOMERLIST();
	
}

function loadDefaultInfo(){
		$.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/warehouseInfos/",
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        async : false,
	        success: function (data) {
	        	warehouseName =  data[0].warehouseName;
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
}


function countDetails(){
   $.ajax({
       type: "GET",
       contentType: "application/json",
       url: contextPath+ "/clientInfos/countDetails/",
       dataType: 'json',
       cache: false,
       timeout: 600000,
       success: function (response) {
           data = response.data;
           console.log(data)
           $("#totalClient").text(data.countClient);
           $("#totalTally").text(data.countTallysheet);
           $("#totalWso").text(data.countWsoInfo);
           $("#totalLot").text(data.countLotInfo);

       },
       error: function (e) {
           var json = "<h4>Ajax Response</h4><pre>"
               + e.responseText + "</pre>";
           $('#feedback').html(json);
           console.log("ERROR : ", e);
       }
   });
}

function loadAllClient(){
    $('#data-pagination').pagination({
        dataSource: contextPath+ "/clientInfos/",
        locator: 'data',
        totalNumberLocator: function(response) {
            return response.totalElement;
        },
        pageSize: 10,

        position: 'bottom',
        ajax: {
            beforeSend: function() {
                $("#clientDetails").empty();
                $("#clientDetails").append('<tr><td></td> <td></td> <td></td><td><div class="spinner-border" role="status">' +
                    '                <span class="visually-hidden">Loading...</span>' +
                    '              </div></td><tr>');
            }
        },
        callback: function(obj, pagination) {
            var from =pagination.pageNumber;

            if(pagination.pageNumber >1){
                from = pagination.pageNumber * pagination.pageSize;
                from = (from -pagination.pageSize )+1;
            }


            var to = 0;
            if(obj.length<pagination.pageSize){
                to =  (pagination.pageNumber * pagination.pageSize) - (pagination.pageSize-obj.length);
            }else{
                to =  pagination.pageNumber * pagination.pageSize;
            }

            var info  = "Showing "+from+" to "+to+" of "+pagination.totalNumber+" entries";
            $("#dataTable-info-id").empty();
            $("#dataTable-info-id").append(info);

            $("#cardTable").empty();
            $("#wsoMainContent").empty();
            $("#wsoMainContent").append('<div class="table-responsive"><table class="table"><thead><tr style="background-color:#e8e4e469;"><th></th><th>Client Name</th><th style="width:300px;">Address</th><th>City</th><th>State</th><th>Country</th></tr></thead><tbody id="wsoTableId"></tbody></table></div>');
            $("#clientDetails").empty();
            // template method of yourself
            var data = obj;

            var tr = "";
            for (var i = 0; i < data.length; i++) {
                var clientTitle = data[i].clientTitle;
                var clientAddress1 = data[i].clientAddress1;
                clientAddress1 = unescape(clientAddress1);
                var clientCity = data[i].clientCity;
                var clientState = data[i].clientState;
                var clientCountry = data[i].clientCountry;
                tr += "<tr>";
                tr += "<td><a href='#' onclick='client.editClientInfo(\"" + data[i].clientInfoId + "\")'  style='font-size:20px;' data-toggle='modal' data-target='#dynamicModel1' ><i class='fa fa-pencil'></i></a>&nbsp;&nbsp;" +

                    '<a href="#" style="font-size:20px;" onclick="client.loadClientStoragesss(\'' + data[i].clientInfoId + '\',\'' + clientTitle + '\')" >' +
                    "<i class='fa fa-search-plus '></i></a>&nbsp;&nbsp;</td>" +
                    /*"<a href='#' style='font-size:20px; color:#ff4444;'><i class='fa fa-trash-o'></i></a></td>"+
                     */
                    "<td>" + clientTitle + "</td> <td> <p>" + clientAddress1 + "</p> </td> <td> " + clientCity + "</td> <td> " + clientState + " </td> <td> " + clientCountry + " </td> ";
                tr += "</tr>";
                tr += "<tr id='wsoLot" + data[i].clientInfoId + "' style='display:none;' class='viewLotsDetailscls'><td colspan='11'> ";
                tr += '<div style="background:##b1b1b112;width:95%;margin: 0 auto;">'
                tr += '<div class="table-responsive"><table class="table" ><thead><tr><td colspan="10"><a href="#demo" class="btn btn-info wsoAndlot" style="float:right;" data-toggle="modal" data-target="#dynamicModel" onclick="client.openClientStoragePopu(\'' + data[i].clientInfoId + '\',\'\' ,\'' + data[i].clientTitle + '\')"> Add New Storage</a></td></tr><tr style="background-color: #b1b1b112;"><th>Action</th> <th>Storage Type</th> <th>Storage Start Date</th>  <th> Storage End Date</th><th> Monthly Rate</th><th>Handling Charges</th><th>Next Bill Date </th><th>Last Bill Date</th></tr></thead><tbody id="wsoLoTableDteail' + data[i].clientInfoId + '"></tbody></table></div></div>';
                tr += " </td> </tr>"
            }
            $("#clientDetails").append(tr);


        }
    })

}

function loadPaymentByClient(){
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: contextPath+ "/payments/outstandingBillEnquiry",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (response) {
        	data = response.data;
        	console.log(data)
  
        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
        }
    });
}