package com.example.Mc1;

import com.example.Mc1.Controller.TrackContorller;
import com.example.Mc1.Domain.Artist;
import com.example.Mc1.Domain.Track;
import com.example.Mc1.Exception.TrackAlreadyExistException;
import com.example.Mc1.Exception.TrackNotFoundException;
import com.example.Mc1.Service.ITrackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class Mc1ApplicationTests {

	@Mock
	private ITrackService iTrackServices;
	@InjectMocks
	private TrackContorller trackContorller;

	@Autowired
	private MockMvc mockMvc;
	private Track track;
	private Track track1;

	@BeforeEach
	public void setUp(){
		track=new Track(101, "Song1",2, new Artist(1, "Priyanshu"));
		track1=new Track(102, "Song2",4, new Artist(1, "Justin Bieber"));

		mockMvc = MockMvcBuilders.standaloneSetup(trackContorller).build();
	}
	@AfterEach
	public void tearDown(){
		track=null;
		track1=null;
	}
	@Test
	public void testAddTrack() throws Exception{
		when(iTrackServices.saveTrack(track)).thenReturn(track);
		mockMvc.perform(
						post("/api/Track/v1/addTrack")
								.contentType(MediaType.APPLICATION_JSON)
								.content(convertToJson(track)))
				.andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
		verify(iTrackServices, times(1)).saveTrack(track);

	}
	@Test
	public void testAddFail() throws Exception {
		when(iTrackServices.saveTrack(track)).thenThrow(TrackAlreadyExistException.class);

		mockMvc.perform(
						post("/api/Track/v1/addTrack")
								.contentType(MediaType.APPLICATION_JSON)
								.content(convertToJson(track)))
				.andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
		verify(iTrackServices, times(1)).saveTrack(track);

	}
	@Test
	public void testDeleteSuccess() throws Exception {
		when(iTrackServices.deleteTrack(track.getTrackId())).thenReturn(true);
		mockMvc.perform(delete("/api/Track/v1/deleteTrack/101"))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
		verify(iTrackServices, times(1)).deleteTrack(track.getTrackId());
	}
	@Test
	public void testDeleteFail() throws Exception {
		when(iTrackServices.deleteTrack(track.getTrackId())).thenThrow(TrackNotFoundException.class);
		mockMvc.perform(delete("/api/Track/v1/deleteTrack/101"))
				.andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
		verify(iTrackServices, times(1)).deleteTrack(track.getTrackId());
	}
	@Test void testRatingFilter()throws Exception{

		when(iTrackServices.getFilterData()).thenReturn(List.of(track1));
		mockMvc.perform(get("/api/Track/v1/filterRatings"))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
		verify(iTrackServices, times(1)).getFilterData();
	}
	@Test void testJustin()throws Exception{

		when(iTrackServices.getJustin()).thenReturn(List.of(track1));
		mockMvc.perform(get("/api/Track/v1/JustinTracks"))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
		verify(iTrackServices, times(1)).getJustin();
	}

	public static String convertToJson(final Object object){
		String result="";
		ObjectMapper mapper=new ObjectMapper();
		try{
			result=mapper.writeValueAsString(object);
		}catch(JsonProcessingException e){
			e.printStackTrace();
		}
		return result;
	}
}
