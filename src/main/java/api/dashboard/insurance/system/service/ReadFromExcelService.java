package api.dashboard.insurance.system.service;

import api.dashboard.insurance.system.model.common.GenericResponse;
import api.dashboard.insurance.system.model.enums.Status;
import api.dashboard.insurance.system.model.rqrs.response.ReadFromExcelResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ReadFromExcelService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public GenericResponse<ReadFromExcelResponse> execute(String token, String month) {
        GenericResponse<ReadFromExcelResponse> result = new GenericResponse<>();
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        String key = token + ":" + month;
        try {
        Long totalOverThan100MCache = setOps.size(key + ":totalOverThan100M");
        Long totalOverThan50MCache = setOps.size(key + ":totalOverThan50M");
        Long totalOverThan25MCache = setOps.size(key + ":totalOverThan25M");
        Long totalLessThan25MCache = setOps.size(key + ":totalLessThan25M");
        Long totalUnknownCache = setOps.size(token + ":UNKNOWN");
        if(totalOverThan100MCache == null) totalOverThan100MCache = 0L;
        if(totalOverThan50MCache == null) totalOverThan50MCache = 0L;
        if(totalOverThan25MCache == null) totalOverThan25MCache = 0L;
        if(totalLessThan25MCache == null) totalLessThan25MCache = 0L;
        if(totalUnknownCache == null) totalUnknownCache = 0L;

        ReadFromExcelResponse response = new ReadFromExcelResponse()
                .setTotalOverThan100M(totalOverThan100MCache)
                .setTotalOverThan50M(totalOverThan50MCache)
                .setTotalOverThan25M(totalOverThan25MCache)
                .setTotalLessThan25M(totalLessThan25MCache)
                .setTotalUnknown(totalUnknownCache);
        result.setStatus(Status.ok);
        result.setResponse(response);
        } catch (Exception e) {
            result.setStatus(Status.error).setException(e);
        } catch (OutOfMemoryError e) {
            result.setStatus(Status.error);
        }

        return result;

    }
}
