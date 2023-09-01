package lk.me.compass.dto.res;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistAllRecordResDTO {
    private int count;
    private List<ArtistAllRecordListResDTO> allRecordListResDTOs;    
}
