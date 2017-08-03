//如果对msg1确认通过则给出提示信息msg2
function confirm_Alert(msg1,msg2){
  if(window.confirm(msg1)){
  	alert(msg2);
  	return true;
  	}else{
  	return false;
    }
}

//删除左右两端的空格
function mtrim(str){  
 return str.replace(/(^\s*)|(\s*$)/g,"");
}

//删除左边的空格
function mltrim(str){  
 return str.replace(/(^\s*)/g,"");
}

//删除右边的空格
function mrtrim(str){  
 return str.replace(/(\s*$)/g,"");
}