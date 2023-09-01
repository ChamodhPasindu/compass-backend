package lk.me.compass.service;

import jakarta.servlet.http.HttpServletResponse;
import lk.me.compass.dto.req.CreatePdfReqDTO;
import lk.me.compass.util.ResponseUtil;

public interface ArtistService {

    // get all quarter years list
    public ResponseUtil getAllQuarterYears();

    // get all vendor list
    public ResponseUtil getAllVender();

    // get all artist data by artist id
    public ResponseUtil getAllArtistReportDataByUserId(Integer userId, Integer year, Integer quarter, String platform,
            Integer limit, Integer offset);

    // change quarter report status when artist raised hand
    public ResponseUtil changeQuarterReportStatus(Integer reportId);

    // check quarter report status is already change or not
    public ResponseUtil checkQuarterReportStatus(Integer reportId);

    // generate artist report by using report id
    public void generateArtistReportPdf(CreatePdfReqDTO createPdfReqDTO, HttpServletResponse response) throws Exception;

    // get total revenue in quarter wise
    public ResponseUtil getTotalRevenueByReportId(Integer reportId);

    // get all artist record
    public ResponseUtil getAllArtistRecord(Integer limit, Integer offset);

 
}
