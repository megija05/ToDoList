package lv.rtu.id_191rdb114.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.*
import kotlin.system.exitProcess

class First : AppCompatActivity() {
    lateinit var new1: Button
    lateinit var ref: DatabaseReference
    lateinit var taskList:MutableList<Tasks>
    lateinit var activityList:MutableList<Activity>
    lateinit var listView1: ListView
    lateinit var listView2: ListView
    lateinit var exit: Button
    lateinit var activity1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first)

        taskList= mutableListOf()
        showlist()

        new1 = findViewById(R.id.new1)
        listView1 = findViewById(R.id.listView1)
        exit = findViewById(R.id.Quit)
        activity1 = findViewById(R.id.Activity)

        new1.setOnClickListener {
            startActivity(Intent(this, Second::class.java))
        }

        activity1.setOnClickListener {
            setContentView(R.layout.activity)
            activityList = mutableListOf()
            showlist2()
            listView2 = findViewById(R.id.listView2)

            var back: Button
            back = findViewById(R.id.button3)

            back.setOnClickListener {
                startActivity(Intent(this, First::class.java))
            }
        }

        exit.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }
    fun showlist(){
        ref = FirebaseDatabase.getInstance().getReference("TaskList")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    taskList.clear()
                    for(i in snapshot.children){
                        val task = i.getValue(Tasks::class.java)
                        taskList.add(task!!)
                    }
                    val adapter = TasksAdapter(this@First, R.layout.tasks, taskList)
                    listView1.adapter = adapter
                } else {
                    taskList.clear()
                    for(i in snapshot.children){
                        val task = i.getValue(Tasks::class.java)
                        taskList.add(task!!)
                    }
                    val adapter = TasksAdapter(this@First, R.layout.tasks, taskList)
                    listView1.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }

    fun showlist2() {
        ref = FirebaseDatabase.getInstance().getReference("ActivityList")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    activityList.clear()
                    for(i in snapshot.children){
                        val activity = i.getValue(Activity::class.java)
                        activityList.add(activity!!)
                    }
                    val adapter = ActivityAdapter(this@First, R.layout.activities, activityList)
                    listView2.adapter = adapter
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }
}