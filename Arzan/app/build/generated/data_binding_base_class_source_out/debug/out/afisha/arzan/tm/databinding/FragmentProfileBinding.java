// Generated by view binder compiler. Do not edit!
package afisha.arzan.tm.databinding;

import afisha.arzan.tm.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final EditText about;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final ImageView backBtn;

  @NonNull
  public final ImageView banner;

  @NonNull
  public final MaterialCardView card;

  @NonNull
  public final MaterialCardView cardImage;

  @NonNull
  public final LinearLayout confirmed;

  @NonNull
  public final TextView confirmedCount;

  @NonNull
  public final ImageView editUsername;

  @NonNull
  public final TextView favorite;

  @NonNull
  public final TextView favoriteCount;

  @NonNull
  public final LinearLayout favorites;

  @NonNull
  public final TextView followerCount;

  @NonNull
  public final LinearLayout followingLine;

  @NonNull
  public final TextView likeCount;

  @NonNull
  public final LinearLayout likes;

  @NonNull
  public final LinearLayout pending;

  @NonNull
  public final TextView pendingCount;

  @NonNull
  public final ProgressBar progress;

  @NonNull
  public final TextView readMore;

  @NonNull
  public final TextView region;

  @NonNull
  public final RecyclerView rvFavorites;

  @NonNull
  public final MaterialCardView toolbar;

  @NonNull
  public final ImageView userimage;

  @NonNull
  public final EditText username;

  private FragmentProfileBinding(@NonNull CoordinatorLayout rootView, @NonNull EditText about,
      @NonNull AppBarLayout appBarLayout, @NonNull ImageView backBtn, @NonNull ImageView banner,
      @NonNull MaterialCardView card, @NonNull MaterialCardView cardImage,
      @NonNull LinearLayout confirmed, @NonNull TextView confirmedCount,
      @NonNull ImageView editUsername, @NonNull TextView favorite, @NonNull TextView favoriteCount,
      @NonNull LinearLayout favorites, @NonNull TextView followerCount,
      @NonNull LinearLayout followingLine, @NonNull TextView likeCount, @NonNull LinearLayout likes,
      @NonNull LinearLayout pending, @NonNull TextView pendingCount, @NonNull ProgressBar progress,
      @NonNull TextView readMore, @NonNull TextView region, @NonNull RecyclerView rvFavorites,
      @NonNull MaterialCardView toolbar, @NonNull ImageView userimage, @NonNull EditText username) {
    this.rootView = rootView;
    this.about = about;
    this.appBarLayout = appBarLayout;
    this.backBtn = backBtn;
    this.banner = banner;
    this.card = card;
    this.cardImage = cardImage;
    this.confirmed = confirmed;
    this.confirmedCount = confirmedCount;
    this.editUsername = editUsername;
    this.favorite = favorite;
    this.favoriteCount = favoriteCount;
    this.favorites = favorites;
    this.followerCount = followerCount;
    this.followingLine = followingLine;
    this.likeCount = likeCount;
    this.likes = likes;
    this.pending = pending;
    this.pendingCount = pendingCount;
    this.progress = progress;
    this.readMore = readMore;
    this.region = region;
    this.rvFavorites = rvFavorites;
    this.toolbar = toolbar;
    this.userimage = userimage;
    this.username = username;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.about;
      EditText about = rootView.findViewById(id);
      if (about == null) {
        break missingId;
      }

      id = R.id.app_bar_layout;
      AppBarLayout appBarLayout = rootView.findViewById(id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.back_btn;
      ImageView backBtn = rootView.findViewById(id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.banner;
      ImageView banner = rootView.findViewById(id);
      if (banner == null) {
        break missingId;
      }

      id = R.id.card;
      MaterialCardView card = rootView.findViewById(id);
      if (card == null) {
        break missingId;
      }

      id = R.id.card_image;
      MaterialCardView cardImage = rootView.findViewById(id);
      if (cardImage == null) {
        break missingId;
      }

      id = R.id.confirmed;
      LinearLayout confirmed = rootView.findViewById(id);
      if (confirmed == null) {
        break missingId;
      }

      id = R.id.confirmed_count;
      TextView confirmedCount = rootView.findViewById(id);
      if (confirmedCount == null) {
        break missingId;
      }

      id = R.id.edit_username;
      ImageView editUsername = rootView.findViewById(id);
      if (editUsername == null) {
        break missingId;
      }

      id = R.id.favorite;
      TextView favorite = rootView.findViewById(id);
      if (favorite == null) {
        break missingId;
      }

      id = R.id.favorite_count;
      TextView favoriteCount = rootView.findViewById(id);
      if (favoriteCount == null) {
        break missingId;
      }

      id = R.id.favorites;
      LinearLayout favorites = rootView.findViewById(id);
      if (favorites == null) {
        break missingId;
      }

      id = R.id.follower_count;
      TextView followerCount = rootView.findViewById(id);
      if (followerCount == null) {
        break missingId;
      }

      id = R.id.following_line;
      LinearLayout followingLine = rootView.findViewById(id);
      if (followingLine == null) {
        break missingId;
      }

      id = R.id.like_count;
      TextView likeCount = rootView.findViewById(id);
      if (likeCount == null) {
        break missingId;
      }

      id = R.id.likes;
      LinearLayout likes = rootView.findViewById(id);
      if (likes == null) {
        break missingId;
      }

      id = R.id.pending;
      LinearLayout pending = rootView.findViewById(id);
      if (pending == null) {
        break missingId;
      }

      id = R.id.pending_count;
      TextView pendingCount = rootView.findViewById(id);
      if (pendingCount == null) {
        break missingId;
      }

      id = R.id.progress;
      ProgressBar progress = rootView.findViewById(id);
      if (progress == null) {
        break missingId;
      }

      id = R.id.read_more;
      TextView readMore = rootView.findViewById(id);
      if (readMore == null) {
        break missingId;
      }

      id = R.id.region;
      TextView region = rootView.findViewById(id);
      if (region == null) {
        break missingId;
      }

      id = R.id.rv_favorites;
      RecyclerView rvFavorites = rootView.findViewById(id);
      if (rvFavorites == null) {
        break missingId;
      }

      id = R.id.toolbar;
      MaterialCardView toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.userimage;
      ImageView userimage = rootView.findViewById(id);
      if (userimage == null) {
        break missingId;
      }

      id = R.id.username;
      EditText username = rootView.findViewById(id);
      if (username == null) {
        break missingId;
      }

      return new FragmentProfileBinding((CoordinatorLayout) rootView, about, appBarLayout, backBtn,
          banner, card, cardImage, confirmed, confirmedCount, editUsername, favorite, favoriteCount,
          favorites, followerCount, followingLine, likeCount, likes, pending, pendingCount,
          progress, readMore, region, rvFavorites, toolbar, userimage, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
