
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
    "profile_id",
    "profile_description"
})
public class UserProfile {

    @JsonProperty("profile_id")
    private String profileId;
    @JsonProperty("profile_description")
    private String profileDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("profile_id")
    public String getProfileId() {
        return profileId;
    }

    @JsonProperty("profile_id")
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    @JsonProperty("profile_description")
    public String getProfileDescription() {
        return profileDescription;
    }

    @JsonProperty("profile_description")
    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
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
