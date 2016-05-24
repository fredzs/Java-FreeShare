//转移到group_administration中的administration.js中	
//成员退出群组
function quit(uid, groupId) {

	if(confirm('确认退出?') == true){
		$.ajax({
			type : 'POST',
			url : 'members/quit',
			data : {
				uid : uid,
				groupId : groupId,
			},
			success : function() {
				//alert("已经退出该群组！");
				gotonext("groups?tipstatus=4");
			}
		});
	}
}