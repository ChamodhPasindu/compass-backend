package lk.me.compass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lk.me.compass.dto.req.InvoiceReqDTO;
import lk.me.compass.service.BackOfficeService;
import lk.me.compass.util.ExcelConverter;
import lk.me.compass.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/backOffice")
public class BackOfficeController {

    @Autowired
    BackOfficeService backOfficeService;

    // verify the excel sheet and return error cells
    @PostMapping("/verify_upload")
    public ResponseUtil validateAuthentication(@RequestParam("file") MultipartFile file) {
        try {
            return new ResponseUtil(200, "done", ExcelConverter.verifyExcelSheet(file));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // verify the excel sheet and return error cells
    @PostMapping("/upload_excel")
    public ResponseUtil saveExcelSheet(@RequestParam("file") MultipartFile file) {
        try {
            return backOfficeService.saveArtistData(file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // save invoice details
    @PostMapping("/save_invoice")
    public ResponseUtil saveInvoice(@RequestBody InvoiceReqDTO invoiceReqDTO) {
        try {
            return backOfficeService.saveArtistInvoiceData(invoiceReqDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // report content table with pagination
    @GetMapping("/pagination/report")
    public ResponseUtil getAllReport(@RequestParam(value = "limit", required = true) Integer limit,
            @RequestParam(value = "offset", required = true) Integer offset,
            @RequestParam(value = "year", required = true) String year,
            @RequestParam(value = "quarter", required = true) String quarter,
            @RequestParam(value = "search", required = true) String search) {
        try {
            return backOfficeService.getAllArtistReport(year, quarter, search, limit, offset);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // report content table with pagination
    @GetMapping("/payment_history")
    public ResponseUtil getAllPaymentHistory(@RequestParam(value = "limit", required = true) Integer limit,
            @RequestParam(value = "offset", required = true) Integer offset,
            @RequestParam(value = "search", required = true) String search) {
        try {
            return backOfficeService.getAllArtistPaymentStatus(search, limit, offset);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping("/payment_status/{id}")
    public ResponseUtil changePaymentStatus(@PathVariable Integer id) {
        try {
            return backOfficeService.changePaymentStatus(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping("/artist_count")
    public ResponseUtil totalArtistCount() {
        try {
            return backOfficeService.getTotalArtistCount();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping("/content_count")
    public ResponseUtil totalContentCount() {
        try {
            return backOfficeService.getTotalContentCount();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping("/invoice_count")
    public ResponseUtil totalInvoiceCount() {
        try {
            return backOfficeService.getTotalInvoiceCount();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping("/transaction_count")
    public ResponseUtil totalTransactionCount() {
        try {
            return backOfficeService.getTotalTransactionCount();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // get artist quarter payment with vendor number,quarter and year
    @GetMapping("/quarter_payment")
    public ResponseUtil getQuarterPayment(@RequestParam(value = "year", required = true) Integer year,
            @RequestParam(value = "quarter", required = true) Integer quarter,
            @RequestParam(value = "vendorNo", required = true) String vendorNo) {
        try {
            return backOfficeService.getArtistQuarterWisePayment(year, quarter, vendorNo);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // reset artist password
    @GetMapping("/reset_password/{sapNo}")
    public ResponseUtil resetPassword(@PathVariable String sapNo) {
        try {
            return backOfficeService.resetPassword(sapNo);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

}
