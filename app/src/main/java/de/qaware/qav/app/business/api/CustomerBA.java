package de.qaware.qav.app.business.api;

import de.qaware.qav.app.api.Customer;

/**
 * @author QAware GmbH
 */
public interface CustomerBA {
    Customer getById(String id);

    Customer saveCustomer(Customer customer);
}
