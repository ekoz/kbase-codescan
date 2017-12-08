/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.gridfs.GridFSDBFile;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月8日 下午1:59:36
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class FileServiceTests {

	@Resource
	private FileService fileService;
	
	@Test
	public void testFindAll(){
		List<GridFSDBFile> list = fileService.findAll();
		System.out.println(list.size());
	}
	
	@Test
	public void testFindByName(){
		GridFSDBFile dbFile = fileService.findByName("1512702620166.docx");
		System.out.println(dbFile);
		System.out.println(dbFile.getMetaData().get(FileService.FILE_NAME));
	}
	
	@Test
	public void testFindByFileName(){
		List<GridFSDBFile> dbFileList = fileService.findByFileName("北京");
		System.out.println(dbFileList);
	}
	
	@Test
	public void testFindById(){
		GridFSDBFile dbFile = fileService.findById("5a2a029c8e4f0f20b0813e0f");
		System.out.println(dbFile);
	}
	
	@Test
	public void testDeleteById(){
		fileService.deleteById("5a2a029c8e4f0f20b0813e0f");
	}
}
