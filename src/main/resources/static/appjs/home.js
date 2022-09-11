var HOME = function() {

	// Start Load Tallysheet by client id
	this.loadClientTallySheets = function(clientId) {
		/* alert(clientId); */
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : contextPath + "/tallysheets/getAllTallyByClientId/"
					+ clientId,
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(data) {
				console.log(data)
				$(".viewTallysheetDetailscls").hide();
				$("#tallysheet" + clientId).show();
				$("#tallyTableDetail" + clientId).empty();
				// $(thisobj).addClass("hideLot");
				// $(thisobj).removeClass("loadLot");
				$("#tallyTableDteail" + clientId).empty();
				var tr = "";
				for (var i = 0; i < data.length; i++) {
					tr += home.generateTallyTr(data[i], clientId);
				}

				$("#tallyTableDetail" + clientId).append(tr);

			},
			error : function(e) {
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText
						+ "</pre>";
				$('#feedback').html(json);
				console.log("ERROR : ", e);
			}
		});
	}
	// End Load Tallysheet by client id

	// Start generate tallytr
	this.generateTallyTr = function(tallyData, clientId) {
		console.log(tallyData)
		var tr = "";
		var tallysheetNumber = tallyData.tallysheetNumber;
		var storageDate = tallyData.storageDate;
		storageDate = common.getFormattedDate(new Date(storageDate));
		var exVessel = tallyData.exVessel;
		var measurement = tallyData.measurement;

		tr += "<tr style='background-color:#f9f9f969;'>"
		tr += "<td><a href='#' style='font-size:20px;' onclick='home.loadWso("
				+ tallyData.tallysheetId
				+ ")' ><i class='fa fa-search-plus'></i></a></td>"
		tr += "<td>" + tallysheetNumber + "</td>"
		tr += "<td>" + storageDate + "</td>"
		tr += "<td>" + exVessel + "</td>"
		tr += "<td>" + measurement + "</td>"
		tr += "</tr>"
		tr += "<tr id='wso"
				+ tallyData.tallysheetId
				+ "' style='display:none;' class='viewWsoDetailscls'><td colspan='11'> ";
		tr += '<div style="background:##b1b1b112;width:95%;margin: 0 auto;">'
		tr += '<div class="table-responsive"><table class="table" ><thead><tr style="background-color:#e8e4e469;"><th>Action</th><th>WSO No</th><th>WSO Wt</th><th>Cargo</th><th>Stroage Class</th><th>No Of Pallets</th></tr></thead><tbody id="wsoTDetails'
				+ tallyData.tallysheetId + '"></tbody></table></div></div>';
		tr += " </td> </tr>"

		return tr;
	}
	// End generate tallytr

	// Start Load wso
	this.loadWso = function(tallysheetId) {
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : contextPath + "/wsoInfos/getAllWsoByTallysheetId/"
					+ tallysheetId,
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(data) {
				$(".viewWsoDetailscls").hide();
				$("#wso" + tallysheetId).show();
				// $("#wsoTableDetail" + tallysheetId).empty();
				// $(thisobj).addClass("hideLot");
				// $(thisobj).removeClass("loadLot");
				$("#wsoTDetails" + tallysheetId).empty();
				var tr = "";
				for (var i = 0; i < data.length; i++) {
					console.log(data[i].wsoInfoList)
					tr += home.generateWsoTr(data[i], tallysheetId);
				}

				$("#wsoTDetails" + tallysheetId).append(tr);

			},
			error : function(e) {
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText
						+ "</pre>";
				$('#feedback').html(json);
				console.log("ERROR : ", e);
			}
		});
	}
	// End Load wso

	// Start generate wsotr
	this.generateWsoTr = function(wsoData, tallysheetId) {
		console.log(wsoData)
		var tr = "";
		var wsoNo = wsoData.wsoNo;
		var totalWsoWeight = wsoData.totalWsoWeight;
		var cargo = wsoData.cargo;
		var storageClass = wsoData.storageClass.storageTypeName;
		var totalNoOfPallets = wsoData.totalNoOfPallets;

		tr += "<tr style='background-color:#f9f9f969;'>"
		tr += "<td><a href='#' style='font-size:20px;' onclick='home.loadLot("
				+ wsoData.wsoId
				+ ")' ><i class='fa fa-search-plus'></i></a></td>"
		tr += "<td>" + wsoNo + "</td>"
		tr += "<td>" + totalWsoWeight + "</td>"
		tr += "<td>" + cargo + "</td>"
		tr += "<td>" + storageClass + "</td>"
		tr += "<td>" + totalNoOfPallets + "</td>"
		tr += "</tr>"
		tr += "<tr id='lot"
				+ wsoData.wsoId
				+ "' style='display:none;' class='viewLotDetailscls'><td colspan='11'> ";
		tr += '<div style="background:##b1b1b112;width:95%;margin: 0 auto;">'
		tr += '<div class="table-responsive"><table class="table" ><thead><tr style="background-color:#e8e4e469;"><th>Lot No</th><th>Description</th><th>Room No</th><th>Lot Quantity</th><th>Production Date</th></tr></thead><tbody id="lotTDetails'
				+ wsoData.wsoId + '"></tbody></table></div></div>';
		tr += " </td> </tr>"

		return tr;
	}
	// End generate Wsotr

	// Start Load lot
	this.loadLot = function(wsoId) {
		//alert("hemm")
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : contextPath + "/lotInfos/getAllLotByWsoId/" + wsoId,
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(data) {
				$(".viewlotDetailscls").hide();
				$("#lot" + wsoId).show();
				// $("#wsoTableDetail" + tallysheetId).empty();
				// $(thisobj).addClass("hideLot");
				// $(thisobj).removeClass("loadLot");
				$("#lotTDetails" + wsoId).empty();
				var tr = "";
				for (var i = 0; i < data.length; i++) {
					console.log(data[i].wsoInfoList)
					tr += home.generateLotTr(data[i], wsoId);
				}

				$("#lotTDetails" + wsoId).append(tr);

			},
			error : function(e) {
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText
						+ "</pre>";
				$('#feedback').html(json);
				console.log("ERROR : ", e);
			}
		});
	}
	// End Load lot

	// Start lot tr
	this.generateLotTr = function(lotData, wsoId, wsoNo) {

		var tr = "";
		var lotNo = lotData.lotNo;
		var lotQuantity = lotData.lotQuantity;
		var unitWeightInKg = lotData.unitWeightInKg;
		var unitGrossWeightInKg = lotData.unitGrossWeightInKg;
		var unitNetWeightInKg = lotData.unitNetWeightInKg;
		var currentQuantity = lotData.currentQuantity;
		var expiryDate = lotData.expiryDate;
		expiryDate = common.getFormattedDate(new Date(expiryDate));
		var productionDate = lotData.productionDate;
		productionDate = common.getFormattedDate(new Date(productionDate));
		var description = lotData.packages;
		var roomNo = lotData.roomNo;

		tr += "<tr style='background-color:#f9f9f969;'>"
		tr += "<td>" + lotNo + "</td>"
		tr += "<td>" + description + "</td>"
		tr += "<td>" + roomNo + "</td>"
		tr += "<td>" + lotQuantity + "</td>"
		tr += "<td>" + productionDate + "</td>"
		/*
		 * tr +="<td>"+unitNetWeightInKg+"</td>" tr +="<td>"+unitGrossWeightInKg+"</td>"
		 * 
		 * tr +="<td>"+expiryDate+"</td>"
		 */

		tr += "</tr>"
		return tr;
	}

	// End lot tr

	// Start client Search
	this.clientSearch = function() {
		var searchClientTitle = $("#searchClientTitle").val();
		//alert(searchClientTitle)
		if (searchClientTitle == '') {
			loadAllClient();
			return false;
		}

		$
				.ajax({
					type : "GET",
					contentType : "application/json",
					url : contextPath + "/clientInfos/searchClientTitle/"
							+ searchClientTitle,
					dataType : 'json',
					cache : false,
					timeout : 600000,
					success : function(data) {
						$("#clientDetails").empty();
						var tr = "";
						for (var i = 0; i < data.length; i++) {

							var clientTitle = data[i].clientTitle;
							var clientAddress1 = data[i].clientAddress1;
							// clientAddress1 = escape(clientAddress1);
							clientAddress1 = unescape(clientAddress1);
							var clientCity = data[i].clientCity;
							var clientState = data[i].clientState;
							var clientCountry = data[i].clientCountry;
							var clientMobileNo = data[i].contactPersonMobileNumbere;
							tr += "<tr>";
							tr += "<td>"
									+ '<a href="#" style="font-size:20px;" onclick="home.loadClientTallySheets(\''
									+ data[i].clientInfoId
									+ '\')" ><i class="fa fa-search-plus"></i></a>'
									+ "</td>" +

									"<td style='width: 200px;'>" + clientTitle
									+ "</td> <td style='width: 300px;'> "
									+ clientAddress1
									+ " </td> <td style='width: 200px;'> "
									+ clientCity
									+ "</td> <td style='width: 200px;'> "
									+ clientState
									+ " </td> <td style='width: 200px;'> "
									+ clientCountry
									+ " </td> <td style='width: 200px;'> "
									+ clientMobileNo + " </td> ";
							tr += "</tr>";
							tr += "<tr id='tallysheet"
									+ data[i].clientInfoId
									+ "' style='display:none;' class='viewTallysheetDetailscls'><td colspan='11'> ";
							tr += '<div style="background:##b1b1b112;width:95%;margin: 0 auto;">'
							tr += '<div class="table-responsive"><table class="table" ><thead><tr style="background-color: #b1b1b112;"><th>Action</th> <th>Tallysheet No</th> <th>Storage Date</th>  <th> ExVessel</th><th>Measurement</th></tr></thead><tbody id="tallyTableDetail'
									+ data[i].clientInfoId
									+ '"></tbody></table></div></div>';
							tr += " </td> </tr>"
						}
						$("#clientDetails").append(tr);

					},
					error : function(e) {
						var json = "<h4>Ajax Response</h4><pre>"
								+ e.responseText + "</pre>";
						$('#feedback').html(json);
						console.log("ERROR : ", e);
					}
				});

	}
	// End Client Search
}