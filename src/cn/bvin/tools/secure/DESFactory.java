package cn.bvin.tools.secure;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * @ClassName: DESFactory 
 * @Description: DES+Base64双重加密
 * @author: Bvin
 * @date: 2015年1月15日 下午2:34:40
 */
public class DESFactory extends Securor{
	
	private byte[] key;
	

	public DESFactory(byte[] key) {
		super();
		this.key = key;
	}

	public static DESFactory get(byte[] key) {
		return new DESFactory(key);
	}

	@Override
	public byte[] decode(byte[] input) {
		try {
			DESKeySpec keySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			byte[] encryptedWithoutB64 = Base64.decode(input,
					Base64.DEFAULT);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainTextPwdBytes = cipher.doFinal(encryptedWithoutB64);
			return plainTextPwdBytes;
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	@Override
	public byte[] decode(String input, String charsetName) {
		try {
			return decode(input.getBytes(charsetName));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}
	
	@Override
	public byte[] encode(byte[] input) {
		try {
			DESKeySpec keySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encrypedPwd = Base64.encode(cipher.doFinal(input), Base64.DEFAULT);
			return encrypedPwd;
		} catch (Exception e) {
			return super.encode(input);
		}
		
	}

	

	@Override
	public String encode(byte[] input, String charsetName) {
		try {
			DESKeySpec keySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.encodeToString(cipher.doFinal(input), Base64.DEFAULT);
		} catch (Exception e) {
			return super.encode(input,charsetName);
		}
	}
	
	
	
}
