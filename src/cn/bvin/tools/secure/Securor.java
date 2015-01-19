package cn.bvin.tools.secure;

import java.io.UnsupportedEncodingException;

public class Securor implements SecureWay{

	@Override
	public byte[] decode(byte[] input) {
		return input;
	}

	@Override
	public byte[] encode(byte[] input) {
		return input;
	}

	
	public byte[] decode(String input,String charsetName) {
		try {
			return decode(input.getBytes(charsetName));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}

	public String encode(byte[] input,String charsetName) {
		try {
			return new String(encode(input), charsetName);
		} catch (UnsupportedEncodingException e) {
			 throw new AssertionError(e);
		}
	}
}
