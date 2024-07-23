package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {
    Optional<Follow> findFollowByUuidUserFollowerAndUuidUserFollowing(String uuidUserFollower, String uuidUserFollowing);

    Page<Follow> findAllByUuidUserFollowing(String uuidFollowing, Pageable pageable);

    Page<Follow> findAllByUuidUserFollower(String uuidFollower, Pageable pageable);

}
