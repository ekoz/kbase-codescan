/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.entity;

import java.util.Date;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月13日 下午5:34:37
 * @version 1.0
 */
@Document(collection="Attach")
public @Data class Attach {
	@Id
	private String id;
	private String name;
	private long size;
	private Date createDate = new Date();
	private byte[] content;
}
