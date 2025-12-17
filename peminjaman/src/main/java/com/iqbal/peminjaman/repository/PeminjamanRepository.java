package com.iqbal.peminjaman.repository;

import com.iqbal.peminjaman.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository untuk operasi database pada entity Peminjaman.
 * Ekstensi JpaRepository menyediakan method CRUD dasar secara otomatis.
 */
@Repository
public interface PeminjamanRepository extends JpaRepository<Peminjaman, Long> {
    
    /**
     * Contoh custom query: Mencari semua peminjaman berdasarkan ID Anggota.
     * @param anggotaId ID anggota yang dicari.
     * @return Daftar transaksi Peminjaman.
     */
     // List<Peminjaman> findByAnggotaId(Long anggotaId);

    /**
     * Contoh custom query: Mencari semua peminjaman berdasarkan ID Buku.
     * @param bukuId ID buku yang dicari.
     * @return Daftar transaksi Peminjaman.
     */
     // List<Peminjaman> findByBukuId(Long bukuId);
}