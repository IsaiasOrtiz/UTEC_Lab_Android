package sv.edu.utec.utec;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import java.util.ArrayList;

public class Administrador_NuevaCarreraOP extends Fragment {

    Spinner sp;
    EditText Nombres;
    Button btn;
    ArrayList tipos=new ArrayList();
    ArrayList carrera_profesion=new ArrayList();
    ArrayList carrera=new ArrayList();
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_administrador__nueva_carrera_o, container, false);

        sp=(Spinner)v.findViewById(R.id.spTipo);
        Nombres=(EditText)v.findViewById(R.id.etNombreCP);
        tipos.add("Carrera");
        btn=(Button)v.findViewById(R.id.btnGuardarCarreraOP);
        tipos.add("Profesion");
        ArrayAdapter ad=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,tipos);
        sp.setAdapter(ad);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(ValidarVacio())
                {
                    int tipo=0;
                    if(sp.getSelectedItem().toString().equals("Carrera"))
                    {
                        tipo=1;
                    }
                    if(sp.getSelectedItem().toString().equals("Profesion"))
                    {
                        tipo=2;
                    }
                    try{
                        ip ips=new ip();



                        HttpClient client=new HttpClient(new OnHttpRequestComplete() {
                            @Override
                            public boolean onComplete(Response status)
                            {
                            if(status.isSuccess())
                            {
                                if((status.getResult()).equals("0"))
                                {
                                    Nombres.setError("Ya existe una carrera o profesion con este nombre :C");
                                }else
                                    {
                                        Toast.makeText(getActivity(), "Nueva carrera o profesion agregada con exito"+status.getResult(), Toast.LENGTH_SHORT).show();
                                    }

                            }
                                return false;
                            }
                        });



                        String name=(Nombres.getText().toString()).replace(" ","-9-");
                        String url1="http://"+ips.Ip()+"/webservice/admin/InsertarCarreraProfesion.php?nombre="+(name)+"&tipo="+(tipo)+"";
                        client.excecute(url1);



                    }
                    catch (Exception e){
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }






                }
            }
        });



        return v;
    }

    public boolean ValidarVacio()
    {
        boolean vali=true;

        if (Nombres.getText().toString().isEmpty())
        {
            Nombres.setError("Campo esta vacio.");
            vali=false;
        }

        return  vali;
    }


}
