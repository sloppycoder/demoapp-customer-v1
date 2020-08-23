package demo.bank.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("login_name")
    private String loginName;
    private String name;
}
