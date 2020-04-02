package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    public String login() {


        return "login";
    }

    @RequestMapping("/main")
    public String main() {


        return "frame";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@RequestParam String userCode, @RequestParam String userPassword) {
        logger.info("doLogin=======================================");
        logger.info("userCode=" + userCode + "userPassword=" + userPassword);
        User user = userService.login(userCode, userPassword);
        if (user != null) {
            return "redirect:/user/main";
        } else {
            return "login";
        }
    }


}
