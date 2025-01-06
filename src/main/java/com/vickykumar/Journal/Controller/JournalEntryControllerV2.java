package com.vickykumar.Journal.Controller;

import com.vickykumar.Journal.entity.JournalEntry;
import com.vickykumar.Journal.entity.UserEntity;
import com.vickykumar.Journal.service.JournalEntryService;
import com.vickykumar.Journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//  Controller ---> Service ---> Repository
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // Get Journal of user
    @GetMapping()
    public ResponseEntity<?> getAllJournalOfUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        List<JournalEntry> userJournal = journalEntryService.getAllEntry(userName);

        if(userJournal != null && !userJournal.isEmpty()){
            return new ResponseEntity<>(userJournal, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // SAVE Journal
    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> findEntryById(@PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        //List<JournalEntry> userJournalEntry = journalEntryService.getJournalOfUser(userName);

        UserEntity user = userService.findUserByName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE BY ID
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        boolean deleted = journalEntryService.deleteById(myId, userName);

        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // UPDATE
    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();

        UserEntity user = userService.findUserByName(userName);
        Optional<JournalEntry> optionalEntry = journalEntryService.findById(id);

        if(optionalEntry.isPresent()) {

            JournalEntry oldEntry = optionalEntry.get();
            boolean isOwnedByUser = user.getJournalEntries().stream()
                                    .anyMatch(x -> x.getId().equals(id));

            if (isOwnedByUser) {
                oldEntry.setTitle(newEntry.getTitle() != null
                        && !newEntry.getTitle().equals("")
                        ? newEntry.getTitle() : oldEntry.getTitle());

                oldEntry.setContent(newEntry.getContent() != null
                        && !newEntry.getContent().equals("")
                        ? newEntry.getContent() : oldEntry.getContent());

                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
