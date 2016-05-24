//实现搜索提示
var ListUtil = new Object();

ListUtil.getSelectedIndexes = function (oListbox) {
    var arrIndexes = new Array;
    for (var i=0; i < oListbox.options.length; i++) {
        if (oListbox.options[i].selected) {
            arrIndexes.push(i);
           
        }
    }
   
    return arrIndexes;
};

ListUtil.add = function (oListbox, sName, sValue) {

    var oOption = document.createElement("option");
    oOption.appendChild(document.createTextNode(sName));

    if (arguments.length == 3) {
        oOption.setAttribute("value", sValue);
    }

    oListbox.appendChild(oOption);

};
ListUtil.myadd = function (oListbox, sName) {

    var oOption = document.createElement("option");
    //alert("oOption"+oOption);
    var tmp;
    tmp = sName;
    /*this part is used to process the long string presentation*/
    if(sName.length>30){    	
    	tmp = tmp.substring(0,10);
    	tmp+="...";
    }
    if(sName.length>4){ 
	    if( tmp.match("'的资源")!=null||tmp.match("'的列表")!=null
	    		||tmp.match("'的群组")!=null){
			  
			tmp=tmp.substring(1,length-4);
			
	    }
    }
    oOption.appendChild(document.createTextNode("搜'"+tmp+"'的"+"资源"));
    /*var ziyuan = document.createTextNode("搜'"+tmp+"'的"+"资源");
    
    oOption.appendChild(ziyuan);
    */
	//oOption.appendChild(document.createTextNode("搜'<span class='redletter'>"+tmp+"</span>'的"+"资源"));
	//oListbox.appendChild(oOption);
    //var myOption = 
    oListbox.innerHTML +="<option>搜'<span class='redletter'>"+tmp+"</span>'的资源</option>"; 
	//oListbox.
	oOption = document.createElement("option");
	oOption.appendChild(document.createTextNode("搜'"+tmp+"'的"+"列表"));
	oListbox.appendChild(oOption);
	
	oOption = document.createElement("option");
	oOption.appendChild(document.createTextNode("搜'"+tmp+"'的"+"群组"));
	oListbox.appendChild(oOption);
    

};

ListUtil.remove = function (oListbox, iIndex) {
    oListbox.remove(iIndex);
};

ListUtil.clear = function (oListbox) {
    for (var i=oListbox.options.length-1; i >= 0; i--) {
        ListUtil.remove(oListbox, i);
    }
};

ListUtil.move = function (oListboxFrom, oListboxTo, iIndex) {
    var oOption = oListboxFrom.options[iIndex];

    if (oOption != null) {
        oListboxTo.appendChild(oOption);
    }
};

ListUtil.shiftUp = function (oListbox, iIndex) {
    if (iIndex > 0) {    
        var oOption = oListbox.options[iIndex];
        var oPrevOption = oListbox.options[iIndex-1];
        oListbox.insertBefore(oOption, oPrevOption);
    }    
};

ListUtil.shiftDown = function (oListbox, iIndex) {
    if (iIndex < oListbox.options.length - 1) {
        var oOption = oListbox.options[iIndex];
        var oNextOption = oListbox.options[iIndex+1];
        oListbox.insertBefore(oNextOption, oOption);
    }
};

var EventUtil = new Object;
EventUtil.addEventHandler = function (oTarget, sEventType, fnHandler) {
    if (oTarget.addEventListener) {
        oTarget.addEventListener(sEventType, fnHandler, false);
    } else if (oTarget.attachEvent) {
        oTarget.attachEvent("on" + sEventType, fnHandler);
    } else {
        oTarget["on" + sEventType] = fnHandler;
    }
};
        
EventUtil.removeEventHandler = function (oTarget, sEventType, fnHandler) {
    if (oTarget.removeEventListener) {
        oTarget.removeEventListener(sEventType, fnHandler, false);
    } else if (oTarget.detachEvent) {
        oTarget.detachEvent("on" + sEventType, fnHandler);
    } else { 
        oTarget["on" + sEventType] = null;
    }
};

EventUtil.formatEvent = function (oEvent) {
    if (isIE && isWin) {
        oEvent.charCode = (oEvent.type == "keypress") ? oEvent.keyCode : 0;
        oEvent.eventPhase = 2;
        oEvent.isChar = (oEvent.charCode > 0);
        oEvent.pageX = oEvent.clientX + document.body.scrollLeft;
        oEvent.pageY = oEvent.clientY + document.body.scrollTop;
        oEvent.preventDefault = function () {
            this.returnValue = false;
        };

        if (oEvent.type == "mouseout") {
            oEvent.relatedTarget = oEvent.toElement;
        } else if (oEvent.type == "mouseover") {
            oEvent.relatedTarget = oEvent.fromElement;
        }

        oEvent.stopPropagation = function () {
            this.cancelBubble = true;
        };

        oEvent.target = oEvent.srcElement;
        oEvent.time = (new Date).getTime();
    }
    return oEvent;
};

