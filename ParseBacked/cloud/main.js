
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
	var obj = {
		"data":"test",
		"image":"url"
	}
  response.success(obj);
});

Parse.Cloud.define("image",function(request,response){
	var obj = {
		"image":"http://mud-kage.kakao.co.kr/14/dn/btqbS4p40b5/OKhsBA1UMnKKkiHnqBOu80/o.jpg"
	}
	response.success(obj);
});
