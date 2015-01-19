package cn.bvin.tools.secure;

public interface SecureWay {

	public byte[] decode(byte[] input);
	
	public byte[] encode(byte[] input);
}
