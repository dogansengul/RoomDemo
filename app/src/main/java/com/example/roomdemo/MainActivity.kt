package com.example.roomdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.data.EmployeeApp
import com.example.roomdemo.data.EmployeeDao
import com.example.roomdemo.data.EmployeeEntity
import com.example.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val employeeDao = (application as EmployeeApp).db?.employeeDao()
        binding?.btnAdd?.setOnClickListener {
            addRecord(employeeDao)
        }
        lifecycleScope.launch {
            employeeDao?.getAllEmployees()?.collect{
                val list = ArrayList(it)
                setDataToRecyclerView(list, employeeDao)
            }
        }
    }

    private fun addRecord(dao: EmployeeDao?) {
        val name = binding?.etName?.text.toString()
        val email = binding?.etEmailId?.text.toString()
        if(name.isNotEmpty() && email.isNotEmpty()) {
            lifecycleScope.launch {
                dao?.insert(EmployeeEntity(name = name, email = email))
                Toast.makeText(this@MainActivity, "Data recorded.", Toast.LENGTH_SHORT).show()
                binding?.etName?.text?.clear()
                binding?.etEmailId?.text?.clear()
            }
        } else {
            Toast.makeText(this@MainActivity, "Name or password is empty.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDataToRecyclerView(employeeList: ArrayList<EmployeeEntity>, employeeDao: EmployeeDao) {
        if (employeeList.isNotEmpty()) {
            val itemAdapter = ItemAdapter(employeeList)
            binding?.rvItemsList?.layoutManager = LinearLayoutManager(this@MainActivity)
            binding?.rvItemsList?.adapter = itemAdapter
            binding?.rvItemsList?.visibility = View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility = View.GONE
        } else {
            binding?.rvItemsList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}