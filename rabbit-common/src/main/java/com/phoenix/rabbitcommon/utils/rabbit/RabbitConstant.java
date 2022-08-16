package com.phoenix.rabbitcommon.utils.rabbit;

public class RabbitConstant {


    public static final String TEST_A_QUEUE_NAME = "test_a_queue";
    public static final String TEST_B_QUEUE_NAME = "test_b_queue";
    public static final String TEST_C_QUEUE_NAME = "test_c_queue";
    public static final String TEST_DEAD_QUEUE_NAME = "test_dead_queue";

    public static final String TEST_TOPIC_EXCHANGE_NAME = "test_topic_exchange";
    public static final String TEST_FANOUT_EXCHANGE_NAME = "test_fanout_exchange";
    public static final String TEST_HEADERS_EXCHANGE_NAME = "test_headers_exchange";
    public static final String TEST_DEAD_EXCHANGE_NAME = "test_dead_exchange";


    public static final String A_ROUTING_KEY = "test.a.*";
    public static final String B_ROUTING_KEY = "test.b.*";
    public static final String C_ROUTING_KEY = "test.c.*";
    public static final String DEAD_ROUTING_KEY = "test.dead.*";
}
