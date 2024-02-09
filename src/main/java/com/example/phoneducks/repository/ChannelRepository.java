package com.example.phoneducks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.phoneducks.model.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findChannelById(Long id);
}
