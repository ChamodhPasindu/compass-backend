package lk.me.compass.service;

import org.springframework.web.multipart.MultipartFile;

import lk.me.compass.dto.req.InvoiceReqDTO;
import lk.me.compass.util.ResponseUtil;

public interface BackOfficeService {

    // save artist data into quarter,quarterReport & quarterData entities
    public ResponseUtil saveArtistData(MultipartFile file) throws Exception;

    // save invoice data
    public ResponseUtil saveArtistInvoiceData(InvoiceReqDTO invoiceReqDTO) throws Exception;

    // get all artist reports
    public ResponseUtil getAllArtistReport(String year, String quarter, String search, Integer limit, Integer offset)
            throws Exception;

    // get all artist payment status
    public ResponseUtil getAllArtistPaymentStatus(String search, Integer limit, Integer offset) throws Exception;

    // change payment status as 4 after paid to artist status
    public ResponseUtil changePaymentStatus(Integer reportId) throws Exception;

    // get total artist count
    public ResponseUtil getTotalArtistCount() throws Exception;

    public ResponseUtil getTotalContentCount() throws Exception;

    public ResponseUtil getTotalInvoiceCount() throws Exception;

    public ResponseUtil getTotalTransactionCount() throws Exception;

    // get artist quarter payment
    public ResponseUtil getArtistQuarterWisePayment(Integer year, Integer quarter, String vendorNo) throws Exception;

    // reset artist password
    public ResponseUtil resetPassword(String sapNo);
}
