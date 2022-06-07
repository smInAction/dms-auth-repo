package com.ctc.dms.auth.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Container(containerName="dms-cosmo-collection")
@Getter 
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SiteUserDocument extends SiteUser {
	@Id
	@GeneratedValue
	@PartitionKey
	private long id;
	protected String username;
	@JsonIgnore
	protected String password;
}
