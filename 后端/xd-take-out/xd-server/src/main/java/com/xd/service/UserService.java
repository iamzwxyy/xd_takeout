package com.xd.service;

import com.xd.dto.UserLoginDTO;
import com.xd.entity.User;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
