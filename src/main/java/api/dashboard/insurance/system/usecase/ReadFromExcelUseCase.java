package api.dashboard.insurance.system.usecase;

import api.dashboard.insurance.system.model.common.AdapterResponse;
import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.service.ReadFromExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class ReadFromExcelUseCase {

//    private static final String CSV_FILE_LOCATION = "C:/Users/fahrizal.aziz_idstar/myProject/document/Claim Register Complex Case 2024.xlsx";
    @Autowired
    private ReadFromExcelService readFromExcelService;


    public AdapterResponse<ReadFromExcelResponse> exectute(String month, Integer estimateLoseMin, String token) {
        Workbook wb;
        AdapterResponse<ReadFromExcelResponse> adapterResponse = new AdapterResponse<>();
        String pathFile = "";
        Integer sheet = 0;
        GenericResponse<ReadFromExcelResponse> result = readFromExcelService.execute(pathFile, sheet, month, estimateLoseMin);

        if(result.isOK()) {
            adapterResponse.setSuccess().setData(result.getResponse());
        } else if (result.isError()) {
            adapterResponse.setCode("99");
        }

        return adapterResponse;
    }
}
