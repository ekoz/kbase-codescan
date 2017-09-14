/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.util;
/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月14日 上午11:18:26
 * @version 1.0
 */
public class MetadataUtils {

	/**
	 * 根据文件格式获取返回的 contentType
	 * @author eko.zhan at 2017年9月14日 上午11:26:32
	 * @param extension
	 * @return
	 */
	public static String getContentType(String extension){
		extension = extension.toLowerCase();
		if ("ico".equals(extension)){
			return "image/x-icon";
		}else if ("jpg".equals(extension) || "jpeg".equals(extension)
				|| "png".equals(extension) || "gif".equals(extension)){
			return "image/" + extension;
		}else if ("ppt".equals(extension)){
			return "application/vnd.ms-powerpoint";
		}else if ("pptx".equals(extension)){
			return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		}else if ("doc".equals(extension)){
			return "application/msword";
		}else if ("docx".equals(extension)){
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		}else if ("xls".equals(extension)){
			return "application/vnd.ms-excel";
		}else if ("xlsx".equals(extension)){
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}else if ("pdf".equals(extension)){
			return "application/pdf";
		}else if ("htm".equals(extension) || "html".equals(extension)){
			return "text/html";
		}
		return "";
	}
}
