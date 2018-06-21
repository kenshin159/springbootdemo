package com.family.tech.service.external;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.family.tech.connection.JerseyClientConnection;
import com.family.tech.constant.external.EmcProductField;

@Component
public class ProductExternalServiceImpl implements ProductExternalService {

	@Autowired
	private JerseyClientConnection jerseyClient;

	@Override
	public Integer getTotalProductByProductCode(String productCode) {
		String url = "http://localhost:9090/Service/products/total";
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(EmcProductField.PRODUCT_CODE, productCode);
		Response response = jerseyClient.doGet(url, queryParams);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		int result = response.readEntity(Integer.class);
		return result;
	}

}
