eureka - http://localhost:8761/

config server: http://localhost:8888/products-service/default, property spring.cloud.config.server.git.uri

produtos service: (rodar multiplos em diferentes portas) bootstrap.yml
        - hystrix fault tolerance: http://localhost:8085/api/produtos/config-fault-tolerance
        - produtos: http://localhost:8085/api/produtos/1

loja service: 
        - post url: http://localhost:8185/api/loja/1/comprar
        - body: 
        {
        	"productId" : 1
        }