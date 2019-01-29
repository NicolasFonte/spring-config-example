package com.nicolas.loja.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * If eureka server is not present url needs to be present.
 * Otherwise it should be ommited and service name should be placed as prefix
 */

// name is the name of the client, not the application
// @FeignClient(name = "products-service", url = "localhost:8085")
@FeignClient(name = "products-service")
@RibbonClient(name = "products-service")
public interface ProdutoServiceProxy {

    @GetMapping("/api/produtos/{productId}")
    ProdutoResponse retrieveExchangeValue(@PathVariable("productId") Integer productId);

}
