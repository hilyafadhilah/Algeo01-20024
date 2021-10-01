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

- Pustaka berisi kode general untuk melakukan perhitungan yang berhubungan
  dengan matriks dan solusi SPL.

- Aplikasi berisi alur utama program yang berinteraksi dengan pengguna
  dengan memanfaatkan pustaka untuk melakukan perhitungan.

## Menjalankan Program

Versi Java minimal adalah Java 11.
Untuk menjalankan program, pindahlah ke direktori utama repository ini,
kemudian jalankan perintah berikut:

```
java -cp bin app.App
```

## Input File dan Testing

Program dapat menerima input file, sebagai contoh:

```
Masukkan ukuran matriks dalam bilangan bulat,
dengan format "[jumlah baris]<spasi>[jumlah kolom]"
Jika matriks dalam file, masukkan path file tersebut

matriks>
```

File yang dimasukkan dapat berupa absolute path maupun relative path.
Pada repository ini telah terdapat file untuk keperluan testing pada direktori `test`.
Karena itu jika program dijalankan pada direktori utama, maka cukup memasukkan,
sebagai contoh:

```
matriks> test/spl_1a.txt
```

File pada direktori `test` juga telah dinamai sesuai peruntukannya.

- `spl_*` merupakan input untuk pencarian solusi SPL.
- `det_*` merupakan input untuk pencarian determinan matriks.
- `inv_*` merupakan input untuk pencarian invers matriks.
- `intpl_*` merupakan input untuk interpolasi polinom.
- `reg_*` merupakan input untuk regresi linear berganda.

## License

[MIT](https://opensource.org/licenses/MIT)

Copyright (C) 2021, Pyxis Nautica Cabang IF
