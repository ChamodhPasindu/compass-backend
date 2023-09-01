package lk.me.compass.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConvertExcelResDTO {

    private String venderNo;
    private String customID;
    private String isrc;
    private String assetTitle;
    private String artist;
    private double telco;
    private double streaming;
    private double youtube;
    private int revenueShare;
    private int quarter;
    private int year;
    
}
