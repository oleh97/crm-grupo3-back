package com.anonymous.crm.repository;

import com.anonymous.crm.model.Video;
import com.anonymous.crm.model.Youtuber;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    @Query("SELECT SUM (V.monetization) FROM Video V WHERE V.videoUploadDate BETWEEN :start AND :end")
    Double sumAllVideosProfitInMonth(LocalDate start, LocalDate end);

}
