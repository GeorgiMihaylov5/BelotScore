package com.example.belotscore

import android.widget.TextView
import org.w3c.dom.Text
import java.lang.StringBuilder
import java.util.ArrayList
import kotlin.math.round

class History(val oldValue:String, val newValue:String){
    companion object{
        fun teamResult(list:ArrayList<History>, totalResultValue:Int, inputResultValue:Int, history:TextView){
            list.add(History(totalResultValue.toString(),inputResultValue.toString()))
            val sb = StringBuilder()
            for (i in list){
                //history1.text = "${i.oldValue} + ${i.newValue}\n${allValue1} +"
                sb.appendLine("${i.oldValue} + ${i.newValue}")
            }
            history.text = "$sb${totalResultValue + inputResultValue} +"
        }
    }
}