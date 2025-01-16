package api.dashboard.insurance.system.controller;

import api.dashboard.insurance.system.model.common.AdapterResponse;
import api.dashboard.insurance.system.model.rqrs.info.ResponseInfo;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.usecase.ReadFromExcelUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class ReadFromExcelController {

    @Autowired
    private ReadFromExcelUseCase readFromExcelUseCase;

    @GetMapping("read")
    public ResponseEntity<?> readFromExcel(
            @RequestParam String month
            , @RequestHeader(name = "Authorization") String token
    ) {

        ResponseInfo responseInfo = readFromExcelUseCase.exectute(month, token);
        return ResponseEntity
                .status(responseInfo.getHttpStatus())
                .body(responseInfo.getResponse());
    }

//    public ResponseEntity<?> refreshData(
//            @RequestHeader(name = "Authorization") String token
//    ) {
//        ResponseInfo responseInfo = readFromExcelUseCase.refreshData(token);
//        return ResponseEntity
//                .status(responseInfo.getHttpStatus())
//                .body(responseInfo.getResponse());
//    }


}
