package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText dni;
    Button consultar;
    TextView nom,dir,fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dni = (EditText) findViewById(R.id.editTextDni);
        nom= (TextView) findViewById(R.id.editTextNombre);
        dir= (TextView) findViewById(R.id.editTextDirec);
        fecha= (TextView) findViewById(R.id.editTextFec);
        consultar= (Button) findViewById(R.id.button);
        
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarPersona();
            }
        });
    }

    public Connection conexionBD(){
        Connection cnn= null;
        try {
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.43.225;databaseName=DBtienda;user=sa;password=123456;");
            //cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.85;databaseName=DBtienda;user=sa;password=123456;");
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.85;databaseName=Develop;user=sa;password=123456;");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cnn;
    }

    public void consultarPersona(){

        try {
            Statement stm = conexionBD().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM persona WHERE dni = '" + dni.getText().toString() + "'");

            if (rs.next()){
                nom.setText(rs.getString(2));
                dir.setText(rs.getString(3));
                fecha.setText(rs.getString(4));
            }
            dni.setText("");
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
