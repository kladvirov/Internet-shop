package by.kladvirov.controller;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Order controller", description = "Order API")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_ORDERS')")
    @Operation(summary = "Get role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderDto> getById(
            @PathVariable("id")
            @Parameter(description = "Order id", example = "11", required = true) Long id
    ) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ORDERS')")
    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Orders not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<OrderDto>> getAllOrders(Pageable pageable) {
        return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ORDERS')")
    @Operation(summary = "Create new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Order hasn't been created. Check the data validity"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody
            @Valid
            @Parameter(description = "Info towards creating order", required = true) OrderCreationDto orderCreationDto
    ) {
        return new ResponseEntity<>(orderService.save(orderCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ORDERS')")
    @Operation(summary = "Update order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Order hasn't been updated. Check the data validity"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id")
            @Parameter(description = "Order id", example = "11", required = true)  Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Info towards updating order", required = true) OrderCreationDto orderCreationDto
    ) {
        orderService.update(id, orderCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ORDERS')")
    @Operation(summary = "Delete order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order has been deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Order hasn't been deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HttpStatus> deleteById(
            @PathVariable("id")
            @Parameter(description = "Order id", example = "11", required = true) Long id
    ) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
