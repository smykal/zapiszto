package com.zapiszto.controllers.account.controller;

import com.zapiszto.controllers.account.payload.request.UpdatePasswordRequest;
import com.zapiszto.controllers.account.service.AuthService;
import com.zapiszto.controllers.common.ControllerCommon;
import com.zapiszto.controllers.dictionaries.dictLanguages.entity.DictLanguagesEntity;
import com.zapiszto.controllers.dictionaries.dictLanguages.repository.DictLanguagesRepository;
import com.zapiszto.controllers.userDetails.entity.UserDetailsEntity;
import com.zapiszto.controllers.userDetails.repository.UserDetailsRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zapiszto.controllers.account.entity.ERole;
import com.zapiszto.controllers.account.entity.Role;
import com.zapiszto.controllers.account.entity.User;
import com.zapiszto.controllers.account.payload.request.LoginRequest;
import com.zapiszto.controllers.account.payload.request.SignupRequest;
import com.zapiszto.controllers.account.payload.response.JwtResponse;
import com.zapiszto.controllers.account.payload.response.MessageResponse;
import com.zapiszto.controllers.account.repository.RoleRepository;
import com.zapiszto.controllers.account.repository.UserRepository;
import com.zapiszto.controllers.account.security.jwt.JwtUtils;
import com.zapiszto.controllers.account.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController implements ControllerCommon {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserDetailsRepository userDetailsRepository;

  @Autowired
  DictLanguagesRepository dictLanguagesRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  AuthService authService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext()
        .setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities()
        .stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity
        .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword())
    );

    String strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      switch (strRoles) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        case "trainer":
          Role trnRole = roleRepository.findByName(ERole.ROLE_TRAINER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(trnRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
      }
    }

    user.setRoles(roles);
    User savedUser = userRepository.save(user);
    DictLanguagesEntity dictLanguagesEntity = dictLanguagesRepository.getByCode("en");
    UserDetailsEntity userDetailsEntity = UserDetailsEntity.builder()
        .userId(savedUser.getId())
        .dictLanguagesEntity(dictLanguagesEntity)
        .build();
    userDetailsRepository.save(userDetailsEntity);


    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/updatePassword")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
    var userId = extractUserId();
    try {
      authService.updatePassword(userId, updatePasswordRequest.getOldPassword(), updatePasswordRequest.getNewPassword());
      return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
    }
  }

  @DeleteMapping("/deleteAccount")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('TRAINER')")
  public ResponseEntity<?> deleteUserAccount() {
    var userId = extractUserId();
    if (!userRepository.existsById(userId)) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
    }

    authService.deleteUser(userId);
    return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
  }
}
