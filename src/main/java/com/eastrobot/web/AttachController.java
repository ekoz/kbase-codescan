/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eastrobot.entity.Attach;
import com.eastrobot.service.AttachService;
import com.eastrobot.util.MetadataUtils;
import com.sun.xml.internal.ws.util.MetadataUtil;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月13日 下午5:23:55
 * @version 1.0
 */
@Controller
@RequestMapping("attach")
public class AttachController {

	@Resource
	private AttachService attachService;
	
	@RequestMapping("upload")
	@ResponseBody
	public int upload(@RequestParam("file") MultipartFile file){
		try {
			Attach att = new Attach();
			att.setName(file.getOriginalFilename());
			att.setSize(file.getSize());
			att.setContent(file.getBytes());
			attachService.save(att);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	@RequestMapping(value="get/{id}")
	@ResponseBody()
	public void get(@PathVariable String id, HttpServletResponse response) throws IOException{
		Attach attach = attachService.findOne(id);
		String name = attach.getName();
		String extension = FilenameUtils.getExtension(name);
		
		response.setContentType(MetadataUtils.getContentType(extension));

        OutputStream stream = response.getOutputStream();
        stream.write(attach.getContent());
        stream.flush();
        stream.close();
	}
	
	@RequestMapping("loadList")
	@ResponseBody
	public List<Attach> loadList(){
		List<Attach> list = attachService.findAll();
		return list;
	}
	
	@PostMapping("delete")
	@ResponseBody
	public int delete(@RequestParam("id") String id){
		attachService.delete(id);
		return 1;
	}
}
