package com.pc;

import com.github.pagehelper.Page;
import com.pc.Dao.VideoMapper;
import com.pc.Domain.Forum;
import com.pc.Domain.Movie;
import com.pc.Service.VideoService;
import com.pc.utils.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PcvideoApplicationTests {

	private Logger logger= LoggerFactory.getLogger(PcvideoApplicationTests.class);

	@Autowired
	private VideoService videoService;

	@Autowired
	private VideoMapper videoMapper;

	@Test
	public void contextLoads() {
		/*Page<Movie>pages=videoMapper.findByPage("机动");

		System.out.println(pages.toString());*/
		/*List<Integer>list=videoMapper.findByType();
		for (Integer num:
			 list) {
			System.out.println(num+",");
		}*/
		/*List<Movie>movies=videoMapper.findByChannel(1);
		for (Movie m:movies) {
			System.out.println(m);
		}*/
		/*Movie movie=videoService.movieDetails(50);
		System.out.println(movie);*/
		/*Movie movie=videoMapper.movieDetails(50);
		System.out.println(movie);*/
		/*List<Movie>movies=videoService.currentMovies(50);
		System.out.println(movies);*/

		List<Forum>forums= videoService.forumlist();
		System.out.println(forums);
	}

}
