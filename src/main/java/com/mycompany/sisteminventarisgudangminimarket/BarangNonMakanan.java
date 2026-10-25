package com.mycompany.sisteminventarisgudangminimarket;

public class BarangNonMakanan extends Barang
        implements BisaDiskon {

    public BarangNonMakanan(String kode, String nama,
                            int stok, double harga) {
        super(kode, nama, stok, harga);
    }

    @Override
    public String getKategori() {
        return "Nonmakanan";
    }

    @Override
    public String getInfoTambahan() {
        return "Tidak memerlukan tanggal kadaluwarsa makanan.";
    }

    @Override
    public double hitungHargaSetelahDiskon(double persen) {
        if (persen < 0 || persen > 100) {
            throw new IllegalArgumentException(
                    "Diskon harus antara 0 sampai 100 persen.");
        }

        return harga - (harga * persen / 100.0);
    }
}