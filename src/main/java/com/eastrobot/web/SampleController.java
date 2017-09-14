/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.web;

import javax.annotation.Resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eastrobot.entity.Sample;
import com.eastrobot.service.SampleService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 上午10:58:43
 * @version 1.0
 */
@Controller
@RequestMapping("sample")
@PreAuthorize("hasRole('ADMIN')")
public class SampleController {
	
	@Resource
	private SampleService sampleService;

	@RequestMapping("loadData")
	@ResponseBody
	public Sample loadData(String id){
		Sample sample = sampleService.findOne(id);
		return sample;
	}
	
	@PostMapping("save")
	@ResponseBody
	public Sample save(@RequestBody Sample sample){
		sampleService.save(sample);
		return sample;
	}
	
	@PostMapping("delete")
	@ResponseBody
	public void delete(String id){
		sampleService.delete(id);
	}
	
	@RequestMapping("loadList")
	@ResponseBody
	public Iterable<Sample> loadList(){
		return sampleService.findAll();
	}
}
