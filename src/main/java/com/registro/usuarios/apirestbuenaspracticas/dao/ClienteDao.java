package com.registro.usuarios.apirestbuenaspracticas.dao;

import com.registro.usuarios.apirestbuenaspracticas.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, Integer> {
}
