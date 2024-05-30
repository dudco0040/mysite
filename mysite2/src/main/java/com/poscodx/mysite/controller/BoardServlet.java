package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.controller.action.board.BoardAction;
import com.poscodx.mysite.controller.action.board.DeleteAction;
import com.poscodx.mysite.controller.action.board.ModifyAction;
import com.poscodx.mysite.controller.action.board.ModifyFormAction;
import com.poscodx.mysite.controller.action.board.ViewAction;
import com.poscodx.mysite.controller.action.board.WriteAction;
import com.poscodx.mysite.controller.action.board.WriteFormAction;
import com.poscodx.mysite.controller.action.board.WriteReplyAction;

public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"writeform", new WriteFormAction(),
			"write", new WriteAction(),
			"delete", new DeleteAction(),
			"view", new ViewAction(),
			"modifyform", new ModifyFormAction(),
			"modify", new ModifyAction(),
			"reply", new WriteReplyAction()
	);
			
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new BoardAction());
		// Default Action - "list" 
	}
}