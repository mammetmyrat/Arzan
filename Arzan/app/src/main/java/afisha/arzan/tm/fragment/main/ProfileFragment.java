package afisha.arzan.tm.fragment.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;
import com.yandex.metrica.impl.ob.G;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import afisha.arzan.tm.R;
import afisha.arzan.tm.activity.ImageViewActivity;
import afisha.arzan.tm.adapter.ConfirmPostAdapter;
import afisha.arzan.tm.adapter.PostAdapter;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.model.Post;
import afisha.arzan.tm.model.UpdateProfile;
import afisha.arzan.tm.model.User;
import afisha.arzan.tm.response.Like;
import afisha.arzan.tm.response.MainPost;
import afisha.arzan.tm.response.Profile;
import afisha.arzan.tm.response.StandardResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private String token, bannerUrl,tag;
    private int myId;
    final int REQUEST_GALLERY = 9544;
    private Bitmap bitmap1,bitmap2;
    String part_image;
    private List<Post> favList = new ArrayList<>();
    private List<Post> likeList = new ArrayList<>();
    private List<Post> pendList = new ArrayList<>();
    private List<Post> confirmList = new ArrayList<>();
    private List<Post> myPosts = new ArrayList<>();
    private User user;
    private ImageView banner, back, userImage,editName;
    private EditText username;
    private TextView region,  likes, confirmed, pending,  favorite_count,followerCount,about,readMore;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private Dialog dialog;
    private ProgressBar progressBar;
    private LinearLayout following_line,confirm_line,pending_line,favorite_line,likes_line;
    private boolean isVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        token = SPManager.getInstance(requireContext()).getData(SPManager.TOKEN, "");
        myId = SPManager.getInstance(requireContext()).getIntData(SPManager.MY_ID, 0);
        Log.e("TAG", "onCreateView: "+myId );
        dialog = new Dialog(requireContext());
        adapter = new PostAdapter(requireActivity());
        SPManager.loading(dialog);

        init(view);
        getProfile(token, myId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in_left, R.anim.out_right)
                        .replace(R.id.frame, new HomeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag= "wallpaper";
                showPopup();
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag= "user_image";
                showPopup();
            }
        });

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username.setEnabled(true);
                username.setFocusable(true);
                username.requestFocus();
            }
        });

        username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_DONE){
                    if (username.getText().toString().length()!=0){
                        username.setEnabled(false);
                        Log.e("TAG", "onEditorAction: "+username.getText());
                        setProfile(username.getText().toString());
                        return true;
                    }else {
                        username.setError(getResources().getString(R.string.short_name));
                    }
                }
                return false;
            }
        });

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVisible){
                    about.setMaxLines(3);
                    isVisible = false;
                }else {
                    about.setMaxLines(Integer.MAX_VALUE);
                    isVisible = true;
                }
            }
        });
        return view;
    }

    private void init(View view) {
        back = view.findViewById(R.id.back_btn);
        banner = view.findViewById(R.id.banner);
        userImage = view.findViewById(R.id.userimage);
        region = view.findViewById(R.id.region);
        username = view.findViewById(R.id.username);
        likes = view.findViewById(R.id.like_count);
        confirmed = view.findViewById(R.id.confirmed_count);
        pending = view.findViewById(R.id.pending_count);
        favorite_count = view.findViewById(R.id.favorite_count);
        recyclerView = view.findViewById(R.id.rv_favorites);
        editName = view.findViewById(R.id.edit_username);
        following_line = view.findViewById(R.id.following_line);
        followerCount = view.findViewById(R.id.follower_count);
        about = view.findViewById(R.id.about);
        progressBar = view.findViewById(R.id.progress);
        likes_line = view.findViewById(R.id.likes);
        confirm_line = view.findViewById(R.id.confirmed);
        favorite_line = view.findViewById(R.id.favorites);
        pending_line = view.findViewById(R.id.pending);
        readMore = view.findViewById(R.id.read_more);

        likes_line.setOnClickListener(this::onClick);
        confirm_line.setOnClickListener(this::onClick);
        favorite_line.setOnClickListener(this::onClick);
        pending_line.setOnClickListener(this::onClick);

    }
    private void onClick(View view){
        switch (view.getId()){
            case R.id.likes:
                if (likeList.size()!=0){
                    likes_line.setBackgroundResource(R.color.check);
                    confirm_line.setBackgroundResource(R.color.white);
                    favorite_line.setBackgroundResource(R.color.white);
                    pending_line.setBackgroundResource(R.color.white);
                    changeAdapter(likeList);
                }

                break;
            case R.id.confirmed:
                if (confirmList.size()!=0){
                    likes_line.setBackgroundResource(R.color.white);
                    favorite_line.setBackgroundResource(R.color.white);
                    confirm_line.setBackgroundResource(R.color.check);
                    pending_line.setBackgroundResource(R.color.white);
                    setNewAdapter(confirmList);
                }
                break;
            case R.id.favorites:
                if (favList.size()!=0){
                    likes_line.setBackgroundResource(R.color.white);
                    confirm_line.setBackgroundResource(R.color.white);
                    favorite_line.setBackgroundResource(R.color.check);
                    pending_line.setBackgroundResource(R.color.white);
                    changeAdapter(favList);
                }
                break;
            case R.id.pending:
                if (pendList.size()!=0){
                    likes_line.setBackgroundResource(R.color.white);
                    confirm_line.setBackgroundResource(R.color.white);
                    favorite_line.setBackgroundResource(R.color.white);
                    pending_line.setBackgroundResource(R.color.check);
                    setNewAdapter(pendList);
                }
                break;

        }
    }
    private void changeAdapter(List<Post> posts){
        adapter.setType(adapter.getType());
        adapter.setPosts(posts);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }

    private void setNewAdapter(List<Post> posts){
        ConfirmPostAdapter adapter =new ConfirmPostAdapter(requireActivity());
        adapter.setPosts(posts);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
    }


    private void showPopup() {
        List<String> image = new ArrayList<>();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.change_image);
        TextView show = bottomSheetDialog.findViewById(R.id.show);
        TextView change = bottomSheetDialog.findViewById(R.id.change);

        assert show != null;
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag.equals("user_image")){
                    image.add(user.getUserImage());
                    Global.images1 = image;
                    startActivity(new Intent(requireContext(), ImageViewActivity.class));
                    bottomSheetDialog.dismiss();
                }else {
                    image.add(bannerUrl);
                    Global.images1 = image;
                    startActivity(new Intent(requireContext(), ImageViewActivity.class));
                    bottomSheetDialog.dismiss();
                }
            }
        });
        assert change != null;
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();

    }

    private void getProfile(String token, int myId) {
        Call<Profile> call = ApiClient.getInstance().getApi().getProfile(token, myId);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful() && response.body().getUser() != null) {
                    dialog.dismiss();
                    setData(response.body().getUser());
                    getMyPosts(token, myId);
                    SPManager.getInstance(getContext()).setData(SPManager.NAME, response.body().getUser().getUsername());
                    SPManager.getInstance(getContext()).setIntData(SPManager.MY_ID, response.body().getUser().getId());
                    SPManager.getInstance(getContext()).setData(SPManager.STATUS, response.body().getUser().getStatus());
                    if (response.body().getBanners().size() != 0) {
                        bannerUrl = response.body().getBanners().get(0).getImage();
                    }
                    Picasso.get().load(bannerUrl)
                            .error(R.drawable.image)
                            .into(banner);
                    user = response.body().getUser();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
    private void getMyPosts(String token,int myId){
        Call<MainPost> liked = ApiClient.getInstance().getApi().getMyPosts(token,myId,"liked");
        liked.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()){
                    if (response.isSuccessful() && response.body().getPosts() != null) {
                        likeList.addAll(response.body().getPosts());
                        likes.setText(String.valueOf(response.body().getCount()));
                    }
                    Log.e("TAG", "liked: "+response.body().getPosts());
                }else Log.e("TAG", "liked error: "+response.code() );
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getLocalizedMessage() );
            }
        });
        Call<MainPost> favorites = ApiClient.getInstance().getApi().getMyPosts(token,myId,"favorites");
        favorites.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body().getPosts() != null) {
                        favList.addAll(response.body().getPosts());
                        changeAdapter(favList);
                        favorite_count.setText(String.valueOf(response.body().getCount()));
                    }
                    Log.e("TAG", "favorites: "+response.body().getPosts());
                }else Log.e("TAG", "favorites error: "+response.code() );
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getLocalizedMessage() );
            }
        });
        Call<MainPost> confirm = ApiClient.getInstance().getApi().getMyPosts(token,myId,"confirmed");
        confirm.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()){
                    if (response.isSuccessful() && response.body().getPosts() != null) {
                        confirmList.addAll(response.body().getPosts());
                        confirmed.setText(String.valueOf(response.body().getCount()));
                    }
                    Log.e("TAG", "confirmed: "+response.body().getPosts());
                }else Log.e("TAG", "confirmed error: "+response.code() );
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getLocalizedMessage() );
            }
        });
        Call<MainPost> pend = ApiClient.getInstance().getApi().getMyPosts(token,myId,"pending");
        pend.enqueue(new Callback<MainPost>() {
            @Override
            public void onResponse(Call<MainPost> call, Response<MainPost> response) {
                if (response.isSuccessful()){
                    if (response.isSuccessful() && response.body().getPosts() != null) {
                        pendList.addAll(response.body().getPosts());
                        pending.setText(String.valueOf(response.body().getCount()));
                    }
                    Log.e("TAG", "confirmed: "+response.body().getPosts());
                }else Log.e("TAG", "confirmed error: "+response.code() );
            }

            @Override
            public void onFailure(Call<MainPost> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getLocalizedMessage() );
            }
        });

    }

    private void setProfile(String username) {
        dialog.show();
        UpdateProfile body = new UpdateProfile();
        body.setUserName(username);
        Call<Profile> call = ApiClient.getInstance().getApi().updateProfile(token, myId, body);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(requireContext(), R.string.username_changed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(requireContext(), R.string.network_error_toast, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Log.v("Tag", "put error: " + t.getLocalizedMessage());

            }
        });
    }
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 41;
    private void permission(){
        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            if (tag.equals("user_image")) changeUSerImage();
            else  changeBanner();
        }else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else {
               ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }

    }

    private void changeBanner(){
        Intent intent1  = new Intent();
        intent1.setType("image/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent1,"Title"),REQUEST_GALLERY);
        tag= "wallpaper";
    }
    private void  changeUSerImage(){
        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Title"),REQUEST_GALLERY);
        tag= "user_image";

    }
    private void setData(User user) {
        username.setText(user.getUsername());
        Picasso.get().load(user.getUserImage()).error(R.drawable.user)
                .into(userImage);
        region.setText(SPManager.getInstance(requireContext()).getData(SPManager.REGION, "Aşgabat"));
        if (user.getStatus().equals("user")) following_line.setVisibility(View.GONE);
        Log.e("TAG", "followers: "+user.getFollowersCount() );
        followerCount.setText(String.valueOf(user.getFollowersCount()));
        about.setText(user.getAbout());
        about.post(new Runnable() {
            @Override
            public void run() {
                int lineCount =   about.getLineCount();
                if (lineCount>2) {
                    readMore.setVisibility(View.VISIBLE);
                } else readMore.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_OK) {
            if (this.tag.equals("user_image")) {
                Uri uri = data.getData();
                try {
                    bitmap1  = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),uri);
                    Uri tempUri  = getImageUri(requireContext().getApplicationContext(),bitmap1);
                    getRealPathFromUri(tempUri);
                    uploadImage();
                }catch (Exception err){

                }
            }else
            {
                Uri uri = data.getData();
                try {
                    bitmap2  = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),uri);
                    Uri tempUri  = getImageUri(requireContext().getApplicationContext(),bitmap2);
                    getRealPathFromUri(tempUri);
                    uploadWallpaper();
                }catch (Exception err){

                }
            }

        }
    }
    private Uri  getImageUri(Context context, Bitmap inImage){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),inImage,"Title",null);
        return Uri.parse(path);

    }
    private String getRealPathFromUri(Uri uri) {
        Cursor cursor = requireContext().getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        part_image =  cursor.getString(index);
        Log.e("Tag", "getRealPathFromUri: "+part_image);
        return part_image;
    }


    private void uploadImage(){
        dialog.show();
        File imageFile = new File(part_image);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        Call<StandardResponse> call = ApiClient.getInstance().getApi().changeImageProfile(token, myId, partImage);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    userImage.setImageBitmap(bitmap1);
                    Log.e("Tag", "userImage calsyldy");
                } else {
                    dialog.dismiss();
                    Log.e("Tag", "userImage calsylmady");
                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e("Tag", "error request " + t.getLocalizedMessage());
                Toast.makeText(requireContext(), R.string.network_error_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadWallpaper(){
        dialog.show();
        File imageFile = new File(part_image);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("banner", imageFile.getName(), requestBody);
        Call<StandardResponse> call = ApiClient.getInstance().getApi().changeImageProfile(token, myId, partImage);
        call.enqueue(new Callback<StandardResponse>() {
            @Override
            public void onResponse(Call<StandardResponse> call, Response<StandardResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    banner.setImageBitmap(bitmap2);
                    Log.e("Tag", "wallpaper calsyldy");
                } else {
                    dialog.dismiss();
                    Log.e("Tag", "userImage calsylmady");

                }
            }

            @Override
            public void onFailure(Call<StandardResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e("Tag", "error request " + t.getLocalizedMessage());
                Toast.makeText(requireContext(), R.string.network_error_toast, Toast.LENGTH_SHORT).show();

            }
        });
    }

}