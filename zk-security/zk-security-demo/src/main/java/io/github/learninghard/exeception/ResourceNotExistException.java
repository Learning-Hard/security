package io.github.learninghard.exeception;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-22
 * \* Time: 18:26
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 资源不存在异常
 * \
 */
public class ResourceNotExistException extends RuntimeException{

    private static final long serialVersionUID = 3596702225238770642L;

    private String id;

    public ResourceNotExistException(String id){
        super("Resouce not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
