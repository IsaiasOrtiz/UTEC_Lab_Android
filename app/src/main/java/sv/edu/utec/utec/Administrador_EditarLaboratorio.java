package sv.edu.utec.utec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Administrador_EditarLaboratorio extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText Nombre,Coordenada,Capacidad,ID;
    Spinner Edificio,Nivel,Admin;
    ArrayList EdificioAR=new ArrayList();
    ArrayList NivelesAr=new ArrayList();
    ArrayList AdminsAr=new ArrayList();
    ArrayList NivelesAr2=new ArrayList();
    ArrayList AdministradorL=new ArrayList();
    ArrayList idLab=new ArrayList();
    ArrayList NombreAr=new ArrayList();
    ArrayList EdificioArAct=new ArrayList();
    ArrayList NivelAr=new ArrayList();
    ArrayList CoordenadaAr=new ArrayList();
    ArrayList CapacidadAr=new ArrayList();
    ArrayList AdministradorAr=new ArrayList();
    ArrayList Labs=new ArrayList();
    ArrayList IdLab=new ArrayList();
    ArrayList Sotano=new ArrayList();
    ArrayList  IdUser=new ArrayList();
    ArrayList IdEdf=new ArrayList();

    ip ips=new ip();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador__editar_laboratorio);
        Nombre=(EditText)findViewById(R.id.etNombre);
        Coordenada=(EditText)findViewById(R.id.etCoordenada);
        Capacidad=(EditText)findViewById(R.id.etCapacidad);
        Edificio=(Spinner)findViewById(R.id.SpEdificio);
        Nivel=(Spinner)findViewById(R.id.SpNivel);
        ID=(EditText) findViewById(R.id.etID);
        Edificio.setOnItemSelectedListener(this);



        try
        {
            HttpClient cliente1=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status)
                {
                    if(status.isSuccess())
                    {

                        try{
                            JSONObject NombresEdf=new JSONObject(status.getResult());
                            for(int i=0 ;; i++)
                            {
                                NivelesAr.add(NombresEdf.getJSONObject(""+i+"").get("Niveles"));
                                Sotano.add(NombresEdf.getJSONObject(""+i+"").get("Sotano"));
                                IdEdf.add(NombresEdf.getJSONObject(""+i+"").get("ID_EDF"));
                                EdificioAR.add(NombresEdf.getJSONObject(""+i+"").get("Nombre"));
                            }

                        }
                        catch (JSONException E){}
                    }
                    return false;
                }
            });

            cliente1.excecute("http://"+ips.Ip()+"/webservice/admin/ListaEdificios.php");
        }
        catch (Exception e){}

        ArrayAdapter adap=new ArrayAdapter(this, android.R.layout.simple_spinner_item,EdificioAR);
        Edificio.setAdapter(adap);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if((parent.getId())==R.id.SpEdificio)
        {
            int nivelesI=Integer.parseInt(NivelesAr.get(position).toString());
            int SotanoI=Integer.parseInt(Sotano.get(position).toString());
            NivelesAr2.clear();

            for(int i=SotanoI; i<=nivelesI;i++)
            {
                NivelesAr2.add(i);
            }
            ArrayAdapter NivelesAd=new ArrayAdapter(this, android.R.layout.simple_spinner_item,NivelesAr2);
            Nivel.setAdapter(NivelesAd);



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean validarVacio()
    {
        boolean val=true;
        if((ID.getText().toString()).isEmpty())
        {
            ID.setError("Completar campo vacio");
            val=false;
        }

        if((Nombre.getText().toString()).isEmpty())
        {
            Nombre.setError("Completar campo vacio");
            val=false;
        }
        if((Coordenada.getText().toString()).isEmpty())
        {
            Coordenada.setError("Completar campo vacio");
            val=false;
        }
        if((Capacidad.getText().toString()).isEmpty())
        {
            Capacidad.setError("Completar campo vacio");
            val=false;
        }

        return val;
    }
  
    public  void Cargar(View v)
    {
        if((ID.getText().toString()).isEmpty())
        {
            ID.setError("Ingrese un ID");
        }else
            {
                try{
                    HttpClient cargarC=new HttpClient(new OnHttpRequestComplete() {
                        @Override
                        public boolean onComplete(Response status) {
                            if(status.isSuccess())
                            {
                                if(status.getResult().equals("0"))
                                {
                                    ID.setError("No se encontro laboratorio con este Id.");
                                }else
                                    {
                                        try{
                                            JSONObject Labs=new JSONObject(status.getResult());
                                            Nombre.setText(Labs.getJSONObject("0").get("Nombre").toString());
                                            int idc33=IdEdf.indexOf((Labs.getJSONObject("0").get("ID_EDF")));
                                            Edificio.setSelection(idc33);
                                            Coordenada.setText(Labs.getJSONObject("0").get("Coordenada").toString());
                                            Capacidad.setText(Labs.getJSONObject("0").get("capacidad").toString());
                                            Labs.remove("0");
                                            ID.setFocusable(false);
                                        }
                                        catch (JSONException E)
                                        {}
                                    }
                            }
                            return false;
                        }
                    });
                    cargarC.excecute("http://"+ips.Ip()+"/webservice/admin/LaboratoriosXid.php?ID="+ID.getText().toString()+"");
                }
                catch (Exception e){}
                
                
            }
        
    }
    

    public void guardar(View v)
    {
        if(validarVacio())
        {
              HttpClient actualiza=new HttpClient(new OnHttpRequestComplete() {
                  @Override
                  public boolean onComplete(Response status) {
                      if(status.isSuccess())
                      {
                          Toast.makeText(Administrador_EditarLaboratorio.this, status.getResult(), Toast.LENGTH_SHORT).show();


                      }
                      return false;
                  }
              });
              int idc12=EdificioAR.indexOf(Edificio.getSelectedItem().toString());
              String urlR="http://"+ips.Ip()+"/webservice/admin/ActualizarLaboratorio.php?nombre="+Nombre.getText().toString()+"&edificio="+IdEdf.get(idc12)+"&nivel="+Nivel.getSelectedItem().toString()+"&url="+Coordenada.getText().toString()+"&capacidad="+Capacidad.getText().toString()+"&ID_Lab="+ID.getText().toString()+"";
              String newUrl=urlR.replace(" ","-9-");
              actualiza.excecute(newUrl);
        }

    }
}
