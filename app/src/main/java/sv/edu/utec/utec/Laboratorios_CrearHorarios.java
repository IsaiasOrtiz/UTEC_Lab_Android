package sv.edu.utec.utec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpRetryException;
import java.util.ArrayList;


public class Laboratorios_CrearHorarios extends Fragment {
    Spinner sp;
    Button Asignar;
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_laboratorios__crear_horarios, container, false);
        tv=(TextView)v.findViewById(R.id.TVlab);


        try
        {
            SharedPreferences settings = getActivity().getSharedPreferences("Val",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = settings.edit();
            String id=(settings.getString("ID","NULL"));
            editor.commit();
            HttpClient cl=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status) {
                    if(status.isSuccess())
                    {
                        try{
                            JSONObject LL=new JSONObject(status.getResult());
                            tv.setText(LL.getJSONObject("0").get("Nombre").toString());


                        }
                        catch (JSONException e){}
                    }
                    return false;
                }
            });
            ip ips=new ip();

            cl.excecute("http://"+ips.Ip()+"/webservice/adminlab/MyLab.php?ID="+id+"");
        }
        catch (Exception e)
        {

        }

        sp=(Spinner)v.findViewById(R.id.spinner2);
        Asignar=(Button)v.findViewById(R.id.btnAsignarHorarios);
        ArrayList listSpinner = new ArrayList();
        listSpinner.add("Dia");
        listSpinner.add("Semana");
        listSpinner.add("Mes");
        listSpinner.add("Ciclo");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listSpinner);
        sp.setAdapter(adapter);

        Asignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String valor_sp=sp.getSelectedItem().toString();
                Intent Xdia=new Intent(getActivity(),LaboratorioAdministrador_AsignarPor_Dia.class);
                Intent SemanaCiclo=new Intent(getActivity(),Laboratorio_Administrador_Asignarpor_Semana.class);


                if(valor_sp.equals("Dia"))
                {
                    startActivity(Xdia);
                }
                if(valor_sp.equals("Semana")||valor_sp.equals("Mes")||valor_sp.equals("Ciclo"))
                {
                    if(valor_sp.equals("Semana"))
                    {
                        SemanaCiclo.putExtra("Tipo","Semana");

                        startActivity(SemanaCiclo);

                    }
                    if(valor_sp.equals("Mes"))
                    {
                        SemanaCiclo.putExtra("Tipo","Mes");

                        startActivity(SemanaCiclo);

                    }
                    if(valor_sp.equals("Ciclo"))
                    {
                        SemanaCiclo.putExtra("Tipo","Ciclo");

                        startActivity(SemanaCiclo);

                    }
                }

                }

        });


        return v;
    }


}
