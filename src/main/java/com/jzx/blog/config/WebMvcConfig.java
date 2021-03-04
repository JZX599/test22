package com.jzx.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/*
 *初始化视图控制器
 * 问题：工程运行时默认进行加载static文件夹下的index.html
 * 解决方案：添加初始化视图接口配置
 *配置addViewControllers视图跳转控制器的跳转路径
 *
 * */

@Configuration //配置类必须加此注解
public class WebMvcConfig implements WebMvcConfigurer {

    /*
    *
    * 2、视图跳转控制器
    *
    *
    * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //设置初始化默认路径为  工程名 /login
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/login").setViewName("login");
    }

    /*
    * 7、解决跨域问题
    *
    * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                .maxAge(3600);
    }

    /*
    * 3、静态资源处理
    *
    * 解决服务器上传文件后重启项目或者多次刷新才显示图片
    *
    *addResourceHandler后面加相对路径     （项目文件资源的服务器相对路径）
    *addResourceLocations后面加绝对路径   （项目文件资源的本地绝对路径）
    * */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/ArtUpload/**").addResourceLocations("file:D:\\IdeaProjects\\blog\\src\\main\\resources\\static\\files\\ArtUpload\\");
    }
}



/*
 *WebMvcConfigurer类   常用配置如下：
 *
 *1、拦截器配置
 *  void addInterceptors(InterceptorRegistry var1);
 *2、视图跳转控制器
 *  void addViewControllers(ViewControllerRegistry registry);
 *3、静态资源处理
 *  void addResourceHandlers(ResourceHandlerRegistry registry);
 *4、默认静态资源处理器
 *  void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);
 *5、这里配置视图解析器
 *  void configureViewResolvers(ViewResolverRegistry registry);
 *6、配置内容裁决的一些选项
 *  void configureContentNegotiation(ContentNegotiationConfigurer configurer);
 *7、解决跨域问题
 *  public void addCorsMappings(CorsRegistry registry) ;
 *
 **/
