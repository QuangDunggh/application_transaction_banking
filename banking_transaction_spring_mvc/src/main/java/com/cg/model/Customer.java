package com.cg.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Full name not empty")
    @Size(min = 6, max = 30, message = "Full Name required min 6 and max 30")
    private String fullName;
    @Email
    @NotBlank(message = "email not empty")
    private String email;
    @NotBlank(message = "Phone not empty")
    private String phone;
    @NotBlank(message = "Address not empty")
    private String address;
    @ColumnDefault("0")
    private Long balance;
    private Date create_at;
    private String create_by;
    private Date update_at;
    private String update_by;
    @ColumnDefault("0")
    private int deleted;

    @OneToMany(mappedBy = "customerWithDraw")
    private List<Withdraw> withdraws;

    @OneToMany(mappedBy = "customerDeposit", cascade = CascadeType.ALL)
    private List<Deposit> deposits;

    @OneToMany(mappedBy = "customerRecipient")
    private List<Transfer> transfersRecipient;

    @OneToMany(mappedBy = "customerSender")
    private List<Transfer> transfersSender;

    public Customer() {
    }

    public Customer(String fullName, String email, String phone, String address, Long balance,
                    Date create_at, String create_by, Date update_at, String update_by, int deleted, List<Withdraw> withdraws) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.create_at = create_at;
        this.create_by = create_by;
        this.update_at = update_at;
        this.update_by = update_by;
        this.deleted = deleted;
        this.withdraws = withdraws;
    }

    public Customer(String fullName, String email, String phone, String address,Long balance) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public Customer(Long id, String fullName, String email, String phone, String address, Long balance, Date create_at, String create_by,
                    Date update_at, String update_by, int deleted, List<Withdraw> withdraws) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.create_at = create_at;
        this.create_by = create_by;
        this.update_at = update_at;
        this.update_by = update_by;
        this.deleted = deleted;
        this.withdraws = withdraws;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public List<Withdraw> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(List<Withdraw> withdraws) {
        this.withdraws = withdraws;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public List<Transfer> getTransfersRecipient() {
        return transfersRecipient;
    }

    public void setTransfersRecipient(List<Transfer> transfersRecipient) {
        this.transfersRecipient = transfersRecipient;
    }

    public List<Transfer> getTransfersSender() {
        return transfersSender;
    }

    public void setTransfersSender(List<Transfer> transfers_sender) {
        this.transfersSender = transfers_sender;
    }
}
