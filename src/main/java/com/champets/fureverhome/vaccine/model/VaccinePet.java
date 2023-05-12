package com.champets.fureverhome.vaccine.model;

import com.champets.fureverhome.pet.model.Pet;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vaccine_histories")
public class VaccinePet {
//    @EmbeddedId
//    private VaccineHistoryId id;
//
//    @ManyToOne
//    @MapsId("petId")
//    @JoinColumn(name = "pet_id")
//    private Pet pet;
//
//    @ManyToOne
//    @MapsId("vaccineId")
//    @JoinColumn(name = "vaccine_id")
//    private Vaccine vaccine;
    @Id
    @SequenceGenerator(
            name = "vaccine_history_sequence",
            sequenceName = "vaccine_history_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vaccine_history_sequence"
    )
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;
    public VaccinePet(Pet pet, Vaccine vaccine) {
        this.pet = pet;
        this.vaccine = vaccine;
    }

}