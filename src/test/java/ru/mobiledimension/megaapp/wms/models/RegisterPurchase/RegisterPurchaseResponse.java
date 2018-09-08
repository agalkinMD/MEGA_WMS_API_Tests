package ru.mobiledimension.megaapp.wms.models.RegisterPurchase;

import java.util.List;

import ru.mobiledimension.megaapp.wms.models.Purchase;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Error",
        "ErrorID",
        "ErrorText",
        "Purchases"
})
public class RegisterPurchaseResponse {

    @JsonProperty("Error")
    private Boolean error;
    @JsonProperty("ErrorID")
    private Integer errorID;
    @JsonProperty("ErrorText")
    private String errorText;
    @JsonProperty("Purchases")
    private List<Purchase> purchases;

//    public RegisterPurchaseResponse(Builder builder) {
//        this.error = builder.error;
//        this.errorID = builder.errorID;
//        this.errorText = builder.errorText;
//        this.purchases = builder.purchases;
//    }
    @JsonProperty("Error")
    public Boolean getError() {
        return error;
    }

    @JsonProperty("Error")
    public void setError(Boolean error) {
        this.error = error;
    }

    @JsonProperty("ErrorID")
    public Integer getErrorID() {
        return errorID;
    }

    @JsonProperty("ErrorID")
    public void setErrorID(Integer errorID) {
        this.errorID = errorID;
    }

    @JsonProperty("ErrorText")
    public String getErrorText() {
        return errorText;
    }

    @JsonProperty("ErrorText")
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    @JsonProperty("Purchases")
    public List<Purchase> getPurchases() {
        return purchases;
    }

    @JsonProperty("Purchases")
    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public static class Builder {
        private Boolean error;
        private Integer errorID;
        private String errorText;
        private List<Purchase> purchases;

        public Builder withEror(Boolean error) {
            this.error = error;
            return this;
        }

        public Builder withErrorID(Integer errorID) {
            this.errorID = errorID;
            return this;
        }

        public Builder withErrorText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        public Builder withPurchases(List<Purchase> purchases) {
            this.purchases = purchases;
            return this;
        }

//        public RegisterPurchaseResponse build() {
//            return new RegisterPurchaseResponse(this);
//        }
    }
}