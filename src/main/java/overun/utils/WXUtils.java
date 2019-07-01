package overun.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @ClassName: WXUtils
 * @Description: 微信工具类
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 12:45
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */

public class WXUtils {
    private static Logger logger = LoggerFactory.getLogger(WXUtils.class);
    /**
     * 对字符串进行MD5运算(32位)
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String md5To32(String text) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = text.getBytes("utf-8");
            /** 使用MD5创建MessageDigest对象 */
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                /** 将没个数(int)b进行双字节加密 */
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {return null;}
    }


	/**
	 * 获取签名
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getSignNew(Map<String, String> map, String key) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String k = entry.getKey();
			Object v = entry.getValue();
			if (!StringUtils.isEmpty(v.toString())) {
				list.add(k + "=" + v + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.append("key=" + key).toString();
		logger.info("Sign Before MD5:" + result);
		result = md5To32(result).toUpperCase();
		logger.info("Sign Result:" + result);
		return result;
	}

    /**
     * 微信支付报文转xml字符串
     * @param map
     * @return
     */
    public static String mapXml(Map<String, String> map){
    	StringBuilder builder = new StringBuilder("<xml> \n");
    	for (String str : map.keySet()) {
    		builder.append("<" + str + "><![CDATA[" + map.get(str) + "]]></" + str + ">\n");
		}
    	builder.append("</xml>");
    	logger.info("拼接xml字符串为{}", builder.toString());
    	return builder.toString();
    }

    private static final String SHA_ALG = "HmacSHA1";

    /**
     * 生成随机数
     * @return
     */
    public static String nonce(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成时间戳
     * @return
     */
    public static String timestamp(){

    	long timeLong = System.currentTimeMillis();
    	return timeLong / 1000 + "";
    }


    public static String sign(String supplierKey, String timestamp, String nonce) throws InvalidKeyException {

    	String[] arr = new String[]{supplierKey, timestamp, nonce};
        /** 将supplierKey、timestamp、nonce、三个参数进行字典序排序 */
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        Mac mac = null;
        String signature = null;
        try {
        	mac = Mac.getInstance(SHA_ALG);
        	SecretKeySpec secret = new SecretKeySpec(supplierKey.getBytes(), mac.getAlgorithm());
            mac.init(secret);
            /** 将三个参数字符串拼接成一个字符串进行sha1加密 */
            byte[] digest = mac.doFinal(content.toString().getBytes());
            signature = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        return signature;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {

        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
    public static String getsign(String adminpass, String str) {
    	String sign1 = "";
    	try {
    		String sign = sign(adminpass);
    		sign1 = sign1(sign, str);
			System.out.println();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
    	return sign1;
    }

    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        /** 参数length，表示生成几位随机数 */
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            /** 输出字母还是数字 */
            if( "char".equalsIgnoreCase(charOrNum) ) {
                /** 输出是大写字母还是小写字母 */
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

	private static String sign(String supplierKey) throws InvalidKeyException {

	        StringBuilder content = new StringBuilder("supplierKey");
	        Mac mac = null;
	        String signature = null;
	        try {
	        	mac = Mac.getInstance(SHA_ALG);
	        	SecretKeySpec secret = new SecretKeySpec(supplierKey.getBytes(), mac.getAlgorithm());
	            mac.init(secret);
	            /** 将三个参数字符串拼接成一个字符串进行sha1加密 */
	            byte[] digest = mac.doFinal(content.toString().getBytes());
	            signature = byteToStr(digest);
	        } catch (NoSuchAlgorithmException e) {
	            logger.error(e.getMessage());
	        }
	        return signature;
	    }

	 private static String sign1(String supplierKey, String nonce) throws InvalidKeyException {

	    	String[] arr = new String[]{supplierKey, nonce};
	        /** 将supplierKey、timestamp、nonce、三个参数进行字典序排序 */
	        Arrays.sort(arr);
	        StringBuilder content = new StringBuilder("supplierKey");
	        for (int i = 0; i < arr.length; i++) {
	            content.append(arr[i]);
	        }
	        Mac mac = null;
	        String signature = null;
	        try {
	        	mac = Mac.getInstance(SHA_ALG);
	        	SecretKeySpec secret = new SecretKeySpec(supplierKey.getBytes(), mac.getAlgorithm());
	            mac.init(secret);
	            /** 将三个参数字符串拼接成一个字符串进行sha1加密 */
	            byte[] digest = mac.doFinal(content.toString().getBytes());
	            signature = byteToStr(digest);
	        } catch (NoSuchAlgorithmException e) {
	            logger.error(e.getMessage());
	        }
	        return signature;
	    }

	 public static void main(String[] args) {
		 String str  = nonce();
		System.out.println(str);
		System.out.println(getsign("admin1", str));
	}
}
