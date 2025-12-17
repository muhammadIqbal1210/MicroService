package com.iqbal.peminjaman.controller;

import com.iqbal.peminjaman.model.Peminjaman;
import com.iqbal.peminjaman.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller untuk menangani permintaan REST API terkait Peminjaman.
 */
@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    /**
     * Endpoint untuk mendapatkan semua data peminjaman.
     * GET /api/peminjaman
     */
    @GetMapping
    public List<Peminjaman> getAllPeminjaman() {
        return peminjamanRepository.findAll();
    }

    /**
     * Endpoint untuk mendapatkan peminjaman berdasarkan ID.
     * GET /api/peminjaman/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Peminjaman> getPeminjamanById(@PathVariable Long id) {
        Optional<Peminjaman> peminjaman = peminjamanRepository.findById(id);
        
        if (peminjaman.isPresent()) {
            return ResponseEntity.ok(peminjaman.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint untuk membuat transaksi peminjaman baru.
     * POST /api/peminjaman
     */
    @PostMapping
    public Peminjaman createPeminjaman(@RequestBody Peminjaman peminjaman) {
        // Logika bisnis tambahan (misalnya, cek stok buku di Buku Service, 
        // cek status anggota di Anggota Service) harus dilakukan di Service Layer 
        // sebelum menyimpan ke repository. 
        // Untuk contoh ini, kita langsung menyimpan.
        
        // Atur status awal
        peminjaman.setStatusPeminjaman("DIPINJAM"); 
        
        return peminjamanRepository.save(peminjaman);
    }

    /**
     * Endpoint untuk menghapus transaksi peminjaman.
     * DELETE /api/peminjaman/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeminjaman(@PathVariable Long id) {
        if (peminjamanRepository.existsById(id)) {
            peminjamanRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}