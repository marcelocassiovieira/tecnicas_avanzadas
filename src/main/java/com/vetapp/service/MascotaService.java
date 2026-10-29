package com.vetapp.service;

import com.vetapp.exception.RecursoNoEncontradoException;
import com.vetapp.model.Duenio;
import com.vetapp.model.Mascota;
import com.vetapp.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final DuenioService duenioService;

    @Autowired
    public MascotaService(MascotaRepository mascotaRepository, DuenioService duenioService) {
        this.mascotaRepository = mascotaRepository;
        this.duenioService = duenioService;
    }

    public List<Mascota> listarTodas() {
        return mascotaRepository.findAll();
    }

    public Mascota buscarPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Mascota no encontrada con id " + id));
    }

    public List<Mascota> listarPorDuenio(Long duenioId) {
        duenioService.buscarPorId(duenioId);
        return mascotaRepository.findByDuenioId(duenioId);
    }

    public Mascota crear(Mascota mascota) {
        Duenio duenio = duenioService.buscarPorId(mascota.getDuenio().getId());
        mascota.setDuenio(duenio);
        return mascotaRepository.save(mascota);
    }

    public Mascota actualizar(Long id, Mascota datos) {
        Mascota existente = buscarPorId(id);
        Duenio duenio = duenioService.buscarPorId(datos.getDuenio().getId());
        existente.setNombre(datos.getNombre());
        existente.setEspecie(datos.getEspecie());
        existente.setRaza(datos.getRaza());
        existente.setFechaNacimiento(datos.getFechaNacimiento());
        existente.setDuenio(duenio);
        return mascotaRepository.save(existente);
    }

    public void eliminar(Long id) {
        Mascota existente = buscarPorId(id);
        mascotaRepository.delete(existente);
    }
}
