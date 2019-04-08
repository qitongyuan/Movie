package com.pc.Dao;


import com.pc.Domain.Movie;
import com.pc.Domain.Source;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SourceMapper {

    public void insertSource(Source source);

    public void insertMovieCotent(Movie movie);
}
