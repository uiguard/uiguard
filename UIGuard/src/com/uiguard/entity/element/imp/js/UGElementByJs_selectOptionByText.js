
function selectOptionByText(selectDom, text) {
	var options = selectDom.getElementsByTagName('option');
	for ( var i = 0; i < options.length; i++) {
		var option = options[i];
		if (option.innerHTML == text) {
			return option;
		}
	}
};
