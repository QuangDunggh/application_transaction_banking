package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    private ModelAndView showListCustomer() {
        ModelAndView modelAndView = new ModelAndView("/customer/list-customer");
        Iterable<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/save")
    private ModelAndView showCreateCustomer() {
        ModelAndView modelAndView = new ModelAndView("/customer/createCustomer");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/save")
    private ModelAndView saveNewCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("/customer/createCustomer");
        if (result.hasFieldErrors()) {
            return modelAndView;
        } else {
            customer.setBalance(0L);
            customerService.save(customer);
            modelAndView.addObject("customer", new Customer());
            modelAndView.addObject("message", "Create new customer successful");
            return modelAndView;
        }
    }

    @GetMapping("/update/{id}")
    private ModelAndView showFormUpdateCustomer(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/updateCustomer");
        Optional<Customer> customer = customerService.findById(id);
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    @PostMapping("/update")
    private ModelAndView updateCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("/customer/updateCustomer");
        if(result.hasFieldErrors()){
            return modelAndView;
        }
        if (!customerService.existById(customer.getId())) {
            modelAndView.addObject("message","Id customer not exist");
            return modelAndView;
        }
        else {
            customerService.save(customer);
            modelAndView.setViewName("redirect: /customer/");
            return modelAndView;
        }
    }

    @GetMapping("/suspension/{id}")
    private ModelAndView showFormSuspension(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/suspensionCustomer");
        Optional<Customer> customer = customerService.findById(id);
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    @PostMapping("/suspension")
    private ModelAndView suspension(@ModelAttribute("customer") Customer customer) {
        ModelAndView modelAndView = new ModelAndView("redirect:/customer/");
        if(!customerService.existById(customer.getId())) {
            modelAndView.addObject("message","This id is not exist");
            return modelAndView;
        } else {
            customerService.remove(customer.getId());
            modelAndView.addObject("message","Suspension is successful");
            return modelAndView;
        }
    }

}
