window.__scan_total = 0;
window.__scan_success = 0;
window.__scan_error = 0;
window.__scan_status = 'off'; //on off 
window.__scan_canEnter = true;

//日期初始化
$('#dateCode').datepicker({
	format: 'yyyy-mm-dd',
	language: 'zh-CN'
}).val(dateFns.format(new Date(), 'YYYY-MM-DD'));

//填充料号数据
$.post($.kbase.ctx + '/code/loadSampleList', function(data){
	//$('#list').append(template('templateList', data));
	$(data).each(function(i, item){
		$('#partName').append('<option value="' + item.id + '">' + item.partName + '</option>')
	});
}, 'json');

//料号切换 根据日期和料号获取 codeValue
$('#partName').on('change', function(){
	var partName = $('#partName option:selected').val();
	var dateCode = $('#dateCode').val();
	if (partName==''){
		$('#codeValue').val('');
	}else{
		$.post($.kbase.ctx + '/code/getCodeValue', {id: partName, dateCode: dateCode}, function(data){
			$('#codeValue').val(data);
		}, 'text');
	}
});

//确认知晓
$('#btnIknow').click(function(){
	window.__scan_canEnter = true;
	$('#codesArea').val('');
	//停止声音播放
	$('#alarm')[0].pause();
	window.clearInterval(window.__kbs_timer);
	document.body.style.backgroundColor = "#FFFFFF";
	window.setTimeout(function(){
		$('#codesArea').focus();
	}, 100);
	layer.close(window.__kbs_layer_index);
});

//开始扫描
$('#btnScan').click(function(){
	if ($('#codeValue').val()==''){
		$('#codeValue').focus();
		return false;
	}
	window.__scan_status = 'on';
	$('#codesArea').focus();
	
	$('#btnScan').attr('disabled', 'disabled');
	$('#btnStopScan').removeAttr('disabled');
});

//停止扫描
$('#btnStopScan').click(function(){
	window.__scan_status = 'off';
	$('#btnStopScan').attr('disabled', 'disabled');
	$('#btnScan').removeAttr('disabled');
	
	$('#codesArea').val('');
});

$('#codesArea').on({
	blur: function(){
		if (window.__scan_status=='on'){
			window.setTimeout(function(){
				$('#codesArea').focus();
			}, 100);
		}
	},
	keydown: function(event){
		if (window.__scan_status=='on'){
			if (event.keyCode==13){
				event.cancelBubble = true;
				event.preventDefault();
				event.stopPropagation();
				
				if (window.__scan_canEnter){
					var dateCode = $('#dateCode').val();
					var codesArea = $.trim($('#codesArea').val());
					var codeValue = $('#codeValue').val();
					var partName = $('#partName').find("option:selected").text();
					
					if (codeValue==''){
						$('#codeValue').focus();
						return false;
					}
					if (codesArea==''){
						$('#codesArea').focus();
						return false;
					}
					
					window.__scan_canEnter = false;
					
					var _data = {"dateCode": dateCode, "codesArea": codesArea, "codeValue": codeValue, "partName": partName};
					
					window.__scan_total++;
					
					$.ajax({
						async: false,
						type: 'POST',
						url: $.kbase.ctx + '/code/check',
						data: _data,
						timeout: 5 * 1000,
						dataType: 'text',
						success: function(errlog){
							var data = {
								errlog : errlog
							}
							var _issuccess = 0;
							if (data.errlog==''){
								_issuccess = 1;
							}
							if (_issuccess==1){
								window.__scan_canEnter = true;
								$('#codesArea').val('');
								//条形码ok
								var _lines = $('#rightcodes').html();
								_lineArr = _lines.split('<br/>');
								//当前页显示最多扫描码条数
								if (_lineArr.length>20){
									$('#rightcodes').html(_lines.substring(_lines.indexOf('<br/>')+4));
								}
								$('#rightcodes').append(codesArea+'<br/>');
								window.__scan_success++;
							}else{
								//条形码错误
								$('#errorcodes').append(codesArea+'<br/>');
								$('#errmsg').html(data.errlog);
								window.__scan_error++;
								window.__kbs_layer_index = layer.open({
									title: '错误信息',
								    type: 1,
								    closeBtn: false, //不显示关闭按钮
								    shift: 2,
								    shadeClose: false, //开启遮罩关闭
								    area: ['420px', '300px'], //宽高
								    content: $('#iknow')
								});
								
								//启动声音播放
								$('#alarm')[0].play();
								//切换背景色
								var i=0;
								window.__kbs_timer = window.setInterval(function(){
									if (i%2==0){
										document.body.style.backgroundColor = "#FF0000";
									}else{
										document.body.style.backgroundColor = "#FFFFFF";
									}
									i++;
								}, 100);
							}
						},
						error: function(){
							//条形码错误
							$('#errorcodes').append('网络连接失败<br/>');
							$('#errmsg').html('网络连接失败');
							
							window.__kbs_layer_index = layer.open({
								title: '错误信息',
							    type: 1,
							    closeBtn: false, //不显示关闭按钮
							    shift: 2,
							    shadeClose: false, //开启遮罩关闭
							    area: ['420px', '300px'], //宽高
							    content: $('#iknow')
							});
							
							//启动声音播放
							$('#alarm')[0].play();
							//切换背景色
							var i=0;
							window.__kbs_timer = window.setInterval(function(){
								if (i%2==0){
									document.body.style.backgroundColor = "#FF0000";
								}else{
									document.body.style.backgroundColor = "#FFFFFF";
								}
								i++;
							}, 100);
						}
					});	//end $.ajax
					
					//刷新数字
					$('#scanTotal').text(window.__scan_total);
					$('#scanSuccess').text(window.__scan_success);
					$('#scanError').text(window.__scan_error);
					
				} //end window.__scan_canEnter
			}
		}
	}
});