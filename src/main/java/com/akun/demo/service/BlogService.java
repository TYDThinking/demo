package com.akun.demo.service;



import com.akun.demo.model.Blog;
import com.akun.demo.model.vo.BlogInfo;
import com.akun.demo.model.vo.BlogVO;

import java.util.List;

/**
 * @Package: com.akun.demo.service
 * @ClassName: BlogMapper
 * @Author: akun
 * @CreateTime: 2022/3/27 18:22
 * @Description:
 */
public interface BlogService {
    //1、查询：最新推荐的5篇文章， 若推荐文章不足5篇， 则选取：最新文章 （补齐5篇）
    List<Blog> queryRecoUpdFive();

    //2、查询：通过（博客ID） 查询到（博客信息）
    Blog queryBlogInfoById(Long id);


    //6、查询：通过（搜索字符串），模糊查询（标题、内容、描述）包含的所有博客
    List<Blog> queryBlogsByQueryStr(String query);

    //7、更新：通过（博客id）， 更新博客（访问次数+1）
    int updateBlogViewsNumById(Long id);

    //9、添加博客: 通过BlogInfo对象, 添加一个Blog
    Long addBlog(BlogInfo blogInfo);

    //10、更新博客：通过BlogInfo对象
    int updBlog(BlogInfo blogInfo);

    //11、删除博客：通过blogId
    int delBlogById(Long id);            //删除：blog

    //12、查询：所有博客信息
    List<Blog> queryAll();

    //13、查询：所有的BlogVO
    List<BlogVO> queryAllBlogVO();

    //15、查询：博客总数目
    Long queryBlogsNum();
}
