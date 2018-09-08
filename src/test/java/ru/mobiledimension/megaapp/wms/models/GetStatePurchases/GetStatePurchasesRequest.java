package ru.mobiledimension.megaapp.wms.models.GetStatePurchases;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Customer",
        "MallID",
        "Barcode",
        "PlaceID",
        "StateTypes"
})
public class GetStatePurchasesRequest {

    @JsonProperty("Customer")
    private String customer;
    @JsonProperty("MallID")
    private String mallID;
    @JsonProperty("Barcode")
    private List<String> barcode;
    @JsonProperty("PlaceID")
    private String placeID;
    @JsonProperty("StateTypes")
    private List<Integer> stateTypes;

    public GetStatePurchasesRequest(Builder builder) {
        this.customer = builder.customer;
        this.mallID = builder.mallID;
        this.barcode = builder.barcode;
        this.placeID = builder.placeID;
        this.stateTypes = builder.stateTypes;
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
    public List<String> getBarcode() {
        return barcode;
    }

    @JsonProperty("Barcode")
    public void setBarcode(List<String> barcode) {
        this.barcode = barcode;
    }

    @JsonProperty("PlaceID")
    public String getPlaceID() {
        return placeID;
    }

    @JsonProperty("PlaceID")
    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    @JsonProperty("StateTypes")
    public List<Integer> getStateTypes() {
        return stateTypes;
    }

    @JsonProperty("StateTypes")
    public void setStateTypes(List<Integer> stateTypes) {
        this.stateTypes = stateTypes;
    }

    public static class Builder {
        private String customer;
        private String mallID;
        private List<String> barcode;
        private String placeID;
        private List<Integer> stateTypes;

        public Builder() { }

        public Builder withCustomer(String customer) {
            this.customer = customer;
            return this;
        }

        public Builder withMallID(String mallID) {
            this.mallID = mallID;
            return this;
        }

        public Builder withBarcode(List<String> barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder withPlaceID(String placeID) {
            this.placeID = placeID;
            return this;
        }

        public Builder withStateTypes(Integer... stateTypes) {
            this.stateTypes = new ArrayList<>();

            for (Integer stateType : stateTypes) {
                this.stateTypes.add(stateType);
            }
            return this;
        }

        public GetStatePurchasesRequest build() {
            return new GetStatePurchasesRequest(this);
        }
    }
}