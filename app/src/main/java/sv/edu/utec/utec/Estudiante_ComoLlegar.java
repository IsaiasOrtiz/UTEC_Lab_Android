package sv.edu.utec.utec;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Estudiante_ComoLlegar extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, AdapterView.OnItemSelectedListener {
    WebView map;
    Spinner sp;
    TextView tv;
    ArrayList edificios=new ArrayList();
    ArrayList URL=new ArrayList();
    ArrayList ubicacion=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante__como_llegar);
        map = (WebView) findViewById(R.id.Mapa);
        sp=(Spinner)findViewById(R.id.spLabsUbicacion);
        tv=(TextView)findViewById(R.id.tvUbicar);

        HttpClient cl=new HttpClient(new OnHttpRequestComplete() {
            @Override
            public boolean onComplete(Response status) {
                if(status.isSuccess())
                {
                    try{
                        JSONObject Labs=new JSONObject(status.getResult());

                                for(int i=0;;i++)
                                {

                                    edificios.add(Labs.getJSONObject(""+i+"").get("Nombre"));
                                    URL.add(Labs.getJSONObject(""+i+"").get("Coordenada"));
                                    ubicacion.add("Edificio: "+Labs.getJSONObject(""+i+"").get("NEdificio")+"\nNivel: "+Labs.getJSONObject(""+i+"").get("Nivel"));



                                }

                    }
                    catch (JSONException e){}

                }
                return false;
            }
        });
        ip ips=new ip();
        cl.excecute("http://"+ips.Ip()+"/webservice/admin/Laboratorios.php");
        ArrayAdapter ar=new ArrayAdapter(this, android.R.layout.simple_spinner_item,edificios);
        sp.setAdapter(ar);

        sp.setOnItemSelectedListener(this);









    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

            map.getSettings().setJavaScriptEnabled(true);
            map.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
            map.setWebViewClient(new WebViewClient());
            map.loadUrl(URL.get(position).toString());
            tv.setText(ubicacion.get(position).toString());


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
