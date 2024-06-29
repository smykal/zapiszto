package com.zapiszto.controllers.account.entity;

import com.zapiszto.controllers.bodyParams.entity.BodyParamsEntity;
import com.zapiszto.controllers.clients.entity.ClientEntity;
import com.zapiszto.controllers.dictionaries.dictBodyParams.entity.DictBodyParamsEntity;
import com.zapiszto.controllers.dictionaries.dictBodyTest.dictBodyTestPerUser.entity.DictBodyTestPerUserEntity;
import com.zapiszto.controllers.dictionaries.dictCategory.dictCategoryPerUser.entity.DictCategoryPerUserEntity;
import com.zapiszto.controllers.dictionaries.dictExercises.dictExercisesPerUser.entity.DictExercisesPerUserEntity;
import com.zapiszto.controllers.dictionaries.dictMicrocyclePhase.dictMicrocyclePhasePerUser.entity.DictMicrocyclePhasePerUserEntity;
import com.zapiszto.controllers.dictionaries.dictQuantityType.dictQuantityTypePerUser.entity.DictQuantityTypePerUserEntity;
import com.zapiszto.controllers.dictionaries.dictUnits.dictUnitsPerUser.entity.DictUnitsPerUserEntity;
import com.zapiszto.controllers.invitations.entity.InvitationsEntity;
import com.zapiszto.controllers.program.programs.entity.ProgramEntity;
import com.zapiszto.controllers.userDetails.entity.UserDetailsEntity;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "users", 
        uniqueConstraints = { 
          @UniqueConstraint(columnNames = "username"),
          @UniqueConstraint(columnNames = "email") 
        })
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private UserDetailsEntity userDetails;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<DictBodyTestPerUserEntity> dictBodyTests = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<DictUnitsPerUserEntity> dictUnitsPerUser = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<DictQuantityTypePerUserEntity> dictQuantityTypePerUserEntities = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<DictExercisesPerUserEntity> dictExercisesPerUserEntities = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<DictCategoryPerUserEntity> dictCategoryPerUserEntities = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<BodyParamsEntity> dictBodyParams = new HashSet<>();

  @OneToMany(mappedBy = "inviteeEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<InvitationsEntity> receivedInvitations = new HashSet<>();

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<ClientEntity> clients = new HashSet<>();

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
