package com.example.Mc1.Domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Document
public class Track {
    @Id
    private int trackId;
    private String trackName;
    private int trackRating;
    private Artist trackArtist;
}
