package com.android.simplecalculator;

import java.util.Hashtable;
import java.util.Stack;

public class Calculator {
    private static String operators = "=()+-*/^!";
    private Hashtable<String, Integer> orderOfOps =  new Hashtable<>();

    Calculator() {
        // PEMDAS
        orderOfOps.put("+", 1);
        orderOfOps.put("-", 1);
        orderOfOps.put("*", 2);
        orderOfOps.put("/", 2);
        orderOfOps.put("^", 3);
        orderOfOps.put("(", 4);
        orderOfOps.put(")", 4);
    }

    public Hashtable<String, Integer> getOrderOfOps() {
        return orderOfOps;
    }

    /*
     * Description: Check if e is an operator :    =()+-x/^!
     * Return: True if e is operator, otherwise False
     */
    public boolean isOperator(String e) {
        return operators.contains(e);
    }

    /*
     * Description: Compute factorial on input n
     * Return: Computed Value
     */
    private long factorial( int n ) {
        if( n == 1)
            return 1;
        return n * factorial(n-1);
    }

    /*
     * Description: Compute result given a binary operator and its operands
     * Return: Computed value
     */
    private long binaryOP( String operator, String op1, String op2) {
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
     * Description: Evaluate a post-fix expression stored in numStack
     * Return: computed value
     */
    public long evaluate(Stack<String> numStack, Stack<String> opStack) {
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
                    numStack.push(Long.toString(factorial(Integer.parseInt(operand1))));

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


}
