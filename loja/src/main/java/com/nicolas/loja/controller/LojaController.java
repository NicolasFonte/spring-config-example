package com.nicolas.loja.controller;

import com.nicolas.loja.client.ProdutoResponse;
import com.nicolas.loja.client.ProdutoServiceProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/loja")
public class LojaController {

    private final ProdutoServiceProxy produtoServiceProxy;

    public LojaController(ProdutoServiceProxy produtoServiceProxy) {
        this.produtoServiceProxy = produtoServiceProxy;
    }

    @PostMapping(value = "/{userId}/comprar")
    public ComprarResponse comprar(@PathVariable Integer userId, @RequestBody ProductIdRequest productIdRequest) {
        ProdutoResponse produtoResponse = produtoServiceProxy.retrieveExchangeValue(productIdRequest.getProductId());

        ComprarResponse comprarResponse = new ComprarResponse();
        comprarResponse.setProductId(produtoResponse.getId());
        comprarResponse.setUserId(userId);
        comprarResponse.setProductPrice(produtoResponse.getPrice());
        comprarResponse.setProductName(produtoResponse.getName());
        comprarResponse.setPort(produtoResponse.getServerPort());

        return comprarResponse;
    }

}
