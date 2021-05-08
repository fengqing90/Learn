#MyOauth

![image_text](https://img2018.cnblogs.com/blog/1580998/202001/1580998-20200106131330689-2043178712.png)

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
    .loginProcessingUrl("/auth/form") // 登录处理url
    .successHandler(null) // 登录成功处理
    .failureHandler(null) // 登录失败处理

    .and().authorizeRequests() // 身份认证设置
    .antMatchers("/signin.html").permitAll()// 匹配 signin.html 不需要认证
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
