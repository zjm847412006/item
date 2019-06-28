package com.jt.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.vo.SysResult;

//因为需要跳转页面 所以不能使用restController
//如果后期返回值是json串.则在方法上添加@ResponseBody注解
@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout=3000,check=false)
	private DubboCartService cartService;
	
	/**
	 * 1.实现商品列表信息展现
	 * 2.页面取值: ${cartList}
	 */
	@RequestMapping("/show")
	public String findCartList(Model model) {
		Long userId = 7L; //暂时写死
		List<Cart> cartList = 		
		cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";//返回页面逻辑名称
	}
	/**
	 * 实现购物车数量的修改
	 * 如果url参数重视使用restful风格获取数据时
	 * 接收参数数对象并且属性匹配,则可以使用对象接收
	 */
	@ResponseBody
	@RequestMapping("/update/num/{itemId}/{num}")
	public SysResult updateCartNum(Cart cart) {
		try {
			Long userId=7L;
			cartService.updateCarNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	@RequestMapping
	public String deleteCart(Cart cart) {
		Long userId=7L;
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
		
	}
	/**
	 * 新增购物车
	 * 页面表单提交发起post请求
	 * 携带购物车参数
	 */
	@RequestMapping("/add/{itemId}")
	public String insertCart(Cart cart) {
		Long userId=7L;
		cart.setUserId(userId);
		cartService.insertCart(cart);
		//新增数据之后,展现购物车列表信息
		return "redirect:/cart/show.html";
	}
	
	
}
