#MyOauth

![image_text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200106131330689-2043178712.png)

---
Spring Security 常用过滤器介绍
~~~ JAVA
> 1. org.springframework.security.web.context.SecurityContextPersistenceFilter
    首当其冲的一个过滤器，作用之重要，自不必多言。
    SecurityContextPersistenceFilter主要是使用SecurityContextRepository在session中保存或更新一个SecurityContext，并将SecurityContext给以后的过滤器使用，来为后续filter建立所需的上下文。
    SecurityContext中存储了当前用户的认证以及权限信息。

> 2. org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter
    此过滤器用于集成SecurityContext到Spring异步执行机制中的WebAsyncManager

> 3 . org.springframework.security.web.header.HeaderWriterFilter
    向请求的Header中添加相应的信息,可在http标签内部使用security:headers来控制

> 4 . org.springframework.security.web.csrf.CsrfFilter
    csrf又称跨域请求伪造，SpringSecurity会对所有post请求验证是否包含系统生成的csrf的token信息，如果不包含，则报错。起到防止csrf攻击的效果。

> 5. org.springframework.security.web.authentication.logout.LogoutFilter
    匹配 URL为/logout的请求，实现用户退出,清除认证信息。

> 6 . org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
    认证操作全靠这个过滤器，默认匹配URL为/login且必须为POST请求。

> 7 . org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
    如果没有在配置文件中指定认证页面，则由该过滤器生成一个默认认证页面。

> 8 . org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter
    由此过滤器可以生产一个默认的退出登录页面

> 9 . org.springframework.security.web.authentication.www.BasicAuthenticationFilter
    此过滤器会自动解析HTTP请求中头部名字为Authentication，且以Basic开头的头信息。

> 10 . org.springframework.security.web.savedrequest.RequestCacheAwareFilter
    通过HttpSessionRequestCache内部维护了一个RequestCache，用于缓存HttpServletRequest

> 11 . org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
    针对ServletRequest进行了一次包装，使得request具有更加丰富的API

> 12 . org.springframework.security.web.authentication.AnonymousAuthenticationFilter
    当SecurityContextHolder中认证信息为空,则会创建一个匿名用户存入到SecurityContextHolder中。
    spring security为了兼容未登录的访问，也走了一套认证流程，只不过是一个匿名的身份。

> 13 . org.springframework.security.web.session.SessionManagementFilter
    SecurityContextRepository限制同一用户开启多个会话的数量

> 14 . org.springframework.security.web.access.ExceptionTranslationFilter
    异常转换过滤器位于整个springSecurityFilterChain的后方，用来转换整个链路中出现的异常

> 15 . org.springframework.security.web.access.intercept.FilterSecurityInterceptor
    获取所配置资源访问的授权信息，根据SecurityContextHolder中存储的用户信息来决定其是否有权限。
~~~
---
# 授权操作
##### 1. securedEnabled securedEnabled是SpringSecurity提供的注解
~~~
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Secured({"ROLE_PRODUCT","ROLE_ADMIN"}) 
~~~

##### 2. jsr250注解
~~~
@EnableGlobalMethodSecurity(securedEnabled = true)
@RolesAllowed({"ROLE_PRODUCT", "ROLE_ADMIN"}) //jsr250注解
~~~

##### 3. prePostEnabled
~~~
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PreAuthorize("hasAnyAuthority('ROLE_PRODUCT','ROLE_ADMIN')")
~~~

---
## 1. 表单登录 ##
~~~
http.formLogin().and().authorizeRequests().anyRequest().authenticated(); 
~~~
##2. basic登录
~~~
http.httpBasic().and().authorizeRequests().anyRequest().authenticated();
~~~

##3. 自定义登录
~~~
http.formLogin().loginPage("/auth/require")// 设置登录路由
    .loginProcessingUrl("/login") // 登录处理url
    .successHandler(null) // 登录成功处理
    .failureHandler(null) // 登录失败处理

    .and().authorizeRequests() // 身份认证设置
    .antMatchers("/login.html").permitAll()// 匹配 login.html 不需要认证
    .antMatchers("/auth/*").permitAll() // 匹配/auth/*  不需要认证
    .anyRequest().authenticated()// 其他需要认证

    .and().csrf().disable();// 禁用跨脚本攻击csrf
~~~

###4. 验证码登录
![Image text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200107125258622-1037197645.png)

主要添加 ValidateCodeFilter、ImageCode
~~~
http.addFilterBefore(this.validateCodeFilter,
    UsernamePasswordAuthenticationFilter.class)// 添加图片验证，在UsernamePasswordAuthenticationFilter之前，顺序见: org.springframework.security.config.annotation.web.builders.FilterComparator.FilterComparator() 
~~~

###5. 记住我功能
基本原理
![Image text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200107165220671-894516268.png)

