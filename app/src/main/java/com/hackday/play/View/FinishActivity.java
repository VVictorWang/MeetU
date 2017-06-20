package com.hackday.play.View;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.platform.comapi.map.B;
import com.hackday.play.First;
import com.hackday.play.R;
import com.hackday.play.Utils.GlobaData;
import com.hackday.play.Utils.MyActivityManager;
import com.hackday.play.Utils.Utils;

public class FinishActivity extends AppCompatActivity {
    private View view;
    private Button getsafely;
    private TextView accident;
    private TextView dia_text;

    private Button positive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        MyActivityManager.getInstance().pushActivity(FinishActivity.this);
        view = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        InitView();
    }

    private void InitView() {
        getsafely = (Button) findViewById(R.id.safe_get);
        accident = (TextView) findViewById(R.id.happen_something);
        dia_text = (TextView) view.findViewById(R.id.dialog_text);
        positive = (Button) view.findViewById(R.id.dialog_positive);
        dia_text.setText("哇～,你成功和ta分享了一次雨伞\n     你的爱心值增加了1");
        final AlertDialog dialog = new AlertDialog.Builder(FinishActivity.this).create();
        getsafely.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setView(view);
                dialog.show();
            }
        });
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int level = Utils.getIntValue(FinishActivity.this, GlobaData.LOVE_LEVEL);
                level++;
                Utils.putIntValue(FinishActivity.this, GlobaData.LOVE_LEVEL,level);
                dialog.dismiss();
                MyActivityManager.getInstance().popAllActivity();
                Utils.startActivity(FinishActivity.this,MainActivity.class);
            }
        });
    }
}
