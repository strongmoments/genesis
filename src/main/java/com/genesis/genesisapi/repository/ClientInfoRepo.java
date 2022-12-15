package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientInfoRepo extends JpaRepository<ClientInfo, Long> {

    @Query("Select c from clientInfo c where c.isDeleted = 0")
    List<ClientInfo> findClientInfo();
    
    @Query("Select c from clientInfo c where c.clientInfoId  BETWEEN :clientId1 AND :clientId2")
    List<ClientInfo> clientBetweenClient(@Param("clientId1") Long clientId1, @Param("clientId2") Long clientId2);
    
    @Query("Select count(c) from clientInfo c ")
    Long findCountClientInfo();
    
    List<ClientInfo> findByClientTitleIgnoreCaseContaining(String clientTitle);
    
    @Query("Select c from clientInfo c where c.clientInfoId = :clientId")
    ClientInfo findByCientId(@Param("clientId") Long clientId1);
    

    @Query("SELECT d FROM clientInfo d WHERE d.clientInfoId BETWEEN :fromClient AND :toClient ")
    List<ClientInfo> getClientBetweenClient(@Param("fromClient") Long fromClient,  @Param("toClient") Long toClient);


}
