package org.teamplus.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Apps {

	@GetMapping("/apps-calendar")
	public String apps_calendar() {
		return "apps/calendar";
	}

	
	@GetMapping("/apps-mailbox")
	public String apps_mailbox() {
		return "apps/mailbox";
	}

	
	@GetMapping("/apps-projects-list")
	public String apps_projects_list() {
		return "apps/projects-list";
	}
	
	@GetMapping("/apps-projects-overview")
	public String apps_projects_overview() {
		return "apps/projects-overview";
	}
	
	@GetMapping("/apps-projects-create")
	public String apps_projects_create() {
		return "apps/projects-create";
	}
	
	@GetMapping("/apps-tasks-kanban")
	public String apps_tasks_kanban() {
		return "apps/tasks-kanban";
	}
	
	@GetMapping("/apps-tasks-list-view")
	public String apps_tasks_list_view() {
		return "apps/tasks-list-view";
	}
	
	@GetMapping("/apps-tasks-details")
	public String apps_tasks_list_details() {
		return "apps/tasks-list-details";
	}

	
	@GetMapping("/apps-crypto-transactions")
	public String apps_crypto_transactions() {
		return "apps/crypto-transactions";
	}
	
	@GetMapping("/apps-crypto-buy-sell")
	public String apps_crypto_buy_sell() {
		return "apps/crypto-buy-sell";
	}
	
	@GetMapping("/apps-crypto-orders")
	public String apps_crypto_orders() {
		return "apps/crypto-orders";
	}
	
	@GetMapping("/apps-crypto-wallet")
	public String apps_crypto_wallet() {
		return "apps/crypto-wallet";
	}
	
	@GetMapping("/apps-crypto-ico")
	public String apps_crypto_ico() {
		return "apps/crypto-ico";
	}
	
	@GetMapping("/apps-crypto-kyc")
	public String apps_crypto_kyc() {
		return "apps/crypto-kyc";
	}

	
	@GetMapping("/apps-tickets-list")
	public String apps_tickets_list() {
		return "apps/tickets-list";
	}
	
	@GetMapping("/apps-tickets-details")
	public String apps_tickets_details() {
		return "apps/tickets-details";
	}

	
	@GetMapping("/apps-nft-creators")
	public String apps_nft_creators() {
		return "apps/nft-creators";
	}

	
	@GetMapping("/apps-file-manager")
	public String apps_file_manager() {
		return "apps/file-manager";
	}
	
	@GetMapping("/apps-todo")
	public String apps_todo() {
		return "apps/todo";
	}

	
	@GetMapping("/apps-api-key")
	public String apps_api_key() {
		return "apps/api-key";
	}
}
