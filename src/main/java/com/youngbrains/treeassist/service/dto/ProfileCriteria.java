package com.youngbrains.treeassist.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.youngbrains.treeassist.domain.enumeration.HumanSex;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Profile entity. This class is used in ProfileResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /profiles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfileCriteria implements Serializable {
    /**
     * Class for filtering HumanSex
     */
    public static class HumanSexFilter extends Filter<HumanSex> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private LocalDateFilter birthday;

    private StringFilter familyPhones;

    private StringFilter bloodType;

    private StringFilter allergicReactions;

    private HumanSexFilter sex;

    private StringFilter phone;

    private BooleanFilter isVolunteer;

    private StringFilter fcmToken;

    private StringFilter latitude;

    private StringFilter longitude;

    private StringFilter organization;

    private StringFilter login;

    private StringFilter email;

    private LongFilter userId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public LocalDateFilter getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateFilter birthday) {
        this.birthday = birthday;
    }

    public StringFilter getFamilyPhones() {
        return familyPhones;
    }

    public void setFamilyPhones(StringFilter familyPhones) {
        this.familyPhones = familyPhones;
    }

    public StringFilter getBloodType() {
        return bloodType;
    }

    public void setBloodType(StringFilter bloodType) {
        this.bloodType = bloodType;
    }

    public StringFilter getAllergicReactions() {
        return allergicReactions;
    }

    public void setAllergicReactions(StringFilter allergicReactions) {
        this.allergicReactions = allergicReactions;
    }

    public HumanSexFilter getSex() {
        return sex;
    }

    public void setSex(HumanSexFilter sex) {
        this.sex = sex;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public BooleanFilter getIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(BooleanFilter isVolunteer) {
        this.isVolunteer = isVolunteer;
    }

    public StringFilter getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(StringFilter fcmToken) {
        this.fcmToken = fcmToken;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getOrganization() {
        return organization;
    }

    public void setOrganization(StringFilter organization) {
        this.organization = organization;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProfileCriteria that = (ProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(birthday, that.birthday) &&
            Objects.equals(familyPhones, that.familyPhones) &&
            Objects.equals(bloodType, that.bloodType) &&
            Objects.equals(allergicReactions, that.allergicReactions) &&
            Objects.equals(sex, that.sex) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(isVolunteer, that.isVolunteer) &&
            Objects.equals(fcmToken, that.fcmToken) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(login, that.login) &&
            Objects.equals(email, that.email) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullName,
        birthday,
        familyPhones,
        bloodType,
        allergicReactions,
        sex,
        phone,
        isVolunteer,
        fcmToken,
        latitude,
        longitude,
        organization,
        login,
        email,
        userId
        );
    }

    @Override
    public String toString() {
        return "ProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullName != null ? "fullName=" + fullName + ", " : "") +
                (birthday != null ? "birthday=" + birthday + ", " : "") +
                (familyPhones != null ? "familyPhones=" + familyPhones + ", " : "") +
                (bloodType != null ? "bloodType=" + bloodType + ", " : "") +
                (allergicReactions != null ? "allergicReactions=" + allergicReactions + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (isVolunteer != null ? "isVolunteer=" + isVolunteer + ", " : "") +
                (fcmToken != null ? "fcmToken=" + fcmToken + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (organization != null ? "organization=" + organization + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
