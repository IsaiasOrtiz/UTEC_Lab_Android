package sv.edu.utec.utec;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getAvailableCalendarTypes;
import static java.util.Calendar.getInstance;

public class Laboratorio_Administrador_Asignarpor_Semana extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    CheckBox Lunes,Martes,Miercoles,Jueves,Viernes,Sabado,Domingo;
    Button Finicio,Ffinal;
    EditText PcDisponibles,Observaciones;
    Button HoraInicio,HoraFinal;
    TextView Tipo;
    ListView DatosLista;
    int id;

    ArrayList FechaAR=new ArrayList();
    ArrayList HoraInicioAr=new ArrayList();
    ArrayList HoraFinalAr=new ArrayList();
    ArrayList PcDisponiblesAr=new ArrayList();
    ArrayList ObservacionesAr=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio__administrador__asignarpor__semana);

        Finicio=(Button)findViewById(R.id.btnInicioDate);
        Ffinal=(Button)findViewById(R.id.btnFechaFinalDate);
       HoraInicio=(Button)findViewById(R.id.Inicio);
       HoraFinal=(Button)findViewById(R.id.Fin);
        PcDisponibles=(EditText)findViewById(R.id.etPcDisponiblesDia);
        Observaciones=(EditText)findViewById(R.id.etObservacioness);
        Lunes=(CheckBox)findViewById(R.id.cL);
        Martes=(CheckBox)findViewById(R.id.cM);
        Miercoles=(CheckBox)findViewById(R.id.cMi);
        Jueves=(CheckBox)findViewById(R.id.cJ);
        Tipo=(TextView)findViewById(R.id.tvTipo);
        Viernes=(CheckBox)findViewById(R.id.cV);
        Sabado=(CheckBox)findViewById(R.id.cS);
        Domingo=(CheckBox)findViewById(R.id.cD);
        DatosLista=(ListView)findViewById(R.id.lsFechasDia);

        Tipo.setText(getIntent().getStringExtra("Tipo"));

        Ffinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=1;
                DialogFragment Selector=new SelectorDeFechas();
                Selector.show(getSupportFragmentManager(),"date picker");
            }
        });
        Finicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment Selector=new SelectorDeFechas();
                Selector.show(getSupportFragmentManager(),"date picker");
                id=0;
            }
        });



    }
    public boolean validarCB()
    {
        boolean valor=true;
            if((Lunes.isChecked())||(Martes.isChecked())||(Miercoles.isChecked())|| (Miercoles.isChecked())||(Jueves.isChecked())||(Viernes.isChecked())||(Sabado.isChecked())||(Domingo.isChecked()))
            {

            }else
            {
                Toast.makeText(this, "Seleccione los dias a asignar", Toast.LENGTH_SHORT).show();
                valor=false;
            }


        return valor;
    }

    public static Date ParseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

   ArrayList lista=new ArrayList();
    public void Agregar(View v)
    {
        if(validarCB())
        {

            if(ValidarVacio())
            {
                /* String calen=Finicio.getText().toString();
                Date fechasCal;
                Calendar fechasAdd= Calendar.getInstance();

                boolean value=true;

                 for(int i=0; value; i++)
                {
                  if((calen).equals(Ffinal.getText().toString()))
                    {
                        value=false;
                    }

                    SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
                    String dateInString = calen;

                    try {

                        Date date = formatter.parse(dateInString);
                        Toast.makeText(this, formatter.format(date), Toast.LENGTH_SHORT).show();


                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }*/


                    /*fechasAdd.add(Calendar.DAY_OF_YEAR,1);
                    calen=fechasAdd.getTime().toString();
                }*/
                String dias="";

                Date f=ParseFecha(Finicio.getText().toString());

                Date ffinal=ParseFecha(Ffinal.getText().toString());
                int diaf=ffinal.getDate();
                int mesf=ffinal.getMonth()+1;
                int anof=ffinal.getYear()+1900;
                String fechaf=diaf+"/"+mesf+"/"+anof;
                String finalizacion=Ffinal.getText().toString();
                ArrayList a2=new ArrayList();
                int sumad=1;
                boolean val=true;
                while (val)
                {
                    int dia=f.getDate();
                    int mes=f.getMonth()+1;
                    int ano=f.getYear()+1900;
                    String fechaN=dia+"/"+mes+"/"+ano;
                    a2.add(fechaN);
                    f.setDate(f.getDate()+sumad);
                    sumad++;
                    if(f.equals(ffinal))
                    {
                        val=false;

                    }

                }





                ArrayAdapter ad=new ArrayAdapter(this,R.layout.list_view,a2);
                DatosLista.setAdapter(ad);













                if(Lunes.isChecked() )
                {
                    dias=dias+"Lunes ";

                }
                if(Martes.isChecked())
                {
                    dias=dias+"Martes ";

                }
                if(Miercoles.isChecked())
                {
                    dias=dias+"Miercoles ";

                }
                if(Jueves.isChecked())
                {
                    dias=dias+"Jueves ";

                }
                if(Viernes.isChecked())
                {
                    dias=dias+"Viernes ";

                }
                if(Sabado.isChecked())
                {
                    dias=dias+"Sabado ";

                }
                if(Domingo.isChecked())
                {
                    dias=dias+"Domingo ";
                }
                String agregado= ("Fecha de inicio:"+Finicio.getText().toString()+"\nFecha de finalizacion:"+Ffinal.getText().toString()+"\nDias:"+dias+"\nHora de inicio:"+HoraInicio.getText().toString()+"\nHora de finalizacion:"+HoraFinal.getText().toString()+"\nPC disponibles:"+PcDisponibles.getText().toString()+"\nObservaciones:"+Observaciones.getText().toString());
                if(lista.indexOf(agregado)!=(-1))
                {
                    Toast.makeText(this, "El horario que intenta agregar ya existe...", Toast.LENGTH_SHORT).show();
                }else{
                    lista.add(agregado);

                }

            }

        }
    }
    public Boolean ValidarVacio()
    {
        boolean valR=true;
        if((HoraInicio.getText().toString()).equals("Seleccionar"))
        {
            Toast.makeText(this, "Ingrese Hora de inicio", Toast.LENGTH_SHORT).show();
            valR=false;
        }
        if((HoraFinal.getText().toString()).equals("Seleccionar"))
        {
            Toast.makeText(this, "Ingrese hora final", Toast.LENGTH_SHORT).show();
            valR=false;
        }
        if((PcDisponibles.getText().toString()).isEmpty())
        {
            PcDisponibles.setError("Debe ingresar la cantidad de PC disponibles");
            valR=false;
        }
        if((Finicio.getText().toString()).equals("20/2/2019"))
        {
            Toast.makeText(this, "Porfavor seleccione una fecha...", Toast.LENGTH_SHORT).show();
            valR=false;
        }
        if((Ffinal.getText().toString()).equals("27/2/2019"))
        {
            Toast.makeText(this, "Porfavor seleccione una fecha...", Toast.LENGTH_SHORT).show();
            valR=false;
        }
        return valR;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        if(id==1)
        {
            Calendar c=Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.YEAR,year);
            String fecha= DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
            Ffinal.setText(fecha);
        }
        if(id==0)
        {
            Calendar c=Calendar.getInstance();
            c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.YEAR,year);
            String fecha= DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());
            Finicio.setText(fecha);
        }




    }

    public void guardarDatos(View v)
    {
        if(lista.size()==0)
        {
            Toast.makeText(this, "Usted no ha agredado horarios....", Toast.LENGTH_SHORT).show();
        }
        else
            {

                Toast.makeText(this, "Horarios agredados correctamente...", Toast.LENGTH_SHORT).show();
                lista.clear();
                ArrayAdapter ars=new ArrayAdapter(this,R.layout.list_view,lista);
                DatosLista.setAdapter(ars);
            }

    }
    int hora, minuto;
    Calendar currenTime;


    public void SelectorHora(View v)
    {
        currenTime= getInstance();
        hora= currenTime.get(HOUR_OF_DAY);
        minuto =currenTime.get(MINUTE);

        TimePickerDialog TM=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
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

        TimePickerDialog TM2=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                HoraFinal.setText(hourOfDay+":"+minute);

            }
        },hora2,minuto2,true);
        TM2.show();


    }
}
