package by.kladvirov.controller;

import by.kladvirov.dto.AuthorityCreationDto;
import by.kladvirov.dto.AuthorityDto;
import by.kladvirov.service.AuthorityService;
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
@RequestMapping("/authorities")
@Tag(name = "Authority controller", description = "Authority API")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_AUTHORITIES')")
    @Operation(summary = "Get authority by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Authority not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorityDto> getById(
            @PathVariable("id")
            @Parameter(description = "Authority id", example = "11", required = true) Long id
    ) {
        return new ResponseEntity<>(authorityService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_AUTHORITIES')")
    @Operation(summary = "Get all authorities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Authorities not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<AuthorityDto>> getAllAuthorities(Pageable pageable) {
        return new ResponseEntity<>(authorityService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_AUTHORITIES')")
    @Operation(summary = "Create new authority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Authority has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Authority hasn't been created. Check the data validity"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorityDto> createAuthority(
            @RequestBody
            @Valid
            @Parameter(description = "Info towards creating authority", required = true) AuthorityCreationDto authorityCreationDto
    ) {
        return new ResponseEntity<>(authorityService.save(authorityCreationDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_AUTHORITIES')")
    @Operation(summary = "Update authority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Authority hasn't been updated. Check the data validity"),
            @ApiResponse(responseCode = "404", description = "Authority not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HttpStatus> update(
            @PathVariable("id")
            @Parameter(description = "Authority id", example = "11", required = true) Long id,
            @RequestBody
            @Valid
            @Parameter(description = "Info towards updating authority", required = true) AuthorityCreationDto authorityCreationDto
    ) {
        authorityService.update(id, authorityCreationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_AUTHORITIES')")
    @Operation(summary = "Delete authority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Authority has been deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Authority hasn't been deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HttpStatus> deleteById(
            @PathVariable("id")
            @Parameter(description = "Authority id", example = "11", required = true) Long id
    ) {
        authorityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
