package com.youngbrains.treeassist.service;




import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.youngbrains.treeassist.domain.Profile;
import com.youngbrains.treeassist.repository.ProfileRepository;
import com.youngbrains.treeassist.service.dto.ProfileCriteria;
import com.youngbrains.treeassist.service.dto.ProfileDTO;
import com.youngbrains.treeassist.service.mapper.ProfileMapper;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.StringFilter;
import net.logstash.logback.encoder.org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Profile.
 */
@Service
@Transactional
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    private final ProfileQueryService profileQueryService;


    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper, ProfileQueryService profileQueryService) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.profileQueryService = profileQueryService;
    }

    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save
     * @return the persisted entity
     */
    public ProfileDTO save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Get all the profiles.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProfileDTO> findAll() {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll().stream()
            .map(profileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    //TEST
    @Transactional(readOnly = true)
    public void sendPushNotificationsTo(List<ProfileDTO> profileDTOList, String userLatitude,String userLongitude) {

        for (ProfileDTO profileDTO: profileDTOList) {

            JSONObject body = new JSONObject();
            body.put("to", profileDTO.getFcmToken());
            body.put("priority", "high");

            JSONObject data = new JSONObject();
            data.put("title", "Помогите");
            data.put("body", "Требуется помощь по данным координатам");
            data.put("latitude", userLatitude);
            data.put("longitude", userLongitude);
            body.put("data", data);

            HttpEntity<String> request = new HttpEntity<>();

            CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            try {
                String firebaseResponse = pushNotification.get();
                log.debug(firebaseResponse, HttpStatus.OK);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ProfileDTO> help(String latitude,String longitude, String userLogin) {
        log.debug("Request to get all Profiles");

        ProfileCriteria profileCriteria = new ProfileCriteria();

        BooleanFilter booleanFilter = new BooleanFilter();
        booleanFilter.setEquals(true);
        profileCriteria.setIsVolunteer(booleanFilter);

        List <ProfileDTO> profileDTOList = profileQueryService.findByCriteria(profileCriteria);
        ArrayList<ProfileDTO> resultProfileDTOList = new ArrayList<>();

        try {
            for (int i = 0; i < profileDTOList.size(); i++) {

                ProfileDTO currentProfileDTO = profileDTOList.get(i);

                if(currentProfileDTO.getLogin().equals(userLogin)
                    || currentProfileDTO.getLongitude().length() < 1
                    || currentProfileDTO.getLatitude().length() < 1)
                    continue;

                if(resultProfileDTOList.size()>10)
                    break;

                HttpResponse<JsonNode> jsonResponse = Unirest.post("https://matrix.route.api.here.com/routing/7.2/calculatematrix.json")
                    .queryString("app_id", "54Gjd4XAgaftkEoQo61u")
                    .queryString("app_code", "oqRHrm2ZuCRt429n8-0EJw")
                    .queryString("matrixattributes", "ix,su")
                    .queryString("searchrange", "1000")
                    .queryString("summaryattributes", "cf,tt,di")
                    .queryString("mode", "fastest;car;traffic:enabled")
                    .queryString("start0", latitude + "," + longitude)
                    .queryString("destination0", currentProfileDTO.getLatitude() + "," + currentProfileDTO.getLongitude())
                    .asJson();
                JSONObject jsonObject = jsonResponse.getBody().getObject();
                JSONObject response = jsonObject.getJSONObject("response");
                JSONArray matrixEntry = response.getJSONArray("matrixEntry");

                if (!matrixEntry.getJSONObject(0).has("status")){
                    resultProfileDTOList.add(currentProfileDTO);
                }
            }

        }catch (UnirestException e){
            log.debug("Unirest Exception", e);
        }

        return resultProfileDTOList;
    }


    /**
     * Get one profile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findById(id)
            .map(profileMapper::toDto);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }
}
