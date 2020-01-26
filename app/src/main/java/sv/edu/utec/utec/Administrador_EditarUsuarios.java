package sv.edu.utec.utec;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Administrador_EditarUsuarios extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spTipos,Carrera_profesion;
    ArrayList tipos=new ArrayList();
    ArrayList Carreras=new ArrayList();
    ArrayList Profesiones=new ArrayList();
    EditText Nombre,Apellido,Usuario,Clave,Confirmar,ID;
    ArrayList idU=new ArrayList();
    ArrayList nombreU=new ArrayList();
    ArrayList apellidosU=new ArrayList();
    ArrayList usuarioU=new ArrayList();
    ArrayList ClaveU=new ArrayList();
    ArrayList idCP=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador__editar_usuarios);
        Nombre=(EditText)findViewById(R.id.etNombre);
        Usuario=(EditText)findViewById(R.id.etUsuario);
        Clave=(EditText)findViewById(R.id.etClave);
        Confirmar=(EditText)findViewById(R.id.etConfirmarClave);
        ID=(EditText)findViewById(R.id.etID);



        spTipos=(Spinner)findViewById(R.id.spinner);
        Carrera_profesion=(Spinner)findViewById(R.id.spinner1);
        tipos.add("Estudiante");
        tipos.add("Administrador");
        tipos.add("Administrador Laboratorios");
        tipos.add("Desabilitado");

        ArrayAdapter tiposU=new ArrayAdapter(this, android.R.layout.simple_spinner_item,tipos);
        spTipos.setAdapter(tiposU);
        spTipos.setOnItemSelectedListener(this);
    }



    public boolean ValidarID()
    {
        boolean retorno=true;
        if((ID.getText().toString()).isEmpty())
        {
            ID.setError("Completar... Campo vacio.");
            retorno=false;
        }
    return retorno;
    }

    public void Cargar(View v)
    {
        if(ValidarID())
        {
            try
            {
                HttpClient clienteID=new HttpClient(new OnHttpRequestComplete() {
                    @Override
                    public boolean onComplete(Response status)
                    {
                        if(status.isSuccess())
                        {
                            try{
                                JSONObject user=new JSONObject(status.getResult());
                                Nombre.setText(user.getJSONObject("0").get("Nombre").toString());
                                Usuario.setText(user.getJSONObject("0").get("Usuario").toString());
                                Clave.setText(user.getJSONObject("0").get("Clave").toString());
                                Confirmar.setText(user.getJSONObject("0").get("Clave").toString());
                                String TipoU1=user.getJSONObject("0").get("Tipo").toString();
                                String CarreraUsuario=user.getJSONObject("0").get("NombreCP").toString();
                                if(TipoU1.equals("1"))
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
                                        ArrayAdapter AdProfesioness=new ArrayAdapter(Administrador_EditarUsuarios.this, android.R.layout.simple_spinner_item,Carreras);
                                        Carrera_profesion.setAdapter(AdProfesioness);
                                        spTipos.setSelection(0);
                                        for (int i=0;i<=Carreras.size();i++)
                                        {
                                            if((Carreras.get(i).equals(CarreraUsuario)))
                                            {
                                                Carrera_profesion.setSelection(i);
                                            }
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                    }



                                }
                                if(TipoU1.equals("2"))
                                {
                                    spTipos.setSelection(1);
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
                                        ArrayAdapter AdProfesioness=new ArrayAdapter(Administrador_EditarUsuarios.this, android.R.layout.simple_spinner_item,Carreras);
                                        Carrera_profesion.setAdapter(AdProfesioness);spTipos.setSelection(1);
                                        for (int i=0;i<=Carreras.size();i++)
                                        {
                                            if((Carreras.get(i).equals(CarreraUsuario)))
                                            {
                                                Carrera_profesion.setSelection(i);
                                            }
                                        }


                                    }
                                    catch (Exception e){}

                                }
                                if(TipoU1.equals("3"))
                                {
                                    spTipos.setSelection(2);

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
                                        ArrayAdapter AdProfesioness=new ArrayAdapter(Administrador_EditarUsuarios.this, android.R.layout.simple_spinner_item,Carreras);
                                        Carrera_profesion.setAdapter(AdProfesioness);spTipos.setSelection(1);
                                        for (int i=0;i<=Carreras.size();i++)
                                        {
                                            if((Carreras.get(i).equals(CarreraUsuario)))
                                            {
                                                Carrera_profesion.setSelection(i);
                                            }
                                        }


                                    }
                                    catch (Exception e){}


                                }
                                if(TipoU1.equals("4"))
                                {
                                    spTipos.setSelection(3);
                                    for (int i=0;i<=Carreras.size();i++)
                                    {
                                        if((Carreras.get(i).equals(CarreraUsuario)))
                                        {
                                            Carrera_profesion.setSelection(i);
                                        }
                                    }
                                }




                            }
                            catch (JSONException E)
                            {
                                Toast.makeText(Administrador_EditarUsuarios.this, "No existe usuario con este ID", Toast.LENGTH_SHORT).show();
                            }


                        }


                        return false;
                    }
                });
                ip ips=new ip();
                clienteID.excecute("http://"+(ips.Ip())+"/webservice/admin/UsuariosID.php?id="+(ID.getText().toString())+"");
            }
            catch (Exception e)
            {

            }

        }
    }
    public boolean Validar()
    {
        boolean retorno=true;
        if((ID.getText().toString()).isEmpty())
        {
            ID.setError("Completar... Campo vacio.");
            retorno=false;
        }
        if((Nombre.getText().toString()).isEmpty())
        {
            Nombre.setError("Completar... Campo vacio.");
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
                try{
                    HttpClient actualizarU=new HttpClient(new OnHttpRequestComplete() {
                        @Override
                        public boolean onComplete(Response status)
                        {
                            if(status.isSuccess())
                            {
                                if(status.getResult().equals("0"))
                                {
                                    ID.setError("El Id no coincide con el usuario...");
                                }
                                else
                                    {
                                        Toast.makeText(Administrador_EditarUsuarios.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                                    }
                            }
                            return false;
                        }
                    });

                    String tipodeU="0";
                    if(spTipos.getSelectedItem().equals("Estudiante"))
                    {
                        tipodeU="1";
                    }
                    if(spTipos.getSelectedItem().equals("Administrador"))
                    {
                        tipodeU="2";
                    }
                    if(spTipos.getSelectedItem().equals("Administrador Laboratorios"))
                    {
                        tipodeU="3";
                    }
                    if(spTipos.getSelectedItem().equals("Desabilitado"))
                    {
                        tipodeU="4";
                    }
                    int idc1=Carreras.indexOf(Carrera_profesion.getSelectedItem());
                    ip ips=new ip();

                    String url21="http://"+ips.Ip()+"/webservice/admin/ActualizarUsuarios.php?id="+ID.getText().toString()+"&nombre="+Nombre.getText().toString()+"&usuario="+Usuario.getText().toString()+"&clave="+Clave.getText().toString()+"&tipo="+tipodeU+"&CoP="+idCP.get(idc1).toString()+"";
                    String urlD=url21.replace(" ","-9-");
                    actualizarU.excecute(urlD);
                }
                catch (Exception e){}

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
