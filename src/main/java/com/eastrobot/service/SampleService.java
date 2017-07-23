/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.eastrobot.entity.Sample;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 下午12:51:55
 * @version 1.0
 */
@Service
public interface SampleService extends CrudRepository<Sample, Serializable>{

	
}
