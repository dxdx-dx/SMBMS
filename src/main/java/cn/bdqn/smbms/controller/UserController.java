package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.Role;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.RoleService;
import cn.bdqn.smbms.service.UserService;
import cn.bdqn.smbms.util.Constants;
import cn.bdqn.smbms.util.PageSupport;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 用户控制层
 */
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

    /**
     * 登陆工能
     *
     * @param userCode     用户名code
     * @param userPassword 密码
     * @param sesion       sesion对象
     * @param request      request对象
     * @return 登陆页面
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, HttpSession sesion, HttpServletRequest request) {
        User user = userService.login(userCode, userPassword);
        if (user != null) {
            if (sesion.getAttribute(Constants.USER_SESSION) != null) {
                sesion.removeAttribute(Constants.USER_SESSION);
            }
            sesion.setAttribute(Constants.USER_SESSION, user);
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

    /**
     * 用户列表的分页显示
     *
     * @param queryname     用户名
     * @param queryUserRole 用户角色
     * @param pageIndex     当前页
     * @param model         model对象
     * @return 用户列表页面
     */
    @RequestMapping("/userlist")
    public String userList(
            @RequestParam(value = "queryname", required = false) String queryname,
            @RequestParam(value = "queryUserRole", required = false) String queryUserRole,
            @RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
        //用户名
        if (queryname == null) queryname = "";
        //角色id
        int _queryUserRole;
        if (queryUserRole == null) {
            _queryUserRole = 0;
        } else {
            _queryUserRole = Integer.valueOf(queryUserRole);
        }
        //当前页
        int _pageIndex;
        if (pageIndex == null) {
            _pageIndex = 1;
        } else {
            _pageIndex = Integer.valueOf(pageIndex);
        }
        //分页对象
        PageSupport pageSupport = new PageSupport();
        pageSupport.setPageSize(Constants.PAGE_SIZE);
        pageSupport.setCurrentPageNo(_pageIndex);
        //查询总条数
        int tatalCount = userService.findByPageCount(queryname, _queryUserRole);
        pageSupport.setTotalCount(tatalCount);
        //查询用户列表
        List<User> userList = userService.findByPage(queryname, _queryUserRole, _pageIndex, pageSupport.getPageSize());
        //查询角色列表
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roleList", roleList);
        model.addAttribute("userList", userList);
        model.addAttribute("totalCount", pageSupport.getTotalCount());
        model.addAttribute("currentPageNo", pageSupport.getCurrentPageNo());
        model.addAttribute("totalPageCount", pageSupport.getTotalPageNo());
        model.addAttribute("queryUserName", queryname);
        model.addAttribute("queryUserRole", queryUserRole);
        return "userlist";
    }

    /**
     * 注销功能
     *
     * @param httpSession httpSession对象
     * @return 返回登陆页面
     */
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession.getAttribute(Constants.USER_SESSION) != null) {
            httpSession.removeAttribute(Constants.USER_SESSION);
        }
        return "redirect:/user/login";
    }

    /**
     * 跳转到添加用户页面
     *
     * @param model model对象
     * @return 用户添加页面
     */
    @RequestMapping("/useradd")
    public String adduser(@ModelAttribute("user") User user, Model model) {
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roleList", roleList);
        //return "useradd";
        return "useradd";
    }

    /**
     * 添加用户
     *
     * @param user        用户对象
     * @param httpSession session对象
     * @return 跳转页面
     */
    @RequestMapping("/addsave")
    public String addsave(@Valid User user, BindingResult bindingResult, HttpSession httpSession) {
//        if (bindingResult.hasErrors()) {
//            return "user/adduser";
//        }
        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
        user.setCreatedBy(userSession.getId());
        user.setCreationDate(new Date());
        boolean result = userService.adduser(user);
        if (result) {
            return "redirect:/user/userlist";
        } else {
            // return "user/adduser";
            return "useradd";
        }
    }

    /**
     * 局部异常处理
     *
     * @param e
     * @param request
     * @return error
     */
    //    @ExceptionHandler(value = RuntimeException.class)
    //    public String handlerException(RuntimeException e, HttpServletRequest request) {
    //        request.setAttribute("e", e);
    //        return "error";
    //    }

}
