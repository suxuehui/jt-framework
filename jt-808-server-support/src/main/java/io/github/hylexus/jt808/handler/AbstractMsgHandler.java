package io.github.hylexus.jt808.handler;

import io.github.hylexus.jt.data.msg.MsgType;
import io.github.hylexus.jt.utils.HexStringUtils;
import io.github.hylexus.jt808.codec.Encoder;
import io.github.hylexus.jt808.msg.RequestMsgBody;
import io.github.hylexus.jt808.msg.RequestMsgMetadata;
import io.github.hylexus.jt808.msg.RespMsgBody;
import io.github.hylexus.jt808.msg.resp.CommonReplyMsgBody;
import io.github.hylexus.jt808.session.Session;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

/**
 * @author hylexus
 * Created At 2019-08-24 15:45
 */
@Slf4j
public abstract class AbstractMsgHandler<T extends RequestMsgBody> implements MsgHandler<T> {

    private Encoder encoder = new Encoder();

    @Override
    public void handleMsg(RequestMsgMetadata metadata, T body, Session session) throws IOException, InterruptedException {
        Optional<RespMsgBody> respInfo = this.doProcess(metadata, body, session);
        if (!respInfo.isPresent()) {
            log.debug("MsgHandler return empty(). [SendResult2Client] canceled.");
            return;
        }

        RespMsgBody respBody = respInfo.get();
        byte[] respBytes = this.encoder.encodeRespMsg(respBody, session.getCurrentFlowId(), metadata.getHeader().getTerminalId());
        this.send2Client(session.getChannel(), respBytes);

        log.debug("<<<<<<<<<<<<<<< : {}", HexStringUtils.bytes2HexString(respBytes));
    }

    protected void send2Client(Channel channel, byte[] bytes) throws InterruptedException {
        ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(bytes)).sync();
        if (!future.isSuccess()) {
            log.error("ERROR : 'send data to client:'", future.cause());
        }
    }

    protected abstract Optional<RespMsgBody> doProcess(RequestMsgMetadata metadata, T msg, Session session);

    protected RespMsgBody commonSuccessReply(RequestMsgMetadata metadata, MsgType replyFor) {
        return CommonReplyMsgBody.success(metadata.getHeader().getFlowId(), replyFor);
    }
}
