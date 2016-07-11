package me.ibrohim.kalkulatorcandah;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Hasil extends AppCompatActivity {

    private ListView mListKolom;
    private List<Kolom> pListKolom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        Intent intent = getIntent();
        int penghasilan = intent.getIntExtra("penghasilan", 0);
        int badan = intent.getIntExtra("badan", 0);
        int statusMusi = intent.getIntExtra("statusMusi", 0);
        int perjanjianPembangunan = intent.getIntExtra("perjanjianPembangunan", 0);

        pListKolom = hitung(penghasilan, badan, statusMusi, perjanjianPembangunan);

        hitungJumlah(pListKolom);

        mListKolom = (ListView) findViewById(R.id.list);

        ListAdapter customAdapter = new ListAdapter(this, R.layout.item_kolom, pListKolom);

        mListKolom.setAdapter(customAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hasil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_salin_ke_clipboard) {
            salin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salin(){

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        StringBuilder buffer = new StringBuilder();

        for (Kolom kolom : pListKolom) {
            buffer.append(kolom);
            buffer.append(System.getProperty("line.separator"));
        }

        ClipData clip = ClipData.newPlainText("simple text", buffer.toString());

        clipboard.setPrimaryClip(clip);

    }

    private List<Kolom> hitung(int penghasilan, int badan, int statusMusi, int perjanjianPembangunan) {

        ArrayList<Kolom> listKolom = new ArrayList<>();

        int aam = -1;
        int hissaamad = -1;

        switch (statusMusi) {
            case 0:
                aam = (int)Math.ceil((1.0/16.0)*penghasilan);
                hissaamad = 0;
                break;
            case 1:
                aam = 0;
                hissaamad = (int)Math.ceil((1.0/10.0)*penghasilan);
                break;
            case 2:
                aam = 0;
                hissaamad = (int)Math.ceil((1.0/5.0)*penghasilan);
                break;
            case 3:
                aam = 0;
                hissaamad = (int)Math.ceil((1.0/3.0)*penghasilan);
                break;
            default:
                aam = 0;
                hissaamad = 0;
                break;
        }

        Log.v("CANDAH", "MUSI " + Integer.toString(statusMusi));

        listKolom.add(new Kolom( "Aam", aam));
        listKolom.add(new Kolom( "Hissa Ammad Wasiyyat", hissaamad));

        listKolom.add(new Kolom( "Dana Jalsah", (int)Math.ceil((1.0/120)*penghasilan)));
        listKolom.add(new Kolom( "Iuran Badan", (int)Math.ceil((1.0/100)*penghasilan)));

        int ijtimaBadan;

        switch (badan) {
            case 0:
                ijtimaBadan = (int)Math.ceil((2.5/1200)*penghasilan);
                break;
            case 1:
                ijtimaBadan = 1000;
                break;
            case 2:
                ijtimaBadan = (int)Math.ceil((2.5/1200)*penghasilan);
                break;
            default:
                ijtimaBadan = 0;
                break;
        }

        listKolom.add(new Kolom( "Ijtima Badan", ijtimaBadan));

        int pembangunanMasjidNasional = (int)Math.ceil((double)perjanjianPembangunan/100.0*penghasilan);

        listKolom.add(new Kolom( "Pembangunan Masjid Nasional", pembangunanMasjidNasional));

        listKolom.add(new Kolom( "Tasyakur 100 Thn JAI", (int)12500));

        return listKolom;
    }

    private void hitungJumlah(List<Kolom> listKolom) {
        int jumlah = 0;

        for (Kolom kolom : listKolom) {
            jumlah += kolom.jumlah;
        }

        listKolom.add(new Kolom("Total", jumlah));
    }


    private class Kolom {
        private String nama;
        private int jumlah;

        public Kolom(String _nama, int _jumlah){
            nama = _nama; jumlah = _jumlah;
        }

        @Override
        public String toString(){

            NumberFormat nf = NumberFormat.getInstance();
            String jumlahString = nf.format(jumlah);

            return nama + " - " + "Rp. " + jumlahString;

        }
    }

    public class ListAdapter extends ArrayAdapter<Kolom> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Kolom> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.item_kolom, null);
            }

            Kolom p = getItem(position);

            if (p != null) {

                TextView tt1 = (TextView) v.findViewById(R.id.txtKolom);
                TextView tt2 = (TextView) v.findViewById(R.id.txtJumlah);

                if (tt1 != null) {
                    tt1.setText(p.nama);
                }

                if (tt2 != null) {
                    NumberFormat nf = NumberFormat.getInstance();
                    String jumlahString = nf.format(p.jumlah);

                    tt2.setText(jumlahString);
                }

            }

            return v;
        }

    }
}
