package com.example.mateu.buscafilmes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button Busca;
    private Button BuscaPadrao;

    public static final String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Busca = (Button) findViewById(R.id.Buscar);
        BuscaPadrao = (Button) findViewById(R.id.BuscaPadrao);

        Busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Carregando filme", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ResutadoBusca.class);

                EditText editText = (EditText) findViewById(R.id.editText);
                String message = editText.getText().toString();

                intent.putExtra(EXTRA_MESSAGE, message);
                // A implementar a busca personalizada.
                startActivity(intent);
            }
        });

        BuscaPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Carregando filme", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, ResutadoBusca.class);
                EditText editText = (EditText) findViewById(R.id.editText);
                String message = editText.getText().toString();

                intent.putExtra(EXTRA_MESSAGE, "avengers");
                startActivity(intent);
            }
        });

        //startActivity(new Intent(MainActivity.this , ResutadoBusca.class));
    }
}