springsecruity基本原理
![Image text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200107165234881-1805567454.png)

>会根据参数中是否有“remember-me” 进行判断是否进行 remember操作。（AbstractRememberMeServices）


---
---
---

# JWT
### 1.组成部分：
- 头部：规范信息，编码格式
- 载荷：token，用户名、角色、过期时间
- 签名：将头部与载荷用base64编码，用“.”相连，加入盐，再用头部编码类型编码，得到签名。

流程：表单登录 -> 加密user信息，生成token，设置并返回token -> JwtVerifyFilter 验证token，保存authentication

---
---
---

# 终章：OAuth2
借助微信认证，登录网站（这样就不用再网站注册账户）
---

###认证授权详细的执行流程
![Image text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200113192917675-1745106987.png)
---

###OAuth2.0认证流程：
![Image text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200113192908176-15754733.png)
---

# 一、OAauth2.0包括以下角色：
1、客户端
> 本身不存储资源，需要通过资源拥有者的授权去请求资源服务器的资源，比如：Android客户端、Web客户端（浏览器端）、微信客户端等。

2、资源拥有者
> 通常为用户，也可以是应用程序，即该资源的拥有者。

3、授权服务器（也称认证服务器）
> 用于服务提供商对资源拥有的身份进行认证、对访问资源进行授权，认证成功后会给客户端发放令牌
（access_token），作为客户端访问资源服务器的凭据。本例为微信的认证服务器。

4、资源服务器
> 存储资源的服务器，本例子为微信存储的用户信息。
现在还有一个问题，服务提供商能允许随便一个客户端就接入到它的授权服务器吗？答案是否定的，服务提供商会
给准入的接入方一个身份，用于接入时的凭据:

    client_id：客户端标识
    client_secret：客户端秘钥
> 因此，准确来说，授权服务器对两种OAuth2.0中的两个角色进行认证授权，分别是资源拥有者、客户端。


---

# 二、OAuth2.0 中四种授权方式
## 1. 授权码模式（ authorization code）
![Image text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200113192855718-1043650555.png)

###流程
>说明：【A服务客户端】需要用到【B服务资源服务】中的资源

第一步：【A服务客户端】将用户自动导航到【B服务认证服务】，这一步用户需要提供一个回调地址，以备【B服务认证服务】返回授权码使用。

第二步：用户点击授权按钮表示让【A服务客户端】使用【B服务资源服务】，这一步需要用户登录B服务，也就是说用户要事先具有B服务的使用权限。

第三步：【B服务认证服务】生成授权码，授权码将通过第一步提供的回调地址，返回给【A服务客户端】。
>注意这个授权码并非通行【B服务资源服务】的通行凭证。

第四步：【A服务认证服务】携带上一步得到的授权码向【B服务认证服务】发送请求，获取通行凭证token。

第五步：【B服务认证服务】给【A服务认证服务】返回令牌token和更新令牌refresh token。



###使用场景
授权码模式是OAuth2中最安全最完善的一种模式，应用场景最广泛，可以实现服务之间的调用，常见的微信，QQ等第三方登录也可采用这种方式实现。

---
---

## 2. 简化模式（implicit）
###流程
>说明：简化模式中没有【A服务认证服务】这一部分，全部有【A服务客户端】与B服务交互，整个过程不再有授权码，token直接暴露在浏览器。

第一步：【A服务客户端】将用户自动导航到【B服务认证服务】，这一步用户需要提供一个回调地址，以备【B服务认证服务】返回token使用，还会携带一个【A服务客户端】的状态标识state。

第二步：用户点击授权按钮表示让【A服务客户端】使用【B服务资源服务】，这一步需要用户登录B服务，也就是说用户要事先具有B服务的使用权限。

第三步：【 B服务认证服务】生成通行令牌token，token将通过第一步提供的回调地址，返回给【A服务客户端】。

###使用场景
适用于A服务没有服务器的情况。比如：纯手机小程序，JavaScript语言实现的网页插件等。

---


## 3. 密码模式（resource owner password credentials）
###流程
第一步：直接告诉【A服务客户端】自己的【B服务认证服务】的用户名和密码

第二步：【A服务客户端】携带【B服务认证服务】的用户名和密码向【B服务认证服务】发起请求获取
token。

第三步：【B服务认证服务】给【A服务客户端】颁发token。

###使用场景
此种模式虽然简单，但是用户将B服务的用户名和密码暴露给了A服务，需要两个服务信任度非常高才能使
用。
---


## 4. 客户端模式（client credentials）
###流程
>说明：这种模式其实已经不太属于OAuth2的范畴了。A服务完全脱离用户，以自己的身份去向B服务索取token。换言之，用户无需具备B服务的使用权也可以。完全是A服务与B服务内部的交互，与用户无关了。

