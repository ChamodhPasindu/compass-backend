package lk.me.compass.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePdfReqDTO {
    private int reportId;
    private String ipAddress;
}
