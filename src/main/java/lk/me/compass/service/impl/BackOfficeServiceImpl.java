package lk.me.compass.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lk.me.compass.config.Encoder;
import lk.me.compass.dto.req.InvoiceReqDTO;
import lk.me.compass.dto.req.UserDTO;
import lk.me.compass.dto.res.ConvertExcelResDTO;
import lk.me.compass.dto.res.PaymentHistoryResDTO;
import lk.me.compass.dto.res.QuarterReportResDTO;
import lk.me.compass.dto.res.ReportDataListResDTO;
import lk.me.compass.entity.Invoice;
import lk.me.compass.entity.Quarter;
import lk.me.compass.entity.QuarterData;
import lk.me.compass.entity.QuarterReport;
import lk.me.compass.entity.User;
import lk.me.compass.repo.InvoiceRepo;
import lk.me.compass.repo.QuarterDataRepo;
import lk.me.compass.repo.QuarterRepo;
import lk.me.compass.repo.QuarterReportRepo;
import lk.me.compass.repo.UserRepo;
import lk.me.compass.service.BackOfficeService;
import lk.me.compass.util.CommonUtil;
import lk.me.compass.util.ExcelConverter;
import lk.me.compass.util.RandomLoginDetail;
import lk.me.compass.util.ResponseUtil;

