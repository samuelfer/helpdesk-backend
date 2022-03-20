package com.marhasoft.helpdesk.services;

import com.marhasoft.helpdesk.domain.Chamado;
import com.marhasoft.helpdesk.domain.Cliente;
import com.marhasoft.helpdesk.domain.Tecnico;
import com.marhasoft.helpdesk.domain.enums.Perfil;
import com.marhasoft.helpdesk.domain.enums.Prioridade;
import com.marhasoft.helpdesk.domain.enums.Status;
import com.marhasoft.helpdesk.repositories.ChamadoRepository;
import com.marhasoft.helpdesk.repositories.ClienteRepository;
import com.marhasoft.helpdesk.repositories.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    public void instanciaDB() {
        Tecnico tec1 = new Tecnico(null, "Samuel Fernandes", "24437060016", "samuelfesan@gmail.com", "123");
        tec1.addPerfil(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "Saulo", "32185698001", "saulo@gmail.com", "123");

        Chamado cha1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 001", "Primeiro chamado", tec1, cli1);

        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(cha1));

    }
}
