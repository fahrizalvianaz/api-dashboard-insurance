package api.dashboard.insurance.system.controller;

import api.dashboard.insurance.system.model.common.AdapterResponse;
import api.dashboard.insurance.system.model.rqrs.info.ResponseInfo;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.model.rqrs.response.AddDataResponse;
import api.dashboard.insurance.system.usecase.AddDataUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/")
public class AddDataController {

    @Autowired
    private AddDataUseCase addDataUseCase;

    @PostMapping(value = "add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addData(
            @RequestBody MultipartFile file,
            @RequestParam(name = "username") String username
    ) {
        ResponseInfo responseInfo = addDataUseCase.execute(username, file);
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
