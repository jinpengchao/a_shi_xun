//layui.config({
//	base : "js/"
//}).use(['form','layer','jquery','laypage'],function(){
//	var form = layui.form(),
//		layer = parent.layer === undefined ? layui.layer : parent.layer,
//		laypage = layui.laypage,
//		$ = layui.jquery;
//
//	//加载页面数据
//	var linksData = '';
//	$.ajax({
//		url : "../../json/linksList.json",
//		type : "get",
//		dataType : "json",
//		success : function(data){
//			linksData = data;
//			if(window.sessionStorage.getItem("addLinks")){
//				var addLinks = window.sessionStorage.getItem("addLinks");
//				linksData = JSON.parse(addLinks).concat(linksData);
//			}
//			//执行加载数据的方法
//			linksList();
//		}
//	})
//	
//	//全选
//	form.on('checkbox(allChoose)', function(data){
//		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
//		child.each(function(index, item){
//			item.checked = data.elem.checked;
//		});
//		form.render('checkbox');
//	});
//
//	//通过判断文章是否全部选中来确定全选按钮是否选中
//	form.on("checkbox(choose)",function(data){
//		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
//		var childChecked = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"]):checked')
//		data.elem.checked;
//		if(childChecked.length == child.length){
//			$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = true;
//		}else{
//			$(data.elem).parents('table').find('thead input#allChoose').get(0).checked = false;
//		}
//		form.render('checkbox');
//	})
// 
//	//操作
//	$("body").on("click",".links_edit",function(){  //编辑
//		layer.alert('您点击了友情链接编辑按钮，由于是纯静态页面，所以暂时不存在编辑内容，后期会添加，敬请谅解。。。',{icon:6, title:'友链编辑'});
//	})
//
//	$("body").on("click",".links_del",function(){  //删除
//		var _this = $(this);
//		layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
//			//_this.parents("tr").remove();
//			for(var i=0;i<linksData.length;i++){
//				if(linksData[i].linksId == _this.attr("data-id")){
//					linksData.splice(i,1);
//					linksList(linksData);
//				}
//			}
//			layer.close(index);
//		});
//	})
//
//	function linksList(that){
//		//渲染数据
//		function renderDate(data,curr){
//			var dataHtml = '';
//			if(!that){
//				currData = linksData.concat().splice(curr*nums-nums, nums);
//			}else{
//				currData = that.concat().splice(curr*nums-nums, nums);
//			}
//			if(currData.length != 0){
//				for(var i=0;i<currData.length;i++){
//					dataHtml += '<tr>'
//			    	+'<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
//			    	+'<td align="left">'+currData[i].linksName+'</td>'
//			    	+'<td><a style="color:#1E9FFF;" target="_blank" href="'+currData[i].linksUrl+'">'+currData[i].linksUrl+'</a></td>'
//			    	+'<td>'+currData[i].masterEmail+'</td>'
//			    	+'<td>'+currData[i].linksTime+'</td>'
//			    	+'<td>'+currData[i].showAddress+'</td>'
//			    	+'<td>'
//					+  '<a class="layui-btn layui-btn-mini links_edit"><i class="iconfont icon-edit"></i> 编辑</a>'
//					+  '<a class="layui-btn layui-btn-danger layui-btn-mini links_del" data-id="'+data[i].linksId+'"><i class="layui-icon">&#xe640;</i> 删除</a>'
//			        +'</td>'
//			    	+'</tr>';
//				}
//			}else{
//				dataHtml = '<tr><td colspan="7">暂无数据</td></tr>';
//			}
//		    return dataHtml;
//		}
//
//		//分页
//		var nums = 13; //每页出现的数据量
//		if(that){
//			linksData = that;
//		}
//		laypage({
//			cont : "page",
//			pages : Math.ceil(linksData.length/nums),
//			jump : function(obj){
//				$(".links_content").html(renderDate(linksData,obj.curr));
//				$('.links_list thead input[type="checkbox"]').prop("checked",false);
//		    	form.render();
//			}
//		})
//	}
//})
