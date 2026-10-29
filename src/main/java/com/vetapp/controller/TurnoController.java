package com.vetapp.controller;

import com.vetapp.model.Turno;
import com.vetapp.service.TurnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    @Autowired
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public List<Turno> listar() {
        return turnoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Turno obtener(@PathVariable Long id) {
        return turnoService.buscarPorId(id);
    }

    @GetMapping("/mascota/{mascotaId}")
    public List<Turno> listarPorMascota(@PathVariable Long mascotaId) {
        return turnoService.listarPorMascota(mascotaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turno crear(@Valid @RequestBody Turno turno) {
        return turnoService.crear(turno);
    }

    @PutMapping("/{id}")
    public Turno actualizar(@PathVariable Long id, @Valid @RequestBody Turno turno) {
        return turnoService.actualizar(id, turno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
