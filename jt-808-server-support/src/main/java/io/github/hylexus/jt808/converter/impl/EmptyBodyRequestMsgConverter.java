package io.github.hylexus.jt808.converter.impl;

import io.github.hylexus.jt808.converter.RequestMsgBodyConverter;
import io.github.hylexus.jt808.msg.RequestMsgWrapper;
import io.github.hylexus.jt808.msg.req.EmptyRequestBody;

import java.util.Optional;

/**
 * @author hylexus
 * createdAt 2019/2/5
 * @see io.github.hylexus.jt808.msg.BuiltinMsgType#CLIENT_HEART_BEAT
 **/
public class EmptyBodyRequestMsgConverter implements RequestMsgBodyConverter<EmptyRequestBody> {

    @Override
    public Optional<EmptyRequestBody> convert2Entity(RequestMsgWrapper wrapper) {
        return Optional.of(new EmptyRequestBody());
    }
}