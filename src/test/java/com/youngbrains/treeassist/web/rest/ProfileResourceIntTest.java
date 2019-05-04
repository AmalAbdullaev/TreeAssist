package com.youngbrains.treeassist.web.rest;

import com.youngbrains.treeassist.TreeassistApp;

import com.youngbrains.treeassist.domain.Profile;
import com.youngbrains.treeassist.domain.User;
import com.youngbrains.treeassist.repository.ProfileRepository;
import com.youngbrains.treeassist.service.ProfileService;
import com.youngbrains.treeassist.service.dto.ProfileDTO;
import com.youngbrains.treeassist.service.mapper.ProfileMapper;
import com.youngbrains.treeassist.web.rest.errors.ExceptionTranslator;
import com.youngbrains.treeassist.service.dto.ProfileCriteria;
import com.youngbrains.treeassist.service.ProfileQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.youngbrains.treeassist.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.youngbrains.treeassist.domain.enumeration.HumanSex;
/**
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TreeassistApp.class)
public class ProfileResourceIntTest {

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FAMILY_PHONES = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY_PHONES = "BBBBBBBBBB";

    private static final String DEFAULT_BLOOD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BLOOD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ALLERGIC_REACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ALLERGIC_REACTIONS = "BBBBBBBBBB";

    private static final HumanSex DEFAULT_SEX = HumanSex.MALE;
    private static final HumanSex UPDATED_SEX = HumanSex.FEMALE;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VOLUNTEER = false;
    private static final Boolean UPDATED_IS_VOLUNTEER = true;

    private static final String DEFAULT_FCM_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_FCM_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileQueryService profileQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    private AccountResource accountResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService, profileQueryService, accountResource);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .fullName(DEFAULT_FULL_NAME)
            .birthday(DEFAULT_BIRTHDAY)
            .familyPhones(DEFAULT_FAMILY_PHONES)
            .bloodType(DEFAULT_BLOOD_TYPE)
            .allergicReactions(DEFAULT_ALLERGIC_REACTIONS)
            .sex(DEFAULT_SEX)
            .phone(DEFAULT_PHONE)
            .isVolunteer(DEFAULT_IS_VOLUNTEER)
            .fcmToken(DEFAULT_FCM_TOKEN)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .organization(DEFAULT_ORGANIZATION)
            .login(DEFAULT_LOGIN)
            .email(DEFAULT_EMAIL);
        return profile;
    }

    @Before
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testProfile.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testProfile.getFamilyPhones()).isEqualTo(DEFAULT_FAMILY_PHONES);
        assertThat(testProfile.getBloodType()).isEqualTo(DEFAULT_BLOOD_TYPE);
        assertThat(testProfile.getAllergicReactions()).isEqualTo(DEFAULT_ALLERGIC_REACTIONS);
        assertThat(testProfile.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.isIsVolunteer()).isEqualTo(DEFAULT_IS_VOLUNTEER);
        assertThat(testProfile.getFcmToken()).isEqualTo(DEFAULT_FCM_TOKEN);
        assertThat(testProfile.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testProfile.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testProfile.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testProfile.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].familyPhones").value(hasItem(DEFAULT_FAMILY_PHONES.toString())))
            .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].allergicReactions").value(hasItem(DEFAULT_ALLERGIC_REACTIONS.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].isVolunteer").value(hasItem(DEFAULT_IS_VOLUNTEER.booleanValue())))
            .andExpect(jsonPath("$.[*].fcmToken").value(hasItem(DEFAULT_FCM_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.familyPhones").value(DEFAULT_FAMILY_PHONES.toString()))
            .andExpect(jsonPath("$.bloodType").value(DEFAULT_BLOOD_TYPE.toString()))
            .andExpect(jsonPath("$.allergicReactions").value(DEFAULT_ALLERGIC_REACTIONS.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.isVolunteer").value(DEFAULT_IS_VOLUNTEER.booleanValue()))
            .andExpect(jsonPath("$.fcmToken").value(DEFAULT_FCM_TOKEN.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName equals to DEFAULT_FULL_NAME
        defaultProfileShouldBeFound("fullName.equals=" + DEFAULT_FULL_NAME);

        // Get all the profileList where fullName equals to UPDATED_FULL_NAME
        defaultProfileShouldNotBeFound("fullName.equals=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName in DEFAULT_FULL_NAME or UPDATED_FULL_NAME
        defaultProfileShouldBeFound("fullName.in=" + DEFAULT_FULL_NAME + "," + UPDATED_FULL_NAME);

        // Get all the profileList where fullName equals to UPDATED_FULL_NAME
        defaultProfileShouldNotBeFound("fullName.in=" + UPDATED_FULL_NAME);
    }

    @Test
    @Transactional
    public void getAllProfilesByFullNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fullName is not null
        defaultProfileShouldBeFound("fullName.specified=true");

        // Get all the profileList where fullName is null
        defaultProfileShouldNotBeFound("fullName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthday equals to DEFAULT_BIRTHDAY
        defaultProfileShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the profileList where birthday equals to UPDATED_BIRTHDAY
        defaultProfileShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultProfileShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the profileList where birthday equals to UPDATED_BIRTHDAY
        defaultProfileShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthday is not null
        defaultProfileShouldBeFound("birthday.specified=true");

        // Get all the profileList where birthday is null
        defaultProfileShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthday greater than or equals to DEFAULT_BIRTHDAY
        defaultProfileShouldBeFound("birthday.greaterOrEqualThan=" + DEFAULT_BIRTHDAY);

        // Get all the profileList where birthday greater than or equals to UPDATED_BIRTHDAY
        defaultProfileShouldNotBeFound("birthday.greaterOrEqualThan=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllProfilesByBirthdayIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where birthday less than or equals to DEFAULT_BIRTHDAY
        defaultProfileShouldNotBeFound("birthday.lessThan=" + DEFAULT_BIRTHDAY);

        // Get all the profileList where birthday less than or equals to UPDATED_BIRTHDAY
        defaultProfileShouldBeFound("birthday.lessThan=" + UPDATED_BIRTHDAY);
    }


    @Test
    @Transactional
    public void getAllProfilesByFamilyPhonesIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where familyPhones equals to DEFAULT_FAMILY_PHONES
        defaultProfileShouldBeFound("familyPhones.equals=" + DEFAULT_FAMILY_PHONES);

        // Get all the profileList where familyPhones equals to UPDATED_FAMILY_PHONES
        defaultProfileShouldNotBeFound("familyPhones.equals=" + UPDATED_FAMILY_PHONES);
    }

    @Test
    @Transactional
    public void getAllProfilesByFamilyPhonesIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where familyPhones in DEFAULT_FAMILY_PHONES or UPDATED_FAMILY_PHONES
        defaultProfileShouldBeFound("familyPhones.in=" + DEFAULT_FAMILY_PHONES + "," + UPDATED_FAMILY_PHONES);

        // Get all the profileList where familyPhones equals to UPDATED_FAMILY_PHONES
        defaultProfileShouldNotBeFound("familyPhones.in=" + UPDATED_FAMILY_PHONES);
    }

    @Test
    @Transactional
    public void getAllProfilesByFamilyPhonesIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where familyPhones is not null
        defaultProfileShouldBeFound("familyPhones.specified=true");

        // Get all the profileList where familyPhones is null
        defaultProfileShouldNotBeFound("familyPhones.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByBloodTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where bloodType equals to DEFAULT_BLOOD_TYPE
        defaultProfileShouldBeFound("bloodType.equals=" + DEFAULT_BLOOD_TYPE);

        // Get all the profileList where bloodType equals to UPDATED_BLOOD_TYPE
        defaultProfileShouldNotBeFound("bloodType.equals=" + UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    public void getAllProfilesByBloodTypeIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where bloodType in DEFAULT_BLOOD_TYPE or UPDATED_BLOOD_TYPE
        defaultProfileShouldBeFound("bloodType.in=" + DEFAULT_BLOOD_TYPE + "," + UPDATED_BLOOD_TYPE);

        // Get all the profileList where bloodType equals to UPDATED_BLOOD_TYPE
        defaultProfileShouldNotBeFound("bloodType.in=" + UPDATED_BLOOD_TYPE);
    }

    @Test
    @Transactional
    public void getAllProfilesByBloodTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where bloodType is not null
        defaultProfileShouldBeFound("bloodType.specified=true");

        // Get all the profileList where bloodType is null
        defaultProfileShouldNotBeFound("bloodType.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByAllergicReactionsIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where allergicReactions equals to DEFAULT_ALLERGIC_REACTIONS
        defaultProfileShouldBeFound("allergicReactions.equals=" + DEFAULT_ALLERGIC_REACTIONS);

        // Get all the profileList where allergicReactions equals to UPDATED_ALLERGIC_REACTIONS
        defaultProfileShouldNotBeFound("allergicReactions.equals=" + UPDATED_ALLERGIC_REACTIONS);
    }

    @Test
    @Transactional
    public void getAllProfilesByAllergicReactionsIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where allergicReactions in DEFAULT_ALLERGIC_REACTIONS or UPDATED_ALLERGIC_REACTIONS
        defaultProfileShouldBeFound("allergicReactions.in=" + DEFAULT_ALLERGIC_REACTIONS + "," + UPDATED_ALLERGIC_REACTIONS);

        // Get all the profileList where allergicReactions equals to UPDATED_ALLERGIC_REACTIONS
        defaultProfileShouldNotBeFound("allergicReactions.in=" + UPDATED_ALLERGIC_REACTIONS);
    }

    @Test
    @Transactional
    public void getAllProfilesByAllergicReactionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where allergicReactions is not null
        defaultProfileShouldBeFound("allergicReactions.specified=true");

        // Get all the profileList where allergicReactions is null
        defaultProfileShouldNotBeFound("allergicReactions.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesBySexIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sex equals to DEFAULT_SEX
        defaultProfileShouldBeFound("sex.equals=" + DEFAULT_SEX);

        // Get all the profileList where sex equals to UPDATED_SEX
        defaultProfileShouldNotBeFound("sex.equals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllProfilesBySexIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sex in DEFAULT_SEX or UPDATED_SEX
        defaultProfileShouldBeFound("sex.in=" + DEFAULT_SEX + "," + UPDATED_SEX);

        // Get all the profileList where sex equals to UPDATED_SEX
        defaultProfileShouldNotBeFound("sex.in=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllProfilesBySexIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where sex is not null
        defaultProfileShouldBeFound("sex.specified=true");

        // Get all the profileList where sex is null
        defaultProfileShouldNotBeFound("sex.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone equals to DEFAULT_PHONE
        defaultProfileShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultProfileShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone is not null
        defaultProfileShouldBeFound("phone.specified=true");

        // Get all the profileList where phone is null
        defaultProfileShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByIsVolunteerIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where isVolunteer equals to DEFAULT_IS_VOLUNTEER
        defaultProfileShouldBeFound("isVolunteer.equals=" + DEFAULT_IS_VOLUNTEER);

        // Get all the profileList where isVolunteer equals to UPDATED_IS_VOLUNTEER
        defaultProfileShouldNotBeFound("isVolunteer.equals=" + UPDATED_IS_VOLUNTEER);
    }

    @Test
    @Transactional
    public void getAllProfilesByIsVolunteerIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where isVolunteer in DEFAULT_IS_VOLUNTEER or UPDATED_IS_VOLUNTEER
        defaultProfileShouldBeFound("isVolunteer.in=" + DEFAULT_IS_VOLUNTEER + "," + UPDATED_IS_VOLUNTEER);

        // Get all the profileList where isVolunteer equals to UPDATED_IS_VOLUNTEER
        defaultProfileShouldNotBeFound("isVolunteer.in=" + UPDATED_IS_VOLUNTEER);
    }

    @Test
    @Transactional
    public void getAllProfilesByIsVolunteerIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where isVolunteer is not null
        defaultProfileShouldBeFound("isVolunteer.specified=true");

        // Get all the profileList where isVolunteer is null
        defaultProfileShouldNotBeFound("isVolunteer.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByFcmTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fcmToken equals to DEFAULT_FCM_TOKEN
        defaultProfileShouldBeFound("fcmToken.equals=" + DEFAULT_FCM_TOKEN);

        // Get all the profileList where fcmToken equals to UPDATED_FCM_TOKEN
        defaultProfileShouldNotBeFound("fcmToken.equals=" + UPDATED_FCM_TOKEN);
    }

    @Test
    @Transactional
    public void getAllProfilesByFcmTokenIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fcmToken in DEFAULT_FCM_TOKEN or UPDATED_FCM_TOKEN
        defaultProfileShouldBeFound("fcmToken.in=" + DEFAULT_FCM_TOKEN + "," + UPDATED_FCM_TOKEN);

        // Get all the profileList where fcmToken equals to UPDATED_FCM_TOKEN
        defaultProfileShouldNotBeFound("fcmToken.in=" + UPDATED_FCM_TOKEN);
    }

    @Test
    @Transactional
    public void getAllProfilesByFcmTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where fcmToken is not null
        defaultProfileShouldBeFound("fcmToken.specified=true");

        // Get all the profileList where fcmToken is null
        defaultProfileShouldNotBeFound("fcmToken.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where latitude equals to DEFAULT_LATITUDE
        defaultProfileShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the profileList where latitude equals to UPDATED_LATITUDE
        defaultProfileShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultProfileShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the profileList where latitude equals to UPDATED_LATITUDE
        defaultProfileShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where latitude is not null
        defaultProfileShouldBeFound("latitude.specified=true");

        // Get all the profileList where latitude is null
        defaultProfileShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where longitude equals to DEFAULT_LONGITUDE
        defaultProfileShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the profileList where longitude equals to UPDATED_LONGITUDE
        defaultProfileShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultProfileShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the profileList where longitude equals to UPDATED_LONGITUDE
        defaultProfileShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllProfilesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where longitude is not null
        defaultProfileShouldBeFound("longitude.specified=true");

        // Get all the profileList where longitude is null
        defaultProfileShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where organization equals to DEFAULT_ORGANIZATION
        defaultProfileShouldBeFound("organization.equals=" + DEFAULT_ORGANIZATION);

        // Get all the profileList where organization equals to UPDATED_ORGANIZATION
        defaultProfileShouldNotBeFound("organization.equals=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllProfilesByOrganizationIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where organization in DEFAULT_ORGANIZATION or UPDATED_ORGANIZATION
        defaultProfileShouldBeFound("organization.in=" + DEFAULT_ORGANIZATION + "," + UPDATED_ORGANIZATION);

        // Get all the profileList where organization equals to UPDATED_ORGANIZATION
        defaultProfileShouldNotBeFound("organization.in=" + UPDATED_ORGANIZATION);
    }

    @Test
    @Transactional
    public void getAllProfilesByOrganizationIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where organization is not null
        defaultProfileShouldBeFound("organization.specified=true");

        // Get all the profileList where organization is null
        defaultProfileShouldNotBeFound("organization.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where login equals to DEFAULT_LOGIN
        defaultProfileShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the profileList where login equals to UPDATED_LOGIN
        defaultProfileShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllProfilesByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultProfileShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the profileList where login equals to UPDATED_LOGIN
        defaultProfileShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllProfilesByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where login is not null
        defaultProfileShouldBeFound("login.specified=true");

        // Get all the profileList where login is null
        defaultProfileShouldNotBeFound("login.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email equals to DEFAULT_EMAIL
        defaultProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the profileList where email equals to UPDATED_EMAIL
        defaultProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the profileList where email equals to UPDATED_EMAIL
        defaultProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where email is not null
        defaultProfileShouldBeFound("email.specified=true");

        // Get all the profileList where email is null
        defaultProfileShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        profileRepository.saveAndFlush(profile);
        Long userId = user.getId();

        // Get all the profileList where user equals to userId
        defaultProfileShouldBeFound("userId.equals=" + userId);

        // Get all the profileList where user equals to userId + 1
        defaultProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].familyPhones").value(hasItem(DEFAULT_FAMILY_PHONES)))
            .andExpect(jsonPath("$.[*].bloodType").value(hasItem(DEFAULT_BLOOD_TYPE)))
            .andExpect(jsonPath("$.[*].allergicReactions").value(hasItem(DEFAULT_ALLERGIC_REACTIONS)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].isVolunteer").value(hasItem(DEFAULT_IS_VOLUNTEER.booleanValue())))
            .andExpect(jsonPath("$.[*].fcmToken").value(hasItem(DEFAULT_FCM_TOKEN)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .fullName(UPDATED_FULL_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .familyPhones(UPDATED_FAMILY_PHONES)
            .bloodType(UPDATED_BLOOD_TYPE)
            .allergicReactions(UPDATED_ALLERGIC_REACTIONS)
            .sex(UPDATED_SEX)
            .phone(UPDATED_PHONE)
            .isVolunteer(UPDATED_IS_VOLUNTEER)
            .fcmToken(UPDATED_FCM_TOKEN)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .organization(UPDATED_ORGANIZATION)
            .login(UPDATED_LOGIN)
            .email(UPDATED_EMAIL);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testProfile.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testProfile.getFamilyPhones()).isEqualTo(UPDATED_FAMILY_PHONES);
        assertThat(testProfile.getBloodType()).isEqualTo(UPDATED_BLOOD_TYPE);
        assertThat(testProfile.getAllergicReactions()).isEqualTo(UPDATED_ALLERGIC_REACTIONS);
        assertThat(testProfile.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.isIsVolunteer()).isEqualTo(UPDATED_IS_VOLUNTEER);
        assertThat(testProfile.getFcmToken()).isEqualTo(UPDATED_FCM_TOKEN);
        assertThat(testProfile.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testProfile.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testProfile.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testProfile.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = new Profile();
        profile1.setId(1L);
        Profile profile2 = new Profile();
        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);
        profile2.setId(2L);
        assertThat(profile1).isNotEqualTo(profile2);
        profile1.setId(null);
        assertThat(profile1).isNotEqualTo(profile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileDTO.class);
        ProfileDTO profileDTO1 = new ProfileDTO();
        profileDTO1.setId(1L);
        ProfileDTO profileDTO2 = new ProfileDTO();
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO2.setId(profileDTO1.getId());
        assertThat(profileDTO1).isEqualTo(profileDTO2);
        profileDTO2.setId(2L);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
        profileDTO1.setId(null);
        assertThat(profileDTO1).isNotEqualTo(profileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(profileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(profileMapper.fromId(null)).isNull();
    }
}
