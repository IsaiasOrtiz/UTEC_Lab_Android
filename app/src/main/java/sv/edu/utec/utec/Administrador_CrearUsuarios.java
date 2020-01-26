package sv.edu.utec.utec;

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

public class Administrador_CrearUsuarios extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spTipos,Carrera_profesion;
    ArrayList tipos=new ArrayList();
    ArrayList Carreras=new ArrayList();
    ArrayList idCP=new ArrayList();
    ArrayList Profesiones=new ArrayList();
    EditText Nombre,Apellido,Usuario,Clave,Confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_administrador__crear_usuarios);
        Nombre=(EditText)findViewById(R.id.etNombre);
        Apellido=(EditText)findViewById(R.id.etApellidos);
        Usuario=(EditText)findViewById(R.id.etUsuario);
        Clave=(EditText)findViewById(R.id.etClave);
        Confirmar=(EditText)findViewById(R.id.etConfirmarClave);


        spTipos=(Spinner)findViewById(R.id.spinner);
        Carrera_profesion=(Spinner)findViewById(R.id.spinner1);
        tipos.add("Estudiante");
        tipos.add("Administrador");
        tipos.add("Administrador Laboratorios");

        ArrayAdapter tiposU=new ArrayAdapter(this, android.R.layout.simple_spinner_item,tipos);
        spTipos.setAdapter(tiposU);
        spTipos.setOnItemSelectedListener(this);
    }
    public boolean Validar()
    {
        boolean retorno=true;
        if((Nombre.getText().toString()).isEmpty())
        {
            Nombre.setError("Completar... Campo vacio.");
            retorno=false;
        }
        if((Apellido.getText().toString()).isEmpty())
        {
            Apellido.setError("Completar... Campo vacio.");
            retorno=false;
        }
        if((Usuario.getText().toString()).isEmpty())
        {
            Usuario.setError("Completar... Campo vacio.");
            retorno=false;
        }
        if((Clave.getText().toString()).isEmpty())
        {
            Clave.setError("Completar... Campo vacio.");
            retorno=false;
        }
        if((Confirmar.getText().toString()).isEmpty())
        {
            Confirmar.setError("Completar... Campo vacio.");
            retorno=false;
        }

        return retorno;
    }
    ArrayList users=new ArrayList();
    public boolean validarPas()
    {
        boolean pass=true;

        if((Clave.getText().toString()).equals(Confirmar.getText().toString()))
        {

        }else
        {
            Clave.setError("Contraseñas no coinciden");
            Confirmar.setError("Contraseñas no coinciden");
            pass=false;
         }

        return pass;
    }

    public void guargar(View v)
    {
        if(Validar())
        {
            if(validarPas())
            {
               try
               {
                   HttpClient cliente=new HttpClient(new OnHttpRequestComplete() {
                       @Override
                       public boolean onComplete(Response status)
                       {
                       if(status.isSuccess())
                       {
                           if((status.getResult()).equals("0"))
                           {
                               Usuario.setError("Ya existe un usuario con este nombre...");
                           }else
                               {
                                   Toast.makeText(Administrador_CrearUsuarios.this, "Usuario agregado exitosamente :D", Toast.LENGTH_SHORT).show();
                               }
                       }
                           return false;
                       }
                   });
                   ip ips=new ip();
                   int pos=Carrera_profesion.getSelectedItemPosition();
                   String idCoP=idCP.get(pos).toString();
                   int tipo=4;
                   if(spTipos.getSelectedItem().toString().equals("Estudiante"))
                   {
                       tipo=1;
                   }
                   if(spTipos.getSelectedItem().toString().equals("Administrador Laboratorios"))
                   {
                       tipo=2;
                   }
                   if(spTipos.getSelectedItem().toString().equals("Administrador"))
                   {
                       tipo=3;
                   }

                   String url1="http://"+ips.Ip()+"/webservice/admin/CrearUsuario.php?nombre="+(Nombre.getText().toString())+" "+(Apellido.getText().toString())+"&user="+(Usuario.getText().toString())+"&clave="+(Clave.getText().toString())+"&tipo="+tipo+"&id_cop="+idCoP+"";
                   String url2=url1.replace(" ","-9-");
                   cliente.excecute(url2);

               }
               catch(Exception e)
               {

               }

            }


        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

             String Seleccion=parent.getItemAtPosition(position).toString();
             if(Seleccion.equals("Estudiante"))
             {

                 idCP.clear(); Carreras.clear();
                 try
                 {
                     HttpClient clienteCarreras=new HttpClient(new OnHttpRequestComplete() {
                         @Override
                         public boolean onComplete(Response status)
                         {
                             if(status.isSuccess())
                             {
                                 try {
                                     JSONObject carreras = new JSONObject(status.getResult());

                                     for (int i=0; ;i++) {
                                         Carreras.add(carreras.getJSONObject(""+i+"").get("Nombre").toString());
                                         idCP.add(carreras.getJSONObject(""+i+"").get("Id_CoP").toString());

                                     }



                                 }catch (JSONException E)
                                 {}




                             }

                             return false;
                         }
                     });
                     ip ips=new ip();
                     clienteCarreras.excecute("http://"+ips.Ip()+"/webservice/admin/Carreras.php");
                     ArrayAdapter AdProfesioness=new ArrayAdapter(this, android.R.layout.simple_spinner_item,Carreras);
                     Carrera_profesion.setAdapter(AdProfesioness);
                 }
                 catch (Exception e)
                 {

                 }



             }
             if(Seleccion.equals("Administrador") || Seleccion.equals("Administrador Laboratorios"))
             {
                 idCP.clear(); Carreras.clear();
                 try{
                     HttpClient clienteCarreras=new HttpClient(new OnHttpRequestComplete() {
                         @Override
                         public boolean onComplete(Response status)
                         {
                             if(status.isSuccess())
                             {
                                 try {
                                     JSONObject carreras = new JSONObject(status.getResult());

                                     for (int i=0; ;i++) {
                                         Carreras.add(carreras.getJSONObject(""+i+"").get("Nombre").toString());
                                         idCP.add(carreras.getJSONObject(""+i+"").get("Id_CoP").toString());

                                     }



                                 }catch (JSONException E)
                                 {}





                             }

                             return false;
                         }
                     });
                     ip ips=new ip();
                     clienteCarreras.excecute("http://"+ips.Ip()+"/webservice/admin/Profesiones.php");
                     ArrayAdapter AdProfesioness=new ArrayAdapter(this, android.R.layout.simple_spinner_item,Carreras);
                     Carrera_profesion.setAdapter(AdProfesioness);

                 }
                 catch (Exception e){}



             }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
