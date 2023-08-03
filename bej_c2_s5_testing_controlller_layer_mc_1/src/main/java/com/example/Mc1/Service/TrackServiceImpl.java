package com.example.Mc1.Service;

import com.example.Mc1.Domain.Track;
import com.example.Mc1.Exception.TrackAlreadyExistException;
import com.example.Mc1.Exception.TrackNotFoundException;
import com.example.Mc1.Repository.ITrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements ITrackService{

    ITrackRepository iTrackRepository;

    @Autowired
    public TrackServiceImpl(ITrackRepository iTrackRepository) {
        this.iTrackRepository = iTrackRepository;
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistException {
        if(iTrackRepository.findById(track.getTrackId()).isEmpty()){
            return iTrackRepository.insert(track);
        }else{
            throw new TrackAlreadyExistException();
        }
    }

    @Override
    public boolean deleteTrack(int id) throws TrackNotFoundException {
        if(iTrackRepository.findById(id).isEmpty()){
            throw new TrackNotFoundException();
        }else {
            return true;
        }
    }

    @Override
    public List<Track> getAllTrack() {
        return iTrackRepository.findAll();
    }

    @Override
    public List<Track> getFilterData() {
        return iTrackRepository.getFilterRatings();
    }

    @Override
    public List<Track> getJustin() {
        return iTrackRepository.getJustinTracks();
    }
}
