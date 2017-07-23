/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 上午10:51:53
 * @version 1.0
 */
@Document(collection="Sample")
public @Data class Sample {

	@Id
	private String id;
	//料号
	private String partNo;
	//料号名称
	private String partName;
	//版本号
	private String versionNo;
	//厂商代码
	private String venderCode;
	//模具号
	private String mouldCode;
	
}
