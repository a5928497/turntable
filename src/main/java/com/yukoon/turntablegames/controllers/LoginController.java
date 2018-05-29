package com.yukoon.turntablegames.controllers;

import com.yukoon.turntablegames.entities.User;
import com.yukoon.turntablegames.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.websocket.Session;
import java.util.Map;

@Controller
@SessionAttributes(value = {"user"},types = {User.class})
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(Map<String,Object> map, User user,String flag){
        User user_temp = userService.login(user);
        System.out.println(user_temp);
        if (user_temp != null) {
            map.put("user",user_temp);
        }else {
            if (flag.equals("bg")){
                return "redirect:/loginpage/login.html";
            }
            return "redirect:/index.html";
        }
        if (user_temp.getRole_id() == 2) {
            return "background/bg_index";
        }
        return "public/pb_index";
    }

    @GetMapping("/reflash")
    public String reflash(ModelMap modelMap, User user) {
        //更新session中user信息
        modelMap.addAttribute("user",userService.findById(user.getId()));
        return "public/pb_index";
    }
}