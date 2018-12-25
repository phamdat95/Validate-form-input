package com.codegym.cms.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String firstname;
    @NotEmpty
    @Size(min = 2, max = 30)
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    public Customer() {
    }

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;

    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s']", id, firstname, lastname);
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
