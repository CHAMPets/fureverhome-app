package com.champets.fureverhome.application.model;

import com.champets.fureverhome.application.enums.ApplicationStatus;
import com.champets.fureverhome.pet.model.Pet;
import com.champets.fureverhome.user.model.User;
import com.champets.fureverhome.user.model.UserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString(includeFieldNames = true)
@Entity
@Builder
@AllArgsConstructor
@Table(name = "applications")
public class Application {

    @Id
    @SequenceGenerator(
            name = "application_sequence",
            sequenceName = "application_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_sequence"
    )
    @Column(nullable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="pet_id", nullable=false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name="user_id")//, nullable=false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private ApplicationStatus applicationStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
}
