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
 * @date 2017年7月22日 上午10:55:04
 * @version 1.0
 */
@Document(collection="Code")
public @Data class Code {

	@Id
	private String id;
	//样本码主键
	private String sampleId;
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
	//日期码
	private String dateCode;
	//操作流水号
	private String serialNo;
	//扫描日期
	private Date scanDate = new Date();
	//状态 1-正常码 0-非正常码
	private int status;
	//摘要备注
	private String memo;
	//扫描码
	private String scanCode;
	
}
