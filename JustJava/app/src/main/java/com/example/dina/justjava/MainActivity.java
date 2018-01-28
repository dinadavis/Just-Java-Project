package com.example.dina.justjava;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int priceCoffee = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the edit text field is filled.
     */
    public void NameField(Editable insertName) {
        EditText editName = (EditText) findViewById(R.id.name_view);
        String name = editName.getText().toString();
    }


    /**
     * This method is called when the checkbox for toppings is marked.
     */
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.whipped_checkbox:
                if (checked) ;
                    // Put whipped cream on top of the coffee
                else
                    // Dont add any whipped cream
                    break;
                Context contextWC = getApplicationContext();
                CharSequence textWC = getString(R.string.cost_one_dollar);
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(contextWC, getString(R.string.cost_one_dollar), Toast.LENGTH_SHORT).show();
        }
        switch(view.getId()){
            case R.id.cinnamon_checkbox:
                if (checked);
                    // Sprinkle cinnamon
                else
                    // No cinnamon
                    break;
                Context contextC = getApplicationContext();
                CharSequence textC = getString(R.string.cost_two_dollars);
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(contextC, getString(R.string.cost_two_dollars), Toast.LENGTH_SHORT).show();
                // TODO: Add toppings
        }
        displayMessage("$" + calculatePrice() + ".00");
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if(quantity>100){
            quantity=100;
        }
        displayQuantity(quantity);
        displayMessage("$" + calculatePrice() + ".00");

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity--;
        if(quantity<0){
            quantity=0;
        }
        displayQuantity(quantity);
        displayMessage("$" + calculatePrice() + ".00");
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editName = (EditText) findViewById(R.id.name_view);
        String name = editName.getText().toString();
        CheckBox onCheckboxClicked = (CheckBox) findViewById(R.id.whipped_checkbox);
        boolean hasWhippedCream = onCheckboxClicked.isChecked();
        CheckBox onCheckboxClickedtwo = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon = onCheckboxClickedtwo.isChecked();
        int price = calculatePrice();
        String priceMessage;
        priceMessage = createOrderSummary(name, calculatePrice(), hasWhippedCream, hasCinnamon);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: dinagdavis@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * return total price
     */
    private int calculatePrice() {
        int priceHasWhippedCream = 1;
        int priceHasCinnamon = 2;
        CheckBox onCheckboxClicked = (CheckBox) findViewById(R.id.whipped_checkbox);
        boolean hasWhippedCream = onCheckboxClicked.isChecked();
        CheckBox onCheckboxClickedtwo = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon = onCheckboxClickedtwo.isChecked();
        if (hasWhippedCream) {
            priceHasWhippedCream = 1;

        }
        else {
            priceHasWhippedCream = 0;
        }
        if (hasCinnamon) {
            priceHasCinnamon = 2;

        }
        else {
            priceHasCinnamon = 0;
        }
        return (priceHasWhippedCream + priceHasCinnamon + priceCoffee) * quantity ;
    }

    /**
     * creates an order summary
     *@param name returns name from edit text field
     * @param calculatePrice of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasCinnamon is whether or not the user wants cinnamon topping
     * @return text summary
     */
    private String createOrderSummary(String name, int calculatePrice, boolean hasWhippedCream, boolean hasCinnamon) {
        String priceMessage = getString(R.string.order_summary_name) + " " + name;
        priceMessage += "\n" + getString(R.string.order_summary_WC) + " " + hasWhippedCream;
        priceMessage += "\n" + getString(R.string.order_summary_C) + " " + hasCinnamon;
        priceMessage += "\n" + getString(R.string.order_summary_quantity) + " " + quantity;
        priceMessage += "\n" + getString(R.string.order_summary_total) + (calculatePrice()) + ".00";
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
