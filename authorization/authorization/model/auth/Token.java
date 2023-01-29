
package io.blacktoast.utils.authorization.model.auth;

import java.util.HashMap;
import java.util.List;
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
    "authtype",
    "office_desc",
    "payload",
    "uid",
    "roles",
    "ip",
    "modules",
    "sidu",
    "system",
    "office_id",
    "getInstancesVersion",
    "dependencia_cod",
    "one_shoot",
    "dependencia_desc",
    "organization",
    "ou",
    "extra_data",
    "cn",
    "email",
    "token_and_sign",
    "user_profiles"
})
public class Token {

    @JsonProperty("authtype")
    private String authtype;
    @JsonProperty("office_desc")
    private String officeDesc;
    @JsonProperty("payload")
    private Payload payload;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("roles")
    private List<Role> roles = null;
    @JsonProperty("ip")
    private String ip;
    @JsonProperty("modules")
    private List<Module> modules = null;
    @JsonProperty("sidu")
    private Sidu sidu;
    @JsonProperty("system")
    private String system;
    @JsonProperty("office_id")
    private String officeId;
    @JsonProperty("getInstancesVersion")
    private String getInstancesVersion;
    @JsonProperty("dependencia_cod")
    private String dependenciaCod;
    @JsonProperty("one_shoot")
    private Boolean oneShoot;
    @JsonProperty("dependencia_desc")
    private String dependenciaDesc;
    @JsonProperty("organization")
    private Organization_ organization;
    @JsonProperty("ou")
    private String ou;
    @JsonProperty("extra_data")
    private Object extraData;
    @JsonProperty("cn")
    private String cn;
    @JsonProperty("email")
    private String email;
  
    @JsonProperty("user_profiles")
    private List<UserProfile> userProfiles = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("authtype")
    public String getAuthtype() {
        return authtype;
    }

    @JsonProperty("authtype")
    public void setAuthtype(String authtype) {
        this.authtype = authtype;
    }

    @JsonProperty("office_desc")
    public String getOfficeDesc() {
        return officeDesc;
    }

    @JsonProperty("office_desc")
    public void setOfficeDesc(String officeDesc) {
        this.officeDesc = officeDesc;
    }

    @JsonProperty("payload")
    public Payload getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @JsonProperty("roles")
    public List<Role> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty("ip")
    public String getIp() {
        return ip;
    }

    @JsonProperty("ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonProperty("modules")
    public List<Module> getModules() {
        return modules;
    }

    @JsonProperty("modules")
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    @JsonProperty("sidu")
    public Sidu getSidu() {
        return sidu;
    }

    @JsonProperty("sidu")
    public void setSidu(Sidu sidu) {
        this.sidu = sidu;
    }

    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    @JsonProperty("system")
    public void setSystem(String system) {
        this.system = system;
    }

    @JsonProperty("office_id")
    public String getOfficeId() {
        return officeId;
    }

    @JsonProperty("office_id")
    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    @JsonProperty("getInstancesVersion")
    public String getGetInstancesVersion() {
        return getInstancesVersion;
    }

    @JsonProperty("getInstancesVersion")
    public void setGetInstancesVersion(String getInstancesVersion) {
        this.getInstancesVersion = getInstancesVersion;
    }

    @JsonProperty("dependencia_cod")
    public String getDependenciaCod() {
        return dependenciaCod;
    }

    @JsonProperty("dependencia_cod")
    public void setDependenciaCod(String dependenciaCod) {
        this.dependenciaCod = dependenciaCod;
    }

    @JsonProperty("one_shoot")
    public Boolean getOneShoot() {
        return oneShoot;
    }

    @JsonProperty("one_shoot")
    public void setOneShoot(Boolean oneShoot) {
        this.oneShoot = oneShoot;
    }

    @JsonProperty("dependencia_desc")
    public String getDependenciaDesc() {
        return dependenciaDesc;
    }

    @JsonProperty("dependencia_desc")
    public void setDependenciaDesc(String dependenciaDesc) {
        this.dependenciaDesc = dependenciaDesc;
    }

    @JsonProperty("organization")
    public Organization_ getOrganization() {
        return organization;
    }

    @JsonProperty("organization")
    public void setOrganization(Organization_ organization) {
        this.organization = organization;
    }

    @JsonProperty("ou")
    public String getOu() {
        return ou;
    }

    @JsonProperty("ou")
    public void setOu(String ou) {
        this.ou = ou;
    }

    @JsonProperty("extra_data")
    public Object getExtraData() {
        return extraData;
    }

    @JsonProperty("extra_data")
    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    @JsonProperty("cn")
    public String getCn() {
        return cn;
    }

    @JsonProperty("cn")
    public void setCn(String cn) {
        this.cn = cn;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

  

    @JsonProperty("user_profiles")
    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    @JsonProperty("user_profiles")
    public void setUserProfiles(List<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
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
