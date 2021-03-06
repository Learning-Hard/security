package io.github.learninghard;

import io.github.learninghard.dao.UserRepository;
import io.github.learninghard.entity.User;
import io.github.learninghard.security.core.utils.SpringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-07-20
 * \* Time: 16:31
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RunWith(JUnit4.class)
@SpringBootTest
public class UserControllerTest {

    /**
     * 注入配置文件
     */
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    /**
     * 在整个测试之前执行
     */
    @Before
    public void setup(){
        /**
         * 加载配置文件
         */
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("name", "learning")
                .param("username", "hard")
                .param("age", "25")
                .param("ageTo", "36")
//                .param("size","10")
//                .param("page","15")
//                .param("sort","age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println("返回结果是result："+result);
    }

    /**
     * pathVariable 的使用,正则的使用
     * jsonView注解的使用
     * @throws Exception
     */
    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("learning"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("返回结果是：" + result);
    }

    /**
     * 验证pathVariable的正则是否生效
     * @throws Exception
     */
    @Test
    public void whenGetInfoFail() throws Exception {
         mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * 验证RequestBody
     * @throws Exception
     */
    @Test
    public void whenCreateSuccess() throws Exception {
        System.out.println("发送的时间是：" + System.currentTimeMillis());
        Date date = new Date();
        String content = "{\"name\":\"learning\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("返回结果result：" + result);
    }

    /**
     * 验证Valid 的使用
     * @throws Exception
     */
    @Test
    public void whenUpdateSuccess() throws Exception {
        System.out.println("发送的时间是：" + System.currentTimeMillis());
        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        String content = "{\"id\":\"1\",\"name\":\"learning\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("返回结果result：" + result);
    }

    /**
     * 测试删除
     * @throws Exception
     */
    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 测试文件上传
     * @throws Exception
     */
    @Test
    public void whenUploadSuccess() throws Exception {
        String resoult = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes("UTF-8"))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resoult);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        userRepository.save(user);
    }


    @Test
    public void testGetBean(){
//        ConnectController bean = SpringUtil.getBean(ConnectController.class);
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String str : beanDefinitionNames) {
        }
    }
}
