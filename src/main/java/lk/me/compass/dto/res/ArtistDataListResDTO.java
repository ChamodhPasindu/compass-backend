package lk.me.compass.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDataListResDTO {
    private int quarterNo;
    private int reportId;
    private int year;
    private String artist;
    private String assetTitle;
    private String customId;
    private String platform;
    private double revenue;
    private double revenueShare;
    private String isrc;
}
