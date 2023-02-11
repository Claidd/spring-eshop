package com.hunt.springeshop.endpoint;

import com.hunt.springeshop.service.GreetingService;
import com.hunt.springeshop.ws.greeting.GetGreetingRequest;
import com.hunt.springeshop.ws.greeting.GetGreetingResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class GreetingEndpoint {

    public static final String NAMESPACE_URL = "http://hunt.com/springeshop/ws/greeting";
    private GreetingService greetingService;

    public GreetingEndpoint(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getGreetingRequest")
    @ResponsePayload //Полезная нагрузка
    public GetGreetingResponse getGreeting(@RequestPayload GetGreetingRequest request) throws DatatypeConfigurationException {
        GetGreetingResponse response = new GetGreetingResponse();
        response.setGreeting(greetingService.generateGreeting(request.getName()));
        return response;
    }

}
