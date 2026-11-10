package com.example.cryptoexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "dtype")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AdminUser.class, name = "ADMIN"),
    @JsonSubTypes.Type(value = ManagerUser.class, name = "MANAGER"),
    @JsonSubTypes.Type(value = CustomerUser.class, name = "CUSTOMER")
})
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}