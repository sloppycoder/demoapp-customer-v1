package demo.bank.customer;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Random;

@RestController
@Slf4j
public class CustomerController {

  private final Tracer tracer;
  private final ExternalCustomerRepository repository;

  @Autowired
  public CustomerController(Tracer tracer, ExternalCustomerRepository repository) {
    this.tracer = tracer;
    this.repository = repository;
  }

  @GetMapping("/customers/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable("id") String customerId) {
    log.info("Retrieving Customer details for {}", customerId);

    try {
      Map<String, String> extCustomer = repository.getExternalCustomerById(customerId);
      Customer customer = new Customer();
      customer.setCustomerId(extCustomer.get("id"));
      customer.setLoginName(extCustomer.get("login_name"));
      customer.setName(extCustomer.get("name"));
      return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      log.warn("exception ", e);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
