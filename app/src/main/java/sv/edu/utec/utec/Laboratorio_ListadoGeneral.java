package sv.edu.utec.utec;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Laboratorio_ListadoGeneral extends Fragment implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {

    ListView ls;
    Button btnFecha,recargar,llegar;

    ArrayList Horarios=new ArrayList();
    ArrayList IDHorario=new ArrayList();
    ArrayList fechas=new ArrayList();
    ArrayList edificios=new ArrayList();
    ArrayList arDia=new ArrayList();
    ip ips=new ip();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View vis=inflater.inflate(R.layout.fragment_laboratorio__listado_general, container, false);

        ls=(ListView)vis.findViewById(R.id.LsEstudiante);
        btnFecha=(Button)vis.findViewById(R.id.btnFechaES);
        recargar=(Button)vis.findViewById(R.id.RecargarLabs);

        try
        {
            HttpClient LsG=new HttpClient(new OnHttpRequestComplete() {
                @Override
                public boolean onComplete(Response status) {
                    if(status.isSuccess())
                    {
                        try{
                            JSONObject LIS=new JSONObject(status.getResult());
                            for(int i=0; ; i++)
                            {
                                IDHorario.add(LIS.getJSONObject(""+i+"").get("ID_Horarios"));
                                String fecha=LIS.getJSONObject(""+i+"").get("Fecha").toString();
                                String inicio=LIS.getJSONObject(""+i+"").get("Inicio").toString();
                                String fin=LIS.getJSONObject(""+i+"").get("Final").toString();
                                String pc=LIS.getJSONObject(""+i+"").get("PcDisponibles").toString();
                                String Observaciones=LIS.getJSONObject(""+i+"").get("Observaciones").toString();
                                String Laboratorio=LIS.getJSONObject(""+i+"").get("Nlab").toString();
                                Horarios.add("Fecha:"+fecha+"\nHora de inicio:"+inicio+"\nHora de finalizacion"+fin+"\nPC disponibles:"+pc+"\nObservaciones:"+Observaciones+"\nLaboratorio:"+Laboratorio);

                            }
                        }
                        catch (JSONException e){}
                    }
                    return false;
                }
            });
            SharedPreferences settings = getActivity().getSharedPreferences("Val",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = settings.edit();
            String id=(settings.getString("ID","NULL"));
            editor.commit();
            LsG.excecute("http://"+ips.Ip()+"/webservice/adminlab/Mispracticas.php?ID="+id+"");
            ArrayAdapter arr=new ArrayAdapter(getActivity(), R.layout.list_view,Horarios);
            ls.setAdapter(arr);
        }
        catch (Exception e){}



        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        c.set(Calendar.MONTH,monthOfYear);
                        c.set(Calendar.YEAR,year);
                        String fecha=DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
                        btnFecha.setText(fecha);
                        try
                        {
                            HttpClient LsG=new HttpClient(new OnHttpRequestComplete() {
                                @Override
                                public boolean onComplete(Response status) {
                                    if(status.isSuccess())
                                    {
                                        IDHorario.clear();
                                        Horarios.clear();
                                        try{
                                            JSONObject LIS=new JSONObject(status.getResult());
                                            for(int i=0; ; i++)
                                            {
                                                IDHorario.add(LIS.getJSONObject(""+i+"").get("ID_Horarios"));
                                                String fecha=LIS.getJSONObject(""+i+"").get("Fecha").toString();
                                                String inicio=LIS.getJSONObject(""+i+"").get("Inicio").toString();
                                                String fin=LIS.getJSONObject(""+i+"").get("Final").toString();
                                                String pc=LIS.getJSONObject(""+i+"").get("PcDisponibles").toString();
                                                String Observaciones=LIS.getJSONObject(""+i+"").get("Observaciones").toString();
                                                String Laboratorio=LIS.getJSONObject(""+i+"").get("Nlab").toString();
                                                Horarios.add("Fecha:"+fecha+"\nHora de inicio:"+inicio+"\nHora de finalizacion"+fin+"\nPC disponibles:"+pc+"\nObservaciones:"+Observaciones+"\nLaboratorio:"+Laboratorio);

                                            }
                                        }
                                        catch (JSONException e){}
                                    }
                                    return false;
                                }
                            });
                            SharedPreferences settings = getActivity().getSharedPreferences("Val",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor;
                            editor = settings.edit();
                            String id=(settings.getString("ID","NULL"));
                            editor.commit();
                            LsG.excecute("http://"+ips.Ip()+"/webservice/adminlab/BuscarPorFecha.php?ID="+id+"&fecha="+fecha+"");
                            ArrayAdapter arr=new ArrayAdapter(getActivity(), R.layout.list_view,Horarios);
                            ls.setAdapter(arr);
                        }
                        catch (Exception e){}


                    }
                }, yy, mm, dd);

                datePicker.show();
            }
        });

        recargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Horarios.clear();
                IDHorario.clear();

                try
                {
                    HttpClient LsG=new HttpClient(new OnHttpRequestComplete() {
                        @Override
                        public boolean onComplete(Response status) {
                            if(status.isSuccess())
                            {
                                try{
                                    JSONObject LIS=new JSONObject(status.getResult());
                                    for(int i=0; ; i++)
                                    {
                                        IDHorario.add(LIS.getJSONObject(""+i+"").get("ID_Horarios"));
                                        String fecha=LIS.getJSONObject(""+i+"").get("Fecha").toString();
                                        String inicio=LIS.getJSONObject(""+i+"").get("Inicio").toString();
                                        String fin=LIS.getJSONObject(""+i+"").get("Final").toString();
                                        String pc=LIS.getJSONObject(""+i+"").get("PcDisponibles").toString();
                                        String Observaciones=LIS.getJSONObject(""+i+"").get("Observaciones").toString();
                                        String Laboratorio=LIS.getJSONObject(""+i+"").get("Nlab").toString();
                                        Horarios.add("Fecha:"+fecha+"\nHora de inicio:"+inicio+"\nHora de finalizacion"+fin+"\nPC disponibles:"+pc+"\nObservaciones:"+Observaciones+"\nLaboratorio:"+Laboratorio);

                                    }
                                }
                                catch (JSONException e){}
                            }
                            return false;
                        }
                    });
                    SharedPreferences settings = getActivity().getSharedPreferences("Val",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor;
                    editor = settings.edit();
                    String id=(settings.getString("ID","NULL"));
                    editor.commit();
                    LsG.excecute("http://"+ips.Ip()+"/webservice/adminlab/Mispracticas.php?ID="+id+"");
                    ArrayAdapter arr=new ArrayAdapter(getActivity(), R.layout.list_view,Horarios);
                    ls.setAdapter(arr);
                }
                catch (Exception e){}
            }
        });





        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {


        final AlertDialog.Builder alet=new AlertDialog.Builder( getActivity());
        alet.setIcon(R.mipmap.ic_launcher);
        alet.setTitle("Edificios");
        alet.setMessage(Horarios.get(position).toString());
        alet.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                try{
                    HttpClient cl2=new HttpClient(new OnHttpRequestComplete() {
                        @Override
                        public boolean onComplete(Response status) {
                            if(status.isSuccess())
                            {
                                Toast.makeText(getActivity(), "Se elmino correctamente", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                    String pos=IDHorario.get(position).toString();
                    cl2.excecute("http://"+ips.Ip()+"/webservice/adminLab/EliminarHorario.php?ID="+pos+"");
                }
                catch (Exception e){}
                Horarios.remove(position);
                IDHorario.remove(position);
                ArrayAdapter aer=new ArrayAdapter(getContext(), R.layout.list_view,Horarios);
                ls.setAdapter(aer);
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





        return vis;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c=Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.YEAR,year);
        String fecha= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        btnFecha.setText(fecha);

    }
}

