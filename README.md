# mepro-crud-demo

## Deskripsi
**mepro-crud-demo** adalah proyek demo CRUD yang digunakan sebagai materi pembelajaran fundamental springboot di lingkungan perusahaan **Mepro**.  
Proyek ini menggunakan contoh kasus nyata yang diadaptasi dari proses bisnis internal, sehingga dapat menjadi acuan bagi pengembang baru maupun tim pengembang yang ingin memahami alur pengembangan aplikasi berbasis Spring Boot.

## Teknologi yang Digunakan
- **Java** (Spring Boot)
- **Thymeleaf** (Template Engine)
- **Adminlte** (Frontend Template)
- **Oracle Database**
- **Hibernate & JPA** (Object Relational Mapping)
- **Maven**

## Tujuan
- Menyediakan referensi CRUD sederhana namun lengkap untuk internal perusahaan.
- Menunjukkan implementasi koneksi database menggunakan Hibernate dan JPA.
- Memberikan gambaran alur kerja pengembangan aplikasi berbasis Spring Boot dengan Thymeleaf.

## Cara Menjalankan
1. **Clone / Download Repository**
   - Download & extract project, **atau**
   - Request pembuatan branch khusus melalui maintainer repository.

2. **Persiapkan Database**
   - Pastikan Oracle Database sudah berjalan.
   - Konfigurasi koneksi database di `application.properties` atau `application.yml`.

3. **Build & Run**
   ```bash
   mvn spring-boot:run

atau build terlebih dahulu:
mvn clean install

untuk menjalankannya, 
java -jar target/mepro-crud-demo-0.0.1-SNAPSHOT.jar
http://localhost:8080/appDemo

mepro-crud-demo/
- ├── src/main/java/...   # Source code utama
- ├── src/main/resources/ # File konfigurasi & template
- ├── pom.xml             # Konfigurasi Maven
- └── README.md           # Dokumentasi proyek

 ## Catatan
 1. **Repository ini bersifat PRIVATE dan hanya digunakan untuk keperluan internal perusahaan Mepro.**
 2. Konten dan source code di dalamnya tidak diperbolehkan untuk dibagikan ke pihak luar tanpa izin.

## Penyusun
**Yohanes Andy**
