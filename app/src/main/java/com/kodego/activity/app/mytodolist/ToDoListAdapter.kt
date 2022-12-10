package com.kodego.activity.app.mytodolist

import android.graphics.ColorSpace.Model
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kodego.activity.app.mytodolist.databinding.RowItemBinding
import com.kodego.activity.app.mytodolist.db.ToDoList

class ToDoListAdapter(var toDoListModel: MutableList<ToDoList>): RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder>() {

        var onItemDelete : ((ToDoList, Int) -> Unit) ? = null
        var onUpdate : ((ToDoList, Int) -> Unit) ? = null
        var onBoxIsChecked : ((ToDoList, Boolean) -> Unit) ? = null

        inner class ToDoListViewHolder(var binding: RowItemBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RowItemBinding.inflate(layoutInflater, parent, false)
            return ToDoListViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {

                holder.binding.apply {
                    try {
                        tvItemEntry.text = toDoListModel[position].toDoItem
                        if (toDoListModel[position].isTaskDone) {
                            checkBox.isChecked = toDoListModel[position].isTaskDone
                            checkBox.isEnabled = false
//                            tvItemEntry.paintFlags
                            tvItemEntry.setPaintFlags(tvItemEntry.paintFlags)
                            holder.itemView.isEnabled = false
                        }
                            btnDelete.setOnClickListener() {
                                onItemDelete?.invoke(toDoListModel[position], position)
                        }
                        checkBox.setOnClickListener() {
                            onBoxIsChecked?.invoke(toDoListModel[position],position)
                        }
                        btnEdit.setOnClickListener() {
                            onUpdate?.invoke(toDoListModel[position], position)
                        }
                } catch(e:Exception){
                    println("Please enter a valid item.")
                }

            }
        }

        override fun getItemCount(): Int {
            return toDoListModel.size
        }

}

private fun Any?.invoke(toDoList: ToDoList, position: Int) {

}
