//加载文件清单
$.post($.kbase.ctx + '/attach/loadList', function(data){
	$('#list').append(template('templateList', data));
	$('.kbs-date').each(function(i, item){
		var date = new Date();
		date.setTime($(item).text());
		$(this).text(date);
	});
}, 'json');

//打开文件
$('#list').on('click', '.kbs-att', function(){
	var id = $(this).attr('_id');
	window.open($.kbase.ctx + '/attach/get/' + id);
});

//删除文件
$('#list').on('click', '.kbs-del', function(){
	if (window.confirm('确定删除吗')){
		var _this = this;
		var id = $(_this).attr('_id');
		$.post($.kbase.ctx + '/attach/delete', {id: id}, function(data){
			$(_this).parent('td').parent('tr').remove();
		}, 'text');
	}
});