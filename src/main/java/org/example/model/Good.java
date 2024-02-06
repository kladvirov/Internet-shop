package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private BigDecimal price;
    @Column(name = "create_date")
    private LocalDate createDate;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_goods_link",
            joinColumns = {@JoinColumn(name = "good_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")}
    )
    private Set <Order> orders = new HashSet<>();

    public Good(String name, String surname, BigDecimal price, LocalDate createDate, LocalDate expirationDate, Boolean isAvailable) {
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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
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
