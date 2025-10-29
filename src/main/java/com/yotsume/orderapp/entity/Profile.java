package com.yotsume.orderapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profiles")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, referencedColumnName = "id")
    private Client client;

    public Profile(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}
