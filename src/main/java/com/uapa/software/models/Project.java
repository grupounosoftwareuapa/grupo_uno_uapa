package com.uapa.software.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
	
	private int id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	
}
