package com.sankalp.follow_service.aspects;


import com.sankalp.follow_service.annotations.RolesAllowed;
import com.sankalp.follow_service.exceptions.ResourceAccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
public class RoleAuthorizationAspect {

    @Around("@annotation(rolesAllowed)")
    public Object checkUserRoles(ProceedingJoinPoint joinPoint, RolesAllowed rolesAllowed) throws Throwable {
        String rolesHeader = getRolesHeaderValue();

        Set<String> userRoles = Arrays.stream(rolesHeader.split(","))
                                      .map(String::trim)
                                      .collect(Collectors.toSet());

        Set<String> requiredRoles = Set.of(rolesAllowed.value());

        // Check if the user has at least one of the required roles
        if (userRoles.stream().noneMatch(requiredRoles::contains)) {
            throw new ResourceAccessDeniedException("Access Denied: You don't have access to view this");
        }

        return joinPoint.proceed();  // Continue with the method execution
    }

    private static String getRolesHeaderValue() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Request context is missing");
        }

        HttpServletRequest request = attributes.getRequest();
        String rolesHeader = request.getHeader("X-User-Roles");

        if (rolesHeader == null || rolesHeader.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: No roles provided");
        }
        return rolesHeader;
    }
}
