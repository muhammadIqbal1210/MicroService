package com.iqbal.pengembalian_service.vo;

public class Anggota {
    private Long id;
    private String kode;
    private String nama; 
    private String alamat;

    public Anggota(Long id, String nama, String alamat, String kode) {
        this.id = id;
        this.kode = kode;
        this.nama = nama; 
        this.alamat = alamat;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKode(String kode) {   
        this.kode = kode;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        
        return super.toString();
    }

    public Long getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getKode() {   // ✅ getter email
        return kode;
    }

    public String getAlamat() {
        return alamat;
    }

}