package com.kodego.activity.app.mytodolist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoListDao {

    @Insert
    fun addTodoList(todoList: ToDoList)

    @Query("SELECT * FROM ToDoList")
    fun getAllTodoList():MutableList<ToDoList>

    @Query("UPDATE ToDoList SET toDoItem = :toDoItem WHERE itemNumber = :itemNumber")
    fun updatedToDoList(toDoItem:String,itemNumber:Int)

    @Query("DELETE FROM ToDoList WHERE itemNumber = :itemNumber")
    fun deleteToDoList(itemNumber:Int)

    @Query("DELETE FROM ToDoList")
    fun deleteAllToDoList()

    @Query("UPDATE ToDoList SET isTaskDone = :isTaskDone WHERE itemNumber = :itemNumber")
    fun taskIsDone(itemNumber: Int, isTaskDone: Boolean)
}