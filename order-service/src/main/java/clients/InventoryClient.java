package clients;

import com.sid.inventory_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Component
@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient{

    @GetMapping(value = "/api/inventory")
    InventoryResponse[] getInventoryResponses(@RequestParam List<String> SkuCodes);
}
