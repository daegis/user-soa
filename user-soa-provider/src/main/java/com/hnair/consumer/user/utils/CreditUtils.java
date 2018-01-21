package com.hnair.consumer.user.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.net.URLCodec;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hnair.consumer.user.vo.BaseJinPengReqVo;
import com.hnair.consumer.utils.Base64Utils;
import com.hnair.consumer.utils.HMACSHA256Util;
import com.hnair.consumer.utils.MapUtils;
import com.hnair.consumer.utils.bean.BeanUtils;

public class CreditUtils {
	/**
	 * 金鹏参数加密
	 * @param req 参数
	 * @param jinPeng_signKey 密钥
	 * @return
	 * @throws Exception 
	 */
	public static String toSign(BaseJinPengReqVo req,String jinPeng_signKey) throws Exception {
		
		Map<String, Object> map = BeanUtils.convertBeanToMap(req);
		//首字母升序
		List<Entry<String,Object>> maps = MapUtils.sort(map);
		//组合待签名字符串
		StringBuffer sb=new StringBuffer();
		for (Map.Entry<String, Object> mapping : maps) {
			if (mapping.getKey().equals("sign")) {
				continue;
			}
			sb.append(mapping.getKey()).append("=").append(mapping.getValue()).append("&");
		}
		//签名
//		String sign =URLEncoder.encode(Base64Utils.encodeBytes(HMACSHA256Util.sha256_HMAC(sb.substring(0, sb.length() - 1), jinPeng_signKey)),"utf-8") ;
		String sign=Base64Utils.encodeBytes(HMACSHA256Util.sha256_HMAC(sb.substring(0, sb.length() - 1), jinPeng_signKey));
		
		return sign;
	}
	
	
	/**
	 * post方式请求
	 * 
	 * @param params
	 * @return
	 */
	public static String postMethod(String url, String params,String charset) {
		String result = null;
		URL urlStr = null;
		HttpURLConnection httpConn = null;
		InputStream is = null;
		ByteArrayOutputStream bao = null;
		try {
			urlStr = new URL(url);
			httpConn = (HttpURLConnection) urlStr.openConnection();
			//如果是https  
            if (httpConn instanceof HttpsURLConnection)  
            {  
            	SSLContext sc = SSLContext.getInstance("SSL");  
                sc.init(null, new TrustManager[]{new X509TrustManager(){

					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					@Override
					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					}

					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
                	
                }}, new java.security.SecureRandom());  
                ((HttpsURLConnection) httpConn).setSSLSocketFactory(sc.getSocketFactory());  
                ((HttpsURLConnection) httpConn).setHostnameVerifier(new HostnameVerifier(){

					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}});            
                
            }  
            
			httpConn.setRequestProperty("Content-Type", "application/text;charset="+charset);
			httpConn.setRequestProperty("Accept", "application/text");
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Charset", charset);
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			
			// 设置http连接属性
			httpConn.setConnectTimeout(10000);
			httpConn.setReadTimeout(10000);
			//httpConn.setRequestMethod("GET");
			
			if (params!=null && params.length()>0) {
				OutputStream os = httpConn.getOutputStream();
				os.write(params.getBytes("UTF-8"));
				os.close();
			}
			is = httpConn.getInputStream();
			
			String enc = httpConn.getContentEncoding();
			InputStreamReader isr=null;
			if (enc != null && enc.equals("gzip")) {
				java.util.zip.GZIPInputStream gzin = new java.util.zip.GZIPInputStream(is);
				isr = new InputStreamReader(gzin, "UTF-8");

			} else {
				isr = new InputStreamReader(is, "UTF-8");
			}

			BufferedReader bufferedReader=new BufferedReader(isr);
			StringBuffer stringBuffer=new StringBuffer();
			String string=null;
			while ((string=bufferedReader.readLine())!=null) {
				stringBuffer.append(string);
				
			}
			
			/*
			bao = new ByteArrayOutputStream();
			int b = 0;
			while ((b = is.read()) != -1) {
				bao.write(b);
			}*/

			is.close();
			httpConn.disconnect();
			//result = new String(bao.toByteArray(), "UTF-8");
			result=stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			urlStr = null;
			httpConn = null;
			is = null;
			bao = null;
		}
		return result;
	}
	  
}
