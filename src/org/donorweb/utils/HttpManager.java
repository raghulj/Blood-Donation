package org.donorweb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.donorweb.activity.AbstractDonateBloodActivity;

import android.app.Activity;

public class HttpManager {
	private static final DefaultHttpClient sClient;
	static {
		final HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");

		HttpConnectionParams.setStaleCheckingEnabled(params, false);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);

		HttpClientParams.setRedirecting(params, false);

		HttpProtocolParams.setUserAgent(params, "Donate Blood/0.1");

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				params, schemeRegistry);
		sClient = new DefaultHttpClient(manager, params);
	}

	private HttpManager() {
	}

	public static HttpResponse execute(HttpHead head) throws IOException {
		return sClient.execute(head);
	}

	public static HttpResponse execute(HttpHost host, HttpGet get)
			throws IOException {
		return sClient.execute(host, get);
	}

	public static HttpResponse execute(HttpGet get) throws IOException {
		return sClient.execute(get);
	}

	public static String rawPostURL(AbstractDonateBloodActivity activity,
			String url, List<NameValuePair> listParams,
			DefaultHttpClient defaultClient) {
		;

		final HttpPost httppost = new HttpPost(url);
		String responseString = null;
		try {
			if (listParams != null) {
				httppost.setEntity(new UrlEncodedFormEntity(listParams,
						HTTP.UTF_8));

			}
			HttpResponse response = defaultClient.execute(httppost);
			if (response != null) {
				String content = inputStreamToString(response.getEntity()
						.getContent());
				return content;
			}

			return responseString;
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;

		}

	}

	public static String postURL(AbstractDonateBloodActivity activity,
			String url, List<NameValuePair> params, boolean sslConnection) {
		if (sslConnection) {
			return rawPostURL(activity, url, params, sClient);
		} else {
			final DefaultHttpClient dClient = new DefaultHttpClient();
			return rawPostURL(activity, url, params, dClient);
		}

	}

	public static String postURL(Activity activity, String url,
			List<NameValuePair> params) {
		final DefaultHttpClient dClient = new DefaultHttpClient();
		return rawPostURL(activity, url, params, dClient);

	}

	public static String postURL(Activity activity, String url,
			List<NameValuePair> params, boolean sslConnection) {

		if (sslConnection) {
			System.out.println("Usecured url " + url);
			return rawPostURL(activity, url, params, sClient);
		} else {
			final DefaultHttpClient dClient = new DefaultHttpClient();
			System.out.println("UR LIS " + url);
			return rawPostURL(activity, url, params, dClient);
		}

	}

	// Used by other queries where BubblyActivity is not hte parent
	public static String rawPostURL(final Activity activity, String url,
			List<NameValuePair> listParams, DefaultHttpClient defaultClient) {
		final HttpPost httppost = new HttpPost(url);
		String responseString = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(listParams, HTTP.UTF_8));
			HttpResponse response = defaultClient.execute(httppost);
			if (response != null) {
				String content = inputStreamToString(response.getEntity()
						.getContent());
				return content;
			}

			return responseString;
		} catch (ClientProtocolException e) {

			activity.runOnUiThread(new Runnable() {

				public void run() {
					Utils.toast(activity, "No internet connection.");
				}
			});
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			activity.runOnUiThread(new Runnable() {

				public void run() {
					Utils.toast(activity,
							"Connectivity problem. Please try again later");
				}
			});

			return null;

		}

	}

	public static String inputStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();

		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		// Read response until the end
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Return full string
		return total.toString();
	}

}
