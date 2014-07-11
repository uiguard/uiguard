
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

var scollpos = getWinScrolloffset(window);

return (window.screenLeft - scollpos.left )+'###'+ (return window.screenTop - scollpos.top);
