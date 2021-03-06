package io.github.hylexus.jt808.support;

import io.github.hylexus.jt.data.msg.MsgType;
import io.github.hylexus.jt808.converter.RequestMsgBodyConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author hylexus
 * Created At 2019-08-22 21:21
 */
@Slf4j
public class MsgConverterMapping {

    //private Map<MsgType, RequestMsgBodyConverter> mapping;
    private Map<Integer, RequestMsgBodyConverter> mapping;

    public MsgConverterMapping() {
        this.mapping = new HashMap<>();
    }

    public Optional<RequestMsgBodyConverter> getConverter(MsgType msgType) {
        return Optional.ofNullable(mapping.get(msgType.getMsgId()));
    }

    public MsgConverterMapping registerConverter(MsgType msgType, RequestMsgBodyConverter converter) {
        return registerConverter(msgType, converter, false);
    }

    public MsgConverterMapping registerConverter(MsgType msgType, RequestMsgBodyConverter converter, boolean forceOverride) {

        final int msgId = msgType.getMsgId();
        if (containsConverter(msgType)) {
            final RequestMsgBodyConverter oldConverter = this.mapping.get(msgId);
            if (forceOverride || oldConverter.shouldBeReplacedBy(converter)) {
                this.mapping.put(msgId, converter);
                log.warn("duplicate msgType : {}, the MsgConverter {} was replaced by {}", msgType, oldConverter.getClass(), converter.getClass());
            }
        } else {
            this.mapping.put(msgId, converter);
        }

        return this;
    }

    public boolean containsConverter(MsgType msgType) {
        return this.mapping.containsKey(msgType.getMsgId());
    }

    public boolean isEmpty() {
        return this.mapping == null || this.mapping.isEmpty();
    }

    public Map<Integer, RequestMsgBodyConverter> getMsgConverterMappings() {
        return Collections.unmodifiableMap(this.mapping);
    }
}
