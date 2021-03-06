package io.github.hylexus.jt808.dispatcher;

import io.github.hylexus.jt.data.msg.MsgType;
import io.github.hylexus.jt808.converter.RequestMsgBodyConverter;
import io.github.hylexus.jt808.msg.RequestMsgBody;
import io.github.hylexus.jt808.msg.RequestMsgWrapper;
import io.github.hylexus.jt808.support.MsgConverterMapping;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author hylexus
 * createdAt 2019/1/24
 **/
@Slf4j
public abstract class AbstractRequestMsgDispatcher implements RequestMsgDispatcher {

    private MsgConverterMapping msgConverterMapping;

    public AbstractRequestMsgDispatcher(MsgConverterMapping msgConverterMapping) {
        this.msgConverterMapping = msgConverterMapping;
    }

    public void doDispatch(RequestMsgWrapper wrapper) throws Exception {

        final Optional<RequestMsgBody> subMsg = tryParseMsgBody(wrapper);
        if (!subMsg.isPresent()) {
            return;
        }

        wrapper.setBody(subMsg.get());
        this.doBroadcast(wrapper);
    }

    private Optional<RequestMsgBody> tryParseMsgBody(RequestMsgWrapper wrapper) {
        final MsgType msgType = wrapper.getMetadata().getMsgType();
        final Optional<RequestMsgBodyConverter> converter = this.msgConverterMapping.getConverter(msgType);
        if (!converter.isPresent()) {
            log.error("No [MsgConverter] found for msgType {}", msgType);
            return Optional.empty();
        }

        @SuppressWarnings("unchecked") final RequestMsgBodyConverter<RequestMsgBody> msgBodyConverter = converter.get();
        final Optional<RequestMsgBody> subMsg = msgBodyConverter.convert2Entity(wrapper.getMetadata());
        if (!subMsg.isPresent()) {
            log.debug("[MsgConverter] return empty(). converter:{}", msgBodyConverter.getClass());
            return Optional.empty();
        }
        return subMsg;
    }

    public abstract void doBroadcast(RequestMsgWrapper wrapper) throws Exception;
}
