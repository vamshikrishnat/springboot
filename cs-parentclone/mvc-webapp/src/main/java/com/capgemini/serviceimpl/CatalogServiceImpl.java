package com.capgemini.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capgemini.bean.CustomerFeedback;
import com.capgemini.bean.GiftCardCatalog;
import com.capgemini.bean.ProductCatalog;
import com.capgemini.constant.URLConstants;
import com.capgemini.service.CatalogService;

@Service
public class CatalogServiceImpl implements CatalogService {
	@Autowired
	RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);

	@Override
	public List<ProductCatalog> getProduct() {
		ProductCatalog[] cartLists = restTemplate.getForObject(URLConstants.GET_ALLPRODUCT, ProductCatalog[].class);
		List<ProductCatalog> list = new ArrayList<ProductCatalog>();
		for (int i = 0; i < cartLists.length; i++) {
			list.add(cartLists[i]);
		}
		return list;
	}

	@Override
	public List<ProductCatalog> searchProduct(String prodName) {
		ProductCatalog[] cartLists = restTemplate.postForObject(URLConstants.SEARCH_PRODUCT, ProductCatalog[].class,
				ProductCatalog[].class, prodName);
		List<ProductCatalog> list = new ArrayList<ProductCatalog>();
		for (int i = 0; i < cartLists.length; i++) {
			list.add(cartLists[i]);
		}
		return list;
	}

	@Override
	public List<ProductCatalog> categorySearch(String categoryName) {
		ProductCatalog[] cartLists = restTemplate.postForObject(URLConstants.CATEGORY_SEARCH, ProductCatalog[].class,
				ProductCatalog[].class, categoryName);
		List<ProductCatalog> list = new ArrayList<ProductCatalog>();
		for (int i = 0; i < cartLists.length; i++) {
			list.add(cartLists[i]);
		}
		return list;
	}

	@Override
	public List<CustomerFeedback> getProductDetails(String productId) {
		/*return restTemplate.getForEntity("http://feedback-rating/getbypid?productId="+productId, responseType, uriVariables)*/
		
		/*List<CustomerFeedback> customerFeedback=new ArrayList<>();
		
		CustomerFeedback[] responseEntity = restTemplate.getForEntity("http://feedback-rating/getbypid?productId="+productId, CustomerFeedback[].class).getBody();
		for (int i = 0; i < responseEntity.length; i++) {
			customerFeedback.add(responseEntity[i]);
		}
		return customerFeedback;*/
		
		return restTemplate
		.exchange("http://feedback-rating/feedback/getbypid?productId="+productId, HttpMethod.GET,
				null, new ParameterizedTypeReference<List<CustomerFeedback>>() {
				})
		.getBody();
	}
	
	@Override
	public GiftCardCatalog addGiftCard(GiftCardCatalog giftCardCatalog) {
		return restTemplate.postForObject("http://catalog-mgmt/addGift", giftCardCatalog, GiftCardCatalog.class);
	}

}
