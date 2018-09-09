package com.spider.processer;

import com.spider.bean.MusicComment;
import com.spider.utils.MusicEncrypt;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Music163Processer implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    // 歌曲地址  id 歌曲id
    private static final String SONGNADDRESS = "https://music\\.163\\.com/song\\?id=(\\d+)";
    /**
     * 获取评论地址
     */
    private static final String COMMENT = "https://music\\.163\\.com/weapi/v1/resource/comments/*+";
    /**
     * 正则匹配歌id
     */
    private static final String SONG_ID_REGEX = "song\\?id=(\\d*)";

    /**
     * 正则匹配歌曲id 通过匹配评论地址url
     */
    private static final String SONG_ID = "R_SO_4_(\\d+)";
    /***
     * 密钥
     */
    private static String sKey = "0CoJUm6Qyw8W8jud";

    Pattern compileSong = Pattern.compile(SONG_ID_REGEX);
    Pattern songId = Pattern.compile(SONG_ID);
    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);

    @Override
    public void process(Page page) 
    {
        if (page.getUrl().regex(SONGNADDRESS).match()) 
        {
        	Matcher matcher = compileSong.matcher(page.getUrl().toString());
            if (matcher.find()) 
            {
            	for(int i = 1;i<2;++i)//请求评论页面
            	{
            		String _songId = matcher.group(1);
                    Request request = new Request("https://music.163.com/weapi/v1/resource/comments/R_SO_4_" + _songId + "?csrf_token=");
                    request.setMethod("post");
                    request.setRequestBody(HttpRequestBody.form(makePostParam(_songId, "true", i), "UTF-8"));
                    page.addTargetRequest(request);
            	}
            }
        }
        else if(page.getUrl().regex(COMMENT).match())
        {
        	Matcher matcher1 = songId.matcher(page.getUrl().toString());
            if (matcher1.find())//或取评论页面信息
            {
            	System.out.println(page.getJson().toString());
                String songId = matcher1.group(1);
                List<String> contentList = new JsonPathSelector("$.comments.[*].content").selectList(page.getRawText());
                List<String> likeCountList = new JsonPathSelector("$.comments.[*].likedCount").selectList(page.getRawText());
                List<String> nicknameList = new JsonPathSelector("$.comments.[*].user.nickname").selectList(page.getRawText());
                List<String> timeList = new JsonPathSelector("$.comments.[*].time").selectList(page.getRawText());
                String stringTotal = new JsonPathSelector("$.total").select(page.getRawText());
                List<MusicComment> commentsList = new ArrayList<>();
                for (int i = 0; i < contentList.size(); i++)//信息临时保存于list
                {
                    MusicComment comment = new MusicComment();
                    comment.setContent(filterEmoji(contentList.get(i)));
                    comment.setSongId(songId);
                    comment.setLikedCount(Integer.valueOf(likeCountList.get(i)));
                    comment.setNickName(nicknameList.get(i));
                    comment.setTime(stampToDate(Long.valueOf(timeList.get(i))));
                    System.out.println(comment.getContent());
                    commentsList.add(comment);
                }
                page.putField("commentsList", commentsList);//调用Music163Pipeline 保存信息
            }
        }
        return;
     } 
    @Override
    public Site getSite() {
        return site;
    }
    
    

 ///////////////////////////////////////////////评论获取///////////////////////////////////////////////////////
    public static String makeContent(String songId, String paging, int nowPageNum) //获取评论 每页获取20条*** 参考：https://link.jianshu.com/?t=https://www.zhihu.com/question/36081767
    {
        int offset;
        if (nowPageNum < 1) {
            offset = 20;
        }
        offset = (nowPageNum - 1) * 20;
        String baseContent = "{rid: \"R_SO_4_%s\",offset: \"%d\",total: \"%s\",limit: \"20\",csrf_token: \"\"}";
        return String.format(baseContent, songId, offset, paging);
    }

    
    public Map<String, Object> makePostParam(String content) //获取2个评论参数  **
    {
        Map<String, Object> map = new HashMap<>();
        map.put("params", MusicEncrypt.AESEncrypt((MusicEncrypt.AESEncrypt(content, sKey)), "FFFFFFFFFFFFFFFF"));
        map.put("encSecKey", MusicEncrypt.rsaEncrypt());
        return map;
    }

    public Map<String, Object> makePostParam(String songId, String paging, int nowPageNum)//评论获取 *
    {
        return makePostParam(makeContent(songId, paging, nowPageNum));
    }
//////////////////////////////////////////////////////////////评论获取///////////////////////////////////////
    
    
    
    
    public static String filterEmoji(String source) //处理评论中的表情
    {
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        } else {
            return source;
        }
    }

    public static String stampToDate(long s) //时间各式转发
    {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = s;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
