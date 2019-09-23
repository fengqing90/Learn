package com.example.jpa.services;

import com.example.jpa.model.AccountUser;
import com.example.jpa.model.UserType;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * KEP-TODO
 *
 * @ClassName AccountUserServiceImpl
 * @Author FengQing
 * @Date 2019/6/28 13:46
 */
@Service
@Transactional
public class AccountUserServiceImpl extends AbstractBaseService<AccountUser, Long> implements IAccountUserService {


    public AccountUser testFindOne(Long id) {
        return this.baseRepository.findOne(id);
    }

    public AccountUser testFindOne4Example(Long id) {

        AccountUser accountUser = new AccountUser();
        accountUser.setUserType(UserType.COMMON);
        accountUser.setId(id);

        Example<AccountUser> example = Example.of(accountUser);
        return this.baseRepository.findOne(example);
    }

    
}
