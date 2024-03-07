package by.kladvirov.controller;

import by.kladvirov.dto.OrderCreationDto;
import by.kladvirov.dto.OrderDto;
import by.kladvirov.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(Pageable pageable) {
        return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderCreationDto orderCreationDto) {
        return new ResponseEntity<>(orderService.save(orderCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody @Valid OrderCreationDto orderCreationDto) {
        orderService.update(id, orderCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
