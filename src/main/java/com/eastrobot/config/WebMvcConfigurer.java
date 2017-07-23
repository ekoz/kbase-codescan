/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年7月22日 下午2:14:09
 * @version 1.0
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController( "/" ).setViewName("redirect:/code/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
        
        registry.addViewController("/code").setViewName("code/list");
        registry.addViewController("/code/index").setViewName("code/index");
        registry.addViewController("/sample").setViewName("sample/list");
        registry.addViewController("/sample/create").setViewName("sample/edit");
        
		super.addViewControllers(registry);
	}
}
