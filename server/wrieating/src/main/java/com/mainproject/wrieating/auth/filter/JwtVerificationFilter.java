package com.mainproject.wrieating.auth.filter;

import com.mainproject.wrieating.auth.utils.CustomAuthorityUtils;
import com.mainproject.wrieating.auth.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final CustomAuthorityUtils authorityUtils;

    public JwtVerificationFilter(JwtUtils jwtUtils,
                                 CustomAuthorityUtils authorityUtils) {
        this.jwtUtils = jwtUtils;
        this.authorityUtils = authorityUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = jwtUtils.getJwsClaimsFromRequest(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }

//    private Map<String, Object> verifyJws(HttpServletRequest request) {
//        String jws = request.getHeader("Authorization").replace("Bearer ", "");
//        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
//        Map<String, Object> claims = jwtUtils.getClaims(jws, base64EncodedSecretKey).getBody();
//
//        return claims;
//    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String email = (String) claims.get("email");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
