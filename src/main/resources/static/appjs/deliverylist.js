
var DELIVERY_LIST = function() {
	var lot;
	var wsoListForDlvery = new Array();
	var rowNumber = 0;
	this.show = function(){
		$("#content-wrapper").empty();
	    $("#content-wrapper").append('<div class="row">'+
	    '<div class="col-lg-12">'+
	        '<div class="row">'+
	            '<div class="col-lg-12">'+
	                '<h1>Delivery List</h1>'+
	            '</div>'+
	        '</div>'+
	        '<div class="row delivery-style datesscroll">'+
	            '<div class="col-lg-12">'+
	                '<form role="form" id="clearDeliveryListField">'+
	                    '<div class="main-box">'+
	                        '<div class="main-box-body clearfix">'+
	                            '<div class="row">'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Client Name</label>'+
	                                        '<select class="form-control" id="clientInfo"  onchange="delivery.loadDeliveryList()" >'+
	                                        '</select>'+
	                                    '</div>'+
	                                '</div>'+
	                                '<div class="col-lg-4"></div>'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Date Of Delivery</label>'+
	                                        '<input type="date" class="form-control" placeholder="Date Of Delivery" id="dateOfDelivery">'+
	                                    '</div>'+
	                                '</div>'+
	                            '</div>'+
	                            '<div class="row">'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>DL No</label>'+
	                                        '<select class="form-control" id="dlNo" onchange="delivery.loadDlDetiail()"></select>'+
	                                    '</div>'+
	                                '</div>'+
	                                '<div class="col-lg-4"></div>'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Time Of Issue</label>'+
	                                        '<input type="text" class="form-control time-picker" id="timeOfIssue">'+
	                                    '</div>'+
	                                '</div>'+
	                            '</div>'+
	                            '<div class="row">'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Name Of Receiver</label>'+
	                                        '<input class="form-control" id="nameOfReceiver">'+
	                                    '</div>'+
	                                '</div>'+
	                                '<div class="col-lg-4"></div>'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Vehicle No</label>'+
	                                        '<input class="form-control" id="vehicleNo">'+
	                                   '</div>'+
	                                '</div>'+
	                            '</div>'+
	                            '<div class="row">'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>NRIC Of Receiver</label>'+
	                                        '<input class="form-control" id="nricOfReceiver">'+
	                                    '</div>'+
	                                '</div>'+
	                                '<div class="col-lg-4"></div>'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Verify</label>'+
	                                        '<select class="form-control" id="verify">'+
	                                            '<option value="false" selected>No</option>'+
	                                            '<option value="true">Yes</option>'+
	                                        '</select>'+
	                                    '</div>'+
	                                '</div>'+
	                            '</div>'+
	                            '<div class="row">'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Employee Name</label>'+
	                                        '<input type="text" class="form-control" placeholder="Emp Name" value="'+LoggedUserIdVar+'" readOnly="true">'+
	                                    '</div>'+
	                                '</div>'+
	                                '<div class="col-lg-4"></div>'+
	                                '<div class="col-lg-4">'+
	                                    '<div class="form-group">'+
	                                        '<label>Total Qty</label>'+
	                                        '<input type="number" class="form-control" id="totalQuantity" placeholder="Total Quantity"  readonly="true" ">'+
	                                    '</div>'+
	                                '</div>'+

	                            '</div>'+

	                        '</div>'+
	                    '</div>'+
	                    '<div class="main-box" style="background: #eeeeee">'+
	                        '<div class="main-box-body clearfix">'+
	                            '<div class="table-responsive">'+
	                                '<table class="table" id="clearTDeliveryLists">'+
	                                    '<thead>'+
	                                        '<tr>'+
	                                            '<th class="text-center">WSO No</th>'+
	                                            '<th class="text-center">Lot No</th>'+
	                                            '<th class="text-center">Expiry Date</th>'+
	                                            '<th class="text-center">Initial Qty</th>'+
	                                            '<th class="text-center">Current Qty</th>'+
	                                            '<th class="text-center">Pkgs Out</th>'+
	                                            '<th class="text-center">Action</th>'+
	                                        '</tr>'+
	                                    '</thead>'+
	                                    '<tbody id="deliveryTbody">'+
	                                       
	                                    '</tbody>'+
	                                '</table>'+
	                            '</div>'+
	                            '<div class="row">'+
	                                '<div class="col-lg-2"></div>'+
	                                '<div class="col-lg-2">'+
	                                    '<div class="btn btn-success" id="saveDeliveryLists" onclick="delivery.saveDeliveryLists()">Save</div>'+
	                                '</div>'+
	                                '<div class="col-lg-2">'+
	                                    '<div class="btn btn-warning" onclick="delivery.clearDeliveryLists()">Clear</div>'+
	                                '</div>'+
	                                '<div class="col-lg-2">'+
	                                    '<a  href="index.html" class="btn btn-danger">Exit</a>'+
	                                '</div>'+                                                
	                            '</div>'+
	                        '</div>'+
	                    '</div>'+
	                '</form>'+
	            '</div>'+
	        '</div>'+
	    '</div>'+
	'</div>');
	    $(".time-picker").hunterTimePicker();
	    delivery.getClientName();
	    
	}
	
	
	//Start New Row
	 this.newRow = function(){
		rowNumber += 1;
	   event.preventDefault();

	   $("#deliveryTbody").append('<tr id='+rowNumber+'>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<select class="form-control wsoInfo" style="width:120px;" onchange="delivery.onWsoInfoChange(this)" rownum='+rowNumber+'  id="wsoInfo'+rowNumber+'">'+
	            '<option id="2"> okdd </option>'+
	            '<option id="3"> okdd </option>'+
	            '</select>'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<select class="form-control lotInfocls" style="width:120px;" id="lotInfo'+rowNumber+'"   onchange="delivery.lotInfochng(this)" rownum='+rowNumber+'  >'+

	            '</select>'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	        	'<input type="text" class="form-control" style="text-aligne:center" placeholder="Expiry Date" id="expiryDate'+rowNumber+'" readonly>'+
	            '</input>'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	        	'<input type="text" class="form-control" style="text-aligne:center" placeholder="Lot Quantity" id="lotQuantity'+rowNumber+'" readonly>'+
	        	'</input>'+
	            
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	        	'<input type="text" class="form-control" style="text-aligne:center" placeholder="Current Quantity" id="currentQuantity'+rowNumber+'" readonly>'+
	        	'</input>'+
	            
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input class="form-control packageoutcls"  id="packagesOut'+rowNumber+'" type="number" style="width: 70%" onblur="delivery.changepackegeout('+rowNumber+')">'+
	        '</div>'+
	    '</td>'+
	    '<td style="width: 15%;" class="text-center">'+
	        '<a href="#"  onclick="delivery.newRow()" style="font-size:20px;">'+
	                                                        '<i class="fa fa-plus"></i>'+
	                                                '</a>&nbsp;&nbsp; '+
	                                                '<a href="#" onclick="delivery.deleteRow(this)" style="font-size:20px; color:#ff4444;">'+
	                                                        '<i class="fa fa-trash-o"></i>'+
	                                                '</a>'+
	    '</td>'+
	'</tr>');

	   var option = "";
	   option  += "<option value=''>Select Wso</option>"
	   for (i = 0; i < wsoListForDlvery.length; i++){
			option += "<option value="+wsoListForDlvery[i].id+" >"+wsoListForDlvery[i].value+" </option>"
	   }
		$("#wsoInfo"+rowNumber).empty();
		$("#wsoInfo"+rowNumber).append(option);
		
		$("#lotInfo"+rowNumber).empty();
		$('#lotInfo'+rowNumber).append("<option value =''>Select Lot</option>");
	}
// End new Row
	 
	 
	 //Start get Client name
	 this.getClientName = function(){
		    $.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+"/clientInfos/allClientTitle/",
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
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
	 //End Client name
	
	
	 //Start load delivery list
	 this.loadDeliveryList = function(){
			var clientId = $( "#clientInfo" ).val();
			clientId = parseInt(clientId)
			$('#dateOfDelivery').val("");
			$('#dlNo').empty();
	    	$('#dlNo').append("<option value =''>Select DL No</option>");
			$('#timeOfIssue').val("");
			$('#nameOfReceiver').val("");
			$('#vehicleNo').val("");
			$('#nricOfReceiver').val("");
			$('#verify').val("false");
			$('#totalQuantity').val("");
			for (i = 0; i <= rowNumber; i++){
				$('#expiryDate'+i+'').val("");
				$('#lotQuantity'+i+'').val("");
				$('#currentQuantity'+i+'').val("");
				$('#packagesOut'+i+'').val("");
			}
	    	 for (i = 0; i < ('#wsoInfo').length; i++){
	    		 $("#wsoInfo"+i).empty();
	    		 $('#wsoInfo'+i).append("<option value =''>Select Wso</option>");
	            }
	    	 for (i = 0; i < ('#lotInfo').length; i++){
	    		 $("#lotInfo"+i).empty();
	    		 $('#lotInfo'+i).append("<option value =''>Select Lot</option>");
	            }
	    	 
			delivery.getDl(clientId);
			delivery.getAllWsoByClientId(clientId);
			$("#saveDeliveryLists").show();
		}
	 //End load delivery list
	 
	 //Start change package out
	  this.changepackegeout  = function(rowNumber){
		  
			var total = 0;
			var packages = $("#packagesOut"+rowNumber).val();
			var current = $("#currentQuantity"+rowNumber).val();
			
			if(parseInt(packages) > parseInt(current)){
				bootAlert.show("error","packages out can't be greater than current qty");
				$("#packagesOut"+rowNumber).val(current);
			}
			
			$(".packageoutcls").each(function() {
				var totalqty = $(this).val();
				if(totalqty != null && totalqty != ""){
					total += parseInt(totalqty);
				}
			});
			$("#totalQuantity").val(total);
		}
////End change package out
	  
	  
	  //Strat get Dl 
	   this.getDl = function(clientId) {
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+"/deliveryLists/clientDl/"+clientId,
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (response) {
	        	var data = response.data;
	            console.log("success.....", data);
	            $('#dlNo').empty();
	            var tempArray = new Array();
	            $('#dlNo').append("<option value =''>Select DL No</option>");
	            var option = "";
	            /*for (i = 0; i < data.length; i++){
	            	if(tempArray.indexOf(data[i]) == -1){
	            		var d = data[i].split("D00000").pop(-1);
	            		console.log(d)
	            		tempArray.push(parseInt(d));
	            	}
	            }
	            var uniqueDls = [];
	            $.each(tempArray, function(i, el){
	                if($.inArray(el, uniqueDls) == -1) uniqueDls.push(el);
	            });
	            delivery.sortArray(uniqueDls);
            	console.log(uniqueDls)*/
            	
            	for (i = 0; i < data.length; i++){
            		option  += "<option value=D00000"+data[i].dl+">D00000"+data[i].dl+"</option>"
            	}
	            $('#dlNo').append(option);
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
//End Get Dl
	   
	   
	   this.sortArray= function(array) {
		    array.sort(function(a, b) {
		        return a > b;
		    });
		},
	   
	   
	   //Start get all Wso by Client Id
	   this.getAllWsoByClientId = function(clientId) {
			var dlNoss = $("#dlNo").val();
			
			if(dlNoss == ""){
				$.ajax({
			        type: "GET",
			        contentType: "application/json",
			        url: contextPath+"/wsoInfos/getAllWsoByClientIdWithDlNo/"+clientId,
			        dataType: 'json',
			        cache: false,
			        timeout: 600000,
			        success: function (data) {
			        	console.log(data)
			        	wsoListForDlvery=[];
			        	for (i = 0; i < data.length; i++){
			        		wsoListForDlvery.push({"id":data[i].wsoId,"value":data[i].wsoNo});
			            }
			        	$("#deliveryTbody").empty();
			        	delivery.newRow();
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
			
			/*if(dlNoss != ""){
				$.ajax({
			        type: "GET",
			        contentType: "application/json",
			        url: contextPath+"/wsoInfos/getAllWsoByClientIdWithDlNo/"+clientId,
			        dataType: 'json',
			        async : false,
			        cache: false,
			        timeout: 600000,
			        success: function (data) {
			        	console.log(data)
			        	wsoListForDlvery=[];
			        	for (i = 0; i < data.length; i++){
			        		wsoListForDlvery.push({"id":data[i].wsoId,"value":data[i].wsoNo});
			            }
			        	//$("#deliveryTbody").empty();
			        	//delivery.newRow();
			            console.log("SUCCESS : ", data);
			        },
			        error: function (e) {
			            var json = "<h4>Ajax Response</h4><pre>"
			                + e.responseText + "</pre>";
			            $('#feedback').html(json);
			            console.log("ERROR : ", e);
			        }
			    });
			}*/
			
			if(dlNoss != ""){
				$.ajax({
			        type: "GET",
			        contentType: "application/json",
			        url: contextPath+"/wsoInfos/getAllWsoByClientId/"+clientId,
			        dataType: 'json',
			        async : false,
			        cache: false,
			        timeout: 600000,
			        success: function (data) {
			        	console.log(data)
			        	wsoListForDlvery=[];
			        	for (i = 0; i < data.length; i++){
			        		wsoListForDlvery.push({"id":data[i].wsoId,"value":data[i].wsoNo});
			            }
			        	//$("#deliveryTbody").empty();
			        	//delivery.newRow();
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
		    
		}
//End get all Wso by Client Id
	   
	   //	Start Lot by client id
	    this.getAllLotByWsoId = function(wsoId,rowNum) {
		    $.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+"/lotInfos/getAllLotByWsoId/"+wsoId,
		        dataType: 'json',
		        cache: false,
		        async : false,
		        timeout: 600000,
		        success: function (data) {
		        	var tempArray = new Array();
		        	$(".lotInfocls").each(function() {
		        		var lotId = $(this).val();
		        		if(lotId != null && lotId != ""){
		        		 tempArray.push($(this).val());
		        		}
		        	});
		            $('#lotInfo'+rowNum).empty();
		            $('#currentQuantity'+rowNum).empty();
		            $('#lotInfo'+rowNum).append('<option value=""> Select Lot</option>');
		            var option ="";
		            for (i = 0; i < data.length; i++){
		            	if(tempArray.indexOf(data[i].lotId.toString()) == -1){
		            		console.log("dlNo : "+$('#dlNo').val());
		            		
		            		if($('#dlNo').val() == ""){
		            			if(parseInt(data[i].currentQuantity) > 0){
		            				option += "<option value="+data[i].lotId+"> "+data[i].lotNo+"</option>";
		            			}
		            		}else{
		            			option += "<option value="+data[i].lotId+"> "+data[i].lotNo+"</option>";
		            		}
		            		/*if($('#dlNo').val() == "" && parseInt(data[i].currentQuantity) > 0){
		            			option += "<option value="+data[i].lotId+"> "+data[i].lotNo+"</option>";
		            		}*/
		                		
		            	}
		            }
		            $('#lotInfo'+rowNum).append(option);
		        },
		        error: function (e) {
		            var json = "<h4>Ajax Response</h4><pre>"
		                + e.responseText + "</pre>";
		            $('#feedback').html(json);
		            console.log("ERROR : ", e);
		        }
		    });
		}
	   //End Lot by client id
	   
	 // Start Lot info change
	   this.onWsoInfoChange = function(thisObj){
			var id  = $(thisObj).attr("id");
			var rowNum = $(thisObj).attr("rownum");  
		    var cl = $(thisObj).val();
		    if(cl == "" || cl == undefined ){
		    	$('#lotInfo'+rowNum).empty("");
		    }else{
		    	var clId = parseInt(cl)
		        delivery.getAllLotByWsoId(clId,rowNum);
		    }
		    
		    $('#expiryDate'+rowNum+'').val("");
			$('#lotQuantity'+rowNum+'').val("");
			$('#currentQuantity'+rowNum+'').val("");
			$('#packagesOut'+rowNum+'').val("");
		    
		}
	   
	// End Wso Info change
	   
	   // Start Lot info change
	    this.lotInfochng = function(thisObj){
			var id  = $(thisObj).attr("id");
			var rowNum = $(thisObj).attr("rownum");  
		    var lotId = $(thisObj).val();
		    if(lotId == "" || lotId == undefined){
		    	$('#expiryDate'+rowNum).empty("");
		        $('#currentQuantity'+rowNum).empty("");
		        $('#lotQuantity'+rowNum).empty("");
		    }else{
		    	
		    	lotId = parseInt(lotId)
		        delivery.getLotDetailByWsoforDlvr(lotId,rowNum);	
		    }
		    
		}
	   // End Lot Info change
	    
	   this.getLotDetailByWsoforDlvr = function(lotId,rowNum) {
	        var id = lotId;
	        console.log("selectLotInfoById() calling");

	        $.ajax({
	            type: "GET",
	            contentType: "application/json",
	            url: contextPath+"/lotInfos/"+id,
	            dataType: 'json',
	            cache: false,
	            timeout: 600000,
	            async : false,
	            success: function (data) {
	            	var wsoInfo = data.wsoInfo;
	                console.log("success.....", data);
	                $('#expiryDate'+rowNum).empty("");
	                
	                $('#expiryDate'+rowNum).val(common.getFormattedDate(new Date(data.expiryDate)));
	                
	                $('#currentQuantity'+rowNum).empty("");
	                
	                $('#currentQuantity'+rowNum).val(data.currentQuantity);
	                
	                if(data.currentQuantity <= 0){
	                	$('#packagesOut'+rowNum).val(0);
	                	$('#packagesOut'+rowNum).prop("readonly", true);
	                }else{
	                	$('#packagesOut'+rowNum).prop("readonly", false);
	                }
	                
	                $('#lotQuantity'+rowNum).empty("");
	                
	                $('#lotQuantity'+rowNum).val(data.initialQuantity);
	                
	                
	            },
	            error: function (e) {

	                var json = "<h4>Ajax Response</h4><pre>"
	                    + e.responseText + "</pre>";
	                $('#feedback').html(json);

	                console.log("ERROR : ", e);

	            }
	        });

	    }
	   
	   //Start Save Delivery list
	    this.saveDeliveryLists = function() {
			var wsoInfows = new Array();
			
	        var dateOfDelivery = $("#dateOfDelivery").val();
	        var clientInfo = $("#clientInfo").val();
	    //var timeOfIssue = $("#timeOfIssue").val();
	        var dlNo = $("#dlNo").val();
	        var timeOfIssue = $("#timeOfIssue").val();
	        var nameOfReceiver = $("#nameOfReceiver").val();
	        var vehicleNo = $("#vehicleNo").val();

	        var nricOfReceiver = $("#nricOfReceiver").val();
	        var verify = $("#verify").val();

	        if(clientInfo == "" || clientInfo == undefined){
	            bootAlert.show("error","Enter Client Info...");
	            return false;
	        }
	        if(dateOfDelivery == "" || dateOfDelivery == undefined){
	            bootAlert.show("error","Enter Date Of Delivery...");
	            return false;
	        }

	        if(timeOfIssue == "" || timeOfIssue == undefined){
	            bootAlert.show("error","Select Time Of Issue..");
	            return false;
	        }
	        if(nameOfReceiver == "" || nameOfReceiver == undefined){
	            bootAlert.show("error","Enter Name Of Receiver....");
	            return false;
	        }
	        if(vehicleNo == "" || vehicleNo == undefined){
	            bootAlert.show("error","Enter Vehicle No......");
	            return false;
	        }
	        if(nricOfReceiver == "" || nricOfReceiver == undefined){
	            bootAlert.show("error","Enter Nric Of Receiver...");
	            return false;
	        }
	        //bootAlert.show("Verify:",verify);
	        if(verify == "" || verify == undefined || verify == "false"){
	            bootAlert.show("error","Enter Verify Yes before saving...");
	            return false;
	        }


	    $("#deliveryTbody tr").each(function() {
	        	var rowNum = $(this).attr("id");
	        	var wsoInfo = $("#wsoInfo"+rowNum).val();
	            var lotInfo = $("#lotInfo"+rowNum).val();
	            var expiryDate= $("#expiryDate"+rowNum).val();
	            var currentQuantity  =$("#currentQuantity"+rowNum).val();
	            var packetOut  =$("#packagesOut"+rowNum).val();
	            
	            if(parseInt(packetOut) > parseInt(currentQuantity)){
	            	 bootAlert.show("error","Pkge out cant be greater than currentQuantity");
	 	            return false;
	            }
            	if(wsoInfo == "Select Wso" || wsoInfo == ""){
            		return;
            	}	
            	
            	if(lotInfo == "Select Lot" || lotInfo == ""){
            		return;
            	}
            	
            	if(packetOut == undefined || packetOut == "" || parseInt(packetOut) <= 0){
            		return;
            	}
	            wsoInfows.push({"wsoInfo":wsoInfo,"lotInfo":lotInfo,"expiryDate":expiryDate,"packagesOut":packetOut});
	    	});
	        var wsoDtls  = encodeURIComponent(JSON.stringify(wsoInfows));
	        var jsondata = {"dateOfDelivery":dateOfDelivery,"clientId":clientInfo,"dlNo":dlNo,"timeOfIssue":timeOfIssue,"nameOfReceiver":nameOfReceiver,"vehicleNo":vehicleNo,"nricOfReceiver":nricOfReceiver,"verify":verify,"wsoInfows":wsoDtls};
	    $.ajax({
	        type: "POST",
	        url: contextPath+"/deliveryLists/saveDeliveryList",
	        data: jsondata,
	       // data: '{"dateOfDelivery":"'+dateOfDelivery+'","dlNo":"'+dlNo+'","timeOfIssue":"'+timeOfIssue+'","nameOfReceiver":"'+nameOfReceiver+'","vehicleNo":"'+vehicleNo+'","nricOfReceiver":"'+nricOfReceiver+'","verify":"'+verify+'"}',
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	bootAlert.show("success","Save Sucessfully DLNO : "+data.dlNo)
	        	//$('#clearDeliveryListField')[0].reset();
	        	//$('#deliveryTbody')[0].reset();
	        	delivery.clearDeliveryLists();
	            console.log("success.....");
	            

	        },
	        error: function (e) {

	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);

	            console.log("ERROR : ", e);

	        }
	    });

	}
	    // End Save Delivery List
	    
	    // Start Dl detail
	     this.loadDlDetiail = function(){
	    	 var clientId = $("#clientInfo").val();
	    	 clientId = parseInt(clientId);
	    	var dlNoss = $("#dlNo").val();
	    	
	    	if(dlNoss == "" ){	 
	    		
	    		$('#dateOfDelivery').val("");	
				$('#timeOfIssue').val("");
				$('#nameOfReceiver').val("");
				$('#vehicleNo').val("");
				$('#nricOfReceiver').val("");
				$('#verify').val("false");
				$('#totalQuantity').val("");
				
				for (i = 0; i <= rowNumber; i++){
					$('#expiryDate'+i+'').val("");
					$('#lotQuantity'+i+'').val("");
					$('#currentQuantity'+i+'').val("");
					$('#packagesOut'+i+'').val("");
				}
				
		    	 for (i = 0; i < ('#wsoInfo').length; i++){
		    		 $("#wsoInfo"+i).empty();
		    		 $('#wsoInfo'+i).append("<option value =''>Select Wso</option>");
		            }
		    	 
		    	 for (i = 0; i < ('#lotInfo').length; i++){
		    		 $("#lotInfo"+i).empty();
		    		 $('#lotInfo'+i).append("<option value =''>Select Lot</option>");
		            }
	    	}
	    	
	    	if(dlNoss != "" ){
				$.ajax({
			        type: "GET",
			        contentType: "application/json",
			        url: contextPath+"/wsoInfos/getAllWsoByClientId/"+clientId,
			        dataType: 'json',
			        async : false,
			        cache: false,
			        timeout: 600000,
			        success: function (data) {
			        	console.log(data)
			        	wsoListForDlvery=[];
			        	for (i = 0; i < data.length; i++){
			        		wsoListForDlvery.push({"id":data[i].wsoId,"value":data[i].wsoNo});
			            }
			        	//$("#deliveryTbody").empty();
			        	//delivery.newRow();
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
	    	
	    	
	    	if(dlNoss != "" ){
	    		
	    	var dlNo = $("#dlNo").val();
	    	var jsondata = {"dlNo":dlNo}
	    	$.ajax({
	            type: "POST",
	            url: contextPath+"/deliveryLists/findDeliveryDetail",
	            data: jsondata,
	           // data: '{"dateOfDelivery":"'+dateOfDelivery+'","dlNo":"'+dlNo+'","timeOfIssue":"'+timeOfIssue+'","nameOfReceiver":"'+nameOfReceiver+'","vehicleNo":"'+vehicleNo+'","nricOfReceiver":"'+nricOfReceiver+'","verify":"'+verify+'"}',
	            dataType: 'json',
	            cache: false,
	            timeout: 600000,
	            success: function (data) {
	            	//delivery.getAllWsoByClientId($("#clientInfo").val());
	            	var timeOfIssue=data[0].timeOfIssue;
	            	var nameOfReceiver = data[0].nameOfReceiver;
	            	var vehicleNo=data[0].vehicleNo;
	            	var nricOfReceiver=data[0].nricOfReceiver;
	            	var verify=data[0].verify;
	            	var totalQty=data[0].totalQty;
	            	var dateOfDelivery  = data[0].dateOfDelivery;
	            	
	            	$("#dateOfDelivery").val(dateOfDelivery);
	            	$("#timeOfIssue").val(timeOfIssue);
	            	$("#nameOfReceiver").val(nameOfReceiver)
	            	$("#vehicleNo").val(vehicleNo);
	            	$("#nricOfReceiver").val(nricOfReceiver);
	            	if(verify){
	            		$("#verify").val("true");
	            		$("#saveDeliveryLists").hide();
	            	}else{
	            		$("#verify").val("false");
	            		$("#saveDeliveryLists").show();
	            	}
	            	$("#deliveryTbody").empty();
	            	for(var i = 0; i<data.length; i++){
	            		delivery.newRow();
	            		var lotInfo  = data[i].lotInfo;
	            		var wsoInfo  = data[i].wsoInfo;
	            		$("#wsoInfo"+rowNumber).val(wsoInfo.wsoId);
	            		
	            		delivery.getAllLotByWsoId(parseInt(wsoInfo.wsoId),rowNumber);
	            		
	            		$("#lotInfo"+rowNumber).val(lotInfo.lotId);
	            		delivery.getLotDetailByWsoforDlvr(parseInt(lotInfo.lotId),rowNumber);
	            		$("#packagesOut"+rowNumber).val(data[i].totalQty);
	            	}
	            	delivery.changepackegeout();
	            },
	            error: function (e) {
	                var json = "<h4>Ajax Response</h4><pre>"
	                    + e.responseText + "</pre>";
	                $('#feedback').html(json);
	                console.log("ERROR : ", e);
	            }
	        });
	     }else{
	    	 $("#saveDeliveryLists").show();
	    	}
	    	
	    }
	    // End Dl detail
	     
	     
	     // Start clear delivery lists
	     this.clearDeliveryLists = function(){
	    	 $('#clearDeliveryListField')[0].reset();
	    	 $('#dlNo').empty();
	    	 $('#dlNo').append("<option value =''>Select DL No</option>");
	    	 
	    	 for (i = 0; i < ('#wsoInfo').length; i++){
	    		 $("#wsoInfo"+i).empty();
	    		 $('#wsoInfo'+i).append("<option value =''>Select Wso</option>");
	            }
	    	 for (i = 0; i < ('#lotInfo').length; i++){
	    		 $("#lotInfo"+i).empty();
	    		 $('#lotInfo'+i).append("<option value =''>Select Lot</option>");
	            }
	    	 
	    	
	    	 //$('#deliveryTbody')[0].reset();
	     }
	  // End clear delivery lists
	     
	     this.deleteRow=function(thisobj){
	    	    event.preventDefault();
	    	    if($("#deliveryTbody tr").length>1){
	    	    	$(thisobj).closest("tr").remove();
	    	    }
	    	    delivery.changepackegeout();
	    	    
	    	}

}