var COMMON = function() {
	
	this.getFormattedDate = function(date){
		var twoDigitMonth = ((date.getMonth().length+1) === 1)? (date.getMonth()+1) :  + (date.getMonth()+1);
		var formattedDate = date.getDate() + "-" + twoDigitMonth + "-" + date.getFullYear();
		return formattedDate;
	}
	
	this.getAllclient = function(){
		var response = "";
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: contextPath+"/clientInfos/allClientTitle/",
	        dataType: 'json',
	        cache: false,
	        timeout: 600000,
	        async:false,
	        success: function (data) {
	        response = data;
	        },
	        error: function (e) {
	        	response  = "ERROR";
	        }
	    });
	    return response;
	}
	
	this.refreshPage = function (){
		window.location.reload();
	}
	
}