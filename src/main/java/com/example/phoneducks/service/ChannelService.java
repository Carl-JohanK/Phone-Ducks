package com.example.phoneducks.service;

import com.example.phoneducks.model.ChannelEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.phoneducks.repository.ChannelRepository;
import com.example.phoneducks.repository.ChannelEntryRepository;
import com.example.phoneducks.model.Channel;
import java.util.List;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository repository;

    /* Probably better to have two different services so as not to confuse
     * between channels and entries for example there id's */

    @Autowired
    private ChannelEntryRepository entryRepository;

    public List<Channel> getAllChannels() {
        return repository.findAll();
    }

    public void createChannel(Channel channel) {
        repository.save(channel);
    }
    public void createEntry(Long id, ChannelEntry entry) {
        entry.setChannelId(id);
        entryRepository.save(entry);
    }

    public void editChannelTitle(Long id, Channel title) {
        String newtitle = title.getTitle();
        Channel editChannel = repository.findChannelById(id);
        editChannel.setTitle(newtitle);

        repository.save(editChannel);
    }
    public void editEntryTitle(Long id, ChannelEntry title){
        String newtitle = title.getEntry();
        ChannelEntry editEntry = entryRepository.findChannelEntryById(id);
        editEntry.setEntry(newtitle);

        entryRepository.save(editEntry);
    }

    public String findChannelTitleById(Long id) {
        return repository.findChannelById(id).getTitle();
    }

    public boolean channelExistsById(Long id) {
        return repository.existsById(id);
    }

    public boolean entryExistsById(Long id){
        return entryRepository.existsById(id);
    }

    public List<ChannelEntry> findChannelEntryByChannelId(Long id) {
        return entryRepository.findChannelEntryByChannelId(id);
    }

    public void deleteChannel(Long id) {
        repository.deleteById(id);
    }
    public void deleteAllEntry(List<Long> ids) {
        entryRepository.deleteAllById(ids);
    }
    public void deleteEntryById(Long id){
        entryRepository.deleteById(id);
    }
}
