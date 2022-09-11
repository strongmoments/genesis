
var WSO = function() {
	var wso_tally_Verified = false;
	var clientStorageInfo = new Array();
	var sessionClientId="";
	var sessiontallyId = "";
	var currentLotWsoWtStatus = "";
	var currentLotNoStatus = "";
	
	this.show = function(clientId,tallySheetNo) {
        $("#content-wrapper").empty();
        	var html = "";
        	html += '<div class=\"row\">'
        		html += '<div class=\"col-lg-12\">'
        		html += '<div class=\"row\">'
        		html += '<div class=\"col-lg-12\">'
        		html += '<h1>WSO Entry</h1>'
        		html += '</div>'
        		html += '<div class=\"col-lg-12\">'
        		html += '<select class=\"form-control\" id=\"clientInfo\" style=\"width: 22em;float: left;\" onchange="wso1.onClientSelect(this);">'
        		html += '<option></option>'
        		html += '</select>'
        		html += '<select class=\"form-control\" id=\"tallySheetNo\" style=\"width: 18em;\" onchange="wso1.onTallySheetSelect(this)">'
        		html += '<option></option>'
        		html += '</select>'
        		html += '<div class="filter-block pull-right">'
        			
        		html += '<input type="text" class="form-control" style="width:15em;margin-right:180px;margin-top: -46px" placeholder="Search Using Wso No..." id="searchWsoNo" onkeyup="wso1.wsoSearch();">'
		        html += '<button type=\"button\" class=\"btn btn-primary  mr-10 wsoAndlot\" id=\"addnewWso\" style=\"margin-top: -41px; margin-right:60px; float: right;\"  onclick=\"wso1.openWsoPopu();\">Add New Wso</button>'
        		html += '<a  href=\"index.html\" class=\"btn btn-danger\" style=\"margin-top: -41px;float: right;\">Exit</a>'
        		html += '</div>'
        		html += '</div>'
        		html += '<div class=\"row table-style\">'
        		html += '<div class=\"col-lg-12 full-height\">'
        		html += '<form role=\"form\" id=\"wsoForm\" class=\"full-height\">'
        		html += '<!-- WSO TABLE START -->'
        		html += '<div class=\"main-box datesscroll wsostyle-overflow\" id=\"wsoMainContent\">'
        		html += '<div class=\"table-responsive\">'
        		html += '<table class=\"table\">'
        		html += '<thead>'
        		html += '<tr>'
        		html += '<th>WSO No</th>'
        		html += '<th>WSO Wt</th>'
        		html += '<th>Cargo</th>'
        		html += '<th>Stroage Class</th>'
        		html += '<th>No Of Pallets</th>'
        		html += '<!--<th>GST</th>-->'
        		html += '<!--<th>Invoice Rate</th>-->'
        		html += '<!--<th>Tally Sheet</th>'
        		html += '<th>Client</th>-->'
        		html += '</tr>'
        		html += '</thead>'
        		html += '<tbody id=\"wsoTableId\">'
        		html += '</tbody>'
        		html += '</table>'
        		html += '</div>'
        		html += '</div>'
        		html += '<!-- END OF WSO TABLE  -->'
        		html += '</form>'
        		html += '</div>'
        		html += '</div>'
        		html += '</div>'
        		html += '<!-- popup code -->'
        		html += '<div class=\"modal fade\" id=\"dynamicModel\" role=\"dialog\" tabindex=\"-1\">'
        		html += '<div class=\"modal-dialog\">'
        		html += '<div class=\"modal-content\">'
        		html += '<div class=\"modal-header\" style=\"background: #7FC8BA; color: #fff;\">'
        		html += '<button type=\"button\" class=\"close\" data-dismiss=\"modal\">X</button>'
        		html += '<h3 id=\"dmhId\"></h3>'
        		html += '</div>'
        		html += '<div class=\"modal-body\" id=\"modelBodyId\">'
        		html += '</div>'
        		html += '</div>'
        		html += '</div>'
        		html += '</div>'
        		html += '<div class=\"modal fade\" id=\"dynamicModel1\" role=\"dialog\" tabindex=\"-1\">'
        		html += '<div class=\"modal-dialog\">'
        		html += '<div class=\"modal-content\">'
        		html += '<div class=\"modal-header\" style=\"background: #7FC8BA; color: #fff;\">'
        		html += '<button type=\"button\" class=\"close\" data-dismiss=\"modal\">X</button>'
        		html += '<h3 id=\"dmhId1\"></h3>'
        		html += '</div>'
        		html += '<div class=\"modal-body\" id=\"modelBodyId1\">'
        		html += '</div>'
        		html += '</div>'
        		html += '</div>'
        		html += '</div>'
        		html += '<!--end of popup code  -->'
        		html += '</div>'
        		html += '<footer id=\"footer-bar\" class=\"row\">'
        		html += '<p id=\"footer-copyright\" class=\"col-xs-12\">'
        		html += 'Powered by Sandrokottos From StrongMoments Pvt Ltd'
        		html += '</p>'
        		html += '</footer>' 
          $("#content-wrapper").append(html);	
        wso1.loadAllClient();
        if(clientId != undefined && tallySheetNo != undefined ){
    		$('#clientInfo').val(clientId);
    		wso1.loadClientTallySheets(clientId,tallySheetNo);
    		$('#tallySheetNo').val(tallySheetNo);
    		wso1.loadallWso(clientId,tallySheetNo);
    	}
        if(tallySheetNo != undefined){
        	$('#tallySheetNo').val(tallySheetNo.toString());
        }
        wso_tally_Verified  =  $( "#tallySheetNo option:selected" ).attr("data");
	    
	    if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
	    	$(".wsoAndlot").hide();
	    }else{
	    	$(".wsoAndlot").show();
	    }
      
        }
	
	this.loadAllClient = function (){
		$.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/clientInfos/allClientTitle/",
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        async:false,
	        success: function (data) {
	            $('#clientInfo').empty();
	            $('#clientInfo').append("<option value=''>Select Client</option>");
	            for(var i=0;i<data.length;i++){
	                $('#clientInfo').append(
	                    "<option value="+data[i].clientInfoId+">"+data[i].clientTitle+"</option>"
	                );
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
	
	
	this.onClientSelect = function(){
		 var clientId = $( "#clientInfo" ).val();
		    $("#wsoTableId").empty();
		    if(clientId == "" || clientId == "Select Client"){
		    	$('#tallySheetNo').empty();
		        $('#wsoNo').empty();
		        $('#tallySheetNo').append("<option value=''>Select Tally Sheet</option>")
		       
		    }else{
		    	wso1.loadClientTallySheets(clientId);	
		    }
	}
	
	
	this.loadClientTallySheets = function (id){
		 $.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+ "/tallysheets/getAllTallyByClientId/"+id,
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        async:false,
		        success: function (data) {
		            $('#tallySheetNo').empty();
		            $('#wsoNo').empty();
		            $('#tallySheetNo').append("<option value=''>Select Tally Sheet</option>")
		            var option = "";
		            for(var i=0;i<data.length;i++){
		            	option += "<option data="+data[i].verify+" value="+data[i].tallysheetId+">"+data[i].tallysheetNumber+"</option>"    
		            }
		            $('#tallySheetNo').append(option);
		        },
		        error: function (e) {
		            var json = "<h4>Ajax Response</h4><pre>"
		                + e.responseText + "</pre>";
		            $('#feedback').html(json);
		            console.log("ERROR : ", e);
		        }
		    });
		
	}
	
	this.onTallySheetSelect = function (thisObj){
		 var tallyId = $( "#tallySheetNo" ).val();
		    var clientId = $("#clientInfo").val();
		    var tallyId = $("#tallySheetNo").val();
		    wso1.loadallWso(clientId,tallyId);
		    wso_tally_Verified  =  $( "#tallySheetNo option:selected" ).attr("data");
		    
		    if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
		    	$(".wsoAndlot").hide();
		    }else{
		    	$(".wsoAndlot").show();
		    }
	}

	
	this.loadallWso = function (clientId, tallysheetId){
		 var jsondata = {"clientId":clientId,"tallysheetId":tallysheetId}
	     $.ajax({
	            type: "GET",
	            contentType: "application/json",
	            data: jsondata,
	            url: contextPath+ "/wsoInfos/loadAllWsos",
	            dataType: 'json',
	            cache: false,
	            timeout: 600000,
	            success: function (data) {
	                $("#wsoMainContent").empty();
	                $("#wsoMainContent").append('<div class="table-responsive"><table class="table"><thead><tr style="background-color:#e8e4e469;"><th></th><th style="text-align:right">WSO No</th><th style="text-align:right">WSO Wt</th><th>Cargo</th><th>Stroage Class</th><th style="text-align:right">No Of Pallets</th><th style="text-align:right">GST</th><th style="text-align:right">Invoice Rate</th></tr></thead><tbody id="wsoTableId"></tbody></table></div>');
	                $("#wsoTableId").empty();
	                var tr="";
	                for(var i=0; i<data.length; i++){
	                    var wsoNo = data[i].wsoNo;
	                    var totalWsoWeight   = data[i].totalWsoWeight.toFixed(0) ;
	                    var cargo   = data[i].cargo ;
	                    var storageClass   = data[i].storageClass.storageTypeName ;
	                    var totalNoOfPallets   = data[i].totalNoOfPallets ;
	                    var gst   = data[i].gst ;
                        var invoiceRate   = data[i].invoiceRate.toFixed(2) ;
	                    var tallysheetText =  $("#tallySheetNo selected").text();
	                    var clientText =  $("#clientInfo selected").text(); 
	                    tr += "<tr>";
	                    tr += "<td><a href='#' onclick='wso1.editWsoForDelevery(" + data[i].wsoId + ",this)'  style='font-size:20px;' data-toggle='modal' data-target='#dynamicModel1' ><i class='fa fa-pencil'></i></a>&nbsp;&nbsp;" +
	                    
	                    "<a href='#' style='font-size:20px;' data="+wsoNo+" onclick='wso1.loadWsolotsss(" + data[i].wsoId + ",this)' >" +
	                    "<i class='fa fa-search-plus '></i></a>&nbsp;&nbsp;" +
	                    "<a href='#' class='wsoAndlot1' style='font-size:20px; color:#ff4444;' onclick='wso1.deleteWso(" +data[i].wsoId + ",this) '><i class='fa fa-trash-o'></i></a>"+
	                    
	                    "</td><td style='text-align:right'>"+wsoNo+"</td> <td style='text-align:right'> "+totalWsoWeight+" </td> <td> "+cargo+"</td> <td> "+storageClass+" </td> <td style='text-align:right'> "+totalNoOfPallets+" </td><td style='text-align:right'> "+gst+"% </td> <td style='text-align:right'> $"+invoiceRate+" </td>  ";
	                    tr += "</tr>";
	                    tr += "<tr id='wsoLot"+data[i].wsoId+"' style='display:none;' class='viewLotsDetailscls'><td colspan='11'> ";
	                    tr +='<div style="background:##b1b1b112;width:95%;margin: 0 auto;">'
	                    tr += '<div class="table-responsive"><table class="table" ><thead><tr><td colspan="10"><a href="#demo" class="btn btn-info wsoAndlot" style="float:right;" data-toggle="modal" data-target="#dynamicModel" data='+wsoNo+'  onclick="wso1.openLotPopu('+data[i].wsoId+',this)"> Add New Lot</a></td></tr><tr style="background-color: #b1b1b112;"><th> </th> <th>Description</th>  <th style="text-align:right"> Lot No</th><th style="text-align:right"> Lot Qty</th><th style="text-align:right"> Lot Wt(Kg) </th><th style="text-align:right">Lot Gross Wt(Kg) </th><th>Production Date </th><th>Expiry Date</th><th>Room No </th></tr></thead><tbody id="wsoLoTableDteail'+data[i].wsoId+'"></tbody></table></div></div>';
	                    tr += " </td> </tr>"
	                }
	                //alert(JSON.stringify(data));vz
	                $("#wsoTableId").append(tr);
	                
	                if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
	                	$(".wsoAndlot1").hide();
	                }else{
	                	$(".wsoAndlot1").show();
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
	
	
	this.openWsoPopu = function (wsoId){
		var clientId = $("#clientInfo").val();
		var tallySheetNo = $("#tallySheetNo").val();
		if(clientId == undefined ||  clientId == "" || clientId == "Select Client"){
			bootAlert.show("error","Please Sellect Client");
			return false
		}
		if(tallySheetNo == undefined ||  tallySheetNo == ""){
			bootAlert.show("error","Please Sellect Tally Sheet");
			return false
		}
		wso1.loadClientStorage(clientId);
	    $('#dynamicModel1').modal('show');
	    if(wsoId == undefined){
	        wsoId = "";
	    }
	    $("#dmhId1").empty();
	    $("#modelBodyId1").empty();
	    $("#dmhId1").append(" WSO Entry");
	    $("#modelBodyId1").append('<div class="row"><div class="col-lg-6"><div class="form-group"><label>Total WSO Wt</label><input type="number" step="0.00" min="0" class="form-control" id="totalWsoWeight" placeholder="Total WSO Wt"></div></div><div class="col-lg-6"><div class="form-group"><label>Cargo</label><input type="text" class="form-control" id="cargo" placeholder="Cargo"></div></div></div>'+
	        '<div class= "row" > <div class="col-lg-6"><div class="form-group"><label>Stroage Types</label>  <select class="form-control" id="storageClass" onchange="wso1.setInvoiceRate(this)"><option value="" id="storageClass">Select Storage Types</option></select> </div></div><div class="col-lg-6"><div class="form-group"><label>Total No Of Pallets</label><input type="number" min="0" max="20000" step="1" class="form-control" id="totalNoOfPallets" placeholder="Total No Of"></div></div></div>'+
	        '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Description</label> <textarea  rows="3" cols="50" class="form-control" id="description" placeholder="Description"></textarea></div></div><div class="col-lg-6"><div class="form-group"><label>Remarks</label><textarea  rows="3" cols="50" class="form-control" id="remarks" placeholder="Remarks"></textarea></div></div></div>'+
	        '<div class="row"><div class="col-lg-6"><label>Invoice Rate</label><input type="number" step="0.0" min="0" class="form-control" id="invoiceRate" placeholder="Invoice Rate"></div></div></div>'+
	        '<div><div type="button" class="btn btn-primary  mr-10 wsoAndlot" style="margin-top:30px;" onclick="wso1.saveWSOInfo('+wsoId+');">Save WSO</div>'+
	        '<div type="button" class="btn btn-warning  mr-10" style="margin-top:30px;margin-left:30px;" data-dismiss="modal">Cancel</div></div>');
	    if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
	        $(".wsoAndlot").hide();
	    }else{
	        $(".wsoAndlot").show();
	    }
	    if(wsoId == undefined || wsoId == ''){
	    	var option=""
	    		for(var i=0; i<clientStorageInfo.length; i++){
	    			option += "<option id="+clientStorageInfo[i].id+" data="+clientStorageInfo[i].rate+">"+clientStorageInfo[i].value+"</option>"
	    		}
	    	$("#storageClass").append(option);
	    }
	}

	
	this.loadClientStorage = function (id){
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/clientStorageInfos/clientId/"+id,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        async: false,
	        success: function (data) {
	        	clientStorageInfo = new Array();
	           for(i = 0; i < data.length; i++){
	        	 clientStorageInfo.push({"id":data[i].storageType.storageTypeId,"value":data[i].storageType.storageTypeName,"rate":data[i].monthlyRate})
	           }
	            console.log("SUCCESS : ", data);
	           
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });

	}
	
	
	this.setInvoiceRate = function (thisObj){
		var id = $(thisObj).attr("id");
		var rate = $( "#"+id+" option:selected" ).attr("data")
		if(rate == undefined || rate == null || rate == "null" ){
			rate = "";
		}
		$("#invoiceRate").val(parseFloat(rate).toFixed(2));
	}

	
	this.saveWSOInfo = function (wsoId){
		if(wsoId == undefined ||wsoId == ""){
			wsoId = "";
		}
	        var totalWsoWeight = $("#totalWsoWeight").val();
	        var cargo = $("#cargo").val();
	        var wsoNo = $("#wsoNo").val();
	        if(wsoNo == undefined){
	        	wsoNo  = "";
	        }
	        var storageClass = $("#storageClass").children(":selected").attr("id");
	        var totalNoOfPallets = $("#totalNoOfPallets").val();
	        var tallySheetId = $("#tallySheetNo").val();
	        var invoiceRate = $("#invoiceRate").val();
	        var description = $("#description").val();
	        var remarks = $("#remarks").val();
	        if(totalWsoWeight == "" || totalWsoWeight == undefined){
	        	bootAlert.show("error","Select Total Wso Weight...");
	            return false;
	        }
	        if(totalWsoWeight <= 0){
	        	bootAlert.show("error","Total Wso Weight should be Greater than 0...");
	            return false;
	        }
	        if(cargo == "" || cargo == undefined){
	            bootAlert.show("error","Enter Cargo...");
	            return false;
	        }
	        if(totalNoOfPallets == "" || totalNoOfPallets == undefined){
	            bootAlert.show("error","Enter Total No Of Pallets....");
	            return false;
	        }
	        if(totalNoOfPallets <= 0){
	            bootAlert.show("error","Total No Of Pallets should be Greater than 0....");
	            return false;
	        }
	        if(invoiceRate == "" || invoiceRate == undefined){
	            bootAlert.show("error","Enter Invoice Rate...");
	            return false;
	        }
	        if(invoiceRate <= 0){
	            bootAlert.show("error","Invoice Rate should be Greater than 0....");
	            return false;
	        }
	        if(description == "" || description == undefined){
	            bootAlert.show("error","Enter Description......");
	            return false;
	        }
	        if(remarks == "" || remarks == undefined){
	            bootAlert.show("error","Enter Remarks...");
	            return false;
	        }
	        var tallysheetId = 1;
	        console.log("saveWSOInfo() calling....");
	       var type  ="POST" ;
	        var jsoData  ='{"transWsoWt":"'+totalWsoWeight+'","cargo":"'+cargo+'","invoiceRate":"'+invoiceRate+'","wsoNo":"'+wsoNo+'","totalNoOfPallets":"'+totalNoOfPallets+'","description":"'+description+'","remarks":"'+remarks+'","tallysheet":{"tallysheetId":"'+tallySheetId+'"}, "storageClass":{"storageTypeId":"'+storageClass+'"}}';

	    if(wsoId != "" ){
	    	   type = "PUT";
	        var jsoData  ='{"transWsoWt":"'+totalWsoWeight+'","cargo":"'+cargo+'","invoiceRate":"'+invoiceRate+'","wsoNo":"'+wsoNo+'","totalNoOfPallets":"'+totalNoOfPallets+'","description":"'+description+'","remarks":"'+remarks+'","tallysheet":{"tallysheetId":"'+tallySheetId+'"}, "storageClass":{"storageTypeId":"'+storageClass+'"} }';

	    }
	       $.ajax({
	        type: type,
	        contentType: "application/json",
	        url: contextPath+ "/wsoInfos/"+wsoId,
	        data: jsoData,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	            $('#wsoForm')[0].reset();
	            wsoid = data.wsoId;
	            $('#dynamicModel1').modal('toggle');
	            if(wsoId!=""){
	            	bootAlert.show("success","Wso has been saved with Updated Successfully!");
	            }else{
	            	bootAlert.show("success","Wso has been saved with : "+ data.wsoNo);
	            }
	            var clientId = $("#clientInfo").val();
	            var tallyId = $("#tallySheetNo").val();
	            wso1.loadallWso(clientId,tallyId);

	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
	}
	
	this.editWsoForDelevery = function (wsoId,thisOb){
		var clientId = $("#clientInfo").val();
		var tallySheetNo = $("#tallySheetNo").val();
		if(clientId == undefined ||  clientId == ""){
			bootAlert.show("error","Please Sellect Client")
			return false
		}
		if(tallySheetNo == undefined ||  tallySheetNo == ""){
			bootAlert.show("error","Please Sellect Tally Sheet")
			return false
		}
		wso1.loadClientStorage(clientId);
	    
	    if(wsoId == undefined){
	        wsoId = "";
	    }
	    
	    $("#dmhId1").empty();
	    $("#modelBodyId1").empty();
	    $("#dmhId1").append(" WSO Entry");
	    $("#modelBodyId1").append('<div class="row"><div class="col-lg-6"><div class="form-group"><label>Total WSO Wt</label><input type="number" step="0.00" min="0" class="form-control" id="totalWsoWeight" placeholder="Total WSO Wt"></div></div><div class="col-lg-6"><div class="form-group"><label>Cargo</label><input type="text" class="form-control" id="cargo" placeholder="Cargo"></div></div></div>'+
	        '<div class= "row" > <div class="col-lg-6"><div class="form-group"><label>Stroage Types</label> <select class="form-control" id="storageClass" onchange="wso1.setInvoiceRate(this)"><option value="" id="storageClass">Select Storage Types</option></select>   </div></div><div class="col-lg-6"><div class="form-group"><label>Total No Of Pallets</label><input type="number" class="form-control" id="totalNoOfPallets" placeholder="Total No Of"></div></div></div>'+
	        '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Description</label> <textarea rows="3" cols="50" class="form-control" id="description" placeholder="Description"></textarea></div></div><div class="col-lg-6"><div class="form-group"><label>Remarks</label><textarea rows="3" cols="50" class="form-control" id="remarks" placeholder="Remarks"></textarea></div></div></div>'+
	        '<div class="row"><div class="col-lg-6"><label>Invoice Rate</label><input type="number" step="0.0" min="0" class="form-control" id="invoiceRate" placeholder="Invoice Rate"></div></div></div>'+
	        '<div><div type="button" class="btn btn-primary  mr-10 wsoAndlot" style="margin-top:30px;" onclick="wso1.saveWSOInfo('+wsoId+');">Save WSO</div>'+
	        '<div type="button" class="btn btn-warning  mr-10" style="margin-top:30px;margin-left:30px;" data-dismiss="modal">Cancel</div></div>');
	    if(wso_tally_Verified == "true" || wso_tally_Verified ==true ){
	        $(".wsoAndlot").hide();
	    }else{
	        $(".wsoAndlot").show();
	    }
	    if(wsoId == undefined || wsoId == ''){
	        $('#invoiceRate').val(invoiceRate)
	        $('#storageClass').val(id)

	    }
		
		
		$.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/wsoInfos/getWsoDetail/"+wsoId,
	        dataType: 'json',
	        cache: false,
	        async: false,
	        timeout: 600000,
	        success: function (data) {
	        	 $("#totalWsoWeight").val(data.totalWsoWeight.toFixed(0));
	             $("#cargo").val(data.cargo);
	             $("#totalNoOfPallets").val(data.totalNoOfPallets);
	             $("#gst").val(data.gst);
	             $("#invoiceRate").val(data.invoiceRate.toFixed(2));
	             $("#description").val(data.description);
	             $("#remarks").val(data.remarks);
	             $("#storageClass").empty();
	             var option  = option = "<option id="+data.storageClass.storageTypeId+" value ="+data.storageClass.storageTypeId+" selected>"+data.storageClass.storageTypeName+"</option>"
	             $("#storageClass").append(option);
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
	}
	
	this.openLotPopu  = function (wsoId,thisObj,lotId){
		 var wsoNO = $(thisObj).attr("data");
		    $("#dmhId").empty();
		    $("#modelBodyId").empty();
		    $("#dmhId").append(" Lot Entry for "+wsoNO);
		    $("#modelBodyId").append('<div class="row">'+
		        '<div class="col-lg-5">'+
		        '<div class="form-grup">'+
		        '<label>Lot No</label>'+
		        '<input type="text" class="form-control" id="lotNo" placeholder="Lot No" />'+
		        '</div>'+
		        '</div>'+
		        '<div class="col-lg-2"></div>'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Description</label>'+
		        '<input type="text" class="form-control" id="packages" placeholder="Description">'+
		        '</div>'+
		        '</div>'+
		        '</div>'+
		        '<div class="row">'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Production Date</label>'+
		        '<input type="date" class="form-control" id="productionDate">'+
		        '</div>'+
		        '</div>'+
		        '<div class="col-lg-2"></div>'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Expiry Date</label>'+
		        '<input type="date" class="form-control" id="expiryDate">'+
		        '</div>'+
		        '</div>'+
		        '</div>'+
		        '<div class="row">'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Lot Qty</label>'+
		        '<input type="number" min="0" step="0.00" class="form-control" id="lotQuantity" placeholder="Lot Qty" onblur="wso1.calculateGrossWeith()" >'+
		        '</div>'+
		        '</div>'+
		        '<div class="col-lg-2"></div>'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Room No</label>'+
		        '<input type="text" class="form-control" id="roomNo" placeholder="Room No">'+
		        '</div>'+
		        '</div>'+
		        '</div>'+
		        '<div class="row">'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Lot Wt(Kg)</label>'+
		        '<input type="number" step="0.00" min="0" class="form-control" id="unitNetWeightInKg" placeholder="Unit Net Wt(Kg)" onblur="wso1.calculateGrossWeith()">'+
		        '</div>'+
		        '</div>'+
		        '<div class="col-lg-2"></div>'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Unit Wt (Kg)</label>'+
		        '<input type="number" step="0.00" min="0" class="form-control" id="unitWeightInKg" placeholder="Unit Wt (Kg)o">'+
		        '</div>'+
		        '</div>'+
		        '</div>'+
		        '<div class="row">'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Lot Gross Wt(Kg)</label>'+
		        '<input type="number" step="0.00" min="0" class="form-control" id="unitGrossWeightInKg" placeholder="Unit Gross Wt(Kg)" readonly>'+
		        '</div>'+
		        '</div>'+
		        '<div class="col-lg-2"></div>'+
		        '<div class="col-lg-5">'+
		        '<div class="form-group">'+
		        '<label>Unit Quantity/Lot</label>'+
		        '<input type="number" step="0.00" min="0" class="form-control" id="unitQuantity" placeholder="Lot Wt (Kg)">'+
		        '</div>'+
		        '</div>'+
		        '</div>'+
		        '<div class="row">'+
		        '<div class="col-lg-2"></div>'+
		        '<div class="col-lg-2">'+
		        '<div class="btn btn-success wsoAndlot" id="saveLotInfo" data='+wsoNO+' onclick="wso1.saveLotInfo('+wsoId+','+lotId+',this)">Save</div>'+
		        '</div>'+
		        '<div class="col-lg-2">'+
		        '<div class="btn btn-warning" id="clearLotField" data-dismiss="modal" >Cancel</div>'+
		        '</div>'+

		        '</div>');
		    if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
		        $(".wsoAndlot").hide();
		    }else{
		        $(".wsoAndlot").show();
		    }
	}
	
	
	this.saveLotInfo = function (wsoId,lotId,thisObj){

		var wsoNO = $(thisObj).attr("data");
	    var lotNo = $("#lotNo").val();
	    var packages = $("#packages").val();
	    var expiryDate = $("#expiryDate").val();
	    var productionDate = $("#productionDate").val();
	    var lotQuantity = $("#lotQuantity").val();
	    var roomNo = $("#roomNo").val();
	    var unitNetWeightInKg = $("#unitNetWeightInKg").val();
	    var unitWeightInKg = $("#unitWeightInKg").val();
	    var unitGrossWeightInKg = $("#unitGrossWeightInKg").val();
	    var unitQuantity = $("#unitQuantity").val();
	    var jsonData = ""

	    if(lotNo == "" || lotNo == undefined){
	        bootAlert.show("error","Enter lot No...");
	        return false;
	    }
	    
	    wso1.getLotNoStatus(wsoId, lotId, lotNo);
	    if(currentLotNoStatus != ""){
	    	bootbox.alert(currentLotNoStatus);
	    	return false;
	    }
	    
	    if(packages == "" || packages == undefined){
	        bootAlert.show("error","Enter Description...");
	        return false;
	    }
	    /*if(wsoNo == "" || wsoNo == undefined){
	        bootAlert.show("Select Lorry-Contaninar...");
	        return false;
	    }*/
	    if(expiryDate == "" || expiryDate == undefined){
	        bootAlert.show("error","Select Expiry Date..");
	        return false;
	    }
	    if(productionDate == "" || productionDate == undefined){
	        bootAlert.show("error","Enter Production Date....");
	        return false;
	    }
	    if(lotQuantity == "" || lotQuantity == undefined){
	        bootAlert.show("error","Enter Lot Quantity......");
	        return false;
	    }
	    if(lotQuantity <= 0){
	        bootAlert.show("error","Lot Quantity should be Greater than 0....");
	        return false;
	    }
	    
	    if(roomNo == "" || roomNo == undefined){
	        bootAlert.show("error","Enter Room No...");
	        return false;
	    }
	    if(unitNetWeightInKg == "" || unitNetWeightInKg == undefined){
	        bootAlert.show("error","Enter Unit Net Weight In Kg......");
	        return false;
	    }
	    if(unitNetWeightInKg <= 0){
	        bootAlert.show("error","Unit Net Weight In Kg should be Greater than 0....");
	        return false;
	    }
	    if(unitWeightInKg == "" || unitWeightInKg == undefined){
	        bootAlert.show("error","Enter Unit Weight In Kg...");
	        return false;
	    }
	    if(unitWeightInKg <= 0){
	        bootAlert.show("error","Unit Weight In Kg should be Greater than 0....");
	        return false;
	    }
	    if(unitGrossWeightInKg == "" || unitGrossWeightInKg == undefined){
	        bootAlert.show("error","Enter Unit Gross Weight In Kg......");
	        return false;
	    }
	    if(unitGrossWeightInKg <= 0){
	        bootAlert.show("error","Unit Gross Weight In Kg should be Greater than 0....");
	        return false;
	    }
	    
	    wso1.getLotInfoGrossWeight(wsoId, lotId, unitGrossWeightInKg);
	    
	    if(currentLotWsoWtStatus != ""){
	    	bootbox.alert(currentLotWsoWtStatus);
	    	return false;
	    }
	    if(unitQuantity == "" || unitQuantity == undefined){
	        bootAlert.show("error","Enter Unit Quantity...");
	        return false;
	    }
	    if(unitQuantity <= 0){
	        bootAlert.show("error","Unit Quantity should be Greater than 0....");
	        return false;
	    }
	    if(Date.parse(expiryDate) <= Date.parse(productionDate)){
	        bootAlert.show("error","Expiry Date can't be less than or equal to production date......");
	        return false;
	    }
	    	var requestType= "PUT";
	    	var requestUrl=  "/lotInfos/" + lotId;
	    	if (lotId != undefined && lotId != "") {
	    		requestType= "PUT";
		    	requestUrl=  "/lotInfos/" + lotId;
	            jsonData = '{"lotId":"' + lotId + '", "lotNo":"' + lotNo + '","packages":"' + packages + '","expiryDate":"' + expiryDate + '","productionDate":"' + productionDate + '","lotQuantity":"' + lotQuantity + '","roomNo":"' + roomNo + '","unitNetWeightInKg":"' + unitNetWeightInKg + '","unitWeightInKg":"' + unitWeightInKg + '","unitGrossWeightInKg":"' + unitGrossWeightInKg + '","unitQuantity":"' + unitQuantity + '","wsoInfo":{"wsoId":"' + wsoId + '"}}';
	        } else {
	        	requestType= "POST";
		    	requestUrl=  "/lotInfos/";
	            jsonData = '{"lotNo":"' + lotNo + '","packages":"' + packages + '","expiryDate":"' + expiryDate + '","productionDate":"' + productionDate + '","lotQuantity":"' + lotQuantity + '","roomNo":"' + roomNo + '","unitNetWeightInKg":"' + unitNetWeightInKg + '","unitWeightInKg":"' + unitWeightInKg + '","unitGrossWeightInKg":"' + unitGrossWeightInKg + '","unitQuantity":"' + unitQuantity + '","wsoInfo":{"wsoId":"' + wsoId + '"}}';
	           
	        }

	    	$.ajax({
	                type: requestType,
	                contentType: "application/json",
	                url: contextPath+requestUrl,
	                data: jsonData,
	                dataType: 'json',
	                cache: false,
	                timeout: 600000,
	                success: function (data) {
	                    if(requestType == "POST"){
	                    	$('#dynamicModel').modal('toggle');
		                    var tr = wso1.generateLotTr(data, wsoId, wsoNO);
		                    $("#wsoLoTableDteail" + wsoId).append(tr);
		                    bootAlert.show("success","Lot has been saved successfully ")
	                    }else{
	                    	 $('#dynamicModel').modal('toggle');
	 	                    wso1.loadWsolotsssUpdate(wsoId, wsoNO);
	 	                    bootAlert.show("success","Lot has been updated successfully ")
	                    }
	                },
	                error: function (e) {

	                    var json = "<h4>Ajax Response</h4><pre>"
	                        + e.responseText + "</pre>";
	                    $('#feedback').html(json);

	                    console.log("ERROR : ", e);
	                    // $("#btn-search").prop("disabled", false);

	                }
	            });
	}
	
	this.loadWsolotsss = function (wsoId,thisobj){
	    var wsoNO = $(thisobj).attr("data");
	    if (!$(thisobj).hasClass("loadLot")) {
	        $(".viewLotsDetailscls").hide();
	        $(thisobj).addClass("loadLot");
	        $(thisobj).removeClass("hideLot");
	    } else {
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/lotInfos/getAllLotByWsoId/"+wsoId,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	            $(".viewLotsDetailscls").hide();
	            $("#wsoLot" + wsoId).show();
	            $("#wsoLoTableDteail" + wsoId).empty();
	             $(thisobj).addClass("hideLot");
	             $(thisobj).removeClass("loadLot");
	            var tr = "";
	            for(var i=0; i< data.length; i++){
	                tr += wso1.generateLotTr(data[i],wsoId,wsoNO);
	            }

	            $("#wsoLoTableDteail"+wsoId).append(tr);
	            if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
	                $(".wsoAndlot2").hide();
	            }else{
	                $(".wsoAndlot2").show();
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
	}
	
	this.loadWsolotsssUpdate = function (wsoId,wsoNO){
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/lotInfos/getAllLotByWsoId/"+wsoId,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	$("#wsoLoTableDteail"+wsoId).empty();
	            var tr = "";
	            for(var i=0; i< data.length; i++){
	                tr += wso1.generateLotTr(data[i],wsoId,wsoNO);
	            }
	            if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
	                $(".wsoAndlot").hide();
	            }else{
	                $(".wsoAndlot").show();
	            }
	            $("#wsoLoTableDteail"+wsoId).append(tr);
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
	}
	
	
	this.generateLotTr = function (lotData,wsoId,wsoNo){

	    var tr ="";
	    var lotNo = lotData.lotNo;
	    var lotQuantity = lotData.lotQuantity;
	    var unitWeightInKg = lotData.unitWeightInKg.toFixed(2);
	    var unitGrossWeightInKg = lotData.unitGrossWeightInKg.toFixed(0);
	    var unitNetWeightInKg = lotData.unitNetWeightInKg.toFixed(2);
	    var currentQuantity = lotData.currentQuantity;
	    var expiryDate = lotData.expiryDate;
	    expiryDate = common.getFormattedDate(new Date(expiryDate));
	    var productionDate = lotData.productionDate;
	    productionDate = common.getFormattedDate(new Date(productionDate));
	    var description =lotData.packages;
	    var roomNo = lotData.roomNo;
	    tr +="<tr style='background-color:#f9f9f969;'>"
	    tr += "<td><a href='#' style='font-size:20px;' data="+wsoNo+" onclick='wso1.editLotDetail("+wsoId+",this,"+lotData.lotId+")' data-toggle='modal' data-target='#dynamicModel'   >" +
	        "<i class='fa fa-pencil'></i>" +
	        "</a>&nbsp;&nbsp;  <a href='#' class='wsoAndlot2' data="+wsoNo+" style='font-size:20px; color:#ff4444;' onclick='wso1.deleteLotDetail("+wsoId+",this,"+lotData.lotId+")'>"+
	        "<i class='fa fa-trash-o'></i>"+            
	        "</a> </td>"
	    tr +="<td>"+description+"</td>"                    
	    tr +="<td style='text-align:right'>"+lotNo+"</td>"
	    tr +="<td style='text-align:right'>"+lotQuantity+"</td>"
	    tr +="<td style='text-align:right'>"+unitNetWeightInKg+"</td>"
	    tr +="<td style='text-align:right'>"+unitGrossWeightInKg+"</td>"
	    tr +="<td>"+productionDate+"</td>"
	    tr +="<td>"+expiryDate+"</td>"
	    tr +="<td>"+roomNo+"</td>"
	    tr +="</tr>"

	    if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
	        $(".wsoAndlot2").hide();
	    }else{
	        $(".wsoAndlot2").show();
	    }
	return tr;
	}
	
	this.calculateGrossWeith = function(){
		var quntity = $("#lotQuantity").val();
		var weight = $("#unitNetWeightInKg").val();
		var grossWeight =0;
		if((weight!= undefined || weight != "" ) && (weight!= undefined || weight != "" ) ){
			weight =  parseFloat(weight);
			quntity =  parseFloat(quntity);
			 grossWeight = quntity*weight;
			
		}else{
			grossWeight = 0;
		}
		$("#unitGrossWeightInKg").val(grossWeight.toFixed(0));
	}
	
	this.editLotDetail = function(wsoId,thisOb,lotId){
		wso1.openLotPopu(wsoId,thisOb,lotId);
		$.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/lotInfos/getLotDetail/"+lotId,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (lotData) {
	        	    var lotNo = lotData.lotNo;
	        	    var lotQuantity = lotData.lotQuantity;
	                var unitQuantity = lotData.unitQuantity;
	        	    var unitWeightInKg = lotData.unitWeightInKg;
	        	    var unitGrossWeightInKg = lotData.unitGrossWeightInKg;
	        	    var unitNetWeightInKg = lotData.unitNetWeightInKg;
	        	    var currentQuantity = lotData.currentQuantity;
	        	    var expiryDate = lotData.expiryDate;
	        	    var productionDate = lotData.productionDate;
	        	    var unitQuantity = lotData.unitQuantity;
	        	    var roomNo = lotData.roomNo;
	        	    var description =lotData.packages;
	        	    $("#lotNo").val(lotNo);
	        	    $("#packages").val(description);
	        	    $("#expiryDate").val(expiryDate);
	        	    $("#productionDate").val(productionDate);
	        	    $("#lotQuantity").val(lotQuantity);
	        	    $("#roomNo").val(roomNo);
	        	    $("#unitGrossWeightInKg").val(unitGrossWeightInKg);
	        	    $("#unitWeightInKg").val(unitWeightInKg);
	        	    $("#unitNetWeightInKg").val(unitNetWeightInKg);
	        	    $("#unitQuantity").val(unitQuantity);
	        	    $("#roomNo").val(roomNo);
	        	
	        },
	        error: function (e) {
	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });
	}
	
	
	//Start delete Lot
	 this.deleteLotDetail  = function(wsoId,thisObj,lotId) {
	    var wsoNO = $(thisObj).attr("data");
	    console.log($(thisObj).attr("data"));
	    lot = parseInt(lotId)
	    wso = parseInt(wsoId)
	    
	    bootbox.confirm("Are you sure you want to delete Lot?", function(result){ 
	    	//console.log('This was logged in the callback: ' + result); 
	    	if(result == true){
	    	 $.ajax({
	                type: "DELETE",
	                contentType: "application/json",
	                url: contextPath+ "/lotInfos/"+lot,
	                //dataType: 'json',
	                cache: false,
	                timeout: 600000,
	                success: function (resultMsg) {
	                	wso1.loadWsolotsssUpdate(wsoId, wsoNO);
	                    bootAlert.show("success","Lot has been deleted successfully ")
	                    
	                },
	                error: function (e) {
	                    var json = "<h4>Ajax Response</h4><pre>"
	                        + e.responseText + "</pre>";
	                    $('#feedback').html(json);
	                    console.log("ERROR : ", e);
	                }
	            });
	    	}else{
	    		
	    	}
	    });
}
	//End Delete Lot
	
	
	 //Start delete wso
	  this.deleteWso = function(wsoId){
		    wso = parseInt(wsoId)
		     bootbox.confirm("Are you sure you want to delete WSO?", function(result){
		    	 if(result == true){
		    		 $.ajax({
				            type: "DELETE",
				            contentType: "application/json",
				            url: contextPath+ "/wsoInfos/"+wso,
				            //dataType: 'json',
				            cache: false,
				            timeout: 600000,
				            success: function (resultMsg) {
				                if(resultMsg == "cancel"){
				                    bootAlert.show("error","Can't delete WSO unless Lot is deleted ")
				                }
				                else{
				                    bootAlert.show("success","Wso has been deleted successfully ")
				                    var clientId = $("#clientInfo").val();
				                    var tallyId = $("#tallySheetNo").val();
				                    wso1.loadallWso(clientId,tallyId);
				                }
				                
				            },
				            error: function (e) {
				                var json = "<h4>Ajax Response</h4><pre>"
				                    + e.responseText + "</pre>";
				                $('#feedback').html(json);
				                console.log("ERROR : ", e);
				            }
				        });

		    	 }else{
		    		 
		    	 }
		     });
	}
	 //End Delete Wso
	  
	  //Start wso search
	  this.wsoSearch = function(){
		  
		  var searchWsoNo = $("#searchWsoNo").val();
		  var clientId = $("#clientInfo").val();
		  var tallyId = $("#tallySheetNo").val();
		  
		  if(searchWsoNo == ''){
			  wso1.loadallWso(clientId,tallyId);
			  return false;
		  }
		  
		  var jsondata = {"clientId":clientId,"tallysheetId":tallyId,"wsoNo":searchWsoNo}
		     $.ajax({
		            type: "GET",
		            contentType: "application/json",
		            data: jsondata,
		            url: contextPath+ "/wsoInfos/loadAllWsosByWsoNo",
		            dataType: 'json',
		            cache: false,
		            timeout: 600000,
		            success: function (data) {
		                $("#wsoMainContent").empty();
		                $("#wsoMainContent").append('<div class="table-responsive"><table class="table"><thead><tr style="background-color:#e8e4e469;"><th></th><th>WSO No</th><th>WSO Wt</th><th>Cargo</th><th>Stroage Class</th><th>No Of Pallets</th><th>GST</th><th>Invoice Rate</th></tr></thead><tbody id="wsoTableId"></tbody></table></div>');
		                $("#wsoTableId").empty();
		                var tr="";
		                for(var i=0; i<data.length; i++){
		                    var wsoNo = data[i].wsoNo;
		                    var totalWsoWeight   = data[i].totalWsoWeight ;
		                    var cargo   = data[i].cargo ;
		                    var storageClass   = data[i].storageClass.storageTypeName ;
		                    var totalNoOfPallets   = data[i].totalNoOfPallets ;
		                    var gst   = data[i].gst ;
	                        var invoiceRate   = data[i].invoiceRate ;
		                    var tallysheetText =  $("#tallySheetNo selected").text();
		                    var clientText =  $("#clientInfo selected").text(); 
		                    tr += "<tr>";
		                    tr += "<td><a href='#' onclick='wso1.editWsoForDelevery(" + data[i].wsoId + ",this)'  style='font-size:20px;' data-toggle='modal' data-target='#dynamicModel1' ><i class='fa fa-pencil'></i></a>&nbsp;&nbsp;" +
		                    
		                    "<a href='#' style='font-size:20px;' data="+wsoNo+" onclick='wso1.loadWsolotsss(" + data[i].wsoId + ",this)' >" +
		                    "<i class='fa fa-search-plus '></i></a>&nbsp;&nbsp;" +
		                    "<a href='#' class='wsoAndlot1' style='font-size:20px; color:#ff4444;' onclick='wso1.deleteWso(" +data[i].wsoId + ",this) '><i class='fa fa-trash-o'></i></a>"+
		                    
		                    "</td><td>"+wsoNo+"</td> <td> "+totalWsoWeight+" </td> <td> "+cargo+"</td> <td> "+storageClass+" </td> <td> "+totalNoOfPallets+" </td><td> "+gst+" </td> <td> "+invoiceRate+" </td>  ";
		                    tr += "</tr>";
		                    tr += "<tr id='wsoLot"+data[i].wsoId+"' style='display:none;' class='viewLotsDetailscls'><td colspan='11'> ";
		                    tr +='<div style="background:##b1b1b112;width:95%;margin: 0 auto;">'
		                    tr += '<div class="table-responsive"><table class="table" ><thead><tr><td colspan="10"><a href="#demo" class="btn btn-info wsoAndlot" style="float:right;" data-toggle="modal" data-target="#dynamicModel" data='+wsoNo+'  onclick="wso1.openLotPopu('+data[i].wsoId+',this)"> Add New Lot</a></td></tr><tr style="background-color: #b1b1b112;"><th> </th> <th>Description</th>  <th> Lot No</th><th> Lot Qty</th><th> Lot Wt(Kg) </th><th>Lot Gross Wt(Kg) </th><th>Production Date </th><th>Expiry Date</th><th>Room No </th></tr></thead><tbody id="wsoLoTableDteail'+data[i].wsoId+'"></tbody></table></div></div>';
		                    tr += " </td> </tr>"
		                }
		                //alert(JSON.stringify(data));vz
		                $("#wsoTableId").append(tr);
		                
		                if(wso_tally_Verified == "true" ||wso_tally_Verified ==true ){
		                	$(".wsoAndlot1").hide();
		                }else{
		                	$(".wsoAndlot1").show();
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
	  //End Wso Search
	  
	  //Start getLotInfoGrossWeight
	  
	  this.getLotInfoGrossWeight = function(wsoId, lotId, unitGrossWeightInKg){
		  currentLotWsoWtStatus = "";
		  if(lotId == undefined || lotId == null){
			  lotId = "";
		  }
		  var jsonData = {'unitGrossWeightInKg':unitGrossWeightInKg, 'lotId':lotId}
		  $.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+ "/wsoInfos/getLotInfoGrossWeight/" + wsoId,
		        data: jsonData,
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        async:false,
		        success: function (response) {
		           var data = response.data;
		           if(data == "ok"){
		        	   currentLotWsoWtStatus = "";
		           }else{
		        	   currentLotWsoWtStatus = "Sorry, you're available wso weight is : "+data.lessGrossWeight;
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
	  //End getLotInfoGrossWeight
	  
	  
//Start getLotNoStatus
	  
	  this.getLotNoStatus = function(wsoId, lotId, lotNo){
		  currentLotNoStatus = "";
		  wsoId  =parseInt(wsoId);
		  if(lotId == undefined || lotId == null){
			  lotId = "";
		  }
		  var jsonData = {'lotNo':lotNo, 'lotId':lotId}
		  $.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+ "/wsoInfos/getLotInfoNo/" +wsoId,
		        data: jsonData,
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        async:false,
		        success: function (response) {
		           var data = response.data;
		           if(data == "lotNo is available"){
		        	   currentLotNoStatus = "";
		           }else{
		        	   currentLotNoStatus = "Sorry, this lotNo is already occupied : "+lotNo;
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
	  //End getLotInfoGrossWeight
	  
	  
}
