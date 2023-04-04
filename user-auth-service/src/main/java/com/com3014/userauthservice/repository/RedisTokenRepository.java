package com.com3014.userauthservice.repository;

import com.com3014.userauthservice.model.BlacklistedToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<BlacklistedToken, String> {}
