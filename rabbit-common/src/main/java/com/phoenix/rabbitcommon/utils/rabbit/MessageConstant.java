package com.phoenix.rabbitcommon.utils.rabbit;

/**
 * 消息常量
 * @author Phoenix
 */
public class MessageConstant {

    /**
     * 消息状态
     * <p> 0: 消息初始化暂未发送</p>
     * <p> 1: 消息已经发送,但是不知道是成功了还是失败了</p>
     * <p> 2: 消息发送成功(到broker)</p>
     * <p> 3: 消息发送失败(没有到broker)</p>
     * <p> 4: 消息被预期消费者消费</p>
     * <p> 5: 消息变成死信,此时是被成功消费了,但是不是我们预期的消费者消费</p>
     */
    public enum Status{
        // 消息初始状态,消息生产者new一个消息
        INIT(0),
        // 消息已经发送状态,还不知道是成功还是失败
        SEND(1),
        // 消息发送成功状态
        SUCCESS(2),
        // 消息发送失败状态
        ERROR(3),
        // 消息已经被消息状态
        NORMALCONSUMER(4),
        // 消息被消费但是是被死信队列消费
        DEADCONSUMER(5);

        private final int status;

        Status(int status) {
            this.status = status;
        }

        public int status() {
            return status;
        }
    }
}
