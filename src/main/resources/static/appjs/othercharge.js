var OTHER_CHARGE = function() {
	var lot;
	//var warehouseName = "";
	var chargeITemList = new Array();
	var rowNumber  = 0
	this.show = function(){
		
		$("#content-wrapper").empty();
	    $("#content-wrapper").append('<div class="row">'+
                '<div class="col-lg-12">'+
                '<div class="row">'+
                    '<div class="col-lg-12">'+
                        '<h1>Other Charges</h1>'+
                    '</div>'+
                '</div>'+
                '<div class="row delivery-style datesscroll">'+
                    '<div class="col-lg-12">'+
                        '<form role="form" id="otherChargesForm">'+
                            '<div class="main-box">'+
                                '<header class="main-box-header clearfix">'+

                                '</header>'+
                                '<div class="main-box-body clearfix">'+
                                    '<div class="row">'+
                                        '<div class="col-lg-4">'+
                                            '<div class="form-group">'+
                                                '<label>Client Name</label>'+
                                                '<select class="form-control" id="clientInfo" onchange="othercharge.loadFormDetail(this)">'+
                                                    '<option>Select Client</option>'+

                                                '</select>'+
                                            '</div>'+
                                        '</div>'+
                                        '<div class="col-lg-4"></div>'+
                                        '<div class="col-lg-4">'+
                                            '<div class="form-group">'+
                                                '<label>Verify_Ind</label>'+
                                                '<select class="form-control" id="verifyInd">'+
                                                    '<option  value="yes">Yes</option>'+
                                                    '<option selected  value="no">No</option>'+
                                                '</select>'+
                                            '</div>'+
                                        '</div>'+
                                    '</div>'+
                                    '<div class="row">'+
                                        '<div class="col-lg-4">'+
                                            '<div class="form-group">'+
                                                '<label>Warehouse Name</label>'+
                                                '<input type="text" class="form-control" id="warehouseName" readonly>'+
                                            '</div>'+
                                        '</div>'+
                                        '<div class="col-lg-4"></div>'+
                                        '<div class="col-lg-4">'+
                                            '<div class="form-group">'+
                                                '<label>Form No</label>'+
                                                '<select class="form-control" id="formNo" onchange="othercharge.loadclientOtherCharge(this)"></select>'+
                                            '</div>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>'+
                            '</div>'+
                            '<div class="main-box" style="background: #eeeeee">'+
                                '<div class="main-box-body clearfix">'+
                                    '<div class="table-responsive">'+
                                        '<table class="table">'+
                                            '<thead>'+
                                                '<tr>'+
                                                    '<th class="text-center">Charge Item</th>'+
                                                    '<th class="text-center">From Date</th>'+
                                                    '<th class="text-center">To Date</th>'+
                                                    '<th class="text-center">Time From</th>'+
                                                    '<th class="text-center">Time To</th>'+
                                                    '<th class="text-center">Billable Unit</th>'+
                                                    '<th class="text-center">Rate</th>'+
                                                    '<th class="text-center">Amount</th>'+
                                                    '<th class="text-center">Narration</th>'+
                                                    '<th class="text-center">Action</th>'+
                                                '</tr>'+
                                            '</thead>'+
                                            '<tbody id="otherChargeTbody">'+
                                                
                                            '</tbody>'+
                                        '</table>'+
                                    '</div>'+
                                    '<br>'+
                                    '<div class="row">'+
                                        '<div class="col-lg-2"></div>'+
                                        '<div class="col-lg-2">'+
                                            '<div class="btn btn-success" id="saveOtherCharges" onclick="othercharge.saveOtherCharges()">Save</div>'+
                                        '</div>'+
                                        '<div class="col-lg-2">'+
                                            '<div class="btn btn-warning" id="clearOtherCharges" onclick="othercharge.clearOtherCharge()">Clear</div>'+
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
	    $("#warehouseName").val(warehouseName);
	    othercharge.loadAllclient();
	    //othercharge.newRow1();
	    othercharge.getChargeItems();
	}

	// load all client on tallysheet menu click
	this.loadAllclient = function (){
		var data = common.getAllclient();
		for(var i=0;i<data.length;i++){
            $('#clientInfo').append(
                "<option value="+data[i].clientInfoId+">"+data[i].clientTitle+"</option>"
            );
        }
	} // end of load all client on tallysheet menu click
	

	//load form details
	this.loadFormDetail = function (){
		$("#saveOtherCharges").show();
		var clientInfo = $("#clientInfo").val();
		if(clientInfo == "" ||  clientInfo == "Select Client" ){
			 $('#otherChargesForm')[0].reset();
			 $("#warehouseName").val(warehouseName);
		}else{
			 $('#otherChargesForm')[0].reset();
			 $("#warehouseName").val(warehouseName);
			 $("#clientInfo").val(clientInfo);
			 
			$.ajax({
		         type: "GET",
		         contentType: "application/json",
		         url: contextPath+ "/otherCharges/geteOtherChargeDetail/"+clientInfo,
		         dataType: 'json',
		         cache: false,
		         async:false,
		         timeout: 600000,
		         success: function (response) {
		        	 var data = response.data;
		        	 $("#formNo").empty();
		        	 var option = "<option value=''>select/new</option>;"
		        	 if(data.length>0){
		        		 for(var i = 0; i<data.length; i++){
		        			 option += "<option value="+data[i]+">"+data[i]+"</option>;"
		        		 }
		        		 $("#formNo").append(option);
		        	 }else{
		        		 $('#otherChargesForm')[0].reset();
		        		 $("#warehouseName").val(warehouseName);
		        		 $("#clientInfo").val(clientInfo);
		        		 $("#formNo").append(option);
		        	 }
		         },
		         error: function (e) {
		             console.log("ERROR : ", e);
		 
		         }
		     });
		}
	}
	//End form details
	
	//add new row 
	 this.newRow1 = function(){
		rowNumber += 1;
		$("#otherChargeTbody").append('<tr id='+rowNumber+'>'+
	    '<td class="text-center">'+
	    '<div class="form-group">'+
	        '<div class="input-group">'+
	            '<select class="form-control" id="otherChargeItemsList'+rowNumber+'" style="width: 100px;"  rownum='+rowNumber+' >'+
	                '<option>Select Charge Item</option>'+
	            '</select>'+
	            '<span class="input-group-addon">'+
	                '<a href="#" data-toggle="modal" data-target="#otherChargeModel">'+
	                    '<i class="glyphicon glyphicon-plus form-comtrol"></i>'+
	                '</a>'+
	            '</span>'+
	        '</div>'+
	       '</div>'+ 
	        '<div class="modal fade" id="otherChargeModel" role="dialog" tabindex="-1">'+
	            '<div class="modal-dialog modal-sm" >'+
	                '<div class="modal-content">'+
	                    '<div class="modal-header" style="background: #7FC8BA; color: #fff;">'+
	                            '<button type="button" class="close" data-dismiss="modal">&times;</button>'+
	                        '<h3 id="dmhId">ADD Charge Item</h3>'+
	                          
	                    '</div>'+
	                    '<div class="modal-body" id="modelBodyId">'+
	                        '<div class="form-group">'+
	                            '<label>Charge Item</label>'+
	                            '<input type="text" class="form-control" id="chargeItem">'+
	                            '<div class="btn btn-success" id="saveChargeItem" onclick="othercharge.saveChargeItem()">Save</div>'+
	                        '</div>'+
	                    '</div>'+
	                '</div>'+
	            '</div>'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input type="date" class="form-control" style="width: 130px;" id="fromDate'+rowNumber+'" rownum='+rowNumber+' >'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input type="date" class="form-control" style="width: 130px;" id="toDate'+rowNumber+'"  rownum='+rowNumber+' >'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input type="text" class="form-control time-picker" style="width: 75px;" id="fromTime'+rowNumber+'"  rownum='+rowNumber+' >'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input type="text" class="form-control time-picker" style="width: 75px;" id="ToTime'+rowNumber+'"   rownum='+rowNumber+' >'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input type="number" step="0.00" class="form-control" placeholder="Billable Unit" style="width: 90px;" id="bilableUnit'+rowNumber+'"  rownum='+rowNumber+' onblur="othercharge.calculateBillamount('+rowNumber+')" >'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input type="number" step="0.00" class="form-control" placeholder="Rate" style="width: 90px;" id="bilableRate'+rowNumber+'" rownum='+rowNumber+' onblur="othercharge.calculateBillamount('+rowNumber+')"  >'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<input type="number" step="0.00" class="form-control" placeholder="Amount" style="width: 90px; background-color: white;" id="bilableAmount'+rowNumber+'" rownum='+rowNumber+' readonly>'+
	        '</div>'+
	    '</td>'+
	    '<td class="text-center">'+
	        '<div class="form-group">'+
	            '<textarea rows="3" cols="50" class="form-control" placeholder="Naration" style="width: 170px;" id="naration'+rowNumber+'"  rownum='+rowNumber+' ></textarea>'+
	        '</div>'+
	    '</td>'+
	    '<td style="width: 15%;" class="text-center">'+
	    '<a href="#"  onclick="othercharge.newRow1()" style="font-size:20px;">'+
	                                                    '<i class="fa fa-plus"></i>'+
	                                            '</a>&nbsp;&nbsp; '+
	                                            '<a href="#" onclick="othercharge.deleteRow(this)" style="font-size:20px; color:#ff4444;">'+
	                                                    '<i class="fa fa-trash-o"></i>'+
	                                            '</a>'+
	'</td>'+
	'</tr>');
		$(".time-picker").hunterTimePicker();
		 var option = "";
		   option  += "<option value=''>Select</option>"
		   for (i = 0; i < chargeITemList.length; i++){
				option += "<option value="+chargeITemList[i].id+" >"+chargeITemList[i].value+" </option>"
		   }
			$("#otherChargeItemsList"+rowNumber).empty();
			$("#otherChargeItemsList"+rowNumber).append(option);
			$(".time-picker").hunterTimePicker();
	}

	// End new row 
	 
	 
	 //delete row
	 this.deleteRow = function(thisobj){
		    event.preventDefault();
		    if($("#otherChargeTbody tr").length>1){
		    	$(thisobj).closest("tr").remove();
		    }
		}
	 //end delete row
	 
	 
	 //Save OtherCharges
	  this.saveOtherCharges = function() {
		 	var chargTemInfos = new Array(); 
	        var verifyInd = $("#verifyInd").val();
	        var warehouseName = $("#warehouseName").val();
	        var formNo = $("#formNo").val();
	        var clientInfo = $("#clientInfo").val();
		if(clientInfo == "" || clientInfo == undefined  || clientInfo == "Select Client" ){
	        	bootAlert.show("error","Please Select Client First ");
	        	return false;
	       }
	        flag = true;
	        $("#otherChargeTbody tr").each(function() {
	        	var rowNum = $(this).attr("id");
	        	var chargeItems = $("#otherChargeItemsList"+rowNum).val();
	        	var fromDate = $("#fromDate"+rowNum).val();
	            var toDate = $("#toDate"+rowNum).val();
	            var fromTime = $("#fromTime"+rowNum).val();
	            var toTime = $("#ToTime"+rowNum).val();
	            var bilableUnit = $("#bilableUnit"+rowNum).val();
	            var bilableRate = $("#bilableRate"+rowNum).val();
	            var bilableAmount = $("#bilableAmount"+rowNum).val();
	            var naration = $("#naration"+rowNum).val();
	            
	            if(bilableUnit == "" || bilableUnit == undefined)
	            {
	            	bootAlert.show("error","Please Enter Bilable Unit ");
	            	flag = false;
	            }
	            
	            if(bilableRate == "" || bilableRate == undefined)
	            {
	            	bootAlert.show("error","Please Enter Bilable rate ");
	            	flag = false;
	            }
	            
	            if(naration == "" || naration == undefined)
	            {
	            	bootAlert.show("error","Please Enter Narration ");
	            	flag = false;
	            }
	            
		    if(chargeItems == "" || chargeItems == undefined)
	            {
	            	bootAlert.show("error","Please Select / Enter Charge Item ");
	            	flag = false;
	            }
	            chargTemInfos.push({"fromTime":fromTime,"ToTime":toTime,"fromDate":fromDate,"toDate":toDate,"warehouseName":warehouseName,"formNo":formNo,"bilableUnit":bilableUnit,"bilableRate":bilableRate,"bilableAmount":bilableAmount,"naration":naration,"chargeItemId":chargeItems});
	    	});
	        
	        if(flag == false){
	        	bootAlert.show("error","Please Select / Enter Charge Item or billable Unit or rate or narration");
	        	return false;
	        }
	        var data  = encodeURIComponent(JSON.stringify(chargTemInfos));
	        
	        var jsondata = {"data":data,"clientId":clientInfo,"verifyInd":verifyInd,"formNumber":formNo,"warehouseName":warehouseName};
	        $.ajax({
	            type: "POST",
	            url: contextPath+ "/otherCharges/saveOtherCharges",
	            data: jsondata,
	           // data: '{"dateOfDelivery":"'+dateOfDelivery+'","dlNo":"'+dlNo+'","timeOfIssue":"'+timeOfIssue+'","nameOfReceiver":"'+nameOfReceiver+'","vehicleNo":"'+vehicleNo+'","nricOfReceiver":"'+nricOfReceiver+'","verify":"'+verify+'"}',
	            dataType: 'json',
	            cache: false,
	            timeout: 600000,
	            success: function (data) {
	            console.log("SUCCESS : ", data);
	            //bootbox.alert("success","Other Charges Is Save...")
	            $('#otherChargesForm')[0].reset();
	            $("#warehouseName").val(warehouseName);
	            $("#clientInfo").val(clientInfo);
	            bootAlert.show("success","Other Charges Save Succesfully : " + data.formNo);
	            othercharge.loadFormDetail();

	        },
	        error: function (e) {

	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);

	            console.log("ERROR : ", e);

	        }
	    });

	}

	 //End save othercharges
	 
	 //Calculate Bill Items
	 this.calculateBillamount = function(rowNum){
			var unit = $("#bilableUnit"+rowNum).val();
			var rate  = $("#bilableRate"+rowNum).val();
			if(unit == "" || unit == undefined ){
				 $("#bilableAmount"+rowNum).val("");
				 return false
			}
			if(rate == "" || rate == undefined ){
				 $("#bilableAmount"+rowNum).val("");
				 return false;
			}
			unit = parseFloat(unit);
			rate  = parseFloat(rate);
			var total = unit*rate;
			$("#bilableAmount"+rowNum).val(total.toFixed(2));
		}
	 // End Calculate bill items
	 
	 //Save ChargeItem
	  this.saveChargeItem = function(){
		 var chargeItem  = $("#chargeItem").val();
		 	 $.ajax({
		          type: "POST",
		          contentType: "application/json",
		          url: contextPath+ "/chargeItems/",
		          data: '{"chargeItem":"'+chargeItem+'"}',
		          dataType: 'json',
		          cache: false,
		          async:false,
		          timeout: 600000,
		          success: function (data) {
		              var id = data.chargeItemId;
		              var value = data.chargeItem;
		              chargeITemList.push({"id":id,"value":value});
		              othercharge.refereshChargeItem();
		         	 $("#otherChargeModel").modal("hide");
		          },
		          error: function (e) {
		              console.log("ERROR : ", e);
		  
		          }
		      });
		 }
	 //End Save ChargeItem
	  
	  //Refresh ChargeItem
	  this.refereshChargeItem = function(){
			var option = "";
			   option  += "<option value=''>Select</option>"
			   for (i = 0; i < chargeITemList.length; i++){
					option += "<option value="+chargeITemList[i].id+" >"+chargeITemList[i].value+" </option>"
			   }
			$("#otherChargeTbody tr").each(function() {
		    	var rowNum = $(this).attr("id");
		    	$("#otherChargeItemsList"+rowNum).empty();
				$("#otherChargeItemsList"+rowNum).append(option);
		   	});
		}
	  //End Refresh ChargeItem
	  
	  //Get Charge Item
	  this.getChargeItems = function(){

			$.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+ "/chargeItems/",
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
		        	chargeITemList=[];
		        	for (i = 0; i < data.length; i++){
		        		chargeITemList.push({"id":data[i].chargeItemId,"value":data[i].chargeItem});
		            }
		        	othercharge.newRow1();
		        },
		        error: function (e) {
		            var json = "<h4>Ajax Response</h4><pre>"
		                + e.responseText + "</pre>";
		            $('#feedback').html(json);
		            console.log("ERROR : ", e);
		        }
		    });
		}

	  //End GetChargeItem
	  
	  //loadClient
	   this.loadclientOtherCharge = function(thisObj){
			$("#saveOtherCharges").show();
			var clientInfo = $("#clientInfo").val();
			if(clientInfo == "" ||  clientInfo == "Select Client" ){
				 $('#otherChargesForm')[0].reset();
				 $("#warehouseName").val(warehouseName);
			}else{
				
				var formNumber = $(thisObj).val();
				if(formNumber == "" ||  formNumber == "" ){
					 $('#otherChargesForm')[0].reset();
					 $("#warehouseName").val(warehouseName);
					 $("#clientInfo").val(clientInfo);
					 
					 return false;
				}
				$.ajax({
			         type: "GET",
			         contentType: "application/json",
			         url: contextPath+ "/otherCharges/geteOtherChargeDetail/"+clientInfo+"/"+formNumber,
			         dataType: 'json',
			         cache: false,
			         async:false,
			         timeout: 600000,
			         success: function (data) {
			        	
			        	 if(data.length>0){
			        		 $("#verifyInd").val(data[0].verifyInd);
			        		 if(data[0].verifyInd == "yes"){
			        			$("#saveOtherCharges").hide();
			        		 }else{
			        			 $("#saveOtherCharges").show(); 
			        		 }
				        	 $("#warehouseName").val(data[0].warehouseName);
				        	 $("#formNo").val(data[0].formNo);
				        	 $("#otherChargeTbody").empty();
				        	 for(var i =0; i<data.length; i++){
				        		 othercharge.newRow1();
				        		 $("#fromDate"+rowNumber).val(data[i].fromDate);
					        	 $("#toDate"+rowNumber).val(data[i].toDate);
					        	 $("#fromTime"+rowNumber).val(data[i].fromTime);
					        	 $("#ToTime"+rowNumber).val(data[i].ToTime);
					        	 $("#bilableUnit"+rowNumber).val(data[i].bilableUnit.toFixed(2));
					        	 $("#bilableRate"+rowNumber).val(data[i].bilableRate.toFixed(2));
					        	 $("#bilableAmount"+rowNumber).val(data[i].bilableAmount.toFixed(2));
					        	 $("#naration"+rowNumber).val(data[i].naration);
					        	 $("#totalAmount"+rowNumber).val(data[i].totalAmount.toFixed(2));
					        	 $("#grandTotalAmount"+rowNumber).val(data[i].grandTotalAmount.toFixed(2));
					        	 $("#otherChargeItemsList"+rowNumber).val(data[i].chargeItems.chargeItemId);
				        	 } 
			        	 }else{
			        		 $('#otherChargesForm')[0].reset();
			        		 $("#warehouseName").val(warehouseName);
			        		 $("#clientInfo").val(clientInfo);
			        		 $("#saveOtherCharges").show();
			        		 
			        	 }
			         },
			         error: function (e) {
			             console.log("ERROR : ", e);
			 
			         }
			     });
			}
		}
	  //End load Client
	   
	   //Start clear othercharges
	   this.clearOtherCharge = function(){
		   $('#otherChargesForm')[0].reset();
	       $("#warehouseName").val(warehouseName);
	   }
	   //End Clear other charges
}