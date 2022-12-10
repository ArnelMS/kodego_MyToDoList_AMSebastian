package com.kodego.activity.app.mytodolist.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoList (
        var toDoItem: String,
        var isTaskDone: Boolean = false
        ){
    @PrimaryKey(autoGenerate = true)
    var itemNumber : Int = 0
}


