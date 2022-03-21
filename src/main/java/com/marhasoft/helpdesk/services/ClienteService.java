package com.marhasoft.helpdesk.services;

import com.marhasoft.helpdesk.domain.Cliente;
import com.marhasoft.helpdesk.domain.Pessoa;
import com.marhasoft.helpdesk.domain.dtos.ClienteDTO;
import com.marhasoft.helpdesk.repositories.ClienteRepository;
import com.marhasoft.helpdesk.repositories.PessoaRepository;
import com.marhasoft.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marhasoft.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontado com id: "+id));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente create(ClienteDTO clienteDTO) {
        clienteDTO.setId(null);
        clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
        validaPorCpfAndEmail(clienteDTO);
        Cliente newCliente = new Cliente(clienteDTO);
        return clienteRepository.save(newCliente);
    }

    public Cliente update(Integer id, ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        Cliente oldCliente = findById(id);
        validaPorCpfAndEmail(clienteDTO);
        oldCliente = new Cliente(clienteDTO);
        return clienteRepository.save(oldCliente);
    }

    public void delete(Integer id) {
        Cliente cliente = findById(id);
        if (cliente.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("O cliente não pode ser deletado!");
        }
        clienteRepository.deleteById(id);
    }

    private void validaPorCpfAndEmail(ClienteDTO clienteDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(clienteDTO.getCpf());
        if (pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        pessoa = pessoaRepository.findByEmail(clienteDTO.getEmail());
        if (pessoa.isPresent() && pessoa.get().getId() != clienteDTO.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }
}
