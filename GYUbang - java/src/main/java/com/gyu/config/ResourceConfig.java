package com.gyu.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

///**没有生效的静态资源放行/
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler("/**")静态资源处理器，让框架知道resources文件夹是静态资源目录
        // addResourceLocations("classpath:/static/") 开放的资源
        registry.addResourceHandler("/**").addResourceLocations("file:D:\\development\\毕业设计\\GYUbang - java\\src\\main\\resources\\static\\lostArticle");
    }
}


