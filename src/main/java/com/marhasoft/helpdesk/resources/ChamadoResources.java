package com.marhasoft.helpdesk.resources;

import com.marhasoft.helpdesk.domain.Chamado;
import com.marhasoft.helpdesk.domain.dtos.ChamadoDTO;
import com.marhasoft.helpdesk.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResources {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        Chamado chamado = chamadoService.findById(id);
        return ResponseEntity.ok().body(new ChamadoDTO(chamado));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        List<Chamado> chamados = chamadoService.findAll();
        List<ChamadoDTO> chamadosDTO = chamados.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(chamadosDTO);
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO) {
        Chamado chamado = chamadoService.create(chamadoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(chamado.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
