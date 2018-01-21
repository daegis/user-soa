package com.hnair.consumer.user.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hnair.consumer.order.util.ProxyIPPort;
import com.hnair.consumer.utils.system.ConfigPropertieUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.AbstractContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * http相关工具方法.
 * 
 * @author dangdang
 */
public abstract class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/** 代理名称. */
	private static final String PROXY_NAME = ConfigPropertieUtils.getString("proxy.name");

	/** 代理端口. */
	private static final String PROXY_PORT = ConfigPropertieUtils.getString("proxy.port");

	/** 代理用户 */
	private static final String PROXY_USERNAME = ConfigPropertieUtils.getString("proxy.userName");

	/** 代理端口. */
	private static final String PROXY_PASSWORD = ConfigPropertieUtils.getString("proxy.password");

	/** get请求. **/
	private static final String GET_METHOD = "get";

	/** post请求. **/
	private static final String POST_METHOD = "post";

	/** put请求. **/
	private static final String PUT_METHOD = "put";

	/** 是否使用代理. */
	private static final boolean IS_WITH_PROXY = isWithProxy();

	public static final String UTF8 = "UTF-8";

	public static final String GBK = "GBK";

	/** httpclient对应CONNECTION_TIMEOUT对应的值 */
	private static final Integer CONNECTION_TIMEOUT = ConfigPropertieUtils.getInteger("httpclient.connection.timeout",
			10000);

	/** httpclient对应SO_TIMEOUT对应的值 */
	private static final Integer SO_TIMEOUT = ConfigPropertieUtils.getInteger("httpclient.so.timeout", 10000);
	
	public static final String PROXY_URL = "http://www.xdaili.cn/ipagent/greatRecharge/getGreatIp?spiderId=a8699e125e064c35ac33ca3bf81eae48&orderno=YZ20174263539x6315C&returnType=2&count=1";
	
