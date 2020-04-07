package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.Role;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.RoleService;
import cn.bdqn.smbms.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/main")
    public String main() {
        return "frame";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpSession sesion, HttpServletRequest request) {
        User user = userService.login(userCode, userPassword);
        if (user != null) {
            if (sesion.getAttribute("userSession") != null) {
                sesion.removeAttribute("userSession");
            }
            sesion.setAttribute("userSession", user);
            return "redirect:/user/main";
        } else {
            // request.setAttribute("error", "用户名或者密码不正确！！！");
            // return "login";
            /**
             * 全局异常处理
             */
            throw new RuntimeException("用户名或者密码不正确！！！");
        }
    }

    @RequestMapping("/userlist")
    public String userList(
            @RequestParam(value = "queryname", required = false) String queryname,
            @RequestParam(value = "queryUserRole", required = false) String queryUserRole,
            @RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
        if (queryname == null) queryname = "";
        int _queryUserRole;
        if (queryUserRole == null) _queryUserRole = 0;
        int _pageIndex;
        if (pageIndex == null) _pageIndex = 1;
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roleList", roleList);
        return "userlist";
    }
    /**
     * 局部异常处理
     *
     * @param e
     * @param request
     * @return
     */
//    @ExceptionHandler(value = RuntimeException.class)
//    public String handlerException(RuntimeException e, HttpServletRequest request) {
//        request.setAttribute("e", e);
//        return "error";
//    }

}
