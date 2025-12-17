package com.abdhu.pemakaian_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Entity
@Table(name = "pemakaian")
@NoArgsConstructor // Diperlukan oleh JPA
@Builder // MEMUNGKINKAN konstruksi objek tanpa ID, sangat direkomendasikan
public class Pemakaian {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String kdTransaksi;
    private String pelanggan;
    private Double meterBulanIni;
    private Double meterBulanLalu;
    private Double pemakaian;
    private Double tarifPermeter;
    private Double total;

    // --- Konstruktor Manual untuk Menggantikan AllArgsConstructor ---
    // Konstruktor ini TIDAK menyertakan 'id', sehingga record baru dapat dibuat
    // tanpa menetapkan ID, dan membiarkan @GeneratedValue menanganinya.
    public Pemakaian(String kdTransaksi, String pelanggan, Double meterBulanIni, Double meterBulanLalu, Double pemakaian, Double tarifPermeter, Double total) {
        this.kdTransaksi = kdTransaksi;
        this.pelanggan = pelanggan;
        this.meterBulanIni = meterBulanIni;
        this.meterBulanLalu = meterBulanLalu;
        this.pemakaian = pemakaian;
        this.tarifPermeter = tarifPermeter;
        this.total = total;
    }
    
    // Konstruktor dengan semua field (jika benar-benar diperlukan oleh Lombok @Builder)
    // Jika Anda menggunakan @Builder, ini akan di-generate secara otomatis, tetapi 
    // pastikan logika pembuatan objek baru Anda TIDAK MENGGUNAKAN ID.
    @Builder // Anotasi @Builder akan membuat konstruktor all-args *dan* logika builder.
    public Pemakaian(Long id, String kdTransaksi, String pelanggan, Double meterBulanIni, Double meterBulanLalu, Double pemakaian, Double tarifPermeter, Double total) {
        this.id = id;
        this.kdTransaksi = kdTransaksi;
        this.pelanggan = pelanggan;
        this.meterBulanIni = meterBulanIni;
        this.meterBulanLalu = meterBulanLalu;
        this.pemakaian = pemakaian;
        this.tarifPermeter = tarifPermeter;
        this.total = total;
    }
    
    // --- Metode Perhitungan ---

    public void calculatePemakaian() {
        // Mencegah NaN jika salah satu nilai null
        if (this.meterBulanIni != null && this.meterBulanLalu != null) {
            this.pemakaian = this.meterBulanIni - this.meterBulanLalu;
        } else {
            this.pemakaian = 0.0;
        }
    }

    public void calculateTotal() {
        // Mencegah NaN jika salah satu nilai null
        if (this.pemakaian != null && this.tarifPermeter != null) {
            this.total = this.pemakaian * this.tarifPermeter;
        } else {
            this.total = 0.0;
        }
    }

    // --- Setter yang Sudah Ada ---
    
    public void setMeterBulanIni(Double meterBulanIni) {
        this.meterBulanIni = meterBulanIni;
        if (this.meterBulanLalu != null) {
            calculatePemakaian();
            if (this.tarifPermeter != null) {
                calculateTotal();
            }
        }
    }

    public void setMeterBulanLalu(Double meterBulanLalu) {
        this.meterBulanLalu = meterBulanLalu;
        if (this.meterBulanIni != null) {
            calculatePemakaian();
            if (this.tarifPermeter != null) {
                calculateTotal();
            }
        }
    }

    public void setTarifPermeter(Double tarifPermeter) {
        this.tarifPermeter = tarifPermeter;
        // Pastikan pemakaian sudah terhitung sebelum menghitung total
        if (this.pemakaian != null) { 
            calculateTotal();
        }
    }
}