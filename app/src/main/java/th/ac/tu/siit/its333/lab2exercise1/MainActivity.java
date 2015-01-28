package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;
    int result =0;
    int mem =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void updateAnsDisplay() {
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(result));
    }

    public void recalculate() {
        //Calculate the expression and display the output

        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");


        String op = "";

        if (tokens.length % 2 == 1) {

            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")) {
                    op = tokens[i];
                } else {
                    if (op.equals("+")) {
                        result = result + Integer.parseInt(tokens[i]);
                    }
                    else if (op.equals("-")){
                        result = result - Integer.parseInt(tokens[i]);
                    }
                    else if (op.equals("*")){
                        result = result * Integer.parseInt(tokens[i]);
                    }
                    else if (op.equals("/")){
                        result = result / Integer.parseInt(tokens[i]);
                    }
                    else{
                        result = Integer.parseInt(tokens[i]);
                    }
                }
            }
        }

        updateAnsDisplay();
    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        if(expr.toString().endsWith("0") && expr.length()==1){
            expr.deleteCharAt(0);
        }
        if (expr.toString().endsWith("0") && (expr.toString().charAt(expr.length()-2) == '+' || expr.toString().charAt(expr.length()-2) == '-'|| expr.toString().charAt(expr.length()-2) == '*'|| expr.toString().charAt(expr.length()-2) == '/')){
            expr.deleteCharAt(expr.length()-1);
        }
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void operatorClicked(View v) {
        //IF the last character in expr is not an operator and expr is not "",
        //THEN append the clicked operator and updateExprDisplay,
        //ELSE do nothing
       String d = ((TextView)v).getText().toString();
        String temp_expr = expr.toString();
        if(!temp_expr.endsWith("+") && !temp_expr.endsWith("-") && !temp_expr.endsWith("*") && !temp_expr.endsWith("/") && !temp_expr.isEmpty() ) {
            expr.append(d);
            updateExprDisplay();
        }
    }

    public void equalClicked(View v) {
        recalculate();
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        expr = new StringBuffer(tvAns.getText());
        result =0;
        updateExprDisplay();
        updateAnsDisplay();
    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();
        updateExprDisplay();
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();
        result=0;
        updateAnsDisplay();
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);
            recalculate();
            updateExprDisplay();
        }
    }

    public void MPlusClicked(View v){
        mem = mem+result;
        String s = "Mem =" + mem;
        Toast t = Toast.makeText(this.getApplicationContext(),
                s, Toast.LENGTH_SHORT);
        t.show();
    }

    public void MMinusClicked(View v){
        mem = mem-result;
        String s = "Mem =" + mem;
        Toast t = Toast.makeText(this.getApplicationContext(),
                s, Toast.LENGTH_SHORT);
        t.show();
    }

    public void MRClicked(View v){

        expr = new StringBuffer(Integer.toString(mem));
        result = mem;
        updateExprDisplay();
        updateAnsDisplay();
    }

    public void MCClicked(View v){
        mem = 0;
        String s = "Mem =" + mem;
        Toast t = Toast.makeText(this.getApplicationContext(),
                s, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
