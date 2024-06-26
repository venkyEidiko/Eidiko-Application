package com.eidiko.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Data
public class Roles_Table {

	@Id
	private Integer roleId;
	private String name;

	public Roles_Table() {
	}

	public Roles_Table(Integer roleId, String name) {
		this.name = name;
		this.roleId = roleId;
	}
}
