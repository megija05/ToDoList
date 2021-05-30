package lv.rtu.id_191rdb114.todolist

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class TasksAdapter(val a: Context, val layoutResId:Int, val taskList: List<Tasks>):ArrayAdapter<Tasks>(a, layoutResId, taskList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {//ViewGroup?
        val layoutInflater: LayoutInflater= LayoutInflater.from(a)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val button = view.findViewById<Button>(R.id.button2)

        val task = taskList[position]
        button.text=task.title

        button.setOnClickListener(){
            clicked(task)
        }

        return view;
    }

    fun clicked(task: Tasks) { // kad uz teksta uzspiež, viņš parādās un tad var updatot ja velas
        val builder = AlertDialog.Builder(a)

        val inflater = LayoutInflater.from(a)

        val view = inflater.inflate(R.layout.showtask, null)
        val inputTitle = view.findViewById<TextInputEditText>(R.id.inputTitle)
        val inputText = view.findViewById<TextInputEditText>(R.id.inputText)

        inputTitle.setText(task.title)
        inputText.setText(task.task)

        //builder.setTitle(inputTitle.text.toString()) //tam lodzinam virsrakts

        builder.setView(view)
        builder.setPositiveButton("Update"
        ) { dialog, which ->
            val dbTask = FirebaseDatabase.getInstance().getReference("TaskList")
            val newtitle = inputTitle.text.toString()
            val newtext = inputText.text.toString()

            val task = Tasks(task.id, newtitle, newtext)
            dbTask.child(task.id).setValue(task)

            Toast.makeText(a, "Task is updated!", Toast.LENGTH_LONG).show()
        }

        builder.setNeutralButton("Delete"
        ) { dialog, which ->
            val dbTask = FirebaseDatabase.getInstance().getReference("TaskList").child(task.id)
            val dbActivity = FirebaseDatabase.getInstance().getReference("ActivityList")
            val date = Date()
            val taskid = dbTask.push().key.toString()
            val toDatabase2 = Activity("Deleted", taskid, task.title, date.toString())
            dbActivity.child(taskid).setValue(toDatabase2)

            dbTask.removeValue()

            Toast.makeText(a, "Task is deleted!", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("Back"
        ) { dialog, which ->
        }

        val alert = builder.create()
        alert.show()
    }
}

class ActivityAdapter(val a: Context, val layoutResId:Int, val activityList: MutableList<Activity>):ArrayAdapter<Activity>(a, layoutResId, activityList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {//ViewGroup?
        val layoutInflater: LayoutInflater= LayoutInflater.from(a)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textview = view.findViewById<TextView>(R.id.textView2)

        val activity = activityList[position]
        textview.text = activity.f + " " + activity.title + " " + activity.date

        return view;
    }
}
//https://www.youtube.com/watch?v=ZB1liwuQCP8
//www.youtube.com/watch?v=kc3LVeCDy14&list=RDCMUC9YTuDeKzDoyOphWHtdK0jA&start_radio=1&rv=kc3LVeCDy14&t=156