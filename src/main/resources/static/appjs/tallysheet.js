
var TALLYSHEET = function() {
	var warehouseName;
	this.show = function() {
        $("#content-wrapper").empty();
        	var html = "";
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-12\">'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-12\">'
        	html += '<h1>Tally Sheet</h1>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"row table-style\" >'
        	html += '<div class=\"col-lg-12 full-height\">'
        	html += '<form id=\"tallysheetForm\" class=\"full-height\">'
        	html += '<div class=\"main-box datesscroll style-overflow\" >'
        	html += '<header class=\"main-box-header clearfix\">'
        	html += ''
        	html += '</header>'
        	html += '<div class=\"main-box-body clearfix\">'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Client Name</label>'
        	html += '<select class=\"form-control\" id=\"clientInfo\" onchange="tally.onClientSelect(this)">'
        	html += '<option>Select Client</option>'
        	html += '</select>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>T/S No</label>'
        	html += '<select class=\"form-control\" id=\"tallysheetNumber\"  onchange="tally.onTallySheetSelect(this)">'
        	html += '<option>Select / Add New</option>'
        	html += '</select>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Reefer/Dry</label>'
        	html += '<select class=\"form-control\" id=\"refDry\">'
        	html += '<option value=\"\">Select Reefer/Dry </option>'
        	html += '<option value=\"R\">R</option>'
        	html += '<option value=\"D\">D</option>'
        	html += '</select>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Storage Date</label>'
        	html += '<input type=\"date\" class=\"form-control\" placeholder=\"Stroage Date\" id=\"storageDate\">'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Lorry/Cntr</label>'
        	html += '<select class=\"form-control\" id=\"lorryContainer\">'
        	html += '<option value=\"\">Select Lorry/Cntr</option>'
        	html += '<option>L</option>'
        	html += '<option>C</option>'
        	html += '</select>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Ex Vessel</label>'
        	html += '<input type=\"text\" class=\"form-control\" id=\"exVessel\" placeholder=\"Ex Vessel\">'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Temp Recorded</label>'
        	html += '<input type=\"number\" step=\"0.00\" class=\"form-control\" id=\"tempRecorded\" placeholder=\"Temp Recorded\">'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>containerNo</label>'
        	html += '<input type=\"text\" class=\"form-control\" id=\"containerNo\" placeholder=\"containerNo\">'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Temp(Unstuff/Unload)</label>'
        	html += '<input type=\"number\" step=\"0.00\" class=\"form-control\" id=\"tempUnstuddUnload\" placeholder=\"Temp(Unstuff/Unload)\">'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Warehouse Name</label>'
        	html += '<input type=\"text\"  class=\"form-control\" id=\"warehouseName\" readonly>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Grand Tot Qty</label>'
        	html += '<input type=\"number\" step=\"0.00\" min=\"0\" class=\"form-control\" id=\"grndTtlQty\" readonly>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Ops Name</label>'
        	html += '<input type=\"text\" class=\"form-control\" value="'+LoggedUserIdVar+'" id=\"opsName\" readonly>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Grand Tot</label>'
        	html += '<input type=\"number\" step=\"0.00\" min=\"0\" class=\"form-control\" id=\"grndTot\" readonly>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Verify</label>'
        	html += '<select class=\"form-control\" id=\"verify\">'
        	html += '<option value=\"false\" selected>No</option>'
        	html += '<option value=\"true\">Yes</option>'
        	html += '</select>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"main-box\" style=\"background: #eeeeee\">'
        	html += '<div class=\"main-box-body clearfix\">'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-2\">'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<div class=\"btn btn-success\" id=\"saveTallysheet\" style=\"margin-top: 20px;\" onclick="tally.saveTallysheet()">Save</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<div class=\"btn btn-warning\" id=\"clearTallysheetField\" style=\"margin-top: 20px;\" onclick="tally.clearTallysheet()">Clear</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<a  href=\"index.html\" class=\"btn btn-danger\" style=\"margin-top: 20px;\">Exit</a>'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<!-- <button class=\"btn btn-info\" onclick=\"openBilling()\">Generate-Bill</button> -->'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<div class=\"btn btn-primary\" style=\"margin-top: 20px;\" id=\"openWsoWindow\" onclick=\"tally.openwso(this)\">WSO-Information</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '</form>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<footer id=\"footer-bar\" class=\"row\">'
        	html += '<p id=\"footer-copyright\" class=\"col-xs-12\">'
        	html += 'Powered by Sandrokottos From StrongMoments Pvt Ltd'
        	html += '</p>'
        	html += '</footer>'
          $("#content-wrapper").append(html);
          //$("#warehouseName").val(warehouseName);
          
          tally.getcurrentdate();
          tally.loadAllclient();
          tally.getWarehouseName();
	} // end of show method 
	
	this.getcurrentdate=function(){
		var date = new Date();

  		var day = date.getDate();
  		var month = date.getMonth() + 1;
  		var year = date.getFullYear();

  		if (month < 10) month = "0" + month;
  		if (day < 10) day = "0" + day;

  		var today = year + "-" + month + "-" + day;


  		document.getElementById('storageDate').value = today;
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
	
	
	
	
	// Save Tallysheet
	this.saveTallysheet = function(redirectToWso){
	        var tallysheetNumber =  $("#tallysheetNumber option:selected").text();
	        var tallysheetId =  $("#tallysheetNumber option:selected").val();
	        
	        if(tallysheetNumber  == "Select / Add New"){
	        	tallysheetNumber   = "";
	        }
	        var id  =  $("#tallysheetNumber option:selected").val();
	        if(id == undefined ||  id == "" ||  id == "Select / Add New" ){
	        	 id = -1;
	        }
	        var clientInfo = $("#clientInfo").val();
	        console.log("tallysheetNo : ----", tallysheetNumber);
	        var refDry = $("#refDry").val();
	        var storageDate = $("#storageDate").val();
	        var lorryContainer = $("#lorryContainer").val();
	        var exVessel = $("#exVessel").val();
	        var tempRecorded = $("#tempRecorded").val();
	        var containerNo = $("#containerNo").val();
	        var tempUnstuddUnload = $("#tempUnstuddUnload").val();
	        console.log("/nrefDry : "+refDry+"/nstorageDate : "+storageDate+"/nlorryContainer : "+lorryContainer+"/n")
	        console.log("exVessel : "+exVessel+"/tempRecorded : "+tempRecorded+"/containerNo : "+containerNo+"/tempUnstuddUnload : "+tempUnstuddUnload+"/n")
	        var opsName = $("#opsName").val();
	        var grndTot = $("#grndTot").val();
	        var grndTtlQty = $("#grndTtlQty").val();
	        var verify = $("#verify").val();
	        var d = new Date();
	        d.setDate(d.getDate()-7);
	        console.log(d)
	        if(Date.parse(d) > Date.parse(storageDate)){
	        	 bootAlert.show("error","Storage Date can't be less than 7 days earlier");
	            return false;
	        }

	        if(refDry == "" || refDry == undefined){
	        	 bootAlert.show("error","Select Reefer/Dry.");
	            return false;
	        }
	        if(storageDate == "" || storageDate == undefined){
	        	 bootAlert.show("error"," Enter Stroage Date");
	            return false;
	        }
	        if(lorryContainer == "" || lorryContainer == undefined){
	        	 bootAlert.show("error"," Select Lorry-Contaninar");
	            return false;
	        }
	        if(exVessel == "" || exVessel == undefined){
	        	 bootAlert.show("error"," Enter EX-Vessel");
	            return false;
	        }
	        if(tempRecorded == "" || tempRecorded == undefined){
	        	 bootAlert.show("error"," Enter Temperature Recorded");
	            return false;
	        }
	        if(containerNo == "" || containerNo == undefined){
	        	 bootAlert.show("error"," Enter containerNo");
	            return false;
	        }
	        if(tempUnstuddUnload == "" || tempUnstuddUnload == undefined){
	        	 bootAlert.show("error"," Enter Temperature Recorded for Unload");
	            return false;
	        }
	       
	        var requestType = "POST";
	        var requestURL = "/tallysheets/";
	       if(id != -1 && id != "-1"){
	    	   requestType = "PUT";
	    	   requestURL ="/tallysheets/"+id;
	       }
	     $.ajax({
	        type:requestType,
	        contentType: "application/json",
	        url: contextPath+requestURL ,
	         data: '{"tallysheetId":"'+id+'","tallysheetNumber":"'+tallysheetNumber+'","opsName":"'+opsName+'","containerNo":"'+containerNo+'","refDry":"'+refDry+'","storageDate":"'+storageDate+'","lorryContainer":"'+lorryContainer+'","exVessel":"'+exVessel+'","tempRecorded":"'+tempRecorded+'","tempUnstuddUnload":"'+tempUnstuddUnload+'","grndTot":"'+grndTot+'","grndTtlQty":"'+grndTtlQty+'","verify":"'+verify+'","clientInfo":{"clientInfoId":"'+clientInfo+'"}}',
	         dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        async : false,
	        success: function (data) {
	        	if(requestType == "PUT"){
	           	    bootAlert.show("success", "Tallysheet updated successfully !");
	           	 $('#tallysheetForm')[0].reset();
	           	 if(redirectToWso){
		        		wso1.show(clientInfo,data.tallysheetId )
		        	}
	        	}else{
	        	 $('#tallysheetForm')[0].reset()
	        	 bootAlert.show("success", "Tallysheet Successfully saved with Number : "+data.tallysheetNumber);
	        	 tally.getAllTallySheetByClientId(clientInfo);
	        	 if(redirectToWso){
		        		wso1.show(clientInfo,data.tallysheetId )
		        	}
		         tally.customReferesh();
	        	}
	        	
	        	if(data.verify == true || data.verify == "true"){
	            	$("#verify").val("true");
	            	$("#openWsoWindow").addClass("nochange");
	            	$("#saveTallysheet").hide();
	            }else{
	            	$("#verify").val("false");
	            	$("#saveTallysheet").show();
	            	$("#openWsoWindow").removeClass("nochange");
	            }
	        	$("html, body").animate({ scrollTop: 0 }, "slow");
	        },
	        error: function (e) {

	            var json = "<h4>Ajax Response</h4><pre>"
	                + e.responseText + "</pre>";
	            $('#feedback').html(json);
	            console.log("ERROR : ", e);
	        }
	    });

	}

	//Function to get TallySheet Number
	this.getTallysheetNumber =function(){
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+ "/tallysheets/allTallysheetId/",
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	            console.log("success.....", data);
	            for (i = 0; i < data.length; i++){ 
	                $('#tallysheetNumber').append(
	                    $('<option>',{
	                        value: data[i],
	                        text : data[i] 
	                    })
	                );
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
	//-------------  Update Tallysheet  -------------------
    this.updateTallysheet = function(updateTallyId) {
        // var warehouseName = $("#warehouseName").val();
        var refDry = $("#refDry").val();
        var storageDate = $("#storageDate").val();
        var lorryContainer = $("#lorryContainer").val();
        var exVessel = $("#exVessel").val();
        var tempRecorded = $("#tempRecorded").val();
        var containerNo = $("#containerNo").val();
        var tempUnstuddUnload = $("#tempUnstuddUnload").val();
        
        console.log("/nrefDry : "+refDry+"/nstorageDate : "+storageDate+"/nlorryContainer : "+lorryContainer+"/n")
        console.log("exVessel : "+exVessel+"/tempRecorded : "+tempRecorded+"/containerNo : "+containerNo+"/tempUnstuddUnload : "+tempUnstuddUnload+"/n")

        var id = 1;
        console.log("updateTallysheet() calling");
    
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: contextPath+ "/tallysheets/"+updateTallyId,
            data: '{"refDry":"'+refDry+'","storageDate":"'+storageDate+'","exVessel":"'+exVessel+'","tempRecorded":"'+tempRecorded+'","tempUnstuddUnload":"'+tempUnstuddUnload+'"}',
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("success.....");
    
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#feedback').html(json);
    
                console.log("SUCCESS : ", data);
                $('#tallysheetForm')[0].reset();
                bootbox.bootbox.alert("Tallysheet updated.....");
    
            },
            error: function (e) {
    
                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);
    
                console.log("ERROR : ", e);
    
            }
        });
        
    
    }

  //-------------  Select Tallysheet By Id  -------------------
    this.getAllTallySheetByClientId=function(id) {
       console.log("getAllTallySheetByClientId() calling");
       if(id != "" && id != undefined){
    	   $.ajax({
    	       type: "GET",
    	       contentType: "application/json",
    	       url: contextPath+ "/tallysheets/"+id,
    	       dataType: 'json',
    	       cache: false,
    	       timeout: 600000,
    	       async:false,
    	       success: function (data) {
    	    	   $("#tallysheetNumber").empty();
    	    	   $("#tallysheetNumber").append("<option value=''>Select / Add New</option>");
    	    	   var option= "";
    	    	   for(var i =0; i<data.length; i++){
    	    		   $("#tallysheetNumber").val();
    	    		   var tallysheetNo = data[i].tallysheetNumber;
    	    		   var tallysheetId = data[i].tallysheetId;
    	    		   option += "<option value="+tallysheetId+">"+tallysheetNo+"</option>";
    	    	   }
    	    	   $("#tallysheetNumber").append(option);
    	       },
    	       error: function (e) {

    	           var json = "<h4>Ajax Response</h4><pre>"
    	               + e.responseText + "</pre>";
    	           $('#feedback').html(json);
    	           console.log("ERROR : ", e);
    	       }
    	   });
       }else{
    	   $("#tallysheetNumber").empty();
    	   $("#tallysheetNumber").append("<option value=''>Select TallySheet</option>");
       }
    }
    
    //Function to get Tallysheet Detail
    this.getTallysheetDetail=function(tallysheetId){
    	if(tallysheetId != undefined && tallysheetId != ""){
    		var id = $("#clientInfo option:selected").val();
        	if(id == "Select Client" || id == ""){
        		bootAlert.show("error","Please Select Client!");
        		return false;
        	}
    		$.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+"/tallysheets/getTallysheetDetail/"+tallysheetId,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	//alert(JSON.stringify(data));
    	            console.log("success.....", data.containerNo);
    	            tallyId = data.tallysheetId;
    	            $("#containerNo").val(data.containerNo);
    	            $("#warehouseName").val(warehouseName);
    	            $("#refDry").val(data.refDry);
    	            $("#storageDate").val(data.storageDate);

    	            $("#lorryContainer").val(data.lorryContainer);
    	            $("#exVessel").val(data.exVessel);
    	            $("#tempRecorded").val(data.tempRecorded);
    	           // $("#containerNo").val(data.);
    	            $("#tempUnstuddUnload").val(data.tempUnstuddUnload);
                /*$("#noOfOvertimeMen").val(data.noOfOvertimeMen);
                $("#overtimeStartTime").val(data.overtimeStartTime);
                $("#overtimeEndTime").val(data.overtimeEndTime);
                $("#overtimeHours").val(data.overtimeHours);
                $("#overtimeMinutes").val(data.overtimeMinutes);
                $("#forkLiftNo").val(data.forkLiftNo);
                $("#forkLiftStartTime").val(data.forkLiftStartTime);
                $("#forkLiftEndTime").val(data.forkLiftEndTime);
                $("#forkLiftHours").val(data.forkLiftHours);
                $("#forkLiftMinutes").val(data.forkLiftMinutes);
                $("#holidaySundayIndicator").val(data.holidaySundayIndicator);
                //alert(data.holidaySundayIndicator);
                if(data.holidaySundayIndicator ==true || data.holidaySundayIndicator=="true")
                    {
                        $("#holidaySundayIndicator").val("true");
                    }else{
                        $("#holidaySundayIndicator").val("false");
                    }

                $("#transport").val(data.transport);
                $("#manpowerCharge").val(data.manpowerCharge);
                $("#otherType").val(data.otherType);
                   */
                $("#opsName").val(data.opsName);
                $("#grndTtlQty").val(data.grndTtlQty);
                    $("#grndTot").val(data.grndTot);
                    // $("#gst").val(data.gst);
                    $("#verify").val(data.verify);

    	            if(data.verify == true || data.verify == "true"){
    	            	$("#verify").val("true");
    	            	$("#openWsoWindow").addClass("nochange");
    	            	$("#saveTallysheet").hide();
    	            }else{
    	            	$("#verify").val("false");
    	            	$("#saveTallysheet").show();
    	            	$("#openWsoWindow").removeClass("nochange");
    	            }
    	            updateTallyId = data.tallysheetId;
    	            var json = "<h4>Ajax Response</h4><pre>"
    	                + JSON.stringify(data, null, 4) + "</pre>";
    	            $('#feedback').html(json);
    	            console.log("SUCCESS : ", data);
    	        },
    	        error: function (e) {
    	            var json = "<h4>Ajax Response</h4><pre>"
    	                + e.responseText + "</pre>";
    	            $('#feedback').html(json);
    	            console.log("ERROR : ", e);
    	        }
    	    });
    } else{
    	tally.customReferesh();
    	$("#saveTallysheet").show();
    }

    }
    

    //Function empyFilledTallyDetail
    this.customReferesh=function(){
  	  $("#containerNo").val("");
        $("#refDry").val("");
        $("#storageDate").val("");
        $("#lorryContainer").val("");
        $("#exVessel").val("");
        $("#tempRecorded").val("");
        $("#tempUnstuddUnload").val("");
        $("#opsName").val(LoggedUserIdVar);
        $("#otherType").val("");
        $("#grndTtlQty").val("");
        $("#grndTot").val("");
        $("#verify").val("false");
        $("#warehouseName").val(warehouseName);
        
     }
    
  //-------------  Select Tallysheet  -------------------
    this.selectTallysheet=function() {
        console.log("selectTallysheet() calling");

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/tallysheets/",
            //data: '{"exVessel":"'+exVessel+'"}',
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("success.....");

                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#feedback').html(json);

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

//-----------------Function to Open WSO---------------
   this.openwso=function(thisObj) {
    	if($(thisObj).hasClass("nochange")){
    		var clientInfo =  $("#clientInfo option:selected").val();
    		var tallysheetNumber =  $("#tallysheetNumber option:selected").val();
    		wso1.show(clientInfo,tallysheetNumber);
    	}else{
    		tally.saveTallysheet(true);
    	}
    };
   
    this.onClientSelect = function(thisObj){
    	tally.customReferesh();
    	$("#tallysheetNumber").empty();
  	    $("#tallysheetNumber").append("<option value=''>Select / Add New</option>");
    	var id = $("#clientInfo").val();
        tally.getAllTallySheetByClientId(id);
        $("#saveTallysheet").show();
        tally.getcurrentdate();
    
    }
    
    this.onTallySheetSelect = function(thisObj){
    	var tallysheetId=  $("#tallysheetNumber").val();
        tally.getTallysheetDetail(tallysheetId);
    }
    
    this.clearTallysheet=function(){
    	$('#tallysheetForm')[0].reset();
    	 $("#warehouseName").val(warehouseName);
    	 $("#opsName").val(LoggedUserIdVar);
    	 tally.getcurrentdate();
    }
    
    this.getWarehouseName = function(){
    	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/warehouseInfos/",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            async : false,
            success: function (data) {
            	$("#warehouseName").val(data[0].warehouseName);
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

}
