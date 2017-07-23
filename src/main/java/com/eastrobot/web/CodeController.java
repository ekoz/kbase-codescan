/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eastrobot.entity.Code;
import com.eastrobot.entity.Sample;
import com.eastrobot.service.CodeService;
import com.eastrobot.service.SampleService;
import com.eastrobot.util.CodeUtils;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 上午10:58:43
 * @version 1.0
 */
@Controller
@RequestMapping("code")
public class CodeController {
	
	@Resource
	private SampleService sampleService;
	
	@Resource
	private CodeService codeService;

	@RequestMapping("loadList")
	@ResponseBody
	public Iterable<Code> loadList(){
		 return codeService.findAll();
	}
	
	/**
	 * 根据选择的料号获取样本码
	 * @author eko.zhan at 2017年7月23日 下午7:00:58
	 * @param id
	 * @param dateCode
	 * @return
	 */
	@RequestMapping("getCodeValue")
	@ResponseBody
	public String getCodeValue(@RequestParam String id, @RequestParam String dateCode){
		Sample sample = sampleService.findOne(id);
		return sample.getPartNo() + sample.getVenderCode() + sample.getVersionNo() + CodeUtils.formatDateCode(dateCode) + sample.getMouldCode();
	}
	
	/**
	 * 获取料号数据时用到，应该只用返回 partName/sampleId即可
	 * @author eko.zhan at 2017年7月23日 下午7:00:42
	 * @return
	 */
	@RequestMapping("loadSampleList")
	@ResponseBody
	public Iterable<Sample> loadSampleList(){
		return sampleService.findAll();
	}
	
	/**
	 * 通过扫描码解析出相应的日期码，流水号
	 * @author eko.zhan at 2017年7月22日 下午4:50:08
	 * @param partName 料号名称
	 * @param dateCode 日期 yyyy-MM-dd
	 * @param codeValue 样本码
	 * @param codesArea 扫描码
	 */
	@RequestMapping("check")
	@ResponseBody
	public String check(@RequestParam String partName, @RequestParam String dateCode, @RequestParam String codeValue, @RequestParam String codesArea){
		//用来记录扫描日志
		StringBuffer logBuffer = new StringBuffer();
		StringBuffer errBuffer = new StringBuffer();
		StringBuffer errCodeBuffer = new StringBuffer();
		
		if (codesArea.length()!=27){
			errBuffer.append("扫描码不是27位 ");
		}else if (!(codesArea).equals((codesArea).toUpperCase())){
			errBuffer.append("扫描码必须大写 ");
		}
		
		//拆分样本码
		String sPartNo = CodeUtils.getPartNo(codeValue);
		String sVenderCode = CodeUtils.getVenderCode(codeValue);
		String sVersionNo = CodeUtils.getVersionNo(codeValue);
		String sDateCode = CodeUtils.getDateCode(codeValue);
		String sMouldCode = CodeUtils.getMouldCode(codeValue);
		
		//拆分扫描码
		String iPartNo = CodeUtils.getPartNo(codesArea);
		String iVenderCode = CodeUtils.getVenderCode(codesArea);
		String iVersionNo = CodeUtils.getVersionNo(codesArea);
		String iDateCode = CodeUtils.getDateCode(codesArea);
		String iMouldCode = CodeUtils.getMouldCode(codesArea);
		String iSerialNo = CodeUtils.getSerialNo(codesArea);
		
		if (!sPartNo.equals(sPartNo)){
			errBuffer.append("料号错误 ");
			errCodeBuffer.append("<font color='#FF0000'>"+iPartNo+"</font> ");
		}else{
			errCodeBuffer.append(iPartNo + " ");
		}
		
		if (!sVenderCode.equals(iVenderCode)){
			errBuffer.append("厂商代码错误 ");
			errCodeBuffer.append("<font color='#FF0000'>"+iVenderCode+"</font> ");
		}else{
			errCodeBuffer.append(iVenderCode + " ");
		}
		if (!sVersionNo.equals(iVersionNo)){
			errBuffer.append("版本错误 ");
			errCodeBuffer.append("<font color='#FF0000'>"+iVersionNo+"</font> ");
		}else{
			errCodeBuffer.append(iVersionNo + " ");
		}
		if (!sDateCode.equals(iDateCode)){
			errBuffer.append("日期码错误 ");
			errCodeBuffer.append("<font color='#FF0000'>"+iDateCode+"</font> ");
		}else{
			errCodeBuffer.append(iDateCode + " ");
		}
		if (!sMouldCode.equals(iMouldCode)){
			errBuffer.append("模具码错误 ");
			errCodeBuffer.append("<font color='#FF0000'>"+iMouldCode+"</font> ");
		}else{
			errCodeBuffer.append(iMouldCode + " ");
		}
		
		//判断流水号是否是16进制
		String serialerr = "";
		try{
			int n = Integer.parseInt(iSerialNo, 16);
			if (n<1){
				serialerr = "流水号错误";
			}
		}catch (Exception e) {
			serialerr = "流水号错误";
		}
		//判断流水号是否重复
		List<Code> list = codeService.findByScanCode(codesArea);
		if (serialerr.length()==0 && list.size()>0) {
			serialerr = "流水号重复";
		}
		//记录流水号错误日志
		if (serialerr.length()==0){
			errCodeBuffer.append(iSerialNo + " ");
		}else{
			errBuffer.append(serialerr + " ");
			errCodeBuffer.append("<font color='#FF0000'>"+iSerialNo+"</font> ");
		}
		
		String errlog = "";
		if (errBuffer.length()>0){
			errlog = errBuffer.toString() + "<br>" + "样本码：<br>" + sPartNo + " " + iVenderCode + " " + sVersionNo + " " + sDateCode + " " + sMouldCode + " " + iSerialNo + "<br>扫描码：<br>" + errCodeBuffer.toString();
		}
		
		//记录日志
		Code code = new Code();
		code.setPartNo(iPartNo);
		code.setPartName(partName);
		code.setVenderCode(iVenderCode);
		code.setVersionNo(iVersionNo);
		code.setDateCode(iDateCode);
		code.setMouldCode(iMouldCode);
		code.setSerialNo(iSerialNo);
		code.setScanCode(codesArea);
		code.setMemo(errlog);
		if (errlog.length()==0){
			code.setStatus(1);
		}else{
			code.setStatus(0);
		}
		codeService.save(code);
		
		return errlog;
	}
}
