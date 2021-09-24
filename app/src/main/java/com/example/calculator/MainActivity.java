package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    int[] idButtonFirstRow;
    int idFirstRow;
    int[] idButtonSixRows;
    int[] idSixRows;

    LinearLayout firstRowLayout;
    GridLayout buttonLayout;
    TextView textViewAnswer;
    EditText arguments;
    View.OnClickListener helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateGlobal();
        generateId();
        addFirstRow();
        addSixRows();
    }

    private void addSixRows() {
        String[] labels={
                "%", "(", ")" , "BkSp",
                "1/x", "x^2", "sqrt", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };
        Button btn;
        for(int i=0;i<24;i++){
            btn=createButton(idButtonSixRows[i]);
            btn.setText(labels[i]);

            buttonLayout.addView(btn);
        }
    }

    private void generateGlobal() {
        firstRowLayout=(LinearLayout) findViewById(R.id.FirstRowLayout);
        buttonLayout=(GridLayout) findViewById(R.id.ButtonLayout);
        textViewAnswer=(TextView) findViewById(R.id.TextViewAnswer);
        arguments=(EditText)findViewById(R.id.Arguments);
        helper=null;
    }

    private void generateId() {
        idFirstRow=60000;
        idButtonFirstRow=new int[6];
        for(int i=0;i<6;i++){
            idButtonFirstRow[i]=60001+i;
        }
        idSixRows=new int[6];
        for(int i=0;i<6;i++){
            idSixRows[i]=60050+i;
        }
        idButtonSixRows=new int [24];
        for(int i=0;i<24;i++){
            idButtonSixRows[i]=60051+i;
        }
    }


    private void addFirstRow() {
        String[] labels={
                "MB", "MR", "MC", "M+", "M-", "M"
        };
        Button btn;
        for(int i=0;i<6;i++){
            btn=createButton(idButtonFirstRow[i]);
            btn=setWeight(btn, 1.0f);
            btn.setText(labels[i]);
            firstRowLayout.addView(btn);
        }
    }

    private Button setWeight(Button btn, float v) {
        LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                v
        );
        btn.setLayoutParams(param);
        return btn;
    }

    void handleOnClickButton(View view){
        Button btn=(Button)view;
        String arg=btn.getText().toString();
        String ans=textViewAnswer.getText().toString();
        String editTextString=arguments.getText().toString();

        if(arg=="="){
            editTextString=editTextString.replace("Ans", ans);
            Log.d("infix: ", editTextString);
            Log.d("postfix: ", Calculator.convertInfixToPostfix(editTextString));
            int res=Calculator.calculateAnswer(Calculator.convertInfixToPostfix(editTextString));
            textViewAnswer.setText(String.valueOf(res));
        }
        else if((arg=="+" || arg=="-" || arg=="*" || arg=="/") && ans!=""){
            if(editTextString.charAt(0)!='A'){
                arguments.setText("Ans"+arg);
            }
            else arguments.setText(editTextString+arg);
        }
        else{
            arguments.setText(editTextString+arg);
        }
    }


    private Button createButton(int i) {
        Button btn=new Button(this);
        btn.setId(i);
        if(helper==null){
            helper=new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleOnClickButton(view);
                }
            };
        }
        btn.setOnClickListener(helper);
        return btn;
    }

    private LinearLayout createLinearLayout(int id) {
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setId(id);
        return linearLayout;
    }


}