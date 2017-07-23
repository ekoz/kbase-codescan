//日期初始化
$('#dateCode').datepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN'
}).val(dateFns.format(new Date(), 'YYYY-MM-DD'));

$.post($.kbase.ctx + '/code/loadList', function(data){
	$('#list').append(template('templateList', data));
	$('tr[_status="0"]').addClass('danger');
}, 'json');