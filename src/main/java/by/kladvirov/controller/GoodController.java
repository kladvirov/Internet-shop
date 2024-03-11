package by.kladvirov.controller;

import by.kladvirov.dto.GoodCreationDto;
import by.kladvirov.dto.GoodDto;
import by.kladvirov.dto.GoodUpdateDto;
import by.kladvirov.service.GoodService;
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
@RequestMapping("/goods")
public class GoodController {

    private final GoodService goodService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_GOODS')")
    public ResponseEntity<GoodDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(goodService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_GOODS')")
    public ResponseEntity<List<GoodDto>> getAllGoods(Pageable pageable) {
        return new ResponseEntity<>(goodService.findAll(pageable), HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_GOODS')")
    public ResponseEntity<GoodDto> createGood(@RequestBody @Valid GoodCreationDto goodCreationDto) {
        return new ResponseEntity<>(goodService.save(goodCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_GOODS')")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody @Valid GoodUpdateDto goodUpdateDto) {
        goodService.update(id, goodUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_GOODS')")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        goodService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

