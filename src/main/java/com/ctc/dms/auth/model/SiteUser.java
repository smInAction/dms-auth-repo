package com.ctc.dms.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public abstract class SiteUser {
	protected String username;
	protected String password;
}
