package cn.bvin.tools.secure;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class ParamsEncoder {

	public enum Mode{Base64,DES}
	
	Map<String, Object> params;

	public ParamsEncoder(Map<String, Object> params) {
		super();
		this.params = params;
	}
	
	public byte[] encodeParameters(String paramsEncoding,Mode mode) {
        try {
        	String encodedStr = getStringFromMap(params,paramsEncoding);
        	if (mode!=null) {
        		 return encodedStr.getBytes(paramsEncoding);
			} else if (mode == Mode.Base64){
				return Base64.encode(encodedStr.getBytes(paramsEncoding),Base64.DEFAULT);
			} else if (mode == Mode.DES){
				//其实是需要一个key的，暂时先放encodedStr
				return DESFactory.get(encodedStr.getBytes(paramsEncoding)).encode(encodedStr.getBytes(paramsEncoding));
			} else {
				 return encodedStr.getBytes(paramsEncoding);
			}
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }
	
	private String getStringFromMap(Map<String, Object> map,String paramsEncoding){
		StringBuilder encodedParams = new StringBuilder();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				String encodedMapString = URLEncoder.encode(entry.getValue().toString(), paramsEncoding);
				if (encodedMapString!=null&&encodedMapString.length()>0) {
					encodedParams.append(URLEncoder.encode(entry.getKey(),paramsEncoding));

					encodedParams.append('=');

					encodedParams.append(URLEncoder.encode(entry.getValue().toString(), paramsEncoding));

					encodedParams.append('&');
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (encodedParams.toString().endsWith("&")) {
			encodedParams.deleteCharAt(encodedParams.length()-1);
		}
		return encodedParams.toString();
	}
}
