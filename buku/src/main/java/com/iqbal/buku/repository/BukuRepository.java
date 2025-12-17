package com.iqbal.buku.repository;

import com.iqbal.buku.model.Buku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository untuk operasi database pada entity Buku.
 */
@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {
    
    /**
     * Mencari buku berdasarkan judul (menggunakan LIKE atau mengandung)
     */
    List<Buku> findByJudulContainingIgnoreCase(String judul);

    /**
     * Mencari buku berdasarkan kode buku/ISBN.
     */
    Optional<Buku> findByKodeBuku(String kodeBuku);
}