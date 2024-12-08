package api.dashboard.insurance.system.controller;

import api.dashboard.insurance.system.model.common.AdapterResponse;
import api.dashboard.insurance.system.model.rqrs.info.ResponseInfo;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.model.rqrs.response.AddDataResponse;
import api.dashboard.insurance.system.usecase.AddDataUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/")
public class AddDataController {

    @Autowired
    private AddDataUseCase addDataUseCase;

    @PostMapping("add")
    public ResponseEntity<?> addData(
            @RequestBody AddDataRequest addDataRequest
    ) {
        ResponseInfo responseInfo = addDataUseCase.execute(addDataRequest);
        return ResponseEntity
                .status(responseInfo.getHttpStatus())
                .body(responseInfo.getResponse());
    }

    @GetMapping("test")
    public ResponseEntity<?> test(

    ) {
        return ResponseEntity.ok("Niceee");
    }
}
