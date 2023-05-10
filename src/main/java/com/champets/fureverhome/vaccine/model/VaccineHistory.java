package com.champets.fureverhome.vaccine.model;

import com.champets.fureverhome.pet.model.Pet;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vaccine_histories")
public class VaccineHistory {
    @EmbeddedId
    private VaccineHistoryId id;

    @ManyToOne
    @MapsId("petId")
    @JoinColumn(name = "id")
    private Pet pet;

    @ManyToOne
    @MapsId("vaccineId")
    @JoinColumn(name = "id")
    private Vaccine vaccine;
}