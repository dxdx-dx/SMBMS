package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.Provider;
import cn.bdqn.smbms.pojo.Role;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.RoleService;
import cn.bdqn.smbms.service.UserService;
import cn.bdqn.smbms.util.Constants;
import cn.bdqn.smbms.util.PageSupport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户控制层
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;//用户service层对象
    @Resource
    private RoleService roleService; //角色service层对象

    /**
     * 跳转登陆页面
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 23:05
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转主页面
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 23:05
     */
    @RequestMapping("/main")
    public String main() {
        return "frame";
    }

    /**
     * 登陆功能
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
            _queryUserRole = Integer.parseInt(queryUserRole);
        }
        //当前页
        int _pageIndex;
        if (pageIndex == null) {
            _pageIndex = 1;
        } else {
            _pageIndex = Integer.parseInt(pageIndex);
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
        // model.addAttribute("roleList", roleList);
        //return "useradd";
        return "useradd";//Spring标签的表单页面
    }

    /**
     * 通过ajax异步获取角色列表
     *
     * @return java.lang.Object
     * @author Matrix
     * @date 2020/4/26 22:45
     */
    @RequestMapping("/roleList")
    @ResponseBody
    public Object roleList() {
        List<Role> roleList = roleService.findAll();
        return JSONArray.toJSONString(roleList);
    }

    /**
     * 添加用户 spring标签+=多文件上传
     *
     * @param user        用户对象
     * @param httpSession session对象
     * @return 跳转页面
     */
    @RequestMapping("/addsave")
    public String addsave(User user, HttpSession httpSession, HttpServletRequest httpServletRequest,
                          @RequestParam(value = "attachs", required = false) MultipartFile[] multipartFiles) {
        //证件照路径+名称
        String idPicPath = null;
        //工作照路径 + 名称
        String workPicPath = null;
        String errorInfo = null;
        //上传文件是否为空
        boolean flag = true;
        //上传文件的路劲
        String path = httpSession.getServletContext().getRealPath("statics" + File.separator + "upload");
        //定义文件上传的最大值
        int fileSize = 5000000;
        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];
            if (!multipartFile.isEmpty()) {
                if (i == 0) {
                    errorInfo = "uploadFileError";
                } else {
                    errorInfo = "uploadWpError";
                }
                // 上传文件的源文件名
                String oldFileName = multipartFile.getOriginalFilename();
                //获取文件的扩展名
                String extension = FilenameUtils.getExtension(oldFileName);
                if (multipartFile.getSize() > fileSize) {
                    httpServletRequest.setAttribute(errorInfo, "文件不能超过5M");
                    flag = false;
                } else if (extension.equalsIgnoreCase("jpeg") ||
                        extension.equalsIgnoreCase("pneg") ||
                        extension.equalsIgnoreCase("jpg") ||
                        extension.equalsIgnoreCase("png")) {
                    //上传文件的新名字
                    String newFileName = System.currentTimeMillis() + RandomUtils.nextInt(10000) + "_person.jpg";
                    //上传文件对象
                    File targerFile = new File(path, newFileName);
                    if (!targerFile.exists()) {
                        //创建文件夹
                        targerFile.mkdirs();
                    }
                    try {
                        multipartFile.transferTo(targerFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        httpServletRequest.setAttribute(errorInfo, "上传错误");
                    }
                    if (i == 0) {
                        idPicPath = path + File.separator + newFileName;
                    } else {
                        workPicPath = path + File.separator + newFileName;
                    }

                } else {
                    httpServletRequest.setAttribute(errorInfo, "文件格式不正确，必须是jpg或者png");
                    flag = false;
                }
            }
        }
        if (flag) {
            User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
            user.setCreatedBy(userSession.getId());
            user.setCreationDate(new Date());
            user.setIdPicPath(idPicPath);
            user.setWorkPicPath(workPicPath);
            boolean result = userService.adduser(user);
            if (result) {
                return "redirect:/user/userlist";
            }
        }
        return "useradd";
    }
    /**
     * 添加用户 spring标签+=单文件上传
     *
     * @param user        用户对象
     * @param httpSession session对象
     * @return 跳转页面
     *//*
    @RequestMapping("/addsave")
    public String addsave(User user, HttpSession httpSession, HttpServletRequest httpServletRequest,
                          @RequestParam(value = "attachs", required = false) MultipartFile multipartFile) {
        //证件照路径+名称
        String idPicPath = null;
        if (!multipartFile.isEmpty()) {
            //上传文件的路劲
            String path = httpSession.getServletContext().getRealPath("statics" + File.separator + "upload");
            // 上传文件的源文件名
            String oldFileName = multipartFile.getOriginalFilename();
            //定义文件上传的最大值
            int fileSize = 5000000;
            //获取文件的扩展名
            String extension = FilenameUtils.getExtension(oldFileName);
            if (multipartFile.getSize() > fileSize) {
                httpServletRequest.setAttribute("uploadFileError", "文件不能超过5M");
                return "useradd";
            } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png")) {
                //上传文件的新名字
                String newFileName = System.currentTimeMillis() + "_person.jpg";
                //上传文件对象
                File targerFile = new File(path, newFileName);
                if (!targerFile.exists()) {
                    //创建文件夹
                    targerFile.mkdirs();
                }
                try {
                    multipartFile.transferTo(targerFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    httpServletRequest.setAttribute("uploadFileError", "上传错误");
                    return "useradd";
                }
                idPicPath = path +File.separator + newFileName;
            } else {
                httpServletRequest.setAttribute("uploadFileError", "文件格式不正确，必须是jpg或者png");
                return "useradd";
            }
        }
        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
        user.setCreatedBy(userSession.getId());
        user.setCreationDate(new Date());
        user.setIdPicPath(idPicPath);
        boolean result = userService.adduser(user);
        if (result) {
            return "redirect:/user/userlist";
        } else {
            // return "user/adduser";
            return "useradd";
        }
    }*/

    /**
     * 添加用户 spring标签
     *
     * @param user        用户对象
     * @param httpSession session对象
     * @return 跳转页面
     */
   /* @RequestMapping("/addsave")
    public String addsave(User user, HttpSession httpSession) {
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
    }*/

    /**
     * 添加用户 SRS 303验证
     *
     * @param user        用户对象
     * @param httpSession session对象
     * @return 跳转页面
     */
