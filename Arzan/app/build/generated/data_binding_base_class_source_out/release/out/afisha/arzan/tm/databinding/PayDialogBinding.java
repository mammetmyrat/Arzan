// Generated by view binder compiler. Do not edit!
package afisha.arzan.tm.databinding;

import afisha.arzan.tm.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PayDialogBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText code;

  @NonNull
  public final MaterialCardView codeCard;

  @NonNull
  public final MaterialButton confirm;

  private PayDialogBinding(@NonNull ConstraintLayout rootView, @NonNull EditText code,
      @NonNull MaterialCardView codeCard, @NonNull MaterialButton confirm) {
    this.rootView = rootView;
    this.code = code;
    this.codeCard = codeCard;
    this.confirm = confirm;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PayDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PayDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.pay_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PayDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.code;
      EditText code = rootView.findViewById(id);
      if (code == null) {
        break missingId;
      }

      id = R.id.code_card;
      MaterialCardView codeCard = rootView.findViewById(id);
      if (codeCard == null) {
        break missingId;
      }

      id = R.id.confirm;
      MaterialButton confirm = rootView.findViewById(id);
      if (confirm == null) {
        break missingId;
      }

      return new PayDialogBinding((ConstraintLayout) rootView, code, codeCard, confirm);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}