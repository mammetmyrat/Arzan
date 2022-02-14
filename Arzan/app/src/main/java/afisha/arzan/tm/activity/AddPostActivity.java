package afisha.arzan.tm.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.google.android.material.button.MaterialButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.ImagesAdapter;
import afisha.arzan.tm.adapter.RegionAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Main;
import afisha.arzan.tm.model.Region;
import afisha.arzan.tm.response.StandardResponse;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AddPostActivity extends AppCompatActivity {

    private RecyclerView recyclerView,rvImages;
    public static TextView regionText;
    private ImagesAdapter imagesAdapter;
    private ImageView add_image,back;
    private EditText title,desc;
    private MaterialButton send;
    private androidx.appcompat.app.AlertDialog dialog;
    public static ArrayList<File> selectedImages = new ArrayList<>();
    private Context context;
    private Dialog loading;
    public static ExpansionLayout expansionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = AddPostActivity.this;
        loading = new Dialog(this);
        SPManager.getInstance(this).getData(SPManager.LANG,"rus");
        setContentView(R.layout.activity_add_post);
        recyclerView = findViewById(R.id.rv_regions);
        rvImages = findViewById(R.id.rv_images);
        regionText = findViewById(R.id.region_text);
        add_image = findViewById(R.id.add_image);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        send = findViewById(R.id.send);
        back = findViewById(R.id.back_btn);
        expansionLayout = findViewById(R.id.expanded_layout);

        imagesAdapter = new ImagesAdapter(context);

        RegionAdapter adapter = new RegionAdapter(AddPostActivity.this, Global.regions);
        adapter.setType(RegionAdapter.SMALL);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        rvImages.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rvImages.setAdapter(imagesAdapter);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toChekRequests();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void toChekRequests() {
        if (title.getText().length() == 0 || desc.getText().length() == 0 || Global.postImages.size() <= 0 ||Global.region==null) {
            if (title.getText().length() == 0&&title.getText().length()<=3) {
                Toast.makeText(this, getResources().getString(R.string.wrong_title), Toast.LENGTH_SHORT).show();
            }
            if ( desc.getText().length() == 0&&desc.getText().length()<=10) {
                Toast.makeText(this, getResources().getString(R.string.wrong_desc), Toast.LENGTH_SHORT).show();

            }
            if (Global.postImages.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.wrong_image_select), Toast.LENGTH_SHORT).show();
            }
            if (Global.region == null) {
                Toast.makeText(this, getResources().getString(R.string.sel_region), Toast.LENGTH_SHORT).show();
            }
        } else {
            postPost();
            SPManager.loading(loading);
        }
    }

    private void requestPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectImageFromGallery();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                selectImageFromGallery();

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
    private void selectImageFromGallery() {
        TedBottomPicker.with(AddPostActivity.this)
                .setPeekHeight(1600)
                .setSelectMaxCount(5)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        imagesAdapter.setData(uriList);
                        Global.postImages = uriList;
                    }
                });
    }
    private List<MultipartBody.Part> getImageData(){
        for(Uri uri : Global.postImages /* List of the files you want to send */) {
            File file = new File(uri.getPath());
            selectedImages.add(file);
            Log.e(TAG, "getImageData: "+selectedImages );

        }

        List<MultipartBody.Part> result = new ArrayList<>();

        ArrayList<File> files = selectedImages;
        if (files == null) files = new ArrayList<>();
        for (int i = 0; i<files.size();i++){
            RequestBody requestBody = RequestBody.create(SPManager.IMAGE_TYPE , files.get(i));
            result.add(SPManager.partGenerate(requestBody , "images"+i));
        }

        return result;
    }

    private Map<String, RequestBody> getTextData() {
        Map<String, RequestBody> result = new HashMap<>();
        result.put("title", RequestBody.create(SPManager.TEXT_TYPE, String.valueOf(title.getText())));
        result.put("content", RequestBody.create(SPManager.TEXT_TYPE, String.valueOf(desc.getText())));

        return result;
    }

    private List<RequestBody> getRegionsData() {
        List<RequestBody> result = new ArrayList<>();
            RequestBody regionsBody = RequestBody.create(SPManager.TEXT_TYPE, String.valueOf(Global.region.getId()));
            result.add(regionsBody);
        return result;
    }
    private List<RequestBody> getCategoriesData() {
        List<RequestBody> result = new ArrayList<>();
            RequestBody categoriesBody = RequestBody.create(SPManager.TEXT_TYPE, String.valueOf(142));
            result.add(categoriesBody);
        return result;
    }

    private void postPost() {
        String token =SPManager.getInstance(this).getData(SPManager.TOKEN,"");
        Log.e(TAG, "token: "+token );
        Call<StandardResponse> call = ApiClient.getInstance().getApi().postPosts(token, getImageData(), getTextData(),getRegionsData(),getCategoriesData() );
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    Dialog dialog = new Dialog(context);
                    SPManager.doneDialog(context,getResources().getString(R.string.post_added),dialog);
                    MaterialButton button = dialog.findViewById(R.id.ok);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(context, MainActivity.class));
                        }
                    });
                    Toast.makeText(context, R.string.sended, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Post gitdi ");
                } else {
                    Log.e(TAG, "Post gitmedi: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AddPostActivity.this, getResources().getString(R.string.network_error_toast), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "post error: " + t.getLocalizedMessage());
            }
        });
    }


}