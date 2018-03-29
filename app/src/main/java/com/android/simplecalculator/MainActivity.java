package com.android.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Created by Andy Or on 3/25/2018.
 */
public class MainActivity extends AppCompatActivity{

    private static String operators = "=()+-*/^!";
    private Stack<String> numStack = new Stack<>(); // input and numbers
    private Stack<String> opStack = new Stack<>(); // postfix and operators
    private Hashtable<String, Integer> orderOfOps =  new Hashtable<>();

    // Buttons on Calculator
    EditText userInput = (EditText) findViewById(R.id.output);
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
        userInput.setText(" ");


        // PEMDAS
        orderOfOps.put("+", 1);
        orderOfOps.put("-", 1);
        orderOfOps.put("*", 2);
        orderOfOps.put("/", 2);
        orderOfOps.put("^", 3);
        orderOfOps.put("!", 3);

        // Basic Operations
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp(" + ");
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp(" - ");
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp(" * ");
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp(" / ");
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPostFix();    // change infix expression to postfix
                evaluate();     // evaluate all of stack

            }
        });


        // Number inputs
        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("0");
            }
        });
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("1");
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("2");
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("3");
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("4");
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("5");
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("6");
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("7");
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("8");
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendExp("9");
            }
        });

    }

    /*
     * Description: Check if e is an operator :    =()+-x/^!
     * Return: True if e is operator, otherwise False
     */
    public boolean isOperator(String e) {
        return operators.contains(e);
    }

    /*
     * Description: Evaluate a post-fix expression stored in numStack
     * Return: computed value
     */
    public long evaluate() {
        String operator;
        String operand1, operand2;
        long result;
        // reverse numStack onto opStack
        while (!numStack.isEmpty())
            opStack.push(numStack.pop());

        while (!opStack.isEmpty()) {

            if (!isOperator(opStack.peek())) { // push numbers to numStack
                numStack.push(opStack.pop());

            } else {   // top element is an operand
                operator = opStack.pop();
                operand1 = numStack.pop();

                // Unary Operator
                if (operator.equals("!")) {
                    // factorial
                    numStack.push(Long.toString(Long.parseLong(operand1)));

                    // Binary Operator
                } else {
                    operand2 = numStack.pop();
                    numStack.push(Long.toString(binaryOP(operator, operand1, operand2)));
                }
            }
        }

        result = Long.parseLong(numStack.peek());
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
     * Description: Appends String c to current user input
     * Source of Help:
     *  http://interactivepython.org/runestone/static/pythonds/BasicDS/InfixPrefixandPostfixExpressions.html
     */
    public void appendExp(String c) {
        String added = userInput.getText() + c;
        userInput.setText(added);
    }

    /*
     * Description: Convert infix expression from user to postfix, stored in numStack.
     * Source of Help:
     *  http://interactivepython.org/runestone/static/pythonds/BasicDS/InfixPrefixandPostfixExpressions.html
     */
    public void toPostFix() {
        String[] expression = userInput.getText().toString().split(" ");
        for (String input: expression ) {

            if( input.equals("(")) {
                opStack.push(input);

            } else if ( input.equals(")")) {
                while (!opStack.peek().equals("("))
                    numStack.push(opStack.pop());

            } else if( isOperator(input) ) {
                // remove any  precedence operators on opstack
                while (!opStack.isEmpty() &&
                        orderOfOps.get(input) < orderOfOps.get(opStack.peek())) {
                    numStack.push(opStack.pop());
                }
                opStack.push(input);

            } else { // operand
                numStack.push(input);
            }
        }
        // any remain operators
        while (!opStack.isEmpty())
            numStack.push(opStack.pop());
    }
}
