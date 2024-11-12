package org.alpine.invoice.invoicegem;

import org.springframework.boot.SpringApplication;

public class TestInvoicegemApplication {

	public static void main(String[] args) {
		SpringApplication.from(InvoicegemApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
