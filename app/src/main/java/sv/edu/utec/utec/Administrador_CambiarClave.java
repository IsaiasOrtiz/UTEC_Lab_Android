package sv.edu.utec.utec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Administrador_CambiarClave extends AppCompatActivity {

    EditText clave,nueva,confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador__cambiar_clave);
        clave=(EditText)findViewById(R.id.etClaveAnterior);
        nueva=(EditText)findViewById(R.id.etNueva);
        confirmar=(EditText)findViewById(R.id.etConfirmar);
    }

    public boolean validarVacio()
    {
        boolean retorno=true;

        if((clave.getText().toString()).isEmpty())
        {
            retorno = false;
            clave.setError("Campo vacio... Completar");
        }
        if((confirmar.getText().toString()).isEmpty())
        {
            retorno = false;
            confirmar.setError("Campo vacio... Completar");
        }
        if((nueva.getText().toString()).isEmpty())
        {
            retorno = false;
            nueva.setError("Campo vacio... Completar");
        }


        return  retorno;
    }

    public boolean validarConfirmacion()
    {
        boolean retorno=true;

        if((nueva.getText().toString()).equals(confirmar.getText().toString()))
        {

        }else
            {
                retorno = false;
                nueva.setError("No coincicen");
                confirmar.setError("No coinciden");
            }
        return  retorno;
    }



    public boolean validarIguales()
    {
        boolean retorno=true;

        if((clave.getText().toString()).equals(nueva.getText().toString()))
        {
            retorno = false;
            nueva.setError("La clave que intenta cambiar es la misma");

            clave.setError("La clave que intenta cambiar es la misma.");
        }
        return  retorno;
    }

    SharedPreferences sharedpreferences;




    public void Actualizar(View v)
    {
        sharedpreferences = getSharedPreferences("Val", Context.MODE_PRIVATE);

        final String SesionUser= sharedpreferences.getString("Usuario","NULL");
        String SessionPas= sharedpreferences.getString("Clave","NULL");
        if(validarVacio())
        {
            if(validarConfirmacion())
            {
                if (validarIguales())
                {


                    try
                    {
                        HttpClient client =new HttpClient(new OnHttpRequestComplete() {
                            @Override
                            public boolean onComplete(Response status) {


                                if (status.isSuccess()) {
                                    try {

                                        JSONObject usuarios = new JSONObject(status.getResult());
                                        String ClaveBD = usuarios.getJSONObject("0").get("Clave").toString();
                                        if (ClaveBD.equals(clave.getText().toString()))
                                        {
                                            usuarios.remove("0");

                                            try
                                            {
                                            HttpClient cambiarclave=new HttpClient(new OnHttpRequestComplete() {
                                                @Override
                                                public boolean onComplete(Response status)
                                                {
                                                    if(status.isSuccess())
                                                    {
                                                        Toast.makeText(Administrador_CambiarClave.this, status.getResult(), Toast.LENGTH_SHORT).show();
                                                        if(status.getResult().equals("Clave actualizada con exito :D"))
                                                        {
                                                           /* final AlertDialog.Builder alet=new AlertDialog.Builder( Administrador_CambiarClave.this);
                                                            alet.setIcon(R.mipmap.ic_launcher);
                                                            alet.setMessage("Su sesion caducara al cambiar clave...");
                                                            alet.setTitle("Atencion!");
                                                            alet.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which)
                                                                {

                                                                }
                                                            });
                                                            AlertDialog alertDialog=alet.create();
                                                            alertDialog.show();*/
                                                        }
                                                    }

                                                    return false;
                                                }
                                            });
                                            ip ips=new ip();
                                            String urlC="http://"+ips.Ip()+"/webservice/CambiarClave.php?usuario="+SesionUser+"&clave="+(nueva.getText().toString())+"";
                                            String url=urlC.replace(" ","-9-");
                                            cambiarclave.excecute(url);
                                            }
                                            catch (Exception E)
                                            {

                                            }


                                        } else {
                                            clave.setError("Error su clave es incorrecta...");
                                        }


                                    } catch (JSONException e) {
                                        clave.setError("Error su clave es incorrecta...");
                                    }
                                }
                                return false;
                            }
                        });
                        ip ips=new ip();
                        String Url="http://"+ips.Ip()+"/WebService/login.php?usuario="+SesionUser+"&clave="+SessionPas+"";
                        client.excecute(Url);
                    }catch(Exception e)
                    {
                    }


                }


            }

        }

    }

}
