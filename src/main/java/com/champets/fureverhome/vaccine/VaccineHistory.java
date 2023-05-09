package com.champets.fureverhome.vaccine;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(includeFieldNames = true)
@Entity
@Table(name = "vaccine_histories")
@IdClass(VaccineHistoryId.class) // specify the composite key class
public class VaccineHistory {
    @Id
    @Column(columnDefinition = "VARCHAR(50)")
    private Long petId;

    @Id // specify vaccineId as another part of the primary key
    @Column(columnDefinition = "VARCHAR(50)")
    private Long vaccineId;

    // add constructors and other fields if needed
}