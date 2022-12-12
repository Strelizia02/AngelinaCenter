package top.strelitzia.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import top.strelitzia.vo.JsonResult;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@SuppressWarnings({"unused"})
public class TokenAspect {

    public static final String TOKEN_KEY = "Authorization";

    @Autowired
    private JedisCluster jedisCluster;

//    @Autowired
//    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(top.strelitzia.annotation.Token)")
    public void annotationPointcut() {

    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = request.getHeader(TOKEN_KEY);
        String mes;
        if (null == token){
            mes = "未检测到token，请携带token后再次请求";
        } else {
            String id = jedisCluster.get(token);;
            if (id != null) {
                return joinPoint.proceed();
            } else {
                mes = "token已失效或不存在，请重新登录";
            }
        }
        return JsonResult.failureWithCode("301", mes);
    }

}