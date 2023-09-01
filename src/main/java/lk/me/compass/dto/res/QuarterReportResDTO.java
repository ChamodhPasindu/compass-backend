package lk.me.compass.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterReportResDTO {
    private int count;
    private List<ReportDataListResDTO> dataListResDTOs;    
}
