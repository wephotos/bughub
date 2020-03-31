layui.use(['layer'], function () {
    let $ = layui.jquery;

    let content_img_list = $('.content-img-list');
    // 鼠标经过显示删除按钮
    content_img_list.on('mouseover', '.content-img-list-item', function () {
        $(this).children('a').removeClass('hide');
    });

    // 鼠标离开隐藏删除按钮
    content_img_list.on('mouseleave', '.content-img-list-item', function () {
        $(this).children('a').addClass('hide');
    });

    // 删除图片
    $("body").on("click", ".content-img-list-item a", function (e) {
        let item = $(this).parent();
        let file_id = item.data("fileid");
        $.get(`/hub-file/delete/${file_id}`, function (res) {
            if (res.code === 0) {
                item.remove();
            } else {
                layer.msg(res.msg);
            }
        });
    });
});

/**
 * 粘贴截图
 * @param event
 * @param callback
 * @returns
 */
function onPasteClipboardImage(event, callback) {
	if (!(event.clipboardData || event.originalEvent)) {
		return false;
	}
	var clipboardData = (event.clipboardData || event.originalEvent.clipboardData);
	if (!clipboardData.items) {
		return false;
	}
	//阻止冒泡
	event.preventDefault();
	var blob, items = clipboardData.items;
	for (var i = 0; i < items.length; i++) {
		if (items[i].type.indexOf("image") !== -1) {
			blob = items[i].getAsFile();
		}
	}
	if (blob) {
		var reader = new FileReader();
		reader.onload = function(event) {
			//图片的Base64编码字符串
			var base64 = event.target.result;
			typeof callback === 'function' && callback(base64);
		}
		reader.readAsDataURL(blob);
	}
}