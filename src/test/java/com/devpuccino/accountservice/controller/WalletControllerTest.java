package com.devpuccino.accountservice.controller;

import com.devpuccino.accountservice.domain.request.WalletRequest;
import com.devpuccino.accountservice.domain.response.Wallet;
import com.devpuccino.accountservice.exception.DataNotFoundException;
import com.devpuccino.accountservice.service.WalletService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class WalletControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;
    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}/{step}/"))
                .build();
    }
    @Test
    public void shouldResponseSuccess_withWallet_WhenInsertCashWallet() throws Exception {
        String request = """
                {
                    "wallet_name": "Travel",
                    "wallet_type": "cash",
                    "balance": 1000,
                    "currency": "bath"
                }
                """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request);
        Wallet wallet = new Wallet("1", "Travel", "cash", BigDecimal.valueOf(1000), "bath");
        Mockito.when(walletService.insertWallet(any(WalletRequest.class))).thenReturn(wallet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.wallet_id").value("1"))
                .andExpect(jsonPath("$.data.wallet_name").value("Travel"))
                .andExpect(jsonPath("$.data.wallet_type").value("cash"))
                .andExpect(jsonPath("$.data.balance").value("1000"))
                .andExpect(jsonPath("$.data.currency").value("bath"));
    }

    @Test
    public void shouldResponseSuccess_withWallet_WhenInsertCreditCardWallet() throws Exception {
        String request = """
                {
                    "wallet_name": "Travel",
                    "wallet_type": "credit_card",
                    "currency": "bath",
                    "credit_limit": 40000,
                    "card_type": "visa",
                    "payment_due_date": "1",
                    "billing_date": "30"
                }
                """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request);
        Wallet wallet = new Wallet("1", "Travel", "credit_card", null, "bath", BigDecimal.valueOf(40000), "visa", "1", "30");
        Mockito.when(walletService.insertWallet(any(WalletRequest.class))).thenReturn(wallet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.wallet_id").value("1"))
                .andExpect(jsonPath("$.data.wallet_name").value("Travel"))
                .andExpect(jsonPath("$.data.wallet_type").value("credit_card"))
                .andExpect(jsonPath("$.data.currency").value("bath"))
                .andExpect(jsonPath("$.data.credit_limit").value(40000))
                .andExpect(jsonPath("$.data.card_type").value("visa"))
                .andExpect(jsonPath("$.data.payment_due_date").value("1"))
                .andExpect(jsonPath("$.data.billing_date").value("30"));
    }

    @Test
    public void shouldResponseSuccess_WhenDeleteWallet() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/wallet/1");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
        Mockito.verify(walletService, Mockito.times(1)).deleteWallet("1");
    }
    @Test
    public void shouldResponseSuccess_withWallet_WhenGetWalletById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wallet/1");
        Wallet wallet = new Wallet("1", "Travel", "credit_card", null, "bath", BigDecimal.valueOf(40000), "visa", "1", "30");
        Mockito.when(walletService.getWalletById(any())).thenReturn(wallet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.wallet_id").value(wallet.walletId()))
                .andExpect(jsonPath("$.data.wallet_name").value(wallet.walletName()))
                .andExpect(jsonPath("$.data.wallet_type").value(wallet.walletType()))
                .andExpect(jsonPath("$.data.currency").value(wallet.currency()))
                .andExpect(jsonPath("$.data.credit_limit").value(wallet.creditLimit()))
                .andExpect(jsonPath("$.data.card_type").value(wallet.cardType()))
                .andExpect(jsonPath("$.data.payment_due_date").value(wallet.paymentDueDate()))
                .andExpect(jsonPath("$.data.billing_date").value(wallet.billingDate()));

    }
    @Test
    public void shouldResponseDataNotFound_WhenGetWalletById() throws Exception {
        Mockito.when(walletService.getWalletById(any())).thenThrow(new DataNotFoundException("Data not found"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wallet/1");
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404-001"))
                .andExpect(jsonPath("$.message").value("Data not found"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
    }

    @Test
    public void shouldResponseWallets_WhenGetAllWallet() throws Exception {
        Wallet wallet = new Wallet("1", "Travel", "credit_card", null, "bath", BigDecimal.valueOf(40000), "visa", "1", "30");
        Mockito.when(walletService.getAllWallet()).thenReturn(Arrays.asList(wallet));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wallet");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].wallet_id").value(wallet.walletId()))
                .andExpect(jsonPath("$.data[0].wallet_name").value(wallet.walletName()))
                .andExpect(jsonPath("$.data[0].wallet_type").value(wallet.walletType()))
                .andExpect(jsonPath("$.data[0].currency").value(wallet.currency()))
                .andExpect(jsonPath("$.data[0].credit_limit").value(wallet.creditLimit()))
                .andExpect(jsonPath("$.data[0].card_type").value(wallet.cardType()))
                .andExpect(jsonPath("$.data[0].payment_due_date").value(wallet.paymentDueDate()))
                .andExpect(jsonPath("$.data[0].billing_date").value(wallet.billingDate()));
    }
    @Test
    public void shouldResponseEmptyWallets_WhenGetAllWallet() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wallet");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(Matchers.notNullValue()));

    }

}
