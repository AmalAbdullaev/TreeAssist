package com.youngbrains.treeassist.web.rest;
import com.youngbrains.treeassist.service.ProfileService;
import com.youngbrains.treeassist.web.rest.errors.BadRequestAlertException;
import com.youngbrains.treeassist.web.rest.util.HeaderUtil;
import com.youngbrains.treeassist.service.dto.ProfileDTO;
import com.youngbrains.treeassist.service.dto.ProfileCriteria;
import com.youngbrains.treeassist.service.ProfileQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Profile.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private static final String ENTITY_NAME = "profile";

    private final ProfileService profileService;

    private final ProfileQueryService profileQueryService;

    private final AccountResource accountResource;

    public ProfileResource(ProfileService profileService, ProfileQueryService profileQueryService, AccountResource accountResource) {
        this.profileService = profileService;
        this.profileQueryService = profileQueryService;
        this.accountResource = accountResource;
    }

    /**
     * POST  /profiles : Create a new profile.
     *
     * @param profileDTO the profileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profileDTO, or with status 400 (Bad Request) if the profile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/profiles")
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/profiles/fcm-token/{fcmtoken}")
    public ResponseEntity<ProfileDTO> sendProfileFcmToken(@PathVariable String fcmtoken) throws URISyntaxException {
        log.debug("REST request send fcmtoken to Profile : {}", fcmtoken);
        ProfileDTO profileDTO = accountResource.getAccountProfile();
        profileDTO.setFcmToken(fcmtoken);
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/profiles/geo-position-service/{latitude}/{longitude}")
    public ResponseEntity<ProfileDTO> sendProfileGeoPosition(@PathVariable String latitude, @PathVariable String longitude) throws URISyntaxException {
        log.debug("REST request send geo service to Profile : {}", latitude, longitude);
        ProfileDTO profileDTO = accountResource.getAccountProfile();
        profileDTO.setLatitude(latitude);
        profileDTO.setLongitude(longitude);
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/profiles/volunteer-mode/{mode}")
    public ResponseEntity<ProfileDTO> changeProfileVolunteerMode(@PathVariable Boolean mode) throws URISyntaxException {
        log.debug("REST request change is volonteer to Profile : {}", mode);
        ProfileDTO profileDTO = accountResource.getAccountProfile();
        profileDTO.setIsVolunteer(mode);
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /profiles : Updates an existing profile.
     *
     * @param profileDTO the profileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profileDTO,
     * or with status 400 (Bad Request) if the profileDTO is not valid,
     * or with status 500 (Internal Server Error) if the profileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/profiles")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profileDTO);
        if (profileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, profileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profiles : get all the profiles.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of profiles in body
     */
    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(ProfileCriteria criteria) {
        log.debug("REST request to get Profiles by criteria: {}", criteria);
        List<ProfileDTO> entityList = profileQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /profiles/count : count all the profiles.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/profiles/count")
    public ResponseEntity<Long> countProfiles(ProfileCriteria criteria) {
        log.debug("REST request to count Profiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(profileQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /profiles/:id : get the "id" profile.
     *
     * @param id the id of the profileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/profiles/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        Optional<ProfileDTO> profileDTO = profileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileDTO);
    }

    @GetMapping("/profiles/help/{latitude}/{longitude}")
    public ResponseEntity<List<ProfileDTO>> help(@PathVariable String latitude, @PathVariable String longitude) {
        log.debug("REST request to get Profile help", latitude, longitude);
        ProfileDTO profile = accountResource.getAccountProfile();
        List<ProfileDTO> entityList = profileService.help(latitude,longitude,profile.getUserLogin());
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * DELETE  /profiles/:id : delete the "id" profile.
     *
     * @param id the id of the profileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
