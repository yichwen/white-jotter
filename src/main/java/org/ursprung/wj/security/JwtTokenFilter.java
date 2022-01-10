package org.ursprung.wj.security;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ursprung.wj.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    private JwtUtil jwtUtil;

    public JwtTokenFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorization) && authorization.startsWith(BEARER)) {
            String token = authorization.substring(7);
            if (!jwtUtil.isTokenExpired(token)) {
                Claims claimsFromToken = jwtUtil.getClaimsFromToken(token);
                Map<String, Object> userInfo = (Map<String, Object>) claimsFromToken.get("user");
                List<Map<String, Object>> authorities = (List<Map<String, Object>>) userInfo.get("authorities");
                String username = (String) userInfo.get("username");
                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(authorityMap -> {
                            String authority = (String) authorityMap.get("authority");
                            return new SimpleGrantedAuthority(authority);
                        })
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
