package com.android.simplecalculator;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Stack;

/**
 * Created by Andy Or on 3/25/2018.
 */
public class MainActivity extends AppCompatActivity{

    private static String operators = "=()+-*/^!";
    private Stack<String> numStack = new Stack<>(); // input and numbers
    private Stack<String> opStack = new Stack<>(); // postfix and operators
    private Hashtable<String, Integer> orderOfOps =  new Hashtable<>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private long result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Buttons on Calculator
        final EditText userInput = findViewById(R.id.output);
        final Button add = findViewById(R.id.add);
        final Button subtract = findViewById(R.id.subtract);
        final Button multiply = findViewById(R.id.multiply);
        final Button divide = findViewById(R.id.divide);
        final Button equal = findViewById(R.id.equal);
        final Button num0 = findViewById(R.id.num0);
        final Button num1 = findViewById(R.id.num1);
        final Button num2 = findViewById(R.id.num2);
        final Button num3 = findViewById(R.id.num3);
        final Button num4 = findViewById(R.id.num4);
        final Button num5 = findViewById(R.id.num5);
        final Button num6 = findViewById(R.id.num6);
        final Button num7 = findViewById(R.id.num7);
        final Button num8 = findViewById(R.id.num8);
        final Button num9 = findViewById(R.id.num9);

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
                userInput.setText(append(userInput.getText().toString(), " + "));
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(), " - "));
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(), " * "));
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString()," / "));
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "equal() on infix : " + userInput.getText().toString());
                toPostFix(userInput.getText().toString());    // change infix expression to postfix
                Log.d(TAG, "postfix in numStack, ready to evaluate");
                result = evaluate();     // evaluate all of stack
                userInput.setText(String.format(Locale.US, "%d",    result));
                Log.d(TAG, "result = " + result);
            }
        });


        // Number inputs
        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"0"));
            }
        });
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"1"));
            }
        });
        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"2"));
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"3"));
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"4"));
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"5"));
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"6"));
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"7"));
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"8"));
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInput.setText(append(userInput.getText().toString(),"9"));
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
                result = Integer.parseInt(op2) + Integer.parseInt(op1);
                break;
            case "-":
                result = Integer.parseInt(op2) - Integer.parseInt(op1);     // operand order matters
                break;
            case "/":
                result = Integer.parseInt(op2) / Integer.parseInt(op1);     // operand order matters
                break;
            case "*":
                result = Integer.parseInt(op2) * Integer.parseInt(op1);
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
    public String append(String currentInput, String append) {
        return currentInput + append;
    }

    /*
     * Description: Convert infix expression from user to postfix, stored in numStack.
     * Source of Help:
     *  http://interactivepython.org/runestone/static/pythonds/BasicDS/InfixPrefixandPostfixExpressions.html
     */
    public void toPostFix(String infix) {
        String[] expression = infix.split(" ");
        for (String input: expression ) {

            if( input.equals("(")) {
                opStack.push(input);

            } else if ( input.equals(")")) {
                while (!opStack.peek().equals("(") )
                    numStack.push(opStack.pop());

            } else if( isOperator(input) ) {
                // remove any  precedence operators on opstack
       //         Log.d(TAG, "input = " + input);
//                Log.d(TAG, "top of op stack is " + opStack.peek());
                while (!opStack.isEmpty() &&
                        (orderOfOps.get(input) < orderOfOps.get(opStack.peek()))  ) {
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
