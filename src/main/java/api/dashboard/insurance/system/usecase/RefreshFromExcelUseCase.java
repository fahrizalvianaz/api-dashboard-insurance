//package api.dashboard.insurance.system.usecase;
//
//import api.dashboard.insurance.system.model.common.AdapterResponse;
//import api.dashboard.insurance.system.model.common.GenericResponse;
//import api.dashboard.insurance.system.model.entity.Identity;
//import api.dashboard.insurance.system.model.rqrs.info.ResponseInfo;
//import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
//import api.dashboard.insurance.system.model.rqrs.response.RefreshFromExcelResponse;
//import api.dashboard.insurance.system.repository.IdentifyRepository;
//import api.dashboard.insurance.system.service.ReadFromExcelService;
//import api.dashboard.insurance.system.service.RefreshFromExcelService;
//import api.dashboard.insurance.system.utils.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class RefreshFromExcelUseCase {
//
//    @Autowired
//    private RefreshFromExcelService refreshFromExcelService;
//
//    @Autowired
//    private IdentifyRepository identifyRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public ResponseInfo exectute(String token) {
//        ResponseInfo responseInfo = new ResponseInfo();
//        HttpStatus httpStatus = HttpStatus.OK;
//        AdapterResponse<RefreshFromExcelResponse> adapterResponse = new AdapterResponse<>();
//        GenericResponse<RefreshFromExcelResponse> genericResponse = new GenericResponse<>();
//        String jwt = token.substring(7);
//        String username = jwtUtil.extractUsername(jwt);
//        Optional<Identity> identity = identifyRepository.findByUsername(username);
//        String fileLocation = identity.get().getFileLocation();
//        Integer sheet = identity.get().getSheet();
//
//        if (identity.isPresent() && !fileLocation.isEmpty() && sheet != null) {
//            genericResponse = refreshFromExcelService.execute(jwt, fileLocation, sheet);
//        } else {
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//            adapterResponse.setCode("99").setMessage(genericResponse.getException().getDescription());
//        }
//
//        if(genericResponse.isOK()) {
//            adapterResponse.setSuccess().setData(genericResponse.getResponse());
//        } else if (genericResponse.isError()) {
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//            adapterResponse.setCode("99").setMessage(genericResponse.getException().getDescription());
//        }
//
//        responseInfo
//                .setHttpStatus(httpStatus)
//                .setStatus(genericResponse.getStatus())
//                .setResponse(adapterResponse.setStatus(genericResponse.getStatus()))
//                .addException(genericResponse.getException());
//        return responseInfo;
//    }
//}
