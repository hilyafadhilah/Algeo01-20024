# Tugas Besar 1 IF2123 Aljabar Linier dan Geometri 2021/2022

Kelompok 07
- 13520024 Hilya Fadhilah Imania
- 13520106 Roby Purnomo
- 13520155 Jundan Haris

## Deskripsi Singkat

Program ini bertujuan untuk:
1. Menemukan solusi SPL dengan metode eliminasi Gauss, metode Eliminasi Gauss-Jordan,
   metode matriks balikan, dan kaidah Cramer
2. Menghitung determinan matriks dengan reduksi baris dan dengan ekspansi kofaktor
3. Menghitung balikan matriks dengan reduksi baris dan matriks kofaktor
4. Menghitung interpolasi polinom dengan matriks
5. Menghitung regresi linier berganda dengan matriks

## Struktur Kode

Program ini ditulis dalam bahasa Java. Kode program berada di direktori `src`.
Kode dibagi menjadi 2 yaitu pustaka `lib` dan aplikasi `app`. 
- Pustaka berisi aneka kebutuhan aplikasi, utamanya pustaka `matrix` untuk
  representasi dan operasi matriks.
- Aplikasi berisi alur utama program yang berinteraksi dengan pengguna
  dengan memanfaatkan pustaka.

## Menjalankan Program

Versi Java minimal adalah Java 11.
Untuk menjalankan program, pindahlah ke direktori utama repository ini,
kemudian jalankan perintah berikut:

```
java -cp bin app.App
```
