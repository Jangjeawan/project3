package com.example.project.repository;

import com.example.project.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    @Query(value = "SELECT c.* FROM MEMBERSCONTACTS c JOIN MEMBERCONTACTMAP m ON c.CONTACTID = m.CONTACTID WHERE m.MEMBERID = :memberId", nativeQuery = true)
    List<ContactEntity> findByMemberId(@Param("memberId") String memberId);

    @Modifying
    @Query(value = "INSERT INTO MEMBERCONTACTMAP (MEMBERID, CONTACTID) VALUES (:memberId, :contactId)", nativeQuery = true)
    void mapMemberToContact(@Param("memberId") String memberId, @Param("contactId") Long contactId);

    List<ContactEntity> findByNameContaining(String name);
}