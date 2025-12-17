package com.example.user_service.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users_amezon", uniqueConstraints = @UniqueConstraint(columnNames = "email_id"))
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String mobileNo;
    @NonNull
    private String address;
    @NonNull @Column(unique = true)
    private String emailId;
    @NonNull
    private String password;
    @NonNull
    private String acknowledgementId;

    @Column(nullable = false)
    private String role="USER";
}
