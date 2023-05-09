package com.champets.fureverhome.vaccine;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(includeFieldNames = true)
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

    @Column(columnDefinition = "VARCHAR(250)")
    private String description;
}
