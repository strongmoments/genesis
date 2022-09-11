
var ALERT = function() {
	
	this.show = function(type,msg) {

		var dynclass  = "";
		var text = "Success";
		if(type=="success"){
			dynclass = "success";
		}else{
			text= "Error";
			dynclass = "danger"
		}
		$("#alertPoput").empty("");
		$("#alertPoput").append(
		'<div class="alert alert-'+dynclass+'  fade in" style="top: 5%; position: fixed; left: 10%; left: 62px; margin-left: 40%;z-index: 999;">'+
		'<button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>'+
		'<i class="fa fa-check-circle fa-fw fa-lg"></i>'+
		'<strong>'+text+' !</strong> '+msg+'&nbsp;&nbsp;</div>');
		$("#alertPoput").show();
		$("#alertPoput").slideUp(6000);
	}
	
}