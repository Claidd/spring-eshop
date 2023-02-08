package com.hunt.springeshop.dao;

import com.hunt.springeshop.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
