package com.youngbrains.treeassist.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.youngbrains.treeassist.domain.Profile;
import com.youngbrains.treeassist.domain.*; // for static metamodels
import com.youngbrains.treeassist.repository.ProfileRepository;
import com.youngbrains.treeassist.service.dto.ProfileCriteria;
import com.youngbrains.treeassist.service.dto.ProfileDTO;
import com.youngbrains.treeassist.service.mapper.ProfileMapper;

/**
 * Service for executing complex queries for Profile entities in the database.
 * The main input is a {@link ProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProfileDTO} or a {@link Page} of {@link ProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfileQueryService extends QueryService<Profile> {

    private final Logger log = LoggerFactory.getLogger(ProfileQueryService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfileQueryService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Return a {@link List} of {@link ProfileDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProfileDTO> findByCriteria(ProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileMapper.toDto(profileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProfileDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findByCriteria(ProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileRepository.findAll(specification, page)
            .map(profileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Profile> specification = createSpecification(criteria);
        return profileRepository.count(specification);
    }

    /**
     * Function to convert ProfileCriteria to a {@link Specification}
     */
    private Specification<Profile> createSpecification(ProfileCriteria criteria) {
        Specification<Profile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Profile_.id));
            }
            if (criteria.getFullName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullName(), Profile_.fullName));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), Profile_.birthday));
            }
            if (criteria.getFamilyPhones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFamilyPhones(), Profile_.familyPhones));
            }
            if (criteria.getBloodType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBloodType(), Profile_.bloodType));
            }
            if (criteria.getAllergicReactions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAllergicReactions(), Profile_.allergicReactions));
            }
            if (criteria.getSex() != null) {
                specification = specification.and(buildSpecification(criteria.getSex(), Profile_.sex));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Profile_.phone));
            }
            if (criteria.getIsVolunteer() != null) {
                specification = specification.and(buildSpecification(criteria.getIsVolunteer(), Profile_.isVolunteer));
            }
            if (criteria.getFcmToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFcmToken(), Profile_.fcmToken));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitude(), Profile_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), Profile_.longitude));
            }
            if (criteria.getOrganization() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrganization(), Profile_.organization));
            }
            if (criteria.getLogin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogin(), Profile_.login));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Profile_.email));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Profile_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
