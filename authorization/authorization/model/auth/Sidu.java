
package io.blacktoast.utils.authorization.model.auth;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("deprecation")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
    "department",
    "su",
    "office"
})
public class Sidu {

    @JsonProperty("department")
    private String department;
    @JsonProperty("su")
    private String su;
    @JsonProperty("office")
    private String office;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    @JsonProperty("department")
    public void setDepartment(String department) {
        this.department = department;
    }

    @JsonProperty("su")
    public String getSu() {
        return su;
    }

    @JsonProperty("su")
    public void setSu(String su) {
        this.su = su;
    }

    @JsonProperty("office")
    public String getOffice() {
        return office;
    }

    @JsonProperty("office")
    public void setOffice(String office) {
        this.office = office;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
