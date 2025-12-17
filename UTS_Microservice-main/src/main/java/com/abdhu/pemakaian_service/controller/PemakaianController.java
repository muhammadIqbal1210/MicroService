package com.abdhu.pemakaian_service.controller;

import com.abdhu.pemakaian_service.model.Pemakaian;
import com.abdhu.pemakaian_service.service.PemakaianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pemakaian")
public class PemakaianController {

    @Autowired
    private PemakaianService pemakaianService;

    @GetMapping
    public List<Pemakaian> getAllPemakaian() {
        return pemakaianService.getAllPemakaian();
    }

    @GetMapping("/{kdTransaksi}")
    public ResponseEntity<Pemakaian> getPemakaianById(@PathVariable Long id) {
        return pemakaianService.getPemakaianById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pemakaian createPemakaian(@RequestBody Pemakaian pemakaian) {
        return pemakaianService.savePemakaian(pemakaian);
    }

    @PutMapping("/{kdTransaksi}")
    public ResponseEntity<Pemakaian> updatePemakaian(@PathVariable Long id, @RequestBody Pemakaian pemakaianDetails) {
        try {
            Pemakaian updatedPemakaian = pemakaianService.updatePemakaian(id, pemakaianDetails);
            return ResponseEntity.ok(updatedPemakaian);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{kdTransaksi}")
    public ResponseEntity<Void> deletePemakaian(@PathVariable Long id) {
        try {
            pemakaianService.deletePemakaian(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}