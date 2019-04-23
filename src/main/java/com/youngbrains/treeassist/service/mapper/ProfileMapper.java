package com.youngbrains.treeassist.service.mapper;

import com.youngbrains.treeassist.domain.*;
import com.youngbrains.treeassist.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "userId", target = "user")
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
