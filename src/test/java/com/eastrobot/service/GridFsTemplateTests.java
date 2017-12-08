/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月8日 上午10:07:44
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class GridFsTemplateTests {

	@javax.annotation.Resource
	private GridFsTemplate gridFsTemplate;
	
	@Test
	public void testStore1() throws IOException{
		MultipartFile file = null;
		DBObject attachment = new BasicDBObject();
		
		gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType());
	}
	
	@Test
	public void testStore() throws IOException{
		
		Resource file = new FileSystemResource("E:/ConvertTester/TestFiles/晚安北京.docx");
		String extension = FilenameUtils.getExtension(file.getFile().getName());
		String fileRealName = Calendar.getInstance().getTimeInMillis() + "." + extension;
		DBObject metaData = new BasicDBObject();
		metaData.put("fileName", file.getFilename());
		metaData.put("extension", extension);
		System.out.println(fileRealName);
		gridFsTemplate.store(file.getInputStream(), fileRealName, new MimetypesFileTypeMap().getContentType(file.getFile()), metaData);
	}
	
	@Test
	public void testFindByFilename(){
		Query query = new Query();
		GridFsCriteria criteria = GridFsCriteria.whereFilename();
		criteria.is("1512702620166.docx");
		query.addCriteria(criteria);
		GridFSDBFile dbFile = gridFsTemplate.findOne(query);
		System.out.println(dbFile.getFilename());
		System.out.println(dbFile.getContentType());
		System.out.println(dbFile.getLength());
		System.out.println(dbFile.getUploadDate());
		DBObject metaData = dbFile.getMetaData();
		System.out.println(metaData.get("fileName"));
	}
	
	@Test
	public void testFindByDBObject(){
		Criteria criteria = GridFsCriteria.whereMetaData("fileName").regex(".*?\\晚安.*");
		Query query = new Query(criteria);
		
		Order order = new Order(Sort.Direction.DESC, "uploadDate");
	    Sort sort = new Sort(order);
	    query.with(sort);
		List<GridFSDBFile> list = gridFsTemplate.find(query);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindAll() throws IOException{
		GridFsResource[] resources = gridFsTemplate.getResources("*");
		System.out.println("文件总数：" + resources.length);
		for (GridFsResource resource : resources){
			System.out.println(resource.getFilename());
			System.out.println(resource.getContentType());
			System.out.println(resource.contentLength());
			System.out.println(resource.lastModified());
		}
	}
	
	@Test
	public void testDelete(){
		Criteria criteria = GridFsCriteria.whereFilename().regex(".*?\\晚安.*");
		Query query = new Query(criteria);
		gridFsTemplate.delete(query);
	}
}
