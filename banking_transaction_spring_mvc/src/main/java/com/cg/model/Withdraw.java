package com.cg.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "withdraws")
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date create_at;
    private Long create_by;
    @ColumnDefault("0")
    private int deleted;
    private Date update_at;
    private Long update_By;
    @ManyToOne
    @JoinColumn(name = "customer_Id")
    private Customer customerWithDraw;
    private Long transaction_amount;

    public Withdraw() {
    }

    public Withdraw(Date create_at, Long create_by, int deleted, Date update_at, Long update_By,
                    Customer customerWithDraw, Long transaction_amount) {
        this.create_at = create_at;
        this.create_by = create_by;
        this.deleted = deleted;
        this.update_at = update_at;
        this.update_By = update_By;
        this.customerWithDraw = customerWithDraw;
        this.transaction_amount = transaction_amount;
    }



    public Withdraw(Long id, Date create_at, Long create_by, int deleted,
                    Date update_at, Long update_By, Customer customerWithDraw, Long transaction_amount) {
        this.id = id;
        this.create_at = create_at;
        this.create_by = create_by;
        this.deleted = deleted;
        this.update_at = update_at;
        this.update_By = update_By;
        this.customerWithDraw = customerWithDraw;
        this.transaction_amount = transaction_amount;
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

    public Long getUpdate_By() {
        return update_By;
    }

    public void setUpdate_By(Long update_By) {
        this.update_By = update_By;
    }

    public Customer getCustomerWithDraw() {
        return customerWithDraw;
    }

    public void setCustomerWithDraw(Customer customerWithDraw) {
        this.customerWithDraw = customerWithDraw;
    }

    public Long getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(Long transaction_amount) {
        this.transaction_amount = transaction_amount;
    }
}
