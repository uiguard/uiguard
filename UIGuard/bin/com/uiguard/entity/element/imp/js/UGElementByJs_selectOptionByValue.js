
function getSelectOptionByText(selectDom, value) {
	var options = selectDom.getElementsByTagName('option');
	for ( var i = 0; i < options.length; i++) {
		var option = options[i];
		if(option.getAttribute('value') == value){
			return option;
		}
	}
};
