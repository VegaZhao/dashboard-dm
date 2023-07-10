/**   
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 * 
 * @Package: com.doit.mall.conf 
 * @author: Herry   
 * @date: 2018年5月8日 上午11:27:09 
 * @Description: 解决跨域问题
 */
package com.cbrc.dashboard.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author Herry
 *
 */
@Configuration
@ConditionalOnProperty(name = "cors.enable", havingValue = "true")
public class CorsConfig {
	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*"); //允许任何域名
		corsConfiguration.addAllowedHeader(CorsConfiguration.ALL); //允许任何头
		corsConfiguration.addAllowedMethod(CorsConfiguration.ALL); //允许任何方法(get,post等)
		// This allow us to expose the headers
		corsConfiguration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
				"Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
		corsConfiguration.setAllowCredentials(true); //服务器同意接收Cookies
		corsConfiguration.setMaxAge((long) 1728000); //指定预检请求的有效期，若不设置，则每次请求会有两次Options检查
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}
}
