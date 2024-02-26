package by.kladvirov.controller;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping()
    public List<OrderDto> getAllRoles(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return orderService.findAll(size, page);
    }


    @PostMapping()
    public OrderDto createRole(@RequestBody @Valid OrderCreationDto orderCreationDto) {
        return orderService.save(orderCreationDto);
    }

    @PutMapping("{id}")
    public String update(@PathVariable("id") Long id, @RequestBody @Valid OrderCreationDto orderCreationDto) {
        orderService.update(id, orderCreationDto);
        return "Order has been updated successfully";
    }

    @DeleteMapping("{id}")
    public String deleteById(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "Order has been deleted successfully";
    }

}
