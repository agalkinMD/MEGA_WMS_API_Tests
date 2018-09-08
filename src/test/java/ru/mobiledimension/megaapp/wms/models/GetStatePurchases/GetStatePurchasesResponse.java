package ru.mobiledimension.megaapp.wms.models.GetStatePurchases;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Barcode",
        "MallID",
        "PlaceID",
        "StateID",
        "Oversize",
        "AdmissionDate",
        "StateDate",
        "CellType",
        "CellID",
        "LicensePlate",
        "DeliveryTimer",
        "DeliverySLA"
})
public class GetStatePurchasesResponse {

    @JsonProperty("Barcode")
    private String barcode;
    @JsonProperty("MallID")
    private String mallID;
    @JsonProperty("PlaceID")
    private String placeID;
    @JsonProperty("StateID")
    private Integer stateID;
    @JsonProperty("Oversize")
    private Integer oversize;
    @JsonProperty("AdmissionDate")
    private String admissionDate;
    @JsonProperty("StateDate")
    private String stateDate;
    @JsonProperty("CellType")
    private Integer cellType;
    @JsonProperty("CellID")
    private String cellID;
    @JsonProperty("LicensePlate")
    private Object licensePlate;
    @JsonProperty("DeliveryTimer")
    private Integer deliveryTimer;
    @JsonProperty("DeliverySLA")
    private Integer deliverySLA;

//    public GetStatePurchasesResponse(Builder builder) {
//        this.barcode = builder.barcode;
//        this.mallID = builder.mallID;
//        this.placeID = builder.placeID;
//        this.stateID = builder.stateID;
//        this.oversize = builder.oversize;
//        this.admissionDate = builder.admissionDate;
//        this.stateDate = builder.stateDate;
//        this.cellType = builder.cellType;
//        this.cellID = builder.cellID;
//        this.licensePlate = builder.licensePlate;
//        this.deliveryTimer = builder.deliveryTimer;
//        this.deliverySLA = builder.deliverySLA;
//    }

    @JsonProperty("Barcode")
    public String getBarcode() {
        return barcode;
    }

    @JsonProperty("Barcode")
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @JsonProperty("MallID")
    public String getMallID() {
        return mallID;
    }

    @JsonProperty("MallID")
    public void setMallID(String mallID) {
        this.mallID = mallID;
    }

    @JsonProperty("PlaceID")
    public String getPlaceID() {
        return placeID;
    }

    @JsonProperty("PlaceID")
    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    @JsonProperty("StateID")
    public Integer getStateID() {
        return stateID;
    }

    @JsonProperty("StateID")
    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

    @JsonProperty("Oversize")
    public Integer getOversize() {
        return oversize;
    }

    @JsonProperty("Oversize")
    public void setOversize(Integer oversize) {
        this.oversize = oversize;
    }

    @JsonProperty("AdmissionDate")
    public String getAdmissionDate() {
        return admissionDate;
    }

    @JsonProperty("AdmissionDate")
    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    @JsonProperty("StateDate")
    public String getStateDate() {
        return stateDate;
    }

    @JsonProperty("StateDate")
    public void setStateDate(String stateDate) {
        this.stateDate = stateDate;
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

    @JsonProperty("DeliveryTimer")
    public Integer getDeliveryTimer() {
        return deliveryTimer;
    }

    @JsonProperty("DeliveryTimer")
    public void setDeliveryTimer(Integer deliveryTimer) {
        this.deliveryTimer = deliveryTimer;
    }

    @JsonProperty("DeliverySLA")
    public Integer getDeliverySLA() {
        return deliverySLA;
    }

    @JsonProperty("DeliverySLA")
    public void setDeliverySLA(Integer deliverySLA) {
        this.deliverySLA = deliverySLA;
    }

    public static class Builder {
        private String barcode;
        private String mallID;
        private String placeID;
        private Integer stateID;
        private Integer oversize;
        private String admissionDate;
        private String stateDate;
        private Integer cellType;
        private String cellID;
        private Object licensePlate;
        private Integer deliveryTimer;
        private Integer deliverySLA;

        public Builder() { }

        public Builder withBarcode(String barcode) {
            this.barcode = barcode;
            return this;
        }

        public Builder withMallID(String mallID) {
            this.mallID = mallID;
            return this;
        }

        public Builder withPlaceID(String placeID) {
            this.placeID = placeID;
            return this;
        }

        public Builder withStateID(Integer stateID) {
            this.stateID = stateID;
            return this;
        }

        public Builder withOversize(Integer oversize) {
            this.oversize = oversize;
            return this;
        }

        public Builder withAdmissionDate(String admissionDate) {
            this.admissionDate = admissionDate;
            return this;
        }

        public Builder withStateDate(String stateDate) {
            this.stateDate = stateDate;
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

        public Builder withLicensePlate(Object licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder withDeliveryTimer(Integer deliveryTimer) {
            this.deliveryTimer = deliveryTimer;
            return this;
        }

        public Builder withDeliverySLA(Integer deliverySLA) {
            this.deliverySLA = deliverySLA;
            return this;
        }

//        public GetStatePurchasesResponse build() {
//            return new GetStatePurchasesResponse(this);
//        }
    }
}