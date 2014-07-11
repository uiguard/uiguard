function hasActiveAjaxJsStr() {
	return (window && window.jQuery) ? 
			(window.jQuery.readyList == null && (!window.jQuery.activeAjaxRequestCount || window.jQuery.activeAjaxRequestCount == 0))
			: true
};

return hasActiveAjaxJsStr();
