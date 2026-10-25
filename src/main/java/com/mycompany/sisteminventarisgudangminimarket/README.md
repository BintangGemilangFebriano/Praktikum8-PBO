# Sistem Inventaris Gudang Minimarket

## Deskripsi Project

Sistem Inventaris Gudang Minimarket merupakan aplikasi berbasis Java yang dibuat untuk membantu pengelolaan persediaan barang pada gudang minimarket. Aplikasi ini dikembangkan sebagai project akhir praktikum Pemrograman Berorientasi Objek (PBO) dengan menerapkan konsep Abstract Class, Polymorphism, dan Interface sesuai materi Modul 8 Praktikum PBO.

Program ini menyediakan fitur pengelolaan data barang, pencatatan perubahan stok, pencarian barang, pengecekan tanggal kedaluwarsa barang makanan, serta simulasi perhitungan harga setelah diskon.

## Tujuan Project

1. Mengimplementasikan konsep Pemrograman Berorientasi Objek dalam studi kasus inventaris gudang minimarket.
2. Mempermudah pengelolaan data dan persediaan barang.
3. Menerapkan Abstract Class, Method Overriding, Polymorphism, dan Interface pada program Java.
4. Menggunakan penyimpanan file teks agar data inventaris tetap tersedia setelah program ditutup dan dijalankan kembali.

## Fitur Aplikasi

- Menambahkan data barang makanan dan nonmakanan.
- Menampilkan seluruh data barang.
- Mencari barang berdasarkan kode.
- Mencatat barang masuk dan menambah stok.
- Mencatat barang keluar dan mengurangi stok.
- Memeriksa tanggal kedaluwarsa barang makanan.
- Menghitung simulasi harga barang setelah diskon.
- Menghitung total nilai persediaan.
- Menghapus data barang.
- Menyimpan dan memuat data melalui file teks.


## Struktur Class

| Class / Interface         | Fungsi                                                         |
| ------------------------- | -------------------------------------------------------------- |
| `Main.java`               | Menjalankan program utama dan menampilkan menu inventaris.     |
| `Barang.java`             | Abstract class yang menjadi kerangka dasar semua jenis barang. |
| `BarangMakanan.java`      | Mengelola barang makanan dan tanggal kedaluwarsanya.           |
| `BarangNonMakanan.java`   | Mengelola barang nonmakanan.                                   |
| `BisaCekKedaluwarsa.java` | Interface untuk fitur pengecekan tanggal kedaluwarsa.          |
| `BisaDiskon.java`         | Interface untuk menghitung harga setelah diskon.               |
| `PenyimpananBarang.java`  | Mengelola proses penyimpanan dan pemuatan data barang.         |

## Implementasi Konsep PBO

### 1. Abstract Class

Abstract class diterapkan pada `Barang.java`. Class ini menyimpan atribut umum seperti kode, nama, stok, dan harga. Class ini juga memiliki method abstrak `getKategori()` dan `getInfoTambahan()` yang wajib diimplementasikan oleh class turunannya.

### 2. Polymorphism

Polymorphism diterapkan melalui method overriding pada class `BarangMakanan` dan `BarangNonMakanan`. Kedua class tersebut memiliki implementasi berbeda untuk method `getKategori()` dan `getInfoTambahan()`.

Dengan konsep ini, pemanggilan method yang sama dapat menghasilkan informasi sesuai jenis objek barang yang digunakan.

### 3. Interface

Interface diterapkan melalui `BisaCekKedaluwarsa.java` dan `BisaDiskon.java`.

Class `BarangMakanan` mengimplementasikan kedua interface tersebut, sedangkan `BarangNonMakanan` mengimplementasikan interface `BisaDiskon`. Penerapan ini menunjukkan bahwa satu class Java dapat mengimplementasikan lebih dari satu interface.

## Cara Menjalankan Program

1. Pastikan Java Development Kit (JDK) sudah terpasang.
2. Buka Apache NetBeans.
3. Pilih **File → Open Project**.
4. Pilih folder project `SistemInventarisGudangMinimarket`.
5. Buka package `com.mycompany.sisteminventarisgudangminimarket`.
6. Buka file `Main.java`.
7. Klik kanan pada `Main.java`, kemudian pilih **Run File**.
8. Gunakan menu yang tersedia untuk mengelola inventaris gudang minimarket.

## Penyimpanan Data

Data barang disimpan dalam file `data_barang.txt`. Program memuat data yang tersedia ketika dijalankan dan memperbarui file ketika data inventaris mengalami perubahan.

Penyimpanan pada versi ini menggunakan file teks lokal dan belum menggunakan database.

## Identitas Pengembang

- Nama: Bintang Gemilang Febriano
- NIM: L0325019
- Program Studi: Informatika
- Mata Kuliah: Pemrograman Berorientasi Objek (PBO)
- Project: Sistem Inventaris Gudang Minimarket
