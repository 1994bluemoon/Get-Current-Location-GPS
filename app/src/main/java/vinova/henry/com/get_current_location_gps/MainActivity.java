package vinova.henry.com.get_current_location_gps;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationMangaer=null;
    private MyLocationListener locationListener;

    private static final String TAG = "Debug";
    private Boolean flag = false;

    @BindView(R.id.editTextLocation)
    EditText editTextLocation;
    @BindView(R.id.btnLocation)
    Button btnLocation;
    @BindView(R.id.layButtonH)
    LinearLayout layButtonH;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.layloadingH)
    LinearLayout layloadingH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        locationListener = new MyLocationListener(this);

        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressBar1.setVisibility(View.VISIBLE);
        locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


    }

    @SuppressLint("MissingPermission")
    @OnClick({R.id.btnLocation, R.id.layButtonH})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLocation:
                flag = displayGpsStatus();
                if (flag) {

                    Log.v(TAG, "onClick");

                    locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10,locationListener);

                } else {
                    alertbox("Gps Status!!", "Your GPS is: OFF");
                }
                break;
            case R.id.layButtonH:
                break;
        }
    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }



}
