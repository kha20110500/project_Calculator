package hcmute.edu.project_calculator_nhom06;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnNumber0,btnNumber1, btnNumber2, btnNumber3, btnNumber4, btnNumber5, btnNumber6, btnNumber7, btnNumber8, btnNumber9;
    Button btnAC, btnPlus, btnMinus, btnMultiply, btnDivide, btnPercentage, btnDelete, btnPoint, btnEqual;
    TextView currentText, resultText;
    String syntaxError = "Syntax Error";
    String mathError = "Math Error";
    boolean checkError = false;
    boolean checkErrorMath = false;

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = currentText.getText().toString().trim();
        int dataLength = dataToCalculate.length();

        if(buttonText.equals("AC")) {
            currentText.setText("");
            resultText.setText("0");
            checkError = false;
            return;
        }


        if(buttonText.equals("=")) {
            if (dataToCalculate.equals("")) return;
            if(dataToCalculate.charAt(dataToCalculate.length()-1) == '+' ||
                    dataToCalculate.charAt(dataToCalculate.length()-1) == '-' ||
                    dataToCalculate.charAt(dataToCalculate.length()-1) == 'x' ||
                    dataToCalculate.charAt(dataToCalculate.length()-1) == '/' ||
                    dataToCalculate.charAt(dataToCalculate.length()-1) == '.'){
                resultText.setText("Syntax Error");
                return;
            }
            String result = String.valueOf(getResultFromString(dataToCalculate));
            result = result.endsWith(".0") ? result.replace(".0","") : result;
            result = checkError ? syntaxError : result;
            result = checkErrorMath ? mathError : result;
            resultText.setText(result);
            return;
        }

        if(buttonText.equals("<-")) {
            try{
                dataToCalculate = dataToCalculate.substring(0, dataLength - 1);
                currentText.setText(dataToCalculate);
                return;
            }catch (Exception e){
                currentText.setText(dataToCalculate);
                resultText.setText("0");
                return;
            }
        }

        if (buttonText.equals(".")) {
            if(!dataToCalculate.equals("")){
                boolean flag = true;
                char c = dataToCalculate.charAt(dataToCalculate.length()-1);
                if((c == '%' || c == '.' || c == '+' || c == '-' || c == '*' || c == '/')){
                    flag = false;
                }else{
                    try{
                        for (int i = dataToCalculate.length()-2; i >= 0; i--) {
                            char temp = dataToCalculate.charAt(i);
                            if(temp == '.'){
                                flag = false;
                                break;
                            }
                            if(temp == '+' || temp == '-' || temp == '*' || temp == '/'){
                                flag = true;
                                break;
                            }
                        }
                    }catch (Exception e){
                        flag = true;
                    }
                }
                if(flag){
                    currentText.setText(dataToCalculate +".");
                }
            }
            return;
        }

        if(buttonText.equals("+") || buttonText.equals("-") || buttonText.equals("x")
                || buttonText.equals("/")  ||  buttonText.equals("%")) {
            String checkNumBefore;
            String check2ndNumBefore;
            try {
                checkNumBefore = dataToCalculate.substring(dataLength - 1);
            }
            catch (IndexOutOfBoundsException ex) {
                if(buttonText.equals("-")) currentText.setText(buttonText);
                return;
            }
            if (checkNumBefore.equals(buttonText)) return;
            else if(checkNumBefore.equals("+") || checkNumBefore.equals("-")
                    || checkNumBefore.equals("x") || checkNumBefore.equals("/")){
                try {
                    check2ndNumBefore = dataToCalculate.substring(dataLength - 2);
                    dataToCalculate = dataToCalculate.substring(0, dataLength - 1);
                }
                catch (IndexOutOfBoundsException ex) {
                    return;
                }
            }
        }
        checkError = false;
        checkErrorMath= false;
        dataToCalculate += buttonText;
        currentText.setText(dataToCalculate);
    }

    void assignId(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentText = (TextView) findViewById(R.id.textView);
        resultText = (TextView) findViewById(R.id.textView_result);

        assignId(btnNumber0, R.id.btnNumber0);
        assignId(btnNumber1, R.id.btnNumber1);
        assignId(btnNumber2, R.id.btnNumber2);
        assignId(btnNumber3, R.id.btnNumber3);
        assignId(btnNumber4, R.id.btnNumber4);
        assignId(btnNumber5, R.id.btnNumber5);
        assignId(btnNumber6, R.id.btnNumber6);
        assignId(btnNumber7, R.id.btnNumber7);
        assignId(btnNumber8, R.id.btnNumber8);
        assignId(btnNumber9, R.id.btnNumber9);

        assignId(btnPlus, R.id.btnPlus);
        assignId(btnMinus, R.id.btnMinus);
        assignId(btnMultiply, R.id.btnMultiply);
        assignId(btnDivide, R.id.btnDivide);
        assignId(btnPercentage, R.id.btnPercentage);
        assignId(btnDelete, R.id.btnDelete);
        assignId(btnAC, R.id.btnAllClear);
        assignId(btnPoint, R.id.btnPoint);
        assignId(btnEqual, R.id.btnEqual);

    }

    public double getResultFromString(String input){
        double result = 0;
        String text = input;
        ArrayList<String> numbers = new ArrayList<String>();
        ArrayList<Double> numbersDouble = new ArrayList<Double>();
        ArrayList<String> operations = new ArrayList<String>();
        int start = 0;
        int end = 0;
        boolean check = true;
        while(check){
            for(int i = start; i< text.length(); i++){
                if((text.charAt(i) == '+' || text.charAt(i) == '-' || text.charAt(i) == 'x' || text.charAt(i) == '/') && i != start){
                    end = i;
                    numbers.add(text.substring(start, end));
                    operations.add(String.valueOf(text.charAt(i)));
                    start = i + 1;
                    if(start == text.length()) check = false;
                    break;
                }
                if(i == text.length()-1){
                    end = i + 1;
                    numbers.add(text.substring(start, end));
                    check = false;
                    break;
                }
            }

        }
        for(String a : numbers){
            numbersDouble.add(StringToDouble(a));
        }
        check = true;
        while (check){
            if(operations.size() == 0){
                break;
            }
            for(int i = 0; i< operations.size(); i++){
                if(operations.get(i).equals("x")){
                    Double temp = numbersDouble.get(i).doubleValue() * numbersDouble.get(i+1).doubleValue();
                    numbersDouble.set(i, temp);
                    numbersDouble.remove(i+1);
                    operations.remove(i);
                    break;
                }
                if(operations.get(i).equals("/")){
                    Double temp = numbersDouble.get(i).doubleValue() / numbersDouble.get(i+1).doubleValue();
                    numbersDouble.set(i, temp);
                    numbersDouble.remove(i+1);
                    operations.remove(i);
                    break;
                }
                if(operations.size() == 0 || i == operations.size()-1){
                    check = false;
                    break;
                }
            }
        }
        result = result + numbersDouble.get(0);
        try {
            for(int i = 0; i < operations.size(); i++){
                if(operations.get(i).equals("+")){
                    result = result + numbersDouble.get(i+1);
                }else{
                    result = result - numbersDouble.get(i+1);
                }
            }
        }
        catch (IndexOutOfBoundsException ex) {
            checkError = true;
        }

        BigDecimal bd = BigDecimal.valueOf(0);
        try {
            bd = new BigDecimal(result).setScale(7, RoundingMode.HALF_UP);
        }
        catch (NumberFormatException ex) {
            checkErrorMath = true;
        }

        return bd.doubleValue();
    }

    public double StringToDouble(String input){
        double result = 0;
        String a = input.substring(0, input.length());
        boolean negative = false;
        boolean percent = false;
        boolean point = false;
        int pointIndex = 0;
        if(a.charAt(0) == '-'){
            negative = true;
            a = a.substring(1);
        }
        if(a.charAt(a.length()-1) == '%'){
            percent = true;
            a = a.substring(0,a.length()-1);
        }
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == '.'){
                point = true;
                pointIndex = i;
                break;
            }
        }
        if(point){
            String first = a.substring(0, pointIndex);
            String second = a.substring(pointIndex+1, a.length());
            try{
                double firstNum = Double.parseDouble(first);
                int secondLength = second.length();
                double secondNum = Double.parseDouble(second);
                secondNum = secondNum / Math.pow(10, secondLength);
                result = firstNum + secondNum;
            }catch (Exception e){
                checkError = true;
                Log.e("Point Error","Line 459");
            }
        }else{
            try{
                result = Double.parseDouble(a);
            }catch (Exception e){
                checkError = true;
                Log.e("Non Point Error","Line 469");
            }
        }
        if(percent){
            result = result / 100;
        }
        if(negative){
            result = result * -1;
        }
        return result;
    }

    //Check the orientation then change ContentView
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.landscape_layout);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }
    }


}