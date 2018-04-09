package br.usjt.ads.desmob.paises;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by Vinicius Maciel on 18/03/2018.
 * RA 816117960
 */

public class MainActivity extends Activity {
    Spinner spinnerContinente;
    String continente = "all";
    public static final String URL = "https://restcountries.eu/rest/v2/";
    public static final String PAISES = "br.usjt.ads.desmob.paises.pais";
    Pais[] paises;
    Intent intent;
    ProgressBar timer;
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerContinente = (Spinner) findViewById(R.id.spinnerContinente);
        spinnerContinente.setOnItemSelectedListener(new RegiaoSelecionada());
        timer = (ProgressBar)findViewById(R.id.timer);
        timer.setVisibility(View.INVISIBLE);
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    public void listarPaises(View view) {
        intent = new Intent(this, ListaPaisesActivity.class);
        if(PaisDataNetwork.isConnected(this)) {
            timer.setVisibility(View.VISIBLE);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                paises = PaisDataNetwork.buscarPaises(URL, continente);
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      intent.putExtra(PAISES, paises);
                                                      startActivity(intent);
                                                      timer.setVisibility(View.INVISIBLE);
                                                  }
                                              }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
        } else {
            Toast.makeText(this, "Rede inativa.", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    private class RegiaoSelecionada implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            continente = (String) parent.getItemAtPosition(position);
            if (continente.equals("Todos")) {
                continente = "all";
            } else {
                continente = "region/"+continente.toLowerCase();
            }
        }
        /**
         * Created by Vinicius Maciel on 18/03/2018.
         * RA 816117960
         */
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
