package com.vetapp.repository;

import com.vetapp.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    List<Mascota> findByDuenioId(Long duenioId);
}
