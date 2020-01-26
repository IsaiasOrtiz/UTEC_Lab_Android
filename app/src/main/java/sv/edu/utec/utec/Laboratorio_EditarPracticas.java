package sv.edu.utec.utec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

public class Laboratorio_EditarPracticas extends AppCompatActivity {
    Button cargarBTN;

    ArrayList ida=new ArrayList();
    ArrayList horaI=new ArrayList();
    ArrayList horaF=new ArrayList();
    ArrayList fecha=new ArrayList();
    EditText HoraInicio,HoraFinal,PcDisponibles,Observaciones;
    Button btnFecha;

    ArrayList pc=new ArrayList();
    ArrayList observaciones=new ArrayList();
    EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio__editar_practicas);
        cargarBTN=(Button)findViewById(R.id.carBTN);
        HoraInicio=(EditText)findViewById(R.id.etHoraInicio);
        btnFecha=(Button)findViewById(R.id.btnFechaDia);
        HoraFinal=(EditText)findViewById(R.id.etHoraFinal);
        PcDisponibles=(EditText)findViewById(R.id.etPcDisponiblesDia);
        Observaciones=(EditText)findViewById(R.id.etObservacioness);
        id=(EditText)findViewById(R.id.etID);

        for(int i=1; i<30; i++)
        {
            ida.add(i);
            horaI.add("8:00");
            horaF.add("11:00");
            fecha.add(i+"/Abril/2019");
            pc.add(i);
            observaciones.add("Ninguna");
        }

        cargarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            if(id.getText().toString().isEmpty())
            {
                id.setError("Capo vacio...");
            }else
                {
                    try {

                        String txt=id.getText().toString();
                        int ids2=Integer.parseInt(txt);
                        if((ida.indexOf(ids2))==(-1))
                        {
                            Toast.makeText(Laboratorio_EditarPracticas.this, "Fecha no existe", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            int idd=Integer.parseInt(id.getText().toString());
                            HoraFinal.setText(horaF.get(idd).toString());
                            HoraInicio.setText(horaI.get(idd).toString());
                            btnFecha.setText(fecha.get(idd).toString());
                            Observaciones.setText(observaciones.get(idd).toString());
                            PcDisponibles.setText(pc.get(idd).toString());


                        }

                    }catch (Exception e)
                    {

                    }

                }



            }
        });
    }
    public void guardar(View v)
    {
        if(ValidarVacio())
        {
            try {
                Toast.makeText(this, "Cambios realizados", Toast.LENGTH_SHORT).show();


                String idN=id.getText().toString();
                int ifd=Integer.parseInt(idN);

                horaI.set(ifd,HoraInicio.getText().toString());
                horaF.set(ifd,HoraFinal.getText().toString());
                pc.set(ifd,PcDisponibles.getText().toString());
                observaciones.set(ifd,Observaciones.getText().toString());


            }catch (Exception e){}

        }

    }


    public Boolean ValidarVacio()
    {
        boolean valR=true;
        if(id.getText().toString().isEmpty())
        {
            id.setError("Debe dejar el ID con el cual cargo los datos...");
        }
        if((HoraInicio.getText().toString()).isEmpty())
        {
            HoraInicio.setError("Ingrese una hora de inicio");
            valR=false;
        }
        if((HoraFinal.getText().toString()).isEmpty())
        {
            HoraFinal.setError("Ingrese hora de finalizacion");
            valR=false;
        }
        if((PcDisponibles.getText().toString()).isEmpty())
        {
            PcDisponibles.setError("Debe ingresar la cantidad de PC disponibles");
            valR=false;
        }
        return valR;
    }
}
