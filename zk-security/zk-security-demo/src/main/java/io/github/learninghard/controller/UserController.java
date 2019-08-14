package io.github.learninghard.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.github.learninghard.entity.User;
import io.github.learninghard.vo.UserQueryCondition;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-07-20
 * \* Time: 17:04
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 用户操作类
 * \
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(User.UserSimpleView.class)
    public List<User> query(@RequestParam(required = false) String name,
                            UserQueryCondition condition,
                            @PageableDefault(size = 5, page = 6, sort = "name,desc") Pageable pageable) {
        System.out.println("参数name：" + name);
        System.out.println("参数UserQueryCondition:" + ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        List<User> list = new ArrayList<>();
        list.add(new User("learning", "123"));
        list.add(new User("hard", "123"));
        list.add(new User("cover", "123"));
        /**
         * 使用反射打印对象
         */
        System.out.println(ReflectionToStringBuilder.toString(list, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        return list;
    }

    @GetMapping("/{id:\\d+}") //路径上可以使用正则表达式
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id) {
        System.out.println("传入的参数id："+id);
        return new User("learning","12345678");
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println("接收到的参数user：" + ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("接收到的时间是：" + user.getBirthday());
        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@PathVariable String id, @Valid @RequestBody User user, BindingResult errors) {
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(
                    error -> {
                        FieldError err = (FieldError) error;
                        System.out.println(err.getField() + " " + error.getDefaultMessage());
                    });
        }
        System.out.println("接收到的参数user：" + ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("接收到的时间是：" + user.getBirthday());
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        System.out.println("删除数据的Id:" + id);
    }
}
