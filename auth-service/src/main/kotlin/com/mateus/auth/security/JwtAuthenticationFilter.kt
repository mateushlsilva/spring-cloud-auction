package com.mateus.auth.security

import com.mateus.auth.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = this.recoverToken(request);
        if(token != null){
            val email = jwtUtils.validateToken(token);
            val user: UserDetails = userRepository.findByEmail(email);

            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities);
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response);
    }

    private fun recoverToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization") ?: return null;
        return authHeader.replace("Bearer ", "");
    }

}