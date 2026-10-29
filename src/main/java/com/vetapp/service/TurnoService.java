package com.vetapp.service;

import com.vetapp.exception.RecursoNoEncontradoException;
import com.vetapp.model.Mascota;
import com.vetapp.model.Turno;
import com.vetapp.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final MascotaService mascotaService;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, MascotaService mascotaService) {
        this.turnoRepository = turnoRepository;
        this.mascotaService = mascotaService;
    }

    public List<Turno> listarTodos() {
        return turnoRepository.findAll();
    }

    public Turno buscarPorId(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado con id " + id));
    }

    public List<Turno> listarPorMascota(Long mascotaId) {
        mascotaService.buscarPorId(mascotaId);
        return turnoRepository.findByMascotaId(mascotaId);
    }

    public Turno crear(Turno turno) {
        Mascota mascota = mascotaService.buscarPorId(turno.getMascota().getId());
        turno.setMascota(mascota);
        return turnoRepository.save(turno);
    }

    public Turno actualizar(Long id, Turno datos) {
        Turno existente = buscarPorId(id);
        Mascota mascota = mascotaService.buscarPorId(datos.getMascota().getId());
        existente.setFechaHora(datos.getFechaHora());
        existente.setMotivo(datos.getMotivo());
        existente.setEstado(datos.getEstado());
        existente.setMascota(mascota);
        return turnoRepository.save(existente);
    }

    public void eliminar(Long id) {
        Turno existente = buscarPorId(id);
        turnoRepository.delete(existente);
    }
}