第一步：A服务向B服务索取token。

第二步：B服务返回token给A服务。

###使用场景
A服务本身需要B服务资源，与用户无关。

---
---
#三、 OAuth2.0 sql语句
##说明
既可以写死在代码中，也可以写入到数据库中，通常写入到数据库
##建表语句
https://www.cnblogs.com/kyousuke/p/13050857.html
~~~sql
CREATE SCHEMA IF NOT EXISTS `oauth2` DEFAULT CHARACTER SET utf8 ;
USE `oauth2` ;

-- -----------------------------------------------------
-- Table `oauth2`.`clientdetails`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth2`.`clientdetails` (
  `appId` VARCHAR(128) NOT NULL,
  `resourceIds` VARCHAR(256) NULL DEFAULT NULL,
  `appSecret` VARCHAR(256) NULL DEFAULT NULL,
  `scope` VARCHAR(256) NULL DEFAULT NULL,
  `grantTypes` VARCHAR(256) NULL DEFAULT NULL,
  `redirectUrl` VARCHAR(256) NULL DEFAULT NULL,
  `authorities` VARCHAR(256) NULL DEFAULT NULL,
  `access_token_validity` INT(11) NULL DEFAULT NULL,
  `refresh_token_validity` INT(11) NULL DEFAULT NULL,
  `additionalInformation` VARCHAR(4096) NULL DEFAULT NULL,
  `autoApproveScopes` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`appId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oatuh2`.`oauth_access_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth2`.`oauth_access_token` (
  `token_id` VARCHAR(256) NULL DEFAULT NULL,
  `token` BLOB NULL DEFAULT NULL,
  `authentication_id` VARCHAR(128) NOT NULL,
  `user_name` VARCHAR(256) NULL DEFAULT NULL,
  `client_id` VARCHAR(256) NULL DEFAULT NULL,
  `authentication` BLOB NULL DEFAULT NULL,
  `refresh_token` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oatuh2`.`oauth_approvals`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth2`.`oauth_approvals` (
  `userId` VARCHAR(256) NULL DEFAULT NULL,
  `clientId` VARCHAR(256) NULL DEFAULT NULL,
  `scope` VARCHAR(256) NULL DEFAULT NULL,
  `status` VARCHAR(10) NULL DEFAULT NULL,
  `expiresAt` DATETIME NULL DEFAULT NULL,
  `lastModifiedAt` DATETIME NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oatuh2`.`oauth_client_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth2`.`oauth_client_details` (
  `client_id` VARCHAR(128) NOT NULL,
  `resource_ids` VARCHAR(256) NULL DEFAULT NULL,
  `client_secret` VARCHAR(256) NULL DEFAULT NULL,
  `scope` VARCHAR(256) NULL DEFAULT NULL,
  `authorized_grant_types` VARCHAR(256) NULL DEFAULT NULL,
  `web_server_redirect_uri` VARCHAR(256) NULL DEFAULT NULL,
  `authorities` VARCHAR(256) NULL DEFAULT NULL,
  `access_token_validity` INT(11) NULL DEFAULT NULL,
  `refresh_token_validity` INT(11) NULL DEFAULT NULL,
  `additional_information` VARCHAR(4096) NULL DEFAULT NULL,
  `autoapprove` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oatuh2`.`oauth_client_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth2`.`oauth_client_token` (
  `token_id` VARCHAR(256) NULL DEFAULT NULL,
  `token` BLOB NULL DEFAULT NULL,
  `authentication_id` VARCHAR(128) NOT NULL,
  `user_name` VARCHAR(256) NULL DEFAULT NULL,
  `client_id` VARCHAR(256) NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oatuh2`.`oauth_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth2`.`oauth_code` (
  `code` VARCHAR(256) NULL DEFAULT NULL,
  `authentication` BLOB NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `oatuh2`.`oauth_refresh_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oauth2`.`oauth_refresh_token` (
  `token_id` VARCHAR(256) NULL DEFAULT NULL,
  `token` BLOB NULL DEFAULT NULL,
  `authentication` BLOB NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
~~~

##主要类：
OAuth2AuthenticationProcessingFilter : oauth2认证过滤器，如果不是oauth2请求将跳过这个filter
##接口
- 三方认证：GET localhost:8081/fq-oauth/oauth/authorize?response_type=token&client_id=baidu&redirect_uri=http://www.baidu.com

- 创建token：POST localhost:8081/fq-oauth/oauth/token

>参数：grant_type
>
>authorization_code 模式
>>参数：code
>>
>>参数：client_id
>>
>>参数：client_secret
> 
> password 模式
>>参数：username
>>
>>参数：password
>
> client_credentials 模式
>>参数：client_id
>>
>>参数：client_secret

---
#测试：


---

表达式处理类：OAuth2SecurityExpressionMethods