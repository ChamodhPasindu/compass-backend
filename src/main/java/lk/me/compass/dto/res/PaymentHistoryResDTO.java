package lk.me.compass.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryResDTO {
    private String venderName;
    private String venderNo;
    private int count;
    private List<ReportDataListResDTO> dataListResDTOs;

}
