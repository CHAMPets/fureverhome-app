package com.champets.fureverhome.vaccine;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineHistoryId implements Serializable {
    private Long petId;
    private Long vaccineId;
}
