package com.ctc.dms.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//AUTO: it depends on JPA implementation how it is generated
	//IDENTITY: auto-increment column; DB generated PK after every insertion; supported by MySQL, SQL Server, Postgres, DB2
	//SEQUENCE: id will be increment based on DB sequence; supported by Oracle, Postgres
	//TABLE: DB table is used in assigning the primary key
	@Column
	private String username;
	@Column
	@JsonIgnore
	private String password;
	// @JsonIgnore is only used to Ignore a marked field from being serialized, de-serialized to and from JSON,
	// which means a field marked as @JsonIgnore can still be persisted in a JPA persistence.
	// @Transient is used as part of JPA to ignore a field from persisting if it is marked as @Transient.
	// A field marked @Transient will neither be persisted nor be serialized, de-serialized.
}
