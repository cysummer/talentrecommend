package com.talentrecd.test;

import sun.misc.BASE64Decoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Formatter;

/**
 * Created by flying on 10/6/16.
 */
public class EncryptClient {

    private static final EncryptClient instance =  new EncryptClient();

    public static EncryptClient getInstance() {
        return instance;
    }

    private PublicKey publicKey;

    private EncryptClient(){}

    /**
     * 闅忔満鐢熸垚token
     * @return
     */
    public byte[] genTokenKey(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey key = keyGen.generateKey();
            return key.getEncoded();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public byte[] rsaEncrypt(byte[] tokenKey){
        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA");
            rsa.init(Cipher.ENCRYPT_MODE, publicKey);
            return rsa.doFinal(tokenKey);
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] aesEncrypt(byte[] data, byte[] aesKey){
        try {
            Cipher aes = Cipher.getInstance("AES/ECB/NoPadding");
            Key key = new SecretKeySpec(aesKey, "AES");
            aes.init(Cipher.ENCRYPT_MODE, key);
            byte[] newData = data;
            if(data.length % 16 != 0){
                int newLen = (data.length /16 + 1) *16;
                newData = new byte[newLen];
                System.arraycopy(data, 0, newData, 0, data.length);
            }
            byte[] realData = aes.doFinal(newData);
            return realData;
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void init(String publicKeyPath){
        File f = new File(publicKeyPath);

        try {
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);
            byte[] keyBytes = new byte[(int) f.length()];
            dis.readFully(keyBytes);
            dis.close();

            String temp = new String(keyBytes);
            String publicKeyPEM = temp.replace("-----BEGIN PUBLIC KEY-----\n", "");
            publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

            BASE64Decoder b64=new BASE64Decoder();
            byte[] decoded = b64.decodeBuffer(publicKeyPEM);

            X509EncodedKeySpec spec =
                    new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int l = 0;
        byte[] buf = new byte[1024];
        while ( (l = inputStream.read(buf)) > 0){
            baos.write(buf, 0, l);
        }
        byte[] bytes = baos.toByteArray();
        String respStr = new String(bytes, "utf-8");
        return respStr;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static byte[] hexToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public byte[] aesDecrypt(byte[] data, byte[] aesKey){
        try {
            Cipher aes = Cipher.getInstance("AES/ECB/NoPadding");
            Key key = new SecretKeySpec(aesKey, "AES");
            aes.init(Cipher.DECRYPT_MODE, key);
            byte[] realData = aes.doFinal(data);
            int tail = realData.length - 1;
            for (int i = tail; i >= 0 ; i--) {
                if(realData[i] != 0){
                    tail = i;break;
                }
            }
            byte[] newdata = new byte[tail +1];
            System.arraycopy(realData,0, newdata, 0, newdata.length);
            return newdata;

        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {

        }
    }

    /**
    *
    * @param url  请求的链接
    * @param username  用户名
    * @param password  密码
    * @param data  请求的数据
    * @return
    * @throws IOException
    */
    public String sendReq(String url , String username, String password, String data) throws IOException {
        byte[] tokenBytes = genTokenKey();
//        byte[] tokenBytes = CryptManager.hexToBytes("470cd67b2462c3452dfbac7e32643f89");
        byte[] tokenEncoded = rsaEncrypt(tokenBytes);

        byte[] orgData = data.getBytes("utf-8");
        byte[] dataEncoded = aesEncrypt(orgData, tokenBytes);
        String data2send = byteToHex(dataEncoded);

        String token = byteToHex(tokenEncoded);

        String transNo = System.currentTimeMillis() + "";
        if(!url.endsWith("?")){
            url = url +"?partnerId=" + username;
        }else {
            url = url +"partnerId=" + username;
        }
        String reqpwd = transNo + password;
        byte[] signBytes = aesEncrypt(reqpwd.getBytes(), tokenBytes);
        String sign = byteToHex(signBytes);

        String realUrl = String.format("%s&token=%s&transNo=%s&sign=%s", url,
                 token, transNo, sign);

        URL u = new URL(realUrl);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty( "Content-Type", "application/octet-stream" );
        conn.setRequestProperty( "Content-Length", String.valueOf(data2send.length()));
        OutputStream os = conn.getOutputStream();
        os.write(data2send.getBytes());
        os.flush();
        InputStream inputStream = conn.getInputStream();
        String orgResp = readStream(inputStream);
        byte[] respBytes = hexToBytes(orgResp);
        byte[] realBytes = aesDecrypt(respBytes, tokenBytes);
        return new String(realBytes);
    }

    public static void main(String[] args) throws IOException {

    	  //使用方法. 1. 使用公钥初始化, 只需初始化一次
    	EncryptClient.getInstance().init("C:/Users/cy/Documents/public.pub");

        //2. 调用
        String resp = EncryptClient.getInstance().sendReq(
                "http://114.55.142.37:8080/user/login.do",
                "admin", "123456", "{}");
        System.out.println(resp);



    }
}
