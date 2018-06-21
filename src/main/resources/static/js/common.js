
//[ROLE_ADMIN, ROLE_REGULAR_USER]
function checkRoleExist(listRole, role) {
	var pattern = new RegExp("\\[|\\]", "g");
	var listRoleReplace = listRole.replace(pattern, '');
	var roles = listRoleReplace.split(",");
	for (i = 0; i < roles.length; i++) {
		if (roles[i].trim() == role) {
			return true;
		}
	}
	return false;
}

function validateForm() {
	clearValidateForm();
	var productCode = $('#productCode').val();
	var productName = $('#productName').val();
	var price = $('#price').val();
	var origin = $('#origin').val();
	var detail = $('#detail').val();

	if (productCode == "") {
		$("#messageProductCode").text("Please enter product code");
		$('#productCode').focus();
		return false;
	}else if (productCode.length > 8) {
		$("#messageProductCode").text("Max length of product code is 8 characters");
		$('#productCode').focus();
		return false;
	}
	if (productName == "") {
		$("#messageProductName").text("Please enter product name");
		$('#productName').focus();
		return false;
	}else if (productName.length > 50) {
		$("#messageProductName").text("Max length of product name is 50 characters");
		$('#productName').focus();
		return false;
	}
	if (origin.length > 50) {
		$("#messageOrigin").text("Max length of origin is 50 characters");
		$('#origin').focus();
		return false;
	}
	if (detail.length > 256) {
		$("#messageDetail").text("Max length of detail is 256 characters");
		$('#detail').focus();
		return false;
	}
	if(price != null && price != "" && !checkFormatPrice(price)){
		$("#messagePrice").text("The format of price is wrong (**.**)");
		$('#price').focus();
	}
	return true;
}

function checkFormatPrice(price){
	var regex = new RegExp("^[0-9]+(\.[0-9]+)?$");
	if (regex.test(price)) {
	   return true;
	} 
	return false;
}

function formatMessageResponse(data) {
	var message = "";
	for (i = 0; i < data.alertDetails.length; i++) {
		message += data.alertDetails[i].alertDetailMessage + " | ";
	}
	if(message != ""){
		message = message.substring(0, message.length - 2);
	}
	return message;
}


function clearValidateForm(){
	$("#messageProductName").text("");
	$("#messageProductCode").text("");
	$("#messageOrigin").text("");
	$("#messageDetail").text("");
	$("#messagePrice").text("");
	$("#messageSuccess").text("");
	$("#messageFail").text("");
}