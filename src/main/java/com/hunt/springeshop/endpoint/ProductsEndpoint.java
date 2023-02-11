package com.hunt.springeshop.endpoint;

import com.hunt.springeshop.dto.ProductDTO;
import com.hunt.springeshop.service.ProductService;
import com.hunt.springeshop.ws.products.GetProductsResponse;
import com.hunt.springeshop.ws.products.ProductsWS;
import com.hunt.springeshop.ws.products.copy.GetProductsRequest;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

public class ProductsEndpoint {
    public static final String NAMESPACE_URL = "http://hunt.com/springeshop/ws/products";
    private ProductService productService;

    public ProductsEndpoint(ProductService productService) {
        this.productService = productService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductsRequest")
    @ResponsePayload //Полезная нагрузка
    public GetProductsResponse getProductsWS(@RequestPayload GetProductsRequest request) throws DatatypeConfigurationException {
        GetProductsResponse response = new GetProductsResponse();
        productService.getAll().forEach(dto -> response.getProducts().add(createProductsWs(dto)));
        return response;
    }

    private ProductsWS createProductsWs(ProductDTO dto){
        ProductsWS ws = new ProductsWS();
        ws.setId(dto.getId());
        ws.setPrice(Double.parseDouble(dto.getPrice().toString()));
        ws.setTitle(dto.getTitle());
        return ws;
    }
}
