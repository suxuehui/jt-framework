package io.github.hylexus.jt808.handler.impl.reflection.argument.resolver;

import io.github.hylexus.jt808.exception.ArgumentResolveException;
import io.github.hylexus.jt808.handler.impl.reflection.MethodParameter;
import io.github.hylexus.jt808.msg.RequestMsgBody;
import io.github.hylexus.jt808.msg.RequestMsgMetadata;
import io.github.hylexus.jt808.session.Session;

/**
 * 抄袭自 org.springframework.web.method.support.HandlerMethodArgumentResolver
 *
 * @author hylexus
 * Created At 2020-02-02 12:38 下午
 */
public interface HandlerMethodArgumentResolver {

    boolean supportsParameter(MethodParameter methodParameter);

    Object resolveArgument(MethodParameter parameter, RequestMsgMetadata metadata, Session session, RequestMsgBody msg) throws ArgumentResolveException;

}
