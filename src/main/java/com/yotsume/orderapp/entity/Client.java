package com.yotsume.orderapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(name = "client_name", nullable = false, length = 30)
    private String name;

    @Column(name = "client_email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    @OneToOne(mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Profile profile;

    @OneToMany(mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "client_coupons",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Coupon> coupons = new HashSet<>();

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Утилитарные методы для управления связями (важно!)
    public void addOrder(Order order) {
        orders.add(order);
        order.setClient(this);
    }

    public void addCoupon(Coupon coupon) {
        coupons.add(coupon);
        coupon.getClients().add(this);
    }

    public void removeCoupon(Coupon coupon) {
        coupons.remove(coupon);
        coupon.getClients().remove(this);
    }
}
