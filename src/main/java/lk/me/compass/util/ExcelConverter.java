package lk.me.compass.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lk.me.compass.dto.res.ConvertExcelResDTO;
import lk.me.compass.dto.res.ExcelErrorResDTO;

@Component
public class ExcelConverter {

    // convert excel file into java DTO
    public static List<ExcelErrorResDTO> verifyExcelSheet(MultipartFile file)
            throws EncryptedDocumentException, IOException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int lastCell = sheet.getLastRowNum();

        List<ExcelErrorResDTO> excelErrorResDTOs = new ArrayList<>();


        for (int i = 2; i <= lastCell; i++) {
            try {
                ExcelErrorResDTO errorResDTO = new ExcelErrorResDTO();

                List<String> errorCellList = new ArrayList<>();

                if (sheet.getRow(i).getCell(0).getCellType() == CellType.STRING) {

                } else {
                    errorCellList.add("A");
                }

                if (sheet.getRow(i).getCell(1).getCellType() == CellType.STRING) {

                } else {
                    errorCellList.add("B");
                }

                if (sheet.getRow(i).getCell(2).getCellType() == CellType.STRING) {

                } else {
                    errorCellList.add("C");
                }

                if (sheet.getRow(i).getCell(3).getCellType() == CellType.STRING) {

                } else {
                    errorCellList.add("D");
                }

                if (sheet.getRow(i).getCell(4).getCellType() == CellType.STRING) {

                } else {
                    errorCellList.add("E");
                }

                if (sheet.getRow(i).getCell(5).getCellType() == CellType.NUMERIC
                        || sheet.getRow(i).getCell(5).getCellType() == CellType.BLANK) {

                } else {
                    errorCellList.add("F");
                }

                if (sheet.getRow(i).getCell(6).getCellType() == CellType.NUMERIC
                        || sheet.getRow(i).getCell(6).getCellType() == CellType.BLANK) {

                } else {
                    errorCellList.add("G");
                }

                if (sheet.getRow(i).getCell(7).getCellType() == CellType.NUMERIC
                        || sheet.getRow(i).getCell(7).getCellType() == CellType.BLANK) {

                } else {
                    errorCellList.add("H");
                }

                if (sheet.getRow(i).getCell(8).getCellType() == CellType.NUMERIC) {

                } else {
                    errorCellList.add("I");
                }

                if (sheet.getRow(i).getCell(9).getCellType() == CellType.NUMERIC) {

                } else {
                    errorCellList.add("J");
                }

                if (sheet.getRow(i).getCell(10).getCellType() == CellType.NUMERIC) {

                } else {
                    errorCellList.add("K");
                }

                errorResDTO.setErrorCellList(errorCellList);

                if (!errorCellList.isEmpty()) {
                    errorResDTO.setRowNumber(i + 1);
                    excelErrorResDTOs.add(errorResDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Incorrect Excel Sheet");
            }
        }
        return excelErrorResDTOs;
    }

    public static List<ConvertExcelResDTO> convertExcelSheetToDTO(MultipartFile file)
            throws EncryptedDocumentException, IOException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        int lastCell = sheet.getLastRowNum();

        List<ConvertExcelResDTO> convertExcelResDTOs = new ArrayList<>();

        for (int i = 2; i <= lastCell; i++) {

            ConvertExcelResDTO excelResDTO = new ConvertExcelResDTO();

            excelResDTO.setVenderNo(sheet.getRow(i).getCell(0).getStringCellValue());
            excelResDTO.setCustomID(sheet.getRow(i).getCell(1).getStringCellValue());
            excelResDTO.setIsrc(sheet.getRow(i).getCell(2).getStringCellValue());
            excelResDTO.setAssetTitle(sheet.getRow(i).getCell(3).getStringCellValue());
            excelResDTO.setArtist(sheet.getRow(i).getCell(4).getStringCellValue());
            excelResDTO.setTelco(sheet.getRow(i).getCell(5).getNumericCellValue());
            excelResDTO.setStreaming(sheet.getRow(i).getCell(6).getNumericCellValue());
            excelResDTO.setYoutube(sheet.getRow(i).getCell(7).getNumericCellValue());
            excelResDTO.setRevenueShare((int) sheet.getRow(i).getCell(8).getNumericCellValue());
            excelResDTO.setQuarter((int) sheet.getRow(i).getCell(9).getNumericCellValue());
            excelResDTO.setYear((int) sheet.getRow(i).getCell(10).getNumericCellValue());

            convertExcelResDTOs.add(excelResDTO);
        }

        return convertExcelResDTOs;
    }
}
