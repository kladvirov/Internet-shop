package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Good {
    private Long id;
    private String name;
    private String surname;
    private BigDecimal price;
    private LocalDate createDate;
    private LocalDate expirationDate;
    private Boolean isAvailable;

    public Good(Long id, String name, String surname, BigDecimal price, LocalDate createDate, LocalDate expirationDate, Boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.price = price;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.isAvailable = isAvailable;
    }

    public Good() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", price=" + price +
                ", createDate=" + createDate +
                ", expirationDate=" + expirationDate +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
