package com.nidhi.cms.security;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;
import static com.nidhi.cms.constants.JwtConstants.HEADER_STRING;
import static com.nidhi.cms.constants.JwtConstants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Autowired
	private UserService userservice;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = extractAuthToken(req);
		String username = null;
		String authToken = null;
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			authToken = header.replace(TOKEN_PREFIX, "");
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
			}
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (BooleanUtils.isTrue(jwtTokenUtil.validateToken(authToken, userDetails))) {
				UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken,
						userDetails);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		//validateWhiteListIp(req, username);
		chain.doFilter(req, res);
	}

	private void validateWhiteListIp(HttpServletRequest req, String username) {
		if (username == null) {
			return;
		}
		String ip = getRemoteIpAddress(req);
		if (StringUtils.isBlank(ip)) {
			throw new IllegalArgumentException("Ip not found exception");
		}
		User user = userservice.getUserByUserEmailOrMobileNumber(username, username);
		if (user == null) {
			throw new IllegalArgumentException("Auth Token not valid");
		}
		if (!ip.equals(user.getWhiteListIp())) {
			throw new IllegalArgumentException("please white list the Ip - " + ip);
		}
	}

	public static String getRemoteIpAddress(final HttpServletRequest httpServletRequest) {
		String remoteIpAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");

		if (StringUtils.isBlank(remoteIpAddress)) {
			remoteIpAddress = httpServletRequest.getHeader("X-REAL-IP");
		}
		if (StringUtils.isBlank(remoteIpAddress)) {
			remoteIpAddress = httpServletRequest.getRemoteAddr();
		}
		return remoteIpAddress;
	}

	private String extractAuthToken(HttpServletRequest req) {
		String header = req.getHeader(HEADER_STRING);
		if (StringUtils.isNotBlank(header)) {
			return header;
		}
		Object token = req.getServletContext().getAttribute(AUTH_TOKEN);
		if (token != null) {
			return token.toString();
		}
		return StringUtils.EMPTY;
	}
}