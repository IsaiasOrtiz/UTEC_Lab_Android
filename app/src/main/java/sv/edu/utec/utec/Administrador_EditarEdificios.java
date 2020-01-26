package sv.edu.utec.utec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.ArrayList;

public class Administrador_EditarEdificios extends AppCompatActivity {

    TextView ID,Nombre,Direccion,Niveles;
    RadioButton rdSotano2,rdSotano;
    ArrayList idAR=new ArrayList();
    ArrayList nombreAR=new ArrayList();
    ArrayList nivelesAR=new ArrayList();
    ArrayList DireccionAR=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador__editar_edificios);
        ID=(TextView)findViewById(R.id.etIdEditaredificios);
        Nombre=(TextView)findViewById(R.id.etNombre);
        Direccion=(TextView)findViewById(R.id.tvDireccion);
        Niveles=(TextView)findViewById(R.id.EtNivelesEDF);
        rdSotano=(RadioButton)findViewById(R.id.rdHabilitar);
        rdSotano2=(RadioButton)findViewById(R.id.rdDesabilitar);
        for(int i=0; i<100;i++)
        {
            idAR.add(i);
            nombreAR.add("Benito Juarez "+i);
            nivelesAR.add("5");
            DireccionAR.add("Calle arce #"+i);
        }

        String Seleccion;
        if(rdSotano.isChecked())
        {
            Seleccion="Habilitar";
        }else {Seleccion="Desabilitar";}




    }

    public void guardar(View v)
    {
     if(ValidarVacio())
     {
         try{
             HttpClient clientAc=new HttpClient(new OnHttpRequestComplete() {
                 @Override
                 public boolean onComplete(Response status) {
                     if(status.isSuccess())
                     {
                         Toast.makeText(Administrador_EditarEdificios.this, "actualizado con exito", Toast.LENGTH_SHORT).show();
                     }
                     return false;
                 }
             });
             String sotano="null";
             if(rdSotano.isChecked())
             {
                 sotano="0";
             }
             if(rdSotano2.isChecked())
             {
                 sotano="1";
             }
             ip ipss=new ip();
             String url="http://"+ipss.Ip()+"/webservice/admin/ActualizarEdificios.php?id="+ID.getText().toString()+"&nombre="+Nombre.getText().toString()+"&niveles="+Niveles.getText().toString()+"&sotano="+sotano+"&direccion="+Direccion.getText().toString()+"";
             String url2=url.replace(" ", "-9-");
             clientAc.excecute(url2);
         }
         catch (Exception e){}
     }

    }
    public void Cargar(View v)
    {
        if(ValidarVacioID())
        {
           try {
               HttpClient clienteEdf=new HttpClient(new OnHttpRequestComplete() {
                   @Override
                   public boolean onComplete(Response status) {
                       if(status.isSuccess())
                       {
                           try {
                               JSONObject Edificios = new JSONObject(status.getResult());
                               Nombre.setText(Edificios.getJSONObject("0").get("Nombre").toString());
                               Niveles.setText(Edificios.getJSONObject("0").get("Niveles").toString());
                               Direccion.setText(Edificios.getJSONObject("0").get("Direccion").toString());
                               String sotano=Edificios.getJSONObject("0").get("Sotano").toString();
                               ID.setFocusable(false);
                               if(sotano.equals("0"))
                               {
                                   rdSotano2.setChecked(true);
                               }else
                                   {
                                       rdSotano.setChecked(true);
                                   }
                           }catch (JSONException E)
                           {

                           }

                       }
                       return false;
                   }
               });
               ip ips=new ip();
               clienteEdf.excecute("http://"+ips.Ip()+"/webservice/admin/EdificiosID.php?ID="+ID.getText().toString()+"");
           }catch (Exception e)
           {

           }

        }

    }


    public boolean ValidarVacioID()
    {
        boolean is=true;
        if((ID.getText().toString()).isEmpty())
        {
            ID.setError("Campo Vacio!");
            is=false;
        }

        return is;
    }
    public boolean ValidarVacio()
    {
        boolean is=true;
        if((ID.getText().toString()).isEmpty())
        {
            ID.setError("Campo Vacio!");
            is=false;
        }
        if((Nombre.getText().toString()).isEmpty())
        {
            Nombre.setError("Campo vacio...");
            is=false;
        }
        if((Direccion.getText().toString()).isEmpty())
        {
            Direccion.setError("Campo vacio...");
            is=false;
        }
        if((Niveles.getText().toString()).isEmpty())
        {
            Niveles.setError("Campo vacio...");
            is=false;
        }

        return is;
    }
}
