var $;
layui.config({
	base : "js/"
}).use(['form','layer','jquery'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage;
		$ = layui.jquery;
 	form.on("submit(editUser)",function(){
 		//弹出loading
 		var index = top.layer.msg('数据提交中...',{time:false,shade:0.8});
 		//接受用户数据
 		var id = $("input[name='userId']").val();
 		var phone = $("input[name='userPhone']").val();
 		var sex = $("input[name='sex']:checked").val();
 		var nick = $("input[name='userNick']").val();
 		var p = $("select[name='province'] option:selected").text();
 		var a = $("select[name='area'] option:selected").text();
 		var address = p+a;
 		console.log(address);
 		var status = $("select[name='userStatus'] option:selected").val();
 		var img = $("input[name='userImg']").val();
 		var imei = $("input[name='userImei']").val();
 		var word = $("textarea[name='userWord']").val();
 		console.log(word);
 		$.ajax({  
    		url:"/vhome/PerformUserInfo",
    		data:{"userId":id,"userPhone":phone,"userSex":sex,"userNick":nick,"userArea":address,"userStatus":status,
    			"userImg":img,"userImei":imei,"userWord":word},
    		type:"POST",
    		success:function(e){
    				if(e){
    					setTimeout(function(){
    			            top.layer.close(index);
    						alert("用户更新成功！");
    			 			layer.closeAll("iframe");
    				 		//刷新父页面
    				 		parent.location.reload();
    			        },4000);
    				}
    		}
    	});
 		return false;
 	});
	
});
