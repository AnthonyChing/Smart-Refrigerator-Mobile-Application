package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.example.test2.Connect;

public class MainActivity extends AppCompatActivity {
    TextView txv;
    Uri imgUri;
    ImageView imv;
    WebView wv;
    Button Upload_Btn;
    private String Document_img1="";
    public String URL = "192.168.179.17";
    public String URL_upload = "http://192.168.179.17/index.php?submit=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txv = (TextView) findViewById(R.id.textView);
        imv = (ImageView) findViewById(R.id.imageView);
        WebView wv = (WebView) findViewById(R.id.WebView);
        Upload_Btn = (Button) findViewById(R.id.button3);
        imv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });
        //Upload_Btn.setOnClickListener((View.OnClickListener) this);

        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.invokeZoomPicker();
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(URL);

    }
    //For web returning
    public void onBackPressed(){
        if(wv.canGoBack()){
            wv.goBack();
            return;
        }
        super.onBackPressed();
    }
    //Don't know if I need this
    public void onRequestPermissionResult(int requestCode, String[] Permissions, int[] grantResults){
        if(requestCode == 200){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                savePhoto();
            }
            else{
                Toast.makeText(this, "程式需要此權限才能運作", Toast.LENGTH_LONG);
            }
        }
    }
    //select image
    private void selectImage() {
        final String[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    //Photo taking button
    public void onGet(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        } else {
            savePhoto();
        }
    }
    private void savePhoto(){
        imgUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
        it.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

        startActivityForResult(it, 100);
    }

    @SuppressLint("LongLogTag")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    bitmap=getResizedBitmap(bitmap, 400);
                    imv.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                Log.w("path of image from gallery......******************.........", picturePath+"");
                imv.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
            }
        }
        //old code down here
        /*
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case 100:
                    Intent it = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imgUri);

                    sendBroadcast(it);
                    break;
                case 101:
                    imgUri = data.getData();

                    break;
            }
            showImg();

        }
        else{
            Toast.makeText(this, "沒有拍到照片", Toast.LENGTH_LONG).show();
        }
        //to here

         */
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    void showImg(){
        int iw, ih, vw, vh;
        boolean needRotate;
        BitmapFactory.Options option = new BitmapFactory.Options();

        option.inJustDecodeBounds = true;

        try{
            BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri), null, option);
        }
        catch (IOException e){
            Toast.makeText(this, "讀取照片時發生錯誤", Toast.LENGTH_SHORT).show();
            return;
        }

        iw = option.outWidth;
        ih = option.outHeight;
        vw = imv.getWidth();
        vh = imv.getHeight();

        int scaleFactor;
        if(iw < ih){
            needRotate = false;
            scaleFactor = Math.min(iw/vw, ih/vh);
        }
        else{
            needRotate = true;
            scaleFactor = Math.min(iw/vh, ih/vw);
        }

        option.inJustDecodeBounds = false;
        option.inSampleSize = scaleFactor;

        Bitmap bmp = null;
        try{
            bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri), null, null);
        } catch (IOException e){
            Toast.makeText(this, "無法讀取照片", Toast.LENGTH_LONG).show();
        }

        if(needRotate){
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }

        imv.setImageBitmap(bmp);
        Toast.makeText(this, "照片Uri: " + imgUri.toString(), Toast.LENGTH_SHORT).show();

        //extra information of the picture
        /*new AlertDialog.Builder(this)
                .setTitle("圖檔資訊")
                .setMessage("圖檔Uri: " + imgUri.toString() +
                        "\n原始尺寸: " + iw + "x" + ih +
                        "\n載入尺寸: " + bmp.getWidth() + "x" + bmp.getHeight() +
                        "\n顯示尺寸: " + vw + "x" + vh + (needRotate? "(旋轉)" : "")
                )
                .setNeutralButton("關閉", null)
                .show();*/
    }
    //Choose photo from your phone
    public void onPick(View v){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it, 101);
    }

    public void onUpload(View v) throws IOException {
        if (Document_img1.equals("") || Document_img1.equals(null)) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("No Image Selected");
            alertDialogBuilder.setMessage("No image selected, please select an image.");
            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            alertDialogBuilder.show();
            return;
        }
        else{
            if (checkWifi(this) == true) {
                Log.e("test", Document_img1);
                //txv.setText(Document_img1);
                Map<String, String> post = new HashMap<>();
                post.put("image",  Document_img1);
                Log.e("mark", "Yoooooooooooooooooooooooooooooooooooooooooooooooooo");
                MyDownloadTask.httpConnectionPost(URL_upload, post);
            } else {
                Toast.makeText(this,"You are not online!!!!",Toast.LENGTH_LONG).show();
                Log.v("Home", "############################You are not online!!!!");
            }

        }
    }
    public static boolean checkWifi(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        @SuppressLint("MissingPermission") NetworkInfo info = connectivity.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI;
    }
    /*
    public static String httpConnectionPost(String apiUrl,Map<String, String> params) {
        HttpURLConnection conn = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            //conn.setConnectTimeout(10000);
            //conn.setReadTimeout(10000);
            conn.setDoInput(true); //允許輸入流，即允許下載
            conn.setDoOutput(true); //允許輸出流，即允許上傳
            conn.setUseCaches(false); //設置是否使用緩存 //原false
            conn.connect();

            OutputStream os = conn.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            String jsonString = getJSONString(params);
            Log.d("Json", jsonString);
            writer.writeBytes(jsonString);
            writer.flush();
            writer.close();
            os.close();
            //Get Response
            InputStream is = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            reader.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if(conn!=null) {
                conn.disconnect();
            }
        }

        return response.toString();
    }

     */

    public static String getJSONString(Map<String, String> params){
        JSONObject json = new JSONObject();
        for(String key:params.keySet()) {
            try {
                json.put(key, params.get(key));
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return json.toString();
    }
}

class MyDownloadTask extends AsyncTask<Void,Void,Void>
{


    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    protected void onPreExecute() {
        //display progress dialog.

    }
    public static String httpConnectionPost(String apiUrl,Map<String, String> params) {
        HttpURLConnection conn = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            //conn.setConnectTimeout(10000);
            //conn.setReadTimeout(10000);
            conn.setDoInput(true); //允許輸入流，即允許下載
            conn.setDoOutput(true); //允許輸出流，即允許上傳
            conn.setUseCaches(false); //設置是否使用緩存 //原false
            conn.connect();

            OutputStream os = conn.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            String jsonString = MainActivity.getJSONString(params);
            Log.d("Json", jsonString);
            writer.writeBytes(jsonString);
            writer.flush();
            writer.close();
            os.close();
            //Get Response
            InputStream is = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            reader.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if(conn!=null) {
                conn.disconnect();
            }
        }

        return response.toString();
    }



    protected void onPostExecute(Void result) {
        // dismiss progress dialog and update ui
    }
}