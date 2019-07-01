package overun.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpPostUtil {
	public static String getRemotePortData(String urls, String param) {
		try {
			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			/** 设置连接超时时间 */
			conn.setConnectTimeout(30000);
			/** 设置读取超时时间 */
			conn.setReadTimeout(30000);
			conn.setRequestMethod("POST");
			/** 需要输出 */
			conn.setDoInput(true);
			/** 需要输入 */
			conn.setDoOutput(true);
			/** 设置是否使用缓存 */
			conn.setUseCaches(false);
			/** 设置请求属性 */
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			/** 维持长连接 */
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			if (!StringUtils.isEmpty(param)) {
				/** 建立输入流，向指向的URL传入参数 */
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				dos.writeBytes(param);
				dos.flush();
				dos.close();
			}
			/** 输出返回结果 */
			InputStream input = conn.getInputStream();
			int resLen = 0;
			byte[] res = new byte[1024];
			StringBuilder sb = new StringBuilder();
			while ((resLen = input.read(res)) != -1) {
				sb.append(new String(res, 0, resLen, "utf-8"));
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
