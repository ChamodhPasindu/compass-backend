package lk.me.compass.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistAllRecordListResDTO {
    private String address;
    private String alternativeName;
    private String contactNumber;
    private String email;
    private String nic;
    private String username;
    private String vendorName;
    private String vendorNo;
}
