var SETTING = function() {

	var sno;
    this.show = function() {
    	/*alert("hello");*/
    	$("#content-wrapper").empty();
    	
    	 var html = "";
    	 html += '<div class=\"row\">'
         	html += '<div class=\"col-lg-12\">'
         	html += '<div class=\"row\">'
         	html += '<div class=\"col-lg-12\">'
         	html += '<h1>Profile Setting</h1>'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"row table-style\" >'
         	html += '<div class=\"col-lg-12 full-height\">'
         	html += '<form id=\"profileForm\" class=\"full-height\">'
         	html += '<div class=\"main-box datesscroll style-overflow\" >'
         	html += '<header class=\"main-box-header clearfix\">'
         	html += ''
         	html += '</header>'
         	html += '<div class=\"main-box-body clearfix\">'
         	html += '<div class=\"row\">'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>First Name</label>'
         	html += '<input type=\"text\" class=\"form-control\" placeholder=\"First Name\" id=\"fName\">'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>Last Name</label>'
         	html += '<input type=\"text\" class=\"form-control\" placeholder=\"Last Name\" id=\"lName\">'
         	html += '</div>'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"row\">'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>Email-Id</label>'
         	html += '<input type=\"email\" class=\"form-control\" placeholder=\"Email-Id\" id=\"emailId\">'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>Mobile No</label>'
         	html += '<input type=\"number\" class=\"form-control\" id=\"mobileNo\" placeholder=\"Mobile No\">'
         	html += '</div>'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"row\">'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>Gender</label>'
         	html += '<select class="form-control" id="gender">'
         	html += '<option>Select Gender</option>'
         	html += '<option value="male" selected>Male</option>'
   			html += '<option value="female">Female</option>'
         	html += '</select>'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>Admin-Id</label>'
         	html += '<input type=\"text\" class=\"form-control\" id=\"userId\" placeholder=\"Admin-Id\">'
         	html += '</div>'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"row\">'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>Password</label>'
         	html += '<input type=\"password\" class=\"form-control\" id=\"password\" placeholder=\"Password\">'
         	html += '</div>'
         	html += '</div>'
         	html += '<div class=\"col-lg-6\">'
         	html += '<div class=\"form-group\">'
         	html += '<label>Confirm Password</label>'
         	html += '<input type=\"password\"  class=\"form-control\" id=\"confpassword\" placeholder=\"Confirm Password\">'
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
         	html += '<div class=\"btn btn-success\" id=\"sno\" style=\"margin-top: 20px;\" onclick="setting.saveUser();">Save</div>'
         	html += '</div>'
         	html += '<div class=\"col-lg-2\">'
         	html += '<div class=\"btn btn-warning\" id=\"clearUserField\" style=\"margin-top: 20px;\" onclick="">Clear</div>'
         	html += '</div>'
         	html += '<div class=\"col-lg-2\">'
         	html += '<a  href=\"index.html\" class=\"btn btn-danger\" style=\"margin-top: 20px;\">Exit</a>'
         	html += '</div>'
         	html += '<div class=\"col-lg-2\">'
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
    	 setting.getAdmin(LoggedSnoVar);
    }
    
    
    this.getAdmin = function(LoggedSnoVar){
    	LoggedSnoVar = parseInt(LoggedSnoVar);
    	
    	$.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath+ "/users/" +LoggedSnoVar,
            //data: jsonData,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function(data) {
            	
            	$("#fName").val(data.fName);
				$("#lName").val(data.lName);
				$("#userId").val(data.userId);
				$("#emailId").val(data.emailId);
				$("#mobileNo").val(data.mobileNo);
				var gender = data.gender;
				$("#gender").val(gender);
				$("#password").val(data.password);
				sno = data.sno;
				$("#sno").val(data.sno);

            },
            error: function(e) {

                var json = "<h4>Ajax Response</h4><pre>" +
                    e.responseText + "</pre>";
                $('#feedback').html(json);

                console.log("ERROR : ", e);

            }
        });
    }
    
    
 // Start Save New User
	this.saveUser = function() {
		console.log("saveuserInfo() calling");
		/*serialNo = parseInt(serialNo);*/

		var fName = $("#fName").val();
		var lName = $("#lName").val();
		var userId = $("#userId").val();
		var emailId = $("#emailId").val();
		var mobileNo = $("#mobileNo").val();
		
		var gender = $("#gender").val();
		var password = $("#password").val();
		var confpassword = $("#confpassword").val();
		var name = fName + " " + lName

		if (userId == "" || userId == undefined) {
			bootbox.alert("Please Enter admin Id !");
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
		
		var jsonData = '{"fName":"' + fName + '","lName":"' + lName
				+ '","userId":"' + userId + '","emailId":"' + emailId
				+ '","mobileNo":"' + mobileNo + '","password":"' + password
				+ '","gender":"' + gender + '","name" :"' + name + '"}'

			$.ajax({
				type : "PUT",
				contentType : "application/json",
				url : contextPath + "/users/" + sno,
				data : jsonData,
				dataType : 'json',
				cache : false,
				timeout : 600000,
				success : function(data) {
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
	// End Save New User
	
	
	this.getProfilePic = function(LoggedSnoVar){
	/*	alert("Profile Pic...")*/
		$("#content-wrapper").empty();
    	
   	 var html = "";
   	 html += '<div class=\"row\">'
        	
        	html += '<form class="form-horizontal" id="fileUploadForm" method="POST" enctype="multipart/form-data">'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-6\">'
        	html += '<div class=\"form-group\">'
        	html += '<label>Profile Pic</label>'
        	html += '<input type=\"file\" class=\"form-control\" placeholder=\"Profile Pic\" id=\"profilePic\" name="files" onchange="setting.readURL(this);">'
        	html += '<img id="blah" src="#" alt="your image" />'	
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '<div class=\"main-box\" style=\"background: #eeeeee\">'
        	html += '<div class=\"main-box-body clearfix\">'
        	html += '<div class=\"row\">'
        	html += '<div class=\"col-lg-2\">'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<div class=\"btn btn-success\" id=\"sno\" style=\"margin-top: 20px;\" onclick="setting.updateProfilePic();">Save</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<div class=\"btn btn-warning\" id=\"clearUserField\" style=\"margin-top: 20px;\" onclick="">Clear</div>'
        	html += '</div>'
        	html += '<div class=\"col-lg-2\">'
        	html += '<a  href=\"index.html\" class=\"btn btn-danger\" style=\"margin-top: 20px;\">Exit</a>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        	html += '</div>'
        		html += '</form>'
        	html += '</div>'
        	
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
	}
	
	
	this.readURL = function(input){
		if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blah')
                    .attr('src', e.target.result)
                    .width(150)
                    .height(200);
            };

            reader.readAsDataURL(input.files[0]);
        }

	}
	
	this.updateProfilePic = function(LoggedSnoVar){
		var form = $('#fileUploadForm')[0];

	    var data = new FormData(form);

	    //data.append("CustomField", "This is some extra data, testing");

	    //$("#btnSubmit").prop("disabled", true);

	    $.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
	        url: contextPath + "/users/api/upload",
	        data: data,
	        processData: false, //prevent jQuery from automatically transforming the data into a query string
	        contentType: false,
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	        	bootAlert.show("success","Profile-Pic Change Successful");
	        	location.reload(true);
	            //$("#result").text(data);
	            console.log("SUCCESS : ", data);
	           // $("#btnSubmit").prop("disabled", false);

	        },
	        error: function (e) {

	            //$("#result").text(e.responseText);
	            console.log("ERROR : ", e);
	            //$("#btnSubmit").prop("disabled", false);

	        }
	    });
	} 
	

}