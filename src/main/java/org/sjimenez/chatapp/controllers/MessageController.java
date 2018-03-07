package org.sjimenez.chatapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: On this moment, this contoller is empty, and it is fine on this moment of the design
/** This controller is responsible to handle all actions related with Message Entity.
 * 
 * @author developer
 *
 */
@RestController
@RequestMapping(value = "/message")
public class MessageController {

	private static final Logger log = LoggerFactory.getLogger(MessageController.class);
}
