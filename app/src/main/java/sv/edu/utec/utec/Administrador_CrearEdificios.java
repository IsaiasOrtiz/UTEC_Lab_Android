package sv.edu.utec.utec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import java.util.ArrayList;

public class Administrador_CrearEdificios extends AppCompatActivity {
    TextView ID,Nombre,Direccion,Niveles;
    RadioButton rdSotano2,rdSotano;
    ArrayList idAR=new ArrayList();
    ArrayList nombreAR=new ArrayList();
    ArrayList nivelesAR=new ArrayList();
    ArrayList DireccionAR=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador__crear_edificios);
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
            ip ips=new ip();
            try{
                HttpClient client=new HttpClient(new OnHttpRequestComplete() {
                    @Override
                    public boolean onComplete(Response status)
                    {


                        if(status.isSuccess())
                        {
                            String exito=status.getResult();
                            if(exito.equals("0"))
                            {
                                Toast.makeText(Administrador_CrearEdificios.this, "El nombre del edificio que esta tratando de ingresar ya existe...", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Administrador_CrearEdificios.this, "Nuevo edificio agregado con exito", Toast.LENGTH_SHORT).show();
                            }

                        }
                        return false;
                    }
                });
                int sotano=0;
                if(rdSotano.isChecked())
                {
                    sotano=0;
                }
                if(rdSotano2.isChecked())
                {
                    sotano=1;
                }
                String url="http://"+(ips.Ip())+"/webservice/admin/CrearEdificio.php?Nombre="+(Nombre.getText().toString())+"&Niveles="+Niveles.getText().toString()+"&Direccion="+Direccion.getText().toString()+"&Sotano="+sotano+"";
                String url2=url.replace(" ","-9-");
                client.excecute(url2);

            }catch (Exception e){
                Toast.makeText(this, "Un error ocurrio con la conexion :(", Toast.LENGTH_SHORT).show();
            }


        }

    }


    public boolean ValidarVacio()
    {
        boolean is=true;
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
        if(rdSotano2.isChecked() || rdSotano.isChecked())
        {

        }else{
            Toast.makeText(this, "Seleccione la opcion de sotano...", Toast.LENGTH_SHORT).show();
            is=false;
        }

        return is;
    }
}
