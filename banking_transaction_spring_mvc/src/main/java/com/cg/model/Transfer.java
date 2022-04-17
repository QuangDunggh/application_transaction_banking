package com.cg.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date create_at;
    private Long create_by;
    @ColumnDefault("0")
    private int deleted;
    private Date update_at;
    private Long update_by;
    @ColumnDefault("10")
    private int fee;
    private Long fee_amount;
    private Long transaction_amount;
    private Long transfer_amount;
    @ManyToOne
    @JoinColumn(name = "recipient_Id")
    private Customer customerRecipient;
    @ManyToOne
    @JoinColumn(name = "sender_Id")
    private Customer customerSender;

    public Transfer() {
    }

    public Transfer(Long id, Date create_at, Long create_by, int deleted,
                    Date update_at, Long update_by, int fee, Long fee_amount, Long transaction_amount, Long transfer_amount, Customer customerRecipient, Customer customerSender) {
        this.id = id;
        this.create_at = create_at;
        this.create_by = create_by;
        this.deleted = deleted;
        this.update_at = update_at;
        this.update_by = update_by;
        this.fee = fee;
        this.fee_amount = fee_amount;
        this.transaction_amount = transaction_amount;
        this.transfer_amount = transfer_amount;
        this.customerRecipient = customerRecipient;
        this.customerSender = customerSender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Long getCreate_by() {
        return create_by;
    }

    public void setCreate_by(Long create_by) {
        this.create_by = create_by;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public Long getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(Long update_by) {
        this.update_by = update_by;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Long getFee_amount() {
        return fee_amount;
    }

    public void setFee_amount(Long fee_amount) {
        this.fee_amount = fee_amount;
    }

    public Long getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(Long transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public Long getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(Long transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public Customer getCustomerRecipient() {
        return customerRecipient;
    }

    public void setCustomerRecipient(Customer customerRecipient) {
        this.customerRecipient = customerRecipient;
    }

    public Customer getCustomerSender() {
        return customerSender;
    }

    public void setCustomerSender(Customer customerSender) {
        this.customerSender = customerSender;
    }
}
