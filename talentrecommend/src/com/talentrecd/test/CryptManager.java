package com.talentrecd.test;


import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Formatter;

/**
 * User: shangrenxiang
 */
public class CryptManager {

	public static CryptManager ins = new CryptManager();

	public static CryptManager getIns() {
		return ins;
	}
	private PrivateKey privateKey;
	private byte[] publicKeyBytes;
	private PublicKey publicKey;


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

	public static String byteToHex(final byte[] hash)
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

	public byte[] getTokenKey(){
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


	private PublicKey readPublic(File f) throws IOException {
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
			return kf.generatePublic(spec);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	private PrivateKey readPrivateKey(File privateKey, char[] password) throws IOException {
		FileReader sReader = new FileReader(privateKey);
		try {
			PEMParser pemParser = new PEMParser(sReader);
			Object object = pemParser.readObject();
			PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password);
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
			PrivateKey kp = null;
			if (object instanceof PEMEncryptedKeyPair) {
				System.out.println("Encrypted key - we will use provided password");
				kp = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(decProv)).getPrivate();
			} else if(object instanceof PKCS8EncryptedPrivateKeyInfo) {
				try {
					kp = converter.getPrivateKey(((PKCS8EncryptedPrivateKeyInfo)object).decryptPrivateKeyInfo(new JceOpenSSLPKCS8DecryptorProviderBuilder().build(password)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Unencrypted key - no password needed");
				kp = converter.getKeyPair((PEMKeyPair) object).getPrivate();
			}
			return kp;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			sReader.close();
		}
	}

	public void init(String privateKeyPath, String publicKeyPath, String password) throws IOException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.privateKey = readPrivateKey(new File(privateKeyPath), password.toCharArray());
		this.publicKey =  readPublic(new File(publicKeyPath));
	}
	public void initClient( String publicKeyPath) throws IOException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.publicKey =  readPublic(new File(publicKeyPath));
	}
    public void initServer(String privateKeyPath,  String password) throws IOException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        this.privateKey = readPrivateKey(new File(privateKeyPath), password.toCharArray());
    }



	public byte[] rsaEncrypt(byte[] tokenKey,  PublicKey publicKey){
		try {
			Cipher rsa;
			rsa = Cipher.getInstance("RSA");
			rsa.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] realKey = rsa.doFinal(tokenKey);
			return realKey;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
//            lock.unlock();
		}
	}

	public byte[] rsaEncrypt(byte[] tokenKey){
		return rsaEncrypt(tokenKey,this.publicKey);
	}

	public byte[] rsaDecrypt(byte[] data){
		return rsaDecrypt(data,this.privateKey);
	}

	public byte[] rsaDecrypt(byte[] data, PrivateKey privateKey){
		try {
			Cipher rsa;
			rsa = Cipher.getInstance("RSA");
			rsa.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] d = rsa.doFinal(data);
			return d;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
//            lock.unlock();
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
		}finally {
		}
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


	public byte[] getPublicKeyBytes() {
		return publicKeyBytes;
	}



    public static void main(String[] args) throws Exception {

        //瀹㈡埛绔�鐭ラ亾鍏挜

        CryptManager.getIns().initClient(
                "D:/Develop/Project/workplace/opensource/tantan/server/keys/public_1.pub"
        );


       String orgMsg = "1464943497669123456";//鍘熷鏁版嵁,寰呭姞瀵�

        byte[] tokenBytes = CryptManager.getIns().getTokenKey();//闅忔満鐢熸垚16瀛楄妭
        System.out.println("token:"+byteToHex(tokenBytes));
        byte[] tokenEncoded = CryptManager.getIns().rsaEncrypt(tokenBytes);
        System.out.println("tokenEncoded:"+byteToHex(tokenEncoded));
        byte[] orgEncoded = CryptManager.getIns().aesEncrypt(orgMsg.getBytes(), tokenBytes);
        System.out.println("orgEncoded:"+byteToHex(orgEncoded));


        //瀹㈡埛绔彂閫佺粰鏈嶅姟鍣ㄧtokenEncoded鍜宱rgEncoded涓ゆ潯鏁版嵁
        //瀹㈡埛绔�鏈嶅姟鍣ㄧ,鐭ラ亾绉侀挜



        CryptManager.getIns().initServer(
                "D:/Develop/Project/workplace/opensource/tantan/server/keys/private.key",
                "tt9527");
        byte[] token = CryptManager.getIns().rsaDecrypt(tokenEncoded);//RSA瑙ｅ瘑,寰楀埌鍘熷鐢ㄤ簬aes鍔犲瘑鐨則oken
        System.out.println("token:"+byteToHex(token));
        byte[] orgDecoded = CryptManager.getIns().aesDecrypt(orgEncoded, token);//浣跨敤token瀵瑰姞瀵嗙殑鏁版嵁杩涜瑙ｅ瘑,寰楀埌鍘熷鏁版嵁
        System.out.println("orgDecoded:"+byteToHex(orgDecoded));
        System.out.println("orgMsg:" + new String(orgDecoded));//瑙ｅ瘑寰楀埌鍘熷淇℃伅

    }

}
