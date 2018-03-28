package com.android.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Stack;
import java.util.Arrays;

/**
 * Created by Andy Or on 3/25/2018.
 */
public class MainActivity extends AppCompatActivity{

    private static String operators = new String("=()+-*/^!");
    private Stack<String> stack1 = new Stack<String>(); // input and numbers
    private Stack<String> stack2 = new Stack<String>(); // postfix and operators

    // Buttons on Calculator
    final EditText userInput = (EditText) findViewById(R.id.output);
    final Button add = (Button) findViewById(R.id.add);
    final Button subtract = (Button) findViewById(R.id.subtract);
    final Button multiply = (Button) findViewById(R.id.multiply);
    final Button divide = (Button) findViewById(R.id.divide);
    final Button equal = (Button) findViewById(R.id.equal);
    final Button num0 = (Button) findViewById(R.id.num0);
    final Button num1 = (Button) findViewById(R.id.num1);
    final Button num2 = (Button) findViewById(R.id.num2);
    final Button num3 = (Button) findViewById(R.id.num3);
    final Button num4 = (Button) findViewById(R.id.num4);
    final Button num5 = (Button) findViewById(R.id.num5);
    final Button num6 = (Button) findViewById(R.id.num6);
    final Button num7 = (Button) findViewById(R.id.num7);
    final Button num8 = (Button) findViewById(R.id.num8);
    final Button num9 = (Button) findViewById(R.id.num9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Basic Operations
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stack1.push("+");
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stack1.push("-");
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stack1.push("*");
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stack1.push("/");
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();     // evaluate all of stack

            }
        });


        // Number inputs
        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('0');
            }
        });
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('1');
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('2');
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('3');
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('4');
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('5');
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('6');
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('7');
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('8');
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendNum('9');
            }
        });

    }

    /*
     * Description: Check if e is an operator :    =()+-x/^!
     * Return: True if e is operator, otherwise False
     */
    public boolean isOperator(String e) {
        return (operators.indexOf(e) >= 0)? true : false;
    }

    /*
     * Description: Evaluate a post-fix expression stored in stack1
     * Return: computed value
     */
    public long evaluate() {
        String operator;
        String operand1, operand2;
        long result;
        // reverse stack1 onto stack2
        while (!stack1.isEmpty())
            stack2.push(stack1.pop());

        while (!stack2.isEmpty()) {

            if (!isOperator(stack2.peek())) { // push numbers to stack1
                stack1.push(stack2.pop());

            } else {   // top element is an operand
                operator = stack2.pop();
                operand1 = stack1.pop();

                // Unary Operator
                if (operator == "!") {
                    // factorial
                    stack1.push(Long.toString(Long.parseLong(operand1)));

                    // Binary Operator
                } else {
                    operand2 = stack1.pop();
                    stack1.push(Long.toString(binaryOP(operator, operand1, operand2)));
                }
            }
        }

        result = Long.parseLong(stack1.peek());
        return result;
    }


    /*
     * Description: Compute result given a binary operator and its operands
     * Return: Computed value
     */
    public long binaryOP( String operator, String op1, String op2) {
        long result;

        switch (operator) {
            case "+":
                result = Integer.parseInt(op1) + Integer.parseInt(op2);
                break;
            case "-":
                result = Integer.parseInt(op1) - Integer.parseInt(op2);
                break;
            case "/":
                result = Integer.parseInt(op2) / Integer.parseInt(op1);     // operand order matters
                break;
            case "*":
                result = Integer.parseInt(op1) * Integer.parseInt(op2);
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    /*
     * Description: Compute factorial on input n
     * Return: Computed Value
     */
    public long factorial( int n ) {
        if( n == 1)
            return 1;
        return n * factorial(n-1);
    }

    /*
     * Description: Appends character c to current user input
     */
    public void appendNum(char c) {
             userInput.setText(userInput.getText().append(c));
    }
}
