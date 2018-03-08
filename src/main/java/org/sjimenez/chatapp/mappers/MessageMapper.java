package org.sjimenez.chatapp.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.sjimenez.chatapp.model.Message;

import java.util.List;

@Mapper
public interface MessageMapper {
    void insertMessage(Message message);
    List<Message> selectAll();
}
