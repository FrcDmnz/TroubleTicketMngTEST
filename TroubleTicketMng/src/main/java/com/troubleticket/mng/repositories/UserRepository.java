package com.troubleticket.mng.repositories;

import com.troubleticket.mng.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for 'User' entity.
 * It provides standard CRUD operations and database interaction methods
 * thanks to Spring Data JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // JpaRepository automatically provides methods like save(), findById(), existsById(), deleteById()
    // The parameters <User, String> mean: manage the User entity, whose Primary Key (@Id) is a String (Username)
}