//	public static final String PROXY_URL = "http://www.xdaili.cn/ipagent//url/quality/8000000009938kf4A6Pnf?count=1";
	
	public static Map<String,ProxyIPPort> PROXYIPPORTS = new HashMap<String,ProxyIPPort>();
	
	private HttpUtils() {
		
	}


	private static void setProxy(DefaultHttpClient httpclient) {
		//httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY,
		//		new NTCredentials(PROXY_USERNAME, PROXY_PASSWORD, PROXY_NAME, "dangdang.com"));
		//List<String> authpref = new ArrayList<String>();
		//authpref.add(AuthPolicy.NTLM);
		//httpclient.getParams().setParameter(AuthPNames.PROXY_AUTH_PREF, authpref);
		HttpHost proxy = new HttpHost(PROXY_NAME, Integer.parseInt(PROXY_PORT));

		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	private static void setProxy(HttpClient httpClient) {
		httpClient.getHostConfiguration().setProxy(PROXY_NAME, Integer.valueOf(PROXY_PORT));
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getState().setProxyCredentials(org.apache.commons.httpclient.auth.AuthScope.ANY,
				new org.apache.commons.httpclient.NTCredentials(PROXY_USERNAME, PROXY_PASSWORD, PROXY_NAME,
						"dangdang.com"));
	}

	public static byte[] getBytes(final String urlStr) {

		return getBytes(urlStr, IS_WITH_PROXY);
	}
	
	
	public static byte[] getBytes(final String urlStr, boolean useProxy, int connectionTimeout, int soTimeout) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {

			if (urlStr.startsWith("https")) {
                CloseableHttpClient httpClient=null;

				if (useProxy) { // 使用代理获取url内容
                    logger.info("代理ip:"+PROXY_NAME+",代理端口:"+PROXY_PORT);
                    HttpHost proxy = new HttpHost(PROXY_NAME, Integer.parseInt(PROXY_PORT));//设置代理的机器IP,port?端口是多少?
                    DefaultProxyRoutePlanner httpRoutePlanner = new DefaultProxyRoutePlanner(proxy);
                    httpClient = HttpClients.custom().setRoutePlanner(httpRoutePlanner).setSSLSocketFactory(createSSLConnSocketFactory())
                            .build();

                }else{
                    httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                            .build();
                }
				HttpGet httpGet = new HttpGet(urlStr);
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectTimeout(5000).setConnectionRequestTimeout(5000)
						.setSocketTimeout(5000).build();
				httpGet.setConfig(requestConfig);
				CloseableHttpResponse response = null;
				String httpStr = null;

				response = httpClient.execute(httpGet);
				int statusCode = response.getStatusLine().getStatusCode();
				logger.info("访问url:"+urlStr+",返回状态码"+statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					return null;
				}
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				byte[] result =  IOUtils.toByteArray(in);
                in.close();
                return result;
			} else {

				URL url = new URL(urlStr);

				int port = url.getPort();
				if (port == -1) {
					port = url.getDefaultPort();
				}
				URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
				
				HttpHost targetHost = new HttpHost(url.getHost(), port, url.getProtocol());
				HttpGet httpget = new HttpGet(uri);
				// 设置连接一个url的连接等待超时时间
				if(connectionTimeout<=0){
					connectionTimeout=CONNECTION_TIMEOUT;
				}
				if (useProxy) { // 使用代理获取url内容
					setProxy(httpclient);
				}
				httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
				// 设置读取数据的超时时间
				if (soTimeout < 0) {
					soTimeout = SO_TIMEOUT;
				}
				httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

				HttpResponse response = httpclient.execute(targetHost, httpget);
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					return null;
				}
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
                byte[] result =  IOUtils.toByteArray(in);
                in.close();
                return result;
			}
		} catch ( Exception e) {
			e.printStackTrace();
			logger.error("urlStr:" + urlStr, e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}
	

	public static byte[] getBytes(final String urlStr, boolean useProxy) {
		return getBytes(urlStr, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	public static boolean writeFile(final String urlStr, final String filePath, boolean useProxy) {
		return writeFile(urlStr, filePath, useProxy, null, null);
	}

	public static boolean writeFile(final String urlStr, final String filePath, boolean useProxy,
			Integer connectionTimeout, Integer soTimeout) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		FileOutputStream fs = null;
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port, url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			if (connectionTimeout != null) {
				httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
						// 设置连接一个url的连接等待超时时间
						connectionTimeout);
			} else {
				httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
						// 设置连接一个url的连接等待超时时间
						CONNECTION_TIMEOUT);
			}

			// 设置读取数据的超时时间
			if (soTimeout != null) {
				httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
			} else {
				httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
			}

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return false;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			int byteread = 0;
			fs = new FileOutputStream(filePath);
			byte[] buffer = new byte[1204];
			while ((byteread = in.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			return true;
		} catch (final Exception e) {
			logger.error("", e);
			return false;
		} finally {
			if (fs != null) {
				try {
					fs.flush();
					fs.close();
				} catch (IOException e) {
				}
			}
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static byte[] getBytes(final String urlStr, boolean useProxy, boolean simulateBrowser) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port, url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			if (simulateBrowser) {
				httpget.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
				httpget.setHeader("Accept-Encoding", "gzip, deflate");
				httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
				httpget.setHeader("Connection", "keep-alive");
				// httpget.setHeader("Cookie", cookie);
				String host = "";
				if (urlStr.toLowerCase().startsWith("http://")) {
					host = urlStr.substring(7);
				} else {
					host = urlStr;
				}
				int index = host.indexOf("/");
				if (index > 0) {
					host = host.substring(0, index);
				}
				httpget.setHeader("Host", host);
				// httpget.setHeader("refer",
				// "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");
				httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
			}

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}
	
	public static byte[] getBytes(final String urlStr, boolean useProxy, Map<String,String> headers) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			if (useProxy) { // 使用代理获取url内容
				setProxy(httpclient);
			}
			URL url = new URL(urlStr);

			int port = url.getPort();
			if (port == -1) {
				port = url.getDefaultPort();
			}
			HttpHost targetHost = new HttpHost(url.getHost(), port, url.getProtocol());

			HttpGet httpget = new HttpGet(url.getFile());

			if (!CollectionUtils.isEmpty(headers)) {
				for(Entry<String,String> entry : headers.entrySet()){
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}

			// 设置连接一个url的连接等待超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
			// 设置读取数据的超时时间
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

			HttpResponse response = httpclient.execute(targetHost, httpget);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			return new String(bytes);
		}
		return "";
	}

	/**
	 * 获取url内容.
	 */
	public static String getContent(final String url, String charset) {
		byte[] bytes = getBytes(url);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}

	/**
	 * 获取url内容.
	 * 
	 * @param url
	 * @param charset
	 * @param simulateBrowser
	 *            是否设置相关参数模拟浏览器.
	 * @return
	 */
	public static String getContent(final String url, String charset, boolean simulateBrowser) {
		byte[] bytes = getBytes(url, IS_WITH_PROXY, simulateBrowser);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}
	
	public static String getContent(final String url, String charset, Map<String,String> headers) {
		byte[] bytes = getBytes(url, IS_WITH_PROXY, headers);
		if (bytes != null) {
			try {
				return new String(bytes, charset);
			} catch (UnsupportedEncodingException e) {
				logger.error("不支持的编码格式：" + charset, e);
			}
		}
		return "";
	}

	public static String getContentByPost(final String url, byte[] data) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(new ByteArrayInputStream(data));
		method.setRequestEntity(requestEntity);
		// 设置连接一个url的连接等待超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
		// 设置读取数据的超时时间
		client.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
		try {
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	public static String getContentByPost(final String url, String data, Map<String, String> headerMap) {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStreamRequestEntity requestEntity = new InputStreamRequestEntity(new StringInputStream(data));
		method.setRequestEntity(requestEntity);
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			method.setRequestHeader(entry.getKey(), entry.getValue());
		}
		// 设置连接一个url的连接等待超时时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
		// 设置读取数据的超时时间
		client.getHttpConnectionManager().getParams().setSoTimeout(SO_TIMEOUT);
		try {
			final int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	public static String getContent(final String url, String methodStr, Map<String, String> paramsMap, String encode,
			boolean useProxy, int connectionTimeout, int soTimeout) {
		final HttpClient httpClient = new HttpClient();

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		if (methodStr.toLowerCase().equals(GET_METHOD)) {
			method = new GetMethod(url);

			if (paramsMap.size() > 0) {
				NameValuePair[] params = getParamsFromMap(paramsMap);
				String queryString = EncodingUtil.formUrlEncode(params, encode);
				method.setQueryString(queryString);
			}
		} else if (methodStr.toLowerCase().equals(PUT_METHOD)) {
			method = new PutMethod(url);
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			NameValuePair[] params = getParamsFromMap(paramsMap);
			String queryString = EncodingUtil.formUrlEncode(params, encode);
			((PutMethod) method).setRequestBody(queryString);
		} else {
			method = new PostMethod(url);
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			NameValuePair[] params = getParamsFromMap(paramsMap);
			((PostMethod) method).setRequestBody(params);
		}

		try {
			// 设置连接一个url的连接等待超时时间
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
			// 设置读取数据的超时时间
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			// 客户端主动关闭连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	public static String getContent(final String url, String methodStr, Map<String, String> paramsMap, String encode,
			boolean useProxy, int connectionTimeout, int soTimeout, boolean isReturnError) {
		final HttpClient httpClient = new HttpClient();

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		if (methodStr.toLowerCase().equals(GET_METHOD)) {
			method = new GetMethod(url);

			if (paramsMap.size() > 0) {
				NameValuePair[] params = getParamsFromMap(paramsMap);
				String queryString = EncodingUtil.formUrlEncode(params, encode);
				method.setQueryString(queryString);
			}
		} else {
			method = new PostMethod(url);
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);
			NameValuePair[] params = getParamsFromMap(paramsMap);
			((PostMethod) method).setRequestBody(params);
		}

		try {
			// 设置连接一个url的连接等待超时时间
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
			// 设置读取数据的超时时间
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
				if (isReturnError) {
					InputStream stream = method.getResponseBodyAsStream();
					byte[] bytes = IOUtils.toByteArray(stream);
					return EncodingUtil.getString(bytes, method.getResponseCharSet());
				}

			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			// 客户端主动关闭连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	/**
	 * 通过http方式访问url调用接口.
	 * 
	 * @param url
	 *            接口地址
	 * @param methodStr
	 *            get或post方式
	 * @param paramsMap
	 *            参数列表
	 * @param encode
	 *            参数编码
	 * @param useProxy
	 *            是否使用代理
	 * @return
	 */
	public static String getContent(final String url, String methodStr, Map<String, String> paramsMap, String encode,
			boolean useProxy) {
		return getContent(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	public static String getContent(final String url, String methodStr, Map<String, String> paramsMap, String encode,
			boolean useProxy, boolean returnError) {
		return getContent(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT, returnError);
	}

	public static String getContentWithOutFormEncode(final String url, String methodStr, Map<String, String> paramsMap,
			String encode, boolean useProxy, int connectionTimeout, int soTimeout) {

		final HttpClient httpClient = new HttpClient();

		// 设置连接一个url的连接等待超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
		// 设置读取数据的超时时间
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

		if (useProxy) {
			setProxy(httpClient);
		}

		HttpMethodBase method = null;

		method = new GetMethod(url);

		StringBuffer queryString = new StringBuffer();
		Iterator it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry m = (Map.Entry) it.next();
			queryString.append(m.getKey()).append("=").append(m.getValue()).append("&");
		}

		method.setQueryString(queryString.toString().substring(0, queryString.length() - 1));

		try {
			final int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
			} else {
				// return method.getResponseBodyAsString();
				InputStream stream = method.getResponseBodyAsStream();
				byte[] bytes = IOUtils.toByteArray(stream);
				return EncodingUtil.getString(bytes, method.getResponseCharSet());
			}
		} catch (final Exception e) {
			logger.error("", e);
		} finally {
			method.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	/**
	 * Form- without urlencoding.
	 * 
	 * @param url
	 * @param methodStr
	 * @param paramsMap
	 * @param encode
	 * @param useProxy
	 * @return
	 */
	public static String getContentWithOutFormEncode(final String url, String methodStr, Map<String, String> paramsMap,
			String encode, boolean useProxy) {
		return getContentWithOutFormEncode(url, methodStr, paramsMap, encode, useProxy, CONNECTION_TIMEOUT, SO_TIMEOUT);
	}

	/**
	 * 从参数map中构建NameValuePair数组.
	 * 
	 * @param paramsMap
	 * @return
	 */
	private static NameValuePair[] getParamsFromMap(Map<String, String> paramsMap) {
		NameValuePair[] params = new NameValuePair[paramsMap.size()];
		int pos = 0;
		Iterator<String> iter = paramsMap.keySet().iterator();
		while (iter.hasNext()) {
			String paramName = iter.next();
			String paramValue = paramsMap.get(paramName);

			params[pos++] = new NameValuePair(paramName, paramValue);
		}

		return params;
	}

	/**
	 * 对Url发起请求.
	 * 
	 * @param url
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void requestUrl(final String url) throws IOException {
		final HttpClient httpClient = new HttpClient();
		final GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try {
			httpClient.executeMethod(getMethod);
		} finally {
			getMethod.releaseConnection();
		}
	}

	/**
	 * 是否使用代理.
	 */
	private static boolean isWithProxy() {
		return StringUtils.isNotBlank(PROXY_NAME) && StringUtils.isNotBlank(PROXY_PORT);
	}

	/**
	 * 
	 * @Description:发送类型为multipart/form-data的表单的请求
	 * @author wangdingtai
	 * @time:2015年10月19日 下午3:19:05
	 * @param url
	 * @param params
	 * @param fileMineType
	 * @param encode
	 * @param retryCount
	 * @return
	 */
	public static String getContentByMultiPost(String url, Map<String, Object> params, String fileMineType,
			Charset encode, Integer retryCount) {
		int i = 0;
		retryCount = (retryCount == null ? 0 : retryCount);
		org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		String responseMsg = null;
		while (true) {
			try {
				httpClient = new DefaultHttpClient();
				HttpParams httpParams = httpClient.getParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT); // 连接超时
				HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);// 读取数据超时
				logger.info("getContentByMultiPost url:{}", url);
				httpPost = new HttpPost(url);
				// 初始化请求参数
				logger.info("getContentByMultiPost params:{}", params);
				MultipartEntity entity = new MultipartEntity();
				if (params != null && !params.isEmpty()) {
					Set<Entry<String, Object>> set = params.entrySet();
					Iterator<Entry<String, Object>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, Object> entry = it.next();
						AbstractContentBody ctxBody = null;
						if (entry.getValue() instanceof File) {
							ctxBody = new FileBody((File) entry.getValue(), fileMineType);
						} else {
							ctxBody = new StringBody(entry.getValue().toString(), encode);
						}
						entity.addPart(entry.getKey(), ctxBody);
					}
				}
				httpPost.setEntity(entity);
				// 发送请求
				HttpResponse response = httpClient.execute(httpPost);
				Integer statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("getContentByMultiPost fail statusCode:{}", statusCode);
					i++;
					if (i > retryCount) {
						break;
					}
				} else {
					HttpEntity respEntity = response.getEntity();
					if (respEntity != null) {
						responseMsg = EntityUtils.toString(respEntity);
						if (StringUtils.isEmpty(responseMsg)) {
							logger.error("getContentByMultiPost responseMsg empty!");
						} else {
							logger.info("getContentByMultiPost responseMsg:{}", responseMsg);
						}
					}
					break;
				}
			} catch (Exception e) {
				logger.error("getContentByMultiPost exception=[{}]", ExceptionUtils.getFullStackTrace(e));
				i++;
				if (i > retryCount) {
					break;
				}
			} finally {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return responseMsg;
	}

	/**
	 * Description: 提交post请求 @Version1.0 2016-2-29 下午5:21:44 by
	 * 李超（lichaosz@dangdang.com）创建
	 * 
	 * @param url
	 * @param parameters
	 * @param charSet
	 * @return
	 */
	public static String postRequest(String url, Map<String, String> parameters, String charSet) {
		if (StringUtils.isBlank(charSet)) {
			charSet = "utf-8";
		}
		PostMethod post = getPostMethod(url, charSet);
		String result = null;
		try {
			HttpClient client = getHttpClient();
			if (null != parameters && parameters.size() > 0) {
				int paraSize = parameters.size();
				NameValuePair[] requestBody = new NameValuePair[paraSize];
				int i = 0;
				for (String key : parameters.keySet()) {
					NameValuePair uidValue = new NameValuePair(key, parameters.get(key));
					requestBody[i] = uidValue;
					i++;
				}
				post.setRequestBody(requestBody);
			}
			int returnCode = client.executeMethod(post);

			if (returnCode == 200) {
				result = getResult(post, charSet);
			}
		} catch (Exception e) {
			logger.error("HttpUtil uploadEcard error.", e);
		} finally {
			post.releaseConnection();
		}
		return result;
	}

	private static PostMethod getPostMethod(String url, String encode) {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setParameter("Connection", "Keep-Alive");
		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 20000);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + encode);
		return postMethod;
	}

	private static String getResult(HttpMethod method, String charSet) throws Exception {
		String result = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charSet));
		String tmp = "";
		while ((tmp = reader.readLine()) != null) {
			result += tmp + "\r\n";
		}
		reader = null;

		return result;
	}

	private static HttpClient getHttpClient() throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
		return httpClient;
	}

	/**
	 * 创建SSL安全连接
	 * 
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	/**
	 * 发送 SSL get 请求（HTTPS），K-V形式
	 * 
	 * @param apiUrl
	 *            API接口URL
	 * @return
	 */
	public static String getContentSSL(String apiUrl) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).build();
		HttpGet httpGet = new HttpGet(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return httpStr;
	}
	
	public static ProxyIPPort getProxyList() throws Exception{
		ProxyIPPort finalProxyIPPort = null;
		List<ProxyIPPort> proxyIPPorts = new ArrayList<ProxyIPPort>();
	    try {
			URL urlProxy = new URL(PROXY_URL);
			URLConnection connProxy = null;
			connProxy = urlProxy.openConnection();
			connProxy.setConnectTimeout(10000);
			InputStream inProxy = connProxy.getInputStream();  
		    String IPList = IOUtils.toString(inProxy, "utf-8");
		    Thread.sleep(10 * 1000);
		    
	    	JSONArray jsonArray=JSONObject.parseObject(IPList).getJSONArray("RESULT");
		    
		    if (jsonArray != null) {
	            for (int j = 0; j < jsonArray.size(); j++) {
	                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
	                String ip = jsonObject.getString("ip");
	                String port = jsonObject.getString("port");
	                
	                ProxyIPPort proxyIPPort = new ProxyIPPort();
	                proxyIPPort.setIp(ip);
	                proxyIPPort.setPort(port);
	                
	                proxyIPPorts.add(proxyIPPort);
	            }
	        }
		} catch (Exception e) {
			logger.info("-------------------------------------------------------");
			logger.info("getProxyList:获取代理IP地址失败");
			logger.info("-------------------------------------------------------");
		}
	    
	    
	    if(proxyIPPorts != null && proxyIPPorts.size() > 0){
	    	finalProxyIPPort = proxyIPPorts.get(0);
	    }
	    
		return  finalProxyIPPort;
	}


	public static String httpGetContentByProxy(String url, boolean isProxy){
		String content = null;
		try {
			ProxyIPPort finalProxyIPPort = null;
		    
		    if(isProxy){
		    	/**
		    	 * 先遍历验证通过的IP，如果验证通过的IP地址不能正常获取的话，再去获取新的IP地址
		    	 */
		    	{
		    		Map<String,ProxyIPPort> tempProxyIPPorts  = HttpUtils.PROXYIPPORTS;
		    		
		    		System.out.println("IP池子里边的代理IP个数为："+HttpUtils.PROXYIPPORTS.keySet().size());
		    		
			    	for (String key : HttpUtils.PROXYIPPORTS.keySet()) {
			    		content = null;
			    		finalProxyIPPort = HttpUtils.PROXYIPPORTS.get(key);
			    		Thread.sleep(1 * 1000);
				    	System.out.println("---------proxyIPPort-------------"+finalProxyIPPort.toString()+"-------------------");
			    		try {
				    		content = obtainContentByProxy(url, finalProxyIPPort);  
					        if(content.indexOf("验证码") > 0){
						    	System.out.println("获取失败");
						    	tempProxyIPPorts.remove(finalProxyIPPort.getIp()+"_"+finalProxyIPPort.getPort());
						    	content = null;
					        	continue;
					        }else{
						    	System.out.println("获取成功");
						    	break;
					        }
						} catch (Exception e) {
					    	System.out.println("获取失败，发生异常");
					    	tempProxyIPPorts.remove(finalProxyIPPort.getIp()+"_"+finalProxyIPPort.getPort());
							content = null;
							continue;
						}
			    	}
			    	
			    	HttpUtils.PROXYIPPORTS = tempProxyIPPorts;
		    	}
		    	
		    	/**
		    	 * 如果验证通过的IP拉取失败的话，怎么进行重新获取IP进行重新拉取
		    	 */
		    	{
			    	if(StringUtils.isBlank(content)){
			    		
			    		/**
			    		 * 获取一个新的IP
			    		 */
			    		finalProxyIPPort = getProxyList();
			    		
			    		int i = 0;
				    	while(i < 5){
				    		content = null;
				    		i++;
					    	System.out.println("---------proxyIPPort-------------"+finalProxyIPPort.toString()+"-------------------");
				    		try {
					    		content = obtainContentByProxy(url, finalProxyIPPort);  
						        if(content.indexOf("验证码") > 0){
							    	System.out.println("获取失败");
							    	content = null;
							    	finalProxyIPPort = getProxyList();
						        	continue;
						        }else{
							    	System.out.println("获取成功");
							    	HttpUtils.PROXYIPPORTS.put(finalProxyIPPort.getIp()+"_"+finalProxyIPPort.getPort(), finalProxyIPPort);
							    	break;
						        }
							} catch (Exception e) {
						    	System.out.println("获取失败，发生异常");
								finalProxyIPPort = getProxyList();
								content = null;
								continue;
							}
				    	}
			    	}
		    	}
			}else{
				URL urlSource = new URL(url);
				URLConnection conn = null;
				conn = urlSource.openConnection();
				
				conn.setConnectTimeout(10000);
				
		        InputStream in = conn.getInputStream();  
		        content = IOUtils.toString(in, "utf-8");  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	private static String obtainContentByProxy(String url, ProxyIPPort finalProxyIPPort)
			throws MalformedURLException, IOException {
		String content;
		URL urlSource = new URL(url);
		URLConnection conn = null;
		// 创建代理服务器  
		InetSocketAddress addr = new InetSocketAddress(finalProxyIPPort.getIp(), Integer.parseInt(finalProxyIPPort.getPort()));  
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); 
		conn = urlSource.openConnection(proxy);  
		
		conn.setConnectTimeout(3000);
		conn.setReadTimeout(3000);
		
		InputStream in = conn.getInputStream();  
		content = IOUtils.toString(in, "utf-8");
		return content;
	}
}