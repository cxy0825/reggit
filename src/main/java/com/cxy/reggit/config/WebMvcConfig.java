package com.cxy.reggit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
  /** 设置静态资源映射 */
  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    log.info("开始静态资源映射");
    registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
    registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
  }
  /**
   * 拓展mvc框架的消息转换器
   *
   * @param: converters
   */
  //  @Override
  //  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
  //    // 创建消息转换器
  //    MappingJackson2HttpMessageConverter messageConverter =
  //        new MappingJackson2HttpMessageConverter();
  //    // 设置对象转换器，使用jackson将java对象转换成json
  //    messageConverter.setObjectMapper(new JacksonObjectMapper());
  //    // 把上面的消息转换器对象最佳到mvc框架的转换器中
  //    converters.add(0, messageConverter);
  //  }

  //  拓展mvc框架的消息转换器
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule simpleModule = new SimpleModule();
    // 把Long类型转成string字符串
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    objectMapper.registerModule(simpleModule);
    jackson2HttpMessageConverter.setObjectMapper(objectMapper);
    // 坑2
    // converters.add(jackson2HttpMessageConverter);
    converters.add(0, jackson2HttpMessageConverter);
  }
}
