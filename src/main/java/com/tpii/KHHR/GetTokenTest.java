package com.tpii.KHHR;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.TimeZone;

public class GetTokenTest {

    private static final String clientId = "7626e54efc4a4f5eb885472dc04087e6";
    private static final String privateKeyPath = "D:/myappdev/Payment-khhr-doku/privateKey.pem";
    private static final String url = "https://sandbox.lightspeedtransfer.com/gateway/v1.0/access-token/b2b";

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("grantType", "client_credentials");
        json.put("additionalInfo", new JSONObject());

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        String timestamp = getCurrentTime();
        post.setHeader("X-TIMESTAMP", timestamp);
        post.setHeader("X-CLIENT-KEY", clientId);
        String strToSign = clientId + "|" + timestamp;
        String privateKey = readPrivateKeyFromFile(privateKeyPath);
        String signature = sign(strToSign, privateKey);
        post.setHeader("X-SIGNATURE", signature);

        try {
            JSONObject response = null;
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.parseObject(result);
            }
            String responseCode = response.getString("responseCode");
            if (responseCode != null && "2007300".equals(responseCode)) {
                String accessToken = response.getString("accessToken");
                System.out.println(accessToken);
            } else {
                System.out.println(response.getString("responseMessage"));//Unauthorized.Signature
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readPrivateKeyFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath).getReader())) {
            String line;
            StringBuilder privateKeyBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("-----")) { // Skip header and footer lines
                    privateKeyBuilder.append(line);
                }
            }
            return privateKeyBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentTime() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));//放在Application中全剧设置
//        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
        return ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static String sign(String requestData, String priKey) {
        try {
            PrivateKey privateKey = getPrivateKey(priKey);
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(privateKey);
            sign.update(requestData.getBytes());
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey getPrivateKey(String priKey) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(priKey);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
