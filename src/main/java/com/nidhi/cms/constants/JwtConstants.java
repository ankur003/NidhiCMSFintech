package com.nidhi.cms.constants;

public class JwtConstants {

	/**
	 * 
	 *
	 * @author Ankur Bansala
	 */

	private JwtConstants() {
		//
	}

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTHORITIES_KEY = "scopes";
}
