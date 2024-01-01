package com.registro.usuarios.apirestbuenaspracticas.Services;

import com.registro.usuarios.apirestbuenaspracticas.dto.ClienteDto;
import com.registro.usuarios.apirestbuenaspracticas.entities.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> list ();
    Cliente save(ClienteDto clienteDto);
    Cliente findById(Integer id);
    void delete(Cliente cliente);
    boolean existsById(Integer id);
}
