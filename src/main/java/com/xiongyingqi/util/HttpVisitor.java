package com.xiongyingqi.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author 瑛琪 <a href="http://xiongyingqi.com">xiongyingqi.com</a>
 * @version 2013-11-19 下午9:35:46
 */
public class HttpVisitor {
	
	/**
	 * 访问远程接口
	 * <br>2013-11-21 下午12:02:56
	 * @param address
	 * @param parameters
	 * @return
	 */
	public String visitRemoteApi(String address, Map<String, Set<String>> parameters) {
		HttpClientBuilder client = HttpClientBuilder.create();
		client.setUserAgent("YIXUN_CLIENT");
		final CloseableHttpClient closeableHttpClient = client.build();

		String rs = null;
		try {
			if(!address.startsWith("http://")){
				address =  "http://" + address;
			}
			URIBuilder uriBuilder = new URIBuilder().setPath(address);
			
			if(parameters != null){
				for (Entry<String, Set<String>> entry : parameters.entrySet()) {
					String key = entry.getKey();
					Set<String> values = entry.getValue();
					for (String value : values) {
						uriBuilder.addParameter(key, value);
					}
				}
			}
			URI uri = uriBuilder.build();
//			System.out.println(uri);
//			final HttpPost httpPost = new HttpPost(uri);
			
			final HttpGet httpPost = new HttpGet(uri);

			try {
				CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
//				Header[] headers = response.getAllHeaders();
//				System.out.println(" ------------------------------Headers------------------------------ ");
//				for (int i = 0; i < headers.length; i++) {
//					Header header = headers[i];
//					System.out.print(header.getName() + ": ");
//					System.out.print(header.getValue());
//					System.out.println();
//				}
//				System.out.println(" ------------------------------------------------------------ ");

//				System.out.println("StatusCode: " + response.getStatusLine().getStatusCode());
				
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();
				rs = FileHelper.readInputStreamToString(inputStream);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return rs;

	}

	public static Map<String, Set<String>> processKeyAndValue(Map<String, Set<String>> parameters, String key, String value) {
		if(parameters == null){
			parameters = new HashMap<String, Set<String>>();
		}
		
		Set<String> values = parameters.get(key);
		if (values == null) {
			values = new HashSet<String>();
		}
		values.add(value);
		parameters.put(key, values);
		return parameters;
	}
	
	
	/**
	 * 访问远程接口 <br>
	 * 2013-11-21 下午12:02:56
	 * 
	 * @param address
	 * @param parameters
	 * @return
	 */
	protected static InputStream visitRemoteApiForInputStream(String address,
			Map<String, Set<String>> parameters) {
		HttpClientBuilder client = HttpClientBuilder.create();
		client.setUserAgent("YIXUN_CLIENT");
		final CloseableHttpClient closeableHttpClient = client.build();

		try {
//			EntityHelper.print(address);
			if (!address.startsWith("http://")) {
				address = "http://" + address;
			}
			URIBuilder uriBuilder = new URIBuilder().setPath(address);
			if (parameters != null) {
				for (Entry<String, Set<String>> entry : parameters.entrySet()) {
					String key = entry.getKey();
					Set<String> values = entry.getValue();
					for (String value : values) {
						uriBuilder.addParameter(key, value);
					}
				}
			}

			URI uri = uriBuilder.build();
//			EntityHelper.print(uri);
			final HttpPost httpPost = new HttpPost(uri);
			
//			final HttpGet httpPost = new HttpGet(uri);

			try {
				CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
//				Header[] headers = response.getAllHeaders();
//				System.out
//						.println(" ------------------------------Headers------------------------------ ");
//				for (int i = 0; i < headers.length; i++) {
//					Header header = headers[i];
//					System.out.print(header.getName() + ": ");
//					System.out.print(header.getValue());
//					System.out.println();
//				}
//				System.out
//						.println(" ------------------------------------------------------------ ");
//
//				System.out.println("StatusCode: " + response.getStatusLine().getStatusCode());

				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();
				return inputStream;
				//				rs = FileHelper.readInputStreamToString(inputStream);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return null;

	}
	
	protected static String visitRemoteApiForString(String address,
			Map<String, Set<String>> parameters) {
		String rs = FileHelper.readInputStreamToString(visitRemoteApiForInputStream(address, parameters));
		return rs;
	}

	public static void main(String[] args) throws URISyntaxException {
		//		try {
		//			ThreadPool.setPoolSize(100);
		//		} catch (ThreadPoolException e) {
		//			e.printStackTrace();
		//		}
		//		Map<String, Set<String>> parameters = new HashMap<String, Set<String>>();
		//
		//		Set<String> strings = new HashSet<String>();
		//		strings.add("accurate");
		//		parameters.put("method", strings);
		//
		//		Set<String> values = new HashSet<String>();
		//		values.add("");
		//		parameters.put("prefix", values);
		//
		//		Set<String> values2 = new HashSet<String>();
		//		values2.add(".com");
		//		parameters.put("suffix", values2);
		//
		//		String rs = visitRemoteApi("http://www.xinnet.com/domain/search.do", parameters);
		//		System.out.println(rs);

		
		
		final File file = new File("xinnet.text");
		try {
			System.out.println(file.getCanonicalPath());
			FileHelper.appendStringToFile(file, "result:\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
		for (long i = 100146; i < 10000000L; i++) {//http://www.xinnet.com/domain/search.do?method=accurate&prefix=12306&suffix=.com
			final long j = i;
			Thread thread = new Thread(){
				@Override
				public void run() {
					
					Map<String, Set<String>> parameters = new HashMap<String, Set<String>>();

					Set<String> values = new HashSet<String>();
					values.add(j + "");
					parameters.put("domains", values);
					
					Set<String> valuesSuffixs = new HashSet<String>();
					valuesSuffixs.add(".com");
					parameters.put("suffixs", valuesSuffixs);
					
					Set<String> valuesV = new HashSet<String>();
					valuesV.add("0.1082007973454892");
					parameters.put("v", valuesV);
					
					Set<String> values2 = new HashSet<String>();
					values2.add("query");
					parameters.put("act", values2);
					
					String rs = visitRemoteApiForString(
							"http://www.west263.cn/services/domain/whois.asp", parameters);
					System.out.println(rs);
					if(rs.contains("b:;")){
						FileHelper.appendStringToFile(file, j + "\n");
						System.out.println("ok:" + j);
					}
				}
			};
		}
	}
}
