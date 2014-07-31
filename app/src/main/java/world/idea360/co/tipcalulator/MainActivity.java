// MainActivity.java
// calculates tips 15 - 30 percent
package world.idea360.co.tipcalulator;

import java.text.NumberFormat;
import java.util.Currency;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

//MainActivity class for tip calculator
public class MainActivity extends Activity
{
    // Currency and percentage formatters.
    private static final
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final
        NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double billAmount = 0.0;
    private double customPercent = 0.18;
    private TextView amountDisplayTextView;
    private TextView percentCustomTextView;
    private TextView customPercentTextView;
    private TextView tip15TextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;

    //called when first activity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the references to the textviews
        //that the mainactivity interacts with.
        amountDisplayTextView =
                (TextView) findViewById(R.id.amountDisplayTextView);
        percentCustomTextView =
                (TextView) findViewById(R.id.percentCustomTextView);
        tip15TextView = (TextView) findViewById(R.id.tip15TextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.tipCustomTextView);
        totalCustomTextView =
                (TextView) findViewById(R.id.totalCustomTextView);
        customPercentTextView = (TextView) findViewById(R.id.customPercentTextView);

        //Update GUI based on bill amount and custom percent.
        amountDisplayTextView.setText(
                currencyFormat.format(billAmount));
        updateStandard();
        updateCustom();

        //Set the amountEditText's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar customTipSeekBar =
                (SeekBar) findViewById(R.id.customTipSeekBar);
        customTipSeekBar.setOnSeekBarChangeListener(customTipSeekBarListener);
     } //end on create.


    //update 15% tip feilds
    private void updateStandard()
    {
        //cal the difference on 15% tip
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        //Display the results.
        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
    }

    //Updates the custom tips.
    private  void updateCustom()
    {
        //show the custom percent, formatted.
        percentCustomTextView.setText(percentFormat.format(customPercent));
        customPercentTextView.setText(percentFormat.format(customPercent));

        //Calculate the custom tip and total
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        //Display tip and totals
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    }//end updateCustom

    //called when the user changes the seekbar
    private OnSeekBarChangeListener customTipSeekBarListener =
            new OnSeekBarChangeListener()
            {
                //update custom percent the call updateCustom.
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser)
                {
                    //set custom percentage to position of the seekbar.
                    customPercent = progress / 100.00;
                    updateCustom();//update the custom tip TextViews;
                } //end

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                }
            };

    //event handeling object that responds to amountTextEditText's events.
    private TextWatcher amountEditTextWatcher = new TextWatcher()
    {
        //Called when the user enters a number.
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count)
        {
            //convert amountEditText's text to a double.
            try
            {
             billAmount = Double.parseDouble(s.toString()) / 100;
            } //end try
            catch (NumberFormatException e)
            {
                billAmount = 0.0; //default if exception occurs.
            }

            //display currency formatted bill amount.
            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard();
            updateCustom();
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {
        }
    };
}
