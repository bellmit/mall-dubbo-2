//package com.github.dubbo.trace;
//
//import com.alibaba.dubbo.rpc.RpcContext;
//import org.springframework.cloud.sleuth.Span;
//import org.springframework.cloud.sleuth.SpanExtractor;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//import java.util.Random;
//
//public class DubboSpanExtractor implements SpanExtractor<RpcContext> {
//
//    private Random random;
//    DubboSpanExtractor(Random random) {
//        this.random = random;
//    }
//
//    @Override
//    public Span joinTrace(RpcContext carrier) {
//        Map<String, String> attachments = carrier.getAttachments();
//        if (attachments.get(Span.TRACE_ID_NAME) == null) {
//            // can't build a Span without trace id
//            return null;
//        }
//        boolean skip = Span.SPAN_NOT_SAMPLED.equals(attachments.get(Span.SAMPLED_NAME));
//        long traceId = Span.hexToId(attachments.get(Span.TRACE_ID_NAME));
//        long spanId = attachments.get(Span.SPAN_ID_NAME) != null
//                ? Span.hexToId(attachments.get(Span.SPAN_ID_NAME))
//                : random.nextLong();
//        return buildParentSpan(carrier, skip, traceId, spanId);
//    }
//
//    private Span buildParentSpan(RpcContext carrier, boolean skip,
//                                 long traceId, long spanId) {
//        Map<String, String> attachments = carrier.getAttachments();
//        Span.SpanBuilder span = Span.builder().traceId(traceId).spanId(spanId);
//        String processId = attachments.get(Span.PROCESS_ID_NAME);
//        String parentName = attachments.get(Span.SPAN_NAME_NAME);
//        if (StringUtils.hasText(parentName)) {
//            span.name(parentName);
//        }
//        if (StringUtils.hasText(processId)) {
//            span.processId(processId);
//        }
//        if (attachments.get(Span.PARENT_ID_NAME) != null) {
//            span.parent(Span.hexToId(attachments.get(Span.PARENT_ID_NAME)));
//        }
//        span.remote(true);
//        if (skip) {
//            span.exportable(false);
//        }
//        return span.build();
//    }
//}
