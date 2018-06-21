package com.family.tech.connection;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JerseyClientConnection {

	private static final int DEFAULT_TIMEOUT_CONNECTION = 30 * 1000;
	public static final Logger logger = LoggerFactory.getLogger(JerseyClientConnection.class);

	/**
	 * Executes the given GET request and returns the status code.
	 * 
	 * @param url
	 * @return the status code
	 */
	public int getStatusCodeFromRequestGet(String url) {
		int statusCode = 0;
		try {
			final ClientConfig config = createClientConfig();
			final Client client = ClientBuilder.newClient(config);
			final WebTarget webTarget = client.target(url);
			final Builder request = webTarget.request(MediaType.APPLICATION_JSON);

			final Response response = request.get();
			statusCode = response.getStatus();
		} catch (final Exception e) {
			logger.error(e.toString(), e);
		}
		return statusCode;
	}

	private static ClientConfig createClientConfig() {
		int timeout = DEFAULT_TIMEOUT_CONNECTION;
		final ClientConfig config = new ClientConfig();
		config.property(ClientProperties.CONNECT_TIMEOUT, timeout);
		config.property(ClientProperties.READ_TIMEOUT, timeout);
		config.register(new LoggingFeature(java.util.logging.Logger.getLogger(JerseyClientConnection.class.getName()),
				LoggingFeature.Verbosity.PAYLOAD_ANY));
		return config;
	}

	/**
	 * Executes the given POST request and returns the response.
	 * 
	 * @param entity
	 * @param url
	 * @return the response from the request
	 */
	public <T> Response doPost(T entity, String url) {
		final ClientConfig config = createClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget webTarget = client.target(url);
		final Builder request = webTarget.request(MediaType.APPLICATION_JSON);
		final Entity<T> body = Entity.entity(entity, MediaType.APPLICATION_JSON);

		return request.post(body);
	}

	/**
	 * Executes the given PUT request and returns the response.
	 * 
	 * @param entity
	 * @param url
	 * @return the response from the request
	 */
	public <T> Response doPut(T entity, String url) {
		final ClientConfig config = createClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget webTarget = client.target(url);
		final Builder request = webTarget.request(MediaType.APPLICATION_JSON);
		final Entity<T> body = Entity.entity(entity, MediaType.APPLICATION_JSON);
		return request.put(body);
	}

	/**
	 * Executes the given GET request with its parameters and returns the response.
	 * 
	 * @param param
	 * @param url
	 * @return the response from the request
	 */
	public Response doGet(String param, String url) {
		final ClientConfig config = createClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget webTarget = client.target(url + param);
		final Builder request = webTarget.request(MediaType.APPLICATION_JSON);
		return request.get();
	}

	/**
	 * Executes the given GET request and returns the response.
	 * 
	 * @param url
	 * @return the response from the request
	 */
	public Response doGet(String url) {
		final ClientConfig config = createClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget webTarget = client.target(url);
		final Builder request = webTarget.request(MediaType.APPLICATION_JSON);
		return request.get();
	}

	/**
	 * get with multiple params example:
	 * http:examp.com/ecco?param1=value1&param2=value2
	 * 
	 * @param url
	 * @return the response from the request
	 */
	public Response doGet(String url, Map<String, String> queryParams) {
		final ClientConfig config = createClientConfig();
		final Client client = ClientBuilder.newClient(config);
		String uri = "";
		try {
			uri = buildLinkFetchMultipleParams(url, queryParams);
		} catch (final UnsupportedEncodingException e) {
			logger.debug("Some param is incorrect", e);
		}
		final WebTarget webTarget = client.target(uri);
		final Builder request = webTarget.request(MediaType.APPLICATION_JSON);
		return request.get();
	}

	private static String buildLinkFetchMultipleParams(String link, Map<String, String> params)
			throws UnsupportedEncodingException {
		final UriBuilder uri = UriBuilder.fromUri(link);
		params.forEach((k, v) -> {
			try {
				if (v != null && !v.isEmpty()) {
					uri.queryParam(k, v);
				}
			} catch (final Exception e) {
				logger.debug("Error building link", e);
			}
		});
		return uri.toString();
	}

}
