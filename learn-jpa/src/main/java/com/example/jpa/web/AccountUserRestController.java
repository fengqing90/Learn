package com.example.jpa.web;

import com.example.jpa.model.AccountUser;
import com.example.jpa.services.AbstractBaseService;
import com.example.jpa.services.AccountUserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * KEP-TODO
 *
 * @ClassName AccountUserRestCounter
 * @Author FengQing
 * @Date 2019/6/28 13:48
 */
@RestController
public class AccountUserRestController {

    @Resource
    private AbstractBaseService<AccountUser, Long> baseService;
    @Resource
    private AccountUserServiceImpl accountUserService;

    @GetMapping("/getUser/{id}")
    public String test(@PathVariable Long id) {

        AccountUser accountUser = this.accountUserService.get(id);
        System.out.println("accountUserService : " + accountUser);

        accountUser = this.baseService.get(id);
        System.out.println("baseService : " + accountUser);

        return accountUser.toString();
    }
}
