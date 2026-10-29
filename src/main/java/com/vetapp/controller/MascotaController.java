package com.vetapp.controller;

import com.vetapp.model.Mascota;
import com.vetapp.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    @Autowired
    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> listar() {
        return mascotaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Mascota obtener(@PathVariable Long id) {
        return mascotaService.buscarPorId(id);
    }

    @GetMapping("/duenio/{duenioId}")
    public List<Mascota> listarPorDuenio(@PathVariable Long duenioId) {
        return mascotaService.listarPorDuenio(duenioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mascota crear(@Valid @RequestBody Mascota mascota) {
        return mascotaService.crear(mascota);
    }

    @PutMapping("/{id}")
    public Mascota actualizar(@PathVariable Long id, @Valid @RequestBody Mascota mascota) {
        return mascotaService.actualizar(id, mascota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
