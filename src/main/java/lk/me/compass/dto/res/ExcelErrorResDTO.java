package lk.me.compass.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelErrorResDTO {
    private int rowNumber;
    private List<String> errorCellList;

}
