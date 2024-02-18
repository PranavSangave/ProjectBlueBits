package com.example.paymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    Button payBtn;
    private int planPrice = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payBtn = findViewById(R.id.payBtn);

        // Code for Razorpay
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planPrice != 0)
                    startPayment();
                else
                    showToast("Plan Not Selected !");

            }
        });
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_9gCpkhJrHxTWoT");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "DigiGrow");
            options.put("description", "Payment for XYZ");
            options.put("currency", "INR");
            options.put("amount", ""+(planPrice*100)); // Amount in paise
            options.put("prefill.email", "customer@example.com");
            options.put("prefill.contact", "9876543210");

            checkout.open(MainActivity.this, options);
        } catch (Exception e) {
            showToast("Error: " + e.getMessage());
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        showToast("Payment Successful!");
    }

    @Override
    public void onPaymentError(int i, String s) {
        showToast("Error Occurs : " + s);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}