@Service
@Transactional
public class BackOfficeServiceImpl implements BackOfficeService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private QuarterRepo quarterRepo;

    @Autowired
    private QuarterReportRepo quarterReportRepo;

    @Autowired
    private QuarterDataRepo quarterDataRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private Encoder encoder;

    @Override
    public ResponseUtil saveArtistData(MultipartFile file) throws Exception {
        List<ConvertExcelResDTO> convertExcelResDTOs = ExcelConverter.convertExcelSheetToDTO(file);

        List<String> errorVendors = new ArrayList<>();

        for (ConvertExcelResDTO convertExcelResDTO : convertExcelResDTOs) {
            Optional<User> userEntity = userRepo.findByVenderNo(convertExcelResDTO.getVenderNo());

            if (userEntity.isPresent()) {
                Optional<Quarter> optionalQuarter = quarterRepo.findQuarterByYearAndQuarter(
                        convertExcelResDTO.getYear(),
                        convertExcelResDTO.getQuarter());

                if (optionalQuarter.isPresent()) {
                    Optional<QuarterReport> optionalQuarterReport = quarterReportRepo
                            .findQuarterReportByUserIdAndQuarterId(userEntity.get().getId(),
                                    optionalQuarter.get().getId());

                    if (optionalQuarterReport.isPresent()) {
                        optionalQuarterReport.get().setUpdateDate(new Date());

                        QuarterReport savedQuarterReportEntity = quarterReportRepo.save(optionalQuarterReport.get());

                        if (savedQuarterReportEntity == null) {
                            throw new RuntimeException("Quarter report updated unsuccessfully");
                        }

                        saveQuarterData(savedQuarterReportEntity, convertExcelResDTO);

                    } else {

                        QuarterReport quarterReportEntity = new QuarterReport();

                        quarterReportEntity.setQuarterId(optionalQuarter.get());
                        quarterReportEntity.setUserId(userEntity.get());
                        quarterReportEntity.setStatus(CommonUtil.ACTIVE);

                        QuarterReport savedQuarterReportEntity = quarterReportRepo.save(quarterReportEntity);

                        if (savedQuarterReportEntity == null) {
                            throw new RuntimeException("Quarter report saved unsuccessfully");
                        }

                        saveQuarterData(savedQuarterReportEntity, convertExcelResDTO);

                    }

                } else {
                    Quarter quarterEntity = new Quarter();

                    quarterEntity.setQuarterNumber(convertExcelResDTO.getQuarter());
                    quarterEntity.setStatus(CommonUtil.ACTIVE);
                    quarterEntity.setYear(convertExcelResDTO.getYear());

                    Quarter savedQuarterEntity = quarterRepo.save(quarterEntity);

                    if (savedQuarterEntity == null) {
                        throw new RuntimeException("Quarter saved unsuccessfully");
                    }

                    QuarterReport quarterReportEntity = new QuarterReport();
                    quarterReportEntity.setQuarterId(savedQuarterEntity);
                    quarterReportEntity.setUserId(userEntity.get());
                    quarterReportEntity.setStatus(CommonUtil.ACTIVE);

                    QuarterReport savedQuarterReportEntity = quarterReportRepo.save(quarterReportEntity);

                    if (savedQuarterReportEntity == null) {
                        throw new RuntimeException("Quarter report saved unsuccessfully");
                    }

                    saveQuarterData(savedQuarterReportEntity, convertExcelResDTO);

                }
            } else {
                errorVendors.add(convertExcelResDTO.getVenderNo());
            }
        }
        return new ResponseUtil(200, "Report added successfully", errorVendors);
    }

    // save quarterData each one by one
    public Boolean saveQuarterData(QuarterReport quarterReport, ConvertExcelResDTO convertExcelResDTO)
            throws Exception {
     
        if (convertExcelResDTO.getTelco() != Double.valueOf(0)) {
            QuarterData quarterDataEntity = new QuarterData();

            quarterDataEntity.setArtist(convertExcelResDTO.getArtist());
            quarterDataEntity.setAssetTitle(convertExcelResDTO.getAssetTitle());
            quarterDataEntity.setCustomId(convertExcelResDTO.getCustomID());
            quarterDataEntity.setIsrc(convertExcelResDTO.getIsrc());
            quarterDataEntity.setRevenueShare(convertExcelResDTO.getRevenueShare());
            quarterDataEntity.setStatus(CommonUtil.ACTIVE);
            quarterDataEntity.setQuarterReport(quarterReport);
            quarterDataEntity.setPlatform(CommonUtil.TELCO);
            quarterDataEntity.setRevenue(convertExcelResDTO.getTelco());
            QuarterData quarterData = quarterDataRepo.save(quarterDataEntity);

            if (quarterData == null) {
                throw new RuntimeException("Quarter data saved unsuccessfully");
            }
        }

        if (convertExcelResDTO.getYoutube() != Double.valueOf(0)) {
            QuarterData quarterDataEntity = new QuarterData();


            quarterDataEntity.setArtist(convertExcelResDTO.getArtist());
            quarterDataEntity.setAssetTitle(convertExcelResDTO.getAssetTitle());
            quarterDataEntity.setCustomId(convertExcelResDTO.getCustomID());
            quarterDataEntity.setIsrc(convertExcelResDTO.getIsrc());
            quarterDataEntity.setRevenueShare(convertExcelResDTO.getRevenueShare());
            quarterDataEntity.setStatus(CommonUtil.ACTIVE);
            quarterDataEntity.setQuarterReport(quarterReport);
    
            quarterDataEntity.setPlatform(CommonUtil.YOUTUBE);
            quarterDataEntity.setRevenue(convertExcelResDTO.getYoutube());
            QuarterData quarterData = quarterDataRepo.save(quarterDataEntity);

            if (quarterData == null) {
                throw new RuntimeException("Quarter data saved unsuccessfully");
            }
        }

        if (convertExcelResDTO.getStreaming() != Double.valueOf(0)) {
             QuarterData quarterDataEntity = new QuarterData();

            quarterDataEntity.setArtist(convertExcelResDTO.getArtist());
            quarterDataEntity.setAssetTitle(convertExcelResDTO.getAssetTitle());
            quarterDataEntity.setCustomId(convertExcelResDTO.getCustomID());
            quarterDataEntity.setIsrc(convertExcelResDTO.getIsrc());
            quarterDataEntity.setRevenueShare(convertExcelResDTO.getRevenueShare());
            quarterDataEntity.setStatus(CommonUtil.ACTIVE);

            quarterDataEntity.setQuarterReport(quarterReport);
            quarterDataEntity.setPlatform(CommonUtil.STREAMING);
            quarterDataEntity.setRevenue(convertExcelResDTO.getStreaming());
            QuarterData quarterData = quarterDataRepo.save(quarterDataEntity);

            if (quarterData == null) {
                throw new RuntimeException("Quarter data saved unsuccessfully");
            }
        }
        return true;
    }

    @Override
    public ResponseUtil saveArtistInvoiceData(InvoiceReqDTO invoiceReqDTO) throws Exception {

        Optional<Quarter> optionalQuarter = quarterRepo.findQuarterByYearAndQuarter(invoiceReqDTO.getYear(),
                invoiceReqDTO.getQuarter());

        if (optionalQuarter.isPresent()) {
            Optional<User> optionalUser = userRepo.findByVenderNo(invoiceReqDTO.getVenderNo());

            if (optionalUser.isPresent()) {

                Optional<QuarterReport> optionalQuarterReport = quarterReportRepo
                        .findQuarterReportByUserIdAndQuarterId(optionalUser.get().getId(),
                                optionalQuarter.get().getId());

                if (optionalQuarterReport.isPresent()) {

                    Invoice invoiceEntity = new Invoice();
                    invoiceEntity.setChequeDate(invoiceReqDTO.getChequeDate());
                    invoiceEntity.setInvoiceId(invoiceReqDTO.getInvoiceId());
                    invoiceEntity.setIssueDate(invoiceReqDTO.getIssueDate());
                    // invoiceEntity.setDatePeriod(invoiceReqDTO.getDatePeriod());
                    invoiceEntity.setQuarterId(optionalQuarter.get());
                    invoiceEntity.setStatus(CommonUtil.ACTIVE);
                    invoiceEntity.setUserId(optionalUser.get());

                    Invoice savedInvoice = invoiceRepo.save(invoiceEntity);

                    optionalQuarterReport.get().setStatus(CommonUtil.PROCESSING);

                    QuarterReport savedQuarterReport = quarterReportRepo.save(optionalQuarterReport.get());

                    if (savedInvoice != null) {
                        if (savedQuarterReport != null) {
                            return new ResponseUtil(200, "Invoice Added Successfully", null);
                        } else {
                            throw new RuntimeException("Quarter report update failed");
                        }
                    } else {
                        throw new RuntimeException("Invoice details saved unsuccessfully");
                    }

                } else {
                    throw new RuntimeException("This artist do not have any content in this quarter");
                }
            } else {
                throw new RuntimeException("User not found in given vender no");
            }
        } else {
            throw new RuntimeException("Quarter Record not found");
        }
    }

    @Override
    public ResponseUtil getAllArtistReport(String year, String quarter, String search, Integer limit, Integer offset)
            throws Exception {
        List<Object[]> reportObj = quarterReportRepo.findAllArtistReports(year, quarter, search, limit, offset);
        List<ReportDataListResDTO> dataListResDTOs = new ArrayList<>();

        for (Object[] report : reportObj) {
            ReportDataListResDTO dataListResDTO = new ReportDataListResDTO();

            dataListResDTO.setVenderNo(report[0].toString());
            dataListResDTO.setYear(Integer.parseInt(report[1].toString()));
            dataListResDTO.setQuarter(Integer.parseInt(report[2].toString()));
            dataListResDTO.setArtist(report[4].toString());
            dataListResDTO.setStatus(Integer.parseInt(report[6].toString()));
            dataListResDTO.setReportId(Integer.parseInt(report[7].toString()));
            dataListResDTO.setRevenue(Double.parseDouble(report[8].toString()));

            if (report[3] == null) {

            } else {
                dataListResDTO.setInvoiceId(report[3].toString());
                // dataListResDTO.setDatePeriod(report[5].toString());
                dataListResDTO.setChequeDate(report[5].toString());
            }

            dataListResDTOs.add(dataListResDTO);

        }
        List<Object[]> reportCount = quarterReportRepo.findAllArtistReportsCount(year, quarter, search);

        return new ResponseUtil(200, "Done",
                new QuarterReportResDTO(Integer.parseInt(reportCount.get(0)[0].toString()), dataListResDTOs));
    }

    @Override
    public ResponseUtil getAllArtistPaymentStatus(String search, Integer limit, Integer offset) throws Exception {

        List<Object[]> venderObj = userRepo.filterVenderByNameAndId(search, limit, offset);

        List<PaymentHistoryResDTO> historyResDTOs = new ArrayList<>();

        List<Object[]> venderCount = userRepo.countVenderByNameAndId(search);

        for (Object[] vender : venderObj) {
            PaymentHistoryResDTO historyResDTO = new PaymentHistoryResDTO();

            List<ReportDataListResDTO> dataListResDTOs = new ArrayList<>();
            List<Object[]> paymentStatusList = quarterReportRepo.findAllArtistPaymentHistory(vender[0].toString());

            for (Object[] payment : paymentStatusList) {
                ReportDataListResDTO dataListResDTO = new ReportDataListResDTO();

                dataListResDTO.setQuarter(Integer.parseInt(payment[0].toString()));
                dataListResDTO.setYear(Integer.parseInt(payment[1].toString()));
                dataListResDTO.setStatus(Integer.parseInt(payment[2].toString()));
                dataListResDTO.setRevenue(Double.parseDouble(payment[3].toString()));

                dataListResDTOs.add(dataListResDTO);

            }

            historyResDTO.setVenderName(vender[1].toString());
            historyResDTO.setVenderNo(vender[0].toString());
            historyResDTO.setCount(Integer.parseInt(venderCount.get(0)[0].toString()));
            historyResDTO.setDataListResDTOs(dataListResDTOs);

            historyResDTOs.add(historyResDTO);

        }
        return new ResponseUtil(200, "Done", historyResDTOs);
    }

    @Override
    public ResponseUtil changePaymentStatus(Integer reportId) throws Exception {

        Optional<QuarterReport> optionalQuarterReport = quarterReportRepo.findById(reportId);

        if (optionalQuarterReport.isPresent()) {
            optionalQuarterReport.get().setStatus(CommonUtil.DONE);
            QuarterReport savedQuarterReport = quarterReportRepo.save(optionalQuarterReport.get());

            if (savedQuarterReport != null) {
                return new ResponseUtil(200, "Payment successfully", null);
            } else {
                return new ResponseUtil(500, "Payment unsuccessfully", null);
            }
        } else {
            throw new RuntimeException("Report not found");
        }
    }

    @Override
    public ResponseUtil getTotalArtistCount() throws Exception {
        List<Object[]> vendorObjects = userRepo.findVendorCount();
        return new ResponseUtil(200, "Done", vendorObjects.get(0)[0]);
    }

    @Override
    public ResponseUtil getTotalContentCount() throws Exception {
        List<Object[]> contentObjects = quarterDataRepo.findContentCount();
        return new ResponseUtil(200, "Done", contentObjects.get(0)[0]);
    }

    @Override
    public ResponseUtil getTotalInvoiceCount() throws Exception {
        List<Object[]> invoiceObjects = invoiceRepo.findInvoiceCount();
        return new ResponseUtil(200, "Done", invoiceObjects.get(0)[0]);
    }

    @Override
    public ResponseUtil getTotalTransactionCount() throws Exception {
        List<Object[]> transactionObjects = quarterReportRepo.findTransactionCount();
        return new ResponseUtil(200, "Done", transactionObjects.get(0)[0]);
    }

    @Override
    public ResponseUtil getArtistQuarterWisePayment(Integer year, Integer quarter, String vendorNo) throws Exception {
        List<Object[]> quarterPaymentObjects = quarterReportRepo.findQuarterPayment(year,quarter,vendorNo);
        return new ResponseUtil(200, "Done", quarterPaymentObjects.get(0)[0]);
    } 

    @Override
    public ResponseUtil resetPassword(String sapNo) {
        Optional<User> optionalUser = userRepo.findByVenderNo(sapNo);

        if(optionalUser.isPresent()){

            String password = RandomLoginDetail.generatePassword();
            optionalUser.get().setPassword(encoder.encode(password));

            User savedUser = userRepo.save(optionalUser.get());

            UserDTO randomDetail = new UserDTO();
            randomDetail.setUsername(savedUser.getUsername());
            randomDetail.setPassword(password);

            if (savedUser != null) {
                return new ResponseUtil(200, "Reset Password Successfully", randomDetail);
            } else {
                return new ResponseUtil(500, "Error,User saved unsuccessfully", null);
            }
        }else{
            throw new RuntimeException("User not found for this vendor number");
        }
    }
}
