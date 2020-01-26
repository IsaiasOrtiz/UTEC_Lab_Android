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

public class Administrador_Edificios extends Fragment
{

    ListView Edificios;
    Button btnEditarEDF,btnCrearEDF;
    ArrayList EdificiosAr= new ArrayList();
    ArrayList IdEdf= new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_administrador__edificios, container, false);
        Edificios=(ListView)fragmentView.findViewById(R.id.lvEdificios);
        btnCrearEDF =(Button)fragmentView.findViewById(R.id.btnCrearEdificios);
        btnEditarEDF=(Button)fragmentView.findViewById(R.id.btnEditarEdificios);
        EdificiosAr.clear();
        try
        {
            HttpClient cliente=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status) {
                    if(status.isSuccess())
                    {
                        try {
                            JSONObject edificios = new JSONObject(status.getResult());
                            for(int i=0; ;i++)
                            {
                                IdEdf.add(edificios.getJSONObject(""+i+"").get("ID_EDF").toString());
                                String id="ID:"+edificios.getJSONObject(""+i+"").get("ID_EDF").toString();
                                String nombre="\nNombre:"+edificios.getJSONObject(""+i+"").get("Nombre").toString();
                                String Niveles="\nNiveles:"+edificios.getJSONObject(""+i+"").get("Niveles").toString();
                                String Sotano="";
                                if((edificios.getJSONObject(""+i+"").get("Sotano").toString()).equals("1"))
                                {
                                    Sotano="\nSotano:Habilitado";

                                }else{
                                    Sotano="\nSotano:Desabilitado";}
                                    String direccion="\nDireccion:"+edificios.getJSONObject(""+i+"").get("Direccion").toString();

                                EdificiosAr.add(id+nombre+Niveles+Sotano+direccion);
                                edificios.remove(""+i+"");

                            }
                        }
                        catch (JSONException e)
                        {

                        }

                    }
                    return false;
                }
            });


            ip ips=new  ip();
            cliente.excecute("http://"+ips.Ip()+"/webservice/admin/ListaEdificios.php");
            ArrayAdapter ad=new ArrayAdapter(getActivity(), R.layout.list_view,EdificiosAr);
            Edificios.setAdapter(ad);
        }catch (Exception e)
        {
            Toast.makeText(getActivity(), "Error de conexion: "+e.toString(), Toast.LENGTH_SHORT).show();
        }



        Edificios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id)
            {

                final AlertDialog.Builder alet=new AlertDialog.Builder( getActivity());
                alet.setIcon(R.mipmap.ic_launcher);
                alet.setTitle("Edificios");
                alet.setMessage(EdificiosAr.get(position).toString());
                alet.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {


                        HttpClient cliente1=new HttpClient(new OnHttpRequestComplete() {
                            @Override
                            public boolean onComplete(Response status)
                            {
                                if(status.isSuccess())
                                {
                                    EdificiosAr.remove(position);
                                    Toast.makeText(getActivity(), "Edificio eliminado con exito :D", Toast.LENGTH_SHORT).show();
                                    IdEdf.remove(position);
                                    ArrayAdapter ar1=new ArrayAdapter(getActivity(), R.layout.list_view,EdificiosAr);
                                    Edificios.setAdapter(ar1);
                                }


                                return false;
                            }
                        });
                        ip ips=new ip();
                        cliente1.excecute("http://"+ips.Ip()+"/webservice/admin/EliminarEdificio.php?id="+(IdEdf.get(position).toString())+");");

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







       btnEditarEDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent in=new Intent(getActivity(),Administrador_EditarEdificios.class);
                startActivity(in);
            }
        });

       btnCrearEDF.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in=new Intent(getActivity(),Administrador_CrearEdificios.class);
               startActivity(in);
           }
       });



        return fragmentView;
    }

}