//    @RequestMapping("/addsave")
//    public String addsave(@Valid User user, BindingResult bindingResult, HttpSession httpSession) {
////        if (bindingResult.hasErrors()) {
////            return "user/adduser";
////        }
//        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
//        user.setCreatedBy(userSession.getId());
//        user.setCreationDate(new Date());
//        boolean result = userService.adduser(user);
//        if (result) {
//            return "redirect:/user/userlist";
//        } else {
//            // return "user/adduser";
//            return "useradd";
//        }
//    }

    /**
     * 根据id查询用户
     *
     * @param uid   用户id
     * @param model model对象
     * @return 用户页面
     */
    @RequestMapping("/usermodify")
    public String findUserById(@RequestParam String uid, Model model) {
        User user = userService.findUserById(Integer.valueOf(uid));
        model.addAttribute("user", user);
        return "usermodify";
    }

    /**
     * 修改用户
     *
     * @param user        用户对象
     * @param httpSession httpSession对象
     * @return 跳转页面
     */
    @RequestMapping("/usermodifysave")
    public String usermodifysave(User user, HttpSession httpSession) {
        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
        user.setModifyBy(userSession.getId());
        user.setModifyDate(new Date());
        boolean result = userService.modifyUser(user);
        if (result) {
            return "redirect:/user/userlist";
        } else {
            return "usermodify";
        }

    }


    /**
     * 查看用户详情
     *
     * @param id 用户id
     * @return 详情页面
     */
//    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
//    public String userView(@PathVariable String id, Model model) {
//        User user = userService.findUserById(Integer.valueOf(id));
//        model.addAttribute("user", user);
//        return "userview";
//    }

    /**
     * 查看用户详情
     *
     * @param id 用户id
     * @return java.lang.Object
     * @author Matrix
     * @date 2020/4/26 22:56
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    //解决中文乱码----produces = {"application/json;charset=UTF-8"}
    @ResponseBody
    public Object userView(@RequestParam String id) {
        User user = null;
        if (StringUtils.isNullOrEmpty(id)) {
            // userJson = "nodata";
        } else {
            user = userService.findUserById(Integer.valueOf(id));
            // userJson = JSON.toJSONString(user);
        }
        return user;
    }

    /**
     * 判断用户编码是否可用
     *
     * @param userCode 用户编码
     * @return java.lang.Object
     * @author Matrix
     * @date 2020/4/26 22:56
     */
    @RequestMapping("/userCodeIsExist")
    @ResponseBody
    public Object userCodeIsExist(@RequestParam(value = "userCode", required = true) String userCode) {
        HashMap<String, String> resultMap = new HashMap<>();
        if (StringUtils.isNullOrEmpty(userCode)) {
            resultMap.put("userCode", "empty");
        } else {
            User user = userService.findByUserCode(userCode);
            if (user != null) {
                resultMap.put("userCode", "exist");
            } else {
                resultMap.put("userCode", "noexist");
            }

        }
        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 删除用户
     *
     * @param uid 用户id
     * @return java.lang.Object
     * @author Matrix
     * @date 2020/4/26 23:43
     */
    @RequestMapping("/deluser")
    @ResponseBody
    public Object deluser(@RequestParam String uid) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(uid);
        HashMap<String, String> delResult = new HashMap<>();
        if (StringUtils.isNullOrEmpty(uid)) {
            delResult.put("delResult", "notexist");
        } else {
            boolean flag = userService.deluser(Integer.valueOf(uid));
            if (flag == true) {
                delResult.put("delResult", "true");
            } else {
                delResult.put("delResult", "false");
            }
        }
        return JSONArray.toJSONString(delResult);
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
