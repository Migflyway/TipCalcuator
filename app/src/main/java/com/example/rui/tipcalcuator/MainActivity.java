/*
package com.example.rui.tipcalcuator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
*/

package com.example.rui.tipcalcuator;


import android.os.Bundle;
import android.util.LogPrinter;
import android.view.Menu;
import android.view.MenuItem;
import java.text.NumberFormat;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends Activity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private double billAmount = 0.0;
    private double customPercent = 0.18;
    private double customTax = 0.00;
    private double preTax = 0.00;
    private TextView amountDisplayTextView;
    private TextView percentCustomTextView;
    private TextView tip15TextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;

    private TextView perTaxAmountTextView;
    private TextView percentCustomTsxTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView = (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);
        amountDisplayTextView.setText(currencyFormat.format(billAmount));

        perTaxAmountTextView = (TextView) findViewById(R.id.perTaxAmountTextView);
        percentCustomTsxTextView = (TextView) findViewById(R.id.percentCustomeTaxTextView);

        updateStandard();
        updateCustom();
        updatePreTax();

        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);
        SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

        SeekBar TaxSeekBar = (SeekBar) findViewById(R.id.TaxSeekBar);
        TaxSeekBar.setOnSeekBarChangeListener(taxSeekBarLinstener);
    }
    private void updateStandard()
    {
        //double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTip = preTax * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        amountDisplayTextView.setText(currencyFormat.format(billAmount));
    }
    private void updateCustom()
    {
        percentCustomTextView.setText(percentFormat.format(customPercent));
        //double customTip = billAmount * customPercent;
        double customTip = preTax * customPercent;
        double customTotal = billAmount + customTip;
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    }

    private void  updateSalesTax(){

       //percentCustomTsxTextView.setText(percentFormat.format(customTax));
       percentCustomTsxTextView.setText(String.format("%.2f %%",customTax));
    }

    private void updatePreTax(){
        preTax = billAmount/(1+(customTax/100));
        perTaxAmountTextView.setText(currencyFormat.format(preTax));
    }
    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener()
    {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            customPercent = progress / 100.0;
            updateCustom();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    };

    private OnSeekBarChangeListener taxSeekBarLinstener = new OnSeekBarChangeListener()
    {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            //customTax = progress / 100.0;
            customTax = progress * 0.25;
            updateSalesTax();
            updatePreTax();

            updateStandard();
            updateCustom();

        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    };


    private TextWatcher amountEditTextWatcher = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try
            {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
            }
            catch (NumberFormatException e)
            {
                billAmount = 100.0;
            }
            updateStandard();
            updateCustom();
            updatePreTax();
            updateStandard();

        }
        @Override
        public void afterTextChanged(Editable s)
        {

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }
    };

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