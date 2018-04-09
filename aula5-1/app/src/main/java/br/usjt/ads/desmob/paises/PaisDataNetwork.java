package br.usjt.ads.desmob.paises;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Vinicius Maciel on 18/03/2018.
 * RA 816117960
 */

public class PaisDataNetwork {
    public static Pais[] buscarPaises(String url, String regiao) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ArrayList<Pais> paises = new ArrayList<>();

        Request request = new Request.Builder()
                .url(url+regiao)
                .build();

        Response response = client.newCall(request).execute();

        String resultado = response.body().string();

        try {
            JSONArray vetor = new JSONArray(resultado);
            for(int i = 0; i < vetor.length(); i++){
                JSONObject item = (JSONObject) vetor.get(i);
                Pais pais = new Pais();
                try {
                    pais.setArea(item.getInt("area"));
                } catch (Exception e){
                    pais.setArea(0);
                }
                try {
                    pais.setGini( item.getDouble( "gini" ) );
                }catch (Exception e){
                    pais.setGini(0.0);
                }
                try {
                    pais.setLatitude(item.getDouble("lat"));
                    pais.setLongitude(item.getDouble("lng"));
                }catch (Exception e){
                    pais.setLongitude(0.0);
                    pais.setLatitude(0.0);
                }

                pais.setBandeira(item.getString("flag"));
                pais.setCapital(item.getString("capital"));
                pais.setNome(item.getString("name"));
                pais.setRegiao(item.getString("region"));
                pais.setCodigo3(item.getString("alpha3Code"));
                pais.setPopulacao(item.getInt ("population"));
                pais.setSubRegiao(item.getString("subregion"));
                pais.setDemonimo(item.getString("demonym"));

                paises.add(pais);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return paises.toArray(new Pais[0]);
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
