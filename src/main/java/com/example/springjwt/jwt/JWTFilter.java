package com.example.springjwt.jwt;

import com.example.springjwt.dto.CustomUserDetails;
import com.example.springjwt.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if(jwtUtil.isExpired(token)){
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 username, role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // UserEntity 생성
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setRole(role);

        // UserDetails 객체 생성
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);

        // 스프링 시큐리티 인증 토큰 생성
       Authentication authToken
                = new UsernamePasswordAuthenticationToken(
                        userDetails,
                null,
                       userDetails.getAuthorities());

       log.info("all process success={},{}", username, role);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
