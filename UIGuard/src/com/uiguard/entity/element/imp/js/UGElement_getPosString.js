function getPageAbslutePosExceptIE(win_, element_) {
	var pos_ = {
		left : 0,
		top : 0
	}, top_ = element_.offsetTop, left_ = element_.offsetLeft;
	var parent = undefined;
	while (element_.offsetParent != null) {
		parent = element_.offsetParent;
		top_ += parent.offsetTop;
		left_ += parent.offsetLeft;
		element_ = parent;
	}
	pos_.top += top_;
	pos_.left += left_;
	if (win_.frameElement) {
		var framePos = getPageAbslutePosExceptIE(win_.parent, win_.frameElement);
		pos_.left += framePos.left;
		pos_.top += framePos.top;
		win_ = win_.parent;
	}
	return pos_;
};

function getWinScrolloffset(win_) {
	var pos_ = {
		left : 0,
		top : 0
	};
	while (win_.frameElement) {
		pos_.top += win_.document.body.scrollTop;
		pos_.left += win_.document.body.scrollLeft;
		win_ = win_.parent;
	}
	return pos_;
};

function getElementByJsString() {
	return "+ getJsString() +"
};

var abspos = getPageAbslutePosExceptIE(window, getElementByJsString());

var scrpos = getWinScrolloffset(window);

return (abspos.left - scrpos.left + window.screenX) + '###' + (abspos.top - scrpos.top + window.screenY);

