# Spring Boot学习笔记

**核心理念：约定大于配置**

4大核心特性：

- 自动配置
- 起步依赖
- Actuator
- 命令行界面（CLl）

## 1.springboot 基础配置

### 1.1 核心注解：@SpringBootApplication

标注该类是项目的启动类，并且表明该项目为spring boot项目。

该注解 为组合注解，由以下3个注解组合而成：

| 注解名                  | 功能                                                         |
| ----------------------- | ------------------------------------------------------------ |
| SpringBootConfiguration | 标注为配置类，在该类中配置bean，功能上与spring中的applicationCentext.xml文件相似。 |
| EnableAutoConfiguration | 表示开启自动化配置，springboot中的自动化配置是非侵入式的。   |
| ComponentScan           | 完成包扫描功能。默认扫描当前类所在的包下的所有类。           |

## 2.springboot 整合视图层技术

### 2.1 整合Thymeleaf

**Thymelaef属于java模板引擎，支持HTML原型。spring boot提供了Thymeleaf自动化配置解决方案。**

整合过程：

**创建工程，添加项目依赖。**

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--添加模板引擎thymeleaf-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```

**配置thymeleaf：**

默认模板文件位于：classpath:/templates/下，默认模板文件后缀为.html。

若要对于thmeleaf进行自定义配置，可以在appliction.properties文件中进行配置。

```properties
#自定义配置thymeleaf，下列值为默认值
#是否开启缓存。
spring.thymeleaf.cache=true
#检查模板是否存在
spring.thymeleaf.check-template=true
#检查模板位置是否存在
spring.thymeleaf.check-template-location=true
#模板文件编码
spring.thymeleaf.encoding=UTF-8
#模板文件位置
spring.thymeleaf.prefix=classpath:/templates/
#Centent-Type配置
spring.thymeleaf.servlet.content-type=text/html
#模板文件后缀
spring.thymeleaf.suffix=.html
```

**配置控制器**：

创建实体类，接着创建控制器，最后创建html模板。

详细thymealeaf用法参见官网：http://www.thymeleaf.org

**其他模板引擎（例如freemarker等）整合流程类似。**

如果采用前后端分离技术，则不需要整合视图层技术。

## 3.spring boot整合web开发

### 3.1 JSON数据传输

JSON数据格式是现在主流的前后端数据传输方式。

**在Spring MVC中使用消息转换器HttpMessageConverter对JSON的转换提供支持。**

在spring boot中添加web依赖，该依赖中默认加入了**jackson-databind**作为JSON处理器

应用实例参见代码

#### 3.1.1自定义JSON转换器

**Gson**

Gson是谷歌的开源JSON解析框架，在使用Gson时需要去掉web依赖中的默认的

jackson-databind处理器，在添加Gson依赖。

```xml
 <!--除去jackson-databind-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <artifactId>com.fasterxml.jackson.core</artifactId>
                <groupId>jackson-databind</groupId>
            </exclusion>
        </exclusions>
        </dependency>
        <!--添加谷歌的JSON处理框架JSON-->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
        </dependency>
```

spring boot默认提供GsonHttpMessageConvertersConfiguration，所以依赖添加成功后，可以直接使用。

但是Gson中没有自定义设置日期格式功能，所有要对日期进行格式化，需要自定义HttmMesssageConverter。

实例参见代码。

**fastjson**

阿里的开源JSON解析框架，目前JSON解析速度最快的框架。

### 3.2 静态资源访问

**默认策略**

spring boot 对于Spring MVC的自动化配置在**WebMvcAutoConfiguration**类中

一般静态资源位于/resources/static目录下。

### 3.3 文件上传

在spring boot 中提供文件上传自动化配置类：MultipartAutoConfiguration默认采用StandardServletMultipartResolver组件实现文件上传。

**单文件上传**

操作步骤：

首先创建文件上传页面：upload.html

接着创建文件上传处理接口

```java
/**
 * 文件上传处理控制器
 */
@RestController
public class FileUploadController {
    //设置日期格式
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
    @PostMapping("/upload")
    public String upload(MultipartFile multipartFile, HttpServletRequest request){
        //设置上传文件保存路径
        String realPath=request.getSession().getServletContext().getRealPath("/uploadfile");
        //获取当前系统时间
        String format=simpleDateFormat.format(new Date());
        File file=new File(realPath+format);
        //通过上传日期对上传文件进行分类保存
        if(file.isDirectory()){
            file.mkdirs();
        }
        String oldName=multipartFile.getOriginalFilename();
        //采用随机数方式给上传文件命名，防止文件重名
        String newName= UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            //保存文件
            multipartFile.transferTo(new File(file,newName));
            //设置上传文件的访问路径
            String filepath=request.getScheme()+"://"+request.getServerName()+":"+
                    request.getServerPort()+"/uploadfile/"+format+newName;
            return  filepath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";


    }
}
```

### 3.4@ControllerAdvice

该注解主要应用于处理全局数据。

**实例一：处理全局异常。**

通过@ControllerAdvice结合@ExceptionHandler定义全局异常捕获机制。

```java
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void uploadException(MaxUploadSizeExceededException e, HttpServletResponse resp)
        throws IOException{
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out=resp.getWriter();
        ///日志输出
        out.write("上传文件大小超出限制");
        out.flush();
        out.close();
        
    }
}
```

**实例二：添加全局数据**

@ControllerAdvice是一个全局数据处理组件，在该注解中使用@ModelAttribute配置。

```java
/**
 * 全局数据配置实例
 * 任意Controller请求userInfo方法参数Model都可以获得info中的数据
 */
@ControllerAdvice
public class GlobalConfig {
    @ModelAttribute(value = "info")
    public Map<String,String> userInfo(){
        HashMap<String,String> map=new HashMap<>();
        map.put("username","罗贯中");
        map.put("gender","男");
        return map;
    }
    
}
```

**实例三：请求参数预处理**

@ControllerAdvice结合@InitBinde实现参数预处理（表单中的数据绑定到实体之前的一些额外处理）功能。

应用实例：

```java
/**
 * 请求参数预处理
 * 2个实体类的name属性名相同时，参数传递会混淆，采用@ControllerAdvice结合@InitBinder解决该问题。
 */
public class CanshuBookController {
    @GetMapping("/canshubook")
    @ResponseBody
    public String book(@ModelAttribute("b") Book book, @ModelAttribute("a") Author author){
        return book.toString()+">>>>"+author.toString();
    }
}


   // @InitBinder("b")处理Controller中@ModelAttribute("b")对应的参数
    //在WebDataBinder中还可以设置允许的字段，禁止的字段，必填字段和验证器等。
    @InitBinder("b")
    public void init(WebDataBinder binder){
        binder.setFieldDefaultPrefix("b.");
    }
    @InitBinder("a")
    public void init1(WebDataBinder binder){
        binder.setFieldDefaultPrefix("a.");
    }

```

