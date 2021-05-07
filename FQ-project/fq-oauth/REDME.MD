#MyOauth
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