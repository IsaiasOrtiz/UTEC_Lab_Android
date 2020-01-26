package sv.edu.utec.utec;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static sv.edu.utec.utec.R.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Administrador_usuarios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Administrador_usuarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Administrador_usuarios extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Administrador_usuarios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Administrador_usuarios.
     */
    // TODO: Rename and change types and number of parameters
    public static Administrador_usuarios newInstance(String param1, String param2) {
        Administrador_usuarios fragment = new Administrador_usuarios();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Spinner sp;
    ArrayList tipos=new ArrayList();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    ListView list;
    ArrayList listaAr=new ArrayList();
    Button bn,bnEditar;
    ArrayList idU=new ArrayList();
    ArrayList Ls2=new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View vist=inflater.inflate(layout.fragment_administrador_usuarios, container, false);
        bn=(Button)vist.findViewById(id.btnCrear);
        bnEditar=(Button)vist.findViewById(id.btnEditar);

        bnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in=new Intent(getActivity(), Administrador_EditarUsuarios.class);
                startActivity(in);

            }
        });

        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(), Administrador_CrearUsuarios.class);
                startActivity(in);
            }
        });


        tipos.add("Estudiante");
        tipos.add("Administrador");
        tipos.add("Administrador Laboratorios");
        tipos.add("Todos los usuarios");

        sp= (Spinner)vist.findViewById(R.id.SpTipo);
        sp.setOnItemSelectedListener(this);
        list=(ListView)vist.findViewById(R.id.lista);
        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, tipos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {

                final AlertDialog.Builder alet=new AlertDialog.Builder( getActivity());
                alet.setIcon(mipmap.ic_launcher);
                alet.setTitle("Usuario");
                Toast.makeText(getActivity(), idU.get(position).toString(), Toast.LENGTH_SHORT).show();
                alet.setMessage(Ls2.get(position).toString());
                alet.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    int id=Integer.parseInt(idU.get(position).toString());

                    HttpClient client=new HttpClient(new OnHttpRequestComplete() {
                        @Override
                        public boolean onComplete(Response status) {

                            return false;
                        }
                    });
                        ip ips=new ip();
                        String url1="http://"+ips.Ip()+"/webservice/admin/UsuariosDesabilitar.php?id="+id+"";
                        client.excecute(url1);

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
        return vist;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String seleccion=parent.getItemAtPosition(position).toString();
        list.invalidateViews();
        if (seleccion.equals("Estudiante")){
            Ls2.clear();
            idU.clear();


            HttpClient client=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status)
                {

                    if(status.isSuccess())
                    {
                        try{
                            JSONObject USERSE=new JSONObject(status.getResult());
                            for(int i=0;;i++)
                            {
                                idU.add(USERSE.getJSONObject(""+i+"").get("IdUsuario"));
                                Ls2.add("ID:"+USERSE.getJSONObject(""+i+"").get("IdUsuario")+"\nNombre:"+USERSE.getJSONObject(""+i+"").get("Nombre")+
                                        "\nUsuario:"+USERSE.getJSONObject(""+i+"").get("Usuario")+"\nClave:"+USERSE.getJSONObject(""+i+"").get("Clave")+
                                        "\nCarrera o profesion:"+USERSE.getJSONObject(""+i+"").get("Id_CoP")+"\nTipo:"+USERSE.getJSONObject(""+i+"").get("Tipo"));
                                USERSE.remove(""+i+"");
                                ArrayAdapter adapterListE=new ArrayAdapter(getActivity(), layout.list_view,Ls2);
                                list.setAdapter(adapterListE);



                            }




                        }catch (JSONException e)
                        {

                        }

                    }

                    return false;
                }
            });
            ip ips=new ip();
            String url1="http://"+ips.Ip()+"/webservice/admin/UsuariosEstudiantes.php";
            client.excecute(url1);

        }
        if (seleccion.equals("Administrador")){
            idU.clear();
            Ls2.clear();
            HttpClient client=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status)
                {

                    if(status.isSuccess())
                    {
                        try{
                            JSONObject USERSE=new JSONObject(status.getResult());
                            for(int i=0;;i++)
                            {
                                idU.add(USERSE.getJSONObject(""+i+"").get("IdUsuario"));
                                Ls2.add("ID:"+USERSE.getJSONObject(""+i+"").get("IdUsuario")+"\nNombre:"+USERSE.getJSONObject(""+i+"").get("Nombre")+
                                        "\nUsuario:"+USERSE.getJSONObject(""+i+"").get("Usuario")+"\nClave:"+USERSE.getJSONObject(""+i+"").get("Clave")+"\nCarrera o profesion:"+USERSE.getJSONObject(""+i+"").get("Id_CoP")
                                        +"\nTipo:"+USERSE.getJSONObject(""+i+"").get("Tipo"));
                                USERSE.remove(""+i+"");
                                ArrayAdapter adapterListAL=new ArrayAdapter(getActivity(), layout.list_view,Ls2);
                                list.setAdapter(adapterListAL);
                            }




                        }catch (JSONException e)
                        {

                        }

                    }

                    return false;
                }
            });
            ip ips=new ip();
            String url1="http://"+ips.Ip()+"/webservice/admin/UsuariosAdmin.php";
            client.excecute(url1);


        }
        if (seleccion.equals("Administrador Laboratorios")){
            Ls2.clear();
            idU.clear();
            HttpClient client=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status)
                {

                    if(status.isSuccess())
                    {
                        try{
                            JSONObject USERSE=new JSONObject(status.getResult());
                            for(int i=0;;i++)
                            {
                                idU.add(USERSE.getJSONObject(""+i+"").get("IdUsuario"));
                                Ls2.add("ID:"+USERSE.getJSONObject(""+i+"").get("IdUsuario")+"\nNombre:"+USERSE.getJSONObject(""+i+"").get("Nombre")+
                                        "\nUsuario:"+USERSE.getJSONObject(""+i+"").get("Usuario")+"\nClave:"+USERSE.getJSONObject(""+i+"").get("Clave")+"\nCarrera o profesion:"+USERSE.getJSONObject(""+i+"").get("Id_CoP")
                                        +"\nTipo:"+USERSE.getJSONObject(""+i+"").get("Tipo"));
                                USERSE.remove(""+i+"");
                                ArrayAdapter adapterListAL=new ArrayAdapter(getActivity(), layout.list_view,Ls2);
                                list.setAdapter(adapterListAL);
                            }




                        }catch (JSONException e)
                        {

                        }

                    }

                    return false;
                }
            });
            ip ips=new ip();
            String url1="http://"+ips.Ip()+"/webservice/admin/UsuariosAdminLab.php";
            client.excecute(url1);

        }
        if(seleccion.equals("Todos los usuarios"))
        {
            Ls2.clear();
            idU.clear();
            HttpClient client=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status)
                {

                    if(status.isSuccess())
                    {
                        try{
                            JSONObject USERSE=new JSONObject(status.getResult());
                            for(int i=0;;i++)
                            {
                                idU.add(USERSE.getJSONObject(""+i+"").get("IdUsuario"));
                                Ls2.add("ID:"+USERSE.getJSONObject(""+i+"").get("IdUsuario")+"\nNombre:"+USERSE.getJSONObject(""+i+"").get("Nombre")+
                                        "\nUsuario:"+USERSE.getJSONObject(""+i+"").get("Usuario")+"\nClave:"+USERSE.getJSONObject(""+i+"").get("Clave")+"\nCarrera o profesion:"+USERSE.getJSONObject(""+i+"").get("NombreCP")
                                        +"\nTipo:"+USERSE.getJSONObject(""+i+"").get("Tipo"));
                                USERSE.remove(""+i+"");
                                ArrayAdapter adapterListAL=new ArrayAdapter(getActivity(), layout.list_view,Ls2);
                                list.setAdapter(adapterListAL);
                            }




                        }catch (JSONException e)
                        {

                        }

                    }

                    return false;
                }
            });
            ip ips=new ip();
            String url1="http://"+ips.Ip()+"/webservice/admin/Usuarios.php";
            client.excecute(url1);


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
