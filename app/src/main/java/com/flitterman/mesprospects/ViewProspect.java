package com.flitterman.mesprospects;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewProspect extends AppCompatActivity {

    Prospect p;

    private TextView prospectName;
    private TextView prospectAddr1;
    private TextView prospectAddr2;
    private TextView prospectPostCode;
    private TextView prospectTown;
    private TextView prospectTelephone;
    private TextView prospectMail;
    private TextView prospectCompanyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prospect);

        prospectName = (TextView)findViewById(R.id.viewNameTitle);
        prospectAddr1 = (TextView)findViewById(R.id.addr1);
        prospectAddr2 = (TextView)findViewById(R.id.addr2);
        prospectPostCode = (TextView)findViewById(R.id.fieldPostCode);
        prospectTown = (TextView)findViewById(R.id.fieldtown);
        prospectTelephone = (TextView)findViewById(R.id.fieldphone);
        prospectMail = (TextView)findViewById(R.id.fieldmail);
        prospectCompanyName = (TextView)findViewById(R.id.fieldcompanyname);

        p = new Prospect();
        p = (Prospect) getIntent().getSerializableExtra("prospect");

        Log.e("HeyOh", p.getName());

        prospectName.setText(p.getFirstName() + " " + p.getName());
        prospectAddr1.setText(p.getAddr1());
        prospectAddr2.setText(p.getAddr2());
        prospectPostCode.setText(p.getPostCode());
        prospectTown.setText(p.getTown());
        prospectTelephone.setText(p.getTelephone());
        prospectMail.setText(p.getMail());
        prospectCompanyName.setText(p.getCompanyName());

    }

    public void call(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + p.getTelephone()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
