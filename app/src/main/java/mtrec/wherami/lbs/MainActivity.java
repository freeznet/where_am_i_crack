package mtrec.wherami.lbs;

import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import mtrec.wherami.lbs.decrypt.Bin;
import mtrec.wherami.lbs.decrypt.Deserializer;
import mtrec.wherami.lbs.decrypt.Ids;
import mtrec.wherami.lbs.decrypt.PAll;
import mtrec.wherami.lbs.utils.DecryptUtil;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button move_files = (Button) findViewById(R.id.button);
        move_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFilesToSdCard();
            }
        });

        Button ids_file = (Button) findViewById(R.id.button2);
        ids_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crack_ids_file();
            }
        });

        Button pall_file = (Button) findViewById(R.id.button4);
        pall_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crack_p_all_file();
            }
        });

        Button bin_file = (Button) findViewById(R.id.button3);
        bin_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crack_bin_file();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void crack_ids_file(){
        String readfilepath = getExternalFilesDir(null) + "/mtrec/position_data/hkust/ids.txt";
        byte[] bytes = DecryptUtil.readFileToByte(readfilepath);
        try {
            Deserializer.deserializer(bytes, Ids.getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crack_bin_file(){
        String readfilepath = getExternalFilesDir(null) + "/mtrec/position_data/hkust/1000/bin.txt";
        byte[] bytes = DecryptUtil.readFileToByte(readfilepath);
        try {
            Deserializer.deserializer(bytes, new Bin());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crack_p_all_file(){
        String readfilepath = getExternalFilesDir(null) + "/mtrec/position_data/hkust/1000/p_all.txt";
        byte[] bytes = DecryptUtil.readFileToByte(readfilepath);
        try {
            Deserializer.deserializer(bytes, new PAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decode(String file){
        if (file.endsWith("txt") && !file.endsWith("ids.txt") ) {
            byte[] bytes = DecryptUtil.readFileToByte(file);
            try {
                FileOutputStream fos = new FileOutputStream(file + ".de");
                fos.write(bytes);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(file.endsWith("ids.txt")){
            byte[] bytes = DecryptUtil.readFileToByte(file);
            try {
                FileOutputStream fos = new FileOutputStream(file + ".de");
                fos.write(bytes);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    String TARGET_BASE_PATH = "";

    public void copyFilesToSdCard() {
        TARGET_BASE_PATH = getExternalFilesDir(null) + "/";
        copyFileOrDir("mtrec"); // copy all files in assets folder in my project
    }

    private void copyFileOrDir(String path) {
        AssetManager assetManager = this.getAssets();
        String assets[] = null;
        try {
            Log.i("tag", "copyFileOrDir() " + path);
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path);
            } else {
                String fullPath =  TARGET_BASE_PATH + path;
                Log.i("tag", "path="+fullPath);
                File dir = new File(fullPath);
                if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                    if (!dir.mkdirs())
                        Log.i("tag", "could not create dir "+fullPath);
                for (int i = 0; i < assets.length; ++i) {
                    String p;
                    if (path.equals(""))
                        p = "";
                    else
                        p = path + "/";

                    if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
                        copyFileOrDir( p + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;
        try {
            Log.i("tag", "copyFile() "+filename);
            in = assetManager.open(filename);
            if (filename.endsWith(".jpg")) // extension was added to avoid compression on APK file
                newFileName = TARGET_BASE_PATH + filename.substring(0, filename.length()-4);
            else
                newFileName = TARGET_BASE_PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            decode(newFileName);
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of "+newFileName);
            Log.e("tag", "Exception in copyFile() "+e.toString());
        }

    }
}
