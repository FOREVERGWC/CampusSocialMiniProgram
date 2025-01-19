package org.example.springboot.system.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.example.springboot.system.domain.model.WechatResponse;

import java.util.HashMap;
import java.util.Map;

public class WechatUtils {
    public static WechatResponse getResponseByJsCode(String appId, String secret, String jsCode) {
        if (StrUtil.isBlank(jsCode)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", secret);
        map.put("js_code", jsCode);
        map.put("grant_type", "authorization_code");

        // TODO 使用HTTP线程池代替Hutool(未使用线程池，频繁创建实例性能损耗)
        try (HttpResponse response = HttpUtil.createGet("https://api.weixin.qq.com/sns/jscode2session").form(map).execute()) {
            String result = response.body();
            if (!response.isOk()) {
                return null;
            }
            WechatResponse wechatResponse = JSONUtil.toBean(result, WechatResponse.class);
            if (wechatResponse == null || wechatResponse.getErrcode() != null) {
                return null;
            }
            return wechatResponse;
        }
    }
}
