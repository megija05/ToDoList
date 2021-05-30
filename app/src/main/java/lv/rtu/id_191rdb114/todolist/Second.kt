package lv.rtu.id_191rdb114.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class Second : AppCompatActivity() {
    lateinit var save1: Button
    lateinit var title: TextInputEditText
    lateinit var task: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second)

        save1 = findViewById(R.id.save)
        title = findViewById(R.id.inputTitle)
        task = findViewById(R.id.inputText)

        save1.setOnClickListener {
            savetask();
            startActivity(Intent(this, First::class.java))
        }
    }

    private fun savetask(){  //te pasuta vertibas uz firebase datubazi
        val ref = FirebaseDatabase.getInstance().getReference("TaskList")
        val ref2 = FirebaseDatabase.getInstance().getReference("ActivityList")
        val date = Date()
        var title1 = title.text.toString()
        val task1 = task.text.toString()
        if(title1.isEmpty()){
            title1 = "None"
        }
        val taskID = ref.push().key.toString()
        val toDatabase = Tasks(taskID, title1, task1)
        ref.child(taskID).setValue(toDatabase)

        val toDatabase2 = Activity("Created", taskID, title1, date.toString())
        ref2.child(taskID).setValue(toDatabase2)
    }
}

