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

