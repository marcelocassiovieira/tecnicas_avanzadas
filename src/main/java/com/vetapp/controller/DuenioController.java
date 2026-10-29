package com.vetapp.controller;

import com.vetapp.model.Duenio;
import com.vetapp.service.DuenioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/duenios")
public class DuenioController {

    private final DuenioService duenioService;

    @Autowired
    public DuenioController(DuenioService duenioService) {
        this.duenioService = duenioService;
    }

    @GetMapping
    public List<Duenio> listar() {
        return duenioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Duenio obtener(@PathVariable Long id) {
        return duenioService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Duenio crear(@Valid @RequestBody Duenio duenio) {
        return duenioService.crear(duenio);
    }

    @PutMapping("/{id}")
    public Duenio actualizar(@PathVariable Long id, @Valid @RequestBody Duenio duenio) {
        return duenioService.actualizar(id, duenio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        duenioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
