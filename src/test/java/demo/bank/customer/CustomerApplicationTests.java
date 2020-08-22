package demo.bank.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerApplicationTests {

  @LocalServerPort String port;
  @Autowired
  RestTemplateBuilder builder;

  @Test
  void context_loads() {}

  @Test
  void customerService_running() {
    String url = "http://127.0.0.1:"+port+"/customers/1234";
    HttpEntity<Customer> response = builder.build().getForEntity(url, Customer.class);
    Assertions.assertEquals("1234", response.getBody().getCustomerId());
  }
}
