package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.Withdraw;
import com.cg.service.customer.ICustomerService;
import com.cg.service.withdraws.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

@Controller
@RequestMapping("/withdraw")
public class WithdrawController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IWithdrawService withdrawService;

    @GetMapping("/save/{id}")
    private ModelAndView showFormWithDraw(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/withDraw/withdraw");
        Withdraw withdraw = new Withdraw();
        Customer customer = customerService.findById(id).get();
        withdraw.setTransaction_amount(0L);
        withdraw.setCreate_at(Date.valueOf(java.time.LocalDate.now()));
        withdraw.setCustomerWithDraw(customer);
        withdrawService.save(withdraw);
        modelAndView.addObject("withdraw",withdraw);
        return modelAndView;
    }

    @PostMapping("/saveWithdraw/{id}")
    private ModelAndView saveWithdraw(@Validated @PathVariable("id") Long id,
                                      @ModelAttribute Withdraw withdraw,
                                      @RequestParam("withdrawAmount") Long withdrawAmount,
                                      BindingResult result){
        ModelAndView modelAndView = new ModelAndView("/withDraw/withdraw");
        withdraw = withdrawService.findById(id).get();
        Customer customer = customerService.findById(withdraw.getCustomerWithDraw().getId()).get();
        withdraw.getCustomerWithDraw().setBalance(customer.getBalance());
        if(result.hasFieldErrors()){
            modelAndView.addObject("withdraw",withdraw);
            return modelAndView;
        }
        if(withdrawAmount <= 1000){
            modelAndView.addObject("withdraw",withdraw);
            modelAndView.addObject("message","Withdraw amount must be not smaller 1000");
            return modelAndView;
        }
        if(withdrawAmount >= 100000000000L) {
            modelAndView.addObject("withdraw",withdraw);
            modelAndView.addObject("message","Withdraw amount must be smaller 100000000000");
            return modelAndView;
        }
        if(withdraw.getCustomerWithDraw().getBalance() < withdrawAmount) {
            modelAndView.addObject("withdraw",withdraw);
            modelAndView.addObject("message","Withdraw amount must be smaller the balance");
            return modelAndView;
        } else if(withdrawAmount % 10 != 0) {
            modelAndView.addObject("withdraw",withdraw);
            modelAndView.addObject("message","Withdraw amount must be a multiple of 10 ");
            return modelAndView;
        } else {
            Long newBalance = customer.getBalance() - withdrawAmount;
            customer.setBalance(newBalance);
            withdraw.setTransaction_amount(withdrawAmount);
            withdraw.getCustomerWithDraw().setBalance(newBalance);
            withdraw.setUpdate_at(Date.valueOf(java.time.LocalDate.now()));
            customerService.save(customer);
            withdrawService.save(withdraw);
            withdraw.setId(null);
            withdraw.setCreate_at(Date.valueOf(java.time.LocalDate.now()));
            withdraw.setTransaction_amount(0L);
            withdrawService.save(withdraw);
            modelAndView.addObject("withdraw",withdraw);
            modelAndView.addObject("message","Withdraw successfully");
            return modelAndView;
        }
    }
}
