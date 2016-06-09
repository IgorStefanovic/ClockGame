package rtrk.pnrs.clockgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Setup extends Activity {

    private Button backSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        initViews();
        backSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initViews() {
        backSetup = (Button) findViewById(R.id.backSetup);
    }
}
