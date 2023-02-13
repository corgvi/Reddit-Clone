package com.example.Reddit.Clone.service;


import com.example.Reddit.Clone.dto.SubredditDto;
import com.example.Reddit.Clone.exceptions.SubredditNotfoundExceptions;
import com.example.Reddit.Clone.mapper.SubredditMapper;
import com.example.Reddit.Clone.model.Subreddit;
import com.example.Reddit.Clone.repository.ISubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    @Autowired
    private ISubredditRepository subredditRepository;
    private SubredditMapper subredditMapper;

    public List<SubredditDto> getAllSubreddit() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SubredditNotfoundExceptions("Not found subreddit"));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    @Transactional
    public SubredditDto createSubreddit(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        return subredditDto;
    }
    @Transactional
    public SubredditDto updateSubreddit(SubredditDto subredditDto, Long id){
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SubredditNotfoundExceptions("Not found subreddit"));
        subreddit.setDescription(subredditDto.getDescription());
        subreddit.setName(subredditDto.getName());
        subredditRepository.save(subreddit);
        return subredditDto;
    }

    @Transactional
    public void deleteSubreddit(Long id){
        subredditRepository.deleteById(id);
    }
}
