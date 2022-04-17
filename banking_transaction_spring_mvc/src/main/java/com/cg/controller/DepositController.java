package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import static javafx.scene.input.KeyCode.L;

@Controller
@RequestMapping("/deposit")
public class DepositController {

    @Autowired
    private IDepositService depositService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/save/{id}")
    private ModelAndView showFormDeposit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/deposit/deposit");
        Optional<Customer> customer = customerService.findById(id);
        Customer fakeCustomer = customer.get();
        Deposit deposit = new Deposit();
        deposit.setCreate_at(Date.valueOf(java.time.LocalDate.now()));
        deposit.setTransaction_amount(0L);
        deposit.setCustomerDeposit(fakeCustomer);
        depositService.save(deposit);
        modelAndView.addObject("deposit", deposit);
        return modelAndView;
    }

    @PostMapping("/save")
    private ModelAndView saveDeposit(@Validated @ModelAttribute("deposit") Deposit deposit, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("/deposit/deposit");
        System.out.println(deposit.getTransaction_amount());
        System.out.println(deposit.getId());
        if (result.hasFieldErrors()) {
            modelAndView.addObject("deposit",deposit);
            return modelAndView;
        }
        if (deposit.getTransaction_amount() <= 1000L) {
            modelAndView.addObject("deposit",deposit);
            System.out.println(deposit.getTransaction_amount());
            modelAndView.addObject("message", "Transaction can not smaller 1000");
            return modelAndView;
        } else if (deposit.getTransaction_amount() % 10 != 0) {
            modelAndView.addObject("deposit",deposit);
            modelAndView.addObject("message", "Transaction must be a multiple of 10");
            return modelAndView;
        } else {
            Optional<Customer> customer = customerService.findById(deposit.getCustomerDeposit().getId());
            Long balance_1 = customer.get().getBalance();
            Long balance_2 = balance_1 + deposit.getTransaction_amount();
            customer.get().setBalance(balance_2);
            deposit.setUpdate_at(Date.valueOf(java.time.LocalDate.now()));
            customerService.save(customer.get());
            depositService.save(deposit);
            modelAndView.addObject("deposit",deposit);
            return modelAndView;
        }

    }
}
