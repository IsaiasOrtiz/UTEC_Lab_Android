package sv.edu.utec.utec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Laboratorios extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment perfil=new Estudiante_perfil();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenidolab,perfil).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.laboratorios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences sharedpreferences = getSharedPreferences(("Val"), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();

            Intent Login=new Intent(this,sv.edu.utec.utec.Login.class);
            startActivity(Login);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fra=null;
        Boolean rs=false;

        if (id == R.id.nav_camera) {

            Fragment perfil=new Estudiante_perfil();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidolab,perfil).commit();

        }
        else if (id == R.id.nav_gallery) {

            fra=new Laboratorio_ListadoGeneral();
            rs=true;



        } else if (id == R.id.nav_slideshow)
        {
            fra=new Laboratorios_CrearHorarios();
            rs=true;

        }  else if (id == R.id.nav_share) {
            SharedPreferences sharedpreferences = getSharedPreferences(("Val"), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();

            Intent Login=new Intent(this,sv.edu.utec.utec.Login.class);
            startActivity(Login);
            finish();
        }

        if(rs)
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidolab,fra).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setActivityCambiar(View v) {
        Intent in=new Intent(this,Administrador_CambiarClave.class);
        startActivity(in);
    }
}
