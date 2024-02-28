package by.kladvirov.controller;

import by.kladvirov.dto.GoodCreationDto;
import by.kladvirov.dto.GoodDto;
import by.kladvirov.dto.GoodUpdateDto;
import by.kladvirov.service.GoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
@RequestMapping("/goods")
public class GoodController {

    private final GoodService goodService;

    @GetMapping("/{id}")
    public ResponseEntity<GoodDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(goodService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GoodDto>> getAllGoods(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(goodService.findAll(size, page), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<GoodDto> createGood(@RequestBody @Valid GoodCreationDto goodCreationDto) {
        return new ResponseEntity<>(goodService.save(goodCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody @Valid GoodUpdateDto goodUpdateDto) {
        goodService.update(id, goodUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        goodService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

