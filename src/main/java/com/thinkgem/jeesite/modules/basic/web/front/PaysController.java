package com.thinkgem.jeesite.modules.basic.web.front;

import com.thinkgem.jeesite.modules.basic.entity.PayRequests;
import com.thinkgem.jeesite.modules.basic.entity.WeixinUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by fandaz on 2018/1/19
 */
@Controller
@RequestMapping("ggf")
public class PaysController {

    /**
     * 转化为queryString形式
     * @param s
     * @return
     */
    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 转换为queryString
     * @param map
     * @return
     */
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    /**
     * @param decript 要加密的字符串
     * @return 加密的字符串
     * SHA1加密
     */
    public final static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @ResponseBody
    @RequestMapping("pay")
    public Map<String,String> pay(String total_fee,String openId, HttpSession session, HttpServletRequest request){
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }
        );
        WeixinUserInfo wxUser = (WeixinUserInfo)session.getAttribute("wxUser");
//        String openId = wxUser.getOpenid();
        PayRequests payRequests = new PayRequests();
        Random random = new Random();
        String body = payRequests.setBody("product");
        total_fee = payRequests.setTotal_fee(total_fee);
        String product_id = payRequests.setProduct_id(String.valueOf(random.nextInt(1000000000)));
        String goods_tag = payRequests.setGoods_tag(openId);
        String op_user_id = payRequests.setOp_user_id("f211a3db1f38e19db1bf3e500607deb4");
        String nonce_str = payRequests.setNonce_str(String.valueOf(random.nextInt(1000000000)));
        String spbill_create_ip = payRequests.setSpbill_create_ip(request.getRemoteAddr());
        String notify_url = payRequests.setNotify_url("http://fadaz.natapp1.cc/gg/getIndexList");
        String front_notify_url = payRequests.setFront_notify_url("http://fadaz.natapp1.cc/gg/getIndexList");
        String pay_type = payRequests.setPay_type("1");
//        map.put("payRequests",payRequests);
        map.put("body",body);
        map.put("total_fee",total_fee);
        map.put("product_id",product_id);
        map.put("goods_tag",goods_tag);
        map.put("op_user_id",op_user_id);
        map.put("nonce_str",nonce_str);
        map.put("spbill_create_ip",spbill_create_ip);
        map.put("notify_url",notify_url);
        map.put("front_notify_url",front_notify_url);
        map.put("pay_type",pay_type);
        String queryString = urlEncodeUTF8(map)+"37195bb029765b0b55aa9cd9aa13096aa3088e8d";
        String sign  = SHA1(queryString).toUpperCase();
        map.put("sign",sign);
        return map;

    }



}
