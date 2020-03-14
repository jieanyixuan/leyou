package com.leyou.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 王俊杰
 * 跨域访问的配置类
 */

@Configuration
public class LeyouCorsConfiguration {

    /**
     * cors跨域的过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {

        //初始化配置对象
        CorsConfiguration config = new CorsConfiguration ();
        //设置允许跨域携带cookie
        config.setAllowCredentials (true);
        //设置允许哪些域可以跨域访问,要想携带cookie就不能写*
        config.addAllowedOrigin ("http://manage.leyou.com");
        //允许请求的方式
        config.addAllowedMethod ("*"); //*表示允许所有,如GET POST PUT DELETE
        //允许携带哪些头信息
        config.addAllowedHeader ("*");


        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource ();
        //添加映射路径(拦截路径, cors配置对象); /**表示拦截一切路径
        corsConfigurationSource.registerCorsConfiguration ("/**", config);

        return new CorsFilter (corsConfigurationSource);
    }
}
