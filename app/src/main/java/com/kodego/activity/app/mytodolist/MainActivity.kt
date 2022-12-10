package com.kodego.activity.app.mytodolist

//import com.kodego.activity.app.mytodolist.db.deleteAllTodoList

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodego.activity.app.mytodolist.databinding.ActivityMainBinding
import com.kodego.activity.app.mytodolist.databinding.InfoDialogueBoxBinding
import com.kodego.activity.app.mytodolist.databinding.UpdateDialogueBoxBinding
import com.kodego.activity.app.mytodolist.db.ToDoList
import com.kodego.activity.app.mytodolist.db.ToDoListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var toDoListDB: ToDoListDatabase
    lateinit var adapter: ToDoListAdapter
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var date: String
    lateinit var textView: TextView
    lateinit var calendar: Calendar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toDoListDB = ToDoListDatabase.invoke(this)
        //display table data on screen
        view()

        binding.btnAdd.setOnClickListener() {
            var toDoItem = binding.etItem.text.toString()

            var toDoList = ToDoList(toDoItem)
            save(toDoList)
            adapter.toDoListModel.add(toDoList)
            adapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "New Item Added!", Toast.LENGTH_SHORT).show()
        }
//        binding.btnRefresh.setOnClickListener() {
//            view()
//        }
        binding.btnReset.setOnClickListener() {
            var toDoItem = binding.btnReset.text.toString()

            var toDoList = ToDoList(toDoItem)
            deleteAllToDoList(toDoItem)
            adapter.toDoListModel.remove(toDoList)
            adapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "ALL ITEMS DELETED", Toast.LENGTH_SHORT).show()
        }
        binding.btnInfo.setOnClickListener() {
            val infoDialogue = Dialog(this)
            val binding: InfoDialogueBoxBinding = InfoDialogueBoxBinding.inflate(layoutInflater)
            infoDialogue.setContentView(binding.root)
            infoDialogue.show()

            Toast.makeText(applicationContext, "SHOWING APP INFO", Toast.LENGTH_SHORT).show()

        }
        textView = binding.tvCurrentDate
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("EEE | MMM dd, yyyy")
        date = simpleDateFormat.format(calendar.time)
        textView.text = date


//        binding.checkBox.setOnClickListener() {
//            var chkbox = binding.checkBox.text.toString()
//
//            if (binding.checkBox.ischecked) {
//                Toast.makeText(applicationContext, "DONE", Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(applicationContext, "Don't Forget", Toast.LENGTH_SHORT).show()
//
//            }
//        }


    }

    private fun view() {
        lateinit var toDoList: MutableList<ToDoList>
        GlobalScope.launch(Dispatchers.IO) {
            toDoList = toDoListDB.getTodoList().getAllTodoList()

            withContext(Dispatchers.Main) {
                adapter = ToDoListAdapter(toDoList)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

                adapter.onItemDelete = { item: ToDoList, position: Int ->

                    delete(item)
                    adapter.toDoListModel.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                adapter.onUpdate = { item: ToDoList, position: Int ->

                    showUpdateDialog(item.itemNumber)
                    adapter.notifyDataSetChanged()
//                Toast.makeText(applicationContext,toDoList.toString(),Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private fun save(toDoList: ToDoList) {
        GlobalScope.launch(Dispatchers.IO) {
            toDoListDB.getTodoList().addTodoList(toDoList)
        }
    }

    private fun delete(toDoList: ToDoList) {
        GlobalScope.launch(Dispatchers.IO) {
            toDoListDB.getTodoList().deleteToDoList(toDoList.itemNumber)
            view()
        }
    }

    private fun taskIsDone(itemNumber: Int){
        var isTaskDone: Boolean = true
        GlobalScope.launch(Dispatchers.IO) {
            toDoListDB.getTodoList().taskIsDone(itemNumber, isTaskDone)
            view()
        }
    }
    private fun deleteAllToDoList(toDoItem: String) {
        GlobalScope.launch(Dispatchers.IO) {
            toDoListDB.getTodoList().deleteAllToDoList()
            view()
        }
    }

    private fun showUpdateDialog(itemNumber: Int) {
        val dialog = Dialog(this)
        val binding: UpdateDialogueBoxBinding = UpdateDialogueBoxBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.show()

        binding.btnOK.setOnClickListener() {
            var newName: String = binding.etNewItem.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
                toDoListDB.getTodoList().updatedToDoList(newName, itemNumber)
                view()
            }
            dialog.dismiss()
        }
    }

//    private fun showInfoDialog(currentDateFormat: SimpleDateFormat) {
//        val dialog = Dialog(this)
//        val binding: InfoDialogueBoxBinding = InfoDialogueBoxBinding.inflate(layoutInflater)
////        var infoButton: String = binding.btnInfo.text.toString()
//
//        dialog.setContentView(binding.root)
//        dialog.show()

//    }
}
