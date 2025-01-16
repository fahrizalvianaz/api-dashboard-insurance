package api.dashboard.insurance.system.service;

import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.entity.Identity;
import api.dashboard.insurance.system.model.enums.Status;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import api.dashboard.insurance.system.model.rqrs.response.RefreshFromExcelResponse;
import api.dashboard.insurance.system.repository.IdentifyRepository;
import api.dashboard.insurance.system.utils.JwtUtil;
import api.dashboard.insurance.system.utils.StoreDataToRedis;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RefreshFromExcelService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StoreDataToRedis storeDataToRedis;

    public GenericResponse<RefreshFromExcelResponse> execute(String token, String fileLocation, Integer numberSheet) {
        GenericResponse<RefreshFromExcelResponse> result = new GenericResponse<>();
        Workbook wb = null;
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        try {
            deleteFromRedis(token);
            String ret = storeDataToRedis.storeData(token, fileLocation, numberSheet);
            if(ret.equals("success")) {
                RefreshFromExcelResponse response = new RefreshFromExcelResponse()
                        .setMessage("Updated Successfully");
                result.setStatus(Status.ok);
                result.setResponse(response);
            }
        } catch (Exception e) {
            result.setStatus(Status.error).setException(e);
        } catch (OutOfMemoryError e) {
            result.setStatus(Status.error);
        }

        return result;

    }

    private void deleteFromRedis(String key) {
        try {
             Set<String> keys = redisTemplate.keys(key + "*");
             if (keys != null && !keys.isEmpty()) {
                 redisTemplate.delete(keys);
             }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete Redis data for key: " + key, e);
        }
    }
}
