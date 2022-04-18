package com.cg.controller;


import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.service.customer.ICustomerService;
import com.cg.service.transfer.ITransferService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITransferService transferService;

    @GetMapping("/showListTransfer")
    private ModelAndView showListTransfer() {
        ModelAndView modelAndView = new ModelAndView("/transfer/list-transfer");
        Iterable<Transfer> transfers = transferService.findAll();
        modelAndView.addObject("transfers", transfers);
        return modelAndView;
    }

    @GetMapping("/save/{id}")
    private ModelAndView showForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/transfer/transfer");
        Transfer transfer = new Transfer();
        List<Customer> listRecipientCustomer = customerService.findAllCustomerWithoutId(id);
        Customer senderCustomer = customerService.findById(id).get();
        transfer.setCreate_at(Date.valueOf(java.time.LocalDate.now()));
        transfer.setCustomerSender(senderCustomer);
        transfer.setFee(10);
        transfer.setTransaction_amount(0L);
        transfer.setTransfer_amount(0L);
        transferService.save(transfer);
        modelAndView.addObject("listRecipientCustomer", listRecipientCustomer);
        modelAndView.addObject("transfer", transfer);
        return modelAndView;
    }

    @PostMapping("/saveTransfer/{id}")
    private ModelAndView save(@Validated @PathVariable("id") Long id,
                              @ModelAttribute("transfer") Transfer transfer,
                              @RequestParam("recipientId") Long recipientId,
                              @RequestParam("transactionAmount") String transactionAmount,
                              BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("/transfer/transfer");
        transfer = transferService.findById(id).get();
        List<Customer> customers = customerService.findAllCustomerWithoutId(transfer.getCustomerSender().getId());
        if (result.hasFieldErrors()) {
            modelAndView.addObject("transfer", transfer);
            modelAndView.addObject("listRecipientCustomer", customers);
            return modelAndView;
        }

        if (!ValidateUtils.isNumber(transactionAmount)) {
            modelAndView.addObject("transfer", transfer);
            modelAndView.addObject("listRecipientCustomer", customers);
            modelAndView.addObject("message", "The field must be number!!!");
            return modelAndView;
        }
        if (Long.valueOf(transactionAmount) >= 100000000000L) {
            modelAndView.addObject("transfer", transfer);
            modelAndView.addObject("listRecipientCustomer", customers);
            modelAndView.addObject("message", "The transaction amount cannot be greater than 100000000000");
            return modelAndView;
        }
        if (Long.valueOf(transactionAmount) > transfer.getCustomerSender().getBalance()) {
            modelAndView.addObject("transfer", transfer);
            modelAndView.addObject("listRecipientCustomer", customers);
            modelAndView.addObject("message", "The Transaction amount must be smaller the balance of sender");
            return modelAndView;
        } else if (Long.valueOf(transactionAmount) <= 1000) {
            modelAndView.addObject("transfer", transfer);
            modelAndView.addObject("listRecipientCustomer", customers);
            modelAndView.addObject("message", "The transaction amount cannot be less than 1000 ");
            return modelAndView;
        } else if (Long.valueOf(transactionAmount) % 10 != 0) {
            modelAndView.addObject("transfer", transfer);
            modelAndView.addObject("listRecipientCustomer", customers);
            modelAndView.addObject("message", "The transaction amount must be multiple of 10");
            return modelAndView;
        } else if (Objects.equals(transfer.getCustomerSender().getId(), recipientId)) {
            modelAndView.addObject("transfer", transfer);
            modelAndView.addObject("listRecipientCustomer", customers);
            modelAndView.addObject("message", "Can not transfer to yourself");
            return modelAndView;
        } else {
            if(!customerService.existById(recipientId)) {
                modelAndView.addObject("transfer",transfer);
                modelAndView.addObject("listRecipientCustomer",customers);
                modelAndView.addObject("message","Can not find the recipient, please try again");
                return modelAndView;
            }
            Customer customerSender = customerService.findById(transfer.getCustomerSender().getId()).get();
            Customer customerRecipient = customerService.findById(recipientId).get();
            transfer.setCustomerRecipient(customerRecipient);
            Long newBalanceRecipient = Long.valueOf(transactionAmount) + customerRecipient.getBalance();
            Long transferAmount = Long.valueOf(transactionAmount) + Long.valueOf(transactionAmount) * 10/100;
            Long newBalanceSender = customerSender.getBalance() -  transferAmount;
            transfer.setCustomerRecipient(customerRecipient);
            transfer.getCustomerRecipient().setBalance(newBalanceRecipient);
            transfer.getCustomerRecipient().setBalance(newBalanceSender);
            transfer.setTransfer_amount(transferAmount);
            transfer.setTransaction_amount(Long.valueOf(transactionAmount));
            transfer.setUpdate_at(Date.valueOf(java.time.LocalDate.now()));
            transfer.setFee_amount(Long.valueOf(transferAmount));
            customerSender.setBalance(newBalanceSender);
            customerRecipient.setBalance(newBalanceRecipient);
            transferService.save(transfer);
            customerService.save(customerRecipient);
            customerService.save(customerSender);
            transfer.setId(null);
            transfer.setCreate_at(Date.valueOf(java.time.LocalDate.now()));
            transfer.setFee(10);
            transfer.setTransfer_amount(0L);
            transfer.setTransaction_amount(0L);
            transfer.setCustomerSender(customerSender);
            transferService.save(transfer);
            modelAndView.addObject("transfer",transfer);
            modelAndView.addObject("listRecipientCustomer",customers);
            modelAndView.addObject("message","Transfer successfully");
            return modelAndView;
        }

    }
}
