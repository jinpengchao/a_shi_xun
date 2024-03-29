package com.vhome.vhome.children.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.trace.api.track.SupplementMode;
import com.baidu.trace.model.CoordType;
import com.baidu.trace.model.TransportMode;

import java.text.SimpleDateFormat;

import com.vhome.vhome.MyApp;
import com.vhome.chat.R;
import com.vhome.vhome.children.fragment.dialog.DateDialog;
import com.vhome.vhome.parents.TrackUtil.CommonUtil;
import com.vhome.vhome.parents.TrackUtil.Constants;

public class TrackQueryOptionsActivity extends myBaseActivity
        implements CompoundButton.OnCheckedChangeListener {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
    private MyApp trackApp = null;
    private Intent result = null;
    private DateDialog dateDialog = null;
    private Button startTimeBtn = null;
    private Button endTimeBtn = null;
    private DateDialog.Callback startTimeCallback = null;
    private DateDialog.Callback endTimeCallback = null;
    private long startTime = CommonUtil.getCurrentTime();
    private long endTime = CommonUtil.getCurrentTime();
    private Button no =null;
    private Button ok =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.track_query_options_title);
        init();
        trackApp = (MyApp) getApplication();
        no = findViewById(R.id.bt_no);
        ok = findViewById(R.id.bt_ok);
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent to = new Intent();
//                to.setClass(TrackQueryOptionsActivity.this,TrackQueryActivity.class);
//                startActivity(to);
//            }
//        });
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent to = new Intent();
//                to.setClass(TrackQueryOptionsActivity.this,TrackQueryActivity.class);
//                startActivity(to);
//            }
//        });
    }

    private void init() {
        result = new Intent();
        startTimeBtn = (Button) findViewById(R.id.start_time);
        endTimeBtn = (Button) findViewById(R.id.end_time);

        StringBuilder startTimeBuilder = new StringBuilder();
        startTimeBuilder.append(getResources().getString(R.string.start_time));
        startTimeBuilder.append(simpleDateFormat.format(System.currentTimeMillis()));
        startTimeBtn.setText(startTimeBuilder.toString());

        StringBuilder endTimeBuilder = new StringBuilder();
        endTimeBuilder.append(getResources().getString(R.string.end));
        endTimeBuilder.append(simpleDateFormat.format(System.currentTimeMillis()));
        endTimeBtn.setText(endTimeBuilder.toString());

    }

    public void onStartTime(View v) {
        if (null == startTimeCallback) {
            startTimeCallback = new DateDialog.Callback() {
                @Override
                public void onDateCallback(long timeStamp) {
                    TrackQueryOptionsActivity.this.startTime = timeStamp;
                    StringBuilder startTimeBuilder = new StringBuilder();
                    startTimeBuilder.append(getResources().getString(R.string.start_time));
                    startTimeBuilder.append(simpleDateFormat.format(timeStamp * 1000));
                    startTimeBtn.setText(startTimeBuilder.toString());
                }
            };
        }
        if (null == dateDialog) {
            dateDialog = new DateDialog(this, startTimeCallback);
        } else {
            dateDialog.setCallback(startTimeCallback);
        }
        dateDialog.show();
    }

    public void onEndTime(View v) {
        if (null == endTimeCallback) {
            endTimeCallback = new DateDialog.Callback() {
                @Override
                public void onDateCallback(long timeStamp) {
                    TrackQueryOptionsActivity.this.endTime = timeStamp;
                    StringBuilder endTimeBuilder = new StringBuilder();
                    endTimeBuilder.append(getResources().getString(R.string.end_time));
                    endTimeBuilder.append(simpleDateFormat.format(timeStamp * 1000));
                    endTimeBtn.setText(endTimeBuilder.toString());
                }
            };
        }
        if (null == dateDialog) {
            dateDialog = new DateDialog(this, endTimeCallback);
        } else {
            dateDialog.setCallback(endTimeCallback);
        }
        dateDialog.show();
    }

    public void onCancel(View v) {
        super.onBackPressed();
    }

    public void onFinish(View v) {
        result.putExtra("startTime", startTime);
        result.putExtra("endTime", endTime);

        RadioGroup transportModeGroup = (RadioGroup) findViewById(R.id.transport_mode);
        RadioButton transportModeBtn = (RadioButton) findViewById(transportModeGroup.getCheckedRadioButtonId());
        TransportMode transportMode = TransportMode.driving;
        switch (transportModeBtn.getId()) {
            case R.id.driving_mode:
                transportMode = TransportMode.driving;
                break;

            case R.id.riding_mode:
                transportMode = TransportMode.riding;
                break;

            case R.id.walking_mode:
                transportMode = TransportMode.walking;
                break;

            default:
                break;
        }
        result.putExtra("transportMode", transportMode.name());

        RadioGroup supplementModeOptionGroup = (RadioGroup) findViewById(R.id.supplement_mode);
        RadioButton supplementModeOptionRadio =
                (RadioButton) findViewById(supplementModeOptionGroup.getCheckedRadioButtonId());
        SupplementMode supplementMode = SupplementMode.no_supplement;
        switch (supplementModeOptionRadio.getId()) {
            case R.id.no_supplement:
                supplementMode = SupplementMode.no_supplement;
                break;
            case R.id.straight:
                supplementMode = SupplementMode.straight;
                break;
            case R.id.driving:
                supplementMode = SupplementMode.driving;
                break;

            case R.id.riding:
                supplementMode = SupplementMode.driving;
                break;

            case R.id.walking:
                supplementMode = SupplementMode.walking;
                break;

            default:
                break;
        }
        result.putExtra("supplementMode", supplementMode.name());


        RadioGroup coordTypeOutputGroup = (RadioGroup) findViewById(R.id.coord_type_output);
        RadioButton coordTypeOutputOptionBtn =
                (RadioButton) findViewById(coordTypeOutputGroup.getCheckedRadioButtonId());
        CoordType coordTypeOutput = CoordType.bd09ll;
        switch (coordTypeOutputOptionBtn.getId()) {
            case R.id.bd09ll:
                coordTypeOutput = CoordType.bd09ll;
                break;

            case R.id.gcj02:
                coordTypeOutput = CoordType.gcj02;
                break;

            default:
                break;
        }
        result.putExtra("coordTypeOutput", coordTypeOutput.name());

        setResult(Constants.RESULT_CODE, result);
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_trackquery_options;
    }

}
