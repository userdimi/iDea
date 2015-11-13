package com.example.dimitri.idea;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;




public class MainActivity extends AppCompatActivity {
    int windowWidth, windowHeight, screenCenter, x_cord, y_cord;
    int likes = 0;
    RelativeLayout mainLayoutView;
    float alphaValue = 0;

    //ListView Layout
    private ListView mDrawerList;

    //Toggle
    private ActionBarDrawerToggle mDrawerToggle;
    //Drawer Layout
    private DrawerLayout mDrawerLayout;
    //Titel der Aktivity
    private String mActivityTitle;

    // String ArrayAdapter
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Kamerabutton initialisieren
        final FloatingActionButton btnCamera = (FloatingActionButton) findViewById(R.id.btnCamera);
        //Stiftbutton initialisieren
        final FloatingActionButton btnPen = (FloatingActionButton) findViewById(R.id.btnPen);

        //MainLayout initialisieren
        mainLayoutView = (RelativeLayout) findViewById(R.id.mainLayoutView);

        //NavListView initialisieren
        mDrawerList = (ListView) findViewById(R.id.navList);

        //Drawer Layout initialisieren
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //Hinzufuegen von NavItems
        addDrawerItems();

        //Deklarieren des Drawers
        setupDrawer();

        //Icon Anzeigen lassen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Fensterbreite ermitteln
        windowWidth = getWindowManager().getDefaultDisplay().getWidth();

        //Fensterhoehe ermitteln
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();

        //Fenstermitte ermitteln
        screenCenter = windowWidth / 2;

        //Bilder initialisieren
        int [] imageList = new int[] {R.drawable.ic_test_img_1, R.drawable.ic_test_img_2,
                R.drawable.ic_test_img_3, R.drawable.ic_test_img_4, R.drawable.ic_test_img_5,
                R.drawable.ic_test_img_6};

        //Bilder ins Layout laden
        for (int i = 0; i < 6; i++) {
            final RelativeLayout relView = new RelativeLayout(this);
            relView.setLayoutParams(new RelativeLayout.LayoutParams((windowWidth - 80), windowHeight -80));
            relView.setX(40);
            relView.setY(40);
            relView.setTag(i);
            relView.setBackgroundResource(imageList[i]);

            /*
            //Drehung der Bilder
            if (i == 0) {
                relView.setRotation(-1);

            } else if (i == 1) {
                relView.setRotation(-5);

            } else if (i == 2) {
                relView.setRotation(3);

            } else if (i == 3) {
                relView.setRotation(7);

            } else if (i == 4) {
                relView.setRotation(-2);

            } else if (i == 5) {
                relView.setRotation(5);
            }
            */

            final Button imageLike = new Button(this);
            imageLike.setLayoutParams(new ActionBar.LayoutParams(100, 50));
            imageLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.stamp_liked));
            imageLike.setX(20);
            imageLike.setY(80);
            imageLike.setAlpha(alphaValue);

            relView.addView(imageLike);

            final Button imagePass = new Button(this);
            imagePass.setLayoutParams(new ActionBar.LayoutParams(100, 50));
            imagePass.setBackgroundDrawable(getResources().getDrawable(R.drawable.pass));
            imagePass.setX((windowWidth - 200));
            imagePass.setY(100);
            imagePass.setRotation(45);
            imagePass.setAlpha(alphaValue);

            relView.addView(imagePass);


            //Touchlistener zum Silden
            relView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    relView.setX(x_cord - screenCenter + 40);
                    relView.setY(y_cord - 150);

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();
                            relView.setX(x_cord - screenCenter + 40);
                            relView.setY(y_cord - 150);
                            if (x_cord >= screenCenter) {
                                relView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord > (screenCenter + (screenCenter / 2))) {
                                    imageLike.setAlpha(1);
                                    if (x_cord > (windowWidth - (screenCenter / 4))) {
                                        likes = 2;
                                    } else {
                                        likes = 0;
                                    }
                                } else {
                                    likes = 0;
                                    imageLike.setAlpha(0);
                                }
                                imagePass.setAlpha(0);
                            } else {

                                //Rotation
                                relView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                                if (x_cord < (screenCenter / 2)) {
                                    imagePass.setAlpha(1);
                                    if (x_cord < screenCenter / 4) {
                                        likes = 1;
                                    } else {
                                        likes = 0;
                                    }
                                } else {
                                    likes = 0;
                                    imagePass.setAlpha(0);
                                }
                                imageLike.setAlpha(0);
                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();

                            Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                            imagePass.setAlpha(0);
                            imageLike.setAlpha(0);

                            if (likes == 0) {
                                Log.e("Event Status", "Nothing");
                                relView.setX(40);
                                relView.setY(40);
                                relView.setRotation(0);
                            } else if (likes == 1) {
                                Log.e("Event Status", "Passed");
                                    mainLayoutView.removeView(relView);
                                }
                                else if (likes == 2) {
                                Log.e("Event Status", "Liked");
                                mainLayoutView.removeView(relView);
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

            mainLayoutView.addView(relView);
        }

        //Listener fuer den Kamerabutton
        btnCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });

        //Listener fuer den Stiftbutton
        btnPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PenActivity.class));
            }
        });
    }

    //Hinzufuegen von NavItems
    private void addDrawerItems() {
        String[] osArray = {"Home", "My Compositions", "Layouts"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        //Clicklistener fuer die NavItems
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an other revolution, Che!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Deklarieren des Drawers
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            //Setzt den Titel wenn geöffnet
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            //Wird aufgerufen, wenn komplett geschlossen
            public void onDrawerClosed (View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();

            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //Action Handler fuer den Pfeil Button
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
