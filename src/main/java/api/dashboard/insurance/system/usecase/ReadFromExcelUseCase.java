package api.dashboard.insurance.system.usecase;

import api.dashboard.insurance.system.model.common.AdapterResponse;
import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.entity.Identity;
import api.dashboard.insurance.system.model.rqrs.info.ResponseInfo;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.repository.IdentifyRepository;
import api.dashboard.insurance.system.service.ReadFromExcelService;
import api.dashboard.insurance.system.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private IdentifyRepository identifyRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseInfo exectute(String month, String token) {
        ResponseInfo responseInfo = new ResponseInfo();
        HttpStatus httpStatus = HttpStatus.OK;
        AdapterResponse<ReadFromExcelResponse> adapterResponse = new AdapterResponse<>();
        GenericResponse<ReadFromExcelResponse> genericResponse = new GenericResponse<>();
        String jwt = token.substring(7);
        System.out.println(jwt);
        String username = jwtUtil.extractUsername(jwt);
        Optional<Identity> identity = identifyRepository.findByUsername(username);

        if (identity.isPresent()) {
            String pathFile = identity.get().getFileLocation();
            Integer sheet = identity.get().getSheet();
            genericResponse = readFromExcelService.execute(pathFile, sheet, month);
        }

        if(genericResponse.isOK()) {
            adapterResponse.setSuccess().setData(genericResponse.getResponse());
        } else if (genericResponse.isError()) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            adapterResponse.setCode("99").setMessage(genericResponse.getException().getDescription());
        } else {

        }

        responseInfo
                .setHttpStatus(httpStatus)
                .setStatus(genericResponse.getStatus())
                .setResponse(adapterResponse.setStatus(genericResponse.getStatus()))
                .addException(genericResponse.getException());
        return responseInfo;
    }
}
