package com.marhasoft.helpdesk.services;

import com.marhasoft.helpdesk.domain.Chamado;
import com.marhasoft.helpdesk.repositories.ChamadoRepository;
import com.marhasoft.helpdesk.repositories.PessoaRepository;
import com.marhasoft.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Chamado findById(Integer id) {
        Optional<Chamado> chamado = chamadoRepository.findById(id);
        return chamado.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontado com id: "+id));
    }
}
