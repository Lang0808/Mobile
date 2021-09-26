package com.example.calculator;

import android.util.Log;

import java.util.Stack;

public class Calculator {

    static String convertInfixToPostfix(String s){
        Stack<String> stack=new Stack<String>();
        StringBuilder postfix=new StringBuilder();
        boolean isOperator=false;
        for(int i=0;i<s.length();i++){
            if(!isOperator && (s.charAt(i)=='-' || s.charAt(i)=='+')){
                int j=i;
                boolean positive=true;
                while(j<s.length() && (s.charAt(j)=='+'|| s.charAt(j)=='-')){
                    if(s.charAt(j)=='-') positive=!positive;
                    j++;
                }
                i=j;
                while(j<s.length() && s.charAt(j)>='0' && s.charAt(j)<='9') j++;
                if(!positive){
                    postfix.append('-');
                }
                postfix.append(s.substring(i, j));
                postfix.append(' ');
                i=j-1;
                isOperator=true;
            }
            else if(s.charAt(i)>='0' && s.charAt(i)<='9'){
                int j=i;
                while(j<s.length() && s.charAt(j)>='0' && s.charAt(j)<='9') j++;
                postfix.append(s.substring(i, j));
                postfix.append(' ');
                i=j-1;
                isOperator=true;
            }
            else if(s.charAt(i)=='^'){
                postfix.append("^ ");
                isOperator=false;
            }
            else if(s.charAt(i)=='('){
                stack.push("(");
                isOperator=false;
            }
            else if(s.charAt(i)==')'){
                while(!stack.empty() && stack.peek()!="("){
                    postfix.append(stack.pop()+' ');
                }
                if(!stack.empty()) stack.pop();
                isOperator=false;
            }
            else{
                isOperator=false;
                while(!stack.empty() && preceed(stack.peek().charAt(0))>=preceed(s.charAt(i))){
                    postfix.append(stack.peek()+' ');
                    stack.pop();
                }
                stack.push(s.substring(i, i+1));
            }
        }
        while(!stack.empty()){
            postfix.append(stack.pop()+' ');

        }
        return new String(postfix);
    }

    static int preceed(char a){
        if(a=='+' || a=='-') return 1;
        if(a=='*' || a=='/') return 2;
        if(a=='^') return 3;
        return 0;
    }

    static int calculateAnswer(String postfix) throws Exception {
        Stack<Integer>stack=new Stack<Integer>();
        for(int i=0;i<postfix.length();i++){
            int j=i;
            while(j<postfix.length() && postfix.charAt(j)!=' ') j++;
            String tmp=postfix.substring(i, j);
            boolean b=(tmp=="+");
            Log.d("tmp: ", tmp+" "+String.valueOf(b));
            if(tmp.equals("+")){
                if(stack.size()==0){
                    throw new Exception("Syntax Error");
                }
                else if(stack.size()==1){
                    throw new Exception("Syntax Error");
                }
                else{
                    int a1=stack.pop();
                    int a2=stack.pop();
                    int a3=a1+a2;
                    stack.push(a3);
                }

            }
            else if(tmp.equals("-")){
                if(stack.size()==0){
                    throw new Exception("Syntax Error");
                }
                else if(stack.size()==1){
                    throw new Exception("Syntax Error");
                }
                else{
                    int a1=stack.pop();
                    int a2=stack.pop();
                    int a3=a2-a1;
                    stack.push(a3);
                }

            }
            else if(tmp.equals("*")){
                if(stack.size()<=1){
                    throw new Exception("Syntax Error");
                }
                else{
                    int a1=stack.pop();
                    int a2=stack.pop();
                    int a3=a1*a2;
                    stack.push(a3);
                }

            }
            else if(tmp.equals("/")){
                if(stack.size()<=1){
                    throw new Exception("Syntax Error");
                }
                else{
                    try{
                        int a1=stack.pop();
                        int a2=stack.pop();
                        int a3=a2/a1;
                        stack.push(a3);
                    }
                    catch(ArithmeticException e){
                        throw new Exception("Something wrong");
                    }

                }

            }
            else if(tmp.equals(" ")){

            }
            else{
                stack.push(Integer.parseInt(tmp));
            }
            i=j;
        }
        if(stack.empty() || stack.size()>=2){
            throw new Exception("Syntax ERROR");
        }
        return stack.pop();
    }
}
