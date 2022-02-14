package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import afisha.arzan.tm.R;
import afisha.arzan.tm.adapter.PhotoViewAdapter;
import afisha.arzan.tm.app.Global;
import afisha.arzan.tm.app.SPManager;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewPager imagePager = findViewById(R.id.imagePager);
        List<String> images = new ArrayList<>();
        images = Global.images1;
        PhotoViewAdapter pager = new PhotoViewAdapter(images,this);
        imagePager.setAdapter(pager);
        imagePager.setCurrentItem(SPManager.getInstance(this).getIntData(SPManager.POS,0));
    }
}