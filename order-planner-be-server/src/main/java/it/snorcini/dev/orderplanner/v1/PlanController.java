package it.snorcini.dev.orderplanner.v1;

import it.snorcini.dev.orderplanner.dto.PlanResponse;
import it.snorcini.dev.orderplanner.exception.OrderPlannerServiceException;
import it.snorcini.dev.orderplanner.service.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/plan")
@Slf4j
@AllArgsConstructor
public class PlanController {

    @NotNull
    private OrderService orderService;

    /**
     * Return the delivery path.
     *
     * @return the plan
     */
    @GetMapping(value = "/{depotUid}")
    public ResponseEntity<PlanResponse> startPlan(@PathVariable final String depotUid)
            throws OrderPlannerServiceException {
        log.debug("planController.startPlan[]");
        return new ResponseEntity<>(
                orderService.startPlan(depotUid),
                HttpStatus.OK);
    }
}
