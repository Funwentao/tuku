package com.thinkgem.jeesite.modules.basic.entity;

/**
 * Created by fandaz on 2018/1/19
 * 支付时请求参数
 */
public class PayRequests {


    private String body; //商品名称
    private String total_fee; //金额，字符串类型，以分为单位，1000 分，即10 块钱
    private String product_id; //商品id，用于商户自己商品管理
    private String goods_tag; //商品标签，可以标注来源用户
    private String op_user_id; //操作用户，即商铺自己的用户账号，对应的是获取到的appKey
    private String nonce_str; //随机字符串，不超过32 位
    private String spbill_create_ip; //访问用户IP 地址
    private String notify_url; //后端回调URL
    private String front_notify_url; //前端回调URL
    private String pay_type; //支付类型：0, #微信扫码 1, #微信公众号  2, #微信wap   3, #支付宝wap  4, #支付宝扫码  6, #快捷支付
    private String sign; //Sha1 加密结果

    public String getBody() {
        return body;
    }

    public String setBody(String body) {
        this.body = body;
        return body;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
        return total_fee;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String setProduct_id(String product_id) {
        this.product_id = product_id;
        return product_id;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public String setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
        return goods_tag;
    }

    public String getOp_user_id() {
        return op_user_id;
    }

    public String setOp_user_id(String op_user_id) {
        this.op_user_id = op_user_id;
        return op_user_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public String setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
        return nonce_str;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public String setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
        return spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String setNotify_url(String notify_url) {
        this.notify_url = notify_url;
        return notify_url;
    }

    public String getFront_notify_url() {
        return front_notify_url;
    }

    public String setFront_notify_url(String front_notify_url) {
        this.front_notify_url = front_notify_url;
        return front_notify_url;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String setPay_type(String pay_type) {
        this.pay_type = pay_type;
        return pay_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
