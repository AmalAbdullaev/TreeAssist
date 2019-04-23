package com.youngbrains.treeassist.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.youngbrains.treeassist.domain.enumeration.HumanSex;

/**
 * A DTO for the Profile entity.
 */
public class ProfileDTO implements Serializable {

    private Long id;

    private String fullName;

    private LocalDate birthday;

    private String familyPhones;

    private String bloodType;

    private String allergicReactions;

    private HumanSex sex;

    private String phone;

    private Boolean isVolunteer;

    private String fcmToken;

    private String latitude;

    private String longitude;

    private String organization;

    private String login;

    private String email;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getFamilyPhones() {
        return familyPhones;
    }

    public void setFamilyPhones(String familyPhones) {
        this.familyPhones = familyPhones;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergicReactions() {
        return allergicReactions;
    }

    public void setAllergicReactions(String allergicReactions) {
        this.allergicReactions = allergicReactions;
    }

    public HumanSex getSex() {
        return sex;
    }

    public void setSex(HumanSex sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(Boolean isVolunteer) {
        this.isVolunteer = isVolunteer;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfileDTO profileDTO = (ProfileDTO) o;
        if (profileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", familyPhones='" + getFamilyPhones() + "'" +
            ", bloodType='" + getBloodType() + "'" +
            ", allergicReactions='" + getAllergicReactions() + "'" +
            ", sex='" + getSex() + "'" +
            ", phone='" + getPhone() + "'" +
            ", isVolunteer='" + isIsVolunteer() + "'" +
            ", fcmToken='" + getFcmToken() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", login='" + getLogin() + "'" +
            ", email='" + getEmail() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
