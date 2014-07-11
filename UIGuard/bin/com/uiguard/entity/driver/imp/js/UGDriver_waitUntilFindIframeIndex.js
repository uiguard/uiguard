
function waitUntilFindIframeIndex(frameNameOrId, doc) {
	var iframes = doc.getElementsByTagName('iframe');
	for ( var i = 0; i < iframes.length; i++) {
		var tIframe = iframes[i];
		if ((tIframe.id && tIframe.id == frameNameOrId)
				|| (tIframe.name && tIframe.name == frameNameOrId)) {
			return i;
		}
	}
};

return waitUntilFindIframeIndex(arguments[0], document);
