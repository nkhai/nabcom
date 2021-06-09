package com.nab.icom.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.nab.icom.user.model.OnlineUser;

public interface OnlineUserRepository extends  CrudRepository<OnlineUser, Long>  {

    /**
     * Validating username and password and retrieve the user
     * @param screenName
     * @param password
     * @return
     */
    public OnlineUser findByScreenName(String  screenName);



}
