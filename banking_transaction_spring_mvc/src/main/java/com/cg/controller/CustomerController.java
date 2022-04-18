package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.transfer.ITransferService;
import com.cg.service.withdraws.IWithdrawService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private ITransferService transferService;

    @GetMapping
    private ModelAndView showListCustomer() {
        ModelAndView modelAndView = new ModelAndView("/customer/list-customer");
        Iterable<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("deposit/{id}")
    private ModelAndView showListCustomerAfterDeposit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/list-customer");
        if (!depositService.existById(id)) {
            modelAndView.setViewName("redirect:/customer");
            return modelAndView;
        }
        Iterable<Customer> customers = customerService.findAll();
        depositService.remove(id);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("withdraw/{id}")
    private ModelAndView showListCustomerAfterWithdraw(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/list-customer");
        if(!withdrawService.existById(id)) {
            modelAndView.setViewName("redirect:/customer");
            return modelAndView;
        }
        Iterable<Customer> customers = customerService.findAll();
        withdrawService.remove(id);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/transfer/{id}")
    private ModelAndView showListCustomerAfterTransfer(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/list-customer");
        if(!transferService.existById(id)) {
            modelAndView.setViewName("redirect:/customer");
            return modelAndView;
        }
        Iterable<Customer> customers = customerService.findAll();
        transferService.remove(id);
        modelAndView.addObject("customers",customers);
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
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/update")
    private ModelAndView updateCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("/customer/updateCustomer");
        if (result.hasFieldErrors()) {
            return modelAndView;
        }
        if (!customerService.existById(customer.getId())) {
            modelAndView.addObject("message", "Id customer not exist");
            return modelAndView;
        } else {
            customerService.save(customer);
            modelAndView.addObject("customer",customer);
            modelAndView.addObject("message","Update successfully");
            return modelAndView;
        }
    }

    @GetMapping("/suspension/{id}")
    private ModelAndView showFormSuspension(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/customer/suspensionCustomer");
        Optional<Customer> customer = customerService.findById(id);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/suspension")
    private ModelAndView suspension(@ModelAttribute("customer") Customer customer) {
        ModelAndView modelAndView = new ModelAndView("redirect:/customer/");
        if (!customerService.existById(customer.getId())) {
            modelAndView.addObject("message", "This id is not exist");
            return modelAndView;
        } else {
            customerService.remove(customer.getId());
            modelAndView.addObject("message", "Suspension is successful");
            return modelAndView;
        }
    }

}
