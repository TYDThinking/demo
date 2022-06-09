package com.akun.demo.controller;

import com.akun.demo.model.Blog;
import com.akun.demo.model.vo.BlogInfo;
import com.akun.demo.model.vo.BlogVO;
import com.akun.demo.service.BlogService;
import com.akun.demo.utils.JsonData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.akun.demo.controller
 * @ClassName: Blog
 * @Author: akun
 * @CreateTime: 2022/5/5 9:34
 * @Description:
 */
@RestController
@RequestMapping("api/vakun/pri/notice")
public class BlogController {
    @Autowired
    private BlogService blogService;        //blog业务层对象

    /**
     * 查询总条数
     * @return
     */
    @RequestMapping(value = "selectBlogNum", method = RequestMethod.GET)
    public JsonData selectBlogNum(){
        Long num = blogService.queryBlogsNum();
        System.out.println("num = " + num);
        return JsonData.buildSuccess("博客总条数",num);
    }

    /**
     * 查询博客信息列表***
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "selectBlogVOlist", method = RequestMethod.GET)
    public JsonData selectBlogVOlist (@RequestParam Integer page, @RequestParam Integer limit){

        Page pageObj = PageHelper.startPage(page, limit);
        List<BlogVO> blogVOS = blogService.queryAllBlogVO();
        PageInfo<BlogVO> pageInfo = new PageInfo<>(blogVOS);

        return JsonData.buildSuccess(pageInfo.getList());
    }

    /**
     * 根据id查询博客信息***
     * @param id
     * @return
     */
    @RequestMapping(value = "selectBlogById", method = RequestMethod.GET)
    public JsonData selectBlogById(@RequestParam Long id){
        if (id==null&&id==0){
            return JsonData.buildError("id值为空");
        }
        Blog blog = blogService.queryBlogInfoById(id);
        blogService.updateBlogViewsNumById(blog.getId());

        return JsonData.buildSuccess(blog);
    }

    /**
     * 添加博客***
     * @param blogInfo
     * @return
     */
    @RequestMapping(value = "addBlog", method = RequestMethod.POST)
    public JsonData addBlog (@RequestBody BlogInfo blogInfo, HttpServletRequest request){
        if (blogInfo.getTitle()==null&&"".equals(blogInfo.getTitle())){
            return JsonData.buildError("标题为空");
        }
        if (blogInfo.getContent()==null &&"".equals(blogInfo.getContent())){
            return JsonData.buildError("内容为空");
        }

        Integer ids = (Integer) request.getAttribute("user_id");
        blogInfo.setUid(ids);
        Long id = blogService.addBlog(blogInfo);

        return id==0 ? JsonData.buildError("插入博客失败"): JsonData.buildSuccess(id);
    }

    /**
     * 更新博客***
     * @param blogInfo
     * @return
     */
    @RequestMapping(value = "updateBlog", method = RequestMethod.POST)
    public JsonData updateBlog (@RequestBody BlogInfo blogInfo){
        if (blogInfo.getTitle()==null&&"".equals(blogInfo.getTitle())){
            return JsonData.buildError("标题为空");
        }
        if (blogInfo.getContent()==null &&"".equals(blogInfo.getContent())){
            return JsonData.buildError("内容为空");
        }

        int id = blogService.updBlog(blogInfo);

        return id==0? JsonData.buildError("更新用户失败"): JsonData.buildSuccess(id);
    }

    /**
     * 删除博客***
     * @param map
     * @return
     */
    @RequestMapping(value = "deleteBlog", method = RequestMethod.POST)
    public JsonData deleteBlog (@RequestBody Map<String,Object> map){
        String idString = (String) map.get("id");
        if (idString==null&&"".equals(idString)){
            return JsonData.buildError("id值为空");
        }
        Long id = Long.parseLong(idString);
        int i = blogService.delBlogById(id);
        return i==0? JsonData.buildError("删除用户失败"): JsonData.buildSuccess(i);
    }
}
