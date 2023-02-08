package com.hunt.springeshop.service;

import com.hunt.springeshop.domain.Bucket;
import com.hunt.springeshop.domain.User;
import com.hunt.springeshop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProduct(Bucket bucket, List<Long> productIds);

    void removeProduct(Bucket bucket, List<Long> productIds);

    BucketDTO getBucketByUser(String name);
}
