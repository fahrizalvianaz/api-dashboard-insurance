package api.dashboard.insurance.system.service;

import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.enums.Status;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReadFromExcelService {

    public GenericResponse<ReadFromExcelResponse> execute(String pathFile, Integer sheetNum, String month, Integer estimateLossMin) {
        GenericResponse<ReadFromExcelResponse> result = new GenericResponse<>();
        Workbook wb = null;
        int total = 0;
        int totalNull = 0;
        try {
            wb = WorkbookFactory.create(new File(pathFile));
            Sheet sheet = wb.getSheetAt(sheetNum);
            for(int i = 1; i <= sheet.getLastRowNum(); i++) {
                Optional<Row> row = Optional.ofNullable(sheet.getRow(i));
                Optional<Cell> entryDate = Optional.ofNullable(row.get().getCell(15));
                Optional<Integer> estimateLoss = Optional.of((int) row.get().getCell(18).getNumericCellValue());
                LocalDateTime date = entryDate.get().getLocalDateTimeCellValue();
                if(date.getMonth().toString().equals(month)) {
                    if( estimateLoss.get() > estimateLossMin) {
                        total++;
                    }
                }
            }
            ReadFromExcelResponse response = new ReadFromExcelResponse()
                    .setTotal(total);
            result.setStatus(Status.ok);
            result.setResponse(response);

        } catch (Exception e) {
            totalNull++;
        }
        System.out.println("Total Null : " + totalNull);
        return result;

    }
}
