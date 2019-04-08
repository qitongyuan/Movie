package com.pc.Service;

import com.github.pagehelper.Page;
import com.pc.Domain.Forum;
import com.pc.Domain.Movie;

import java.util.List;
import java.util.Map;

public interface VideoService {

    List<Movie>findAll();

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 每页记录数
     * @return
     */
    Page<Movie>findByPage(int pageNo,int pageSize,String keyword);

    /**
     * 记录总数
     * @return
     */
    int coutAll(String keyword);

    /**
     * 归类查询（只要前10）
     */
    Map<String,List<Movie>>sortByType();

    /**
     * 视频观看+1
     * @return
     */
    int playcountplus(Integer movieId);

    /**
     * 查看电影详情
     * @param movieId
     * @return
     */
    Movie movieDetails(Integer movieId);

    /**
     * 查看相近的五部电影
     * @param movieId
     * @return
     */
    List<Movie>currentMovies(Integer movieId);


    /**
     * 帖子提交
     * @return
     */
    int insertForum(Forum forum);

    /**
     * 列表展示
     * @return
     */
    List<Forum>forumlist();
}
