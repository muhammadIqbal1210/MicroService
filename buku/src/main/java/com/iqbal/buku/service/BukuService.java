package com.iqbal.buku.service;

import com.iqbal.buku.model.Buku;
import com.iqbal.buku.repository.BukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Layer untuk menangani logika bisnis terkait Buku dan manajemen stok.
 */
@Service
public class BukuService {

    private final BukuRepository bukuRepository;

    @Autowired
    public BukuService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    /**
     * Mendapatkan semua buku.
     */
    public List<Buku> findAll() {
        return bukuRepository.findAll();
    }

    /**
     * Mendapatkan buku berdasarkan ID.
     */
    public Optional<Buku> findById(Long id) {
        return bukuRepository.findById(id);
    }

    /**
     * Menyimpan (membuat atau memperbarui) data buku.
     * Mengatur stokTersedia sama dengan jumlahStok saat buku baru dibuat.
     */
    public Buku save(Buku buku) {
        if (buku.getId() == null) {
            // Jika ini buku baru, atur stokTersedia sama dengan jumlahStok
            // (Asumsi getter/setter ini ada di Buku.java berkat Lombok @Data)
            buku.setStokTersedia(buku.getJumlahStok());
        }
        
        // Logika validasi lainnya
        if (buku.getJumlahStok() == null || buku.getJumlahStok() < 0) {
            throw new IllegalArgumentException("Jumlah stok (jumlahStok) harus diisi dan tidak boleh negatif.");
        }

        // Pastikan stokTersedia tidak pernah lebih besar dari jumlahStok
        if (buku.getStokTersedia() > buku.getJumlahStok()) {
            buku.setStokTersedia(buku.getJumlahStok());
        }
        
        if (buku.getStokTersedia() < 0) {
             throw new IllegalArgumentException("Stok tersedia (stokTersedia) tidak boleh negatif.");
        }

        return bukuRepository.save(buku);
    }
    
    /**
     * Mengurangi stok tersedia setelah terjadi peminjaman.
     * @param bukuId ID buku yang dipinjam.
     * @param jumlah Jumlah buku yang dipinjam.
     * @return Buku yang telah diperbarui atau Optional.empty() jika stok kurang.
     */
    public Optional<Buku> kurangiStok(Long bukuId, int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah yang dikurangi harus lebih dari nol.");
        }
        
        Optional<Buku> optionalBuku = bukuRepository.findById(bukuId);
        
        if (optionalBuku.isPresent()) {
            Buku buku = optionalBuku.get();
            if (buku.getStokTersedia() >= jumlah) {
                buku.setStokTersedia(buku.getStokTersedia() - jumlah);
                return Optional.of(bukuRepository.save(buku));
            } else {
                // Stok kurang, bisa dilempar exception atau mengembalikan empty
                return Optional.empty(); 
            }
        }
        return Optional.empty();
    }

    /**
     * Menambah stok tersedia setelah terjadi pengembalian.
     */
    public Optional<Buku> tambahStok(Long bukuId, int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah yang ditambahkan harus lebih dari nol.");
        }
        
        Optional<Buku> optionalBuku = bukuRepository.findById(bukuId);
        
        if (optionalBuku.isPresent()) {
            Buku buku = optionalBuku.get();
            buku.setStokTersedia(buku.getStokTersedia() + jumlah);
            
            // Jangan sampai stok tersedia melebihi jumlah stok fisik (kecuali ada penambahan fisik)
            if (buku.getStokTersedia() > buku.getJumlahStok()) {
                buku.setStokTersedia(buku.getJumlahStok());
            }
            return Optional.of(bukuRepository.save(buku));
        }
        return Optional.empty();
    }

    /**
     * Menghapus buku.
     */
    public void deleteById(Long id) {
        bukuRepository.deleteById(id);
    }
}