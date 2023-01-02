package com.example.csvtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etLabor, etMaterials, etOverhead, etMatMarkup, etProdCost,
            etWholesaleMarkup, etWholesalePrice, etRetailMarkup ,etRetailPrice;
    String strLabor, strMat, strOverhead, strMatMarkup, strProdCost, strWM, strWP, strRM, strRP, resultMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide(); //hide action bar

        readDataByColumn();

        ImageButton btnCompute = (ImageButton) findViewById(R.id.btnCompute);


        btnCompute.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Computing....", Toast.LENGTH_SHORT).show();
        ComputeResult();
    }

    ArrayList<String> listDataArray;


    private void readDataByColumn() {

        InputStream is = getResources().openRawResource(R.raw.data);

        // Reads text from character-input stream, buffering characters for efficient reading
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

        // Initialization
        String line = "";

        // Handling exceptions
        try {
            listDataArray = new ArrayList<String>();
            // If buffer is not empty
            while ((line = br.readLine()) != null) {
                // use comma as separator columns of CSV
                String[] cols = line.split(",");

                // Print in logcat
                System.out.println("Column 0 = '" + cols[0] + "', Column 1 = '" + cols[1] + "', Column 2: '" + cols[2] + "'");
                listDataArray.add(cols[0]+ " " + cols[1]+ " " + cols[2]);
            }
        } catch (IOException e) {
            // Prints throwable details
            e.printStackTrace();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listDataArray);
        ((ListView) findViewById(R.id.list)).setAdapter(arrayAdapter);

    }


    public void ComputeResult(){
        //register EditTexts
        etLabor = (EditText) findViewById(R.id.etLabor);
        etMaterials = (EditText) findViewById(R.id.etMaterials);
        etOverhead = (EditText) findViewById(R.id.etOverhead);
        etMatMarkup = (EditText) findViewById(R.id.etMatMarkup);
        etProdCost = (EditText) findViewById(R.id.etProdCost);
        etRetailMarkup = (EditText) findViewById(R.id.etRetailMarkup);
        etWholesaleMarkup = (EditText) findViewById(R.id.etWholesaleMarkup);
        etWholesalePrice = (EditText) findViewById(R.id.etWholesalePrice);
        etRetailPrice = (EditText) findViewById(R.id.etRetailPrice);

        if(etLabor.getText().toString().isEmpty() ||etRetailMarkup.getText().toString().isEmpty() ||etWholesaleMarkup.getText().toString().isEmpty() || etMaterials.getText().toString().isEmpty() || etMatMarkup.getText().toString().isEmpty() || etOverhead.getText().toString().isEmpty()){
           //set values for fields after checking for missing information
            strLabor = "0";
            etLabor.setText("");
            strMat = "0";
            etMaterials.setText("");
            strOverhead = "0";
            etOverhead.setText("");
            strMatMarkup = "1";
            etMatMarkup.setText(strMatMarkup);
            strRM = "1";
            etRetailMarkup.setText(strRM);
            strWM = "1";
            etWholesaleMarkup.setText(strWM);

            Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show();
            Bundle args = new Bundle();
            args.putString("prodCost", resultMessage);

            // Create a dialog instance
            DialogFragmentCustom dialogFragmentImp = new DialogFragmentCustom();
            // Pass on dialog argument(args), the prodCost
            dialogFragmentImp.setArguments(args);
            // Display the Dialog
            dialogFragmentImp.show(getSupportFragmentManager(),"Display Result");
        }else{
            strLabor = etLabor.getText().toString();
            strMat = etMaterials.getText().toString();
            strOverhead = etOverhead.getText().toString();
            strMatMarkup = etMatMarkup.getText().toString();
            strRM = etRetailMarkup.getText().toString();
            strWM = etWholesaleMarkup.getText().toString();

        }
        double rawL = Double.parseDouble(strLabor);
        double rawM = Double.parseDouble(strMat);
        double rawO = Double.parseDouble(strOverhead);
        double rawMM = Double.parseDouble(strMatMarkup);
        double rawRM = Double.parseDouble(strRM);
        double rawWM = Double.parseDouble(strWM);

        double prodCost = rawO + rawL + rawM*rawMM ;
        double wholesalePrice = prodCost * rawWM;
        double retailPrice = prodCost * rawRM;

        strProdCost = String.valueOf(prodCost);
        strWP = String.valueOf(wholesalePrice);
        strRP = String.valueOf(retailPrice);

        etProdCost.setText(strProdCost);
        etWholesalePrice.setText(strWP);
        etRetailPrice.setText(strRP);

    }

}