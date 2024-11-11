package com.example.testdb.repository;

import com.example.testdb.entity.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends JpaRepository <MyEntity, Long> {

}
