package sv.edu.utec.utec;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Administrador_Laboratorios extends Fragment {

    ListView Laboratorios;
    Button crear,editar;
    ArrayList Labs=new ArrayList();
    ArrayList IdLab=new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_administrador__laboratorios, container, false);
        Laboratorios=(ListView)v.findViewById(R.id.lsvLaboratorios);
        crear=(Button)v.findViewById(R.id.btnCrearLab);
        editar=(Button)v.findViewById(R.id.btnEditarLab);

       /*Labs.add("ID:"+0+"\nLaboratorio: 1"+"\nEdificio:Francisco Morazan"+"\nNivel 5"+"\nCapacidad:50\nAdministrador:Ruth Flores");
        Labs.add("ID:"+1+"\nLaboratorio: 2"+"\nEdificio:Francisco Morazan"+"\nNivel 5"+"\nCapacidad:50\nAdministrador:Jorge Acevedo");
        Labs.add("ID:"+3+"\nLaboratorio: 3"+"\nEdificio:Benito Juarez"+"\nNivel 0"+"\nCapacidad:50\nAdministrador:Ingrid Rivera");*/

       try{
           Labs.clear();
           IdLab.clear();
           HttpClient cliente=new HttpClient(new OnHttpRequestComplete() {
               @Override
               public boolean onComplete(Response status)
               {
                   if(status.isSuccess())
                   {
                       try{
                           JSONObject laboratorios=new JSONObject(status.getResult());
                           for(int i=0 ;;i++)
                           {
                               IdLab.add(laboratorios.getJSONObject(""+i+"").get("ID_Lab"));
                               String idL="ID:"+laboratorios.getJSONObject(""+i+"").get("ID_Lab").toString();
                               String NombreL="\nNombre:"+laboratorios.getJSONObject(""+i+"").get("Nombre").toString();
                               String NivelL="\nNivel:"+laboratorios.getJSONObject(""+i+"").get("Nivel").toString();
                               String UrlLab="\nUrl:"+laboratorios.getJSONObject(""+i+"").get("Coordenada").toString();
                               String Capacidad="\nCapacidad:"+laboratorios.getJSONObject(""+i+"").get("capacidad").toString();
                               String usuario="\nAdministrador:"+laboratorios.getJSONObject(""+i+"").get("UserName").toString();
                               String edificioL="\nEdificio:"+laboratorios.getJSONObject(""+i+"").get("NEdificio").toString();
                               Labs.add(idL+NombreL+NivelL+UrlLab+Capacidad+usuario+edificioL);
                               laboratorios.remove(""+i+"");


                           }
                       }
                       catch (JSONException E){}


                   }


                   return false;
               }
           });
           ArrayAdapter ad=new ArrayAdapter(getActivity(), R.layout.list_view,Labs);
           Laboratorios.setAdapter(ad);
           ip ips=new ip();
           cliente.excecute("http://"+ips.Ip()+"/webservice/admin/Laboratorios.php");


       }
       catch(Exception e){}



       Laboratorios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {

                final AlertDialog.Builder alet=new AlertDialog.Builder( getActivity());
                alet.setIcon(R.mipmap.ic_launcher);
                alet.setTitle("Edificios");
                alet.setMessage(Labs.get(position).toString());
                alet.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {


                        try
                        {
                            HttpClient Eliminar=new HttpClient(new OnHttpRequestComplete() {
                                @Override
                                public boolean onComplete(Response status) {
                                    if(status.isSuccess())
                                    {
                                        Toast.makeText(getActivity(), status.getResult(), Toast.LENGTH_SHORT).show();
                                    }
                                    return false;
                                }
                            });
                            ip ips=new ip();
                            Eliminar.excecute("http://"+ips.Ip()+"/webservice/admin/EliminarLab.php?ID="+IdLab.get(position)+"");
                            Labs.remove(position);
                            IdLab.remove(position);



                        }catch(Exception e)
                        {

                        }


                        ArrayAdapter aer=new ArrayAdapter(getContext(), R.layout.list_view,Labs);
                        Laboratorios.setAdapter(aer);
                    }
                });
                alet.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=alet.create();
                alertDialog.show();

            }
        });


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(getActivity(),Administrador_CrearLaboratorios.class);
                startActivity(in);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(getActivity(),Administrador_EditarLaboratorio.class);
                startActivity(in);
            }
        });



        return v;
    }

}


