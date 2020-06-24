package com.mzl0101.config.file;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;


@SuppressWarnings("deprecation")
@Configuration
public class UploadFileProperties extends WebMvcConfigurerAdapter {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        factory.setMaxFileSize(DataSize.parse("128MB")); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("256MB"));
        //设置文件路径
        //factory.setLocation("");
        return factory.createMultipartConfig();
    }

}