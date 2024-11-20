package api.dashboard.insurance.system.usecase;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class ReadFromExcelUseCase {

    private static final String CSV_FILE_LOCATION = "C:/Users/fahrizal.aziz_idstar/myProject/document/claim.xlsx";

    public String noClaim() {
        Workbook wb = null;

        try {
            wb = WorkbookFactory.create(new File(CSV_FILE_LOCATION));
            log.info("Number of Sheet :" + wb.getNumberOfSheets());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
