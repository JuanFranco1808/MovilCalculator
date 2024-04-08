package com.example.mathmatecalculator

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mathmatecalculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNumberZero.setOnClickListener(this)
        binding.btnNumberOne.setOnClickListener(this)
        binding.btnNumberTwo.setOnClickListener(this)
        binding.btnNumberThree.setOnClickListener(this)
        binding.btnNumberFour.setOnClickListener(this)
        binding.btnNumberFive.setOnClickListener(this)
        binding.btnNumberSix.setOnClickListener(this)
        binding.btnNumberSeven.setOnClickListener(this)
        binding.btnNumberEight.setOnClickListener(this)
        binding.btnNumberNine.setOnClickListener(this)
        binding.btnPoint.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
        binding.btnSubtract.setOnClickListener(this)
        binding.btnMultiply.setOnClickListener(this)
        binding.btnSplit.setOnClickListener(this)
        binding.btnEqual.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
        binding.btnDeleteAll.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnNumberZero -> {
                addNumber("0")
            }

            R.id.btnNumberOne -> {
                addNumber("1")
            }

            R.id.btnNumberTwo -> {
                addNumber("2")
            }

            R.id.btnNumberThree -> {
                addNumber("3")
            }

            R.id.btnNumberFour -> {
                addNumber("4")
            }

            R.id.btnNumberFive -> {
                addNumber("5")
            }

            R.id.btnNumberSix -> {
                addNumber("6")
            }

            R.id.btnNumberSeven -> {
                addNumber("7")
            }

            R.id.btnNumberEight -> {
                addNumber("8")
            }

            R.id.btnNumberNine -> {
                addNumber("9")
            }

            R.id.btnPoint -> {
                addOperator(".")
            }


            R.id.btnAdd -> {
                addOperator("+")
            }

            R.id.btnSubtract -> {
                addOperator("-")
            }

            R.id.btnMultiply -> {
                addOperator("*")
            }

            R.id.btnSplit -> {
                addOperator("/")
            }

            R.id.btnDelete -> {
                deleteLastCharacter()
            }

            R.id.btnDeleteAll -> {
                clearAll()
            }

            R.id.btnEqual -> {
                val expr = binding.editTextResult.text.toString()
                val result = evaluate(expr)
                binding.editTextResult.text = result.toString()
            }
        }
    }

    private fun addNumber(number: String) = binding.editTextResult.append(number)

    private fun addOperator(operator: String) = binding.editTextResult.append(operator)

    private fun deleteLastCharacter() {
        val text = binding.editTextResult.text.toString()
        if (text.isNotEmpty()) {
            binding.editTextResult.text = text.dropLast(1)
        }
    }

    private fun clearAll() {
        binding.editTextResult.text = ""
    }

    private fun evaluate(expr: String): Double {
        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<Char>()

        var i = 0
        while (i < expr.length) {
            if (expr[i].isDigit() || expr[i] == '.' || (expr[i] == '-')) {
                var buffer = ""
                if (expr[i] == '-') {
                    buffer += expr[i]
                    i++
                }
                while (i < expr.length && (expr[i].isDigit() || expr[i] == '.')) {
                    buffer += expr[i]
                    i++
                }
                numbers.add(buffer.toDouble())
            }

            if (i < expr.length && expr[i] in listOf('+', '-', '*', '/')) {
                while (operators.isNotEmpty() && priority(expr[i]) <= priority(operators.last())) {
                    processOperation(numbers, operators)
                }
                operators.add(expr[i])
                i++
            }
        }

        while (operators.isNotEmpty()) {
            processOperation(numbers, operators)
        }

        return numbers.last()
    }

    private fun priority(op: Char): Int {
        return when (op) {
            '+' -> 1
            '-' -> 1
            '*' -> 2
            '/' -> 2
            else -> -1
        }
    }

    private fun processOperation(numbers: MutableList<Double>, operators: MutableList<Char>) {
        if (operators.isEmpty() || numbers.size < 2) {
            return
        }

        val operator = operators.removeLast()
        val num1 = numbers.removeLast()
        val num2 = numbers.removeLast()

        val result = when (operator) {
            '+' -> num2 + num1
            '-' -> num2 - num1
            '*' -> num2 * num1
            '/' -> {
                if (num1 == 0.0) {
                    Toast.makeText(this, "Error: Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    num2 / num1
                }
            }

            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }

        numbers.add(result)
    }
}

