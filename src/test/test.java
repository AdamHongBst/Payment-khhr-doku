public class test {

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