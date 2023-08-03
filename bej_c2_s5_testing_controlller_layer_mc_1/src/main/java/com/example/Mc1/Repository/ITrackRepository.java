package com.example.Mc1.Repository;

import com.example.Mc1.Domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ITrackRepository  extends MongoRepository<Track,Integer> {
    @Query("{'trackRating':{$gt:4}}")
    List<Track> getFilterRatings();
    @Query("{'trackArtist.artistName':'TestArtist3'}")
    List<Track> getJustinTracks();
}
