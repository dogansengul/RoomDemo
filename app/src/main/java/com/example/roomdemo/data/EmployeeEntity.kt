package com.example.roomdemo.data

import androidx.room.*

@Entity("employee-table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "primary-key") val id: Int = 0,
    val name: String = "",
    val email: String = ""
)
