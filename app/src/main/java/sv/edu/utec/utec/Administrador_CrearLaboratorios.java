package sv.edu.utec.utec;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Administrador_CrearLaboratorios extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText Nombre,Coordenada,Capacidad;
    Spinner Edificio,Nivel,Admin;
    ArrayList EdificioAR=new ArrayList();
    ArrayList IdEdf=new ArrayList();
    ArrayList NivelesAr=new ArrayList();
    ArrayList IdUser=new ArrayList();
    ArrayList AdminsAr=new ArrayList();
    ArrayList Sotano=new ArrayList();
    ArrayList NivelesAr2=new ArrayList();
    ArrayList AdministradorL=new ArrayList();
    ip ips=new ip();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador__crear_laboratorios);

        Nombre=(EditText)findViewById(R.id.etNombre);
        Coordenada=(EditText)findViewById(R.id.etCoordenada);
        Capacidad=(EditText)findViewById(R.id.etCapacidad);
        Edificio=(Spinner)findViewById(R.id.SpEdificio);
        Nivel=(Spinner)findViewById(R.id.SpNivel);
        Admin=(Spinner)findViewById(R.id.spAdmin);
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

        try{
            HttpClient tipo2=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status)
                {
                    if(status.isSuccess())
                    {
                        try
                        {
                            JSONObject Users2=new JSONObject(status.getResult());
                            for (int i=0; ;i++)
                            {
                                IdUser.add(Users2.getJSONObject(""+i+"").get("IdUsuario"));
                                AdminsAr.add(Users2.getJSONObject(""+i+"").get("Nombre"));
                                Users2.remove(""+i+"");
                            }

                        }
                        catch (JSONException e){}
                    }

                    return false;
                }
            });
            tipo2.excecute("http://"+ips.Ip()+"/webservice/admin/UsuariosAdminLab.php");
        }
        catch (Exception e)
        {

        }
        ArrayAdapter adap2=new ArrayAdapter(this, android.R.layout.simple_spinner_item,AdminsAr);
        Admin.setAdapter(adap2);



    }
    public boolean validarVacio()
    {
        boolean val=true;

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
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
    public boolean validarSiExiste()
    {
        boolean val=true;
        return val;
    }

    public void guardar(View v)
    {
        if(validarVacio())
        {
            if(validarSiExiste())
            {
               try{
                   HttpClient inserL=new HttpClient(new OnHttpRequestComplete() {
                       @Override
                       public boolean onComplete(Response status) {
                           if(status.isSuccess())
                           {
                               Toast.makeText(Administrador_CrearLaboratorios.this, status.getResult(), Toast.LENGTH_SHORT).show();
                           }
                           return false;
                       }
                   });
                   ip ips=new ip();
                   String nombre=(Nombre.getText().toString()).replace(" ", "-9-");
                   int idEdificio=(EdificioAR.indexOf(Edificio.getSelectedItem()));
                   String IdEdfFinal=IdEdf.get(idEdificio).toString();

                   int administradorE=(AdminsAr.indexOf(Admin.getSelectedItem()));
                   String IdAdm=IdUser.get(administradorE).toString();
                   inserL.excecute("http://"+ips.Ip()+"/webservice/admin/CrearLaboratorio.php?nombre="+nombre+"&edificio="+IdEdfFinal+"&nivel="+(Nivel.getSelectedItem().toString())+"&url="+(Coordenada.getText().toString())+"&capacidad="+(Capacidad.getText().toString())+"&admin="+IdAdm+"");
               }
               catch (Exception e){}

            }
        }

    }
}
