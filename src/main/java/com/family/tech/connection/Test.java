package com.family.tech.connection;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JerseyClientConnection client = new JerseyClientConnection();
		String url = "http://localhost:9090/Service/products/total";
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("productCode", "1234");
		Response response = client.doGet(url, queryParams);
		System.out.println("content: " + response.readEntity(String.class));
		System.out.println("ABC");
	}

}
