package com.youngbrains.treeassist.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.youngbrains.treeassist.domain.enumeration.HumanSex;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "family_phones")
    private String familyPhones;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "allergic_reactions")
    private String allergicReactions;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private HumanSex sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_volunteer")
    private Boolean isVolunteer;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "jhi_organization")
    private String organization;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public Profile fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Profile birthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getFamilyPhones() {
        return familyPhones;
    }

    public Profile familyPhones(String familyPhones) {
        this.familyPhones = familyPhones;
        return this;
    }

    public void setFamilyPhones(String familyPhones) {
        this.familyPhones = familyPhones;
    }

    public String getBloodType() {
        return bloodType;
    }

    public Profile bloodType(String bloodType) {
        this.bloodType = bloodType;
        return this;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergicReactions() {
        return allergicReactions;
    }

    public Profile allergicReactions(String allergicReactions) {
        this.allergicReactions = allergicReactions;
        return this;
    }

    public void setAllergicReactions(String allergicReactions) {
        this.allergicReactions = allergicReactions;
    }

    public HumanSex getSex() {
        return sex;
    }

    public Profile sex(HumanSex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(HumanSex sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public Profile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isIsVolunteer() {
        return isVolunteer;
    }

    public Profile isVolunteer(Boolean isVolunteer) {
        this.isVolunteer = isVolunteer;
        return this;
    }

    public void setIsVolunteer(Boolean isVolunteer) {
        this.isVolunteer = isVolunteer;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public Profile fcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
        return this;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getLatitude() {
        return latitude;
    }

    public Profile latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Profile longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrganization() {
        return organization;
    }

    public Profile organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLogin() {
        return login;
    }

    public Profile login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public Profile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
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
            "}";
    }
}
