package com.cg.fomatter;

import com.cg.model.Customer;
import com.cg.service.customer.ICustomerService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class FormatterCustomer implements Formatter<Customer> {

    private ICustomerService customerService;

    public FormatterCustomer (ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Customer parse(String id, Locale locale) throws ParseException {
        Optional<Customer> customer = customerService.findById(Long.valueOf(id));
        return customer.orElse(null);
    }

    @Override
    public String print(Customer customer, Locale locale) {
        return null;
    }
}
