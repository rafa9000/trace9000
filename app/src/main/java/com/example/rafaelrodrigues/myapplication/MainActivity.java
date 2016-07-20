package com.example.rafaelrodrigues.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.rafaelrodrigues.myapplication.banco.BancoController;
import com.example.rafaelrodrigues.myapplication.banco.ConfiguracaoActivity;
import com.example.rafaelrodrigues.myapplication.banco.Trace9000Banco;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IGPSActivity {

    private GPSTracker gps;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("tdsfasdfasdfasd", "sdfasdfasdfasdfasdf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Trace9000</font>"));
        final Intent intent = new Intent(this, ConfiguracaoActivity.class);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("teste", "teste");
                startActivity(intent);
            }
        });

        gps = new GPSTracker(MainActivity.this);

        // Check if GPS enabled
        if(gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }


        BancoController crud = new BancoController(getBaseContext());
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
//        crud.insereDado(latitude +" " + longitude);

        Cursor cursor = crud.carregaDados();

        String[] nomeCampos = new String[] {Trace9000Banco.ID, Trace9000Banco.NOME};
        int[] idViews = new int[] {R.id.name, R.id.textView1};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.list_row,cursor,nomeCampos,idViews, 0);
        lista = (ListView)findViewById(R.id.listView);
        /*lista.setAdapter(adaptador);*/

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
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

/*
        if (id == R.id.nav_trace) {
            // Handle the camera action        } else if (id == R.id.nav_lista) {

        }
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void locationChanged(double longitude, double latitude) {
        Log.d("X", "Main-Longitude: " + longitude);
        Log.d("Y", "Main-Latitude: " + latitude);
    }

    @Override
    public void displayGPSSettingsDialog() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}
