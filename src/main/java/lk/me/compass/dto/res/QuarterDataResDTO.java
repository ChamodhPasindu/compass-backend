package lk.me.compass.dto.res;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterDataResDTO {
    private int count;
    private List<ArtistDataListResDTO> artistDataListResDTOs;
}
