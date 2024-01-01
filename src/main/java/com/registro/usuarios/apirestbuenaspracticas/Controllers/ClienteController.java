package com.registro.usuarios.apirestbuenaspracticas.Controllers;


import com.registro.usuarios.apirestbuenaspracticas.Services.IClienteService;
import com.registro.usuarios.apirestbuenaspracticas.dto.ClienteDto;
import com.registro.usuarios.apirestbuenaspracticas.entities.Cliente;
import com.registro.usuarios.apirestbuenaspracticas.payload.MensajeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;
    @GetMapping("clientes")
    public ResponseEntity<?> showAll(){
        List<Cliente> clientes=clienteService.list();
        if(clientes==null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No existen registros")
                            .object(null)
                            .build()
                    , HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(clientes)
                        .build()
                , HttpStatus.OK);
    }
    @PostMapping("cliente")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto){
        Cliente clienteSave=null;
        try{
            clienteSave =clienteService.save(clienteDto);
            clienteDto = clienteDto.builder()
                    .idCliente(clienteSave.getIdCliente())
                    .nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .correo(clienteSave.getCorreo())
                    .build();

            return new ResponseEntity<>(
                    MensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(clienteDto)
                    .build()
            , HttpStatus.CREATED);
        }catch(DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @PutMapping("cliente/{id}")
    //@ResponseStatus(HttpStatus.CREATED)
        public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable(name = "id") Integer id){
        Cliente clienteUpdate= null;
        try{
            if (clienteService.existsById(id)){
                clienteDto.setIdCliente(id);
                clienteUpdate= clienteService.save(clienteDto);
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Actualizado correctamente")
                                .object(clienteDto.builder()
                                        .idCliente(clienteUpdate.getIdCliente())
                                        .nombre(clienteUpdate.getNombre())
                                        .apellido(clienteUpdate.getApellido())
                                        .fechaRegistro(clienteUpdate.getFechaRegistro())
                                        .correo(clienteUpdate.getCorreo())
                                        .build())
                                .build()
                        , HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos.")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
        }catch(DataAccessException exDt){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("cliente/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable(name = "id") Integer id){
        //Map<String,Object> response= new HashMap<>();
        try{
            Cliente clienteDelete= clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete,HttpStatus.NO_CONTENT);
        }catch(DataAccessException exDt){
            //response.put("mensaje", exDt.getMessage());
            //response.put("cliente", null);
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                        .mensaje(exDt.getMessage())
                        .object(null)
                        .build()
                    , HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("cliente/{id}")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showById(@PathVariable(name = "id") Integer id){
        Cliente cliente =clienteService.findById(id);

        if(cliente==null){
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intanta buscar, no existe!")
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(ClienteDto.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .correo(cliente.getCorreo())
                                .build())
                        .build()
                , HttpStatus.OK);
    }
}
