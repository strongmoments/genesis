var USER = function() {

	var currentAdminIdStatus = "";
	this.show = function() {

		$("#content-wrapper").empty();
		var html = "";

		html += '<div class="row">'

		html += '<div class="col-lg-12">'
		html += '<div class="row">'
		html += '<div class="col-lg-12">'
		html += '<h1>user Entry</h1>'
		html += '</div>'
		html += '<div class="col-lg-12">'
		html += '<button type="button" class="btn btn-primary  mr-10 wsoAndlot" id="addnewWso" style="margin-top: -41px; margin-right:50px;float: right;" data-toggle="modal" data-target="#dynamicModel1" onclick="user.openUserPopu();">Add New Admin</button>'
		html += '<button type="button" class="btn btn-danger  mr-10 " id="exit" style="margin-top: -41px;float: right;" onclick="common.refreshPage();">Exit</button>'

		html += '</div>'
		html += '</div>'
		html += '<div class="row table-style">'
		html += '<div class="col-lg-12 full-height">'
		html += '<form role="form" class="full-height" id="wsoForm">'
		html += '<!-- WSO TABLE START -->'
		html += '<div class="main-box datesscroll style-overflow" id="wsoMainContent">'
		html += '<div class="table-responsive">'
		html += '<table class="table">'
		html += '<thead>'
		html += '<tr>'
		html += '<th></th>'
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
		// '<!-- END OF WSO TABLE -->
		html += '</form>'
		html += '</div>'
		html += '</div>'
		html += '</div>'
		// <!-- popup code -->
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
		// <!--end of popup code -->
		html += '</div>'
		html += '<footer id="footer-bar" class="row">'
		html += '<p id="footer-copyright" class="col-xs-12">Powered by Sandrokottos From StrongMoments Pvt Ltd'
		html += '</p>'
		html += '</footer>'
		$("#content-wrapper").html(html);

		user.loadAllUser();
	}

	this.openUserPopu = function(userId) {
		if (userId == undefined) {
			userId = "";
		}
		$("#dmhId1").empty();
		$("#modelBodyId1").empty();
		$("#dmhId1").append(" user Entry");
		$("#modelBodyId1")
				.append(
						'<div class="row"><div class="col-lg-6"><div class="form-group"><label>First Name</label><input type="text" class="form-control" placeholder="First Name" id="firstName"></div></div><div class="col-lg-6"><div class="form-group"><label>Middle Name</label><input type="text" class="form-control" placeholder="Middle Name" id="middleName"></div></div></div>'
								+ '<div class= "row" > <div class="col-lg-6"><div class="form-group"><label>Last Name</label><input type="text" class="form-control" placeholder="Last Name" id="lastName"></div></div><div class="col-lg-6"><div class="form-group"><label>user Title</label><input type="text" class="form-control" placeholder="user Title" id="userTitle"></div></div></div>'
								+ '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Address 1</label> <textarea rows="2" cols="50" class="form-control" placeholder="Address 1" id="Address1"></textarea></div></div><div class="col-lg-6"><div class="form-group"><label>Address 2</label><textarea class="form-control" rows="2" cols="50" placeholder="Address 2" id="Address2"></textarea></div></div></div>'
								+ '<div class="row"><div class="col-lg-6"><div class="form-group"><label>City</label><input type="text" class="form-control" placeholder="City" id="city"></div></div><div class="col-lg-6"><div class="form-group"><label>State</label><input type="text" class="form-control" placeholder="State" id="state"></div></div></div>'
								+ '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Country</label><input type="text" class="form-control" placeholder="Country" id="country"></div></div><div class="col-lg-6"><div class="form-group"><label>zip Code</label><input type="number" min="5" max="8" class="form-control" placeholder="zip code" id="zipCode"></div></div></div>'
								+ '<div class="row"><div class="col-lg-6"><div class="form-group"><label>Phone No</label><input type="number" class="form-control" placeholder="Phone No" id="phoneNo"></div></div><div class="col-lg-6"><div class="form-group"><label>Mobile No</label><input type="number" class="form-control" placeholder="Phone No" id="mobileNo"></div></div></div>'
								+ '<div><div type="button" class="btn btn-primary  mr-10 wsoAndlot" style="margin-top:30px;" onclick="user.saveUser(\''
								+ userId
								+ '\')">Save user</div>'
								+ '<div type="button" class="btn btn-warning  mr-10" style="margin-top:30px;margin-left:30px;" data-dismiss="modal">Cancel</div></div>');
	}

	// Start Save New User
	this.saveUser = function(sno) {
		console.log("saveuserInfo() calling");
		if (sno == undefined || sno == "") {
			sno = "";
		}

		var fName = $("#fName").val();
		var lName = $("#lName").val();
		var userId = $("#userId").val();
		var emailId = $("#emailId").val();
		var mobileNo = $("#mobileNo").val();
		/*
		 * var gender = data.gender; if(gender == 'male'){
		 * 
		 * }if(gender == 'female'){ }
		 */
		var gender = $("#gender").val();
		var password = $("#password").val();
		var confpassword = $("#confpassword").val();
		var name = fName + " " + lName

		if (userId == "" || userId == undefined) {
			bootbox.alert("Please Enter admin Id !");
			return false;
		}
		
		user.getUserIdStatus(sno, userId);
		if(currentAdminIdStatus != ""){
	    	bootbox.alert(currentAdminIdStatus);
	    	return false;
	    }

		if (password == "" || password == undefined) {
			bootbox.alert("Please Enter password !");
			return false;
		}

		if (password != confpassword) {
			bootbox.alert("Password and confirm password mismarch!");
			return false;
		}
		// var jsonData = {"fName" : fName, "lName" : lName, "userId" : userId,
		// "emailId" : emailId, "mobileNo" : mobileNo, "password" : password,
		// "gender" : gender}
		var jsonData = '{"fName":"' + fName + '","lName":"' + lName
				+ '","userId":"' + userId + '","emailId":"' + emailId
				+ '","mobileNo":"' + mobileNo + '","password":"' + password
				+ '","gender":"' + gender + '","name" :"' + name + '"}'
		var type = "POST";
		if (sno == "") {
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : contextPath + "/users/",
				data : jsonData,
				dataType : 'json',
				cache : false,
				timeout : 600000,
				success : function(data) {

					$("#dynamicModel1").modal("hide");
					user.loadAllUser();
					bootAlert.show("success",
							"user has been added successfully !");
				},
				error : function(e) {

					var json = "<h4>Ajax Response</h4><pre>" + e.responseText
							+ "</pre>";
					$('#feedback').html(json);

					console.log("ERROR : ", e);

				}
			});
		} else {
			$.ajax({
				type : "PUT",
				contentType : "application/json",
				url : contextPath + "/users/" + sno,
				data : jsonData,
				dataType : 'json',
				cache : false,
				timeout : 600000,
				success : function(data) {
					$("#dynamicModel1").modal("hide");
					user.loadAllUser();
					bootAlert.show("success",
							"user has been updated successfully !");
				},
				error : function(e) {

					var json = "<h4>Ajax Response</h4><pre>" + e.responseText
							+ "</pre>";
					$('#feedback').html(json);

					console.log("ERROR : ", e);

				}
			});
		}

	}
	// End Save New User

	// Start load All User
	this.loadAllUser = function() {
		$
				.ajax({
					type : "GET",
					contentType : "application/json",
					url : contextPath + "/users/allUsers",
					dataType : 'json',
					cache : false,
					timeout : 600000,
					success : function(response) {
						$("#wsoMainContent").empty();
						$("#wsoMainContent")
								.append(
										'<div class="table-responsive"><table class="table"><thead><tr style="background-color:#e8e4e469;"><th>Action</th><th>User Name</th><th style="width:300px;">Email Id</th><th>Gender</th><th>Mobile No</th><th>UserId</th></tr></thead><tbody id="wsoTableId"></tbody></table></div>');
						data = response.data;
						$("#wsoTableId").empty();
						var tr = "";
						for (var i = 0; i < data.length; i++) {
							var name = data[i].name;
							var emailId = data[i].emailId;
							// userAddress1 = unescape(userAddress1);
							var gender = data[i].gender;
							var mobileNo = data[i].mobileNo;
							var userId = data[i].userId;
							tr += "<tr>";
							tr += "<td><a href='#' onclick='user.editUserInfo(\""
									+ data[i].sno
									+ "\")'  style='font-size:20px;' data-toggle='modal' data-target='#dynamicModel1' ><i class='fa fa-pencil'></i></a>&nbsp;&nbsp;</td>"
									+ "<td>"
									+ name
									+ "</td> <td> <p>"
									+ emailId
									+ "</p> </td> <td> "
									+ gender
									+ "</td> <td> "
									+ mobileNo
									+ " </td> <td> "
									+ userId + " </td> ";
							tr += "</tr>";

						}
						$("#wsoTableId").append(tr);

					},
					error : function(e) {
						var json = "<h4>Ajax Response</h4><pre>"
								+ e.responseText + "</pre>";
						$('#feedback').html(json);
						console.log("ERROR : ", e);
					}
				});

	}

	// Start edit User info
	this.editUserInfo = function(sno) {
		user.openUserPopu(sno);
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : contextPath + "/users/allUsers/" + sno,
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(response) {
				data = response.data;
				$("#fName").val(data.fName);
				$("#lName").val(data.lName);
				$("#userId").val(data.userId);
				$("#emailId").val(data.emailId);
				$("#mobileNo").val(data.mobileNo);
				var gender = data.gender;
				$("#gender").val(gender);
				$("#password").val(data.password);
			},
			error : function(e) {
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText
						+ "</pre>";
				$('#feedback').html(json);
				console.log("ERROR : ", e);
			}
		});
	}
	// End edit User info

	// Save and Update Admin
	this.openUserPopu = function(sno) {
		if (sno == undefined) {
			sno = "";
		}
		$("#dmhId1").empty();
		$("#modelBodyId1").empty();
		$("#dmhId1").append(" Admin Entry");
		$("#modelBodyId1")
				.append(
						'<div class="row">'
								+ '<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>First Name</label>'
								+ '<input type="text" class="form-control" id="fName">'
								+ '</div>'
								+ '</div>'
								+ '<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>Last Name</label>'
								+ '<input type="text" class="form-control" id="lName">'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div class="row">'
								+ '<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>Email-Id</label>'
								+ '<input type="email" class="form-control" id="emailId" >'
								+ '</div>'
								+ '</div>'
								+

								'<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>Mobile No</label>'
								+ '<input type="number" class="form-control" id="mobileNo">'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div class="row">'
								+ '<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>Gender</label>'
								+ '<select class="form-control" id="gender">'
								+ '<option>Select Gender</option>'
								+ '<option value="male" selected>Male</option>'
								+ '<option value="female">Female</option>'
								+ '</select>'
								+ '</div>'
								+ '</div>'
								+ '<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>Admin Id</label>'
								+ '<input type="text" class="form-control" id="userId" >'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div class="row">'
								+ '<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>Password</label>'
								+ '<input type="password" class="form-control" id="password" >'
								+ '</div>'
								+ '</div>'
								+

								'<div class="col-lg-6">'
								+ '<div class="form-group">'
								+ '<label>Confirm Password</label>'
								+ '<input type="password" class="form-control" id="confpassword">'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '<div class="row">'
								+ '<div class="col-lg-2"></div>'
								+ '<div class="col-lg-2">'
								+ '<div class="btn btn-success wsoAndlot" onclick="user.saveUser(\''
								+ sno
								+ '\')">Save</div>'
								+ '</div>'
								+ '<div class="col-lg-2">'
								+ '<div class="btn btn-warning" id="clearLotField" data-dismiss="modal" >Cancel</div>'
								+ '</div></div>');
	}
	// End Save and Update Admin
	
	//Start getUserIdStatus
	  
	  this.getUserIdStatus = function(sno, userId){
		  currentAdminIdStatus = "";
		  if(sno == undefined || sno == null){
			  sno = "";
		  }
		  var jsonData = {'userId':userId, 'sno':sno}
		  $.ajax({
		        type: "GET",
		        contentType: "application/json",
		        url: contextPath+ "/users/getUserId",
		        data: jsonData,
		        dataType: 'json',
		        cache: false,
		        timeout: 600000,
		        async:false,
		        success: function (response) {
		           var data = response.data;
		           if(data == "userId is available"){
		        	   currentAdminIdStatus = "";
		           }else{
		        	   currentAdminIdStatus = "Sorry, this AdminId is already occupied : "+userId;
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
	  //End getUserIdStatus
	  

}
