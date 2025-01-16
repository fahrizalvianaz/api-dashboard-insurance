package api.dashboard.insurance.system.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.UUID;

@Component
public class StoreDataToRedis {

    private static final String CACHE_KEY_PREFIX = "estimasi_loss:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String storeData(
            String token
            , String fileLocation
            , Integer numberSheet
            ) {
        Workbook wb = null;
        try(FileInputStream inp = new FileInputStream(fileLocation)) {
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(numberSheet);
            sheet.forEach(row -> {
                if (row.getRowNum() == 0) {
                    return;
                }
                Optional<Cell> claimNumber = Optional.ofNullable(row.getCell(0));
                Optional<Cell> entryDate = Optional.ofNullable(row.getCell(15));
                Optional<Integer> estimateLoss = Optional.ofNullable(row.getCell(18))
                        .map(cell -> (int) cell.getNumericCellValue());
                if (entryDate.isEmpty()) {
                    String monthKey = token + ":UNKNOWN";
                    redisTemplate.opsForSet().add(monthKey, UUID.randomUUID() + ":NULL");
                } else {
                    LocalDateTime localDateTime = entryDate.get().getLocalDateTimeCellValue();
                    Month month = localDateTime.getMonth();
                    totalOverThan(estimateLoss, month.name(), claimNumber.get().getStringCellValue(), token);
                }
            });
            wb.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "Success";

    }

    private void totalOverThan(Optional<Integer> estimateLoss, String month, String claimNumber, String token) {

        estimateLoss.ifPresent(loss -> {
            String value =  claimNumber +  ":" + CACHE_KEY_PREFIX + loss;
            String monthKey = token + ":" + month;
            if (loss >= 100000000) {
//                redisTemplate.opsForValue().set(key, );
                String key = monthKey + ":totalOverThan100M";
                redisTemplate.opsForSet().add(key, UUID.randomUUID() + ":" + value);
            }

            if (loss >= 50000000) {
//                redisTemplate.opsForValue().set(key, );
                String key = monthKey + ":totalOverThan50M";
                redisTemplate.opsForSet().add(key, UUID.randomUUID() + ":" + value);
            }

            if (loss >= 25000000) {
//                redisTemplate.opsForValue().set(key, );
                String key = monthKey + ":totalOverThan25M";
                redisTemplate.opsForSet().add(key, UUID.randomUUID() + ":" + value);
            }

            if (loss < 25000000) {
//                redisTemplate.opsForValue().set(key, );
                String key = monthKey + ":totalLessThan25M";
                redisTemplate.opsForSet().add(key, UUID.randomUUID() + ":" + value);
            }
        });
    }
}
