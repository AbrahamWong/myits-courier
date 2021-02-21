package id.ac.its.myits.courier.ui.proof;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import id.ac.its.myits.courier.data.DataManager;
import id.ac.its.myits.courier.ui.base.BasePresenter;
import id.ac.its.myits.courier.utils.AppLogger;
import id.ac.its.myits.courier.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Response;


public class ProofPresenter<V extends ProofMvpView> extends BasePresenter<V>
        implements ProofMvpPresenter<V> {

    public static final int PERMISSION_STORAGE = 101;
    public static final int REQUEST_TAKE_PHOTO = 501;
    String currentPhotoPath = "";
    private Activity proofActivity;
    private String kodePaket;

    @Inject
    public ProofPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    @Override
    public void getProof(String kodePaket) {
        proofActivity = getMvpView().getProofActivity();
        this.kodePaket = kodePaket;
        checkStoragePermission();
    }

    public void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(proofActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            Toast.makeText(proofActivity, "Permission granted", Toast.LENGTH_SHORT).show();

            // Here is the main function to get the picture, in the vicinity.
            capturePhotoIntent();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(proofActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(proofActivity).setTitle("Permission Required")
                    .setMessage("Storage permission is required to use this application.")
                    .setPositiveButton("Allow", (dialogInterface, i) -> {
                        dialogInterface.cancel();
                        ActivityCompat.requestPermissions(
                                proofActivity,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                PERMISSION_STORAGE
                        );
                    })
                    .setNegativeButton("Deny", (dialogInterface, i) -> dialogInterface.cancel()).show();
        } else {
            ActivityCompat.requestPermissions(
                    proofActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_STORAGE
            );
        }
    }

    private void capturePhotoIntent() {
        // Oh God the code is so high level
        // Implicit intent featuring MediaStore.ACTION_IMAGE_CAPTURE here.
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ComponentName cname = takePictureIntent.resolveActivity(proofActivity.getPackageManager());

        File photoFile;
        try {
            photoFile = createImageFile();
        } catch (Exception ex) {
            photoFile = null;
        }
        Uri photoURI = FileProvider.getUriForFile(proofActivity, "id.ac.its.myits.courier", photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        // Then after all file is created and set and done, launch the intent.
        proofActivity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }

    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = proofActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            File imageFile = File.createTempFile(
                    "JPEG_" + timeStamp + "_", /* prefix */
                    ".jpg", /* suffix */
                    storageDir /* directory */);
            currentPhotoPath = imageFile.getAbsolutePath();
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void convertImgToBase64() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String imageString;

        int targetW = 400;
        int targetH = 600;
        int actualW = 0;
        int actualH = 0;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        double scale = (double) (photoH * photoW) / (double) (targetW * targetH);
        if (scale > 1) {
            double ratio = Math.sqrt(scale);
            actualH = (int) ((double) photoH / ratio);
            actualW = (int) ((double) photoW / ratio);
        }

        double scaleFactor = Math.min((double) photoW / (double) actualW, (double) photoH / (double) actualH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 2;
        bmOptions.inPurgeable = true;

        AppLogger.d("BASE64: convertImgTo64: \nCaptured photo width: " + photoW + ", reduced to: " + actualW +
                "\nCaptured photo height: " + photoH + ", reduced to: " + actualH +
                "\nSmallest scale factor: " + scaleFactor +
                "\nTesting: " + bmOptions.inSampleSize);

        try {
            Bitmap bm = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            bm.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(imgBytes, Base64.DEFAULT);

            // Kirimkan gambar dengan format Base64 ke database dengan API yang disediakan
            logLargeString(imageString, "BASE64");
            postBase64Image(kodePaket, imageString);
        } catch (OutOfMemoryError e) {
            getMvpView().showMessage("Terjadi kesalahan, silahkan coba lagi.");
            proofActivity.finish();
        }
    }

    private void logLargeString(String content, String TAG) {
        if (content.length() > 3000) {
            AppLogger.d(TAG + ": " + content.substring(0, 3000));
            logLargeString(content.substring(3000), TAG);
        } else {
            AppLogger.d(TAG + ": " + content);
        }
    }

    private void postBase64Image(String kodePaket, String image) {
        getDataManager().postProofOfDelivery(kodePaket, image, new OkHttpResponseListener() {
            @Override
            public void onResponse(Response response) {
                getMvpView().showMessage("Foto berhasil diambil, paket sukses diantarkan.");
            }

            @Override
            public void onError(ANError anError) {
                getMvpView().showMessage("Terjadi kesalahan. Silahkan coba lagi.");
                getProof(kodePaket);
            }
        });
        proofActivity.finish();
    }
}


