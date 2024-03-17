package com.jans.app

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jans.app.adapter.RecyclerItemTouchHelper
import com.jans.app.adapter.ToDoAdapter
import com.jans.app.db.DatabaseHandler
import com.jans.app.dialog.AddNewTask
import com.jans.app.dialog.DialogCloseListener
import com.jans.app.model.ToDoModel
import com.jans.app.updateUtils.InAppUpdate
import java.util.Collections


class MainActivity : AppCompatActivity(),
    DialogCloseListener {

    private lateinit var inAppUpdate: InAppUpdate

    private lateinit var db: DatabaseHandler

    private lateinit var tasksRecyclerView: RecyclerView;
    private lateinit var tasksAdapter: ToDoAdapter;
    private lateinit var fab: FloatingActionButton;

    private lateinit var taskList: ArrayList<ToDoModel>;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inAppUpdate = InAppUpdate(this)

        taskMgmtCode()

    }

    private fun taskMgmtCode(){
        db = DatabaseHandler(this)
        db.openDatabase()

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        tasksRecyclerView.setLayoutManager(LinearLayoutManager(this))
        tasksAdapter = ToDoAdapter(db, this@MainActivity)
        tasksRecyclerView.setAdapter(tasksAdapter)

        val itemTouchHelper = ItemTouchHelper(
            RecyclerItemTouchHelper(
                tasksAdapter
            )
        )
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        fab = findViewById(R.id.fab)

        addTask("Task1")
        addTask("Task2")
        addTask("Task3")
        addTask("Task4")
        addTask("Task5")
        addTask("Task6")
        addTask("Task7")
        addTask("Task8")
        addTask("Task9")
        addTask("Task10")
        addTask("Task11")
        addTask("Task12")

        taskList = db.allTasks
        Collections.reverse(taskList)

        tasksAdapter.setTasks(taskList)

        fab.setOnClickListener(View.OnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        })
    }


    private fun addTask(taskStr:String){
        val task = ToDoModel()
        task.task = taskStr
        task.status = 0
        db.insertTask(task)
    }


    override fun handleDialogClose(dialog: DialogInterface?) {
        taskList = db.allTasks
        Collections.reverse(taskList)
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        inAppUpdate.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate.onDestroy()
    }


}