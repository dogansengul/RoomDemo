package com.example.roomdemo.data

import android.app.Application

//bu class a ihtiyacımız var çünkü EmployeeDatabase class ı içerisinde synchronized() metodu
// içerisine bir context argümanı istiyor bu context i uygulamanın context i yapmak için uygulamayı
//yani Application sınıfını inherit eden bir sınıf oluşturup manifest dosyamızda application kısmında
//uygulama ismini oluşturduğumuz class yapmamız gerekiyor.

class EmployeeApp: Application() {
    val db by lazy {
        EmployeeDatabase.getDatabase(context = this)
    }
}