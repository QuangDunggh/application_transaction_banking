package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Date;
import java.util.Optional;


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

    @PostMapping("/saveDeposit/{id}")
    private ModelAndView saveDeposit(@Validated @PathVariable("id") Long id,@RequestParam("transactionAmount") String transactionAmount,
                                     @ModelAttribute("deposit") Deposit deposit, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("/deposit/deposit");
        deposit = depositService.findById(id).get();
//        modelAndView.addObject("deposit",deposit);

        if (result.hasFieldErrors()) {
            modelAndView.addObject("deposit",deposit);
            return modelAndView;
        }
        if(!ValidateUtils.isNumber(transactionAmount)){
            modelAndView.addObject("deposit",deposit);
            modelAndView.addObject("message","This field must be number");
            return modelAndView;
        }
        deposit.setTransaction_amount(Long.valueOf(transactionAmount));
//        deposit.setTransaction_amount(deposit.getTransaction_amount());
        if (deposit.getTransaction_amount() <= 1000) {
            modelAndView.addObject("deposit",deposit);
            modelAndView.addObject("message", "Transaction can not smaller 1000");
            return modelAndView;
        } else if (deposit.getTransaction_amount() % 10 != 0) {
            modelAndView.addObject("deposit",deposit);
            modelAndView.addObject("message", "Transaction must be a multiple of 10");
            return modelAndView;
        } else {
           Customer customer = customerService.findById(deposit.getCustomerDeposit().getId()).get();
            Long newBalance = customer.getBalance() + deposit.getTransaction_amount();
            customer.setBalance(newBalance);
            deposit.getCustomerDeposit().setBalance(newBalance);
            customerService.save(customer);
            deposit.setUpdate_at(Date.valueOf(java.time.LocalDate.now()));
            depositService.save(deposit);
            deposit.setId(null);
            deposit.setTransaction_amount(0L);
            deposit.setCreate_at(Date.valueOf(java.time.LocalDate.now()));
            depositService.save(deposit);
            modelAndView.addObject("deposit",deposit);
            modelAndView.addObject("message","Deposit successfully!");
//            modelAndView.setViewName("redirect:/customer");
            return modelAndView;
        }

    }
}
