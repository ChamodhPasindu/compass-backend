package lk.me.compass.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDataListResDTO {
    private int reportId;
    private String venderNo;
    private int year;
    private int quarter;
    private String invoiceId;
    private String artist;
    // private String datePeriod;
    private String chequeDate;
    private int status;
    private double revenue;
}
