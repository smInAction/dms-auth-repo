package com.ctc.dms.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResponse {
	private final String jwt;
}
