package de.qaware.qav.app.business.impl;

import de.qaware.qav.app.api.Customer;
import de.qaware.qav.app.business.api.CustomerBA;
import de.qaware.qav.app.util.MyStringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author QAware GmbH
 */
@Component
public class CustomerBAImpl implements CustomerBA {

    @Override
    public Customer getById(String id) {
        Customer result = new Customer();
        if (MyStringUtils.isEmpty(id)) {
            return result;
        } else {
            result.setId(id);
            result.setName("John");
            return result;
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (MyStringUtils.isEmpty(customer.getId())) {
            customer.setId(UUID.randomUUID().toString());
        }
        return customer;
    }

}
