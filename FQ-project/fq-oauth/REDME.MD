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