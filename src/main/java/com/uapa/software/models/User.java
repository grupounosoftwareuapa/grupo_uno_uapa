package com.uapa.software.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_name", nullable = false)
	private String userName;
	
    @Column(name = "password", nullable = false)
	private String password;
	@ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	private List<Rol> roles;


}
