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
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: contextPath+ "/clientInfos/",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#clientDetails").empty();
            var tr="";
            for(var i=0; i<data.length; i++){
            
                var clientTitle = data[i].clientTitle;
                var clientAddress1   = data[i].clientAddress1 ;
                //clientAddress1  = escape(clientAddress1);
                clientAddress1 = unescape(clientAddress1);
                var clientCity   = data[i].clientCity ;
                var clientState   = data[i].clientState ;
                var clientCountry   = data[i].clientCountry ;
                var clientMobileNo   = data[i].contactPersonMobileNumbere ;
                tr += "<tr>";
                tr += "<td>"+
                    '<a href="#" style="font-size:20px;" onclick="home.loadClientTallySheets(\''+data[i].clientInfoId+'\')" ><i class="fa fa-search-plus"></i></a>'+
                    "</td>"+

                    "<td style='width: 200px;'>"+clientTitle+"</td> <td style='width: 300px;'> "+clientAddress1+" </td> <td style='width: 200px;'> "+clientCity+"</td> <td style='width: 200px;'> "+clientState+" </td> <td style='width: 200px;'> "+clientCountry+" </td> <td style='width: 200px;'> "+clientMobileNo+" </td> ";
                tr += "</tr>";
                tr += "<tr id='tallysheet"+data[i].clientInfoId+"' style='display:none;' class='viewTallysheetDetailscls'><td colspan='11'> ";
                tr +='<div style="background:##b1b1b112;width:95%;margin: 0 auto;">'
                tr += '<div class="table-responsive"><table class="table" ><thead><tr style="background-color: #b1b1b112;"><th>Action</th> <th>Tallysheet No</th> <th>Storage Date</th>  <th> ExVessel</th><th>Measurement</th></tr></thead><tbody id="tallyTableDetail'+data[i].clientInfoId+'"></tbody></table></div></div>';
                tr += " </td> </tr>"
            }
            $("#clientDetails").append(tr);

        },
        error: function (e) {
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
        }
    });

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