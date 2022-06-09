package com.akun.demo.service.impl;

import com.akun.demo.mapper.BlogMapper;
import com.akun.demo.model.Blog;
import com.akun.demo.model.Manager;
import com.akun.demo.model.vo.BlogInfo;
import com.akun.demo.model.vo.BlogVO;
import com.akun.demo.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.akun.demo.service.impl
 * @ClassName: BlogServiceImpl
 * @Author: akun
 * @CreateTime: 2022/3/27 18:26
 * @Description: 博客：业务层
 */
@Service("BlogServiceImpl")
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogMapper blogMapper;              //博客：持久层

    @Value("${recommendsize}")
    private Integer recommentBlogNum;   //设置首页：最新最近的（博客数目）

    Logger logger = LoggerFactory.getLogger(getClass());

    //1、查询：最新推荐的（recommentBlogNum）篇文章 （默认为：5篇）
    // 当（推荐文章）不足（recommentBlogNum）篇， 则选取：最新文章 （补齐recommentBlogNum篇）
    @Override
    public List<Blog> queryRecoUpdFive() {
        List<Blog> temp = blogMapper.selNewUpdTile(recommentBlogNum);
        for(Blog b1:temp){
            logger.info("最新文章 ===>>> " + b1.getTitle() + "====>>> " + b1.getManager());
        }
        return temp;
    }

    //2、查询：通过（博客ID） 查询到（博客信息）
    @Override
    public Blog queryBlogInfoById(Long id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    //6、查询：通过（搜索字符串），模糊查询（标题、内容、描述）包含的所有博客
    @Override
    public List<Blog> queryBlogsByQueryStr(String query) {
        List<Blog> blogs = blogMapper.selAllBlogByQueryStr(query);
//        for(Blog blog : blogs)      //为博客：设置总评论次数
//            blog.setCommentNum(commentMapper.selComNumByBlogId(blog.getId()));
        return blogs;
    }

    //7、更新：通过（博客id）， 更新博客（访问次数+1）
    @Override
    public int updateBlogViewsNumById(Long id) {
        return blogMapper.updViewsNumById(id);
    }


    //9、添加: 通过BlogInfo对象, 添加一个Blog, 返回博客（自增ID）
    @Override
    public Long addBlog(BlogInfo blogInfo) {
        Blog blog = new Blog(blogInfo.getTitle(), blogInfo.getContent(), blogInfo.getFirstPicture());

        Manager manager = new Manager();                 //设置：用户id
        manager.setId(blogInfo.getUid());
        blog.setManager(manager);
        blogMapper.insert(blog);
        return blog.getId();
    }

    //10、更新：通过BlogInfo对象
    @Override
    public int updBlog(BlogInfo blogInfo) {
        Blog blog = new Blog();
        blog.setId(blogInfo.getBid());
        blog.setTitle(blogInfo.getTitle());
        blog.setContent(blogInfo.getContent());
        blog.setFirstPicture(blogInfo.getFirstPicture());
        return blogMapper.updateByPrimaryKeySelective(blog);
    }


    //11、删除博客：通过blogId
    @Override
    public int delBlogById(Long id) {
        return blogMapper.deleteByPrimaryKey(id);
    }

    //12、查询：所有博客信息
    @Override
    public List<Blog> queryAll() {
        return blogMapper.selAllBlog();
    }
    //13、查询：所有的BlogVO
    @Override
    public List<BlogVO> queryAllBlogVO() {
        List<Blog> blogs = blogMapper.selAllBlog();
        List<BlogVO> blogVOs = new ArrayList<>();
        for(Blog blog:blogs){
            BlogVO blogVO = new BlogVO();
            BeanUtils.copyProperties(blog, blogVO);
//            blogVO.setTypeName(blog.getType().getName());
            blogVOs.add(blogVO);
        }
        return blogVOs;
    }

    //15、查询：博客总数目
    @Override
    public Long queryBlogsNum() {
        return blogMapper.selBlogNums();
    }
}
