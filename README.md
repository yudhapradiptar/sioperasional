# PAYFAZZ POS PRODUCT OPERATION INFORMATION SYSTEM

### Prerequisites

```
Git
Java JDK 11
Postgresql
Pgadmin (Opsional)
```

### Sebelum Mulai

1. Accept dulu invitation collab di github
2. git clone https://github.com/yudhapradiptar/sioperasional.git
3. untuk tahap development kita pake mysql ntar pas deploy baru pake postgresql
4. git checkout -b [usecase-nomor-nama] (git checkout -b usecase-1-yudha)
5. Mulai deh

### Sebelum setiap mulai ngerjain

1. cek udah di branch yang bener belom (git branch)
2. kalo belum (git checkout [nama branch])

### Setelah setiap ngerjain

1. git add .
2. git commit -m "feat: usecase [nomor usecase]" (kalo nambahin usecase baru)
3. git commit -m "fix: [nama file]" (kalo ngebenerin suatu file)
4. git push origin [nama branch]


### Cara akses tables pake pgadmin

1. Buka pgadmin
2. di Sidebar klik kanan postgresql, plih create database
3. namain "sioperasional"
4. Run
5. Refresh pgadmin

### Cara akses table pake cmd

1. Buka cmd
2. ketik "psql -U postgres" (U nya kapital)
3. Run kodingan
4. ketik "\c sioperasional"
4. cek pake "\d"


## Selamat Ngoding :) 
