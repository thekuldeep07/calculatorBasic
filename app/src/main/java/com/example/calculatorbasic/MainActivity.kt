package com.example.calculatorbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var lastNumeric : Boolean = false
    private var lastDot :Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onDigit(view: View) {
        screenText.append((view as Button).text)
        lastNumeric=true
    }

    fun onCLR(view: View) {
        val screenText =findViewById<TextView>(R.id.screenText)
        screenText.text  = ""
        lastDot=false
        lastNumeric=false

    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot){
            screenText.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view: View) {
        val value:String=screenText.text.toString()
        val operator = (view as Button).text
       if (value.isBlank() && operator == "-"){
           screenText.append(operator)
           lastNumeric=false

       }
       if (lastNumeric && !isOperatorAdded(value)) {
                screenText.append(operator)
                lastNumeric=false
                lastDot=false
            }
        }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            var returnValue: Boolean =false
            val subs: String =value.substring(1)
            if (subs.contains("/")||subs.contains("*")
                    || subs.contains("-")
                    || subs.contains("+")){

                returnValue=true
            }

            returnValue
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }


    }

    fun onEqual(view: View) {
        if (lastNumeric){
            var value=screenText.text.toString()
            var prefix=""
            try{
                if (value.startsWith("-")){
                    prefix="-"
                    value=value.substring(1)
                }
                if (value.contains("/")){
                    val splittedValue=value.split("/")
                    var one=splittedValue[0]
                    var two = splittedValue[1]
                    if(!prefix.isEmpty()){
                        one=prefix+one
                    }
                    screenText.text=(one.toDouble()/two.toDouble()).toString()
                }
                else if (value.contains("*")){
                    val splittedValue=value.split("*")
                    var one= splittedValue[0]
                    var two = splittedValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix+one
                    }
                    screenText.text=(one.toDouble()*two.toDouble()).toString()
                        }
                else if (value.contains("+")){
                    val splittedValue=value.split("+")
                    var one= splittedValue[0]
                    var two = splittedValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix+one
                    }
                    screenText.text=(one.toDouble()+two.toDouble()).toString()
                }
                else if (value.contains("-")){
                    val splittedValue=value.split("-")
                    var one= splittedValue[0]
                    var two = splittedValue[1]
                    if(!prefix.isEmpty()){
                        one = prefix+one
                    }
                    screenText.text=removeZeroAfterDot((one.toDouble()-two.toDouble()).toString())
                }

            }
            catch (e:ArithmeticException){
                e.printStackTrace()

            }

        }
        
    }

    private fun removeZeroAfterDot(result: String):String {
        var value=result
        if (result.contains(".0")){
            value=result.substring(0,result.length-2)
        }
        return  value
    }

}


