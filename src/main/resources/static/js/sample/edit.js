var CodeParser = {
	code: '',
	parse: function(code){
		if (code.length!=27){
			alert('扫描码必须是27位');
			return false;
		}
		this.code = code;
		var result = [];
		
		result.push(this.getPartNo());
		result.push(this.getVenderCode());
		result.push(this.getVersionNo());
		result.push(this.getMouldCode());
		
		return result;
	}, 
	getPartNo: function(){
		return this.code.substring(0, 11);
	},
	getVenderCode: function(){
		return this.code.substring(11, 15);
	}, 
	getVersionNo: function(){
		return this.code.substring(15, 17);
	},
	getMouldCode: function(){
		return this.code.substring(20, 23);
	}
};

var id = $('#id').val();
if (id!=''){
	$.post($.kbase.ctx + '/sample/loadData', {id: id}, function(data){
		$('#partName').val(data.partName);
		$('#partNo').val(data.partNo);
		$('#venderCode').val(data.venderCode);
		$('#versionNo').val(data.versionNo);
		$('#mouldCode').val(data.mouldCode);
	}, 'json');
}

$('#btnParse').click(function(){
	var arr = CodeParser.parse($('#code').val());
	$('#partNo').val(arr[0]);
	$('#venderCode').val(arr[1]);
	$('#versionNo').val(arr[2]);
	$('#mouldCode').val(arr[3]);
});

$('#btnSave').click(function(){
	if ($('#partName').val()==''){
		$('#partName').focus();
		return false;
	}
	if ($('#partNo').val()==''){
		$('#partNo').focus();
		return false;
	}
	if ($('#venderCode').val()==''){
		$('#venderCode').focus();
		return false;
	}
	if ($('#versionNo').val()==''){
		$('#versionNo').focus();
		return false;
	}
	if ($('#mouldCode').val()==''){
		$('#mouldCode').focus();
		return false;
	}
	
	var param = {
		'partName': $('#partName').val(),
		'partNo': $('#partNo').val(),
		'venderCode': $('#venderCode').val(),
		'versionNo': $('#versionNo').val(),
		'mouldCode': $('#mouldCode').val()
	}
	if (id!=''){
		param['id'] = id;
	}
	
	//注意：后台采用 @RequestBody 接收，这里一定要指定 dataType 和 contentType 否则会报 415异常
	//http://www.cnblogs.com/quanyongan/archive/2013/04/16/3024741.html
	$.ajax({
		type : 'POST',
		url : $.kbase.ctx + '/sample/save',
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(param),
		success : function(data) {
			alert(data.partName + '保存成功');
		}
	});
});