package com.example.belotscore

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception
//import java.lang.StringBuilder
import java.util.ArrayList
import kotlin.text.StringBuilder

class MainActivity : AppCompatActivity() {
    private val list1:ArrayList<History> = arrayListOf()
    private val list2:ArrayList<History> = arrayListOf()
    private var round = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addButton = findViewById<Button>(R.id.button)

        addButton.setOnClickListener{
            try {
                //input for team 1
                val inputResult1 = findViewById<TextView>(R.id.inputResult1)
                //the value of the input
                val inputResult1Value = if (!inputResult1.text.isNullOrBlank())
                    inputResult1.text.toString().toInt()
                else 0

                //input for team 2
                val inputResult2 = findViewById<TextView>(R.id.inputResult2)
                //the value of the input
                val inputResult2Value = if (!inputResult2.text.isNullOrEmpty())
                    inputResult2.text.toString().toInt()
                else 0


                //total for team 1
                val totalResult1 = findViewById<TextView>(R.id.totalResult1)
                //the value of total textview
                var totalResult1Value = if (totalResult1.text.toString().toInt() > 0)
                    totalResult1.text.toString().toInt()
                else 0

                //total for team 2
                val totalResult2 = findViewById<TextView>(R.id.totalResult2)
                //the value of total textview
                var totalResult2Value = if (totalResult2.text.toString().toInt() > 0)
                    totalResult2.text.toString().toInt()
                else 0

                val history1 = findViewById<TextView>(R.id.history1)
                val history2 = findViewById<TextView>(R.id.history2)

                //history update
                History.teamResult(list1,totalResult1Value,inputResult1Value,history1)
                History.teamResult(list2,totalResult2Value,inputResult2Value,history2)

                //Add new values to the screen
                totalResult1Value += inputResult1Value
                totalResult2Value += inputResult2Value
                totalResult1.text = totalResult1Value.toString()
                totalResult2.text = totalResult2Value.toString()

                round++

                if (totalResult1Value >= 151 || totalResult2Value >= 151) {
                    win(totalResult1Value, totalResult2Value, totalResult1, totalResult2, addButton)
                }

                //clear input field
                inputResult1.text = ""
                inputResult2.text = ""
            }
            catch(ex:Exception) {
                finish()
                startActivity(this.intent)

            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.about -> {
                finish()
                val intent = Intent(this, about().javaClass)
                startActivity(intent)
            }
            R.id.newGame -> {
                val builder = AlertDialog.Builder(this)

                with(builder) {

                    setTitle("Start a new game")
                    setMessage("Are you sure you want to start a new name?")
                }
                builder.setPositiveButton("Yes",DialogInterface.OnClickListener{ dialog, _ ->
                    finish()
                    startActivity(this.intent)
                    dialog.cancel()
                })
                builder.setNegativeButton("No",DialogInterface.OnClickListener{ dialog, _ ->
                    dialog.cancel()
                })

                val alert: AlertDialog = builder.create()
                alert.show()

            }
            R.id.back -> {
                val totalResult1 = findViewById<TextView>(R.id.totalResult1)
                val totalResult2 = findViewById<TextView>(R.id.totalResult2)

                if (round > 0) {
                    val history1 = findViewById<TextView>(R.id.history1)
                    val history2 = findViewById<TextView>(R.id.history2)

                    undo(history1, list1, totalResult1)
                    undo(history2, list2, totalResult2)
                    round--
                }

            }
        }

        return super.onOptionsItemSelected(item)
    }

private fun undo(all:TextView, list:ArrayList<History>, totalResult:TextView){
    val historyList = all.text.toString().split("\n").toMutableList()

    historyList.removeLast()
    list.removeLast()

    var lastAddedPoint = ""
    val index = historyList.count() - 1
    //if (round > 0) {
        for (i in historyList.last()) {
            lastAddedPoint += i.toString()
            if (i == '+'){
                break
            }
        }
        historyList[index] = lastAddedPoint

        val sb = StringBuilder()
        for (i in historyList) {
            if (i == historyList.last()) {
                sb.append(i)
                totalResult.text = i.dropLast(2)
            } else {
                sb.appendLine(i)
            }
        }
        all.text = sb
    //}
}


    private fun win(totalResult1Value:Int, totalResult2Value:Int, totalResult1:TextView, totalResult2:TextView, addButton:Button){
        //input for team 1
        val inputResult1 = findViewById<TextView>(R.id.inputResult1)
        //the value of the input
        val inputResult1Value = if (!inputResult1.text.isNullOrBlank())
            inputResult1.text.toString()
        else "0"

        //input for team 2
        val inputResult2 = findViewById<TextView>(R.id.inputResult2)
        //the value of the input
        val inputResult2Value = if (!inputResult2.text.isNullOrEmpty())
            inputResult2.text.toString()
        else "0"

        if (totalResult1Value > totalResult2Value && inputResult1Value != "0" && inputResult2Value != "0") {
            totalResult1.text = getString(R.string.win)
            totalResult2.text = getString(R.string.lose)
            addButton.text = getString(R.string.newGame)
        } else if (totalResult2Value > totalResult1Value && inputResult1Value != "0" && inputResult2Value != "0") {
            totalResult2.text = getString(R.string.win)
            totalResult1.text = getString(R.string.lose)
            addButton.text = getString(R.string.newGame)
        }
    }
}