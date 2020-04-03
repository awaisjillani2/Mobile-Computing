package pk.edu.pucit.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnDbl0;
    TextView btnMultiply,btnDivide,btnAdd,btnSubtract,btnPercentage,btnClear,btnDelete,btnEqual,btnDot;
    TextView input, output;
    boolean errorFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();
        clearTextViews();
        setOnClick();
        //input.addTextChangedListener(textWatcher);
    }

    private void clearTextViews()
    {
        this.input.setText("");
        this.output.setText("");
    }
    private void initializeButtons()
    {
        this.btn0=findViewById(R.id.btn0);
        this.btn1=findViewById(R.id.btn1);
        this.btn2=findViewById(R.id.btn2);
        this.btn3=findViewById(R.id.btn3);
        this.btn4=findViewById(R.id.btn4);
        this.btn5=findViewById(R.id.btn5);
        this.btn6=findViewById(R.id.btn6);
        this.btn7=findViewById(R.id.btn7);
        this.btn8=findViewById(R.id.btn8);
        this.btn9=findViewById(R.id.btn9);
        this.btnDbl0=findViewById(R.id.btnDbl0);
        this.btnMultiply=findViewById(R.id.btnMultiply);
        this.btnDivide=findViewById(R.id.btnDivide);
        this.btnAdd=findViewById(R.id.btnAdd);
        this.btnSubtract=findViewById(R.id.btnSubtract);
        this.btnPercentage=findViewById(R.id.btnPercentage);
        this.btnClear=findViewById(R.id.btnClear);
        this.btnDelete=findViewById(R.id.btnDelete);
        this.btnDot=findViewById(R.id.btnDot);
        this.btnEqual=findViewById(R.id.tv_calculate);
        this.input=findViewById(R.id.tv_equation);
        this.output=findViewById(R.id.tv_result);

    }
    private void setOnClick()
    {
        this.btn0.setOnClickListener(this);
        this.btn1.setOnClickListener(this);
        this.btn2.setOnClickListener(this);
        this.btn3.setOnClickListener(this);
        this.btn4.setOnClickListener(this);
        this.btn5.setOnClickListener(this);
        this.btn6.setOnClickListener(this);
        this.btn7.setOnClickListener(this);
        this.btn8.setOnClickListener(this);
        this.btn9.setOnClickListener(this);
        this.btnDbl0.setOnClickListener(this);
        this.btnMultiply.setOnClickListener(this);
        this.btnSubtract.setOnClickListener(this);
        this.btnAdd.setOnClickListener(this);
        this.btnDivide.setOnClickListener(this);
        this.btnPercentage.setOnClickListener(this);
        this.btnClear.setOnClickListener(this);
        this.btnDelete.setOnClickListener(this);
        this.btnDot.setOnClickListener(this);
        this.btnEqual.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        int id=v.getId();
        switch (id)
        {
            case R.id.btn0:
                append("0");
                break;
            case R.id.btn1:
                append("1");
                break;
            case R.id.btn2:
                append("2");
                break;
            case R.id.btn3:
                append("3");
                break;
            case R.id.btn4:
                append("4");
                break;
            case R.id.btn5:
                append("5");
                break;
            case R.id.btn6:
                append("6");
                break;
            case R.id.btn7:
                append("7");
                break;
            case R.id.btn8:
                append("8");
                break;
            case R.id.btn9:
                append("9");
                break;
            case R.id.btnDbl0:
                append("00");
                break;
            case R.id.btnAdd:
                if(endsWithOperator())
                    replace("+");
                else if(!isEmpty())
                    append("+");
                break;
            case R.id.btnMultiply:
                if(endsWithOperator())
                    replace("x");
                else if(!isEmpty())
                    append("x");
                break;
            case R.id.btnDivide:
                if(endsWithOperator())
                    replace("/");
                else if(!isEmpty())
                    append("/");
                break;
            case R.id.btnSubtract:
                if(endsWithOperator())
                    replace("-");
                else if(!isEmpty())
                    append("-");
                break;
            case R.id.btnPercentage:
                if(endsWithOperator())
                    replace("%");
                else if(!isEmpty())
                    append("%");
                break;
            case R.id.btnClear:
                clear();
                break;
            case R.id.btnDelete:
                delete();
                break;
            case R.id.btnDot:
                if(!getInput().endsWith("."))
                    append(".");
                break;
            case R.id.tv_calculate:
                calculate();
                //input.setText("");
                break;
            default:break;
        }
    }
    private void append(String str)
    {
        this.input.setText(this.input.getText()+str);
    }
    private void delete()
    {
        if(!isEmpty())
        {
            String origStr=this.input.getText().toString();
            String newStr=origStr.substring(0,origStr.length()-1);
            this.input.setText(newStr);
        }
        else
        {
            this.output.setText("");
        }
    }
    private void clear()
    {
        this.input.setText("");
        this.output.setText("");
    }
    private String getInput()
    {
        return this.input.getText().toString();
    }
    private boolean isEmpty()
    {
        return getInput().isEmpty();
    }
    private boolean endsWithOperator()
    {
        return getInput().endsWith("+") || getInput().endsWith("-") || getInput().endsWith("x") || getInput().endsWith("/") || getInput().endsWith("%");
    }
    private void replace(String str)
    {
        String oldStr= this.input.getText().toString();
        String newStr=oldStr.replace(oldStr.substring(oldStr.length() - 1),str);
        this.input.setText(newStr);
    }
    /*TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };*/
    private void calculate()
    {
        String eqn=getInput();
        try
        {
            if(!isEmpty() && !endsWithOperator())
            {
                if(eqn.contains("x"))
                {
                    eqn = eqn.replaceAll("x","*");
                }
                Expression expression=new ExpressionBuilder(eqn).build();
                Double result=expression.evaluate();
                output.setText(String.valueOf(result));
            }
            else if(endsWithOperator())
            {
                output.setText("Error!");
            }
        }
        catch (Exception e)
        {
            output.setText("Error!");
        }
    }

}
