package com.example.Mc1;


import com.example.Mc1.Domain.Artist;
import com.example.Mc1.Domain.Track;
import com.example.Mc1.Exception.TrackAlreadyExistException;
import com.example.Mc1.Exception.TrackNotFoundException;
import com.example.Mc1.Repository.ITrackRepository;
import com.example.Mc1.Service.TrackServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;


@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {

    @Mock
    private ITrackRepository iTrackRepository;

    @InjectMocks
    private TrackServiceImpl trackServiceImp;

    private Track track;
    private Artist artist;

    private Track track1;
    private Artist artist1;


    @BeforeEach
    public void setUp(){
        artist= new Artist(1,"TestArtist3");
        track=new Track(100, "TeraTrack", 5, artist);
        artist1= new Artist(2,"test");
        track1=new Track(101, "OkTrack", 2, artist1);
    }

    @AfterEach
    public void tearDown(){
        artist=null;
        track=null;
        artist1=null;
        track1=null;
    }

    @Test
    public void testAddTrackSuccess() throws TrackAlreadyExistException {
        when(iTrackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(null));
        when(iTrackRepository.insert(track)).thenReturn(track);
        Track insertedCustomer=trackServiceImp.saveTrack(track);
        assertEquals(insertedCustomer, track);
    }
    @Test
    public void testAddTrackFail() throws TrackAlreadyExistException {
        when(iTrackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(track));
        assertThrows(TrackAlreadyExistException.class, ()->trackServiceImp.saveTrack(track));
    }
    @Test
    public void testDeleteTrackSuccess() throws TrackNotFoundException {
        when(iTrackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(track));
        boolean result=trackServiceImp.deleteTrack(track.getTrackId());
        assertEquals(true, result);
    }
    @Test
    public void testDeleteTrackFail() throws TrackNotFoundException {
        when(iTrackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(null));
        assertThrows(TrackNotFoundException.class, ()->trackServiceImp.deleteTrack(track.getTrackId()));
    }
    @Test
    public void testFilterRatingsSuccess()
    {
        when(iTrackRepository.getFilterRatings()).thenReturn(List.of(track));
        List<Track> trackList = trackServiceImp.getFilterData();
        assertEquals(List.of(track),trackList);
        verify(iTrackRepository,times(1)).getFilterRatings();
    }
    @Test
    public void testJustineTrackSuccess()
    {
        when(iTrackRepository.getJustinTracks()).thenReturn(List.of(track));
        List<Track> trackList = trackServiceImp.getJustin();
        assertEquals(List.of(track),trackList);
        verify(iTrackRepository,times(1)).getJustinTracks();
    }

    //
}
