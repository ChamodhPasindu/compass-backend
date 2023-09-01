package lk.me.compass.dto.req;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceReqDTO {
    private String invoiceId;
    private Date issueDate;
    private Date chequeDate;
    private String datePeriod;
    private int quarter;
    private int year;
    private String venderNo;
}
