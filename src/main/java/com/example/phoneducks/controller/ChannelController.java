package com.example.phoneducks.controller;


import com.example.phoneducks.model.ChannelEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.phoneducks.service.ChannelService;
import com.example.phoneducks.model.Channel;

import java.util.List;

@RestController
@RequestMapping("/channels/")
public class ChannelController {

    @Autowired
    private ChannelService service;

    @GetMapping("/all")
    public ResponseEntity<List<Channel>> getAllChannels(){
        List<Channel> channels = service.getAllChannels();

        if (channels.isEmpty()){
            return ResponseEntity.status(204).body(channels);
        } else {
            return ResponseEntity.status(200).body(channels);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createChannel(@RequestBody Channel channel){
        if(channel.getTitle() == null){
            return ResponseEntity.status(400).body("invalid title");
        }

        service.createChannel(channel);
        return ResponseEntity.status(201).body("new Channel created");
    }

    @PutMapping("/{id}/entry/create")
    public ResponseEntity<String> createEntry(@PathVariable Long id, @RequestBody ChannelEntry entry){
        // can still get bad request if you leave RequestBody empty
        if(!service.channelExistsById(id)){
            return ResponseEntity.status(400).body("invalid id");
        } else if(entry.getEntry().isEmpty()){
            return ResponseEntity.status(400).body("invalid entry");
        }

        service.createEntry(id, entry);
        return ResponseEntity.status(201).body("new entry was successfully added");
    }

    @GetMapping("/{id}/entry")
    public ResponseEntity<List<ChannelEntry>> findChannelEntryByChannelId(@PathVariable Long id){
        List<ChannelEntry> entries = service.findChannelEntryByChannelId(id);

        if(!service.channelExistsById(id)){
            return ResponseEntity.status(400).build();
        } else if (entries.isEmpty()) {
            return ResponseEntity.status(204).body(entries);
        }

        return ResponseEntity.status(200).body(entries);
    }

    @PatchMapping("/edit/{id}")
    public ResponseEntity<String> editTitle(@PathVariable Long id, @RequestBody Channel newTitle){
        if(!service.channelExistsById(id)){
            return ResponseEntity.status(400).body("invalid id");
        } else if (newTitle.getTitle().isEmpty()) {
            return ResponseEntity.status(400).body("invalid new title");
        }

        service.editChannelTitle(id, newTitle);
        return ResponseEntity.status(200).body("channel title change");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteChannel(@PathVariable Long id){
        if(!service.channelExistsById(id)){
            return ResponseEntity.status(400).body("invalid id");
        }
        String title = service.findChannelTitleById(id);
        List<ChannelEntry> entries = service.findChannelEntryByChannelId(id);

        List<Long> ids = entries.stream().map(entry -> entry.getId()).toList();
        service.deleteAllEntry(ids);

        service.deleteChannel(id);

        return ResponseEntity.status(200).body(
                "successful deletion of title: " + title);
    }

    // maybe "/{id}/entry/delete/{entryId}" to make sure that you get the right channel
    @DeleteMapping("/entry/delete/{entryId}")
    public ResponseEntity<String> deleteEntry(@PathVariable Long entryId){
        if(!service.entryExistsById(entryId)){
            return ResponseEntity.status(400).body("invalid id");
        }

        service.deleteEntryById(entryId);
        return ResponseEntity.status(200).body("delete message");
    }

    // maybe "/{id}/entry/edit/{entryId}" to make sure that you get the right channel
    @PatchMapping("/entry/edit/{entryId}")
    public ResponseEntity<String> editEntry(@PathVariable Long entryId, @RequestBody ChannelEntry newEntry){
        if(!service.entryExistsById(entryId)){
            return ResponseEntity.status(400).body("invalid id");
        } else if (newEntry.getEntry().isEmpty()) {
            return ResponseEntity.status(400).body("invalid new entry");
        }

        service.editEntryTitle(entryId, newEntry);
        return ResponseEntity.status(200).body("entry title change");
    }
}
