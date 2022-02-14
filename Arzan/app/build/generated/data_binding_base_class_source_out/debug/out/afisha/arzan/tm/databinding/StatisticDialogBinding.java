// Generated by view binder compiler. Do not edit!
package afisha.arzan.tm.databinding;

import afisha.arzan.tm.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class StatisticDialogBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView backBtn;

  @NonNull
  public final MaterialCardView cardRegion;

  @NonNull
  public final TextView region;

  @NonNull
  public final TabLayout tab;

  @NonNull
  public final MaterialCardView toolbar;

  @NonNull
  public final ViewPager viewPager;

  private StatisticDialogBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView backBtn,
      @NonNull MaterialCardView cardRegion, @NonNull TextView region, @NonNull TabLayout tab,
      @NonNull MaterialCardView toolbar, @NonNull ViewPager viewPager) {
    this.rootView = rootView;
    this.backBtn = backBtn;
    this.cardRegion = cardRegion;
    this.region = region;
    this.tab = tab;
    this.toolbar = toolbar;
    this.viewPager = viewPager;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static StatisticDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static StatisticDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.statistic_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static StatisticDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back_btn;
      ImageView backBtn = rootView.findViewById(id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.card_region;
      MaterialCardView cardRegion = rootView.findViewById(id);
      if (cardRegion == null) {
        break missingId;
      }

      id = R.id.region;
      TextView region = rootView.findViewById(id);
      if (region == null) {
        break missingId;
      }

      id = R.id.tab;
      TabLayout tab = rootView.findViewById(id);
      if (tab == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialCardView toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.viewPager;
      ViewPager viewPager = rootView.findViewById(id);
      if (viewPager == null) {
        break missingId;
      }

      return new StatisticDialogBinding((ConstraintLayout) rootView, backBtn, cardRegion, region,
          tab, toolbar, viewPager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
