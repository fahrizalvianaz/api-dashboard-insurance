package api.dashboard.insurance.system.service;

import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.entity.ClaimRegister;
import api.dashboard.insurance.system.model.entity.Identity;
import api.dashboard.insurance.system.model.enums.Status;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.model.rqrs.response.AddDataResponse;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.model.rqrs.response.RefreshFromExcelResponse;
import api.dashboard.insurance.system.repository.ClaimRegisterRepository;
import api.dashboard.insurance.system.repository.IdentifyRepository;
import api.dashboard.insurance.system.utils.JwtUtil;
import api.dashboard.insurance.system.utils.StoreDataToRedis;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AddDataService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String CACHE_KEY_PREFIX = "estimasi_loss:";

    @Autowired
    private IdentifyRepository identifyRepository;

    @Autowired
    private ClaimRegisterRepository claimRegisterRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StoreDataToRedis storeDataToRedis;

    public GenericResponse<AddDataResponse> execute(String username, MultipartFile file) {

        GenericResponse<AddDataResponse> result = new GenericResponse<>();
        String token = jwtUtil.generateToken(username);
        Identity identity = new Identity();
        identity.setUsername(username);
        Identity identitySave = identifyRepository.save(identity);
        try(Workbook wb = new XSSFWorkbook(file.getInputStream());) {
            Sheet sheet = wb.getSheetAt(4);
            sheet.forEach(row -> {
//                LocalDateTime entryDate = row.getCell(15).getLocalDateTimeCellValue();
//                String claimNumber = row.getCell(0).getStringCellValue();
//                double estimateLoss = row.getCell(18).getNumericCellValue();
//                Optional<Integer> estimateLoss = Optional.ofNullable(row.getCell(18))
//                        .map(cell -> (int) cell.getNumericCellValue());
                ClaimRegister  claimRegister = new ClaimRegister();
                if (row.getRowNum() == 0) {
                    return;
                }
                claimRegister.setUser(identitySave);
                claimRegister.setEntryDate(getCellValueAsString(row.getCell(15)));
                claimRegister.setClaimNo(getCellValueAsString(row.getCell(0)));
                claimRegister.setEstimateLoss(getCellValueAsString(row.getCell(18)));
                ClaimRegister claim = claimRegisterRepository.save(claimRegister);

                System.out.println("estimasi: " + claim.getEstimateLoss());

            });


            wb.close();
            AddDataResponse response = new AddDataResponse()
                    .setAccessToken(token);
            result.setStatus(Status.ok);
            result.setResponse(response);
//
//
//            String ret = storeDataToRedis.storeData(token, request.getFileLocation(), request.getSheet());
//            if(ret.equals("success")) {
//                AddDataResponse response = new AddDataResponse()
//                        .setAccessToken(token);
//                result.setStatus(Status.ok);
//                result.setResponse(response);
//            }
        } catch (Exception e) {
                throw new RuntimeException(e);
        }
        return result;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                Optional<Integer> estimateLoss = Optional.of(cell)
                        .map(e -> (int) e.getNumericCellValue());
                return String.valueOf(estimateLoss.get());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }
}
