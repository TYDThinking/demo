package com.akun.demo.mapper;

import com.akun.demo.model.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BlogMapper")
public interface BlogMapper {

    //2、查询：（最新）的num篇文章
    List<Blog> selNewUpdTile(int num);

    //4、查询所有的博客
    List<Blog> selAllBlog();

    //8、查询：通过（搜索字符串），模糊查询（标题、内容、描述）包含的所有博客
    List<Blog> selAllBlogByQueryStr(String query);

    //9、更新：通过（博客id）， 更新博客（访问次数+1）
    int updViewsNumById(Long id);

    //15、查询：博客总数目
    Long selBlogNums();


    int deleteByPrimaryKey(Long id);

    int insert(Blog record);

    int insertSelective(Blog record);
    //3、查询：（博客ID） 查询博客信息
    Blog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);
}