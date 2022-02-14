package afisha.arzan.tm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GlobalVar {
    @SerializedName("globalVar")
    @Expose
    private PaymentServices paymentServices;

    public PaymentServices getPaymentServices() {
        return paymentServices;
    }

    public void setPaymentServices(PaymentServices paymentServices) {
        this.paymentServices = paymentServices;
    }
}
