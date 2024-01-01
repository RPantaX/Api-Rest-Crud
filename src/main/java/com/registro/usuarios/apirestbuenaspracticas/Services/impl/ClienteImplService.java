package com.registro.usuarios.apirestbuenaspracticas.Services.impl;

import com.registro.usuarios.apirestbuenaspracticas.Services.IClienteService;
import com.registro.usuarios.apirestbuenaspracticas.dao.ClienteDao;
import com.registro.usuarios.apirestbuenaspracticas.dto.ClienteDto;
import com.registro.usuarios.apirestbuenaspracticas.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteImplService implements IClienteService {
    @Autowired
    private ClienteDao clienteDao;

    @Transactional(readOnly = true) //usamos esto para indicarle que haga los procesos de transacción automáticos, como begin, commit, rollback, etc. y nosotros ya no preocuparnos por ello
    //generalmente usamos @Transactional(readOnly=true) para la operación de búsqueda o recuperación para asegurarnos de que solo
    //podamos realizar la operacion de solo lectura
    @Override
    public List<Cliente> list() {
        return (List) clienteDao.findAll();
    }
    @Transactional
    @Override
    public Cliente save(ClienteDto clienteDto) {
        Cliente cliente= Cliente.builder()
                .idCliente(clienteDto.getIdCliente())
                .nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .correo(clienteDto.getCorreo())
                .build();
        return clienteDao.save(cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public Cliente findById(Integer id) {
        return clienteDao.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    @Override
    public boolean existsById(Integer id) {
        return clienteDao.existsById(id);
    }
}
