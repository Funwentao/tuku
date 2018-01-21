package com.thinkgem.jeesite.modules.basic.web.front;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.thinkgem.jeesite.modules.basic.entity.Orders;
import com.thinkgem.jeesite.modules.basic.service.OrdersService;
import com.thinkgem.jeesite.modules.basic.service.WeixinUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Random;

/**
 * Created by asus on 2018/1/20.
 */
@RequestMapping("pays")
@Controller
public class WxPaysController {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private WeixinUserInfoService weixinUserInfoService;

    /**
     * 发起支付
     */
    @RequestMapping(value = "creates")
    public ModelAndView pay( String openid, Map<String, Object> map,String serviceId,double totalFee) {
        PayRequest request = new PayRequest();
        Random random = new Random();

        Orders orders = new Orders();
        orders.setTotalFee(String.valueOf(totalFee));
        orders.setServiceId(serviceId);

        //支付请求参数
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        request.setOrderId(String.valueOf(random.nextInt(1000000000)));
        request.setOrderAmount(totalFee);
        request.setOrderName("服务付款");
        request.setOpenid(openid);


        orders.setOrderNum(request.getOrderId());
        orders.setUserId(openid);
        orders.setPaymentStatus(0);
        String name = weixinUserInfoService.getUserName(openid);
        orders.setUserName(name);
        ordersService.save(orders);

        PayResponse payResponse = bestPayService.pay(request);

        map.put("payResponse", payResponse);

        return new ModelAndView("modules/basic/create", map);
    }

    /**
     * 异步回调
     */
    @RequestMapping(value = "notifys")
    public ModelAndView notify(@RequestBody String notifyData) throws Exception {
        PayResponse response = bestPayService.asyncNotify(notifyData);
        return new ModelAndView("modules/basic/success");
    }
}
