package com.mycompany.sisteminventarisgudangminimarket;

public abstract class Barang {

    protected String kode;
    protected String nama;
    protected int stok;
    protected double harga;

    public Barang(String kode, String nama, int stok, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
    }

    public abstract String getKategori();

    public abstract String getInfoTambahan();

    public void tampilkanDetail() {
        System.out.println("Kode       : " + kode);
        System.out.println("Nama       : " + nama);
        System.out.println("Kategori   : " + getKategori());
        System.out.println("Stok       : " + stok);
        System.out.printf("Harga      : Rp%.2f%n", harga);
        System.out.printf("Nilai stok : Rp%.2f%n",
                hitungNilaiPersediaan());
        System.out.println("Info       : " + getInfoTambahan());
    }

    public double hitungNilaiPersediaan() {
        return stok * harga;
    }

    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException(
                    "Jumlah stok harus lebih dari 0.");
        }

        stok += jumlah;
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException(
                    "Jumlah stok harus lebih dari 0.");
        }

        if (jumlah > stok) {
            throw new IllegalArgumentException(
                    "Stok tidak mencukupi.");
        }

        stok -= jumlah;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getStok() {
        return stok;
    }

    public double getHarga() {
        return harga;
    }
}