package com.tpii.KHHR;

import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class GeneratePrivateKeyAndSign {

    public static void main(String[] args) {
        try {
            // Generate a new RSA key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Get private key as string
            String privateKeyStr = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            // Save private key to a file
            savePrivateKeyToFile(privateKeyStr, "privateKey.pem");

            // Data to be signed
            String requestData = "Sample data to be signed";

            // Sign the data using the generated private key
            String signature = sign(requestData, privateKeyStr);
            System.out.println("Signature: " + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void savePrivateKeyToFile(String privateKey, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("-----BEGIN PRIVATE KEY-----\n");
            writer.write(privateKey);
            writer.write("\n-----END PRIVATE KEY-----");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
