package ru.mobiledimension.megaapp.wms.models.GetStateReference;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "StateID",
        "StateName",
        "StateType"
})
public class GetStateReferenceResponse {

    @JsonProperty("StateID")
    private Integer stateID;
    @JsonProperty("StateName")
    private String stateName;
    @JsonProperty("StateType")
    private Integer stateType;

    @JsonProperty("StateID")
    public Integer getStateID() {
        return stateID;
    }

    @JsonProperty("StateID")
    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }

    @JsonProperty("StateName")
    public String getStateName() {
        return stateName;
    }

    @JsonProperty("StateName")
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @JsonProperty("StateType")
    public Integer getStateType() {
        return stateType;
    }

    @JsonProperty("StateType")
    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }

}