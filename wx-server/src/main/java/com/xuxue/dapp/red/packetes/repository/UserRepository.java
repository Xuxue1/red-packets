package com.xuxue.dapp.red.packetes.repository;



import com.xuxue.dapp.red.packetes.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {

    User findUserByOpenid(String openid);

}
