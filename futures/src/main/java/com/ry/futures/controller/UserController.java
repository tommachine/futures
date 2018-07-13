package com.ry.futures.controller;

import com.alibaba.fastjson.JSONObject;
import com.ry.futures.bean.User;
import com.ry.futures.service.UserService;
import com.ry.futures.util.RedisDao;
import com.ry.futures.util.annotation.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisDao redisDao;



    @RequestMapping("add/{id}")
    @RedisCache(type = User.class)
    public String addUser(@PathVariable(name = "id")  Integer userId){

        if(userId == 1){
            User user = new User();
            user.setId(null);
            user.setName(UUID.randomUUID().toString().replaceAll("-","").substring(0));
            user.setPass(UUID.randomUUID().toString().replaceAll("-","").substring(0));
            userService.insert(user);
            redisDao.set("user1",user);
        }else{
            User user = (User) redisDao.get("user1");
            return JSONObject.toJSONString(user);
        }

        return "ok";
    }
}
