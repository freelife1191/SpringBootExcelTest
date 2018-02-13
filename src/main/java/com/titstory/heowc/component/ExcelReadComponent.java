package com.titstory.heowc.component;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.titstory.heowc.config.ExcelConfig;

@Component
public class ExcelReadComponent {

    public <T> List<T> readExcelToList(final MultipartFile multipartFile,
            final Function<Row, T> rowFunc) throws IOException, InvalidFormatException {

        System.out.println("FILE_NAME ::: "+multipartFile.getName());
        System.out.println("FILE_NAME ::: "+multipartFile.getOriginalFilename());

        final Workbook workbook = readWorkbook(multipartFile);
        final Sheet sheet = workbook.getSheetAt(0);
        final int rowCount = sheet.getPhysicalNumberOfRows();

        return IntStream
                .range(0, rowCount)
                .mapToObj(rowIndex -> rowFunc.apply(sheet.getRow(rowIndex)))
                .collect(Collectors.toList());
    }

    private Workbook readWorkbook(MultipartFile multipartFile) throws IOException, InvalidFormatException {
        verifyFileExtension(multipartFile);
        return multipartFileToWorkbook(multipartFile);
    }

    private void verifyFileExtension(MultipartFile multipartFile) throws InvalidFormatException {
        if( !isExcelExtension(multipartFile.getOriginalFilename()) ) {
            throw new InvalidFormatException("This file extension is not verify");
        }
    }

    private boolean isExcelExtension(String fileName) {
        System.out.println("FILE_NAME :: "+fileName);
        System.out.println(fileName.endsWith(ExcelConfig.XLS) || fileName.endsWith(ExcelConfig.XLSX));
        return fileName.endsWith(ExcelConfig.XLS) || fileName.endsWith(ExcelConfig.XLSX);
    }

    private boolean isExcelXls(String fileName) {
        return fileName.endsWith(ExcelConfig.XLS);
    }

    private boolean isExcelXlsx(String fileName) {
        return fileName.endsWith(ExcelConfig.XLSX);
    }

    private Workbook multipartFileToWorkbook(MultipartFile multipartFile)
            throws IOException, InvalidFormatException {
        if( isExcelXls(multipartFile.getOriginalFilename()) ) {
            return new HSSFWorkbook(multipartFile.getInputStream());
        } else {
            return new XSSFWorkbook(multipartFile.getInputStream());
        }
    }
}