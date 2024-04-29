package com.wacanda.open.ia.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/ia")
public class HotelController {

    @Autowired
    private OpenAiChatClient client;

    @GetMapping("/hoteis")
    public String buscarHoteisNaCidade(
            @RequestParam Map<String, String> params,
            HttpServletRequest request
    ) {
        String cidade = params.getOrDefault("cidade", "São Paulo");

        // Verifica se há parâmetros inesperados
        if (params.size() > 1 || (params.size() == 1 && !params.containsKey("cidade"))) {
            return "Desculpa, esse tópico é só para pesquisa de hotéis.";
        }

        PromptTemplate prompt = new PromptTemplate(
                String.format("""
          
               Olá! Você perguntou sobre hotéis em %s. Aqui estão algumas opções:
               [Por favor, liste os hotéis disponíveis em %s, incluindo informações sobre avaliação,
               faixa de preço, endereço e localização geográfica (latitude e longitude) se possível.]
               
                   """, cidade, cidade)

        );

        return client.call(prompt.create()).getResult().getOutput().getContent();
    }
}
