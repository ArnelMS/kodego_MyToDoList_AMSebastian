package com.kodego.activity.app.mytodolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ToDoList::class],
    version = 1
)

abstract class ToDoListDatabase: RoomDatabase() {
    abstract fun getTodoList():ToDoListDao

    companion object {
        @Volatile
        private var instance: ToDoListDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ToDoListDatabase::class.java,
            "TodoList"
            ).build()
        }
        private fun deleteAllToDoList(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ToDoListDatabase::class.java,
            "TodoListTable"
        )
        private fun isTaskDone(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ToDoListDatabase::class.java,
            "TodoListTable"
        )
}
