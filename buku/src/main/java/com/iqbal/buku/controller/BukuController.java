package com.iqbal.buku.controller;

import com.iqbal.buku.model.Buku;
import com.iqbal.buku.service.BukuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller untuk menangani permintaan REST API terkait Buku.
 */
@RestController
@RequestMapping("/api/buku")
public class BukuController {

    private final BukuService bukuService;

    @Autowired
    public BukuController(BukuService bukuService) {
        this.bukuService = bukuService;
    }

    /**
     * Endpoint untuk mendapatkan semua data buku.
     * GET /api/buku
     */
    @GetMapping
    public List<Buku> getAllBuku() {
        return bukuService.findAll();
    }

    /**
     * Endpoint untuk mendapatkan buku berdasarkan ID.
     * GET /api/buku/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBukuById(@PathVariable Long id) {
        return bukuService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint untuk membuat atau memperbarui buku.
     * POST /api/buku
     */
    @PostMapping
    public ResponseEntity<Buku> createOrUpdateBuku(@RequestBody Buku buku) {
        try {
            Buku savedBuku = bukuService.save(buku);
            return ResponseEntity.ok(savedBuku);
        } catch (IllegalArgumentException e) {
            // Mengembalikan status 400 Bad Request jika ada validasi yang gagal
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint untuk menghapus buku.
     * DELETE /api/buku/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuku(@PathVariable Long id) {
        if (bukuService.findById(id).isPresent()) {
            bukuService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // --- INTEGRASI STOK (Digunakan oleh Peminjaman/Pengembalian Service) ---

    /**
     * Endpoint yang dipanggil oleh Peminjaman Service untuk mengurangi stok.
     * POST /api/buku/{id}/kurang-stok
     */
    @PostMapping("/{id}/kurang-stok")
    public ResponseEntity<Buku> kurangiStok(@PathVariable Long id, @RequestParam int jumlah) {
        return bukuService.kurangiStok(id, jumlah)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(409).build()); // 409 Conflict/Stok Kurang
    }

    /**
     * Endpoint yang dipanggil oleh Pengembalian Service untuk menambah stok.
     * POST /api/buku/{id}/tambah-stok
     */
    @PostMapping("/{id}/tambah-stok")
    public ResponseEntity<Buku> tambahStok(@PathVariable Long id, @RequestParam int jumlah) {
        return bukuService.tambahStok(id, jumlah)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}