

function getUserAssets(userid, callbackFunction) {
	$.getJSON(
		"UserAssetsController",
		{ userid: userid },
	 	callbackFunction
	);
}

