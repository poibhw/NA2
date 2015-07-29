    package com.example.proprietaire.assignmentna2;
    import com.example.proprietaire.assignmentna2.R;
    import com.example.proprietaire.assignmentna2.Sprite;

    import android.content.Context;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.v7.app.ActionBarActivity;
    import android.view.MotionEvent;
    import android.view.SurfaceHolder;
    import android.view.SurfaceView;
    import android.view.View;

    import java.util.ArrayList;
    import java.util.List;

    public class GameView extends SurfaceView implements Runnable,View.OnClickListener {

    private SurfaceHolder holder;
    private int x = 0, xSpeed = 1;
    private Bitmap bmp;
    Thread thread = null;
    volatile boolean running = false;
    static final long FPS = 10;
    private long lastClick;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<Sprite> sprites2 = new ArrayList<Sprite>();
    private List<TempSprite> temps = new ArrayList<TempSprite>();
    private Bitmap bmpSmoke;
    int score = 0;


    public GameView(Context context) {
        super(context);
        thread = new Thread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {


            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                running = false;
                while (retry) {
                    try {
                        thread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                running = true;
                thread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });

        bmpSmoke = BitmapFactory.decodeResource(getResources(), R.drawable.smoke);
    }

    private void createSprites() {
        sprites.add(createSprite(R.drawable.bad));
        sprites2.add(createSprite(R.drawable.good));
    }

    private Sprite createSprite(int resource) {
        bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bmp);
    }

    int getScore(){
        return score;
    }

    @Override
    protected void onDraw(Canvas canvas) {
       canvas.drawColor(Color.WHITE);
        for (int i = temps.size() - 1; i>= 0; i--) {
            temps.get(i).onDraw(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }
        for (Sprite sprite : sprites2) {
            sprite.onDraw(canvas);
        }
    }



    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while (running) {

            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = getHolder().lockCanvas();
                synchronized (getHolder()) {
                    onDraw(c);
                }
            } finally {
                if (c != null) {
                    getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    thread.sleep(sleepTime);
                else
                    thread.sleep(10);
                } catch (Exception e) {
            }
        }
    }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (System.currentTimeMillis() - lastClick> 300) {
            lastClick = System.currentTimeMillis();
            float x = event.getX();
            float y = event.getY();
            synchronized (getHolder()) {
                for (int i = sprites.size() - 1; i >= 0; i--) {
                    Sprite sprite = sprites.get(i);
                    Sprite sprite2 = sprites2.get(i);
                    if (sprite.isHit(event.getX(), event.getY())) {

                        sprites.remove(sprite);
                        temps.add(new TempSprite(temps, this, x, y, bmpSmoke));
                        sprites.add(createSprite(R.drawable.bad));
                        sprites.add(createSprite(R.drawable.bad));
                        sprites2.add(createSprite(R.drawable.good));

                        score++;

                        break;
                    }
                    if (sprite2.isHit(event.getX(),event.getY())) {
                        Intent intent = new Intent(this, LastActivity.class);
                        startActivity(intent);
                        break;
                    }

               //   TextView textView = (TextView)findViewById(R.id.scoreView);
               //   String s = "" + score;
               //   textView.setText(score);
               //   textView.setText((new Integer(score)).toString(Integer.parseInt(s)));
                }
            }
        }
        return true;
    }
    }
