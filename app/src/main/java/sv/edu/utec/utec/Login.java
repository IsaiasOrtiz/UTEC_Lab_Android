package sv.edu.utec.utec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText Usuario;
    EditText Pass;
    ip ips=new ip();
    SharedPreferences sharedpreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Usuario=(EditText)findViewById(R.id.etUsuario);
        Pass=(EditText)findViewById(R.id.etClave);
        sharedpreferences = getSharedPreferences("Val", Context.MODE_PRIVATE);

        final String SesionUser= sharedpreferences.getString("Usuario","NULL");
        final String SessionPas= sharedpreferences.getString("Clave","NULL");
        final Intent i=new Intent(this, Administrador.class);
        final Intent i2=new Intent(this, Laboratorios.class);
        final Intent i3=new Intent(this, Estudiante.class);

        if(SesionUser.equals("NULL") && SessionPas.equals("NULL"))
        {



        }else
            {
                try
                {
                    HttpClient client =new HttpClient(new OnHttpRequestComplete() {
                        @Override
                        public boolean onComplete(Response status) {
                            if(status.isSuccess())
                            {

                                try
                                {



                                    JSONObject usuarios=new JSONObject(status.getResult());
                                    String usuarioJ=usuarios.getJSONObject("0").get("Usuario").toString();
                                    String claveJ=usuarios.getJSONObject("0").get("Clave").toString();
                                    int tipo=Integer.parseInt(usuarios.getJSONObject("0").get("Tipo").toString());
                                    if(usuarioJ.equals(SesionUser) && claveJ.equals(SessionPas))
                                    {

                                        usuarios.remove("0");



                                        if(tipo==1)
                                        {
                                            startActivity(i3);
                                            finish();
                                        }
                                        if(tipo==2)
                                        {
                                            startActivity(i2);
                                            finish();
                                        }
                                        if(tipo==3)
                                        {
                                            startActivity(i);
                                            finish();
                                        }


                                    }
                                    else{
                                        Toast.makeText(Login.this, "No coincide", Toast.LENGTH_SHORT).show();
                                    }

                                }catch (JSONException e)
                                {
                                    Toast.makeText(Login.this, e.toString(), Toast.LENGTH_SHORT).show();
                                }



                            }

                            return false;
                        }
                    });


                    String user=Usuario.getText().toString();
                    String cla=Pass.getText().toString();
                    String ip1=ips.Ip();
                    String Url="http://"+ip1+"/WebService/login.php?usuario="+SesionUser+"&clave="+SessionPas+"";
                    client.excecute(Url);


                }catch(Exception e)
                {

                }

            }








    }

    public boolean ValidarVacio()
    {
        boolean val=true;

        if((Usuario.getText().toString()).isEmpty())
        {
            Usuario.setError("Debe ingresar un usuario");
            val=false;
        }
        if((Pass.getText().toString()).isEmpty())
        {
            Pass.setError("Debe ingresar una Clave");
            val=false;
        }

        return val;
    }
    public void Ingresar(View view)
    {

        final Intent i=new Intent(this, Administrador.class);
        final Intent i2=new Intent(this, Laboratorios.class);
        final Intent i3=new Intent(this, Estudiante.class);
        Context cn=this;
        /*SharedPreferences sesion= getSharedPreferences("Sesion",cn.MODE_PRIVATE);
        SharedPreferences SesionA= getPreferences(cn.MODE_PRIVATE);
        final SharedPreferences.Editor editor=SesionA.edit();*/




        if(ValidarVacio())
        {

            try
            {
                HttpClient client =new HttpClient(new OnHttpRequestComplete() {
                    @Override
                    public boolean onComplete(Response status) {
                        if(status.isSuccess())
                        {

                            try
                            {



                                JSONObject usuarios=new JSONObject(status.getResult());
                                String usuarioJ=usuarios.getJSONObject("0").get("Usuario").toString();
                                String NombreJ=usuarios.getJSONObject("0").get("Nombre").toString();
                                String ID=usuarios.getJSONObject("0").get("IdUsuario").toString();
                                String claveJ=usuarios.getJSONObject("0").get("Clave").toString();
                                int tipo=Integer.parseInt(usuarios.getJSONObject("0").get("Tipo").toString());
                                if(usuarioJ.equals(Usuario.getText().toString()) && claveJ.equals(Pass.getText().toString()))
                                {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString("Usuario", usuarioJ);
                                    editor.putString("Clave", claveJ);
                                    editor.putString("Nombre", NombreJ);
                                    editor.putString("ID",ID);
                                    editor.commit();
                                    /*editor.putString("USUARIO",usuarioJ);
                                    editor.commit();
                                    editor.putString("CLAVE",claveJ);
                                    editor.commit();*/
                                    usuarios.remove("0");



                                    if(tipo==1)
                                    {
                                        startActivity(i3);
                                        finish();
                                    }
                                    if(tipo==2)
                                    {
                                        startActivity(i2);
                                        finish();
                                    }
                                    if(tipo==3)
                                    {
                                        startActivity(i);
                                        finish();
                                    }


                                }
                                else{
                                    Toast.makeText(Login.this, "No coincide", Toast.LENGTH_SHORT).show();
                                }

                            }catch (JSONException e)
                            {
                                Toast.makeText(Login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            }



                        }

                        return false;
                    }
                });


                String user=Usuario.getText().toString();
                String cla=Pass.getText().toString();
                String ip1=ips.Ip();
                String Url="http://"+ip1+"/WebService/login.php?usuario="+user+"&clave="+cla+"";
                client.excecute(Url);


            }catch(Exception e)
            {

            }


        }



    }
}
