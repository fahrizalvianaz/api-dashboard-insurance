package api.dashboard.insurance.system.controller;

import api.dashboard.insurance.system.model.common.AdapterResponse;
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
            , @RequestParam Integer estimateLoseMin
            , @RequestHeader(name = "Authorization") String token
    ) {
        return ResponseEntity.ok(readFromExcelUseCase.exectute(month, estimateLoseMin, token));
    }


}
