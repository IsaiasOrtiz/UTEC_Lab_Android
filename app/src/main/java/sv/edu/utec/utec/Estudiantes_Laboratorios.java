package sv.edu.utec.utec;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Estudiantes_Laboratorios extends Fragment implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {
    Spinner sp;
    ListView ls;
    Button btnFecha,recargar,llegar;

    ArrayList labs=new ArrayList();
    ArrayList fechas=new ArrayList();
    ArrayList edificios=new ArrayList();
    ArrayList arDia=new ArrayList();
    ArrayList idLab=new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vis=inflater.inflate(R.layout.fragment_estudiantes__laboratorios, container, false);
        ls=(ListView)vis.findViewById(R.id.LsEstudiante);
        sp=(Spinner)vis.findViewById(R.id.spLaboratorioEs);
        btnFecha=(Button)vis.findViewById(R.id.btnFechaES);
        recargar=(Button)vis.findViewById(R.id.RecargarLabs);

        HttpClient cl=new HttpClient(new OnHttpRequestComplete() {
            @Override
            public boolean onComplete(Response status) {
                if(status.isSuccess())
                {
                    try{
                        JSONObject Labs=new JSONObject(status.getResult());

                        for(int i=0;;i++)
                        {

                            idLab.add(Labs.getJSONObject(""+i+"").get("ID_Lab"));
                            edificios.add(Labs.getJSONObject(""+i+"").get("Nombre"));


                        }

                    }
                    catch (JSONException e){}

                }
                return false;
            }
        });
        final ip ips=new ip();
        cl.excecute("http://"+ips.Ip()+"/webservice/admin/Laboratorios.php");
        ArrayAdapter ar=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,edificios);
        sp.setAdapter(ar);

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
                        Toast.makeText(getActivity(), fecha, Toast.LENGTH_SHORT).show();
                        int idcc1=fechas.indexOf(fecha);

                        try
                        {
                            fechas.clear();
                            HttpClient LsG=new HttpClient(new OnHttpRequestComplete() {
                                @Override
                                public boolean onComplete(Response status) {
                                    if(status.isSuccess())
                                    {
                                        try{
                                            JSONObject LIS=new JSONObject(status.getResult());
                                            for(int i=0; ; i++)
                                            {

                                                String fecha=LIS.getJSONObject(""+i+"").get("Fecha").toString();
                                                String inicio=LIS.getJSONObject(""+i+"").get("Inicio").toString();
                                                String fin=LIS.getJSONObject(""+i+"").get("Final").toString();
                                                String pc=LIS.getJSONObject(""+i+"").get("PcDisponibles").toString();
                                                String Observaciones=LIS.getJSONObject(""+i+"").get("Observaciones").toString();
                                                String Laboratorio=LIS.getJSONObject(""+i+"").get("Nlab").toString();
                                                fechas.add("Fecha:"+fecha+"\nHora de inicio:"+inicio+"\nHora de finalizacion"+fin+"\nPC disponibles:"+pc+"\nObservaciones:"+Observaciones+"\nLaboratorio:"+Laboratorio);
                                                ArrayAdapter arr=new ArrayAdapter(getActivity(), R.layout.list_view,fechas);
                                                ls.setAdapter(arr);

                                            }
                                        }
                                        catch (JSONException e){}
                                    }
                                    return false;
                                }
                            });
                            LsG.excecute("http://"+ips.Ip()+"/webservice/Estudiante/BuscarxFecha.php?fecha="+fecha+"");

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
                try
                {
                    fechas.clear();
                    HttpClient LsG=new HttpClient(new OnHttpRequestComplete() {
                        @Override
                        public boolean onComplete(Response status) {
                            if(status.isSuccess())
                            {
                                try{
                                    JSONObject LIS=new JSONObject(status.getResult());
                                    for(int i=0; ; i++)
                                    {

                                        String fecha=LIS.getJSONObject(""+i+"").get("Fecha").toString();
                                        String inicio=LIS.getJSONObject(""+i+"").get("Inicio").toString();
                                        String fin=LIS.getJSONObject(""+i+"").get("Final").toString();
                                        String pc=LIS.getJSONObject(""+i+"").get("PcDisponibles").toString();
                                        String Observaciones=LIS.getJSONObject(""+i+"").get("Observaciones").toString();
                                        String Laboratorio=LIS.getJSONObject(""+i+"").get("Nlab").toString();
                                        fechas.add("Fecha:"+fecha+"\nHora de inicio:"+inicio+"\nHora de finalizacion"+fin+"\nPC disponibles:"+pc+"\nObservaciones:"+Observaciones+"\nLaboratorio:"+Laboratorio);

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
                    LsG.excecute("http://"+ips.Ip()+"/webservice/Estudiante/ListadorGeneral.php");
                    ArrayAdapter arr=new ArrayAdapter(getActivity(), R.layout.list_view,fechas);
                    ls.setAdapter(arr);
                }
                catch (Exception e){}


            }
        });




        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fechas.clear();

                final HttpClient cl12=new HttpClient(new OnHttpRequestComplete() {
                    @Override
                    public boolean onComplete(Response status) {
                        if(status.isSuccess())
                        {
                            try{
                                JSONObject Labs=new JSONObject(status.getResult());
                                for (int i =0;; i++)
                                {
                                    String Fecha=Labs.getJSONObject(""+i+"").get("Fecha").toString();
                                    String HoraI=Labs.getJSONObject(""+i+"").get("Inicio").toString();
                                    String HoraF=Labs.getJSONObject(""+i+"").get("Final").toString();
                                    String Disponibles=Labs.getJSONObject(""+i+"").get("PcDisponibles").toString();
                                    String Observaciones=Labs.getJSONObject(""+i+"").get("Observaciones").toString();
                                    String Laboratorio=Labs.getJSONObject(""+i+"").get("Nlab").toString();
                                    fechas.add("Fecha:"+Fecha+"\nHora de inicio:"+HoraI+"\nHora de finalizacion:"+HoraF+"\nPC disponibles"+Disponibles+
                                    "\nObservaciones:"+Observaciones+"\nLaboratorio:"+Laboratorio);

                                }
                            }catch (JSONException e){}

                        }
                        return false;
                    }
                });
                cl12.excecute("http://"+ips.Ip()+"/webservice/Estudiante/ListadoPorLab.php?ID="+idLab.get(position)+"");
                ArrayAdapter ar2=new ArrayAdapter(getActivity(),R.layout.list_view,fechas);
                ls.setAdapter(ar2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter arss=new ArrayAdapter(getActivity(),R.layout.list_view,edificios);
        ls.setAdapter(arss);

        llegar=(Button)vis.findViewById(R.id.btnLlegar1);
        llegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inn=new Intent(getActivity(),Estudiante_ComoLlegar.class);
                startActivity(inn);
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
        String fecha= DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
        btnFecha.setText(fecha);

    }
}
