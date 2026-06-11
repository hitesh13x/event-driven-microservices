package com.eazybytes.profile.repository;

import com.eazybytes.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    Optional<Profile> findByMobileNumberAndActiveSw(String mobileNumber, boolean active);

}