package com.champets.fureverhome.vaccine;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VaccineHistoryId implements Serializable {
    private Long petId;
    private Long vaccineId;
}
