package com.zapiszto.controllers.common;

import com.zapiszto.controllers.account.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface ControllerCommon {

  default Long extractUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl user) {
      return ((UserDetailsImpl) authentication.getPrincipal()).getId();
      //user.getUsername(); // Assuming username is the user ID
    }
    return null;
  }

  default String extractUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl user) {
      return ((UserDetailsImpl) authentication.getPrincipal()).getAuthorities().toString();
      //user.getUsername(); // Assuming username is the user ID
    }
    return null;
  }

  default String extractUserMail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl user) {
      return ((UserDetailsImpl) authentication.getPrincipal()).getEmail();
      //user.getUsername(); // Assuming username is the user ID
    }
    return null;
  }


}
