package uz.yeoju.yeoju_app.payload.resDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface StudentDataForExternalApi {
    @JsonProperty("first_name")
    String getFirstName();
    @JsonProperty("last_name")
    String getLastName();
    @JsonProperty("middle_name")
    String getMiddleName();
    @JsonProperty("login")
    String getLogin();
    @JsonProperty("password")
    String getPassword();
    @JsonProperty("group_name")
    String getGroupName();
    @JsonProperty("course")
    String getCourse();
}
