package ai.techfin.tradesystem.service;

import ai.techfin.tradesystem.config.KafkaTopicConfiguration;
import ai.techfin.tradesystem.domain.Placement;
import ai.techfin.tradesystem.domain.PlacementList;
import ai.techfin.tradesystem.repository.PlacementListRepository;
import ai.techfin.xtpms.service.broker.dto.TradeResponseDTO;
import java.math.BigDecimal;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PlacementService {

    private static final Logger log = LoggerFactory.getLogger(PlacementService.class);

    private final PlacementListRepository placementListRepository;

    @Autowired
    public PlacementService(PlacementListRepository placementListRepository) {
        this.placementListRepository = placementListRepository;
        log.debug("placementListRepository: {}", placementListRepository);
    }

    public @NotNull PlacementList findNotNull(Long id) {
        return placementListRepository.findById(id).orElseThrow(ExceptionInInitializerError::new);
    }

    @KafkaListener(topics = KafkaTopicConfiguration.XTP_TRADE_SUCCEED, id = "placement")
    public void recordResultAcc(TradeResponseDTO dto) {
        log.debug("placementListRepository: {}", placementListRepository);
        log.debug("TradeResponseDTO: {}", dto);
        PlacementList placementList = placementListRepository.findById(dto.getPlacementId()).orElse(null);
        if (placementList == null) {
            return;
        }
        Optional<Placement> placementOptional = placementList.getPlacements().stream()
            .filter(p -> p.getStock().equals(dto.getStock())).findFirst();
        if (ObjectUtils.isEmpty(placementOptional.get())) {
            log.warn("an unexpected TradeResponseDTO is received: {}", dto);
            return;
        }
        Placement placement = placementOptional.get();
        placement.setQuantityDealt(placement.getQuantityDealt() + dto.getQuantity());
        placement.setMoneyDealt(
            dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())).add(placement.getMoneyDealt()));
        placementListRepository.save(placementList);
    }

}
