package com.marhasoft.helpdesk.services;

import com.marhasoft.helpdesk.domain.Pessoa;
import com.marhasoft.helpdesk.domain.Tecnico;
import com.marhasoft.helpdesk.domain.dtos.TecnicoDTO;
import com.marhasoft.helpdesk.repositories.PessoaRepository;
import com.marhasoft.helpdesk.repositories.TecnicoRepository;
import com.marhasoft.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marhasoft.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
        return tecnico.orElseThrow(() -> new ObjectNotFoundException("Técnico não encontado com id: "+id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
        validaPorCpfAndEmail(tecnicoDTO);
        Tecnico newTecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(newTecnico);
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(id);
        Tecnico oldTecnico = findById(id);

        if(!tecnicoDTO.getSenha().equals(oldTecnico.getSenha())) {
            tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
        }

        validaPorCpfAndEmail(tecnicoDTO);
        oldTecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(oldTecnico);
    }

    public void delete(Integer id) {
        Tecnico tecnico = findById(id);
        if (tecnico.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("O técnico possui ordens de serviço e não pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }

    private void validaPorCpfAndEmail(TecnicoDTO tecnicoDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
        if (pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        pessoa = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
        if (pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }
}
