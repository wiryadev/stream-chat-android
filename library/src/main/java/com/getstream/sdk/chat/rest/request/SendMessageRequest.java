package com.getstream.sdk.chat.rest.request;

import com.getstream.sdk.chat.model.Attachment;
import com.getstream.sdk.chat.model.ModelType;
import com.getstream.sdk.chat.rest.Message;
import com.getstream.sdk.chat.rest.codecs.GsonConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMessageRequest {
    @SerializedName("message")
    @Expose
    Map<String, Object> message;

    public SendMessageRequest(Message message, List<String>mentionedUserIDs) {
        if (message.getAttachments() != null && !message.getAttachments().isEmpty()) {
            boolean isGiphy = false;
            for (Attachment attachment : message.getAttachments()) {
                if (!isGiphy && attachment.getType().equals(ModelType.attach_giphy))
                    isGiphy = true;
            }
            if (isGiphy){
                message.setCommand(ModelType.attach_giphy);
                Map<String, String> commandInfo = new HashMap<>();
                commandInfo.put("name","Giphy");
                message.setCommandInfo(commandInfo);
            }
        }
        HashMap<String, Object>extraData = new HashMap<>();
        extraData.put("gender","Male");
        message.setExtraData(extraData);
        String messageStr = GsonConverter.Gson().toJson(message);
        this.message = GsonConverter.Gson().fromJson(messageStr, Map.class);

        if (mentionedUserIDs != null && !mentionedUserIDs.isEmpty())
            this.message.put("mentioned_users", mentionedUserIDs);
    }
}
