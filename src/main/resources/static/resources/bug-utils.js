/**
 * 获取地址栏参数
 * @param key 参数名
 * @returns 参数值
 */
function getQueryParam(key){
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
}
/**
 * AJAX 请求封装
 */
var HttpUtils = {
	get:function(url, data){
		return new Promise(function(resolve, reject){
			$.ajax({
				url:url,
				type:'get',
				data:(data || {}),
				dataType:"json"
			}).done(function(res){
				resolve(res);
			}).fail(function(res){
				reject(res);
			});
		});
	},
	post:function(url, data){
		return new Promise(function(resolve, reject){
			$.ajax({
				url:url,
				type:'post',
				data:(data || {}),
				dataType:"json"
			}).done(function(res){
				resolve(res);
			}).fail(function(res){
				reject(res);
			});
		});
	}
};