package com.study.spring.transcation.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ClassName Account
 * @Author: pouldai
 * @Date: 2020/4/19 17:06
 * @Description:
 * @Version V1.0
 */
@Entity
public class Account {
    private int id;
    private String name;
    private Double money;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "money", nullable = true, precision = 0)
    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(name, account.name) &&
                Objects.equals(money, account.money);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, money);
    }
}
