package org.nick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTTPErrorHandler{
 

  
 @RequestMapping(value="/400")
 public String error400(){
	  return "/customErrorPages/400";
 }
  
 @RequestMapping(value="/404")
 public String error404(){
  return "/customErrorPages/404";
 }
  
 @RequestMapping(value="/500")
 public String error500(){
  return "/customErrorPages/500";
 }
  
  
}