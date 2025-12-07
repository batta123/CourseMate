package com.coursemate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

/**
 * Role entity representing user roles in the system
 * Roles: ADMIN, INSTRUCTOR, STUDENT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @Column(length = 500)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public enum RoleType {
        ADMIN,
        INSTRUCTOR,
        STUDENT
    }

    public Role(RoleType name, String description) {
        this.name = name;
        this.description = description;
    }
}
