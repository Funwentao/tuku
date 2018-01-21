package com.thinkgem.jeesite.modules.basic.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by fandaz on 2018/1/16
 * 微信配置证书
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
        wxPayH5Config.setAppId("wxf8f80c175179dfb7");
        wxPayH5Config.setAppSecret("c4906186fdabe67a83027ca21c0ff0b0");
        wxPayH5Config.setMchId("1395938002");
        wxPayH5Config.setMchKey("T1YCE9pFAxebtQBbnDf2VkTfU8okIoIF");
        wxPayH5Config.setKeyPath("/var/wxconfig/apiclient_cert.p12");
        wxPayH5Config.setNotifyUrl("http://p.handanyida.top/pays/notifys");
        return wxPayH5Config;
    }
}
