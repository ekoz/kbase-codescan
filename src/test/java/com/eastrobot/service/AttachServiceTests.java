/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月8日 下午2:53:09
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class AttachServiceTests {

	@Resource
	private AttachService attachService;
	
	@Test
	public void delete(){
		attachService.deleteAll();
	}
}
