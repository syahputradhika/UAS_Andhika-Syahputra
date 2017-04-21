/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Siak;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.List;
import java.util.Scanner;
import model.Mahasiswa;
import model.Matkul;
import service.ServiceJdbc;

/**
 *
 * @author dhika
 */
public class Siak {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("");
        dataSource.setDatabaseName("siak?serverTimezone=UTC");
        dataSource.setServerName("localhost");

        dataSource.setPortNumber(3306);

        ServiceJdbc service = new ServiceJdbc();
        service.setDataSource(dataSource);
        
        Scanner in = new Scanner(System.in);
        System.out.println("Selamat datang di Sistem Akademik");
        Boolean mainmenu = true;
        while (mainmenu) {
            System.out.println("\nMenu");
            System.out.println("\n1. Pengelolaan Mahasiswa");
            System.out.println("2. Pengelolaan Mata Kuliah");
            System.out.println("3. Input Nilai Mahasiswa");
            System.out.print("\nMasukkan pilihan : ");
            String pilihanutama = in.nextLine();
            switch(Integer.parseInt(pilihanutama)) {
                case 1:
                    getMenuMhs(service);
                    break;
                case 2:
                    getMenuMkl(service);
                    break;
                case 3:
                    getMenuNilai();
                    break;
                default:
                    break;
            }
            
        }
    }
    
    public static void getMenuMahasiswa(ServiceJdbc service) {
        Boolean mhsmenu = true;
        Scanner in = new Scanner(System.in);
        while (mhsmenu) {
            System.out.println("\nMenu Pengelolaan Mahasiswa");
            System.out.println("\n1. Lihat Daftar");
            System.out.println("2. Tambah Data");
            System.out.println("3. Ubah Data");
            System.out.println("4. Hapus Data");
            System.out.print("\nMasukkan pilihan : ");
            String pilihanmhs = in.nextLine();
            switch (Integer.parseInt(pilihanmhs)) {
                case 1:
                    List<Mahasiswa> mhsR = service.getAllMahasiswa();
                    if (mhsR.isEmpty()) {
                        System.out.println("\nBelum ada mahasiswa yang terdaftar!");
                        break;
                    }
                    System.out.println("NIM \t | Nama \t\t\t | Tanggal Lahir | Alamat \t\t\t | No. HP");
                    System.out.println("=============================================================================================");
                    mhsR.stream().forEach((mhs) -> {
                        System.out.println(mhs.getNim() + "\t | " + mhs.getNama() + " \t | " + mhs.getTgllahir() + " | "+ mhs.getAlamat() + "\t\t | " + mhs.getNohp());
            });
                    break;
                case 2:
                    System.out.print("NIM : ");
                    String npm = in.nextLine();
                    System.out.print("Nama : ");                    
                    String nama = in.nextLine();
                    System.out.print("Tanggal Lahir: ");                    
                    String tgllahir = in.nextLine();
                    System.out.print("Alamat : ");
                    String alamat = in.nextLine();
                    System.out.print("No. HP : ");
                    String nohp = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah = in.nextLine();
                    if (tambah.toLowerCase().equals("y")) {
                        Mahasiswa mhs = new Mahasiswa();
                        mhs.setNim(Integer.parseInt(npm));
                        mhs.setNama(nama);
                        mhs.setTgllahir(tgllahir);
                        mhs.setAlamat(alamat);
                        mhs.setNohp(nohp);
                        service.save(mhs);
                    }
                    break;
                case 3:
                    System.out.print("Masukkan NIM : ");
                    String nim_x = in.nextLine();
                    Mahasiswa mhs_x = service.getMahasiswa(Integer.parseInt(nim_x));
                    if (mhs_x == null) {
                        System.out.println("Tidak ditemukan mahasiswa dengan NIM " + nim_x);
                        break;
                    }
                    System.out.print("Nama : ");                    
                    String nama_x = in.nextLine();
                    System.out.print("Tanggal Lahir (YYYY-MM-DD) : ");                    
                    String tgllahir_x = in.nextLine();
                    System.out.print("Alamat : ");
                    String alamat_x = in.nextLine();
                    System.out.print("No. HP : ");
                    String nohp_x = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah_x = in.nextLine();
                    if (tambah_x.toLowerCase().equals("y")) {
                        mhs_x.setNim(Integer.parseInt(nim_x));
                        mhs_x.setNama(nama_x);
                        mhs_x.setTgllahir(tgllahir_x);
                        mhs_x.setAlamat(alamat_x);
                        mhs_x.setNohp(nohp_x);
                        service.update(mhs_x);
                    }
                    break;
                case 4:
                    System.out.print("Masukkan NIM : ");
                    String npm_d = in.nextLine();
                    Mahasiswa mhs_d = service.getMahasiswa(Integer.parseInt(npm_d));
                    if (mhs_d == null) {
                        System.out.println("Tidak ditemukan mahasiswa dengan NIM "+npm_d);
                        break;
                    }
                    System.out.print("Hapus? (Y/N) : ");
                    String hapus = in.nextLine();
                    if (hapus.toLowerCase().equals("y")) {
                        service.delete(mhs_d);
                    }
                    break;
                default:
                    break;
            }

        }
    }
    
    public static void getMenuMkl(ServiceJdbc service) {
        Scanner in = new Scanner(System.in);
        Boolean active = true;
        while (active) {
            System.out.println("\nMenu Pengelolaan Mata Kuliah : \n");
            System.out.println("1. Lihat Daftar Mata Kuliah");
            System.out.println("2. Tambah Data Mata Kuliah");
            System.out.println("3. Ubah Data Mata Kuliah");
            System.out.println("4. Hapus Data Mata Kuliah");
            System.out.println("\n0. Kembali ke Menu Utama");

            System.out.print("\nPilihan : ");
            String pilih = in.nextLine();
            switch (pilih) {
                case "1":
                    List<Matkul> matkulR = service.getAllMatkul();
                    if (matkulR.isEmpty()) {
                        System.out.println("\nBelum ada mata kuliah yang terdaftar. Silakan tambahkan mata kuliah terlebih dahulu!");
                    } else {
                        System.out.println("\nKode Mata Kuliah\t | Nama Mata Kuliah\t | Jumlah SKS");
                        System.out.println("--------------------------------------------------------------------------------------");
                        matkulR.stream().forEach((matkul) -> {
                            System.out.println(matkul.getKd_mk()+ "\t\t | " + matkul.getNama_mk()+ "\t| " + matkul.getSks());
                });
                    }
                    break;
                case "2":
                    System.out.print("Nama Mata Kuliah : ");
                    String nama_mk = in.nextLine();
                    System.out.print("Jumlah SKS : ");
                    String sks = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah = in.nextLine();
                    if (tambah.toLowerCase().equals("y")) {
                        Matkul buku = new Matkul();
                        buku.setNama_mk(nama_mk);
                        buku.setSks(Integer.parseInt(sks));
                        service.save(buku);
                    }
                    break;
                case "3":
                    System.out.print("Masukkan Kode Mata Kuliah : ");
                    String id_matkul_x = in.nextLine();
                    Matkul matkul_x = service.getMatkul(Integer.parseInt(id_matkul_x));
                    if (matkul_x == null) {
                        System.out.println("Tidak ditemukan mata kuliah dengan ID " + id_matkul_x);
                        break;
                    }
                    System.out.print("Nama Mata Kuliah : ");
                    String nama_mk_x = in.nextLine();
                    System.out.print("Jumlah SKS : ");
                    String sks_x = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String ubah_x = in.nextLine();
                    if (ubah_x.toLowerCase().equals("y")) {
                        matkul_x.setKd_mk(Integer.parseInt(id_matkul_x));
                        matkul_x.setNama_mk(nama_mk_x);
                        matkul_x.setSks(Integer.parseInt(sks_x));
                        service.save(matkul_x);
                    }
                    break;
                case "4":
                    System.out.print("Masukkan ID Mata Kuliah : ");
                    String id_matkul_d = in.nextLine();
                    Matkul matkul_d = service.getMatkul(Integer.parseInt(id_matkul_d));
                    if (matkul_d == null) {
                        System.out.println("Tidak ditemukan mata kuliah dengan ID " + id_matkul_d);
                        break;
                    }
                    System.out.print("hapus? (Y/N) : ");
                    String hapus = in.nextLine();
                    if (hapus.toLowerCase().equals("y")) {
                        service.delete(matkul_d);
                    }
                    break;
                case "0":
                    active = false;
                    break;
            }
        }
    }
    
    public static void getMenuNilai() {
        
    }

    private static void getMenuMhs(ServiceJdbc service) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void getMenuMkl(ServiceJdbc service) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
