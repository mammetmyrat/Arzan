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
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRegionBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final TextView addCard;

  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final MaterialCardView cardBg;

  @NonNull
  public final MaterialCardView cardImage;

  @NonNull
  public final MaterialCardView cardMain;

  @NonNull
  public final CollapsingToolbarLayout collapsingToolbarLayout;

  @NonNull
  public final ImageView image;

  @NonNull
  public final NestedScrollView nested;

  @NonNull
  public final MaterialButton next;

  @NonNull
  public final RecyclerView rvRegions;

  @NonNull
  public final TextView title;

  @NonNull
  public final Toolbar toolbar;

  private FragmentRegionBinding(@NonNull CoordinatorLayout rootView, @NonNull TextView addCard,
      @NonNull AppBarLayout appBar, @NonNull MaterialCardView cardBg,
      @NonNull MaterialCardView cardImage, @NonNull MaterialCardView cardMain,
      @NonNull CollapsingToolbarLayout collapsingToolbarLayout, @NonNull ImageView image,
      @NonNull NestedScrollView nested, @NonNull MaterialButton next,
      @NonNull RecyclerView rvRegions, @NonNull TextView title, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.addCard = addCard;
    this.appBar = appBar;
    this.cardBg = cardBg;
    this.cardImage = cardImage;
    this.cardMain = cardMain;
    this.collapsingToolbarLayout = collapsingToolbarLayout;
    this.image = image;
    this.nested = nested;
    this.next = next;
    this.rvRegions = rvRegions;
    this.title = title;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRegionBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRegionBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_region, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRegionBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_card;
      TextView addCard = rootView.findViewById(id);
      if (addCard == null) {
        break missingId;
      }

      id = R.id.appBar;
      AppBarLayout appBar = rootView.findViewById(id);
      if (appBar == null) {
        break missingId;
      }

      id = R.id.card_bg;
      MaterialCardView cardBg = rootView.findViewById(id);
      if (cardBg == null) {
        break missingId;
      }

      id = R.id.card_image;
      MaterialCardView cardImage = rootView.findViewById(id);
      if (cardImage == null) {
        break missingId;
      }

      id = R.id.card_main;
      MaterialCardView cardMain = rootView.findViewById(id);
      if (cardMain == null) {
        break missingId;
      }

      id = R.id.collapsing_toolbar_layout;
      CollapsingToolbarLayout collapsingToolbarLayout = rootView.findViewById(id);
      if (collapsingToolbarLayout == null) {
        break missingId;
      }

      id = R.id.image;
      ImageView image = rootView.findViewById(id);
      if (image == null) {
        break missingId;
      }

      id = R.id.nested;
      NestedScrollView nested = rootView.findViewById(id);
      if (nested == null) {
        break missingId;
      }

      id = R.id.next;
      MaterialButton next = rootView.findViewById(id);
      if (next == null) {
        break missingId;
      }

      id = R.id.rv_regions;
      RecyclerView rvRegions = rootView.findViewById(id);
      if (rvRegions == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = rootView.findViewById(id);
      if (title == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      return new FragmentRegionBinding((CoordinatorLayout) rootView, addCard, appBar, cardBg,
          cardImage, cardMain, collapsingToolbarLayout, image, nested, next, rvRegions, title,
          toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
