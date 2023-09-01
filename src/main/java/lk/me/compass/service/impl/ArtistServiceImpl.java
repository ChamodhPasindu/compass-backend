package lk.me.compass.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import lk.me.compass.config.Encoder;
import lk.me.compass.dto.req.CreatePdfReqDTO;
import lk.me.compass.dto.req.UserDTO;
import lk.me.compass.dto.res.ArtistAllRecordListResDTO;
import lk.me.compass.dto.res.ArtistAllRecordResDTO;
import lk.me.compass.dto.res.ArtistDataListResDTO;
import lk.me.compass.dto.res.QuarterDataResDTO;
import lk.me.compass.entity.QuarterReport;
import lk.me.compass.entity.ReportLogData;
import lk.me.compass.entity.User;
import lk.me.compass.repo.QuarterDataRepo;
import lk.me.compass.repo.QuarterRepo;
import lk.me.compass.repo.QuarterReportRepo;
import lk.me.compass.repo.ReportLogDataRepo;
import lk.me.compass.repo.UserRepo;
import lk.me.compass.service.ArtistService;
import lk.me.compass.util.CommonUtil;
import lk.me.compass.util.RandomLoginDetail;
import lk.me.compass.util.ResponseUtil;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private QuarterRepo quarterRepo;

    @Autowired
    private QuarterDataRepo quarterDataRepo;

    @Autowired
    private QuarterReportRepo quarterReportRepo;

    @Autowired
    private ReportLogDataRepo reportLogDataRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ResponseUtil getAllArtistReportDataByUserId(Integer userId,Integer year,Integer quarter,String platform,Integer limit,Integer offset) {
        List<Object[]> quarterDataObj = quarterDataRepo.findArtistReportDataByArtistId(userId,year,quarter,platform,limit,offset);

        List<ArtistDataListResDTO> artistDataListResDTOs = new ArrayList<>();

        for (Object[] quarterData : quarterDataObj) {
            ArtistDataListResDTO artistDataListResDTO = new ArtistDataListResDTO();

            artistDataListResDTO.setYear(Integer.parseInt(quarterData[1].toString()));
            artistDataListResDTO.setQuarterNo(Integer.parseInt(quarterData[0].toString()));
            artistDataListResDTO.setArtist(quarterData[2].toString());
            artistDataListResDTO.setAssetTitle(quarterData[3].toString());
            artistDataListResDTO.setCustomId(quarterData[4].toString());
            artistDataListResDTO.setPlatform(quarterData[5].toString());
            artistDataListResDTO.setRevenue(Double.parseDouble(quarterData[6].toString()));
            artistDataListResDTO.setRevenueShare(Double.parseDouble(quarterData[7].toString()));
            artistDataListResDTO.setReportId(Integer.parseInt(quarterData[8].toString()));
            artistDataListResDTO.setIsrc(quarterData[9].toString());

            artistDataListResDTOs.add(artistDataListResDTO);
        }

        List<Object[]> quarterDataCount = quarterDataRepo.getCountOfQuarterDataByArtistId(userId,year,quarter,platform);

        return new ResponseUtil(200, "Done",
                new QuarterDataResDTO(Integer.parseInt(quarterDataCount.get(0)[0].toString()), artistDataListResDTOs));
    }

    @Override
    public ResponseUtil changeQuarterReportStatus(Integer reportId) {

        Optional<QuarterReport> optionalQuarterReport = quarterReportRepo.findById(reportId);

        if (optionalQuarterReport.isPresent()) {
            optionalQuarterReport.get().setStatus(CommonUtil.PENDING);

            quarterReportRepo.save(optionalQuarterReport.get());

            return new ResponseUtil(200, "Raised Hand", null);

        } else {
            throw new RuntimeException("Quarter report data not present");
        }
    }

    @Override
    public ResponseUtil checkQuarterReportStatus(Integer reportId) {
        Optional<QuarterReport> optionalQuarterReport = quarterReportRepo.findById(reportId);

        if(optionalQuarterReport.isPresent()){
                return new ResponseUtil(200, "done", optionalQuarterReport.get().getStatus());
        }else{
            throw new RuntimeException("Quarter report data not present");
        }
    }

    @Override
    public void generateArtistReportPdf(CreatePdfReqDTO createPdfReqDTO, HttpServletResponse response)
            throws Exception {
        List<Object[]> quarterDataObj = quarterDataRepo.findArtistReportDataByReportId(createPdfReqDTO.getReportId());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=report.pdf");

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            PdfPTable table = new PdfPTable(9);

            table.addCell(new PdfPCell(new Paragraph("Quarter Number")));
            table.addCell(new PdfPCell(new Paragraph("Year")));
            table.addCell(new PdfPCell(new Paragraph("Artist")));
            table.addCell(new PdfPCell(new Paragraph("Asset Title")));
            table.addCell(new PdfPCell(new Paragraph("Custom ID")));
            table.addCell(new PdfPCell(new Paragraph("ISRC")));
            table.addCell(new PdfPCell(new Paragraph("Platform")));
            table.addCell(new PdfPCell(new Paragraph("Revenue")));
            table.addCell(new PdfPCell(new Paragraph("Revenue Share")));

            for (Object[] quarterData : quarterDataObj) {
                table.addCell(new PdfPCell(new Paragraph(quarterData[1].toString())));
                table.addCell(new PdfPCell(new Paragraph(quarterData[2].toString())));
                table.addCell(new PdfPCell(new Paragraph(quarterData[3].toString())));
                table.addCell(new PdfPCell(new Paragraph(quarterData[4].toString())));  
                table.addCell(new PdfPCell(new Paragraph(quarterData[5].toString())));
                table.addCell(new PdfPCell(new Paragraph(quarterData[9].toString())));
                table.addCell(new PdfPCell(new Paragraph(quarterData[6].toString())));
                table.addCell(new PdfPCell(new Paragraph(quarterData[7].toString())));
                table.addCell(new PdfPCell(new Paragraph(quarterData[8].toString())));

            }

            document.add(table);
            document.close();
            writer.close();

            Optional<QuarterReport> optionalQuarterReport = quarterReportRepo.findById(createPdfReqDTO.getReportId());

            if (optionalQuarterReport.isPresent()) {

                ReportLogData reportLogDataEntity = new ReportLogData();
                reportLogDataEntity.setIpAddress(createPdfReqDTO.getIpAddress());
                reportLogDataEntity.setQuarterReport(optionalQuarterReport.get());
                reportLogDataEntity.setStatus(CommonUtil.ACTIVE);

                ReportLogData reportLogData = reportLogDataRepo.save(reportLogDataEntity);

                if(reportLogData==null){
                    throw new RuntimeException("Report log data saved unsuccessfully");
                }

            } else {
                throw new RuntimeException("Quarter report not found");
            }

        } catch (Exception e) {
            throw new RuntimeException("PDF generate error");
        }

    }

    @Override
    public ResponseUtil getAllVender() {

        List<Object[]> venderObj = userRepo.findAllVenders();
        List<String> venderResDTO = new ArrayList<>();

        for(Object[] vender:venderObj){
            venderResDTO.add(vender[0].toString());
        }
        return new ResponseUtil(200, "Done", venderResDTO);
    }

    @Override
    public ResponseUtil getAllQuarterYears() {

        List<Object[]> quarterObj = quarterRepo.findAllQuarterYears();
        List<String> quarterResDTOs = new ArrayList<>();

        for (Object[] year : quarterObj) {
            quarterResDTOs.add(year[0].toString());
        }
        return new ResponseUtil(200, "Done", quarterResDTOs);
    }

    @Override
    public ResponseUtil getTotalRevenueByReportId(Integer reportId) {
      List<Object []> revenueObj = quarterReportRepo.findTotalRevenue(reportId);
      return new ResponseUtil(200, "Done", revenueObj.get(0)[0]);
    }

    @Override
    public ResponseUtil getAllArtistRecord(Integer limit,Integer offset) {
        List<Object[]> artistObj = userRepo.findAllArtist(limit,offset);
        ArtistAllRecordResDTO allRecordResDTOs = new ArtistAllRecordResDTO();
        List<ArtistAllRecordListResDTO> artistAllRecordListResDTOs = new ArrayList<>();
        List<Object[]> artistCount = userRepo.findVendorCount();

        for (Object[] artist : artistObj) {
            ArtistAllRecordListResDTO recordListResDTO = new ArtistAllRecordListResDTO();
            recordListResDTO.setAddress(artist[0].toString());
            recordListResDTO.setAlternativeName(artist[1].toString());
            recordListResDTO.setContactNumber(artist[2].toString());
            recordListResDTO.setEmail(artist[3].toString());
            recordListResDTO.setNic(artist[4].toString());
            recordListResDTO.setUsername(artist[5].toString());
            recordListResDTO.setVendorName(artist[6].toString());
            recordListResDTO.setVendorNo(artist[7].toString());

            artistAllRecordListResDTOs.add(recordListResDTO);
        }
        allRecordResDTOs.setCount(Integer.parseInt(artistCount.get(0)[0].toString()));
        allRecordResDTOs.setAllRecordListResDTOs(artistAllRecordListResDTOs);

        return new ResponseUtil(200,"Done",allRecordResDTOs);

    }

   
}
