package api.dashboard.insurance.system.service;

import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.enums.Status;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ReadFromExcelService {

    public GenericResponse<ReadFromExcelResponse> execute(String pathFile, Integer sheetNum, String month) {
        GenericResponse<ReadFromExcelResponse> result = new GenericResponse<>();
        Workbook wb = null;
        AtomicInteger totalOverThan100M = new AtomicInteger();
        AtomicInteger totalOverThan50M = new AtomicInteger();
        AtomicInteger totalOverThan25M = new AtomicInteger();
        AtomicInteger totalLessThan25M = new AtomicInteger();
        AtomicInteger totalNull = new AtomicInteger();
        try (FileInputStream inp = new FileInputStream(pathFile)){
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(sheetNum);

            sheet.forEach(row -> {
                if (row.getRowNum() == 0) {
                    return;
                }

                Optional<Cell> entryDate = Optional.ofNullable(row.getCell(15));
                Optional<Integer> estimateLoss = Optional.ofNullable(row.getCell(18))
                        .map(cell -> (int) cell.getNumericCellValue());

                if (entryDate.isPresent()) {
                    LocalDateTime date = entryDate.get().getLocalDateTimeCellValue();
                    if (date.getMonth().toString().equals(month)) {
                        estimateLoss.ifPresent(loss -> {
                            if (loss >= 100000000) {
                                totalOverThan100M.getAndIncrement();
                            }

                            if (loss >= 50000000) {
                                totalOverThan50M.getAndIncrement();
                            }

                            if (loss >= 25000000) {
                                totalOverThan25M.getAndIncrement();
                            }

                            if(loss < 25000000) {
                                totalLessThan25M.getAndIncrement();
                            }
                        });
                    }
                } else {
                    totalNull.getAndIncrement();
                }
            });
            ReadFromExcelResponse response = new ReadFromExcelResponse()
                    .setTotalOverThan100M(totalOverThan100M.get())
                    .setTotalOverThan50M(totalOverThan50M.get())
                    .setTotalOverThan25M(totalOverThan25M.get())
                    .setTotalLessThan25M(totalLessThan25M.get())
                    .setTotalUnknown(totalNull.get());
            result.setStatus(Status.ok);
            result.setResponse(response);
        wb.close();
        } catch (Exception e) {
            result.setStatus(Status.error).setException(e);
        }
        System.out.println("Total Null : " + totalNull);
        return result;

    }
}
