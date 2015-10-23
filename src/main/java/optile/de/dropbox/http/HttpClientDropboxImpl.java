package optile.de.dropbox.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public class HttpClientDropboxImpl implements HttpClientDropbox {
	private static final String HTTPS_API_DROPBOXAPI = "https://api.dropboxapi.com/1/metadata/auto/";
	private CloseableHttpClient httpclient;

	@Inject
	public HttpClientDropboxImpl(CloseableHttpClient httpclient) {
		super();
		this.httpclient = httpclient;
	}

	public String extractMimeTypeForFile(String accessToken, String path) {
		URI uri = null;
		try {
			uri = new URI("https", "api.dropboxapi.com", "/1/metadata/auto/" + path, null);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Authorization", "Bearer " + accessToken);

		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			ObjectMapper mapper = new ObjectMapper();
			JsonNode result = mapper.readTree(entity.getContent());
			JsonNode nodeValue = result.findValue("mime_type");
			String mime = nodeValue.textValue();
			EntityUtils.consume(entity);
			return mime;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;

	}

	@Override
	public String extractModifiedForFolder(String accessToken, String path) {

		URI uri = null;
		try {
			uri = new URI("https", "api.dropboxapi.com", "/1/metadata/auto/" + path, null);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader("Authorization", "Bearer " + accessToken);

		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			ObjectMapper mapper = new ObjectMapper();
			JsonNode result = mapper.readTree(entity.getContent());
			JsonNode nodeValue = result.findValue("modified");
			String mime = nodeValue.textValue();
			EntityUtils.consume(entity);
			return mime;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
