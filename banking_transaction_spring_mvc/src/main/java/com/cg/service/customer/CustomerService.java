package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    private ICustomerRepository customerRepository;
    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return  customerRepository.existsById(id);
    }

    @Override
    public List<Customer> findAllCustomerWithoutId(Long id) {
        Iterable<Customer> customers = findAll();
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer: customers) {
            if(customer.getId() == id) {
                continue;
            } else {
                customerList.add(customer);
            }
        }
        return customerList;
    }
}
