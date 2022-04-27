package ai.techfin.tradesystem.web.rest;

import ai.techfin.tradesystem.domain.ModelOrder;
import ai.techfin.tradesystem.domain.ModelOrderList;
import ai.techfin.tradesystem.domain.Product;
import ai.techfin.tradesystem.repository.ModelOrderListRepository;
import ai.techfin.tradesystem.repository.ProductAccountRepository;
import ai.techfin.tradesystem.security.AuthoritiesConstants;
import ai.techfin.tradesystem.service.ModelOrderService;
import ai.techfin.tradesystem.service.dto.ModelOrderDTO;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderListTwoDimArrayVM;
import ai.techfin.tradesystem.web.rest.vm.ModelOrderListVM;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ModelOrderController {

    private static final Logger logger = LoggerFactory.getLogger(ModelOrderController.class);

    private final ModelOrderListRepository modelOrderListRepository;

    private final ProductAccountRepository productAccountRepository;

    private final ModelOrderService modelOrderService;

    @Autowired
    public ModelOrderController(ModelOrderListRepository modelOrderListRepository,
        ProductAccountRepository productAccountRepository, ModelOrderService modelOrderService) {
        this.modelOrderListRepository = modelOrderListRepository;
        this.productAccountRepository = productAccountRepository;
        this.modelOrderService = modelOrderService;
    }

    @PostMapping("/model-order-list")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured(AuthoritiesConstants.MODEL)
    public void create(@RequestBody ModelOrderListTwoDimArrayVM vm) {
        String productName = vm.getProduct();
        Optional<Product> product = productAccountRepository.findByName(productName);
        if (ObjectUtils.isEmpty(product)) {
            throw new NoSuchElementException();
        }
        HashSet<ModelOrder> sellOrders = vm.getSellOrders();
        HashSet<ModelOrder> buyOrders = vm.getBuyOrders();
        HashSet<ModelOrder> orders = new HashSet<>(sellOrders.size() + buyOrders.size());
        orders.addAll(sellOrders);
        orders.addAll(buyOrders);
        ModelOrderList created = new ModelOrderList(vm.getModel(), product.get(), orders);
        modelOrderListRepository.save(created);
        modelOrderService.prepareTrade(created);
    }

    @GetMapping("/model-order-list")
    @Secured(AuthoritiesConstants.TRADER)
    public List<ModelOrderListVM> queryModelOrderListInVM(@RequestParam @NotNull Instant begin,
        @RequestParam @NotNull Instant end, @RequestParam @NotNull Long productId) {
        logger.info("going to select between: {} to {}", begin, end);
        Optional<Product> product = productAccountRepository.findById(productId);
        if (ObjectUtils.isEmpty(product)) {
            return null;
        }

        List<ModelOrderList> orderLists = modelOrderListRepository
            .findByCreatedAtBetweenAndProduct(begin, end, product.get());

        if (orderLists.size() == 0) {
            return null;
        }

        return orderLists.stream().map(orderList -> {
            List<ModelOrderDTO> placements = orderList.getOrders().stream()
                .map(order -> modelOrderService.createDTO(order, product.get().getProvider()))
                .collect(Collectors.toList());

            return new ModelOrderListVM(placements, orderList.getModel(), product.get().getName(),
                orderList.getCreatedAt(), orderList.getId());
        }).collect(Collectors.toList());
    }

}
