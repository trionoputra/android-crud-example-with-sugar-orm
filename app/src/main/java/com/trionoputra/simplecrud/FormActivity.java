package com.trionoputra.simplecrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trionoputra.simplecrud.object.People;

public class FormActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtEmail;
    private EditText txtAddress;
    private People people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            people = People.findById(People.class, extras.getLong("data")); // get data for update
            txtName.setText(people.getName());  // init update data
            txtEmail.setText(people.getEmail());
            txtAddress.setText(people.getAddress());
        }



        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save()
    {
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String address = txtAddress.getText().toString();

        if(!name.equals("") && !email.equals("") && !address.equals(""))
        {

            if(people == null)
            {
                people = new People(name, email, address); // new people data set by constructor
                people.save(); // save new data
                reset(); // reset form
            }
            else
            {
                // update existing data
                people.setName(name);
                people.setEmail(email);
                people.setAddress(address);
                people.save(); //  update data
            }



            Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Field Cannot be Empty!",Toast.LENGTH_SHORT).show();
        }

    }

    private void reset()
    {
        txtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
    }

}
