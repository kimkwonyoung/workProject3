/**
 * oLoader ajax 처리
 * @param selector
 * @param url
 * @param method
 * @param data
 * @param isWholeWindow
 * @param onComplete 필수 : 완료 후 처리할 콜백 메서드
 */
function oLoader(selector, url, method, data, isWholeWindow, onComplete) {
	if (typeof onComplete !== 'function') {
		alert('onComplete(data) 메서드가 필요합니다.');
		return;
	}
	
	$(selector).oLoader({
		url: url
		, type: method || 'POST'
		, data: data
		//, image: '/images/loading_4.gif'
		, backgroundColor: '#fff'
		, fadeLevel: 0.9
		, image: '/images/loading_3_32.gif'
		, style: 0
		, wholeWindow: isWholeWindow || false
		, updateContent: false 				//자기 자신에게 업데이트 금지 - 결과를 별도 위치에 표시할 경우 사용 : complete()에서 처리
		, complete: onComplete
		, onError: function(jqXHR, textStatus, errorThrown) {
			 alert('error : ' + textStatus);
		}
	});
	
	/*
	 * oLoader : http://projects.ownage.sk/
	{
	  image: 'images/ownageLoader/loader1.gif',
	  style: 1,
	  modal: true,
	  fadeInTime: 300, // time in milliseconds
	  fadeOutTime: 300,
	  fadeLevel: 0.7, // overlay opacity (values 0 - 1)
	  backgroundColor: '#000',
	  imageBgColor: '#fff', // background under the loader image
	  imagePadding: '10',
	  showOnInit: true,
	  hideAfter: 0, // time in ms
	  url: false,
	  type: 'GET',
	  data: false,
	  updateContent: true,
	  updateOnComplete: true,
	  showLoader: true, // shows the loader image
	  effect: '',
	  wholeWindow: false, // when true, oLoader covers the whole window
	  lockOverflow: false, // locks body's overflow while loading
	  waitLoad: false, // oLoader will hide after element is loaded (works
						// on imgs, body and iframes)
	  checkIntervalTime: 100, // interval which checks for position
								// changes
	   
	  // callback functions
	  complete: '', // executes on complete
	  onStart: '', // executes when animation starts
	  onError: '' // executes when ajax request fails
	}
	*/
}