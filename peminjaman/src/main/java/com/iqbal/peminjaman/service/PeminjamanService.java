package com.iqbal.peminjaman.service;

import com.iqbal.peminjaman.model.Peminjaman;
import com.iqbal.peminjaman.repository.PeminjamanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Layer untuk menangani logika bisnis terkait Peminjaman.
 */
@Service
public class PeminjamanService {

    private final PeminjamanRepository peminjamanRepository;

    // Injeksi dependensi (Dependency Injection) melalui constructor
    @Autowired
    public PeminjamanService(PeminjamanRepository peminjamanRepository) {
        this.peminjamanRepository = peminjamanRepository;
    }

    /**
     * Mendapatkan semua transaksi peminjaman.
     */
    public List<Peminjaman> findAll() {
        return peminjamanRepository.findAll();
    }

    /**
     * Mendapatkan transaksi peminjaman berdasarkan ID.
     */
    public Optional<Peminjaman> findById(Long id) {
        return peminjamanRepository.findById(id);
    }

    /**
     * Menyimpan (membuat atau memperbarui) transaksi peminjaman.
     * Di sinilah logika bisnis utama harus ditambahkan.
     */
    public Peminjaman save(Peminjaman peminjaman) {
        // --- LOGIKA BISNIS HARUS DITEMPATKAN DI SINI ---
        
        // 1. Validasi: Pastikan anggotaId dan bukuId tidak null sebelum disimpan.
        if (peminjaman.getAnggotaId() == null || peminjaman.getBukuId() == null) {
            throw new IllegalArgumentException("Anggota ID dan Buku ID harus diisi.");
        }
        
        // 2. Defaulting: Mengatur tanggal peminjaman jika belum diatur
        if (peminjaman.getTanggalPeminjaman() == null) {
            peminjaman.setTanggalPeminjaman(LocalDate.now());
        }

        // 3. Status Awal: Atur status jika ini adalah transaksi baru
        if (peminjaman.getId() == null) {
            peminjaman.setStatusPeminjaman("DIPINJAM");
            // Set tanggal batas kembali (misalnya, 7 hari dari tanggal pinjam)
            peminjaman.setTanggalBatasKembali(peminjaman.getTanggalPeminjaman().plusDays(7));
        }

        // 4. Integrasi SOA: Di masa depan, panggil Buku Service untuk 
        //    mengurangi stok dan Anggota Service untuk memverifikasi.
        
        // ----------------------------------------------------

        return peminjamanRepository.save(peminjaman);
    }

    /**
     * Menghapus transaksi peminjaman berdasarkan ID.
     */
    public void deleteById(Long id) {
        peminjamanRepository.deleteById(id);
    }
    
    /**
     * Mengecek apakah transaksi dengan ID tertentu ada.
     */
    public boolean existsById(Long id) {
        return peminjamanRepository.existsById(id);
    }
}