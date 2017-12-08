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
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eastrobot.entity.Attach;
import com.eastrobot.service.FileService;
import com.eastrobot.util.MetadataUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月13日 下午5:23:55
 * @version 1.0
 */
@Controller
@RequestMapping("attach")
public class AttachController {

	@Resource
	private FileService fileService;
	
	@RequestMapping("upload")
	@ResponseBody
	public int upload(@RequestParam("file") MultipartFile file){
		try {
			fileService.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	@RequestMapping(value="get/{id}")
	@ResponseBody()
	public void get(@PathVariable String id, HttpServletResponse response) throws IOException{
		GridFSDBFile dbFile = fileService.findById(id);
		response.setContentType(dbFile.getContentType());

        OutputStream stream = response.getOutputStream();
        stream.write(IOUtils.toByteArray(dbFile.getInputStream()));
        stream.flush();
        stream.close();
	}
	
	@RequestMapping("loadList")
	@ResponseBody
	public String loadList() throws JSONException, IOException{
		JSONArray arr = new JSONArray();
		
		List<GridFSDBFile> list = fileService.findAll();
		if (list!=null){
			for (GridFSDBFile dbFile : list) {
				JSONObject json = new JSONObject();
				json.put("id", dbFile.getId());
				json.put("name", dbFile.getMetaData().get(FileService.FILE_NAME));
				json.put("size", dbFile.getLength());
				json.put("createDate", dbFile.getUploadDate().getTime());
				arr.put(json);
			}
		}
		return arr.toString();
	}
	
	@PostMapping("delete")
	@ResponseBody
	public int delete(@RequestParam("id") String id){
		fileService.deleteById(id);
		return 1;
	}
}
