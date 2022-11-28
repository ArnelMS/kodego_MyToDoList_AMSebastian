package com.kodego.activity.app.mytodolist

import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kodego.activity.app.mytodolist.databinding.RowItemBinding
import com.kodego.activity.app.mytodolist.db.ToDoList

class ToDoListAdapter(var toDoListModel: MutableList<ToDoList>): RecyclerView.Adapter<ToDoListAdapter.EmployeeViewHolder>() {

        var onItemDelete : ((ToDoList, Int) -> Unit) ? = null
        var onUpdate : ((ToDoList, Int) -> Unit) ? = null
        var onBoxIsChecked : ((ToDoList, Boolean) -> Unit) ? = null

        inner class EmployeeViewHolder(var binding: RowItemBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RowItemBinding.inflate(layoutInflater, parent, false)
            return EmployeeViewHolder(binding)
        }

        override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
            holder.binding.apply{
                tvItemEntry.text = toDoListModel[position].toDoItem
                btnDelete.setOnClickListener(){
                    onItemDelete?.invoke(toDoListModel[position],position)
                }
                btnEdit.setOnClickListener(){
                    onUpdate?.invoke(toDoListModel[position],position)
                }
                checkBox.setOnClickListener(){
                    onBoxIsChecked?.invoke(toDoListModel[position])
                }
            }
        }

        override fun getItemCount(): Int {
            return toDoListModel.size
        }
    }

private fun Any?.invoke(toDoList: ToDoList) {

}
