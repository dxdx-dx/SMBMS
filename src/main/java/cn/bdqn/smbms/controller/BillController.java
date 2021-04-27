package cn.bdqn.smbms.controller;

import cn.bdqn.smbms.pojo.Bill;
import cn.bdqn.smbms.pojo.Provider;
import cn.bdqn.smbms.pojo.User;
import cn.bdqn.smbms.service.BillService;
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
 * 订单控制层
 */
@Controller
@RequestMapping("/bill")
public class BillController {
    @Resource
    private BillService billService;//订单service层对象
    @Resource
    private ProviderService providerService;//供应商service层对象

    /**
     * 分页显示订单列表
     *
     * @param queryProductName 商品名称
     * @param queryProviderId  供应商id
     * @param queryIsPayment   是否付款
     * @param pageIndex        当前页
     * @param model            model对象
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 22:48
     */
    @RequestMapping("/billlist")
    public String findByPage(@RequestParam(value = "queryProductName", required = false) String queryProductName,
                             @RequestParam(value = "queryProviderId", required = false) String queryProviderId,
                             @RequestParam(value = "queryIsPayment", required = false) String queryIsPayment,
                             @RequestParam(value = "pageIndex", required = false) String pageIndex, Model model) {
        if (queryProductName == null) {
            queryProductName = "";
        }
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

    /**
     * 跳转订单添加页面
     *
     * @return 添加页面
     */
    @RequestMapping("/billadd")
    public String billadd() {
        return "billadd";
    }

    /**
     * 查看订单详情
     *
     * @param id    订单编号
     * @param model model对象
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 22:44
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String userView(@PathVariable String id, Model model) {
        Bill bill = billService.findBillById(Integer.valueOf(id));
        model.addAttribute("bill", bill);
        return "billview";
    }

    /**
     * 根据id修改订单页面
     *
     * @param id    订单编号
     * @param model model对象
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/26 22:46
     */
    @RequestMapping("/billmodify/{id}")
    public String findUserById(@PathVariable String id, Model model) {
        Bill bill = billService.findBillById(Integer.valueOf(id));
        model.addAttribute("bill", bill);
        return "billmodify";
    }

    /**
     * 通过ajax异步获取供应商列表
     *
     * @return java.lang.Object
     * @author Matrix
     * @date 2020/4/26 22:45
     */
    @RequestMapping("/providerList")
    @ResponseBody
    public Object providerList() {
        List<Provider> providerList = providerService.findName();
        return JSONArray.toJSONString(providerList);
    }

    /**
     * 删除订单
     *
     * @return java.lang.Object
     * @author Matrix
     * @date 2020/4/26 23:56
     */
    @RequestMapping("/delbill")
    @ResponseBody
    public Object delbill(@RequestParam String billid) {
        HashMap<String, String> delResult = new HashMap<>();
        if (StringUtils.isNullOrEmpty(billid)) {
            delResult.put("delResult", "notexist");
        } else {
            boolean flag = billService.delbill(Integer.valueOf(billid));
            if (flag == true) {
                delResult.put("delResult", "true");
            } else {
                delResult.put("delResult", "false");
            }
        }
        return JSONArray.toJSONString(delResult);
    }

    /**
     * 订单添加
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/27 0:38
     */
    @RequestMapping("/billsave")
    public String billsave(Bill bill, HttpSession httpSession) {
        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
        bill.setCreatedBy(userSession.getId());
        bill.setCreationDate(new Date());
        boolean result = billService.addBill(bill);
        if (result) {
            return "redirect:/bill/billlist";
        } else {
            return "billadd";
        }
    }

    /**
     * 修改订单
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/4/27 0:35
     */
    @RequestMapping("/billmodifysave")
    public String billmodifysave(Bill bill, HttpSession httpSession) {
        User userSession = (User) httpSession.getAttribute(Constants.USER_SESSION);
        bill.setModifyBy(userSession.getId());
        bill.setModifyDate(new Date());
        boolean result = billService.billModify(bill);
        if (result) {
            return "redirect:/bill/billlist";
        } else {
            return "billmodify";
        }
    }

}
