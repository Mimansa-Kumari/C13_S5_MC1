package com.example.Mc1.Service;

import com.example.Mc1.Domain.Track;
import com.example.Mc1.Exception.TrackAlreadyExistException;
import com.example.Mc1.Exception.TrackNotFoundException;

import java.util.List;

public interface ITrackService {

    Track saveTrack(Track track) throws TrackAlreadyExistException;
    boolean deleteTrack(int id) throws TrackNotFoundException;
    List<Track> getAllTrack();
    List<Track> getFilterData();
    List<Track> getJustin();

}
