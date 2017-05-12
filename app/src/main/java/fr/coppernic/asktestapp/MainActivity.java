package fr.coppernic.asktestapp;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import fr.coppernic.cpcframework.cpcask.OnGetReaderInstanceListener;
import fr.coppernic.cpcframework.cpcask.Reader;
import fr.coppernic.cpcframework.cpcpowermgmt.cone.PowerMgmt;
import fr.coppernic.sdk.utils.core.CpcDefinitions;

public class MainActivity extends AppCompatActivity {

    private PowerMgmt mPowerMgmt;

    private Reader mReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPowerMgmt = new PowerMgmt(this);
        Reader.getInstance(this, new OnGetReaderInstanceListener() {
            @Override
            public void OnGetReaderInstance(Reader reader) {
                mReader = reader;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Powers on RFID reader
        powerRfidReader(true);

        SystemClock.sleep(500);

        mReader.cscOpen(CpcDefinitions.ASK_READER_PORT, 115200, false);
        StringBuilder sb = new StringBuilder();
        mReader.cscVersionCsc(sb);
        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Powers off RFID reader
        powerRfidReader(false);
    }

    /**
     * Powers on/off RFID reader
     * @param on
     */
    void powerRfidReader(boolean on) {
        mPowerMgmt.setPower(PowerMgmt.PeripheralTypesCone.RfidSc,
                PowerMgmt.ManufacturersCone.Ask,
                PowerMgmt.ModelsCone.Ucm108,
                PowerMgmt.InterfacesCone.ExpansionPort,
                on);
    }
}
