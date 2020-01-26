package sv.edu.utec.utec;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.getInstance;


public class LaboratorioAdministrador_AsignarPor_Dia extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btnFecha,guardar,agregar,HoraInicio,HoraFinal;
    EditText PcDisponibles,Observaciones;
    ListView ls;

    ArrayList FechaAR=new ArrayList();
    ArrayList HoraInicioAR=new ArrayList();
    ArrayList HoraFinalAR=new ArrayList();
    ArrayList ObservacionesAR=new ArrayList();
    ArrayList PcDisponibles1=new ArrayList();
    ArrayList DatosAr=new ArrayList();
    ip ips=new ip();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_administrador__asignar_por__dia);
        btnFecha=(Button)findViewById(R.id.btnFechaDia);
        guardar=(Button)findViewById(R.id.btnGuardarDia);
        agregar=(Button)findViewById(R.id.btnAgregarDia);
        HoraInicio=(Button)findViewById(R.id.HoraInicio);

        HoraFinal=(Button) findViewById(R.id.HoraFinal);
        PcDisponibles=(EditText)findViewById(R.id.etPcDisponiblesDia);
        Observaciones=(EditText)findViewById(R.id.etObservacioness);
        ls=(ListView)findViewById(R.id.lsFechasDia);
        final ArrayAdapter ar=new ArrayAdapter(this,R.layout.list_view,DatosAr);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment Selector=new SelectorDeFechas();
                Selector.show(getSupportFragmentManager(),"date picker");
            }
        });




        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(DatosAr.size()==0)
                {
                    Toast.makeText(LaboratorioAdministrador_AsignarPor_Dia.this, "Debe agregar datos...", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        HttpClient cl1=new HttpClient(new OnHttpRequestComplete() {
                            @Override
                            public boolean onComplete(Response status)
                            {
                                if(status.isSuccess())
                                {
                                    try{
                                        JSONObject IDS=new JSONObject(status.getResult());
                                        String idL1=IDS.getJSONObject("0").get("ID_Lab").toString();
                                        Toast.makeText(LaboratorioAdministrador_AsignarPor_Dia.this, idL1+"dsd", Toast.LENGTH_SHORT).show();

                                        for(int i=0;i<=FechaAR.size();i++)
                                        {


                                            try{
                                                HttpClient insertH=new HttpClient(new OnHttpRequestComplete() {
                                                    @Override
                                                    public boolean onComplete(Response status) {
                                                        if(status.isSuccess())
                                                        {


                                                        }
                                                        return false;
                                                    }
                                                });
                                                String obser=(ObservacionesAR.get(i).toString()).replace(" ","-9-");
                                                insertH.excecute("http://"+ips.Ip()+"/webservice/adminLab/InsertarHorario.php?fecha="+FechaAR.get(i)+"&horaI="+HoraInicioAR.get(i)+"&HoraF="+HoraFinalAR.get(i)+"&PcD="+PcDisponibles1.get(i)+"&observaciones="+obser+"&idlab="+idL1+"");
                                            }
                                            catch (Exception E){}
                                        }


                                    }
                                    catch (JSONException E)
                                    {}



                                }
                                return false;
                            }
                        });
                        SharedPreferences settings = LaboratorioAdministrador_AsignarPor_Dia.this.getSharedPreferences("Val",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor;
                        editor = settings.edit();
                        String adm=(settings.getString("Usuario","NULL"));
                        editor.commit();
                        cl1.excecute("http://"+ips.Ip()+"/webservice/adminLab/IdmyLab.php?user="+adm+"");

                    }catch (Exception e){}
                    {

                    }


                    HoraFinalAR.clear();
                    HoraInicioAR.clear();
                    ObservacionesAR.clear();
                    FechaAR.clear();
                    DatosAr.clear();
                    ls.setAdapter(ar);
                }



            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(ValidarVacio())
                {

                     /*
                              ArrayList FechaAR=new ArrayList();
                            ArrayList HoraInicioAR=new ArrayList();
                            ArrayList HoraFinalAR=new ArrayList();
                            ArrayList ObservacionesAR=new ArrayList();
                            ArrayList PcDisponibles1=new ArrayList();
                            ArrayList DatosAr=new ArrayList();*/

                        FechaAR.add(btnFecha.getText().toString());

                        HoraFinalAR.add(HoraFinal.getText().toString());

                        HoraInicioAR.add(HoraInicio.getText().toString());

                        PcDisponibles1.add(PcDisponibles.getText().toString());

                        ObservacionesAR.add(Observaciones.getText().toString());

                        if ((Observaciones.getText().toString()).isEmpty()) {
                            ObservacionesAR.add("Ninguna");
                        } else {
                            ObservacionesAR.add(Observaciones.getText().toString());
                        }


                        Toast.makeText(LaboratorioAdministrador_AsignarPor_Dia.this, "Datos agregados correctamente", Toast.LENGTH_SHORT).show();
                        DatosAr.add("Fecha:" + (btnFecha.getText().toString()) + "\nHorade Inicio:" + HoraInicio.getText().toString() + "\nHora de finalizacion:" + HoraFinal.getText().toString() + "\nObservaciones:" + Observaciones.getText().toString());
                        ls.setAdapter(ar);



                }
            }
        });
    }
    int hora, minuto;
    Calendar currenTime;


    public void SelectorHora(View v)
    {
        currenTime= getInstance();
        hora= currenTime.get(HOUR_OF_DAY);
        minuto =currenTime.get(MINUTE);

        TimePickerDialog TM=new TimePickerDialog(LaboratorioAdministrador_AsignarPor_Dia.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                HoraInicio.setText(hourOfDay+":"+minute);

            }
        },hora,minuto,true);
        TM.show();


    }

    int hora2, minuto2;
    Calendar currenTime2;


    public void SelectorHora2(View v)
    {
        currenTime2= getInstance();
        hora= currenTime2.get(HOUR_OF_DAY);
        minuto =currenTime2.get(MINUTE);

        TimePickerDialog TM2=new TimePickerDialog(LaboratorioAdministrador_AsignarPor_Dia.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                HoraFinal.setText(hourOfDay+":"+minute);

            }
        },hora2,minuto2,true);
        TM2.show();


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {

        Calendar c=Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.YEAR,year);
        String fecha= DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());

        btnFecha.setText(fecha);

    }


    public Boolean ValidarVacio()
    {
        boolean valR=true;
        if((HoraInicio.getText().toString()).equals("Seleccionar"))
        {
            Toast.makeText(this, "Seleccione hora de inicio", Toast.LENGTH_SHORT).show();
            valR=false;
        }
        if((HoraFinal.getText().toString()).equals("Seleccionar"))
        {
            Toast.makeText(this, "Seleccione hora de finalizacion", Toast.LENGTH_SHORT).show();
            valR=false;
        }
        if((PcDisponibles.getText().toString()).isEmpty())
        {
            PcDisponibles.setError("Debe ingresar la cantidad de PC disponibles");
            valR=false;
        }else
            {
                if((Integer.parseInt(PcDisponibles.getText().toString()))<=0)
                {
                    PcDisponibles.setError("No puede dejar sin PC disponibles una practica libre :C");
                    valR=false;
                }
            }
        if((btnFecha.getText().toString()).equals("17/05/2019"))
        {
            Toast.makeText(this, "Porfavor seleccione una fecha...", Toast.LENGTH_SHORT).show();
            valR=false;
        }



        return valR;
    }
}
