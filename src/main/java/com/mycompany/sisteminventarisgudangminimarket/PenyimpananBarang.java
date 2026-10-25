package com.mycompany.sisteminventarisgudangminimarket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class PenyimpananBarang {

    private static final Path FILE_DATA =
            Path.of("data_barang.txt");

    private PenyimpananBarang() {
    }

    public static List<Barang> muatData() {
        List<Barang> daftar = new ArrayList<>();

        if (!Files.exists(FILE_DATA)) {
            return daftar;
        }

        try {
            List<String> barisData = Files.readAllLines(
                    FILE_DATA, StandardCharsets.UTF_8);

            for (String baris : barisData) {
                if (baris.isBlank()) {
                    continue;
                }

                try {
                    String[] kolom = baris.split("\\|", -1);

                    if (kolom[0].equals("Makanan")
                            && kolom.length == 6) {

                        daftar.add(new BarangMakanan(
                                kolom[1],
                                kolom[2],
                                Integer.parseInt(kolom[3]),
                                Double.parseDouble(kolom[4]),
                                LocalDate.parse(kolom[5])
                        ));

                    } else if (kolom[0].equals("Nonmakanan")
                            && kolom.length == 5) {

                        daftar.add(new BarangNonMakanan(
                                kolom[1],
                                kolom[2],
                                Integer.parseInt(kolom[3]),
                                Double.parseDouble(kolom[4])
                        ));
                    }

                } catch (RuntimeException e) {
                    System.out.println(
                            "Peringatan: satu baris data dilewati "
                            + "karena formatnya tidak valid.");
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Data belum dapat dibaca: " + e.getMessage());
        }

        return daftar;
    }

    public static void simpanData(List<Barang> daftar) {
        List<String> barisData = new ArrayList<>();

        for (Barang barang : daftar) {
            String namaAman = barang.getNama().replace("|", "/");
            String kodeAman = barang.getKode().replace("|", "/");

            if (barang instanceof BarangMakanan) {
                BarangMakanan makanan = (BarangMakanan) barang;

                barisData.add(
                        "Makanan|" + kodeAman + "|"
                        + namaAman + "|"
                        + makanan.getStok() + "|"
                        + makanan.getHarga() + "|"
                        + makanan.getTanggalKedaluwarsa()
                );

            } else {
                barisData.add(
                        "Nonmakanan|" + kodeAman + "|"
                        + namaAman + "|"
                        + barang.getStok() + "|"
                        + barang.getHarga()
                );
            }
        }

        try {
            Files.write(
                    FILE_DATA,
                    barisData,
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            System.out.println(
                    "Data belum dapat disimpan: " + e.getMessage());
        }
    }
}