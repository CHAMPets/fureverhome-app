package com.champets.fureverhome.pet.model;

import com.champets.fureverhome.application.model.Application;
import com.champets.fureverhome.pet.enums.Gender;
import com.champets.fureverhome.pet.enums.BodySize;
import com.champets.fureverhome.pet.enums.Type;
import com.champets.fureverhome.vaccine.model.Vaccine;
import com.champets.fureverhome.vaccine.model.VaccinePet;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pets") // set the table name explicitly
public class Pet {

    @Id
    @SequenceGenerator(
            name = "pet_sequence",
            sequenceName = "pet_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pet_sequence"
    )
    @Column(nullable = false)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;

    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name="bodySize")
    private BodySize bodySize;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rescueDate;

    @Column(columnDefinition = "VARCHAR(500)")
    private String imagePath;

    @Column(columnDefinition = "VARCHAR(3000)")
    private String description;

    @Column(nullable = true)
    private Boolean isSterilized;

    @Column(nullable = true)
    public Boolean active = true;

    @Column(nullable = true)
    private Integer applicationLimit = 3;

    private Integer applicationCounter = 0;

    @UpdateTimestamp
    private LocalDate lastDateModified;

    @Column(columnDefinition = "VARCHAR(20)")
    private String createdBy;

    @Column(columnDefinition = "VARCHAR(20)")
    private String lastModifiedBy;

    @CreationTimestamp
    private LocalDate createdDate;

    public Boolean getIsSterilized() {
        return isSterilized;
    }

    public void setIsSterilized(boolean isSterilized) {

        this.isSterilized = isSterilized;
    }

    public Boolean getActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VaccinePet> vaccineList = new ArrayList<>();
}