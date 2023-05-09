package com.champets.fureverhome.user.model;

import com.champets.fureverhome.application.model.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeFieldNames = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )

    @Column(nullable = false)
    private Long id;

    private String emailAddress;

    private String password;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    @UpdateTimestamp
    private LocalDate lastDateModified;

    @Column(columnDefinition = "VARCHAR(20)")
    private String createdBy;

    @Column(columnDefinition = "VARCHAR(20)")
    private String lastModifiedBy;

    @CreationTimestamp
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false, insertable = false, updatable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();
}