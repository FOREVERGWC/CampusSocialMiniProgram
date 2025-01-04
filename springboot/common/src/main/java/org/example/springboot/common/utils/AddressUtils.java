package org.example.springboot.common.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public class AddressUtils {
    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip={}&json=true";
    private static final String UNKNOWN = "未知 未知 未知";

    public static String getRealAddressByIP(String ip) {
        if (Validator.isIpv6(ip)) {
            BigInteger integer = NetUtil.ipv6ToBigInteger(ip);
            ip = NetUtil.longToIpv4(integer.longValue());
        }
        if (NetUtil.isInnerIP(ip)) {
            return "内网IP";
        }
        try (HttpResponse response = HttpUtil.createGet(StrUtil.format(IP_URL, ip)).execute()) {
            String body = response.body();
            if (StrUtil.isBlank(body)) {
                throw new RuntimeException();
            }
            JSONObject object = JSONUtil.parseObj(body);
            String region = object.getStr("pro", "未知");
            String city = object.getStr("city", "未知");
            String address = object.getStr("addr", "未知");
            return StrUtil.format("%s %s %s", region, city, address);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return UNKNOWN;
    }
}
