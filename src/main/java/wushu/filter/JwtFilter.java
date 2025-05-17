package wushu.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import wushu.util.JwtUtil;
import wushu.service.CustomUserDetailsService;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{

        String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.isBlank(authHeaderValue) || !authHeaderValue.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeaderValue.substring(7);

        try {
            if (jwtUtil.isTokenExpired(token)) {
                log.warn("Expired JWT token");
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtUtil.extractUsername(token);
            if (StringUtils.isBlank(username)) {
                log.warn("Invalide JWT token");
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e){
            log.error("JWT authetication fails: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);

    }

}
