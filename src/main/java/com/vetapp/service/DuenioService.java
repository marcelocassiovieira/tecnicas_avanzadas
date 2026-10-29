package com.vetapp.service;

import com.vetapp.exception.RecursoNoEncontradoException;
import com.vetapp.model.Duenio;
import com.vetapp.repository.DuenioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuenioService {

    private final DuenioRepository duenioRepository;

    @Autowired
    public DuenioService(DuenioRepository duenioRepository) {
        this.duenioRepository = duenioRepository;
    }

    public List<Duenio> listarTodos() {
        return duenioRepository.findAll();
    }

    public Duenio buscarPorId(Long id) {
        return duenioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Dueño no encontrado con id " + id));
    }

    public Duenio crear(Duenio duenio) {
        return duenioRepository.save(duenio);
    }

    public Duenio actualizar(Long id, Duenio datos) {
        Duenio existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setDni(datos.getDni());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        return duenioRepository.save(existente);
    }

    public void eliminar(Long id) {
        Duenio existente = buscarPorId(id);
        duenioRepository.delete(existente);
    }
}
