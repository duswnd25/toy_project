package app.kimyeonjung.aftercall;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Main extends ActionBarActivity {

    private String body = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences SMS_BODY = getSharedPreferences("sms_body", 0);

        body = SMS_BODY.getString("sms_body", "저장된 문구가 없습니다.");
        Button save = (Button) findViewById(R.id.save_contents);
        EditText contents = (EditText) findViewById(R.id.send_contents);

        contents.setText(body);
        save.setOnClickListener(new LinearLayout.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences SMS_BODY = getSharedPreferences("sms_body", 0);

                body = ((EditText) Main.this.findViewById(R.id.send_contents))
                        .getText().toString(); // 이름
                Editor admin_editor = SMS_BODY.edit();
                admin_editor.putString("sms_body", body);
                admin_editor.commit();
                Toast.makeText(getApplicationContext(), "저장하였습니다.",
                        Toast.LENGTH_LONG).show();

                finish();

            }
        });
    }

}
