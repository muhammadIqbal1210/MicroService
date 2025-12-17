package com.iqbal.peminjaman.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Peminjaman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID unik untuk Transaksi Peminjaman

    // Referensi ke Anggota Service
    private Long anggotaId; // ID Anggota yang melakukan peminjaman

    // Referensi ke Buku Service
    private Long bukuId; // ID Buku yang dipinjam

    private LocalDate tanggalPeminjaman; // Tanggal buku dipinjam
    private LocalDate tanggalBatasKembali; // Tanggal buku harus dikembalikan
    private Integer jumlahBuku; // Jumlah buku yang dipinjam dalam transaksi ini (jika lebih dari 1)
    private String statusPeminjaman; // Contoh: DIPINJAM, TERTUNDA
}