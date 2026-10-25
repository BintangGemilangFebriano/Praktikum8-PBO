package com.mycompany.sisteminventarisgudangminimarket;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BarangMakanan extends Barang
        implements BisaCekKadaluwarsa, BisaDiskon {

    private LocalDate tanggalKadaluwarsa;

    public BarangMakanan(String kode, String nama, int stok,
                         double harga, LocalDate tanggalKedaluwarsa) {
        super(kode, nama, stok, harga);
        this.tanggalKadaluwarsa = tanggalKedaluwarsa;
    }

    @Override
    public String getKategori() {
        return "Makanan";
    }

    @Override
    public String getInfoTambahan() {
        return "Tanggal kadaluwarsa: " + tanggalKadaluwarsa;
    }

    @Override
    public void cekKedaluwarsa() {
        LocalDate hariIni = LocalDate.now();

        if (tanggalKadaluwarsa.isBefore(hariIni)) {
            System.out.println(nama
                    + " sudah kedaluwarsa sejak "
                    + tanggalKadaluwarsa + ".");
        } else if (tanggalKadaluwarsa.isEqual(hariIni)) {
            System.out.println(nama + " kedaluwarsa hari ini.");
        } else {
            long sisaHari = ChronoUnit.DAYS.between(
                    hariIni, tanggalKadaluwarsa);

            System.out.println(nama
                    + " belum kedaluwarsa. Sisa "
                    + sisaHari + " hari.");
        }
    }

    @Override
    public double hitungHargaSetelahDiskon(double persen) {
        if (persen < 0 || persen > 100) {
            throw new IllegalArgumentException(
                    "Diskon harus antara 0 sampai 100 persen.");
        }

        return harga - (harga * persen / 100.0);
    }

    public LocalDate getTanggalKedaluwarsa() {
        return tanggalKadaluwarsa;
    }
}