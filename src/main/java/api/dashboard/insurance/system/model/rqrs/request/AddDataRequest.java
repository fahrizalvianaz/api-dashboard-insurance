package api.dashboard.insurance.system.model.rqrs.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
public class AddDataRequest {
    private String username;
    private MultipartFile file;
}
