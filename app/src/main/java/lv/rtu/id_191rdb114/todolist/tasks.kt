package lv.rtu.id_191rdb114.todolist

import java.util.*


class Tasks(val id: String, val title: String, val task: String){
    constructor():this("","",""){}
}

class Activity(val f: String, val id: String, val title: String, val date: String){
    constructor():this("", "", "", ""){}
}
