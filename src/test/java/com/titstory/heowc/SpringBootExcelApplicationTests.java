package com.titstory.heowc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.titstory.heowc.component.ExcelReadComponent;
import com.titstory.heowc.domain.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootExcelApplicationTests {

    @Autowired
    ExcelReadComponent excelReadComponent;

    @Test
    public void test_readExcel() throws IOException, InvalidFormatException {

        ClassLoader classLoader = this.getClass().getClassLoader();
        //        System.out.println("::RESOURCE PATH : "+resource.getPath());
        // System.out.println("::RESOURCE FILE : "+resource.getFile());
        // System.out.println("::RESOURCE URL : "+resource.getURL());

        File xlsxFile = new File(classLoader.getResource("files/test.xlsx").getFile());
        System.out.println("XLSX : "+xlsxFile.getPath());
        String filename = xlsxFile.getName();
        System.out.println("filename : "+filename);
        System.out.println(filename.endsWith("xlsx"));

        excelReadComponent
        .readExcelToList(new MockMultipartFile("test","test.xlsx","xlsx", new FileInputStream(xlsxFile)),
                Product::rowOf)
        .forEach(System.out::println);

    }
}
