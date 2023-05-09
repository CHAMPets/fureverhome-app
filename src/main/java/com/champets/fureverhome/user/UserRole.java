package com.champets.fureverhome.user;


import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @SequenceGenerator(
            name = "user_role_sequence",
            sequenceName = "user_role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_role_sequence"
    )
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="role_name")
    private RoleName roleName;

}
