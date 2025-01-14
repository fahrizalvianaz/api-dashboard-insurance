package api.dashboard.insurance.system.service;

import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.entity.Identity;
import api.dashboard.insurance.system.model.enums.Status;
import api.dashboard.insurance.system.model.rqrs.request.AddDataRequest;
import api.dashboard.insurance.system.model.rqrs.response.AddDataResponse;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.repository.IdentifyRepository;
import api.dashboard.insurance.system.utils.JwtUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
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
    private JwtUtil jwtUtil;

    public GenericResponse<AddDataResponse> execute(AddDataRequest request) {

        GenericResponse<AddDataResponse> result = new GenericResponse<>();
        String token = jwtUtil.generateToken(request.getUsername());
        Identity identity = new Identity();
        Workbook wb = null;
        try (FileInputStream inp = new FileInputStream(request.getFileLocation())){
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(request.getSheet());
            sheet.forEach(row -> {
                if (row.getRowNum() == 0) {
                    return;
                }
                Optional<Cell> claimNumber = Optional.ofNullable(row.getCell(0));
                Optional<Cell> entryDate = Optional.ofNullable(row.getCell(15));
                Optional<Integer> estimateLoss = Optional.ofNullable(row.getCell(18))
                        .map(cell -> (int) cell.getNumericCellValue());
                LocalDateTime localDateTime = entryDate.get().getLocalDateTimeCellValue();
                Month month = localDateTime.getMonth();
                totalOverThan(estimateLoss, month.name(), claimNumber.get().getStringCellValue());

            });
            wb.close();
        } catch (Exception e) {
            result.setStatus(Status.error).setException(e);
        }
        try {
            identity.setFileLocation(request.getFileLocation());
            identity.setUsername(request.getUsername());
            identity.setSheet(request.getSheet());
            identity.setToken(token);
            identifyRepository.save(identity);
            AddDataResponse response = new AddDataResponse()
                    .setAccessToken(token);
            result.setStatus(Status.ok);
            result.setResponse(response);
            return result;
        } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


     private void totalOverThan(Optional<Integer> estimateLoss, String month, String claimNumber) {
        estimateLoss.ifPresent(loss -> {
            if (loss >= 100000000) {
                String key =  UUID.randomUUID() + ":" + claimNumber + ":" + CACHE_KEY_PREFIX + loss;
//                redisTemplate.opsForValue().set(key, );
                String monthKey = "month:" + month + " totalOverThan100M";
                redisTemplate.opsForSet().add(monthKey, key);
            }

            if (loss >= 50000000) {
                String key =  UUID.randomUUID() + ":" + claimNumber + ":" + CACHE_KEY_PREFIX + loss;
//                redisTemplate.opsForValue().set(key, );
                String monthKey = "month:" + month + " totalOverThan50M";
                redisTemplate.opsForSet().add(monthKey, key);
            }

            if (loss >= 25000000) {
                String key =  UUID.randomUUID() + ":" + claimNumber + ":" + CACHE_KEY_PREFIX + loss;
//                redisTemplate.opsForValue().set(key, );
                String monthKey = "month:" + month + " totalOverThan25M";
                redisTemplate.opsForSet().add(monthKey, key);
            }

            if(loss < 25000000) {
                String key =  UUID.randomUUID() + ":" + claimNumber + ":" + CACHE_KEY_PREFIX + loss;
//                redisTemplate.opsForValue().set(key, );
                String monthKey = "month:" + month + " totalLessThan25M";
                redisTemplate.opsForSet().add(monthKey, key);
            }
        });
    }
}
