package com.tpii.KHHR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class GetPrivateKeyFromPEM {

    public static void main(String[] args) {
        try {
            String privateKeyFilePath = "privateKey.pem"; // Replace with your actual file path
            String privateKeyPem = new String(Files.readAllBytes(Paths.get(privateKeyFilePath)));

            String privateKeyData = extractPrivateKeyData(privateKeyPem);

            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyData);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

            PrivateKey privateKey = getPrivateKey(privateKeySpec);

            // Now you can use the privateKey as needed
            System.out.println("Private key decoded and ready to use.\n" + privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractPrivateKeyData(String privateKeyPem) {
        String marker = "-----BEGIN PRIVATE KEY-----";
        int startIndex = privateKeyPem.indexOf(marker) + marker.length();
        int endIndex = privateKeyPem.indexOf("-----END PRIVATE KEY-----");
        return privateKeyPem.substring(startIndex, endIndex).replaceAll("\\s+", "");
    }

    public static PrivateKey getPrivateKey(PKCS8EncodedKeySpec privateKeySpec) {
        try {
            // Here you can implement your getPrivateKey logic
            // Return the PrivateKey object from the privateKeySpec
            // This could involve using KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
