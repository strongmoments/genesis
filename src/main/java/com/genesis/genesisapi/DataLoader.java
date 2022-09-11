/*
package com.genesis.genesisapi;

import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private AccountRepo accountRepo;
    private ClientInfoRepo clientInfoRepo;
    private TallysheetRepo tallysheetRepo;
    private WSOInfoRepo wsoInfoRepo;
    private LotInfoRepo lotInfoRepo;
    private DeliveryListRepo deliveryListRepo;

    @Autowired
    public DataLoader(AccountRepo accountRepo, ClientInfoRepo clientInfoRepo, TallysheetRepo tallysheetRepo, WSOInfoRepo wsoInfoRepo, LotInfoRepo lotInfoRepo, DeliveryListRepo deliveryListRepo) {
        this.clientInfoRepo = clientInfoRepo;
        this.accountRepo = accountRepo;
        this.tallysheetRepo = tallysheetRepo;
        this.wsoInfoRepo = wsoInfoRepo;
        this.lotInfoRepo = lotInfoRepo;
        this.deliveryListRepo = deliveryListRepo;
    }

    public void run(ApplicationArguments args) {
        Account account = new Account();
        account.setUsername("ashok");
        account.setPassword("ashok");
        accountRepo.save(account);

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClientTitle("Goods wills pvt ltd");
        clientInfo.setClientAddress1("Plot no 110, Barlin");
        clientInfo.setClientCity("jbdfv");
        clientInfo.setClientZip("123123");
        clientInfo.setClientState("hfdbdh");
        clientInfo.setClientCountry("Singapur");
        clientInfoRepo.save(clientInfo);

        ClientInfo clientInfo1 = new ClientInfo();
        clientInfo1.setClientTitle("Texas pvt ltd");
        clientInfo1.setClientAddress1("Plot no 324, dfs");
        clientInfo1.setClientCity("jbdfv");
        clientInfo1.setClientZip("123123");
        clientInfo1.setClientState("hfdbdh");
        clientInfo1.setClientCountry("Singapur");
        clientInfoRepo.save(clientInfo1);

        TallySheet tallySheet = new TallySheet();
        tallySheet.setExVessel("dsv");
        tallySheet.setTallysheetNumber("TSvdfgv2231hbv");
        tallySheet.setWarehouseName("bksdhf");
        tallysheetRepo.save(tallySheet);


    }
}
*/
