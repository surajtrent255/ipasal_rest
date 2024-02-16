package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ishanitech.ipasal.ipasalwebservice.dto.PayLoadDTO;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentGateway {

    @PostMapping("/")
    public Response<?> payByKhalti(@RequestBody Map<?, ?> payload ){
        System.out.println("--------" + payload);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Key cac546a42f434c3a809a40a02ada88a7");
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity<?> httpEntity = new HttpEntity<>(payload, httpHeaders);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity("https://a.khalti.com/api/v2/epayment/initiate/", httpEntity, Map.class);
//        Map <?, ?> name = (Map<?, ?>) responseEntity;
        return Response.ok(responseEntity.getBody(), responseEntity.getStatusCodeValue(),String.valueOf(responseEntity.getStatusCode()));

    }
}
