    package com.example.proprietaire.assignmentna2;

    import android.app.ActionBar;
    import android.app.Activity;
    import android.os.Bundle;
    import android.view.Gravity;
    import android.widget.AutoCompleteTextView;
    import android.widget.LinearLayout;
    import android.widget.RelativeLayout;
    import android.widget.TextView;


    public class GameActivity extends Activity {
        GameView GV;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
            initializeGameView();
        }

        private void initializeGameView() {
            RelativeLayout relative = (RelativeLayout) findViewById(R.id.);

            LinearLayout linear = new LinearLayout(this);

            //Layout stuff
            linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            linear.setOrientation(LinearLayout.VERTICAL);
            linear.setGravity(Gravity.TOP);
            //add more stuff, play around with whatever you want to do here

            linear.addView(GV);

            scoreView.setText(GV.getScore());
            relative.addView(scoreView);

            //Finally, don't forget to add the linear layout to the relative layout
            relative.addView(linear);
        }
    }
