package com.tpii.KHHR;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.TimeZone;
import java.util.UUID;

public class CreateVATest {

    private static final String apiSecret = "b1570f60-a5c7-47c1-b8e4-6aa585b20e17";
    private static final String url = "https://sandbox.lightspeedtransfer.com/gateway/v1.0/transfer-va/create-va";

    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJjbGllbnRJZCI6Ijc2MjZlNTRlZmM0YTRmNWViODg1NDcyZGMwNDA4N2U2IiwiZXhwIjoxNjc4NDM1MjY5LCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTEwVDE0OjQ2OjAzKzA3OjAwIn0.cp-mdhFKyMR_kjZSDjreD5fjDfnXlxczzEuSpPIsXhk";

    public static void main(String[] args) {

        String timestamp = getCurrentTime();

        JSONObject json = new JSONObject();
        json.put("virtualAccountName", "Jokul Doe");
        json.put("trxId", UUID.randomUUID());
        json.put("bankCode", "BNI");

//        json.put("virtualAccountTrxType", "1");
//        JSONObject totalAmount = new JSONObject();
//        totalAmount.put("value", "12345678.00");
//        totalAmount.put("currency", "IDR");
//        json.put("totalAmount", totalAmount);
//        json.put("expiredDate", getPlusDayDate(30));

        json.put("virtualAccountTrxType", "2");

        System.out.println(json.toJSONString());
        String strToSign = new StringBuffer().append("POST:/gateway/v1.0/transfer-va/create-va:").append(token).append(":").append(DigestUtil.sha256Hex(json.toJSONString()).toLowerCase()).append(":").append(timestamp).toString();
        System.out.println(strToSign);
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA512, apiSecret.getBytes());
        String signature = Base64.getEncoder().encodeToString(hMac.digest(strToSign));
        System.out.println(signature);

        String respStr = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("X-TIMESTAMP", timestamp)
                .header("Authorization", "Bearer " + token)
                .header("X-SIGNATURE", signature)
                .body(json.toJSONString())
                .timeout(600000)//毫秒
                .execute().body();
        System.out.println(respStr);
    }

    public static String getCurrentTime() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));//放在Application中全剧设置
//        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
        return ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static String getPlusDayDate(int day) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));//放在Application中全剧设置
        return ZonedDateTime.now().plusDays(day).truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static String sha256Hex(String jsonStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return Hex.encodeHexString(digest.digest(jsonStr.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}


//respone code
//{
//        "bankCode": "BNI",
//        "virtualAccountTrxType": "2",
//        "virtualAccountName": "Jokul Doe",
//        "trxId": "4ebde402-764d-4130-82e8-6ba0241ac7c6"
//        }
//        POST:/gateway/v1.0/transfer-va/create-va:
//        eyJhbGciOiJIUzI1NiJ9.eyJjbGllbnRJZCI6Ijc2MjZlNTRlZmM0YTRmNWViODg1NDcyZGMwNDA4N2U2IiwiZXhwIjoxNjc4NDM1MjY5LCJ0aW1lc3RhbXAiOiIyMDIzLTAzLTEwVDE0OjQ2OjAzKzA3OjAwIn0.cp-mdhFKyMR_kjZSDjreD5fjDfnXlxczzEuSpPIsXhk:
//        efa9501cd512de2bd90c3c5b9d2571edfaffc923cfc6f28021f28f4e7208ef99:2023-08-11T10:03:25+07:00
//        cWKnC325mWPFGKLhgg6Hzv4H2W80DqGEGs7nVmB75zOnzkqaovaGuHBJ/gFC60cMwgLYq0RgkR1MGVdDZJyvgQ==
//        {
//        "responseMessage": "Invalid Token",
//        "responseCode": "4012701"
//        }
