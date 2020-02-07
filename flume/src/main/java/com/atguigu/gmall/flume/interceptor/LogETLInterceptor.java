package com.atguigu.gmall.flume.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class LogETLInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {

        byte[] body = event.getBody();
        String log = new String(body, Charset.forName("utf8"));
        if(log.contains("start")){
            if(LogUtil.validateStart(log)){
                return event;
            }
        }else{
            if(LogUtil.validateEvent(log)){
                return event;
            }
        }


        return null;
    }

    @Override
    public List<Event> intercept(List<Event> events) {

        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()){
            Event event = iterator.next();
            if(intercept(event)==null){
                iterator.remove();
            }
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new LogETLInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }


}
