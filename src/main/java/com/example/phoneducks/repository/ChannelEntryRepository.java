package com.example.phoneducks.repository;

import com.example.phoneducks.model.ChannelEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelEntryRepository extends JpaRepository<ChannelEntry, Long> {
    List<ChannelEntry> findChannelEntryByChannelId(Long id);

    ChannelEntry findChannelEntryById(Long id);
}
