/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseChatMessageList.MessageListItemClickListener;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowBigExpression;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowFile;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowImage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowLocation;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowText;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVideo;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoice;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EaseMessageAdapter extends BaseAdapter{

	private final static String TAG = "msg";

	private Context context;
	
	private static final int HANDLER_MESSAGE_REFRESH_LIST = 0;
	private static final int HANDLER_MESSAGE_SELECT_LAST = 1;
    private static final int HANDLER_MESSAGE_SEEK_TO = 2;
	private static final int HANDLER_MESSAGE_OVER = 14;

	private static final int MESSAGE_TYPE_RECV_TXT = 0;
	private static final int MESSAGE_TYPE_SENT_TXT = 1;
	private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
	private static final int MESSAGE_TYPE_SENT_LOCATION = 3;
	private static final int MESSAGE_TYPE_RECV_LOCATION = 4;
	private static final int MESSAGE_TYPE_RECV_IMAGE = 5;
	private static final int MESSAGE_TYPE_SENT_VOICE = 6;
	private static final int MESSAGE_TYPE_RECV_VOICE = 7;
	private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
	private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
	private static final int MESSAGE_TYPE_SENT_FILE = 10;
	private static final int MESSAGE_TYPE_RECV_FILE = 11;
	private static final int MESSAGE_TYPE_SENT_EXPRESSION = 12;
	private static final int MESSAGE_TYPE_RECV_EXPRESSION = 13;
	
	
	public int itemTypeCount; 
	
	// reference to conversation object in chatsdk
	private EMConversation conversation;
	EMMessage[] messages = null;
	
    private String toChatUsername;

    private MessageListItemClickListener itemClickListener;
    private EaseCustomChatRowProvider customRowProvider;
    
    private boolean showUserNick;
    private boolean showAvatar;
    private Drawable myBubbleBg;
    private Drawable otherBuddleBg;

    private ListView listView;
	public static HashMap<String,List<EMMessage>> msgMap=new HashMap<String,List<EMMessage>>();
	public EaseMessageAdapter(Context context, String username, int chatType, ListView listView) {
		this.context = context;
		this.listView = listView;
		toChatUsername = username;
		this.conversation = EMClient.getInstance().chatManager().getConversation(username, EaseCommonUtils.getConversationType(chatType), true);
	}
	public  static boolean isOver=false;
	public static boolean isOpen=true;
	public static boolean isStop=true;
	public static long lastGameCount;
	Handler handler = new Handler() {
		private void refreshList() {
			// you should not call getAllMessages() in UI thread
			// otherwise there is problem when refreshing UI and there is new message arrive
			if(isStop){
				return;
			}
			java.util.List<EMMessage> var = conversation.getAllMessages();
			ArrayList<EMMessage> tempArr=new ArrayList<>();
			if(null!=var){
				//tempArr.addAll(var);
//				if(isOver){
//					tempArr.clear();
//					addMsg(tempArr);
//					return;
//				}
			}
		for (EMMessage msg:var
				 ) {
				EMTextMessageBody txtBody1 = (EMTextMessageBody) msg.getBody();
				BettingJson json1 = new Gson().fromJson(txtBody1.getMessage(), BettingJson.class);
				if(4==json1.notice_type){
					lastGameCount=json1.game_count;
					tempArr.add(msg);
					isOver=true;
					sendEmptyMessageDelayed(HANDLER_MESSAGE_OVER,25*1000);
				}else if(5==json1.notice_type){
					tempArr.add(msg);
					isOver=false;
				}else{
					if(isOver) {

					}else {
						if ((lastGameCount != json1.game_count)&&isOpen) {
							tempArr.add(msg);
						}

					}
				}



			}
			if(isOver){
				for (EMMessage msg:var
						) {
					EMTextMessageBody txtBody1 = (EMTextMessageBody) msg.getBody();
					BettingJson json1 = new Gson().fromJson(txtBody1.getMessage(), BettingJson.class);
					if(4==json1.notice_type){
						//tempArr.add(msg);
					}else if(5==json1.notice_type){
					//	tempArr.add(msg);
						isOver=false;
					}else{

					}
				}
				addMsg(tempArr);
				return;
			}
			///var=conversation.loadMoreMsgFromDB(var.get(0).getMsgId(),20);
			addMsg(tempArr);
		}
		
		@Override
		public void handleMessage(android.os.Message message) {
			switch (message.what) {
			case HANDLER_MESSAGE_REFRESH_LIST:
				post(new Runnable() {
					@Override
					public void run() {
						refreshList();
					}
				});

				break;
			case HANDLER_MESSAGE_SELECT_LAST:
                if (null!=messages&&messages.length > 0) {
                    listView.setSelection(messages.length - 1);
                }
                break;
            case HANDLER_MESSAGE_SEEK_TO:
                int position = message.arg1;
                listView.setSelection(position);
                break;
			case HANDLER_MESSAGE_OVER:
					isOver=false;
					break;
			default:
				break;
			}
		}
	};

	private void addMsg(List<EMMessage> var) {
		List<EMMessage> set=new ArrayList<>();
		set.addAll(var);
		List<EMMessage> emMessages = msgMap.get(toChatUsername);
		if(emMessages==null){
            //msgMap.put(toChatUsername,set);
            emMessages=set;
        }else{
//		if(emMessages.size()>15&& !isOver){
//		emMessages.clear();
//		}
            emMessages.addAll(set);
           // msgMap.put(toChatUsername,emMessages);
        }
		Set<EMMessage> setMsg=new HashSet<>();
		setMsg.addAll(emMessages);
		emMessages.clear();
		emMessages.addAll(setMsg);
		Collections.sort(emMessages, new Comparator<EMMessage>() {
            @Override
            public int compare(EMMessage lhs, EMMessage rhs) {
                EMTextMessageBody txtBody1 = (EMTextMessageBody) lhs.getBody();
                BettingJson json1 = new Gson().fromJson(txtBody1.getMessage(), BettingJson.class);
                EMTextMessageBody txtBody2 = (EMTextMessageBody) rhs.getBody();
                BettingJson json2 = new Gson().fromJson(txtBody2.getMessage(), BettingJson.class);
                long msgTime1 =json1.create_time;
                long msgTime2 =json2.create_time;
                if(msgTime1>msgTime2){
                    return 1;
                }else if(msgTime1<msgTime2){
                    return -1;
                }else {
                    if(json1.notice_type>json2.notice_type){
                        return -1;
                    }else if(json1.notice_type<json2.notice_type){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }
        });
//		Set<EMMessage> setMsg2=new HashSet<>();
//		setMsg2.addAll(emMessages);
 		if(null!=emMessages){
//			emMessages.clear();
//			emMessages.addAll(setMsg2);
			int count=emMessages.size()-20;
			for (int i = 0; count>0&&i < count; i++) {
				if(i<emMessages.size()) {
					emMessages.remove(i);
				}
			}
		}
		msgMap.put(toChatUsername,emMessages);
		messages = emMessages.toArray(new EMMessage[emMessages.size()]);
		conversation.markAllMessagesAsRead();
		notifyDataSetChanged();
	}

	public void refresh() {
		if (handler.hasMessages(HANDLER_MESSAGE_REFRESH_LIST)) {
			return;
		}
		android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST);
		handler.sendMessage(msg);
	}
	
	/**
     * refresh and select the last
     */
    public void refreshSelectLast() {
        final int TIME_DELAY_REFRESH_SELECT_LAST = 100;
        handler.removeMessages(HANDLER_MESSAGE_REFRESH_LIST);
        handler.removeMessages(HANDLER_MESSAGE_SELECT_LAST);
        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_REFRESH_LIST, TIME_DELAY_REFRESH_SELECT_LAST);
        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_SELECT_LAST, TIME_DELAY_REFRESH_SELECT_LAST);
    }
    
    /**
     * refresh and seek to the position
     */
    public void refreshSeekTo(int position) {
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST));
        android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_SEEK_TO);
        msg.arg1 = position;
        handler.sendMessage(msg);
    }
	

	public EMMessage getItem(int position) {
		if (messages != null && position < messages.length) {
			return messages[position];
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}
	
	/**
     * get count of messages
     */
    public int getCount() {
        return messages == null ? 0 : messages.length;
    }
	
	/**
	 * get number of message type, here 14 = (EMMessage.Type) * 2
	 */
	public int getViewTypeCount() {
	    if(customRowProvider != null && customRowProvider.getCustomChatRowTypeCount() > 0){
	        return customRowProvider.getCustomChatRowTypeCount() + 14;
	    }
        return 14;
    }
	

	/**
	 * get type of item
	 */
	public int getItemViewType(int position) {
		EMMessage message = getItem(position); 
		if (message == null) {
			return -1;
		}
		
		if(customRowProvider != null && customRowProvider.getCustomChatRowType(message) > 0){
		    return customRowProvider.getCustomChatRowType(message) + 13;
		}
		
		if (message.getType() == EMMessage.Type.TXT) {
		    if(message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
		        return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_EXPRESSION : MESSAGE_TYPE_SENT_EXPRESSION;
		    }
			return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT : MESSAGE_TYPE_SENT_TXT;
		}
		if (message.getType() == EMMessage.Type.IMAGE) {
			return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE : MESSAGE_TYPE_SENT_IMAGE;

		}
		if (message.getType() == EMMessage.Type.LOCATION) {
			return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_LOCATION : MESSAGE_TYPE_SENT_LOCATION;
		}
		if (message.getType() == EMMessage.Type.VOICE) {
			return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE : MESSAGE_TYPE_SENT_VOICE;
		}
		if (message.getType() == EMMessage.Type.VIDEO) {
			return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO : MESSAGE_TYPE_SENT_VIDEO;
		}
		if (message.getType() == EMMessage.Type.FILE) {
			return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FILE : MESSAGE_TYPE_SENT_FILE;
		}

		return -1;// invalid
	}
	
	protected EaseChatRow createChatRow(Context context, EMMessage message, int position) {
        EaseChatRow chatRow = null;
        if(customRowProvider != null && customRowProvider.getCustomChatRow(message, position, this) != null){
            return customRowProvider.getCustomChatRow(message, position, this);
        }
        switch (message.getType()) {
        case TXT:
            if(message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
                chatRow = new EaseChatRowBigExpression(context, message, position, this);
            }else{
                chatRow = new EaseChatRowText(context, message, position, this);
            }
            break;
        case LOCATION:
            chatRow = new EaseChatRowLocation(context, message, position, this);
            break;
        case FILE:
            chatRow = new EaseChatRowFile(context, message, position, this);
            break;
        case IMAGE:
            chatRow = new EaseChatRowImage(context, message, position, this);
            break;
        case VOICE:
            chatRow = new EaseChatRowVoice(context, message, position, this);
            break;
        case VIDEO:
            chatRow = new EaseChatRowVideo(context, message, position, this);
            break;
        default:
            break;
        }

        return chatRow;
    }
    

	@SuppressLint("NewApi")
	public View getView(final int position, View convertView, ViewGroup parent) {
		EMMessage message = getItem(position);
		if(convertView == null){
			convertView = createChatRow(context, message, position);
		}

		//refresh ui with messages
		((EaseChatRow)convertView).setUpView(message, position, itemClickListener);
		
		return convertView;
	}


	public String getToChatUsername(){
	    return toChatUsername;
	}
	
	
	
	public void setShowUserNick(boolean showUserNick) {
        this.showUserNick = showUserNick;
    }


    public void setShowAvatar(boolean showAvatar) {
        this.showAvatar = showAvatar;
    }


    public void setMyBubbleBg(Drawable myBubbleBg) {
        this.myBubbleBg = myBubbleBg;
    }


    public void setOtherBuddleBg(Drawable otherBuddleBg) {
        this.otherBuddleBg = otherBuddleBg;
    }


    public void setItemClickListener(MessageListItemClickListener listener){
	    itemClickListener = listener;
	}
	
	public void setCustomChatRowProvider(EaseCustomChatRowProvider rowProvider){
	    customRowProvider = rowProvider;
	}


    public boolean isShowUserNick() {
        return showUserNick;
    }


    public boolean isShowAvatar() {
        return showAvatar;
    }


    public Drawable getMyBubbleBg() {
        return myBubbleBg;
    }


    public Drawable getOtherBuddleBg() {
        return otherBuddleBg;
    }

}
