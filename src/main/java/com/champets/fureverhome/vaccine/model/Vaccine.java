package com.champets.fureverhome.vaccine.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vaccines") // set the table name explicitly
public class Vaccine {
    @Id
    @SequenceGenerator(
            name = "vaccine_sequence",
            sequenceName = "vaccine_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vaccine_sequence"
    )
    @Column(nullable = false)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(columnDefinition = "VARCHAR(50)")
    private String type;

    @Column(columnDefinition = "VARCHAR(300)")
    private String description;



    public Vaccine(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
}
