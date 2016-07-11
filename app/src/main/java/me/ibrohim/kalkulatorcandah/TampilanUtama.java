package me.ibrohim.kalkulatorcandah;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TampilanUtama extends AppCompatActivity {

    private Button mButton;
    private EditText mPenghasilan;
    private Spinner mBadan;
    private Spinner mStatusMusi;
    private Spinner mPerjanjianPembangunan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_utama);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPenghasilan = (EditText) findViewById(R.id.txtPemasukan);
        mBadan = (Spinner) findViewById(R.id.inputBadan);
        mStatusMusi = (Spinner) findViewById(R.id.inputStatusMusi);
        mPerjanjianPembangunan = (Spinner) findViewById(R.id.inputPerjanjian);

        mButton = (Button) findViewById(R.id.tblHitung);
        mButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (mPenghasilan.getText().toString().equals("")) {
                    mPenghasilan.setError("Pemasukan harus diisi");
                } else {
                    tampilkanHasil();
                }
            }
        });
    }

    private void tampilkanHasil(){
        Intent intent = new Intent(TampilanUtama.this, Hasil.class);
        Bundle b = new Bundle();
        b.putInt("penghasilan", Integer.parseInt(mPenghasilan.getText().toString()));
        b.putInt("badan", mBadan.getSelectedItemPosition());
        b.putInt("statusMusi", mStatusMusi.getSelectedItemPosition());

        int persentasePembangunan;

        switch (mPerjanjianPembangunan.getSelectedItemPosition()) {
            case 0:
                persentasePembangunan = 0;
                break;
            case 1:
                persentasePembangunan = 2;
                break;
            case 2:
                persentasePembangunan = 3;
                break;
            case 3:
                persentasePembangunan = 4;
                break;
            case 4:
                persentasePembangunan = 5;
                break;
            default:
                persentasePembangunan = 0;
        }

        b.putInt("perjanjianPembangunan", persentasePembangunan);

        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_tampilan_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}
