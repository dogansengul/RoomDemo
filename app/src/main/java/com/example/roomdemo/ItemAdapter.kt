package com.example.roomdemo

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.data.EmployeeApp
import com.example.roomdemo.data.EmployeeEntity
import com.example.roomdemo.databinding.ItemRowBinding
import com.example.roomdemo.databinding.UpdateDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemAdapter(private val items: ArrayList<EmployeeEntity>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        val linearLayout = binding.llMain
        val tvName = binding.tvName
        val tvEmail = binding.tvEmail
        val ivEdit = binding.ivEdit
        val ivDelete = binding.ivDelete
        fun bind(entity: EmployeeEntity) {
            tvName.text = entity.name
            tvEmail.text = entity.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        val employeeDao = EmployeeApp().db?.employeeDao()
        holder.ivEdit.setOnClickListener {
            val updateDialog = Dialog(holder.itemView.context)
            val dialogBinding = UpdateDialogBinding.inflate(LayoutInflater.from(updateDialog.context))
            updateDialog.setContentView(dialogBinding.root)
            updateDialog.show()
            dialogBinding.updateButton.setOnClickListener{
                if(dialogBinding.name.text.toString().isNotEmpty() && dialogBinding.etEmail.text.toString().isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch{
                        val updatedItem = EmployeeEntity(id = item.id, dialogBinding.name.text.toString(), dialogBinding.etEmail.text.toString())
                        employeeDao?.update(updatedItem)
                        updateDialog.dismiss()
                    }
                }
            }
            dialogBinding.cancel.setOnClickListener {
                updateDialog.dismiss()
            }
        }

        holder.ivDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                employeeDao?.delete(item)
            }
        }

    }


}