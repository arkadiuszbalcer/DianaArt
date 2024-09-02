package pl.javastart.dianaart.client;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseService {
  public Map<String, Object> createSuccessResponse(int cartCount){
      Map<String,Object> response = new HashMap<>();
      response.put("success", true);
      response.put("success", cartCount);
      return response;
  }

  public Map<String, Object> createErrorResponse(String errorMessage){
      Map<String,Object> response= new HashMap<>();
      response.put("success", false);
      response.put("error", errorMessage);
      return response;
  }
}
