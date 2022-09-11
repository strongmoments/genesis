var CLIENT = function() {

    this.show = function() {

        $("#content-wrapper").empty();
        var html = "";

        html += '<div class="row">'

        html += '<div class="col-lg-12">'
        html += '<div class="row">'
        html += '<div class="col-lg-12">'
        html += '<h1>Client Entry</h1>'
        html += '</div>'
        html += '<div class="col-lg-12" style=" margin-bootom:10px;">'
        html +=	'<div class="col-lg-4"><input type="text" class="form-control" placeholder="Search Using Client Name..." id="searchClientTitle" onkeyup="client.clientSearch();" style="width:80%;height:35px;">'
		html += '<i class="fa fa-search search-icon" style="margin-top: -25px;float: right;margin-right:70px;"></i></div>'
        
        html += '<div class="col-lg-8"><button type="button" class="btn btn-danger  mr-10 " id="exit" style="float: right;" onclick="common.refreshPage();">Exit</button>'
        html += '<button type="button" class="btn btn-primary  mr-10 wsoAndlot" id="addnewWso" style=" margin-right:10px;float: right;" data-toggle="modal" data-target="#dynamicModel1" onclick="client.openClientPopu();">Add New Client</button>'
        html += '</div></div>'
        html += '</div>'
        html += '<div class="row table-style"style=" margin-top:10px;">'
        html += '<div class="col-lg-12 full-height">'
        html += '<form role="form" class="full-height" id="wsoForm">'
        html += '<!-- WSO TABLE START -->'
        html += '<div class="main-box datesscroll style-overflow" id="wsoMainContent">'
        html += '<div class="table-responsive">'
        html += '<table class="table">'
        html += '<thead>'
        html += '<tr>'
        html += '<th>Client Title</th>'
        html += '<th>Address</th>'
        html += '<th>City</th>'
        html += '<th>State</th>'
        html += '<th>Country</th>'

        html += '</tr>'
        html += '</thead>'
        html += '<tbody id="wsoTableId">'

        html += '</tbody>'
        html += '</table>'
        html += '</div>'
        html += '</div>'
        //'<!-- END OF WSO TABLE  -->




        html += '</form>'
        html += '</div>'
        html += '</div>'
        html += '</div>'
        //<!-- popup code -->
        html += '<div class="modal fade" id="dynamicModel" role="dialog" tabindex="-1">'
        html += '<div class="modal-dialog">'
        html += '<div class="modal-content">'
        html += '<div class="modal-header" style="background: #7FC8BA; color: #fff;">'
        html += '<button type="button" class="close" data-dismiss="modal">X</button>'
        html += '<h3 id="dmhId"></h3>'
        html += '</div>'
        html += '<div class="modal-body" id="modelBodyId">'
        html += '</div>'
        html += '</div>'
        html += '</div>'
        html += '</div>'

        html += '<div class="modal fade" id="dynamicModel1" role="dialog" tabindex="-1">'
        html += '<div class="modal-dialog">'
        html += '<div class="modal-content">'
        html += '<div class="modal-header" style="background: #7FC8BA; color: #fff;">'
        html += '<h3 id="dmhId1"></h3>'
        html += '</div>'
        html += '<div class="modal-body" id="modelBodyId1">'

        html += '</div>'
        html += '</div>'
        html += '</div>'
        html += '</div>'
        //<!--end of popup code  -->
        html += '</div>'
        html += '<footer id="footer-bar" class="row">'
        html += '<p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd'
        html += '</p>'
        html += '</footer>'
        $("#content-wrapper").html(html);
        
        client.loadAllClient();
    }
    
    

    this.openClientPopu = function(clientId) {
        if (clientId == undefined) {
            clientId = "";
        }
        $("#dmhId1").empty();
        $("#modelBodyId1").empty();
        $("#dmhId1").append(" Client Entry");
        $("#modelBodyId1").append('<div class="row"><div class="col-lg-6"><div class="form-group"><label>First Name</label><input type="text" class="form-control" placeholder="First Name" id="firstName"></div></div><div class="col-lg-6"><div class="form-group"><label>Middle Name</label><input type="text" class="form-control" placeholder="Middle Name" id="middleName"></div></div></div>' +
            '<div class= "row" > <div class="col-lg-6"><div class="form-group"><label>Last Name</label><input type="text" class="form-control" placeholder="Last Name" id="lastName"></div></div><div class="col-lg-6"><div class="form-group"><label>Client Title</label><input type="text" class="form-control" placeholder="Client Title" id="clientTitle"></div></div></div>' +
            '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Address 1</label> <textarea rows="2" cols="50" class="form-control" placeholder="Address 1" id="Address1"></textarea></div></div><div class="col-lg-6"><div class="form-group"><label>Address 2</label><textarea class="form-control" rows="2" cols="50" placeholder="Address 2" id="Address2"></textarea></div></div></div>' +
            '<div class="row"><div class="col-lg-6"><div class="form-group"><label>City</label><input type="text" class="form-control" placeholder="City" id="city"></div></div><div class="col-lg-6"><div class="form-group"><label>State</label><input type="text" class="form-control" placeholder="State" id="state"></div></div></div>' +
            '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Country</label><input type="text" class="form-control" placeholder="Country" id="country"></div></div><div class="col-lg-6"><div class="form-group"><label>zip Code</label><input type="number" min="5" max="8" class="form-control" placeholder="zip code" id="zipCode"></div></div></div>' +
            '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Phone No</label><input type="number" class="form-control" placeholder="Phone No" id="phoneNo"></div></div><div class="col-lg-6"><div class="form-group"><label>Mobile No</label><input type="number" class="form-control" placeholder="Phone No" id="mobileNo"></div></div></div>' +
            '<div><div type="button" class="btn btn-primary  mr-10 wsoAndlot" style="margin-top:30px;" onclick="client.saveClientInfoOnly(\'' + clientId + '\')">Save Client</div>' +
            '<div type="button" class="btn btn-warning  mr-10" style="margin-top:30px;margin-left:30px;" data-dismiss="modal">Cancel</div></div>');
    }

    this.saveClientInfoOnly = function(clientInfoId) {
        console.log("saveClientInfo() calling");
        if (clientInfoId == undefined || clientInfoId == "") {
            clientInfoId = "";
        }
        var contactPersonFirstName = $("#firstName").val();
        var contactPersonMiddleName = $("#middleName").val();
        var contactPersonLastName = $("#lastName").val();
        var clientTitle = $("#clientTitle").val();
        var clientAddress1 = $("#Address1").val();
        clientAddress1  = escape(clientAddress1);
        var clientAddress2 = $("#Address2").val();
        clientAddress2  = escape(clientAddress2);
        var clientCity = $("#city").val();
        var clientState = $("#state").val();
        var clientCountry = $("#country").val();
        var clientZip = $("#zipCode").val();
        var contactPersonPhoneNumber = $("#phoneNo").val();
        var contactPersonMobileNumbere = $("#mobileNo").val();
        if (clientTitle == "" || clientTitle == undefined) {
        	 bootAlert.show("error", "Please Enter Client Title !");
            return false;
        }
        var jsonData = '{"contactPersonFirstName":"' + contactPersonFirstName + '","contactPersonMiddleName":"' + contactPersonMiddleName + '","contactPersonLastName":"' + contactPersonLastName + '","clientAddress1":"' + clientAddress1 + '","clientAddress2":"' + clientAddress2 + '","clientCity":"' + clientCity + '","clientState":"' + clientState + '","clientCountry":"' + clientCountry + '","clientZip":"' + clientZip + '","contactPersonPhoneNumber":"' + contactPersonPhoneNumber + '","contactPersonMobileNumbere":"' + contactPersonMobileNumbere + '","clientTitle":"' + clientTitle + '"}'
        var type = "POST";
        if (clientInfoId == "") {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: contextPath+ "/clientInfos/saveclientListOnly/" + clientInfoId,
                data: jsonData,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function(data) {
                    $("#dynamicModel1").modal("hide");
                    client.loadAllClient();
                    bootAlert.show("success", "Client has been added successfully !");
                },
                error: function(e) {

                    var json = "<h4>Ajax Response</h4><pre>" +
                        e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);

                }
            });
        } else {
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: contextPath+ "/clientInfos/saveclientListOnly/" + clientInfoId,
                data: jsonData,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function(data) {
                    $("#dynamicModel1").modal("hide");
                    client.loadAllClient();
                    bootAlert.show("success", "Client has been updated successfully !");
                },
                error: function(e) {

                    var json = "<h4>Ajax Response</h4><pre>" +
                        e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);

                }
            });
        }

    }

    this.loadAllClient = function() {


        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/clientInfos/",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function(data) {
                $("#wsoMainContent").empty();
                $("#wsoMainContent").append('<div class="table-responsive"><table class="table"><thead><tr style="background-color:#e8e4e469;"><th></th><th>Client Name</th><th style="width:300px;">Address</th><th>City</th><th>State</th><th>Country</th></tr></thead><tbody id="wsoTableId"></tbody></table></div>');
                $("#wsoTableId").empty();
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
                $("#wsoTableId").append(tr);

            },
            error: function(e) {
                var json = "<h4>Ajax Response</h4><pre>" +
                    e.responseText + "</pre>";
                $('#feedback').html(json);
                console.log("ERROR : ", e);
            }
        });


    }

    // open client storage popup
    this.openClientStoragePopu = function(clientInfoId, clientStorageId,clientTitle){
    	if(clientInfoId == undefined){
    		clientInfoId = "";
    	}
    	if(clientStorageId == undefined){
    		clientStorageId = "";
    	}

        $("#dmhId").empty();
        $("#modelBodyId").empty();
        $("#dmhId").append("Client Storage Entry for "+clientTitle);
        $("#modelBodyId").append('<div class="row">'+
            '<div class="col-lg-12">'+
            '<div class="form-group">'+
            '<label>Storage Type</label>'+
            '<select class="form-control" id="storageTypeName">'+
            '<option>Select Storage Types</option>'+
            '<option value="1">Common Dry</option>'+
            '<option value="2">Common Chiller</option>'+
            '<option value="3">Common Freezer</option>'+
            '<option value="4">Common Aircon</option>'+
            '<option value="5">Common Handling</option>'+
            '<option value="6">Lease Dry</option>'+
            '<option value="7">Lease Chiller</option>'+
            ' <option value="8">Lease Freezer</option>'+
            '<option value="9">Lease Aircon</option>'+
            '<option value="10">Lease Handling</option>'+
            '<option value="11">Lease Freezer/Chiller</option>'+
            '</select>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '<div class="row">'+
            '<div class="col-lg-5">'+
            '<div class="form-group">'+
            '<label>Start Date</label>'+
            '<input type="date" class="form-control" id="storageStartDate">'+
            '</div>'+
            '</div>'+
            '<div class="col-lg-2"></div>'+
            '<div class="col-lg-5">'+
            '<div class="form-group">'+
            '<label>End Date</label>'+
            '<input type="date" class="form-control" id="storageEndDate">'+
            '</div>'+
            '</div>'+
            '</div>'+
            /*'<div class="row">'+
            '<div class="col-lg-5">'+
            '<div class="form-group">'+
            '<label>Last Bill Date</label>'+
            '<input type="date" class="form-control" id="lastBillDate" readonly>'+
            '</div>'+
            '</div>'+
            '<div class="col-lg-2"></div>'+
            '<div class="col-lg-5">'+
            '<div class="form-group">'+
            '<label>Next Bill Date</label>'+
            '<input type="date" class="form-control" id="nextBillDate">'+
            '</div>'+
            '</div>'+
            '</div>'+*/
            '<div class="row">'+
            '<div class="col-lg-5">'+
            '<div class="form-group">'+
            '<label>Monthly Charges</label>'+
            '<input type="number" min="0" step="0.00" class="form-control" id="monthlyRate" placeholder="Monthly Rate" >'+
            '</div>'+
            '</div>'+
            '<div class="col-lg-2"></div>'+
            '<div class="col-lg-5">'+
            '<div class="form-group">'+
            '<label>Handling Charges</label>'+
            '<input type="number" min="0" step="0.00" class="form-control" id="handlingCharges" placeholder="handlingCharges">'+
            '</div>'+
            '</div>'+
            '</div>'+
            '<div class="row">'+
            '<div class="col-lg-2"></div>'+
            '<div class="col-lg-2">'+
            '<div class="btn btn-success wsoAndlot" onclick="client.saveClientStorage(\''+clientInfoId+'\', \''+clientStorageId+'\',\''+clientTitle+'\')">Save</div>'+
            '</div>'+
            '<div class="col-lg-2">'+
            '<div class="btn btn-warning" id="clearLotField" data-dismiss="modal" >Cancel</div>'+
            '</div>'+

            '</div>');
        var date = new Date();

		var day = date.getDate();
		var month = date.getMonth() + 1;
		var year = date.getFullYear();

		if (month < 10) month = "0" + month;
		if (day < 10) day = "0" + day;

		var today = year + "-" + month + "-" + day;


		//document.getElementById('lastBillDate').value = today;

        

    }
    // end of client storage popup
    
   //save client storage detail type
    this.saveClientStorage = function (clientInfoId, clientStorageId, clientTitle) {
        var storageTypeName = $("#storageTypeName").val();
        var storageStartDate = $("#storageStartDate").val();
        var storageEndDate = $("#storageEndDate").val();
        var nextBillDate = storageStartDate;
        //var lastBillDate = client.getYesterdaysDate();
        var monthlyRate = $("#monthlyRate").val();
        
        var handlingCharges = $("#handlingCharges").val();
        
       // var nextBillDate = $("#nextBillDate").val();
        
        
        var lastBillDate =nextBillDate;
        if(clientStorageId == "" || clientStorageId == undefined) {
        	var url  = "/clientInfos/saveclientList/" + clientInfoId;
        	var jsonData = {"monthlyRate":monthlyRate,"handlingCharges":handlingCharges,"lastBillDate":lastBillDate,"storageStartDate":storageStartDate,"storageEndDate":storageEndDate,"nextBillDate":nextBillDate,"storageTypeName":storageTypeName}
            
        	var requstType = "POST";
        }else{
        	var url  = "/clientInfos/saveclientList/" + clientStorageId;
        	var jsonData = {"monthlyRate":monthlyRate,"handlingCharges":handlingCharges,"storageStartDate":storageStartDate,"storageEndDate":storageEndDate,"storageTypeName":storageTypeName}
        	var requstType = "PUT";
        }
        
        $.ajax({
            type: requstType,
            url: contextPath+ url,
            data: jsonData,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	$("#dynamicModel").modal("hide");
            	client.loadClientStoragesss(clientInfoId,clientTitle);
            	bootAlert.show("success","Storage type has been added successfully.")
            },
            error: function (e) {

                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);

                console.log("ERROR : ", e);

            }
        });
    }
   //end of client storage  popup detail

 // load client storage detail
    this.loadClientStoragesss = function(clientInfoId, clientTitle) {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/clientStorageInfos/clientId/" + clientInfoId,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function(data) {
                $(".viewLotsDetailscls").hide();
                $("#wsoLot" + clientInfoId).show();
                $("#wsoLoTableDteail" + clientInfoId).empty();
                var tr = "";
                for (var i = 0; i < data.length; i++) {
                    tr += client.generateClientStorageTr(data[i], clientInfoId, clientTitle);
                }
                $("#wsoLoTableDteail" + clientInfoId).append(tr);
            },
            error: function(e) {
                var json = "<h4>Ajax Response</h4><pre>" +
                    e.responseText + "</pre>";
                $('#feedback').html(json);
                console.log("ERROR : ", e);
            }
        });
    }
    //end of load client storage detail
    
    // edit client storage 
    this.editClientStorageDetail = function (clientInfoId, clientStorageId,clientTitle){
        client.openClientStoragePopu(clientInfoId, clientStorageId,clientTitle);
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/clientStorageInfos/"+clientStorageId,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
            	var storageType = data.storageType.storageTypeId;
                var storageStartDate = data.storageStartDate;
                var storageEndDate = data.storageEndDate;
               /* var lastBillDate = data.lastBillDate;
                var nextBillDate = data.nextBillDate;*/
                var monthlyRate = data.monthlyRate;
                var handlingCharges = data.handlingCharges;
                $("#storageTypeName").val(storageType);
                $("#storageStartDate").val(storageStartDate);
                $("#storageEndDate").val(storageEndDate);
               /* $("#lastBillDate").val(lastBillDate);
                $("#nextBillDate").val(nextBillDate);*/
                $("#monthlyRate").val(monthlyRate.toFixed(2));
                $("#handlingCharges").val(handlingCharges.toFixed(2));
            },
            error: function (e) {
                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);
                console.log("ERROR : ", e);
            }
        });

    }
    // end of edit client storage
     
    // generate tr
   this.generateClientStorageTr =  function (ClientStorageData,clientInfoId,clientTitle){
        var tr ="";
        var storageType = ClientStorageData.storageType.storageTypeName;
        var storageStartDate = ClientStorageData.storageStartDate;
        
        storageStartDate = common.getFormattedDate(new Date(storageStartDate));
        var storageEndDate = ClientStorageData.storageEndDate;
        storageEndDate = common.getFormattedDate(new Date(storageEndDate));
        var monthlyRate = ClientStorageData.monthlyRate.toFixed(2);
        var handlingCharges = ClientStorageData.handlingCharges.toFixed(2);
        var lastBillDate = ClientStorageData.lastBillDate;
        lastBillDate = common.getFormattedDate(new Date(lastBillDate));
        var nextBillDate = ClientStorageData.nextBillDate;
        nextBillDate = common.getFormattedDate(new Date(nextBillDate));
        tr +="<tr style='background-color:#f9f9f969;'>"
        tr += "<td><a href='#' style='font-size:20px;' onclick='client.editClientStorageDetail(\""+clientInfoId+"\", \""+ClientStorageData.clientStorageId+"\", \""+clientTitle+"\")' data-toggle='modal' data-target='#dynamicModel'>" +
            "<i class='fa fa-pencil'></i>" +
            "</a>&nbsp;&nbsp;  <a href='#' style='font-size:20px; color:#ff4444;' onclick='client.deleteClientStorageDetail(\""+clientInfoId+"\", \""+ClientStorageData.clientStorageId+"\", \""+clientTitle+"\")'>"+
            "<i class='fa fa-trash-o'></i>"+
            "</a> </td>"
        tr +="<td>"+storageType+"</td>"
        tr +="<td>"+storageStartDate+"</td>"
        tr +="<td>"+storageEndDate+"</td>"
        tr +="<td>"+monthlyRate+"</td>"
        tr +="<td>"+handlingCharges+"</td>"
        tr +="<td>"+lastBillDate+"</td>"
        tr +="<td>"+nextBillDate+"</td>"
        tr +="</tr>"
        return tr;
    }
    // end of generate tr
    
    
    // edit client info 
    this.editClientInfo = function(clientInfoId) {
        client.openClientPopu(clientInfoId);
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/clientInfos/" + clientInfoId,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function(data) {
                $("#firstName").val(data.contactPersonFirstName);
                $("#middleName").val(data.contactPersonMiddleName);
                $("#lastName").val(data.contactPersonLastName);
                $("#clientTitle").val(data.clientTitle);
                $("#Address1").val(unescape(data.clientAddress1));
                $("#Address2").val(unescape(data.clientAddress2));
                $("#city").val(data.clientCity);
                $("#state").val(data.clientState);
                $("#country").val(data.clientCountry);
                $("#zipCode").val(data.clientZip);
                $("#phoneNo").val(data.contactPersonPhoneNumber);
                $("#mobileNo").val(data.contactPersonMobileNumbere);

            },
            error: function(e) {
                var json = "<h4>Ajax Response</h4><pre>" +
                    e.responseText + "</pre>";
                $('#feedback').html(json);
                console.log("ERROR : ", e);
            }
        });
    }
    // end of edit client info

    // start delete client storage
     this.deleteClientStorageDetail = function(clientInfoId, clientStorageId, clientTitle){
        clientStorage = parseInt(clientStorageId)
        clientInfo = parseInt(clientInfoId)
        
        bootbox.confirm("Are you sure you want to delete Client Storage?", function(result){
		    	 if(result == true){
		    		 $.ajax({
		                 type: "DELETE",
		                 contentType: "application/json",
		                 url: contextPath+ "/clientStorageInfos/"+clientStorage,
		                 cache: false,
		                 timeout: 600000,
		                 success: function (resultMsg) {
		                     bootAlert.show("success","ClientStorage has been deleted successfully ")
		                     client.loadClientStoragesss(clientInfo,clientTitle);
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
    //end delete client storage
     
	 //Start client Search
	  this.clientSearch = function(){
		  var searchClientTitle = $("#searchClientTitle").val();
		  //alert(searchClientTitle)
		  if(searchClientTitle == ''){
			  client.loadAllClient();
			  return false;
		  }
		  
		    $.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+ "/clientInfos/searchClientTitle/" + searchClientTitle,
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        success: function (data) {
		        	$("#wsoMainContent").empty();
	                $("#wsoMainContent").append('<div class="table-responsive"><table class="table"><thead><tr style="background-color:#e8e4e469;"><th></th><th>Client Name</th><th style="width:300px;">Address</th><th>City</th><th>State</th><th>Country</th></tr></thead><tbody id="wsoTableId"></tbody></table></div>');
	                $("#wsoTableId").empty();
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
		            $("#wsoTableId").append(tr);

		        },
		        error: function (e) {
		            var json = "<h4>Ajax Response</h4><pre>"
		                + e.responseText + "</pre>";
		            $('#feedback').html(json);
		            console.log("ERROR : ", e);
		        }
		    });

		}
	  //End Client Search
	  
	   this.getYesterdaysDate = function() {
          var date = new Date();
          date.setDate(date.getDate()-1);
          return date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();
      }

}
