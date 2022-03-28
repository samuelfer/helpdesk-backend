package com.marhasoft.helpdesk.resources;

import com.marhasoft.helpdesk.domain.Cliente;
import com.marhasoft.helpdesk.domain.Tecnico;
import com.marhasoft.helpdesk.domain.dtos.ClienteDTO;
import com.marhasoft.helpdesk.domain.dtos.TecnicoDTO;
import com.marhasoft.helpdesk.services.ClienteService;
import com.marhasoft.helpdesk.services.TecnicoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Clientes", tags = "Clientes")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {

    @Autowired
    private ClienteService clienteService;

    @ApiOperation(value = "Retorna cliente por id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(cliente));
    }

    @ApiOperation(value = "Exibe lista de clientes")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(clientesDTO);
    }

    @ApiOperation(value = "Cadastra cliente")
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente newCliente = clienteService.create(clienteDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Atualiza cliente")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.update(id, clienteDTO);
        return ResponseEntity.ok().body(new ClienteDTO(cliente));
    }

    @ApiOperation(value = "Deleta cliente")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

