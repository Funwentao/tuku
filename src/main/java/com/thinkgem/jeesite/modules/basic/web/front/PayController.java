package com.thinkgem.jeesite.modules.basic.web.front;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.basic.entity.Gallery;
import com.thinkgem.jeesite.modules.basic.entity.Orders;
import com.thinkgem.jeesite.modules.basic.entity.WeixinUserInfo;
import com.thinkgem.jeesite.modules.basic.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by fandaz on 2018/1/16
 */
@Controller
@Slf4j
public class PayController extends BaseController{
//    @Autowired
//    private BestPayServiceImpl bestPayService;
//
//    /**
//     * 发起支付
//     */
//    @RequestMapping(value = "/pay")
//    public ModelAndView pay(@RequestParam("openid") String openid,
//                            Map<String, Object> map) {
//        PayRequest request = new PayRequest();
//        Random random = new Random();
//
//        //支付请求参数
//        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
//        request.setOrderId(String.valueOf(random.nextInt(1000000000)));
//        request.setOrderAmount(0.01);
//        request.setOrderName("最好的支付sdk");
//        request.setOpenid(openid);
////        log.info("【发起支付】request={}", JsonUtil.toJson(request));
//
//        PayResponse payResponse = bestPayService.pay(request);
////        log.info("【发起支付】response={}", JsonUtil.toJson(payResponse));
//
//        map.put("payResponse", payResponse);
//
//        return new ModelAndView("pay/create", map);
//    }
//
//    /**
//     * 异步回调
//     */
//    @RequestMapping(value = "/notify",method = RequestMethod.POST)
//    public ModelAndView notify(@RequestBody String notifyData) throws Exception {
////        log.info("【异步回调】request={}", notifyData);
//        PayResponse response = bestPayService.asyncNotify(notifyData);
////        log.info("【异步回调】response={}", JsonUtil.toJson(response));
//
//        return new ModelAndView("pay/success");
//    }

    private OrdersService ordersService;
    private static final String  URL = "http://www.lhq333.cn:8081/paying/lovepay/getQr";

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0 ; i < length; ++i){
            int number = random.nextInt(62);//[0,62)

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 支付功能实现
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pay")
    public HashMap<String,Object> pay(String totalFee, String serviceId, Session session,String serviceContent, HttpServletRequest request, HttpServletResponse response) {
        HashMap <String,Object> jsonMap = new HashMap<String,Object>();

        WeixinUserInfo weixinUserInfo =(WeixinUserInfo)session.getAttribute("wxUser");

        Orders orders = new Orders();
        orders.setTotalFee(totalFee);
        orders.setServiceId(serviceId);

        ordersService.save(orders);

        String body = serviceContent;
        String total_fee = totalFee;
        String product_id = serviceId;
        String goods_tag  = "来自用户" + weixinUserInfo.getNickname() + "支付";
        String op_user_id = "f211a3db1f38e19db1bf3e500607deb4";
        String nonce_str = getRandomString(32);
        String spbill_create_ip = request.getRemoteAddr();
        String notify_urt = "http://p.handanyida.top/";
        String front_notify_url = "http://p.handanyida.top/";
        String pay_type = "1";

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("body",body);
        hashMap.put("total_fee",total_fee);
        hashMap.put("product_id",product_id);
        hashMap.put("goods_tag",goods_tag);
        hashMap.put("op_user_id",op_user_id);
        hashMap.put("nonce_str",nonce_str);
        hashMap.put("spbill_create_ip",spbill_create_ip);
        hashMap.put("notify_urt",notify_urt);
        hashMap.put("front_notify_url",front_notify_url);
        hashMap.put("pay_type",pay_type);

        String[] a = {"front_notify_url","goods_tag","nonce_str","notify_url","op_user_id","pay_type","product_id","spbill_create_ip","total_fee"};

        String str = "body="+hashMap.get("body");

        for(int i = 0,len = a.length;i<len;i++){
            str += a[i] + "=" + hashMap.get(a[i]);
        }

        

        return jsonMap;
    }

}
