package com.example.roomdemo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Update
    suspend fun update(employeeEntity: EmployeeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    @Query("SELECT * FROM `employee-table` ORDER BY `primary-key` ASC")
    fun getAllEmployees(): Flow<List<EmployeeEntity>>

    @Query("SELECT * FROM `employee-table` WHERE `primary-key`=:id ORDER BY `primary-key` ASC ")
    fun getEmployee(id: Int): Flow<EmployeeEntity>

}