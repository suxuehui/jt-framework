package io.github.hylexus.jt808.boot.props.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

/**
 * @author hylexus
 * Created At 2019-08-28 11:59
 */
@Getter
@Setter
@ToString
@Validated
public class MsgProcessorProps {

    @NestedConfigurationProperty
    private MsgProcessorThreadPoolProps threadPool = new MsgProcessorThreadPoolProps();
}
