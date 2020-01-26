package sv.edu.utec.utec;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;

import static android.content.Context.MODE_PRIVATE;


public class Estudiante_perfil extends Fragment {

    Button bnCam;
    TextView nombre;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_estudiante_perfil, container, false);
        bnCam=(Button)v.findViewById(R.id.btnCambiarE);
        nombre=(TextView)v.findViewById(R.id.NombreTV);

        SharedPreferences settings = getActivity().getSharedPreferences("Val",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        nombre.setText(settings.getString("Nombre","NULL"));
        editor.commit();

        bnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inn=new Intent(getActivity(),Administrador_CambiarClave.class);
                startActivity(inn);

            }
        });
        return v;
    }

}
