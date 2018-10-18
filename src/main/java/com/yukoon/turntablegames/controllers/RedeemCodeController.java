package com.yukoon.turntablegames.controllers;

import com.yukoon.turntablegames.entities.RedeemCode;
import com.yukoon.turntablegames.services.RedeemCodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RedeemCodeController {
	@Autowired
	private RedeemCodeService redeemCodeService;

	@RequiresRoles("admin")
	@RequiresPermissions("query")
	@GetMapping("/codes/{reward_id}")
	public String listCodesByRewardId(@PathVariable("reward_id")Integer reward_id, Map<String,Object> map){
		map.put("codes",redeemCodeService.findAllCodesByRewardId(reward_id));
		map.put("reward_id",reward_id);
		return "background/redeem_code_list";
	}

	@RequiresRoles("admin")
	@RequiresPermissions("query")
	@GetMapping("/code/{reward_id}")
	public String toAddCode(@PathVariable("reward_id")Integer reward_id, Map<String,Object> map) {
		map.put("reward_id",reward_id);
		return "background/redeem_code_input";
	}

	@RequiresRoles("admin")
	@RequiresPermissions("query")
	@PostMapping("/code")
	public String addCode(RedeemCode code) {
		redeemCodeService.add(code);
		return "redirect:/codes/" + code.getReward_id();
	}

	@RequiresRoles("admin")
	@RequiresPermissions("query")
	@GetMapping("/editcode/{id}")
	public String toEditCode(@PathVariable("id")Integer id, Map<String,Object> map) {
		map.put("code",redeemCodeService.findById(id));
		return "background/redeem_code_input";
	}
}
