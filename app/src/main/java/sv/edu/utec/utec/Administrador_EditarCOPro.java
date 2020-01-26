package sv.edu.utec.utec;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Administrador_EditarCOPro extends Fragment implements AdapterView.OnItemSelectedListener {


    ArrayList tipos=new ArrayList();
    ArrayList Carreras=new ArrayList();
    ArrayList Profesiones=new ArrayList();
    Button btnGuardarCam;
    Spinner spTiposEditar,spNombresEditar;
    ArrayList idCP=new ArrayList();
    EditText NuevoNmb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v2=inflater.inflate(R.layout.fragment_administrador__editar_co, container, false);
        spTiposEditar=(Spinner)v2.findViewById(R.id.spTipoE);
        spNombresEditar=(Spinner)v2.findViewById(R.id.spNombresCPE);
        NuevoNmb=(EditText)v2.findViewById(R.id.etNombreCP2E);
        btnGuardarCam=(Button)v2.findViewById(R.id.btnGuardarCambios);
        tipos.add("Carrera");
        tipos.add("Profesion");
        ArrayAdapter TiposCP=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,tipos);
        spTiposEditar.setAdapter(TiposCP);
        spTiposEditar.setOnItemSelectedListener(this);
        Carreras.add("Ingenieria en sistemas y computacion");
        Carreras.add("Licenciatura en informatica");
        Carreras.add("Licenciatura en administracion de empresas");
        Carreras.add("Licenciatura en programacion");

        Profesiones.add("Lic. En informatica");
        Profesiones.add("Ing.En sistemas");
        Profesiones.add("Soporte tecnico");
        Profesiones.add("IT");
        Profesiones.add("Desarrollador");


        btnGuardarCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    if (ValidarCampo())
                    {
                        HttpClient cl=new HttpClient(new OnHttpRequestComplete() {
                            @Override
                            public boolean onComplete(Response status) {
                                if(status.isSuccess())
                                {
                                    Toast.makeText(getActivity(), "Cambios realizados con exito", Toast.LENGTH_SHORT).show();

                                }
                                return false;
                            }
                        });
                        ip ips=new ip();
                        int idc1=Carreras.indexOf(spNombresEditar.getSelectedItem().toString());
                        String idc=idCP.get(idc1).toString();
                        String url23="http://"+ips.Ip()+"/webservice/admin/ActualizarCarreraOprofesion.php?ID="+idc+"&nombre="+NuevoNmb.getText().toString()+"";
                        String url=url23.replace(" ", "-9-");
                        cl.excecute(url);


                    }

            }
        });




        return v2;
    }


    public boolean ValidarCampo()
    {
        boolean val=true;

        if((NuevoNmb.getText().toString()).isEmpty())
        {
            NuevoNmb.setError("Campo vacio ingrese un valor...");
            val=false;
        }
        return val;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(parent.getId()==R.id.spTipoE)
        {
            if((spTiposEditar.getSelectedItem().toString()).equals("Carrera"))
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
                    ArrayAdapter AdProfesioness=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,Carreras);
                    spNombresEditar.setAdapter(AdProfesioness);
                }
                catch (Exception e)
                {

                }


            }else if((spTiposEditar.getSelectedItem().toString()).equals("Profesion"))
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
                            ArrayAdapter AdProfesioness=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,Carreras);
                            spNombresEditar.setAdapter(AdProfesioness);

                        }
                        catch (Exception e){}
                    }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
