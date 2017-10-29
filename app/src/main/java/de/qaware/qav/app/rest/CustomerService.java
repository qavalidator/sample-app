package de.qaware.qav.app.rest;

import de.qaware.qav.app.api.Customer;
import de.qaware.qav.app.business.api.CustomerBA;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author QAware GmbH
 */
@RestController
public class CustomerService {

    private static final Logger LOGGER = getLogger(CustomerService.class);
    private final CustomerBA customerBA;

    @Autowired
    public CustomerService(CustomerBA customerBA) {
        this.customerBA = customerBA;
    }

    @GetMapping(path = "/{id}")
    public Customer findCustomer(@PathVariable String id) {
        return customerBA.getById(id);
    }

    @PostMapping(path = "/")
    public Customer saveCustomer(@RequestBody Customer customer) {
        LOGGER.info("saveCustomer: {}", customer);
        return customerBA.saveCustomer(customer);
    }
}
