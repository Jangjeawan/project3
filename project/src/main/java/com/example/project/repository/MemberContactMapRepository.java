package com.example.project.repository;

import com.example.project.entity.MemberContactMapEntity;
import com.example.project.entity.MemberContactMapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberContactMapRepository extends JpaRepository<MemberContactMapEntity, MemberContactMapId> {
    @Query("SELECT m.contactId FROM MemberContactMapEntity m WHERE m.memberId = :memberId")
    List<Long> findContactIdsByMemberId(@Param("memberId") String memberId);
}