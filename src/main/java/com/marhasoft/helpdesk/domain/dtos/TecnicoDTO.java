package com.marhasoft.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marhasoft.helpdesk.domain.Tecnico;
import com.marhasoft.helpdesk.domain.enums.Perfil;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TecnicoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    @NotNull(message = "O campo NOME é requerido")
    protected String nome;
    @NotNull(message = "O campo CPF é requerido")
    @CPF
    protected String cpf;
    @NotNull(message = "O campo EMAIL é requerido")
    protected String email;
    @NotNull(message = "O campo SENHA é requerido")
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public TecnicoDTO() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public TecnicoDTO(Tecnico tecnicoObj) {
        super();
        this.id = tecnicoObj.getId();
        this.nome = tecnicoObj.getNome();
        this.cpf = tecnicoObj.getCpf();
        this.email = tecnicoObj.getEmail();
        this.senha = tecnicoObj.getSenha();
        System.out.println("Entrei"+tecnicoObj.getPerfis());
        this.perfis = tecnicoObj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.dataCriacao = tecnicoObj.getDataCriacao();
        addPerfil(Perfil.CLIENTE);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
