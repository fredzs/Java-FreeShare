/*
 对应的jsp文件是：
group/invitemember.jsp
*/
//TODO:将addOrRemoveTag，addUserFn，addFriendFn移入到futureMember中。
    //或者是一个新的对象，将全局变量尽量局部化。
	var selectList = "";
	var oldSelectList = "";
	var selectIds = "";
	var oldSelectIds = "";
	function set(){
		$("#selectedList").val(selectList);
	}
	/*处理选择列表selectList和发送框中的增加和删除标签。
	* 通过flag的值（click,dblclick）不同的值进行区分，
	* 如果是click就在发送框中添加该用户，如果是dbclick就删除该用户
	*/
	function addOrRemoveTag(value,flag){
		//通过将值付给id = emails的控件上，就通过getFormData的形式将文件的内容进行tag的添加或者是删除。
		document.getElementById('emails').value = value;
		//alert("remove value"+value);
		if(document.all)  
		{  
		     if(flag == "click"){
		    	 document.getElementById('emails').click();  
		     }
		     else if(flag == "dblclick"){
		    	 document.getElementById('emails').dblclick();  
		     }
		     else{
		    	 alert("wrong!");
		     }
		}  
	    else  
		{  
		     var evt = document.createEvent("MouseEvents");  
		     evt.initEvent(flag, true, true);  
		     document.getElementById("emails").dispatchEvent(evt);  
		      
		}   
		document.getElementById('emails').value = "";
		//将selectList的值赋给selectedList的页面隐藏元素。
		$("#selectedList").val(selectList);
		$("#selecteIds").val(selectIds);
	}
	//向发送邮件框中添加一个用户的标签。
	function addUserFn(one){
		if(one.checked == false){		
			addOrRemoveTag(one.value,"dblclick");
			//TODO: 需要将selectList删除该用户名。
			return;
		}		
	   	var addList=selectList;
		var mytable = $("#searchTable");//获取搜索结果表
		if(mytable.find("tr").length == 0){//搜索结果表没内容，就去获取正常列表
			mytable = $("#selectMyTable");
		}	
		oldSelectList = "";
		oldSelectIds = "";
		if( mytable ){
			if(one.checked  && (!selectList.match(one.value))){
				
				addList = one.value;
				
				selectIds += one.name;
				selectIds += ",";				
				addOrRemoveTag(addList,"click");
			}
			else{
			
				oldSelectList = selectList;
				oldSelectIds = selectIds;
				if(addList.match(one.value)){
					var store = new Array();
					store = addList.split(",");
					addList = "";
					for(var i = 0;i < store.length;i++){						
						if(store[i] != one.value){							
							addOrRemoveTag(store[i],"click");
						}
						
					}
				}
			}
			
		}	
	}
	function addFriendFn(one){
		if(one.checked == false){			
			addOrRemoveTag(one.value,"dblclick");
			//TODO: 需要将selectList删除该用户名。
			return;
		}
		if(one.checked  && (!selectList.match(one.value))){
			selectIds += one.name;
			selectIds += ",";	
			addOrRemoveTag(one.value,"click");
		} 		
	}
	function addUserEmail(){
		var emailList = $("#emailSend").val();
		var emails;
		if(emailList.match(",")){
			
			emails = emailList.split(",");
			var count = 0;
			while(count < emails.length){
				
				addOrRemoveTag(emails[count],"click");
				count++;
			}
		}
		else{
			
			addOrRemoveTag(emailList,"click");
		}
	   	var addList = selectList;
		//alert("selectList:"+selectList);
		//alert("selectIds:"+selectIds);
		oldSelectList="";
		oldSelectIds="";
		//将邮件窗口的内容清空。
		$("#emailSend").val("");	
	}
	
	function searchMyFn(searchText,searchDomain){
		var searchText = searchText;
		var mytable ;				
		if(searchDomain == "friend"){
			mytable = $("#myfriends");
		}
		else{			
			mytable = $("#selectMyTable");
		}
		
		var searchresult="";
		var count = 0;
		var avatar = "";
		if( mytable ){
		mytable.find("input[type='checkbox']").each(function(){
			//alert("here"+this.value+"  "+this.name);
			if(this.value.match(searchText)){
				//alert("here"+this.value+"  "+this.name);
				if(count%6 == 0){searchresult +="<tr>";}
				searchresult += "<td>";				
				avatar = "http://freedisk.free4lab.com/download?uuid="+this.name.split(":")[3];
				searchresult += "<img src='"+ avatar + "' onerror=\"javascript:this.src='images/user_male.png'\" width='50' height='50' border='0'/><br/>";
				searchresult += "<input type=\"checkbox\" onclick=\"addUserFn(this);\" name=\""+ this.name +"\" value=\'"+this.value+"\' /><label for = \'"+this.value+"\'>"+ this.value +"</label></td>";
				if(count%6 == 5){searchresult +="</tr>";}
				count++;
			}
		});
		
		if(searchresult){//there exist results
		searchresult = "<tr><td>关键词："+searchText+"<td><td><a href=\"javascript:clearSearchMyFn()\" class=\"blueletter\">清空</a></td></tr>"+searchresult;
		}else{
		searchresult="<tr><td>关键词："+searchText+"<td><td><a href=\"javascript:clearSearchMyFn()\" class=\"blueletter\">清空</a></td></tr>" +
		"<tr><td >没有搜索到结果</td></tr>";
		}
		
		if(searchDomain == "friend"){
			$("#friendSearchTable").html(searchresult);
			
			$("#myfriends").addClass("hidden");
			$("#friendAll").addClass("hidden");
		}
		else{
			$("#searchTable").html(searchresult);
			$("#selectMyTable").addClass("hidden");			
			$("#groupAll").addClass("hidden");
		}
		
		}
	} 
	function clearSearchMyFn(){
		
		$("#searchFriend").val("");
		$("#searchText").val("");
		$("#searchTable").html("");
		$("#friendSearchTable").html("");
		$("#friendAll").removeClass("hidden");
		$("#groupAll").removeClass("hidden")
		$("#selectMyTable").removeClass("hidden").addClass("parseline");
		$("#myfriends").removeClass("hidden").addClass("parseline");
	}
	function submitInvitation(){
		/*if($('#emails').val() == '')
			alert("至少选中一个被邀请人");
		
		else{*/
			document.forms["inviteForm"].submit();
			showLoading();
		//}
	}
	//显示邀请的邮件内容。
	function show(){
		if(document.getElementById("invite").style.display == "none"){
			document.getElementById("invite").style.display="";
			document.getElementById("commonInvite").style.display = "none";
		}
		else{
			document.getElementById("invite").style.display="none";
			document.getElementById("commonInvite").style.display = "";
		}
		
	}
	//设置邮件内容。
	function setInviteLetter(){
		//alert("setInviteLetter");
		var title = document.getElementById("title");
		title.value = "邀请您加入"
		title.value += $("#groupName").val();
		title.value += "小组";
		
		var reason = document.getElementById("reason");
		
		reason.value = "尊敬的用户，您好！\n";
		reason.value += "   ";
		reason.value += $("#myName").val();
		reason.value += "邀请您加入“";
		reason.value += $("#groupName").val();
		reason.value += "”兴趣组。如果您已经拥有Free账号，请点击";
		reason.value += "http://freeshare.free4lab.com/members/getinvitation";
		reason.value += "进行相关处理。";
		reason.value += "如果您尚未拥有Free账号，请请在http://freeshare.free4lab.com/members/getinvitation页面点击注册按钮进行相关注册。\n\t\t\t\t\t“";
		reason.value += $("#groupName").val();
		reason.value += "”小组管理员"; 
	}
	//全角转换为半角函数 
	//全角空格为12288，半角空格为32 
	//其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248 
	function convertToDBC(obj){
		
	   var str=obj.value;
	   var result="";
	   for (var i = 0; i < str.length; i++){
		　　　if (str.charCodeAt(i)==12288){
		　　　　result += String.fromCharCode(str.charCodeAt(i)-12256);
		　　　　continue;
		　　　}
		　　　if (str.charCodeAt(i)>65280 && str.charCodeAt(i)<65375) result+= String.fromCharCode(str.charCodeAt(i)-65248);
		　　　else result+= String.fromCharCode(str.charCodeAt(i));
	   } 
	   obj.value=result;
	}
	//获取公开组的地址。
	function obtainShareAddress(){
		var groupId = $("#groupId").val();
		var address = "自由访问该群组地址为：http://freeshare.free4lab.com/group/resource?groupId="+groupId+"&from=register"; 
		$("#publicIn").html("");
		$("#publicInAddress").html("<h4  class=\"lightgreyletter\">"+address+"</h4>");
	}
	
	var futureMembers = {
			groupId : $("#groupId").val(),
			groupList:null,
			displayList : null,
			friendMap : {},
			membersMap : {},
			totalNum:0,
			futureMemData : null,
			groupNameData: null,
			init : function(){
				this.groupId = $("#groupId").val();
				this.groupList = [];
				this.displayList = null;
				this.friendMap = {};
				this.membersMap = {};
				this.futureMemData = null;
				this.groupNameData = null;
			},
			//进行全选或者是全不选的操作。
			selectAll: function(object){
				var mytable;
				//alert("object:"+object.id);
				//1、首先对当前页面进行判断，如果是群组成员选择页面，就将selectMytable赋给mytable
				if(object.id == "groupMember"){			
					mytable = $("#selectMyTable");
				}
				//如果是好友选择页面，就将friendtable赋给mytable
				else if (object.id == "friendMember"){
					
					mytable = $("#myfriends");
				} 
				//2、对当前页面进行全选操作，如果该选项勾选了，并且在下面的发送框中没有该选项，就将其添加到发送框中。
				if(object.checked == true ){
					mytable.find("input[type='checkbox']").each(function(){
						this.checked = true;
						if(!selectList.match(this.value)){
							addUserFn(this);
						}
									
					});
				}
				else{//对当前页面进行删除，对全部的勾选
					mytable.find("input[type='checkbox']").each(function(){
						this.checked = false;
						addOrRemoveTag(this.value,"dblclick");
					
					});
				}
			},
			//获取我的朋友的列表。
			displayFriends : function(){
				this.init();
				var msgAddr  = "http://webrtcmessage.free4lab.com/";
				//var msgAddr  = "http://localhost:9090/freemessage/";
				
				$.getJSON(msgAddr +"friend/getfriendlist?callback=?",{},function(result){
					 var friends =  result;
					 //console.log(result);
					 			 
					 $.post('members/invitemyfriend',{groupId : $("#groupId").val()},function(data) {
						 console.log(data.members);
						 var members;
						 var memberList = "";
						 members = data.members;
						 console.log(members);
						 $(members).each(function(){
							 memberList += ","+this.uid;
						 });
						 memberList += ",";
						 //alert("memberList:"+memberList)
						 console.log(result);
						 var j = 0;
						 var friendList = [];
						 for(var i = 0;i < friends.length;i++){
							 //alert(i+":"+friends[i].userId)
						 	//if(memberList.indexOf(","+friends[i].userId+",")  == -1){
						 		friendList[j] = friends[i];
						 		j++;
						 		
						 	//}
							 
						 }
						 //alert("friendList:"+friendList.length)
						 var userMap = {};
						 userMap['all'] = friendList;
						 //alert(userMap)
						 var newFriendList = "<tr>";
						 newFriendList += futureMembers._list(userMap);
						 newFriendList += "</tr>";
						 $("#myfriends").html("");
						 $("#myfriends").html(newFriendList);
					 });
				});	 
				//
			},
			_list:function(data){
				return this._getFriend(data,"all");
			},
			//判断筛选中是否匹配某个群组。
			_matchGroupId : function(groupId,key){
				if(groupId == key){
					return true;
				}
				else{
					return false;
				}
			},
			//判断筛选是全部群，还是某一个群组。
			_isAllGroups : function(groupId){
				if("all" == groupId){
					return true;
				}
				else{
					return false;
				}
			},
			_getFriend : function(data,groupId){
				var list = "";
				 var i = 0;
				 var j = 0;
				 //var groupIdList = [];TODO:可以未来用来记录人数。
				 var storeThis = this;
				 var userMap = {};
				 $.each(data,function(key,values){  
						    if(true == futureMembers._matchGroupId(groupId,key)||true == futureMembers._isAllGroups(groupId)){					    	
						    	
						    	$(values).each(function(){     		      
							       //进行判断，如果该用户的id没有在userList中的话，那么就将其加入到用户中。去重复。
							       if(!userMap.hasOwnProperty(this.userId)){
								       list += "<td><div><img src='http://freedisk.free4lab.com/download?uuid="+this.avatar + "' onerror=\"javascript:this.src='images/user_male.png'\" width='50' height='50' border='0'/><br/>";
								       list += "<input  type=\"checkbox\" name='";
								       list += this.userId+":"+this.userName+":"+this.email+":"+this.avatar+"' value='";
								       list += this.userName +"(" +this.userId +")"+ "' onclick='addFriendFn(this);'/>";							      							
								       //处理过长的用户名的显示。
								       var stardardUserName = function(s){
								    	   var length = 0;
								    	   var userName = "";
									  	   var a = s.split("");
										   for (var j = 0;j < a.length;j++) {
											  if (a[j].charCodeAt(0)<299) {
												  length++;
											  } else {
												  length += 2;
											  }
											  if(length > 9){
												  break;
											  }
											  else{
												  userName += a[j];
											  }
										  }									  
										   return userName;
								       }
								       list += stardardUserName(this.userName);
								       list += "</div></td>";
									   if(i % 6 == 5){
											list += "</tr><tr>";																	
									   }
									   i++;
									   //在userMap中标记该用户，避免重复显示。
									   userMap[this.userId] = 0;
									   
							       }
							       else{//doing nothing;
							       }
							    });
						    }
						   
					 });		
				this.totalNum = i;
				
				return list;	
			},
			//被调用的未来用户的拼接。
			_getList : function(data,groupId){
				 var list = "";
				 var i = 0;
				 var j = 0;
				 //var groupIdList = [];TODO:可以未来用来记录人数。
				 var storeThis = this;
				 var userMap = {};
				 $.each(data,function(key,values){  
						    if(true == futureMembers._matchGroupId(groupId,key)||true == futureMembers._isAllGroups(groupId)){					    	
						    	
						    	$(values).each(function(){     		      
							       //进行判断，如果该用户的id没有在userList中的话，那么就将其加入到用户中。去重复。
							       if(!userMap.hasOwnProperty(this.uid)){
								       list += "<td><div><img src='http://freedisk.free4lab.com/download?uuid="+this.profile_image_url + "' onerror=\"javascript:this.src='images/user_male.png'\" width='50' height='50' border='0'/><br/>";
								       list += "<input  type=\"checkbox\" name='";
								       list += this.uid+":"+this.screen_name+":"+this.email+":"+this.profile_image_url+"' value='";
								       list += this.screen_name +"(" +this.uid +")"+ "' onclick='addFriendFn(this);'/>";							      							
								       //处理过长的用户名的显示。
								       var stardardUserName = function(s){
								    	   var length = 0;
								    	   var userName = "";
									  	   var a = s.split("");
										   for (var j = 0;j < a.length;j++) {
											  if (a[j].charCodeAt(0)<299) {
												  length++;
											  } else {
												  length += 2;
											  }
											  if(length > 9){
												  break;
											  }
											  else{
												  userName += a[j];
											  }
										  }									  
										   return userName;
								       }
								       list += stardardUserName(this.screen_name);
								       list += "</div></td>";
									   if(i % 6 == 5){
											list += "</tr><tr>";																	
									   }
									   i++;
									   //在userMap中标记该用户，避免重复显示。
									   userMap[this.uid] = 0;
									   
							       }
							       else{//doing nothing;
							       }
							    });// end each values  
							    //alert("i:"+i);
						    }
						    /* if(true == futureMembers._isAllGroups(groupId)){
						    	groupIdList[j] = key;
							    j++;						    
						    } */
					 });// end data				
				    //futureMembers.groupList = groupIdList;			
				this.totalNum = i;
				
				return list;	
			 },
			 
			_getGroupList : function(){
				 var groups = this.groupNameData;
				 var groupString = "";
				 $.each(groups,function(key,value){  
					 groupString += key+":"+value +",";
				 });			
				 return groupString.trim();				
			},		
			//展示群组名称的列表。
			_displayGroupList : function(groups){		
				 var list = "<div><table id=\"memtable\"><tr><td><span id='all' class='greybox' onclick='futureMembers.selectGroup(\"all:all\");'>";
				 list += "全部其他群组("+this.totalNum+")</span></td></tr><tr>";
				 $(groups).each(function(){
					 //TODO：多一个undefined的。用的是substring笨方法处理的，原因不详。
					var tmp = this.split(":");				
					if(null != tmp[1]){
					 list += "<td><span id=\""+tmp[0]+"\" onclick='futureMembers.selectGroup(\""+this+"\");'>&nbsp;&nbsp;"+tmp[1]+"</span></td></tr><tr>";
					 //list += "<td><span onclick='futureMembers.selectGroup(this);'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+tmp[1]+"</span></td></tr><tr>";
					}
				 });
				 list = list.substring(0,list.length - 4);
				 list += "</table></div>";
				 return list;			 
			},
			 //筛选某一个组的成员。
			 _getSingleGroupMem : function(searchGroupId){
					var memberList = "<table id=\"membertable\"><tr>";
					memberList += this._getList(this.futureMemData,searchGroupId);
					memberList += "<td></td></tr></table>";
					$("#selectMyTable").html("");
					$("#selectMyTable").html(memberList);  
					
			},
			//选择单独的群组。
			selectGroup : function(groupId){	
				$('span').attr("class","");
				$("#"+groupId.split(":")[0]).attr("class","greybox");
				this._getSingleGroupMem(groupId.split(":")[0]);
			},
			//展示全部的群组的成员。
			displayAllGroupsMem : function(){
				 this.init();
				 $("#all").attr("class","greybox");
				 var memberList = "<table id=\"friendtable\"><tr>";
				 //在项目的ajax的post的方法中，this的指针被重新赋值，为了保存futureMembers对象指针，来调用_getList的方法。
				 this.groupList = {};
				 $.post('members/inviteothers',{groupId : this.groupId},function(data) {			
						var userList = [];
						var memberMap = {};
						var futureMem = data.groupUserMap;
						
						//将请求的数据序列化，存在futureMemData和groupNameData中，方便进行筛选查询用，免去再次请求。
						futureMembers.futureMemData = JSON.parse(JSON.stringify(data.groupUserMap));
						futureMembers.groupNameData = JSON.parse(JSON.stringify(data.groupNameMap));
						//alert("futureMembers.groupNameData"+futureMembers.groupNameData.toString());
						//调用对象的_getList的方法，获取拼接好的html形式的其他组的成员显示的字符串。
						//不能使用this进行调用，因为this此时指的是一个ajaxSetting;
					    memberList += futureMembers._getList(futureMem,"all");
						memberList += "<td></td></tr></table>";
						$("#selectMyTable").html(memberList);  
				    	var groups = futureMembers._getGroupList().split(",");					
						var groupTable = futureMembers._displayGroupList(groups);
						$("#groupTable").html(groupTable);  				
				}); //end post			
			 }
		
	};
