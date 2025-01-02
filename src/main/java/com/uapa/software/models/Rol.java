package com.uapa.software.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_rol")
public class Rol {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @Column(name = "name", nullable = false)
	private String name;

}
