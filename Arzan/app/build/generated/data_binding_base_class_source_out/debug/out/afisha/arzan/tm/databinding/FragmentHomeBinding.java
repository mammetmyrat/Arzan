// Generated by view binder compiler. Do not edit!
package afisha.arzan.tm.databinding;

import afisha.arzan.tm.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.card.MaterialCardView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final AppBarLayout appBar;

  @NonNull
  public final TextView badgeNotify;

  @NonNull
  public final TextView badgeOfficial;

  @NonNull
  public final TextView badgeStatistic;

  @NonNull
  public final MaterialCardView bannerCard;

  @NonNull
  public final CollapsingToolbarLayout collapsingToolbarLayout;

  @NonNull
  public final CoordinatorLayout coordinator;

  @NonNull
  public final WormDotsIndicator dot;

  @NonNull
  public final ImageView icon;

  @NonNull
  public final ImageView iconOfficial;

  @NonNull
  public final MaterialCardView mainCard;

  @NonNull
  public final NestedScrollView nested;

  @NonNull
  public final MaterialCardView notification;

  @NonNull
  public final MaterialCardView official;

  @NonNull
  public final ProgressBar progress;

  @NonNull
  public final MaterialCardView region;

  @NonNull
  public final TextView regionText;

  @NonNull
  public final RecyclerView rvPosts;

  @NonNull
  public final RecyclerView rvVip;

  @NonNull
  public final MaterialCardView statistic;

  @NonNull
  public final SwipeRefreshLayout swipeRefresh;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final ImageView typeIcon;

  @NonNull
  public final TextView viewAll;

  @NonNull
  public final ViewPager viewPager;

  @NonNull
  public final MaterialCardView viewType;

  @NonNull
  public final RelativeLayout vipline;

  private FragmentHomeBinding(@NonNull CoordinatorLayout rootView, @NonNull AppBarLayout appBar,
      @NonNull TextView badgeNotify, @NonNull TextView badgeOfficial,
      @NonNull TextView badgeStatistic, @NonNull MaterialCardView bannerCard,
      @NonNull CollapsingToolbarLayout collapsingToolbarLayout,
      @NonNull CoordinatorLayout coordinator, @NonNull WormDotsIndicator dot,
      @NonNull ImageView icon, @NonNull ImageView iconOfficial, @NonNull MaterialCardView mainCard,
      @NonNull NestedScrollView nested, @NonNull MaterialCardView notification,
      @NonNull MaterialCardView official, @NonNull ProgressBar progress,
      @NonNull MaterialCardView region, @NonNull TextView regionText, @NonNull RecyclerView rvPosts,
      @NonNull RecyclerView rvVip, @NonNull MaterialCardView statistic,
      @NonNull SwipeRefreshLayout swipeRefresh, @NonNull Toolbar toolbar,
      @NonNull ImageView typeIcon, @NonNull TextView viewAll, @NonNull ViewPager viewPager,
      @NonNull MaterialCardView viewType, @NonNull RelativeLayout vipline) {
    this.rootView = rootView;
    this.appBar = appBar;
    this.badgeNotify = badgeNotify;
    this.badgeOfficial = badgeOfficial;
    this.badgeStatistic = badgeStatistic;
    this.bannerCard = bannerCard;
    this.collapsingToolbarLayout = collapsingToolbarLayout;
    this.coordinator = coordinator;
    this.dot = dot;
    this.icon = icon;
    this.iconOfficial = iconOfficial;
    this.mainCard = mainCard;
    this.nested = nested;
    this.notification = notification;
    this.official = official;
    this.progress = progress;
    this.region = region;
    this.regionText = regionText;
    this.rvPosts = rvPosts;
    this.rvVip = rvVip;
    this.statistic = statistic;
    this.swipeRefresh = swipeRefresh;
    this.toolbar = toolbar;
    this.typeIcon = typeIcon;
    this.viewAll = viewAll;
    this.viewPager = viewPager;
    this.viewType = viewType;
    this.vipline = vipline;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appBar;
      AppBarLayout appBar = rootView.findViewById(id);
      if (appBar == null) {
        break missingId;
      }

      id = R.id.badgeNotify;
      TextView badgeNotify = rootView.findViewById(id);
      if (badgeNotify == null) {
        break missingId;
      }

      id = R.id.badgeOfficial;
      TextView badgeOfficial = rootView.findViewById(id);
      if (badgeOfficial == null) {
        break missingId;
      }

      id = R.id.badgeStatistic;
      TextView badgeStatistic = rootView.findViewById(id);
      if (badgeStatistic == null) {
        break missingId;
      }

      id = R.id.banner_card;
      MaterialCardView bannerCard = rootView.findViewById(id);
      if (bannerCard == null) {
        break missingId;
      }

      id = R.id.collapsing_toolbar_layout;
      CollapsingToolbarLayout collapsingToolbarLayout = rootView.findViewById(id);
      if (collapsingToolbarLayout == null) {
        break missingId;
      }

      id = R.id.coordinator;
      CoordinatorLayout coordinator = rootView.findViewById(id);
      if (coordinator == null) {
        break missingId;
      }

      id = R.id.dot;
      WormDotsIndicator dot = rootView.findViewById(id);
      if (dot == null) {
        break missingId;
      }

      id = R.id.icon;
      ImageView icon = rootView.findViewById(id);
      if (icon == null) {
        break missingId;
      }

      id = R.id.icon_official;
      ImageView iconOfficial = rootView.findViewById(id);
      if (iconOfficial == null) {
        break missingId;
      }

      id = R.id.main_card;
      MaterialCardView mainCard = rootView.findViewById(id);
      if (mainCard == null) {
        break missingId;
      }

      id = R.id.nested;
      NestedScrollView nested = rootView.findViewById(id);
      if (nested == null) {
        break missingId;
      }

      id = R.id.notification;
      MaterialCardView notification = rootView.findViewById(id);
      if (notification == null) {
        break missingId;
      }

      id = R.id.official;
      MaterialCardView official = rootView.findViewById(id);
      if (official == null) {
        break missingId;
      }

      id = R.id.progress;
      ProgressBar progress = rootView.findViewById(id);
      if (progress == null) {
        break missingId;
      }

      id = R.id.region;
      MaterialCardView region = rootView.findViewById(id);
      if (region == null) {
        break missingId;
      }

      id = R.id.region_text;
      TextView regionText = rootView.findViewById(id);
      if (regionText == null) {
        break missingId;
      }

      id = R.id.rv_posts;
      RecyclerView rvPosts = rootView.findViewById(id);
      if (rvPosts == null) {
        break missingId;
      }

      id = R.id.rv_vip;
      RecyclerView rvVip = rootView.findViewById(id);
      if (rvVip == null) {
        break missingId;
      }

      id = R.id.statistic;
      MaterialCardView statistic = rootView.findViewById(id);
      if (statistic == null) {
        break missingId;
      }

      id = R.id.swipeRefresh;
      SwipeRefreshLayout swipeRefresh = rootView.findViewById(id);
      if (swipeRefresh == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.type_icon;
      ImageView typeIcon = rootView.findViewById(id);
      if (typeIcon == null) {
        break missingId;
      }

      id = R.id.view_all;
      TextView viewAll = rootView.findViewById(id);
      if (viewAll == null) {
        break missingId;
      }

      id = R.id.viewPager;
      ViewPager viewPager = rootView.findViewById(id);
      if (viewPager == null) {
        break missingId;
      }

      id = R.id.view_type;
      MaterialCardView viewType = rootView.findViewById(id);
      if (viewType == null) {
        break missingId;
      }

      id = R.id.vipline;
      RelativeLayout vipline = rootView.findViewById(id);
      if (vipline == null) {
        break missingId;
      }

      return new FragmentHomeBinding((CoordinatorLayout) rootView, appBar, badgeNotify,
          badgeOfficial, badgeStatistic, bannerCard, collapsingToolbarLayout, coordinator, dot,
          icon, iconOfficial, mainCard, nested, notification, official, progress, region,
          regionText, rvPosts, rvVip, statistic, swipeRefresh, toolbar, typeIcon, viewAll,
          viewPager, viewType, vipline);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
