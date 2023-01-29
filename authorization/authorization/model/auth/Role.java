
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
    "rol_id",
    "rol_description"
})
public class Role {

    @JsonProperty("rol_id")
    private String rolId;
    @JsonProperty("rol_description")
    private String rolDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("rol_id")
    public String getRolId() {
        return rolId;
    }

    @JsonProperty("rol_id")
    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    @JsonProperty("rol_description")
    public String getRolDescription() {
        return rolDescription;
    }

    @JsonProperty("rol_description")
    public void setRolDescription(String rolDescription) {
        this.rolDescription = rolDescription;
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
