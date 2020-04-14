package com.leyou.user.service;

import com.leyou.pojo.User;
import com.leyou.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean checkUserData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1:{
                user.setUsername(data);
                break;
            }
            case 2:{
                user.setPhone(data);
                break;
            }
            default: {
                return null;
            }
        }
        Boolean state = userMapper.selectCount(user) == 0;
        return state;
    }
}
