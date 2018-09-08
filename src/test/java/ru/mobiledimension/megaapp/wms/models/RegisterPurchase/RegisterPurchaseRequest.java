package ru.mobiledimension.megaapp.wms.models.RegisterPurchase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Customer",
        "MallID",
        "Barcode"
})
public class RegisterPurchaseRequest {

    @JsonProperty("Customer")
    private String customer;
    @JsonProperty("MallID")
    private String mallID;
    @JsonProperty("Barcode")
    private String barcode;

    public RegisterPurchaseRequest(Builder builder) {
        this.customer = builder.customer;
        this.mallID = builder.mallID;
        this.barcode = builder.barcode;
    }
    @JsonProperty("Customer")
    public String getCustomer() {
        return customer;
    }

    @JsonProperty("Customer")
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @JsonProperty("MallID")
    public String getMallID() {
        return mallID;
    }

    @JsonProperty("MallID")
    public void setMallID(String mallID) {
        this.mallID = mallID;
    }

    @JsonProperty("Barcode")
    public String getBarcode() {
        return barcode;
    }

    @JsonProperty("Barcode")
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public static class Builder {

        private String customer;
        private String mallID;
        private String barcode;

        public Builder() { }

        public Builder withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public Builder withMalID(String mallID) {
            this.mallID = mallID;
            return this;
        }

        public Builder withBarcode(String barcode) {
            this.barcode = barcode;
            return this;
        }

        public RegisterPurchaseRequest build() {
            return new RegisterPurchaseRequest(this);
        }
    }
}