EventUtil.getEvent = function() {
    if (window.event) {
        return this.formatEvent(window.event);
    } else {
        return EventUtil.getEvent.caller.arguments[0];
    }
};

var TextUtil = new Object;
TextUtil.autosuggest = function (oTextbox, arrValues, sListboxId) {
    
    var oListbox = document.getElementById(sListboxId);
    var arrMatches = TextUtil.autosuggestMatch(oTextbox.value, arrValues);
    
    ListUtil.clear(oListbox);
    
    for (var i=0; i < arrMatches.length; i++) {
        ListUtil.add(oListbox, arrMatches[i]);
    }
    
    
};

function enterTest(event){
	oEvent = EventUtil.formatEvent(event);      	
	if(oEvent.keyCode == 13){
		document.forms["search"].submit();			
	}       	
}
/*function addItem is used to add items into listbox;
  it is a functional function.
*/


function addItem() {
        var oListbox = document.getElementById("selListbox");
        var oTxtName = document.getElementById("squery");
        //var length = oTxtName.value.length;

		ListUtil.clear(oListbox);
        ListUtil.myadd(oListbox, oTxtName.value);
        showDiv();
}

/*function searchTip is used for producing the tip for search tip.
it is trigger by the input, whose id is "squery" action keydown.
when it accepts the up/down key's event,it will set focus on the listbox, whose id is "selListbox".
then it will use the addItem function to  add items into the same listbox.
Otherwise,it will only add items into the listbox.

*/
function searchTip(event){
    	//var oListText = document.getElementById("squery");
    	var oListbox = document.getElementById("selListbox");
    	var oTxtName = document.getElementById("squery");
    	oEvent = EventUtil.formatEvent(event);
    	addItem();  
    	if(oEvent.keyCode == 38){		
    		if(oListbox.selectedIndex >=0){
    			oListbox.selectedIndex=(oListbox.selectedIndex-1)%3;		          			
    			oListbox.focus();
    			
    		}
    	}
    	else if(oEvent.keyCode == 40){
    		oListbox.focus(); 
    		if(oListbox.selectedIndex >=0){
				oListbox.selectedIndex=(oListbox.selectedIndex+1)%3;
				
			}
			else{
				oListbox.selectedIndex=oListbox.selectedIndex+1;
				//alert("select"+oListbox.selectedIndex);
			}		
    		oListbox.focus();   
    		setText();
    	}
    	else if(oEvent.keyCode ==8||oEvent.keyCode ==46){
    		
    		if(oTxtName.value.toString().length==0){

    			var div = document.getElementById("searchTip");
    			div.style.display = "none";
    		}
    	}
    	//addItem();           
    }
/*this function packages the function setText.
it is triggered by the listbox action onclick.
if you click one option of the select, it will directly submit the form, and perform 
the submit action.
*/
function setText1(){
    	setText();
    	document.forms["search"].submit();
}
/*function setText is used to set the text type,
for example:
	if the user inputs 云海, and select the option'搜‘云海’的资源'by click or up/down key
	then the input text is set as ‘云海’的资源.
there are three types for the type
all;item;list
*/
function setText(){
    	var oListbox = document.getElementById("selListbox");
    	var list = document.getElementById("squery");
    	var type = document.getElementById("type");
    	var selected = ListUtil.getSelectedIndexes(oListbox);
    	var tmp = list;
    	var length =list.value.length;
    	
        if(list.value.toString()==""){            	
       		alert("null return");
        	return;
        }
        if (selected == -1){
        		type.value = "all";      
        }             	
        	
        else if( list.value.match("'的资源")!=null||list.value.match("'的列表")!=null
        		||list.value.match("'的群组")!=null){
    		  
    		list.value=list.value.substring(1,length-4);
    		
        }
        if(selected == 0){
        		
				list.value = "'"+list.value+"'的资源";      		
        		type.value = "item";
        }
        if(selected == 1){
        		list.value = "'"+list.value+"'的列表";      		
            	//tmp.value = "'"+list.value;
            	//list.value = tmp.value+"'的列表";
            	//list.value += "'的列表";       		
            	type.value = "list";
        }
        if(selected == 2){
        	list.value = "'"+list.value+"'的群组";     
        	//tmp.value = "'"+list.value;
        	//list.value = tmp.value+"'的群组";
        	//list.value += "'的群组";       		
        	type.value = "group";
        }
         	       
    }
/*function showDiv is used to show the listbox of search tip for type*/
function showDiv(){
	var oTxtName = document.getElementById("squery");
	if( oTxtName!=null&&oTxtName.toString().length > 0){
		var div = document.getElementById("searchTip");
		div.style.display = "block";
	}
	/*if( oTxtName != null){
		var div = document.getElementById("searchTip");
		div.style.display = "block";
	}*/
}
function doSubmit(){
	 document.forms["search"].submit();
}



	
