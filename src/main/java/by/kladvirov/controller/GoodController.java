package by.kladvirov.controller;

import by.kladvirov.dto.GoodCreationDto;
import by.kladvirov.dto.GoodDto;
import by.kladvirov.dto.GoodUpdateDto;
import by.kladvirov.service.GoodService;
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
@RequestMapping("/goods")
public class GoodController {

    private final GoodService goodService;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping("/{id}")
    public GoodDto getById(@PathVariable("id") Long id) {
        return goodService.findById(id);
    }

    @GetMapping()
    public List<GoodDto> getAllGoods(@RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return goodService.findAll(size, page);
    }


    @PostMapping()
    public GoodDto createGood(@RequestBody @Valid GoodCreationDto goodCreationDto) {
        return goodService.save(goodCreationDto);
    }

    @PutMapping("{id}")
    public String update(@PathVariable("id") Long id, @RequestBody @Valid GoodUpdateDto goodUpdateDto) {
        goodService.update(id, goodUpdateDto);
        return "Good has been updated successfully";
    }

    @DeleteMapping("{id}")
    public String deleteById(@PathVariable("id") Long id) {
        goodService.delete(id);
        return "Good has been deleted successfully";
    }

}

