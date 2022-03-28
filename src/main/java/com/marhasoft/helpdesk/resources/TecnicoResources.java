package com.marhasoft.helpdesk.resources;

import com.marhasoft.helpdesk.domain.Tecnico;
import com.marhasoft.helpdesk.domain.dtos.TecnicoDTO;
import com.marhasoft.helpdesk.services.TecnicoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Técnicos", tags = "Técnicos")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResources {

    @Autowired
    private TecnicoService tecnicoService;

    @ApiOperation(value = "Retorna técnico por id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico tecnico = tecnicoService.findById(id);
        return ResponseEntity.ok().body(new TecnicoDTO(tecnico));
    }

    @ApiOperation(value = "Exibe lista de técnico")
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> tecnicos = tecnicoService.findAll();
        List<TecnicoDTO> tecnicoDTOS = tecnicos.stream()
                .map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(tecnicoDTOS);
    }

    @ApiOperation(value = "Cadastra técnico")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
        Tecnico newTecnico = tecnicoService.create(tecnicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTecnico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Atualiza técnico")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO tecnicoDTO) {
        System.out.println("Data "+tecnicoDTO);
        Tecnico tecnico = tecnicoService.update(id, tecnicoDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(tecnico));
    }

    @ApiOperation(value = "Deleta técnico")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
        tecnicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

