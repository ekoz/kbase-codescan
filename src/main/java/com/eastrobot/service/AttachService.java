/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.eastrobot.entity.Attach;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年9月13日 下午5:22:59
 * @version 1.0
 */
@Service
public interface AttachService extends MongoRepository<Attach, String>{

}
