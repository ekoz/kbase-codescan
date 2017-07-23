/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eastrobot.entity.Code;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 下午3:11:57
 * @version 1.0
 */
public interface CodeService extends CrudRepository<Code, Serializable>{

	public List<Code> findByScanCode(String scanCode);

}
