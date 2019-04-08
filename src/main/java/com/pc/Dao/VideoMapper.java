package com.pc.Dao;

import com.github.pagehelper.Page;
import com.pc.Domain.Forum;
import com.pc.Domain.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface VideoMapper {

    /**
     * 获取所有数据
     */
    List<Movie>findAll();

    /**
     * 分页查询数据
     */
    Page<Movie>findByPage(@Param(value="keyword")String keyword);

    /**
     * 查询数据总数
     */
    int countAll(@Param(value="keyword")String keyword);

    /**
     * 查找分类列表
     * @return
     */
    List<Integer> findByType();

    /**
     * 根据列表查找电影
     */
    List<Movie> findByChannel(@RequestParam("ID")Integer id);

    /**
     * 观看次数+1
     * @return
     */
    int coutplus(@RequestParam("movieID")Integer movieId);

    /**
     * 电影详情
     * @param movieId
     * @return
     */
    Movie movieDetails(@RequestParam("movieID")Integer movieId);


    int searchType(@RequestParam("movieID")Integer movieId);

    List<Movie>searchMovies(@RequestParam("channelId")Integer channelId);

    int insertForum(Forum forum);

    List<Forum>forumlist();
}
