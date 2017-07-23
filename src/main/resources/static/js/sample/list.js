$.post($.kbase.ctx + '/sample/loadList', function(data){
	$('#list').append(template('templateList', data));
}, 'json');

//编辑
$('#list').on('click', '.btn-edit', function(){
	var id = $(this).attr('_id');
	location.href = $.kbase.ctx + '/sample/create?id=' + id;
});

//删除
$('#list').on('click', '.btn-del', function(){
	var _this = this;
	var id = $(this).attr('_id');
	if (window.confirm('确定删除吗')){
		$.post($.kbase.ctx + '/sample/delete', {id: id}, function(data){
			$(_this).parent('td').parent('tr').remove();
		});
	}
});