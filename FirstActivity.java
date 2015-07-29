    package com.example.proprietaire.assignmentna2;

    import android.support.v7.app.ActionBarActivity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;


    public class FirstActivity extends ActionBarActivity implements View.OnClickListener {

        Button button;
        Intent intent;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_first);

            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(this);
        }

        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button:
                    intent = new Intent(this, GameActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }
