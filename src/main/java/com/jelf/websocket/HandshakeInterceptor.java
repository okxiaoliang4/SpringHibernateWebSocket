package com.jelf.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;

public class HandshakeInterceptor implements
		org.springframework.web.socket.server.HandshakeInterceptor {

	// 进入hander之前的拦截
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Map<String, Object> map)
			throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest()
					.getSession(false);
			if (session != null) {
				String userId = (String) session.getAttribute("userId");
				// 使用userName区分WebSocketHandler，以便定向发送消息
				// String userName = (String)
				// session.getAttribute("WEBSOCKET_USERID");
				map.put("WEBSOCKET_USERID", userId.trim());
			}
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse,
			WebSocketHandler webSocketHandler, Exception e) {

	}
}