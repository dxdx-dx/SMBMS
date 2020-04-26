package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.Provider;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.ProviderService;
import cn.bdqn.smbms.util.Constants;
import cn.bdqn.smbms.util.PageSupport;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 供应商控制层
 */
@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Resource
    private ProviderService providerService;//供应商service层对象

    /**
     * 分页查询供应商列表
     *
     * @param queryProCode 供应商编码
     * @param queryProName 供应商名称
     * @param pageIndex    当前页
     * @param model        model对象
     * @return 供应商列表页面
     */
    @RequestMapping("/providerlist")
    public String findByPage(@RequestParam(value = "queryProCode", required = false) String queryProCode,
                             @RequestParam(value = "queryProName", required = false) String queryProName,
                             @RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
        if (queryProCode == null) {
            queryProCode = "";
        }
        if (queryProName == null) {
            queryProName = "";
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
        int tatalCount = providerService.findByPageCount(queryProCode, queryProName);
        pageSupport.setTotalCount(tatalCount);
        List<Provider> providerList = providerService.findByPage(queryProCode, queryProName, _pageIndex, pageSupport.getPageSize());
        model.addAttribute("totalCount", pageSupport.getTotalCount());
        model.addAttribute("currentPageNo", pageSupport.getCurrentPageNo());
        model.addAttribute("totalPageCount", pageSupport.getTotalPageNo());
        model.addAttribute("queryProCode", queryProCode);
        model.addAttribute("queryProName", queryProName);
        model.addAttribute("providerList", providerList);
        return "providerlist";
    }

    /**
     * 跳转供应商添加页面
     *
     * @return 供应商添加页面
     */
    @RequestMapping("/provideradd")
    public String provideradd() {
        return "provideradd";
    }

    /**
     * 供应商添加
     *
     * @param provider    供应商对象
     * @param httpSession session对象
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 22:51
     */
    @RequestMapping("/providersave")
    public String providersave(Provider provider, HttpSession httpSession) {
        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
        provider.setCreatedBy(userSession.getId());
        provider.setCreationDate(new Date());
        boolean result = providerService.addPrivider(provider);
        if (result) {
            return "redirect:/provider/providerlist";
        } else {
            return "provideradd";
        }
    }

    /**
     * 跳转供应商修改页面
     *
     * @param uid   供应商id
     * @param model model对象
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 22:52
     */
    @RequestMapping("/providermodify")
    public String findUserById(@RequestParam String uid, Model model) {
        Provider provider = providerService.findProviderById(Integer.valueOf(uid));
        model.addAttribute("provider", provider);
        return "providermodify";
    }

    /**
     * 供应商修改
     *
     * @param provider    供应商对象
     * @param httpSession session对象
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 22:52
     */
    @RequestMapping("/providermodifysave")
    public String providermodifysave(Provider provider, HttpSession httpSession) {
        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
        provider.setModifyBy(userSession.getId());
        provider.setModifyDate(new Date());
        boolean result = providerService.providerModify(provider);
        if (result) {
            return "redirect:/provider/providerlist";
        } else {
            return "providermodify";
        }
    }

    /**
     * 查看供应商详情
     *
     * @param id    供应商id
     * @param model model对象
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 22:53
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String userView(@PathVariable String id, Model model) {
        Provider provider = providerService.findProviderById(Integer.valueOf(id));
        model.addAttribute("provider", provider);
        return "providerview";
    }

    /**
     * 删除供应商
     *
     * @return java.lang.Object
     * @author Matrix
     * @date 2020/4/26 23:55
     */
    @RequestMapping("/delprovider")
    @ResponseBody
    public Object delprovider(@RequestParam String proid) {
        HashMap<String, String> delResult = new HashMap<>();
        if (StringUtils.isNullOrEmpty(proid)) {
            delResult.put("delResult", "notexist");
        } else {
            boolean flag = providerService.delprovider(Integer.valueOf(proid));
            if (flag == true) {
                delResult.put("delResult", "true");
            } else {
                delResult.put("delResult", "false");
            }
        }
        return JSONArray.toJSONString(delResult);
    }

}
