package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.Bill;
import cn.bdqn.smbms.pojo.Provider;
import cn.bdqn.smbms.service.BillService;
import cn.bdqn.smbms.service.ProviderService;
import cn.bdqn.smbms.util.Constants;
import cn.bdqn.smbms.util.PageSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单控制层
 */
@Controller
@RequestMapping("/bill")
public class BillController {
    @Resource
    private BillService billService;
    @Resource
    private ProviderService providerService;

    @RequestMapping("/billlist")
    public String findByPage(@RequestParam(value = "queryProductName", required = false) String queryProductName,
                             @RequestParam(value = "queryProviderId", required = false) String queryProviderId,
                             @RequestParam(value = "queryIsPayment", required = false) String queryIsPayment,
                             @RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
        if (queryProductName == null) queryProductName = "";
        int _queryProviderId;
        if (queryProviderId == null) {
            _queryProviderId = -1;
        } else {
            _queryProviderId = Integer.parseInt(queryProviderId);
        }
        int _queryIsPayment;
        if (queryIsPayment == null) {
            _queryIsPayment = -1;
        } else {
            _queryIsPayment = Integer.parseInt(queryIsPayment);
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
        int tatalCount = billService.findByPageCount(queryProductName, _queryProviderId, _queryIsPayment);
        pageSupport.setTotalCount(tatalCount);
        List<Provider> providerList = providerService.findName();
        List<Bill> billList = billService.findByPage(queryProductName, _queryProviderId, _queryIsPayment, _pageIndex, pageSupport.getPageSize());
        model.addAttribute("totalCount", pageSupport.getTotalCount());
        model.addAttribute("currentPageNo", pageSupport.getCurrentPageNo());
        model.addAttribute("totalPageCount", pageSupport.getTotalPageNo());
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", _queryProviderId);
        model.addAttribute("queryIsPayment", _queryIsPayment);
        model.addAttribute("billList", billList);
        model.addAttribute("providerList", providerList);
        return "billlist";
    }

}
