package com.abdhu.pemakaian_service.service;

import com.abdhu.pemakaian_service.model.Pemakaian;
import com.abdhu.pemakaian_service.repository.PemakaianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PemakaianService {

    @Autowired
    private PemakaianRepository pemakaianRepository;

    public List<Pemakaian> getAllPemakaian() {
        return pemakaianRepository.findAll();
    }

    public Optional<Pemakaian> getPemakaianById(Long id) {
        return pemakaianRepository.findById(id);
    }

    public Pemakaian savePemakaian(Pemakaian pemakaian) {
        // Pastikan id tidak diisi dari client
        pemakaian.setId(null);
        // Hitung pemakaian dan total sebelum simpan
        if (pemakaian.getMeterBulanIni() != null && pemakaian.getMeterBulanLalu() != null) {
            pemakaian.calculatePemakaian();
        }
        if (pemakaian.getPemakaian() != null && pemakaian.getTarifPermeter() != null) {
            pemakaian.calculateTotal();
        }
        return pemakaianRepository.save(pemakaian);
    }

    public void deletePemakaian(Long id) {
        pemakaianRepository.deleteById(id);
    }

    public Pemakaian updatePemakaian(Long id, Pemakaian pemakaianDetails) {
        Pemakaian pemakaian = pemakaianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pemakaian tidak ditemukan: " + id));

        // pemakaian.setKdTransaksi(kdTransaksi);
        pemakaian.setPelanggan(pemakaianDetails.getPelanggan());
        pemakaian.setMeterBulanIni(pemakaianDetails.getMeterBulanIni());
        pemakaian.setMeterBulanLalu(pemakaianDetails.getMeterBulanLalu());
        pemakaian.setTarifPermeter(pemakaianDetails.getTarifPermeter());

        return pemakaianRepository.save(pemakaian);
    }
}