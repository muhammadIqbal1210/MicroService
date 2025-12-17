package com.iqbal.buku.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.Year; // Untuk tahun terbit

@Data
@Entity
public class Buku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID unik untuk Buku
    
    private String kodeBuku; // Kode unik atau ISBN buku
    private String judul; // Judul buku
    private String pengarang; // Nama pengarang
    private String penerbit; // Nama penerbit
    private Year tahunTerbit; // Tahun buku diterbitkan (menggunakan Year)
    private Integer jumlahStok;
    private Integer stokTersedia; // Jumlah stok yang tersedia
    private String kategori; // Contoh: Fiksi, Sains, Sejarah
}