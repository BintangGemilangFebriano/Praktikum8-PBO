package com.mycompany.sisteminventarisgudangminimarket;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private static final List<Barang> daftarBarang =
            new ArrayList<>(PenyimpananBarang.muatData());

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" SISTEM INVENTARIS GUDANG MINIMARKET");
        System.out.println("========================================");

        boolean berjalan = true;

        while (berjalan) {
            tampilkanMenu();

            switch (bacaInt("Pilih menu: ")) {
                case 1:
                    tambahBarang();
                    break;
                case 2:
                    tampilkanSemuaBarang();
                    break;
                case 3:
                    cariBarang();
                    break;
                case 4:
                    ubahStok(true);
                    break;
                case 5:
                    ubahStok(false);
                    break;
                case 6:
                    cekKedaluwarsa();
                    break;
                case 7:
                    simulasiDiskon();
                    break;
                case 8:
                    hapusBarang();
                    break;
                case 0:
                    PenyimpananBarang.simpanData(daftarBarang);
                    System.out.println(
                            "Data tersimpan. Program selesai.");
                    berjalan = false;
                    break;
                default:
                    System.out.println(
                            "Pilihan menu tidak tersedia.");
            }

            System.out.println();
        }

        input.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n--- MENU UTAMA ---");
        System.out.println("1. Tambah barang");
        System.out.println("2. Lihat semua barang");
        System.out.println("3. Cari barang berdasarkan kode");
        System.out.println("4. Barang masuk (tambah stok)");
        System.out.println("5. Barang keluar (kurangi stok)");
        System.out.println("6. Cek tanggal kedaluwarsa");
        System.out.println("7. Simulasi harga setelah diskon");
        System.out.println("8. Hapus barang");
        System.out.println("0. Simpan dan keluar");
    }

    private static void tambahBarang() {
        System.out.println("\n--- TAMBAH BARANG ---");

        String kode = bacaTeks("Kode barang: ");

        if (cariDenganKode(kode) != null) {
            System.out.println("Kode barang sudah digunakan.");
            return;
        }

        String nama = bacaTeks("Nama barang: ");
        int stok = bacaIntMinimal("Stok awal: ", 0);
        double harga = bacaDoubleMinimal(
                "Harga satuan: Rp", 0.01);

        System.out.println(
                "Jenis barang: 1. Makanan  2. Nonmakanan");

        int jenis = bacaPilihanJenis();

        if (jenis == 1) {
            LocalDate tanggal = bacaTanggal(
                    "Tanggal kedaluwarsa (YYYY-MM-DD): ");

            daftarBarang.add(new BarangMakanan(
                    kode, nama, stok, harga, tanggal));

        } else {
            daftarBarang.add(new BarangNonMakanan(
                    kode, nama, stok, harga));
        }

        simpanData();

        System.out.println("Barang berhasil ditambahkan.");
    }

    private static void tampilkanSemuaBarang() {
        System.out.println("\n--- DAFTAR INVENTARIS ---");

        if (daftarBarang.isEmpty()) {
            System.out.println("Belum ada data barang.");
            return;
        }

        for (int i = 0; i < daftarBarang.size(); i++) {
            System.out.println("\nBarang ke-" + (i + 1));

            // Polimorfisme: detail mengikuti jenis objek.
            daftarBarang.get(i).tampilkanDetail();
        }

        double totalNilai = 0;

        for (Barang barang : daftarBarang) {
            totalNilai += barang.hitungNilaiPersediaan();
        }

        System.out.printf(
                "\nJumlah jenis barang: %d%n",
                daftarBarang.size());

        System.out.printf(
                "Total nilai persediaan: Rp%.2f%n",
                totalNilai);
    }

    private static void cariBarang() {
        String kode = bacaTeks(
                "Masukkan kode barang yang dicari: ");

        Barang barang = cariDenganKode(kode);

        if (barang == null) {
            System.out.println("Barang tidak ditemukan.");
        } else {
            barang.tampilkanDetail();
        }
    }

    private static void ubahStok(boolean barangMasuk) {
        String kode = bacaTeks("Kode barang: ");

        Barang barang = cariDenganKode(kode);

        if (barang == null) {
            System.out.println("Barang tidak ditemukan.");
            return;
        }

        String keterangan = barangMasuk
                ? "barang masuk" : "barang keluar";

        int jumlah = bacaIntMinimal(
                "Jumlah " + keterangan + ": ", 1);

        try {
            if (barangMasuk) {
                barang.tambahStok(jumlah);
            } else {
                barang.kurangiStok(jumlah);
            }

            simpanData();

            System.out.println(
                    "Stok berhasil diperbarui. Stok sekarang: "
                    + barang.getStok());

        } catch (IllegalArgumentException e) {
            System.out.println(
                    "Gagal memperbarui stok: " + e.getMessage());
        }
    }

    private static void cekKedaluwarsa() {
        String kode = bacaTeks("Kode barang makanan: ");

        Barang barang = cariDenganKode(kode);

        if (barang == null) {
            System.out.println("Barang tidak ditemukan.");

        } else if (barang instanceof BisaCekKadaluwarsa) {
            BisaCekKadaluwarsa dapatDicek =
                    (BisaCekKadaluwarsa) barang;

            dapatDicek.cekKedaluwarsa();

        } else {
            System.out.println(
                    "Barang ini tidak memiliki fitur "
                    + "cek kedaluwarsa makanan.");
        }
    }

    private static void simulasiDiskon() {
        String kode = bacaTeks("Kode barang: ");

        Barang barang = cariDenganKode(kode);

        if (barang == null) {
            System.out.println("Barang tidak ditemukan.");
            return;
        }

        double persen = bacaDoubleRentang(
                "Persentase diskon (0-100): ", 0, 100);

        if (barang instanceof BisaDiskon) {
            BisaDiskon dapatDiskon = (BisaDiskon) barang;

            double hargaAkhir =
                    dapatDiskon.hitungHargaSetelahDiskon(persen);

            System.out.printf(
                    "Harga awal : Rp%.2f%n",
                    barang.getHarga());

            System.out.printf(
                    "Harga diskon: Rp%.2f%n",
                    hargaAkhir);

        } else {
            System.out.println(
                    "Barang ini tidak mendukung "
                    + "perhitungan diskon.");
        }
    }

    private static void hapusBarang() {
        String kode = bacaTeks(
                "Kode barang yang ingin dihapus: ");

        Barang barang = cariDenganKode(kode);

        if (barang == null) {
            System.out.println("Barang tidak ditemukan.");
            return;
        }

        daftarBarang.remove(barang);
        simpanData();

        System.out.println("Barang berhasil dihapus.");
    }

    private static Barang cariDenganKode(String kode) {
        for (Barang barang : daftarBarang) {
            if (barang.getKode().equalsIgnoreCase(kode)) {
                return barang;
            }
        }

        return null;
    }

    private static void simpanData() {
        PenyimpananBarang.simpanData(daftarBarang);
    }

    private static String bacaTeks(String pesan) {
        while (true) {
            System.out.print(pesan);

            String teks = input.nextLine().trim();

            if (!teks.isEmpty()) {
                return teks;
            }

            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);

            try {
                return Integer.parseInt(
                        input.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println(
                        "Masukkan angka bulat yang valid.");
            }
        }
    }

    private static int bacaIntMinimal(
            String pesan, int minimal) {

        while (true) {
            int angka = bacaInt(pesan);

            if (angka >= minimal) {
                return angka;
            }

            System.out.println(
                    "Nilai minimal adalah " + minimal + ".");
        }
    }

    private static double bacaDoubleMinimal(
            String pesan, double minimal) {

        while (true) {
            System.out.print(pesan);

            try {
                double angka = Double.parseDouble(
                        input.nextLine().trim().replace(',', '.'));

                if (Double.isFinite(angka) && angka >= minimal) {
                    return angka;
                }

                System.out.println(
                        "Nilai minimal adalah " + minimal + ".");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Masukkan angka yang valid.");
            }
        }
    }

    private static double bacaDoubleRentang(
            String pesan, double minimal, double maksimal) {

        while (true) {
            double angka = bacaDoubleMinimal(pesan, minimal);

            if (angka <= maksimal) {
                return angka;
            }

            System.out.println(
                    "Nilai maksimal adalah " + maksimal + ".");
        }
    }

    private static LocalDate bacaTanggal(String pesan) {
        while (true) {
            System.out.print(pesan);

            try {
                return LocalDate.parse(
                        input.nextLine().trim());

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Format tanggal salah. "
                        + "Contoh: 2027-03-25.");
            }
        }
    }

    private static int bacaPilihanJenis() {
        while (true) {
            int jenis = bacaInt("Pilih jenis: ");

            if (jenis == 1 || jenis == 2) {
                return jenis;
            }

            System.out.println("Pilih 1 atau 2.");
        }
    }
}