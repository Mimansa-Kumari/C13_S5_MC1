package com.example.Mc1;


import com.example.Mc1.Domain.Artist;
import com.example.Mc1.Domain.Track;
import com.example.Mc1.Repository.ITrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TrackRepositoryTest {
    @Autowired
    private ITrackRepository iTrackRepository;
    private Track track;
    private Artist artist;

    private Track track1;
    private Artist artist1;


    @BeforeEach
    public void setUp(){
        artist=new Artist(1,"Arjit");
        track=new Track(1,"abc",5,artist);
        artist1=new Artist(2,"TestArtist3");
        track1=new Track(2,"bca",2,artist1);
    }
    @AfterEach
    public void tearDown(){
        artist=null;
        track=null;
        artist1=null;
        track1=null;
        iTrackRepository.deleteAll();
    }
    @Test
    public void testFindAllSuccess(){
        iTrackRepository.insert(track);
        track.setTrackId(101);
        iTrackRepository.insert(track);
        track.setTrackId(102);
        iTrackRepository.insert(track);

        List<Track> trackList = iTrackRepository.findAll();
        assertEquals("Matching", 3, trackList.size());
    }
    @Test
    public void testFindAllFail(){
        iTrackRepository.insert(track);
        track.setTrackId(101);
        iTrackRepository.insert(track);
        track.setTrackId(102);
        iTrackRepository.insert(track);

        List<Track> customerList = iTrackRepository.findAll();
        assertNotEquals("Not-Matching", 4, customerList.size());
    }
    @Test
    public void testDeleteCustomerSuccess(){
        iTrackRepository.insert(track);
        iTrackRepository.deleteById(track.getTrackId());
        List<Track> trackList = iTrackRepository.findAll();
        assertEquals("Matching", 0, trackList.size());
    }
    @Test
    public void testDeleteTrackFail(){
        iTrackRepository.insert(track);
        iTrackRepository.deleteById(track.getTrackId());
        List<Track> trackList = iTrackRepository.findAll();
        assertNotEquals("Matching", 1, trackList.size());
    }
    @Test
    public void insertTrackSuccess(){
        iTrackRepository.insert(track);
        List<Track> trackList = iTrackRepository.findAll();
        assertEquals("Matching", 1, trackList.size());
    }
    @Test
    public void insertTrackFail(){
        iTrackRepository.insert(track);
        List<Track> trackList = iTrackRepository.findAll();
        assertNotEquals("Matching", 2, trackList.size());
    }
    @Test
    public void getFilterRatingsSuccess(){
        iTrackRepository.insert(track);
        List<Track> trackList = iTrackRepository.getFilterRatings();
        assertEquals("Matching", 1, trackList.size());
    }
    @Test
    public void getFilterRatingsFail(){
        iTrackRepository.insert(track);
        List<Track> trackList = iTrackRepository.getFilterRatings();
        assertNotEquals("Matching", 0, trackList.size());
    }
    @Test
    public void getJustinTracksFail(){
        iTrackRepository.insert(track1);
        List<Track> trackList = iTrackRepository.getJustinTracks();
        assertNotEquals("Matching", 0, trackList.size());
    }
    @Test
    public void getJustinTracksSuccess(){
        iTrackRepository.insert(track1);
        List<Track> trackList = iTrackRepository.getJustinTracks();
        assertEquals("Matching", 1, trackList.size());
    }
}
