package com.nicolas.produtos.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.nicolas.produtos.config.ProductConfiguration;
import java.util.Arrays;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {

    private List<Produto> produtos = Arrays.asList(
            new Produto(1, "p1", 10.0),
            new Produto(2, "p2", 20.0));

    private final Environment environment;

    private final ProductConfiguration productConfiguration;

    public ProdutoController(Environment environment, ProductConfiguration productConfiguration) {
        this.environment = environment;
        this.productConfiguration = productConfiguration;
    }


    /**
     * Get config from spring cloud config server
     */
    @GetMapping(value = "/config")
    public ProductConfiguration retrieveProductConfig() {
        return productConfiguration;
    }

    @GetMapping("/config-fault-tolerance")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    public ProductConfiguration retrieveConfiguration() {
        throw new RuntimeException("Not available");
    }

    public ProductConfiguration fallbackRetrieveConfiguration() {
        ProductConfiguration productConfiguration = new ProductConfiguration();
        productConfiguration.setMaxPrice(1);
        productConfiguration.setMinPrice(1000);

        return productConfiguration;
    }

    /**
     * Get a sample product with a port for the current server
     */
    @GetMapping("/{productId}")
    public Produto findProdutoByName(@PathVariable Integer productId) {
        Produto produto = produtos.stream()
                .filter(p -> p.getId() == productId)
                .findFirst()
                .orElseThrow(RuntimeException::new);

        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        produto.setServerPort(port);

        return produto;
    }

    private class Produto {

        private int id;
        private String name;
        private double price;
        private int serverPort;

        public Produto() {

        }

        Produto(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setServerPort(int serverPort) {
            this.serverPort = serverPort;
        }

        public int getServerPort() {
            return serverPort;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
