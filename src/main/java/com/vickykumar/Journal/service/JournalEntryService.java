package com.vickykumar.Journal.service;

import com.vickykumar.Journal.Repository.JournalRepository;
import com.vickykumar.Journal.Repository.UserRepository;
import com.vickykumar.Journal.entity.JournalEntry;
import com.vickykumar.Journal.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalRepository journalEntryRepo;

    @Autowired
    private UserService userService;


    @Transactional  //this enables the function as 1 trnx and Rollback if any error occur in bw
    // Add @EnableTransactionManagement to main Class
    public void saveEntry(JournalEntry journalEntry, String userName){

        UserEntity user = userService.findUserByName(userName);     // Search for user by name
        JournalEntry saved = journalEntryRepo.save(journalEntry);   // Save Journal in Repo
        user.getJournalEntries().add(saved);                        // get JEntries of User and Save
        userService.saveUser(user);                                 // then save user

    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllEntry(String userName){
        UserEntity user = userService.findUserByName(userName);     // search user
        // return his JournalEntry
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName){
        UserEntity user = userService.findUserByName(userName);

        // finds Match of journal entry in user
        boolean isThere = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(id));
        if(isThere) {
            user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            userService.saveUser(user);
            journalEntryRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
