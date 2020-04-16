package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.Provider;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.ProviderService;
import cn.bdqn.smbms.util.Constants;
import cn.bdqn.smbms.util.PageSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 供应商控制层
 */
@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Resource
    private ProviderService providerService;

    /**
     * 分页查询供应商列表
     *
     * @param queryProCode
     * @param queryProName
     * @param pageIndex
     * @param model
     * @return
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
     * @return
     */
    @RequestMapping("/provideradd")
    public String provideradd() {
        return "provideradd";
    }

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
}
