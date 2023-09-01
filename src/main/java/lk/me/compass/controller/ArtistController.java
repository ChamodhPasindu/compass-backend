package lk.me.compass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lk.me.compass.dto.req.CreatePdfReqDTO;
import lk.me.compass.service.ArtistService;
import lk.me.compass.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // artist content table with pagination
    @GetMapping("/pagination/content")
    public ResponseUtil getAllMenu(@RequestParam(value = "limit", required = true) Integer limit,
            @RequestParam(value = "offset", required = true) Integer offset,
            @RequestParam(value = "year", required = true) Integer year,
            @RequestParam(value = "quarter", required = true) Integer quarter,
            @RequestParam(value = "platform", required = true) String platform,
            @RequestParam(value = "userId", required = true) Integer userId) {
        try {
            return artistService.getAllArtistReportDataByUserId(userId, year, quarter, platform, limit, offset);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // change artist report status when invoice send
    @GetMapping("/raised_hand/{id}")
    public ResponseUtil changeReportStatus(@PathVariable Integer id) {
        try {
            return artistService.changeQuarterReportStatus(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping("/check_raised_hand/{id}")
    public ResponseUtil checkReportStatus(@PathVariable Integer id) {
        try {
            return artistService.checkQuarterReportStatus(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // create pdf of artist quarter wise report data
    @PostMapping("/create_pdf")
    public void createPdf(@RequestBody CreatePdfReqDTO createPdfReqDTO, HttpServletResponse response) {
        try {
            artistService.generateArtistReportPdf(createPdfReqDTO, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get all quarter years
    @GetMapping("/all_years")
    public ResponseUtil getAllYears() {
        try {
            return artistService.getAllQuarterYears();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // get all venders
    @GetMapping("/all_venders")
    public ResponseUtil getAllVendors() {
        try {
            return artistService.getAllVender();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // get quarter wise total revenue
    @GetMapping("/total_revenue/{id}")
    public ResponseUtil getTotalRevenue(@PathVariable Integer id) {
        try {
            return artistService.getTotalRevenueByReportId(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // get all artist record
    @GetMapping("/all_artist")
    public ResponseUtil getAllArtistRecord(@RequestParam(value = "limit", required = true) Integer limit,
    @RequestParam(value = "offset", required = true) Integer offset) {
        try {
            return artistService.getAllArtistRecord(limit,offset);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

 



}
