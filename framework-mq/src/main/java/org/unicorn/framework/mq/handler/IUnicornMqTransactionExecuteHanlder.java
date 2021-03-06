package org.unicorn.framework.mq.handler;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import sun.security.util.PendingException;

/**
 * 本地事务反查处理器：由事务消息发起方实现
 * @author
 */
public interface IUnicornMqTransactionExecuteHanlder {
    /**
     *
     * @param messageExt
     * @return
     * @throws PendingException
     */
    boolean supports(Message message) throws PendingException;

    /**
     *
     * @param messageExt
     * @return
     * @throws PendingException
     */
    LocalTransactionState execute(Message message) throws PendingException;

    /**
     * Bean的顺序
     *
     * @return
     * @throws PendingException
     */
    default Integer order() throws PendingException {
        return 0;
    }
}
