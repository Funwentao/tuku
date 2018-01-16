package com.thinkgem.jeesite.modules.basic.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by fandaz on 2018/1/16
 */
@Component
public class WechatPayConfig {

    @Bean
    public BestPayServiceImpl bestPayService() {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId("f211a3db1f38e19db1bf3e500607deb4");
        wxPayH5Config.setAppSecret("37195bb029765b0b55aa9cd9aa13096aa3088e8d");
        wxPayH5Config.setMchId("");
        wxPayH5Config.setMchKey("");
        wxPayH5Config.setKeyPath("");
        wxPayH5Config.setNotifyUrl("http://fadaz.natapp1.cc/pay/notify");
        return wxPayH5Config;
    }
}
