/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月7日 下午8:06:10
 * @version 1.0
 */
@Service
public class FileService {

	public final static String FILE_NAME = "fileName";
	@Autowired
	private GridFsTemplate gridFsTemplate;
	/**
	 * 
	 * @author eko.zhan at 2017年12月8日 下午2:46:37
	 * @param file
	 * @throws IOException 
	 */
	public GridFSFile save(MultipartFile file) throws IOException{
		return save(file, new BasicDBObject());
	}
	/**
	 * 保存文件
	 * @author eko.zhan at 2017年12月8日 下午1:53:48
	 * @param file
	 * @param metadata
	 * @return
	 * @throws IOException
	 */
	public GridFSFile save(MultipartFile file, DBObject metadata) throws IOException{
		//gridFsTemplate.store(file.getInputStream(), fileRealName, new MimetypesFileTypeMap().getContentType(file.getFile()), metaData);
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileRealName = Calendar.getInstance().getTimeInMillis() + "." + extension;
		metadata.put(FILE_NAME, file.getOriginalFilename());
		return gridFsTemplate.store(file.getInputStream(), fileRealName, file.getContentType(), metadata);
	}
	/**
	 * 根据文件名存储名查找文件
	 * @author eko.zhan at 2017年12月8日 下午1:53:54
	 * @param name
	 * @return
	 */
	public GridFSDBFile findByName(String name){
		Assert.hasText(name, "文件名称不能为空");
		
		Query query = new Query();
		GridFsCriteria criteria = GridFsCriteria.whereFilename();
		criteria.is(name);
		query.addCriteria(criteria);
		return gridFsTemplate.findOne(query);
	}
	/**
	 * 
	 * @author eko.zhan at 2017年12月8日 下午1:59:03
	 * @param fileName
	 * @return
	 */
	public List<GridFSDBFile> findByFileName(String fileName){
		Assert.hasText(fileName, "文件名称关键字不能为空");
		
		Criteria criteria = GridFsCriteria.whereMetaData("fileName").regex(".*?\\" + fileName + ".*");
		Query query = new Query(criteria);
		
		Order order = new Order(Sort.Direction.DESC, "uploadDate");
	    Sort sort = new Sort(order);
	    query.with(sort);
	    
		return gridFsTemplate.find(query);
	}
	/**
	 * 获取所有文件
	 * @author eko.zhan at 2017年12月8日 下午2:01:03
	 * @return
	 */
	public List<GridFSDBFile> findAll(){
//		GridFsResource[] resources = gridFsTemplate.getResources("*");
//		return resources;
		return gridFsTemplate.find(null);
	}
	/**
	 * 根据主键获取 GridFSDBFile 对象
	 * @author eko.zhan at 2017年12月8日 下午2:27:05
	 * @param id
	 * @return
	 */
	public GridFSDBFile findById(String id){
		Query query = new BasicQuery(new BasicDBObject("_id", id));
		return gridFsTemplate.findOne(query);
	}
	/**
	 * 根据主键删除文件
	 * @author eko.zhan at 2017年12月8日 下午2:35:48
	 * @param id
	 */
	public void deleteById(String id){
		Query query = new BasicQuery(new BasicDBObject("_id", id));
		gridFsTemplate.delete(query);
	}
}
