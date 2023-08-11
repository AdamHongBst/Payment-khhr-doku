package com.tpii.KHHR;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;

public class SimulationTest {

    private static final String clientId = "7626e54efc4a4f5eb885472dc04087e6";
    private static final String url = "https://sandbox.lightspeedtransfer.com/gateway/simulation";

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("clientId", clientId);
        json.put("virtualAccountNo", "9886667800000211");
        json.put("amount", "30000000.00");//Open VA

        String respStr = HttpRequest.post(url)
                .body(json.toJSONString())
                .timeout(600000)//毫秒
                .execute().body();
        System.out.println(respStr);
    }
}

//如何保证接口调用的幂等性，重复支付如何校验
