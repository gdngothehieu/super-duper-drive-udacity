package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFilesByUserId(int userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileDataByFileId(int fileId);

    @Select("SELECT filename FROM FILES WHERE fileid = #{fileId}")
    String getFileNameByFileId(int fileId);

    @Select ("SELECT * FROM FILES WHERE filename = #{fileName}")
    String getFileByFileName(String fileName);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    int insertFile(File file);

    @Update("UPDATE FILES " +
            "SET filename = #{fileName}, " +
            "contenttype = #{contentType}, " +
            "filesize = #{fileSize}, " +
            "userid = #{userId}, " +
            "filedata = #{fileData} " +
            "WHERE fileId = #{fileId}")
    int updateFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);

}
