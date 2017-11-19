package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
public class MainActivity extends ActionBarActivity {

    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        if (quantity==0){
            Toast.makeText(this,"Order Something !!", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText nameTextBar=(EditText)findViewById(R.id.name_text_bar);
        String name = nameTextBar.getText().toString();

        if ((name==null)|| (name.isEmpty())){
            Toast.makeText(this,"Type Your Name Please !!", Toast.LENGTH_SHORT).show();
            return;
        }


        CheckBox whippedCreamCheckBox=(CheckBox)findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedcream = whippedCreamCheckBox.isChecked();

//        Log.v("MainActivity", "Has whipped cream: "+hasWhippedcream);

        CheckBox chocolateCheckBox=(CheckBox)findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price=calculatePrice(hasWhippedcream,hasChocolate);
        String priceMessage=createOrderSummary(price,hasWhippedcream,hasChocolate,name);

      //  displayMessage(priceMessage);

        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " +name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int coffeePrice=5;

        if(addWhippedCream) {
            coffeePrice+=1;
        }
        if(addChocolate){
            coffeePrice+=2;
        }

        return coffeePrice*quantity;
    }

    private String createOrderSummary(int price, boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage="Name: " +name+
                    "\nAdd Whipped Cream: "+addWhippedCream +
                    "\nAdd Chocolate: "+addChocolate +
                    "\nQuantity: "+quantity +
                    "\nTotal: $" + price +
                    "\nThank You!";
        return priceMessage;
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
            display(++quantity);


    }

    public void decrement(View view){
        if (quantity==0) {
            Toast.makeText(this, "No negative orders please !!!", Toast.LENGTH_SHORT).show();
            return;
        }


        if (quantity==1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        display(--quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView displaySummaryTextView = (TextView) findViewById(R.id.summary_text_view);
//        displaySummaryTextView.setText(message);
//    }
}