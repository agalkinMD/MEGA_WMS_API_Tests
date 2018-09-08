package ru.mobiledimension.megaapp.wms.models.RegisterShipping;

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
        "CellType",
        "CellID",
        "LicensePlate",
        "ReturnOfPurchases"
})
public class RegisterShippingRequest {

    @JsonProperty("Customer")
    private String customer;
    @JsonProperty("MallID")
    private String mallID;
    @JsonProperty("Barcode")
    private List<String> barcode;
    @JsonProperty("CellType")
    private Integer cellType;
    @JsonProperty("CellID")
    private String cellID;
    @JsonProperty("LicensePlate")
    private Object licensePlate;
    @JsonProperty("ReturnOfPurchases")
    private Boolean returnOfPurchases;

    public RegisterShippingRequest(Builder builder) {
        this.customer = builder.customer;
        this.mallID = builder.mallID;
        this.barcode = builder.barcode;
        this.cellType = builder.cellType;
        this.cellID = builder.cellID;
        this.licensePlate = builder.licensePlate;
        this.returnOfPurchases = builder.returnOfPurchases;
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

    @JsonProperty("CellType")
    public Integer getCellType() {
        return cellType;
    }

    @JsonProperty("CellType")
    public void setCellType(Integer cellType) {
        this.cellType = cellType;
    }

    @JsonProperty("CellID")
    public String getCellID() {
        return cellID;
    }

    @JsonProperty("CellID")
    public void setCellID(String cellID) {
        this.cellID = cellID;
    }

    @JsonProperty("LicensePlate")
    public Object getLicensePlate() {
        return licensePlate;
    }

    @JsonProperty("LicensePlate")
    public void setLicensePlate(Object licensePlate) {
        this.licensePlate = licensePlate;
    }

    @JsonProperty("ReturnOfPurchases")
    public Boolean getReturnOfPurchases() {
        return returnOfPurchases;
    }

    @JsonProperty("ReturnOfPurchases")
    public void setReturnOfPurchases(Boolean returnOfPurchases) {
        this.returnOfPurchases = returnOfPurchases;
    }

    public static class Builder {
        private String customer;
        private String mallID;
        private List<String> barcode;
        private Integer cellType;
        private String cellID;
        private String licensePlate;
        private boolean returnOfPurchases;

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

        public Builder withCellType(Integer cellType) {
            this.cellType = cellType;
            return this;
        }

        public Builder withCellID(String cellID) {
            this.cellID = cellID;
            return this;
        }

        public Builder withLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder withReturnOfPurchases(boolean returnOfPurchases) {
            this.returnOfPurchases = returnOfPurchases;
            return this;
        }

        public RegisterShippingRequest build() {
            return new RegisterShippingRequest(this);
        }
    }
}