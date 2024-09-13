package com.socialmedia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.socialmedia.entity.UserProfile;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserProfile,Integer> {

    Optional  <UserProfile> findByEmailId(String emailId);

    Optional<UserProfile> findByUserName(String username);
}
