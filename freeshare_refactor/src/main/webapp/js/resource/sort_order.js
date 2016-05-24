/*
 对应的jsp文件是：
basic/_iteminlist.jsp
*/
function sort_order(param) {
		//判断是否已经有调整次序的资源
		 if ($('#order').val() == undefined) {
			append_(param);
			
		} else {
			cancel_();
			append_(param);
		} 
	}
	//提交调整次序的请求
function sub(param) {
		var target_order = $("#order").val();
	var order_ID = parseInt(target_order)
	if (order_ID <= parseInt(0)) {
		alert("调整次序输入有误，输入合法次序！");
	} else {
		var item_number = item_number;
		var listId = $("#resourceId").val();
		
		$.ajax({
			type : 'post',
			url : 'resource/changeorder',
			data : {
				resource_id : param,
				listId : listId,
				target_order : target_order
			},
			success : function(data) {
				location.reload(true);
			}
		});
	}
}
		
function append_(param) {
	var input = "<span id = \"op_area\">&nbsp; <input id = \"order\" size=\"3\" maxlength=\"3\" name = \"order\"></input> &nbsp;"
			+ "<a href = \"javascript:void(0)\" id = \"sub\" onclick = \"sub('"
			+ param
			+ "')\">确定</a> | "
			+ "<a href = \"javascript:void(0)\" id = \"can\" onclick = \"cancel_()\">取消</a></span>"
	$('#' + param).append(input);
}
 
function cancel_() {
	$('#op_area').remove();
}
